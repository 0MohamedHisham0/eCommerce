package com.dal4.ecommerce.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActionBar;
import android.view.View;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.smarteist.autoimageslider.SliderView;

public class ScrollOB implements ObservableScrollViewCallbacks {

    SliderView slider;

    public ScrollOB(SliderView slider) {
        this.slider = slider;
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        if (scrollY == slider.getHeight()) {
            slider.setVisibility(View.GONE);
        } else {
            slider.setVisibility(View.VISIBLE);
            slider.setAlpha(0.0f);

            // Start the animation
            slider.animate()
                    .translationY(scrollY)
                    .alpha(1.0f)
                    .setListener(null);
        }
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        if (scrollState == ScrollState.UP) {

        } else if (scrollState == ScrollState.DOWN) {

        }
    }
}