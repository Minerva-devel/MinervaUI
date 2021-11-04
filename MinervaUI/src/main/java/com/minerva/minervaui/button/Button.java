/*
 * Copyright (c) 2021.
 * GPLv3
 * Author - Mekhtiev Vladimir
 */

package com.minerva.minervaui.button;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.StateSet;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.minerva.minervaui.R;

@SuppressLint("AppCompatCustomView")
public class Button extends android.widget.Button {

    private String mButtonText;


    private ColorStateList mIdleColorState;
    private ScaleAnimation mScaleAnimation;
    private float mCornerRadius;
    private StrokeGradientDrawable background;
    private int mStrokeColor;
    private int mStrokeWidth;
    private StateListDrawable mIdleStateDrawable;

    public Button(@NonNull Context context) {
        super(context);
        initialize(context, null);
    }

    public Button(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);

    }

    public Button(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);

    }

    private void initialize(Context context, AttributeSet attrs) {
        initAttributes(context, attrs);
        mScaleAnimation = new ScaleAnimation(1f, 0.7f,1f,0.7f);
        mScaleAnimation.setRepeatMode(Animation.REVERSE);
        mButtonText = "Hello";
        setText(mButtonText);
        initIdleState();
        setBackgroundCompat(mIdleStateDrawable);
    }

    protected int getColor(int id) {
        return getResources().getColor(id);
    }

    private void initAttributes(Context context, AttributeSet attrs) {
        if (attrs == null){
            return;
        }
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Button);

        mButtonText = ta.getString(R.styleable.Button_text);
        mCornerRadius = ta.getDimension(R.styleable.Button_cornerRadius, 48);
        mStrokeColor = ta.getColor(R.styleable.Button_strokeColor, Color.parseColor("#000000"));
        mStrokeWidth = ta.getDimensionPixelSize(R.styleable.Button_strokeWidth, 3);

        int idleStateSelector = ta.getResourceId(R.styleable.Button_selectorIdle, R.color.idle_color_selector);
        mIdleColorState = getResources().getColorStateList(idleStateSelector);

        ta.recycle();
    }

    private void initIdleState() {
        int colorNormal = getColorNormal(mIdleColorState);
        int colorDisabled = getColorDisabled(mIdleColorState);

        if (background == null){
            background = createDrawable(colorNormal);
        }
        StrokeGradientDrawable drawableDisabled = createDrawable(colorDisabled);

        mIdleStateDrawable = new StateListDrawable();

        mIdleStateDrawable.addState(new int[]{-android.R.attr.state_enabled}, drawableDisabled.getGradientDrawable());
        mIdleStateDrawable.addState(StateSet.WILD_CARD, background.getGradientDrawable());
    }

    private StrokeGradientDrawable createDrawable(int color) {
        GradientDrawable drawable = (GradientDrawable) getResources().getDrawable(R.drawable.button_shape).mutate();
        drawable.setColor(color);
        drawable.setCornerRadius(mCornerRadius);

        StrokeGradientDrawable strokeGradientDrawable = new StrokeGradientDrawable(drawable);
        strokeGradientDrawable.setStrokeColor(mStrokeColor);
        strokeGradientDrawable.setStrokeWidth(mStrokeWidth);

        return strokeGradientDrawable;
    }

    private int getColorDisabled(ColorStateList mIdleColorState) {
        return mIdleColorState.getColorForState(new int[]{-android.R.attr.state_enabled}, 0);
    }

    private int getColorNormal(ColorStateList colorStateList){
        return colorStateList.getColorForState(new int[]{android.R.attr.state_enabled}, 0);
    }

    public void setBackgroundCompat(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
    }

}
