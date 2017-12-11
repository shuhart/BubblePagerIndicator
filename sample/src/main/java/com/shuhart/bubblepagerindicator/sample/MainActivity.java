package com.shuhart.bubblepagerindicator.sample;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.shuhart.bubblepagerindicator.BubblePageIndicator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editTextPageNumber = findViewById(R.id.edittext_page_number);
        final EditText editTextRefreshPagesCount = findViewById(R.id.edittext_refresh_count);
        final ViewPager pager = findViewById(R.id.pager);
        final BubblePageIndicator indicator = findViewById(R.id.indicator);
        final ViewPagerAdapter adapter = new ViewPagerAdapter();
        pager.setAdapter(adapter);
        indicator.setViewPager(pager);
        adapter.setPages(new ArrayList<String>() {{
            for (int i = 0; i < 10; i++) {
                add("Item " + i);
            }
        }});
        pager.setCurrentItem(9);
//        indicator.setCurrentItem(9);
        final SwipeRefreshLayout refreshLayout = findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String number = editTextRefreshPagesCount.getText().toString();
                if (number.isEmpty()) {
                    return;
                }
                final int pagesCount = Integer.parseInt(number);
//                pager.setAdapter(adapter);
//                indicator.setViewPager(pager);
                adapter.setPages(new ArrayList<String>() {{
                    for (int i = 0; i < pagesCount; i++) {
                        add("Item " + i);
                    }
                }});
                adapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        });
        findViewById(R.id.btn_page_number).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = editTextPageNumber.getText().toString();
                if (!number.isEmpty()) {
                    pager.setCurrentItem(Integer.parseInt(number));
                }
            }
        });
    }
}
