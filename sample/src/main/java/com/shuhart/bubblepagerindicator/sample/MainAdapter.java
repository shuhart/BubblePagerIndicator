package com.shuhart.bubblepagerindicator.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.SampleViewHolder> {
    List<SampleItem> items;
    MainItemClickListener listener;

    @NonNull
    @Override
    public SampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SampleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sample, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SampleViewHolder holder, int position) {
        SampleItem item = items.get(position);
        holder.bind(item);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull SampleViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.listener = listener;
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull SampleViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.listener = null;
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    static class SampleViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView = itemView.findViewById(R.id.title);
        TextView subtitleTextView = itemView.findViewById(R.id.subtitle);
        MainItemClickListener listener;

        SampleViewHolder(View itemView) {
            super(itemView);
        }

        void bind(final SampleItem item) {
            String title;
            String subtitle;
            Context context = itemView.getContext();
            switch (item) {
                case RECYCLER_VIEW:
                    title = context.getString(R.string.sample_recyclerview_title);
                    subtitle = context.getString(R.string.sample_recyclerview_subtitle);
                    break;
                case SIMPLE:
                    title = context.getString(R.string.sample_simple_title);
                    subtitle = context.getString(R.string.sample_simple_subtitle);
                    break;
                case DELAYED:
                    title = context.getString(R.string.sample_delayed_title);
                    subtitle = context.getString(R.string.sample_delayed_subtitle);
                    break;
                default:
                    throw new RuntimeException("Unknown item type");
            }
            titleTextView.setText(title);
            subtitleTextView.setText(subtitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(item);
                    }
                }
            });
        }
    }
}
