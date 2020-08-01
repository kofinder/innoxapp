package com.finderbar.innox.utilities;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.finderbar.innox.R;


public class ICGridLayout extends ViewGroup {
    private int mColumns = 4;
    private float mSpacing;

    public ICGridLayout(Context context) {
        super(context);
    }

    public ICGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ICGridLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(
                attrs,
                R.styleable.ICGridLayout_Layout);
        this.mColumns = a.getInt(R.styleable.ICGridLayout_Layout_columns, 3);
        this.mSpacing = a.getDimension(R.styleable.ICGridLayout_Layout_layout_spacing, 0);
        a.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int width = (int) (r - l);
            int side = width / mColumns;
            int children = getChildCount();
            View child = null;
            for (int i = 0; i < children; i++) {
                child = getChildAt(i);
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                int left = (int) (lp.left * side + mSpacing / 2);
                int right = (int) (lp.right * side - mSpacing / 2);
                int top = (int) (lp.top * side + mSpacing / 2);
                int bottom = (int) (lp.bottom * side - mSpacing / 2);
                child.layout(left, top, right, bottom);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureVertical(widthMeasureSpec, heightMeasureSpec);

    }

    private void measureVertical(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = 0;
        int height = 0;


        if (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.EXACTLY) {
            width = MeasureSpec.getSize(widthMeasureSpec);
        } else {
            throw new RuntimeException("widthMeasureSpec must be AT_MOST or " +
                    "EXACTLY not UNSPECIFIED when orientation == VERTICAL");
        }


        View child = null;
        int row = 0;
        int side = width / mColumns;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            child = getChildAt(i);

            LayoutParams lp = (LayoutParams) child.getLayoutParams();

            if (lp.bottom > row) {
                row = lp.bottom;
            }



            int childHeight = (lp.bottom - lp.top)*side;
            int childWidth = (lp.right-lp.left)*side;
            @SuppressLint("WrongConstant") int heightSpec = MeasureSpec.makeMeasureSpec(childHeight, LayoutParams.MATCH_PARENT);
            @SuppressLint("WrongConstant") int widthSpec = MeasureSpec.makeMeasureSpec(childWidth, LayoutParams.MATCH_PARENT);

            child.measure(widthSpec, heightSpec);
        }
        height = row * side;
        // TODO: Figure out a good way to use the heightMeasureSpec...

        setMeasuredDimension(width, height);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new ICGridLayout.LayoutParams(getContext(), attrs);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof ICGridLayout.LayoutParams;
    }

    @Override
    protected ViewGroup.LayoutParams
    generateLayoutParams(ViewGroup.LayoutParams p) {
        return new ICGridLayout.LayoutParams(p);
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        int right = 1;
        int bottom = 1;
        int top = 0;
        int left = 0;
        int width = -1;
        int height = -1;

        public LayoutParams() {
            super(MATCH_PARENT, MATCH_PARENT);
            top = 0;
            left = 1;
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            top = 0;
            left = 1;
        }

        public LayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);
            TypedArray a = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.ICGridLayout_Layout);
            left = a.getInt(R.styleable.ICGridLayout_Layout_layout_left, 0);
            top = a.getInt(R.styleable.ICGridLayout_Layout_layout_top, 0);
            right = a.getInt(R.styleable.ICGridLayout_Layout_layout_right, left + 1);
            bottom = a.getInt(R.styleable.ICGridLayout_Layout_layout_bottom, top + 1);
            height = a.getInt(R.styleable.ICGridLayout_Layout_layout_row_span, -1);
            width = a.getInt(R.styleable.ICGridLayout_Layout_layout_col_span, -1);
            if (height != -1) {
                bottom = top + height;
            }
            if (width != -1) {
                right = left + width;
            }

            a.recycle();
        }

        public LayoutParams(ViewGroup.LayoutParams params) {
            super(params);
        }

    }

}
