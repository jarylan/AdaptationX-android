package com.jary.adaptation_android.widget;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by snaillove on 2017/1/4.
 */

public class ListViewManager {

    /**
     * 计算 ListView 自适应高度
     * @param listView
     * @return
     */
    public static int getWrapHeight(ListView listView){
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return 0;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        totalHeight += (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        return totalHeight;
    }

    /**
     * 在某些特定的场合下 ListView 的 wrap_content 属性不生效 ； 如：ListView 中嵌套 ListView
     * @param listView
     */
    public static void setLvWrapHeight(ListView listView){
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = getWrapHeight(listView);
        listView.setLayoutParams(params);
    }

}
