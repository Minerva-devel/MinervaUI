package com.minerva.minervaui.button;

import android.graphics.drawable.GradientDrawable;

public class StrokeGradientDrawable {

    private int mStrokeWidth;
    private int mStrokeColor;

    private GradientDrawable mGradientDrawable;

    public StrokeGradientDrawable(GradientDrawable mGradientDrawable) {
        this.mGradientDrawable = mGradientDrawable;
    }

    public GradientDrawable getGradientDrawable() {
        return mGradientDrawable;
    }

    public int getStrokeWidth() {
        return mStrokeWidth;
    }

    public void setStrokeWidth(int mStrokeWidth) {
        this.mStrokeWidth = mStrokeWidth;
        mGradientDrawable.setStroke(mStrokeWidth, getStrokeColor());
    }

    public int getStrokeColor() {
        return mStrokeColor;
    }

    public void setStrokeColor(int mStrokeColor) {
        this.mStrokeColor = mStrokeColor;
        mGradientDrawable.setStroke(getStrokeWidth(), mStrokeColor);
    }
}
