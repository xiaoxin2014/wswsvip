package com.wdhhr.wswsvipnew.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.wdhhr.wswsvipnew.utils.ImageUtils;

import java.util.List;

/**
 * Created by felear on 2017/8/14 0014.
 */

public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private final Context mContext;
    private List<T> mDatas;
    protected final int mItemLayoutId;

    public RecyclerAdapter(Context context, List<T> datas, int itemLayoutId) {
        mContext = context;
        mDatas = datas;
        mItemLayoutId = itemLayoutId;
    }

    // item第一次创建时调用，复用时不会调用
    // viewType表示当前item类型，没有使用多布局是此值无效
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 布局加载 参数2:null时根据item内容自动分配宽高，parent时才会根据父容器属性设置宽高
        View view = LayoutInflater.from(mContext).inflate(mItemLayoutId, parent, false);

        return new ViewHolder(view);
    }

    // 每个item出来时都会调用
    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        convert(holder, position, mDatas.get(position));
    }

    public abstract void convert(RecyclerAdapter.ViewHolder helper, int position, T item);

    // 返回item数量
    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }


    /**
     * 替换元素并刷新
     *
     * @param mDatas
     */
    public void refresh(List<T> mDatas) {
        this.mDatas = mDatas;
        this.notifyDataSetChanged();
    }

    // 内部类存储item中的控件
    public class ViewHolder extends RecyclerView.ViewHolder {

        private final SparseArray<View> mViews = new SparseArray<View>();
        private View mConvertView;

        public ViewHolder(View convertView) {
            super(convertView);
            mConvertView = convertView;
        }

        public <T extends View> T getView(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        public View getConvertView() {
            return mConvertView;
        }

        /**
         * 为TextView设置字符串
         *
         * @param viewId
         * @param string
         * @return
         */
        public ViewHolder setText(int viewId, String string) {
            TextView view = getView(viewId);
            view.setText(string);
            return this;
        }

        /**
         * 获取edit文本
         *
         * @param viewId
         * @return
         */
        public String getEditText(int viewId) {
            EditText ed = getView(viewId);
            String str = ed.getText().toString();
            return str;
        }


        public void setRating(int viewId, int iScore) {
            RatingBar ratingBar = getView(viewId);
            //ratingBar.setMax(10);
            ratingBar.setProgress(iScore);
        }

        /**
         * 为ImageView设置图片
         *
         * @param viewId
         * @param drawableId
         * @return
         */
        public ViewHolder setImageResource(int viewId, int drawableId) {
            ImageView view = getView(viewId);
            view.setImageResource(drawableId);

            return this;
        }

        /**
         * 为ImageView设置图片
         *
         * @param viewId
         * @return
         */
        public ViewHolder setImageBitmap(int viewId, Bitmap bm) {
            ImageView view = getView(viewId);
            view.setImageBitmap(bm);
            return this;
        }

        /**
         * 为ImageView设置图片
         *
         * @param viewId
         * @return
         */
        public ViewHolder setImageByUrl(int viewId, String url, Context context) {
            ImageView iv = getView(viewId);
            ImageUtils.loadImageUrl(iv, url, context);
            return this;
        }

        /**
         * 给view设置背景色
         *
         * @param viewId
         * @param color
         * @return
         */
        public ViewHolder setBackgroundColor(int viewId, int color) {
            View view = getView(viewId);
            view.setBackgroundColor(color);
            return this;
        }

    }

}
