package com.chad.library.adapter.base.listener;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

public abstract class OnItemChildLongClickListener extends SimpleClickListener {


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
        SimpleOnItemChildLongClick(adapter,view,position);
    }
    public abstract void SimpleOnItemChildLongClick(BaseQuickAdapter adapter, View view, int position);
}
