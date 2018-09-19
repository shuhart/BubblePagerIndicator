package com.shuhart.bubblepagerindicator.sample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.shuhart.bubblepagerindicator.BubblePageIndicator;

import java.util.ArrayList;

public class DelayedActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setUpViews();
            }
        }, 2000);
    }

    private void setUpViews() {
        final EditText editTextPageNumber = findViewById(R.id.edittext_page_number);
        final EditText editTextRefreshPagesCount = findViewById(R.id.edittext_pages_count);
        final EditText editTextOnSurfaceCount = findViewById(R.id.edittext_on_surface_count);
        final ViewPager pager = findViewById(R.id.pager);
        final BubblePageIndicator indicator = findViewById(R.id.indicator);
        final ViewPagerAdapter adapter = new ViewPagerAdapter();
        pager.setAdapter(adapter);
        indicator.setViewPager(pager);
        findViewById(R.id.btn_page_number).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = editTextPageNumber.getText().toString();
                if (!number.isEmpty()) {
                    pager.setCurrentItem(Integer.parseInt(number));
                }
            }
        });
        findViewById(R.id.btn_on_surface_count).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = editTextOnSurfaceCount.getText().toString();
                if (!number.isEmpty()) {
                    indicator.setOnSurfaceCount(Integer.parseInt(number));
                }
            }
        });
        findViewById(R.id.next_screen_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DelayedActivity.this, BlankActivity.class));
            }
        });
        findViewById(R.id.btn_pages_number).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = editTextRefreshPagesCount.getText().toString();
                if (number.isEmpty()) {
                    return;
                }
                final int pagesCount = Integer.parseInt(number);
                adapter.setPages(new ArrayList<String>() {{
                    for (int i = 0; i < pagesCount; i++) {
                        add("Item " + i);
                    }
                }});
                adapter.notifyDataSetChanged();
            }
        });
        adapter.setPages(new ArrayList<String>() {{
            for (int i = 0; i < 7; i++) {
                add("Item " + i);
            }
        }});
        adapter.notifyDataSetChanged();
    }
}
