package com.shuhart.bubblepagerindicator;

/**
 * Created by Bogdan Kornev
 * on 10/18/2017, 9:14 AM.
 */

public interface ViewPagerProvider {
    int getRealCount();

    int getRealPosition(int position);
}
