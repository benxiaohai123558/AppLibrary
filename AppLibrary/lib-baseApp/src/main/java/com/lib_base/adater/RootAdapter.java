package com.lib_base.adater;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : Guazi
 * Time   : 17/3/4
 * Desc   : 基类adapter
 */
public abstract class RootAdapter<T> extends BaseAdapter {

    protected Context context;

    protected List<T> mList = new ArrayList<T>();

    public List<T> getList() {
        return mList;
    }

    public RootAdapter(Context context) {
        this.context = context;
    }

    public void remove(T t) {
        if (mList != null && mList.contains(t)) {
            mList.remove(t);
        }
        notifyDataSetChanged();
    }

    public void appendToList(List<T> list) {
        if (list == null) {
            return;
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void setList(List<T> list) {
        if (list == null) {
            return;
        }
        this.mList = list;
        notifyDataSetChanged();
    }

    public void appendToTopList(List<T> list) {
        if (list == null) {
            return;
        }
        mList.addAll(0, list);
        notifyDataSetChanged();
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        if (position > mList.size() - 1) {
            return null;
        }
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getExView(position, convertView, parent);
    }

    protected abstract View getExView(int position, View convertView, ViewGroup parent);

    /**
     * 添加到尾部
     *
     * @param t
     */
    public void appendBottom(T t) {
        if (t == null) {
            return;
        }
        mList.add(t);
        notifyDataSetChanged();
    }

    /**
     * 添加到头部
     *
     * @param t
     */
    public void appendTop(T t) {
        if (t == null) {
            return;
        }
        mList.add(0, t);
        notifyDataSetChanged();
    }
}
