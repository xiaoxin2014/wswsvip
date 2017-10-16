package com.wdhhr.wswsvipnew.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Felear on 2017/9/11 0011.
 * 设置RecyclerView Item 间距
 */

public class RecyclerViewSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private int spanCount;

    public RecyclerViewSpaceItemDecoration(int space) {
        this.space = space;
    }

    public RecyclerViewSpaceItemDecoration(int space, int spanCount) {
        this.space = space;
        this.spanCount = spanCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        outRect.bottom = space;

        //由于每行都只有spanCount个，所以第一个都是spanCount的倍数，把左边距设为0
        if (spanCount > 0 && parent.getChildLayoutPosition(view) % spanCount != 0) {
            outRect.left = space;
        }
    }

}
