package com.shuhart.bubblepagerindicator.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.shuhart.bubblepagerindicator.BubblePageIndicator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ViewPager pager;
    BubblePageIndicator indicator;
    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editTextPageNumber = findViewById(R.id.edittext_page_number);
        final EditText editTextRefreshPagesCount = findViewById(R.id.edittext_pages_count);
        final EditText editTextOnSurfaceCount = findViewById(R.id.edittext_on_surface_count);
        pager = findViewById(R.id.pager);
        indicator = findViewById(R.id.indicator);
        adapter = new ViewPagerAdapter();
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
                startActivity(new Intent(MainActivity.this, BlankActivity.class));
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.setPages(new ArrayList<String>() {{
            for (int i = 0; i < 7; i++) {
                add("Item " + i);
            }
        }});
        adapter.notifyDataSetChanged();
    }
}
