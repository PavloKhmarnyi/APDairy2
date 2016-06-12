package com.example.pavlo.apdairy2.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;

import com.example.pavlo.apdairy2.R;
import com.example.pavlo.apdairy2.adapters.ViewsAdapter;
import com.example.pavlo.apdairy2.nasa_apod_api.api.ApiFactory;
import com.example.pavlo.apdairy2.nasa_apod_api.models.Data;
import com.example.pavlo.apdairy2.views.PageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity log";

    private ViewPager viewPager;
    private ViewsAdapter adapter;

    private LayoutInflater inflater;
    private Subscription subscription;

    private SimpleDateFormat simpleDateFormat;

    private ViewPager.OnPageChangeListener onPageChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inflater = getLayoutInflater();
        adapter = new ViewsAdapter();

        initPager();

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        downloadDateOfDay(null);
    }

    private void initPager() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(getOnPageChangeListener());
    }

    private void downloadDateOfDay(final String date) {
        ApiFactory service = new ApiFactory();
        Observable<Data> dataObservable = service.getDataOfDay(date);
        dataObservable.subscribeOn(Schedulers.io()).subscribe(new Action1<Data>() {
            @Override
            public void call(Data data) {
                initialPageView(data);
            }
        });
        subscription = dataObservable.subscribe();
    }

    private void initialPageView(Data data) {
        PageView view = MainActivityHelper.createViewPagerItem(MainActivity.this, inflater);
        view.setTitle(data.getTitle());
        view.setExplanation(data.getExplanation());
        view.setImage(data.getUrl());
        MainActivityHelper.addView(adapter, view.getLayout(), 0);
    }

    private ViewPager.OnPageChangeListener getOnPageChangeListener() {
        if (onPageChangeListener == null) {
            onPageChangeListener = new ViewPager.OnPageChangeListener() {
                private boolean allowSwipe = true;

                private Calendar calendarLowDate = Calendar.getInstance();
                private Calendar calendarHighDate = Calendar.getInstance();

                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if ((positionOffset == 0.0) && allowSwipe) {
                        if (position == 0) {
                            calendarLowDate.add(Calendar.DATE, -1);
                            String lowDate = simpleDateFormat.format(calendarLowDate.getTime());
                            downloadDateOfDay(lowDate);
                        }
                        allowSwipe = false;
                    }
                    Log.d(LOG_TAG, adapter.getCount() + " items, " + position + " page, offset = " + positionOffset);
                    Log.d(LOG_TAG, "lowDate = " + simpleDateFormat.format(calendarLowDate.getTime()) +
                        ", heighDate = " + simpleDateFormat.format(calendarHighDate.getTime()));
                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (state == 0) {
                        allowSwipe = true;
                    }
                    Log.d(LOG_TAG, "state = " + state);
                }
            };
        }

        return onPageChangeListener;
    }

    @Override
    protected void onDestroy() {
        if (this.subscription != null) {
            this.subscription.unsubscribe();
        }
        super.onDestroy();
    }
}
