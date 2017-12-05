package com.shuhart.bubblepagerindicator.sample;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.shuhart.bubblepagerindicator.BubblePageIndicator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ViewPager pager = findViewById(R.id.pager);
        final BubblePageIndicator indicator = findViewById(R.id.indicator);
        final ViewPagerAdapter adapter = new ViewPagerAdapter();
        pager.setAdapter(adapter);
        indicator.setViewPager(pager);
        adapter.setPages(new ArrayList<String>() {{
            for (int i = 0; i < 5; i++) {
                add("Item " + i);
            }
        }});
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.setPages(new ArrayList<String>() {{
                    for (int i = 0; i < 10; i++) {
                        add("Item " + i);
                    }
                }});
            }
        }, 500);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ViewPagerAdapter adapter = new ViewPagerAdapter();
//                pager.setAdapter(adapter);
//                adapter.setPages(new ArrayList<String>() {{
//                    for (int i = 0; i < 7; i++) {
//                        add("Item " + i);
//                    }
//                }});
//            }
//        }, 500);
    }
}
