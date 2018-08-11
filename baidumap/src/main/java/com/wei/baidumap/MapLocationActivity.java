package com.wei.baidumap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

import java.text.DecimalFormat;

/**
 * 百度地图主页
 *
 * @author WEI
 */
public class MapLocationActivity extends FragmentActivity implements View.OnClickListener {
    private final String TAG = getClass().getSimpleName();
    private final int REQUESTCODE_SEARCH = 0x12;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;

    MapView mMapView;
    BaiduMap mBaiduMap;

    static Context mContext;
    // UI相关

    ImageButton requestLocButton;
    boolean isFirstLoc = true; // 是否首次定位

    private RelativeLayout mLocationDetailsRl;
    private TextView mPoiNameTv, mLatlonTv, mConfirmTv, mCancelTv;
    private double mLat, mLon;
    private EditText mInputEdtTxt;
    private ImageView mClearImgView;
    private LocationService locationService;
    private String mCurrentCity;
    private GeoCoder mGeoCoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initView();
        initListener();
        initMap();
    }

    private void initView()
    {
        setContentView(R.layout.activity_map_location);
        requestLocButton = (ImageButton) findViewById(R.id.button1);
        // 地图初始化
        mMapView = (MapView) findViewById(R.id.bmapView);
        mLocationDetailsRl = (RelativeLayout) findViewById(R.id.rl_location_details);
        mPoiNameTv = (TextView) findViewById(R.id.tv_poi_name);
        mLatlonTv = (TextView) findViewById(R.id.tv_latlon);
        mConfirmTv = (TextView) findViewById(R.id.tv_confirm);
        mCancelTv = (TextView) findViewById(R.id.tv_cancel);
        mInputEdtTxt = (EditText) findViewById(R.id.edtTxt_input);
        mClearImgView = (ImageView) findViewById(R.id.imgView_clear);
    }

    private void initListener() {
        mCancelTv.setOnClickListener(this);
        mConfirmTv.setOnClickListener(this);
        mClearImgView.setOnClickListener(this);
        mInputEdtTxt.setOnClickListener(this);
        mLocationDetailsRl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_cancel:
                finish();
                break;

            case R.id.rl_location_details:
            case R.id.tv_confirm:
                Intent intent = getIntent();
                intent.putExtra("lat", mLat);
                intent.putExtra("lon", mLon);
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;

            case R.id.imgView_clear:
                mInputEdtTxt.setText("");
                break;

            case R.id.edtTxt_input:
                Intent intent1 = new Intent(this, SearchAreaActivity.class);
                intent1.putExtra(SearchAreaActivity.INTENT_KEY_CITY, mCurrentCity);
                intent1.putExtra(SearchAreaActivity.INTENT_KEY_LAT, mLat);
                intent1.putExtra(SearchAreaActivity.INTENT_KEY_LNG, mLon);
                startActivityForResult(intent1, REQUESTCODE_SEARCH);
                break;

            default:
        }
    }

    private void initMap() {
        mGeoCoder = GeoCoder.newInstance();
        mGeoCoder.setOnGetGeoCodeResultListener(mOnGetGeoCoderResultListener);
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        View.OnClickListener btnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mCurrentMode) {
//                    case COMPASS:
                    case FOLLOWING:
                    case NORMAL:
//                        requestLocButton.setText("跟随");
                        mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
                        requestLocButton.setImageResource(R.drawable.ic_follow);
                        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true,
                                mCurrentMarker));
                        break;
                    default:
                        break;
                }
            }
        };
        requestLocButton.setOnClickListener(btnClickListener);

        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 开启室内图
        mBaiduMap.setIndoorEnable(true);
        locationService = BaiduMapApp.sBaiduMapApp.getLocationService();
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(myListener);
        //注册监听
        int type = getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }
        locationService.start();

        mBaiduMap.setOnMapClickListener(mOnMapClickListener);
    }

    /**
     * 地图点击事件处理
     */
    BaiduMap.OnMapClickListener mOnMapClickListener = new BaiduMap.OnMapClickListener() {
        @Override
        public void onMapClick(LatLng latLng) {
            Log.e(TAG, latLng.latitude + ", " + latLng.longitude);
            mBaiduMap.clear();
            addMarker(latLng, true);
            showLocationInfo("", latLng.latitude, latLng.longitude);
        }

        @Override
        public boolean onMapPoiClick(MapPoi mapPoi) {
            Log.e(TAG, "--- onMapPoiClick ---" + mapPoi.getName() + ", " + mapPoi.getUid() + ", position : "
                    + mapPoi.getPosition().latitude + ", " + mapPoi.getPosition().longitude);
            addMarker(mapPoi.getPosition(), true);
            showLocationInfo(mapPoi);
            return false;
        }
    };

    OnGetGeoCoderResultListener mOnGetGeoCoderResultListener = new OnGetGeoCoderResultListener() {
        @Override
        public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
            if (geoCodeResult == null || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR)
            {
                Log.e(TAG, "没有检索到结果");
                return;
            }
            //获取地理编码结果
            Log.e(TAG, geoCodeResult.getAddress());
        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
            if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR)
            {
                Log.e(TAG, "没有检索到结果");
                mLatlonTv.setText( "选中地点暂时无法获取到相应信息！" );
                return;
            }
            //获取反向地理编码结果
            String addr = reverseGeoCodeResult.getAddress();
            String sematicDes = reverseGeoCodeResult.getSematicDescription();
            Log.e(TAG, "addr : " + addr + ", sematicDes : " + sematicDes);
            if (TextUtils.isEmpty(mPoiNameTv.getText()))
            {
                if (sematicDes.contains(","))
                {
                    sematicDes = sematicDes.substring(sematicDes.lastIndexOf(",") + 1);
                }
                mPoiNameTv.setText( sematicDes );
            }
            mLatlonTv.setText( addr );
        }
    };

    /**
     * 底部显示选中poi信息
     * @param mapPoi
     */
    private void showLocationInfo(MapPoi mapPoi)
    {
        showLocationInfo(mapPoi.getName(), mapPoi.getPosition().latitude, mapPoi.getPosition().longitude);
    }

    /**
     * 底部显示选中点具体信息
     * @param name
     * @param lat
     * @param lon
     */
    private void showLocationInfo(String name, double lat, double lon)
    {
        mLocationDetailsRl.setVisibility(View.VISIBLE);
//        mPoiNameTv.setVisibility( TextUtils.isEmpty(name) ? View.GONE : View.VISIBLE );
        mPoiNameTv.setText(name);
        DecimalFormat decimalFormat = new DecimalFormat("#.000000");
        mLat = lat;
        mLon = lon;
        if (!TextUtils.isEmpty(name)) {
            String lonLat = decimalFormat.format(mLat) + "，" + decimalFormat.format(mLon);
            mLatlonTv.setText("( " + lonLat + " )");
        }
    }

    /**
     * 加标记
     * @param latLng
     * @param single
     */
    private void addMarker(LatLng latLng, boolean single) {
        if (single) {
            mBaiduMap.clear();
        }
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_marka);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(latLng)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);

        mGeoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            mCurrentCity = location.getCity();
            mLat = location.getLatitude();
            mLon = location.getLongitude();
            MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                showInCenter(ll);
                addMarker(ll, true);
                showLocationInfo("", ll.latitude, ll.longitude);
            }
            else
            {
                locationService.stop();
            }

            Log.e(TAG, "onReceiveLocation : " + location.getCity() + ", " + location.getAddrStr() + ", " + location.getProvince());
        }

        public void onReceivePoi(BDLocation poiLocation) {
            Log.e(TAG, "onReceivePoi : " + poiLocation.getCity() + ", " + poiLocation.getAddrStr() + ", " + poiLocation.getProvince());
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {
        }
    }

    /**
     * 指定位置的点居中显示
     * @param latLng
     */
    private void showInCenter(LatLng latLng)
    {
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(latLng).zoom(14.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
        {
            return;
        }
        switch (requestCode)
        {
            case REQUESTCODE_SEARCH:
                if (data != null)
                {
                    PoiInfo poiInfo = data.getParcelableExtra("poiInfo");
                    if (poiInfo != null)
                    {
                        addMarker(poiInfo.location, true);
                        showLocationInfo(poiInfo.name, poiInfo.location.latitude, poiInfo.location.longitude);
                        showInCenter(poiInfo.location);
                    }
                }
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        locationService.unregisterListener(myListener); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        mGeoCoder.destroy();
        super.onDestroy();
    }
}
