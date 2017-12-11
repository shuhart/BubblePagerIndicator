package com.shuhart.bubblepagerindicator;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Created by Bogdan Kornev
 * on 11/7/2017, 5:01 PM.
 */

abstract class MotionIndicator extends View {
    private static final int INVALID_POINTER = -1;
    private float lastMotionX = -1;
    private int activePointerId = INVALID_POINTER;
    private int touchSlop;
    private boolean isDragging;

    protected int currentPage;
    protected ViewPager viewPager;

    public MotionIndicator(Context context) {
        this(context, null);
    }

    public MotionIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MotionIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        touchSlop = configuration.getScaledPagingTouchSlop();
    }

    protected abstract int getCount();

    public boolean onTouchEvent(MotionEvent ev) {
        if (super.onTouchEvent(ev)) {
            return true;
        }
        if ((viewPager == null) || (getCount() == 0)) {
            return false;
        }

        final int action = ev.getAction() & MotionEvent.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                activePointerId = ev.getPointerId(0);
                lastMotionX = ev.getX();
                break;

            case MotionEvent.ACTION_MOVE: {
                final int activePointerIndex = ev.findPointerIndex(activePointerId);
                final float x = ev.findPointerIndex(activePointerIndex);
                final float deltaX = x - lastMotionX;

                if (!isDragging) {
                    if (Math.abs(deltaX) > touchSlop) {
                        isDragging = true;
                    }
                }

                if (isDragging) {
                    lastMotionX = x;
                    if (viewPager.isFakeDragging() || viewPager.beginFakeDrag()) {
                        viewPager.fakeDragBy(deltaX);
                    }
                }

                break;
            }

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (!isDragging) {
                    final int count = getCount();
                    final int width = getWidth();
                    final float halfWidth = width / 2f;
                    final float sixthWidth = width / 6f;

                    if ((currentPage > 0) && (ev.getX() < halfWidth - sixthWidth)) {
                        if (action != MotionEvent.ACTION_CANCEL) {
                            viewPager.setCurrentItem(currentPage - 1);
                        }
                        return true;
                    } else if ((currentPage < count - 1) && (ev.getX() > halfWidth + sixthWidth)) {
                        if (action != MotionEvent.ACTION_CANCEL) {
                            viewPager.setCurrentItem(currentPage + 1);
                        }
                        return true;
                    }
                }

                isDragging = false;
                activePointerId = INVALID_POINTER;
                if (viewPager.isFakeDragging()) viewPager.endFakeDrag();
                break;

            case MotionEvent.ACTION_POINTER_DOWN: {
                final int index = ev.getActionIndex();
                lastMotionX = ev.getX(index);
                activePointerId = ev.getPointerId(index);
                break;
            }

            case MotionEvent.ACTION_POINTER_UP:
                final int pointerIndex = ev.getActionIndex();
                final int pointerId = ev.getPointerId(pointerIndex);
                if (pointerId == activePointerId) {
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    activePointerId = ev.getPointerId(newPointerIndex);
                }
                lastMotionX = ev.getX(ev.findPointerIndex(activePointerId));
                break;
        }

        return true;
    }
}
