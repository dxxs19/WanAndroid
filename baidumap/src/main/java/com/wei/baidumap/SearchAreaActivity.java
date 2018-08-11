package com.wei.baidumap;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.wei.baidumap.adapter.UniversalAdapter;
import com.wei.baidumap.adapter.UniversalViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchAreaActivity extends AppCompatActivity implements TextView.OnEditorActionListener, AdapterView.OnItemClickListener, View.OnClickListener {
    private final String TAG = this.getClass().getSimpleName();
    public static final String INTENT_KEY_LAT = "SearchAreaActivity_lat";
    public static final String INTENT_KEY_LNG = "SearchAreaActivity_lng";
    public static final String INTENT_KEY_CITY = "SearchAreaActivity_city";
    public static final int ACTIVITY_REQUEST_CODE = 101;
    TextView mTV_Cancel;
    ImageView mIV_Clear;
    EditText mET_Search;
    ListView mListView;
    TextView mTV_emptyHint;
    private Dialog mCustomDialog = null;
    private List<PoiInfo> mAreaList = null;
    private UniversalAdapter mAdapter;
    double mLng, mLat;
    private PoiSearch mPoiSearch;
    private String mCurrentCity;
    LatLng center;
    int radius = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_area);
        initData();
        initView();
    }

    private void initData() {
        Intent intent = getIntent();
        mLat = intent.getDoubleExtra(INTENT_KEY_LAT, 0L);
        mLng = intent.getDoubleExtra(INTENT_KEY_LNG, 0L);
        mCurrentCity = intent.getStringExtra(INTENT_KEY_CITY);
        Log.e(TAG, mLat + ", " + mLng);
        center = new LatLng( mLat , mLng );
    }

    public void initView()
    {
        mTV_Cancel = findViewById(R.id.tv_cancel);
        mIV_Clear = findViewById(R.id.imgView_clear);
        mET_Search = findViewById(R.id.edtTxt_input);
        mListView = findViewById(R.id.listView);

        mTV_Cancel.setOnClickListener(this);
        mIV_Clear.setOnClickListener(this);
        mET_Search.setFocusable(true);
        mET_Search.setFocusableInTouchMode(true);
        mET_Search.requestFocus();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           @Override
                           public void run() {
                               InputMethodManager inputManager =
                                       (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                               inputManager.showSoftInput(mET_Search, 0);
                           }
                       },
                200);

        mAreaList = new ArrayList<>();
        mListView.setOnItemClickListener(this);
//        initEmptyView("没有搜索结果");
        initInputText();

        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
    }

    OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener(){

        @Override
        public void onGetPoiResult(PoiResult result)
        {
            Log.e(TAG, "--- onGetPoiResult ---");
            if (null == result)
            {
                Log.e(TAG, "result == null");
                return;
            }
            //获取POI检索结果
            mAreaList = result.getAllPoi();
            if (mAreaList != null && mAreaList.size() > 0)
            {
                traverse(mAreaList);
                initListView();
            }
            else
            {
                initEmptyView("没有搜索结果");
            }
            hidProgress();
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult result){
            //获取Place详情页检索结果
            Log.e(TAG, "onGetPoiDetailResult : " + result.getName() + ", " + result.getAddress());
        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
            Log.e(TAG, "--- onGetPoiIndoorResult ---");
        }
    };

    private void traverse(@NonNull List<?> objects)
    {
        if (objects == null)
        {
            return;
        }
        for (Object aClass:objects) {
            if (aClass instanceof PoiInfo)
            {
                PoiInfo poiInfo = (PoiInfo) aClass;
                Log.e(TAG, "PoiInfo : " + poiInfo.city + ", " + poiInfo.address + ", " + poiInfo.name + ", " + poiInfo.location.latitude + ", " + poiInfo.location.longitude);
            }
        }
    }

    private void initListView()
    {
        mListView.setVisibility(View.VISIBLE);
        mAdapter = new UniversalAdapter<PoiInfo>(this, mAreaList, R.layout.item_area_layout) {
            @Override
            protected void convert(UniversalViewHolder viewHolder, final PoiInfo item) {
                viewHolder.setText(R.id.txt_content, item.name);
                viewHolder.setText(R.id.txt_addr, item.address);
            }
        };
        mListView.setAdapter(mAdapter);
    }

    private boolean isFirstSet = true;
    private void initEmptyView(String message)
    {
        if (isFirstSet) {
            isFirstSet = false;
            View emptyView = findViewById(R.id.emptyview);
            mTV_emptyHint = (TextView) emptyView.findViewById(R.id.message);
            mTV_emptyHint.setText(message);
            mTV_emptyHint.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
        }
    }

    private void initInputText()
    {
        mET_Search.setOnEditorActionListener(this);
        mET_Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (s.length() > 0)
                {
                    mIV_Clear.setVisibility(View.VISIBLE);
                }
                else
                {
                    mIV_Clear.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if ((actionId == EditorInfo.IME_ACTION_SEARCH) || (actionId == EditorInfo.IME_ACTION_UNSPECIFIED)) {
            // 执行搜索操作
            String searchText = mET_Search.getText().toString();
            searchTarget(searchText, center, radius);
        }
        return false;
    }

    private void searchTarget(String searchText, LatLng center, int radius)
    {
        showProgress("正在搜索，请稍候...");
        mPoiSearch.searchNearby(new PoiNearbySearchOption()
                .keyword(searchText)
                .sortType(PoiSortType.distance_from_near_to_far)
                .location(center)
                .radius(radius)
                .pageCapacity(20)
                .pageNum(10)
        );
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PoiInfo poiInfo = mAreaList.get(position);
        if (null != poiInfo)
        {
            try {
                Intent intent = getIntent();
                intent.putExtra("poiInfo", poiInfo);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }catch (Exception e)
            {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
//        mCustomDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        mPoiSearch.destroy();
        super.onDestroy();
    }

    private void showProgress(String s) {
        /// TODO 显示进度对话框
    }

    private void hidProgress() {
        /// TODO 关闭进度对话框
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                finish();
                break;

            case R.id.imgView_clear:
                mET_Search.setText("");
                break;

            default:
        }
    }
}
