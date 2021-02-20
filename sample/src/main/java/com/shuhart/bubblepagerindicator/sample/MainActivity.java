package com.shuhart.bubblepagerindicator.sample;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        MainAdapter adapter = new MainAdapter();
        adapter.listener = new MainItemClickListener() {
            @Override
            public void onClick(SampleItem item) {
                switch (item) {
                    case SIMPLE:
                        startActivity(new Intent(MainActivity.this, SimpleActivity.class));
                        break;
                    case RECYCLER_VIEW:
                        startActivity(new Intent(MainActivity.this, RecyclerViewSampleActivity.class));
                        break;
                    case DELAYED:
                        startActivity(new Intent(MainActivity.this, DelayedActivity.class));
                        break;
                }
            }
        };
        adapter.items = new ArrayList<SampleItem>(){{
            add(SampleItem.SIMPLE);
            add(SampleItem.RECYCLER_VIEW);
            add(SampleItem.DELAYED);
        }};
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
