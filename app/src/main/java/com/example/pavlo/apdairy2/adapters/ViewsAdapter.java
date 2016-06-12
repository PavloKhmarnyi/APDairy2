package com.example.pavlo.apdairy2.adapters;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by pavlo on 09.06.16.
 */
public class ViewsAdapter extends PagerAdapter {

    private ArrayList<View> views = new ArrayList<View>();

    @Override
    public int getItemPosition(Object object) {
        int index = views.indexOf(object);
        if (index == -1) {
            return POSITION_NONE;
        } else {
            return index;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = views.get(position);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public int addView(View view) {
        return addView(view, views.size());
    }

    public int addView(View view, int position) {
        views.add(position, view);

        return position;
    }

    public int removeView(ViewPager pager, View view) {
        return removeView(pager, views.indexOf(view));
    }

    public int removeView(ViewPager pager, int position) {
        pager.setAdapter(null);
        views.remove(position);
        pager.setAdapter(this);

        return position;
    }

    public View getView(int position) {
        return views.get(position);
    }
}
