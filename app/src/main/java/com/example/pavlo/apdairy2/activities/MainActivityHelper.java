package com.example.pavlo.apdairy2.activities;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.example.pavlo.apdairy2.adapters.ViewsAdapter;
import com.example.pavlo.apdairy2.views.PageView;

/**
 * Created by pavlo on 09.06.16.
 */
public class MainActivityHelper {

    public static PageView createViewPagerItem(Context context, LayoutInflater inflater) {
        PageView pageView = new PageView(context, inflater);

        //return pageView.getLayout();
        return pageView;
    }

    public static void addView(ViewsAdapter adapter, View newPage) {
        int pageIndex = adapter.addView(newPage);
        adapter.notifyDataSetChanged();
    }

    public static void addView(ViewsAdapter adapter, View newPage, int position) {
        adapter.addView(newPage, position);
        adapter.notifyDataSetChanged();
    }

    public static void removeView(ViewPager pager, ViewsAdapter adapter, View defuncPage) {
        int pageIndex = adapter.removeView(pager, defuncPage);

        if (pageIndex == adapter.getCount()) {
            pageIndex--;
        }

        pager.setCurrentItem(pageIndex);
        adapter.notifyDataSetChanged();
    }

    public static View getCurrentPage(ViewPager pager, ViewsAdapter adapter) {
        return adapter.getView(pager.getCurrentItem());
    }

    public static void setCurrentPage(ViewPager pager, ViewsAdapter adapter, View pageToShow) {
        pager.setCurrentItem(adapter.getItemPosition(pageToShow), true);
    }
}
