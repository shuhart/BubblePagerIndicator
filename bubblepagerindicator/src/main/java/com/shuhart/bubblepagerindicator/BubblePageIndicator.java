package com.shuhart.bubblepagerindicator;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * Draws circles (one for each view). The current view position is filled and
 * others are only stroked.
 */
public class BubblePageIndicator extends View implements ViewPager.OnPageChangeListener {
    private static final int INVALID_POINTER = -1;

    private float radius;
    private final Paint paintPageFill = new Paint(ANTI_ALIAS_FLAG);
    private final Paint paintStroke = new Paint(ANTI_ALIAS_FLAG);
    private final Paint paintFill = new Paint(ANTI_ALIAS_FLAG);
    private ViewPager viewPager;
    private ViewPager.OnPageChangeListener listener;
    private int currentPage;
    private int snapPage;
    private float pageOffset;
    private int scrollState;
    private boolean centered;
    private boolean snap;

    private int touchSlop;
    private float lastMotionX = -1;
    private int activePointerId = INVALID_POINTER;
    private boolean isDragging;
    private ViewPagerProvider pagerProvider;

    public BubblePageIndicator(Context context) {
        this(context, null);
    }

    public BubblePageIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.BubblePageIndicatorDefaultStyle);
    }

    public BubblePageIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (isInEditMode()) return;

        //Load defaults from resources
        final Resources res = getResources();
        final int defaultPageColor = ContextCompat.getColor(context, R.color.default_bubble_indicator_page_color);
        final int defaultFillColor = ContextCompat.getColor(context, R.color.default_bubble_indicator_fill_color);
        final int defaultStrokeColor = ContextCompat.getColor(context, R.color.default_bubble_indicator_stroke_color);
        final float defaultStrokeWidth = res.getDimension(R.dimen.default_bubble_indicator_stroke_width);
        final float defaultRadius = res.getDimension(R.dimen.default_bubble_indicator_radius);
        final boolean defaultCentered = res.getBoolean(R.bool.default_bubble_indicator_centered);
        final boolean defaultSnap = res.getBoolean(R.bool.default_bubble_indicator_snap);

        //Retrieve styles attributes
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BubblePageIndicator, defStyle, 0);

        centered = a.getBoolean(R.styleable.BubblePageIndicator_centered, defaultCentered);
        paintPageFill.setStyle(Style.FILL);
        paintPageFill.setColor(a.getColor(R.styleable.BubblePageIndicator_pageColor, defaultPageColor));
        paintStroke.setStyle(Style.STROKE);
        paintStroke.setColor(a.getColor(R.styleable.BubblePageIndicator_strokeColor, defaultStrokeColor));
        paintStroke.setStrokeWidth(a.getDimension(R.styleable.BubblePageIndicator_strokeWidth, defaultStrokeWidth));
        paintFill.setStyle(Style.FILL);
        paintFill.setColor(a.getColor(R.styleable.BubblePageIndicator_fillColor, defaultFillColor));
        radius = a.getDimension(R.styleable.BubblePageIndicator_radius, defaultRadius);
        snap = a.getBoolean(R.styleable.BubblePageIndicator_snap, defaultSnap);

        a.recycle();

        final ViewConfiguration configuration = ViewConfiguration.get(context);
        touchSlop = configuration.getScaledPagingTouchSlop();
    }


    public void setCentered(boolean centered) {
        this.centered = centered;
        invalidate();
    }

    public boolean isCentered() {
        return centered;
    }

    public void setPageColor(int pageColor) {
        paintPageFill.setColor(pageColor);
        invalidate();
    }

    public int getPageColor() {
        return paintPageFill.getColor();
    }

    public void setFillColor(int fillColor) {
        paintFill.setColor(fillColor);
        invalidate();
    }

    public int getFillColor() {
        return paintFill.getColor();
    }

    public void setStrokeColor(int strokeColor) {
        paintStroke.setColor(strokeColor);
        invalidate();
    }

    public int getStrokeColor() {
        return paintStroke.getColor();
    }

    public void setStrokeWidth(float strokeWidth) {
        paintStroke.setStrokeWidth(strokeWidth);
        invalidate();
    }

    public float getStrokeWidth() {
        return paintStroke.getStrokeWidth();
    }

    public void setRadius(float radius) {
        this.radius = radius;
        invalidate();
    }

    public float getRadius() {
        return radius;
    }

    public void setSnap(boolean snap) {
        this.snap = snap;
        invalidate();
    }

    public boolean isSnap() {
        return snap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (viewPager == null) {
            return;
        }

        final int count = pagerProvider.getRealCount();
        if (count == 0) {
            return;
        }

        if (currentPage >= count) {
            setCurrentItem(count - 1);
            return;
        }

        int longSize = getWidth();
        int longPaddingBefore = getPaddingLeft();
        int longPaddingAfter = getPaddingRight();
        int shortPaddingBefore = getPaddingTop();
        final float threeRadius = radius * 3;
        final float shortOffset = shortPaddingBefore + radius;
        float longOffset = longPaddingBefore + radius;
        if (centered) {
            longOffset += ((longSize - longPaddingBefore - longPaddingAfter) / 2.0f) - ((count * threeRadius) / 2.0f);
        }

        //Draw stroked circles
        drawStrokedCircles(canvas, count, threeRadius, shortOffset, longOffset);

        //Draw the filled circle according to the current scroll
        drawFilledCircle(canvas, threeRadius, shortOffset, longOffset);
    }

    private void drawStrokedCircles(Canvas canvas, int count, float threeRadius, float shortOffset, float longOffset) {
        float dX;
        float dY;

        float pageFillRadius = radius;
        if (paintStroke.getStrokeWidth() > 0) {
            pageFillRadius -= paintStroke.getStrokeWidth() / 2.0f;
        }

        for (int iLoop = 0; iLoop < count; iLoop++) {
            dX = longOffset + (iLoop * threeRadius);
            dY = shortOffset;
            // Only paint fill if not completely transparent
            if (paintPageFill.getAlpha() > 0) {
                canvas.drawCircle(dX, dY, pageFillRadius, paintPageFill);
            }

            // Only paint stroke if a stroke width was non-zero
            if (pageFillRadius != radius) {
                canvas.drawCircle(dX, dY, radius, paintStroke);
            }
        }
    }

    private void drawFilledCircle(Canvas canvas, float threeRadius, float shortOffset, float longOffset) {
        float dX;
        float dY;
        float cx = (snap ? snapPage : currentPage) * threeRadius;
        if (!snap) {
            cx += pageOffset * threeRadius;
        }
        dX = longOffset + cx;
        dY = shortOffset;
        canvas.drawCircle(dX, dY, radius, paintFill);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (super.onTouchEvent(ev)) {
            return true;
        }
        if ((viewPager == null) || (pagerProvider.getRealCount() == 0)) {
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
                    final int count = pagerProvider.getRealCount();
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

    public void setViewPager(ViewPager view, ViewPagerProvider pagerProvider) {
        this.pagerProvider = pagerProvider;
        if (viewPager == view) {
            return;
        }
        if (viewPager != null) {
            viewPager.removeOnPageChangeListener(this);
        }
        if (view.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        viewPager = view;
        viewPager.addOnPageChangeListener(this);
        invalidate();
    }

    public void setViewPager(ViewPager view, ViewPagerProvider pagerProvider, int initialPosition) {
        setViewPager(view, pagerProvider);
        initialPosition = pagerProvider.getRealPosition(initialPosition);
        setCurrentItem(initialPosition);
    }

    public void setCurrentItem(int item) {
        if (viewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        viewPager.setCurrentItem(item);
        currentPage = item;
        invalidate();
    }

    public void notifyDataSetChanged() {
        invalidate();
    }

    public void onPageScrollStateChanged(int state) {
        scrollState = state;

        if (listener != null) {
            listener.onPageScrollStateChanged(state);
        }
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        position = pagerProvider.getRealPosition(position);
        currentPage = position;
        pageOffset = positionOffset;
        invalidate();

        if (listener != null) {
            listener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    public void onPageSelected(int position) {
        position = pagerProvider.getRealPosition(position);
        if (snap || scrollState == ViewPager.SCROLL_STATE_IDLE) {
            currentPage = position;
            snapPage = position;
            invalidate();
        }

        if (listener != null) {
            listener.onPageSelected(position);
        }
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        this.listener = listener;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.view.View#onMeasure(int, int)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureLong(widthMeasureSpec), measureShort(heightMeasureSpec));
    }

    /**
     * Determines the width of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    private int measureLong(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if ((specMode == MeasureSpec.EXACTLY) || (viewPager == null)) {
            //We were told how big to be
            result = specSize;
        } else {
            //Calculate the width according the views count
            final int count = pagerProvider.getRealCount();
            result = (int) (getPaddingLeft() + getPaddingRight()
                    + (count * 2 * radius) + (count - 1) * radius + 1);
            //Respect AT_MOST value if that was what is called for by measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * Determines the height of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureShort(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            //We were told how big to be
            result = specSize;
        } else {
            //Measure the height
            result = (int) (2 * radius + getPaddingTop() + getPaddingBottom() + 1);
            //Respect AT_MOST value if that was what is called for by measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }
}
