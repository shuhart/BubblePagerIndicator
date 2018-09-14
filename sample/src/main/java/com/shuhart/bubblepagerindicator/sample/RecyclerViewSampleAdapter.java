package com.shuhart.bubblepagerindicator.sample;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shuhart.bubblepagerindicator.BubblePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.PropertyResourceBundle;

public class RecyclerViewSampleAdapter extends RecyclerView.Adapter<RecyclerViewSampleAdapter.RecyclerViewIndicatorHolder> implements OnPositionChangeListener {
    private static final int ITEMS_SIZE = 20;
    private static final int PAGES_COUNT = 7;

    private List<Integer> positions = new ArrayList<Integer>(){{
        for (int i = 0; i < ITEMS_SIZE; i++) {
            add(0);
        }
    }};

    @NonNull
    @Override
    public RecyclerViewIndicatorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new RecyclerViewIndicatorHolder(inflater.inflate(R.layout.item_indicator, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewIndicatorHolder holder, int position) {
        holder.bind(positions.get(position));
    }

    @Override
    public int getItemCount() {
        return positions.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerViewIndicatorHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.positionChangeListener = this;
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerViewIndicatorHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.positionChangeListener = null;
    }

    @Override
    public void onNewPosition(int adapterPosition, int newPosition) {
        positions.set(adapterPosition, newPosition);
    }

    static class RecyclerViewIndicatorHolder extends RecyclerView.ViewHolder {
        BubblePageIndicator indicator = itemView.findViewById(R.id.indicator);
        ViewPager pager = itemView.findViewById(R.id.pager);
        OnPositionChangeListener positionChangeListener;

        RecyclerViewIndicatorHolder(View itemView) {
            super(itemView);
        }

        void bind(int position) {
            ViewPagerAdapter adapter = new ViewPagerAdapter();
            pager.setAdapter(adapter);
            indicator.setViewPager(pager);
            pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    if (positionChangeListener != null) {
                        positionChangeListener.onNewPosition(getAdapterPosition(), position);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
            adapter.setPages(new ArrayList<String>() {{
                for (int i = 0; i < PAGES_COUNT; i++) {
                    add("Item " + i);
                }
            }});
            adapter.notifyDataSetChanged();
            indicator.setCurrentItem(position);
        }
    }
}
