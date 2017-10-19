package com.shuhart.bubblepagerindicator.sample;

import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shuhart.bubblepagerindicator.ViewPagerProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bogdan Kornev
 * on 10/19/2017, 11:47 AM.
 */

public class ViewPagerAdapter extends PagerAdapter implements ViewPagerProvider {
    private List<String> pages = new ArrayList<String>(){{
       for (int i = 0; i < 10; i++) {
           add("PAGE " + i);
       }
    }};


    @Override
    public int getCount() {
        return pages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(((View) object));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TextView view = new TextView(container.getContext());
        view.setText(pages.get(position));
        view.setGravity(Gravity.CENTER);
        container.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }

    @Override
    public int getRealCount() {
        return getCount();
    }

    @Override
    public int getRealPosition(int position) {
        return position;
    }
}
