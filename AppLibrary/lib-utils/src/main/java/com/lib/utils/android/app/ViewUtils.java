package com.lib.utils.android.app;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * author: Guazi.
 * time  : 2017/3/6.
 * desc  :
 */


public class ViewUtils {
    private ViewUtils() {
        throw new UnsupportedOperationException("ViewUtils can't instantiate");
    }

    /**
     * 根据ListView的所有子项的高度设置其高度
     *
     * @param listView
     */
    public static void setListViewHeightByAllChildrenViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {
            int totalHeight = 0;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
            listView.setLayoutParams(params);
        }
    }
}
