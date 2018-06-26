package com.wei.wanandroid.activity.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wei.utillibrary.ImageUtil;
import com.wei.wanandroid.R;
import com.wei.wanandroid.bean.BeautyBean;

import java.util.List;

public class BeautyAdapter extends BaseAdapter
{
    private Context mContext;
    private List<BeautyBean> mBeauties;

    public BeautyAdapter(Context context, List<BeautyBean> beauties) {
        mContext = context;
        mBeauties = beauties;
    }

    @Override
    public int getCount() {
        return mBeauties.size();
    }

    @Override
    public Object getItem(int position) {
        return mBeauties.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_beauty, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        /**
         * 缓存机制原理：如果一屏能显示3栏，则刚开始会先生成3个新的convertView，当第4栏滑出一部分时，生成第4个convertView。
         *               当第1栏不可见时，第1个convertView被缓存起来。当第五栏滑出一部分时，被缓存的那第1个convertView从缓存池中拿出，
         *               充当第五栏的convertView。以后滑出的栏目都是复用前面缓存起来的convertView。也就是说，listview实际上
         *               最多只生成 （ 一屏幕的栏目数 + 1 ）个 convertView，即此处有：（3 + 1） = 4个convertView。其它的都是复用。这就是它的缓存原理。
         * convertView = android.support.constraint.ConstraintLayout{124b577 V.E...... ......I. 0,0-0,0}, postition = 0
         * convertView = android.support.constraint.ConstraintLayout{fc30e49 V.E...... ......I. 0,0-0,0}, postition = 1
         * convertView = android.support.constraint.ConstraintLayout{da6dc6f V.E...... ......I. 0,0-0,0}, postition = 2
         * convertView = android.support.constraint.ConstraintLayout{26ed08b V.E...... ......I. 0,0-0,0}, postition = 3
         * convertView = android.support.constraint.ConstraintLayout{124b577 V.E...... ........ 0,-656-1080,1}, postition = 4
         * convertView = android.support.constraint.ConstraintLayout{fc30e49 V.E...... ........ 0,-655-1080,2}, postition = 5
         * convertView = android.support.constraint.ConstraintLayout{da6dc6f V.E...... ........ 0,-655-1080,2}, postition = 6
         * convertView = android.support.constraint.ConstraintLayout{26ed08b V.E...... ........ 0,-650-1080,7}, postition = 7
         * convertView = android.support.constraint.ConstraintLayout{124b577 V.E...... ........ 0,-653-1080,4}, postition = 8
         */
        Log.e(getClass().getSimpleName(), "convertView = " + convertView + ", postition = " + position);
        BeautyBean beautyBean = (BeautyBean) getItem(position);
        if (null != beautyBean) {
            Bitmap bitmap = ImageUtil.getOptsBitmap(beautyBean.getFilePath(), 300, 600);
//            Bitmap bitmap = BitmapFactory.decodeFile(beautyBean.getFilePath());
            holder.mPicImgView.setImageBitmap(bitmap);
            holder.mTextView.setText(beautyBean.getFileName());
        }
        return convertView;
    }

    class ViewHolder
    {
        TextView mTextView;
        ImageView mPicImgView;

        public ViewHolder(View itemView)
        {
            mTextView = itemView.findViewById(R.id.tv_des);
            mPicImgView = itemView.findViewById(R.id.imgView_beauty);
        }
    }
}
