package com.example.xville_v1.behavior;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class TranslucentBehavior extends CoordinatorLayout.Behavior<Toolbar>{

    //Height of header
    private int mToolbarHeight = 0;

    public TranslucentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Toolbar child, View dependency) {
        return dependency instanceof TextView;
    }

    /**
     * 必须要加上  layout_anchor，对方也要layout_collapseMode才能使用
     */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, Toolbar child, View dependency) {

        // initialize the tool bar height
        if (mToolbarHeight == 0) {
            mToolbarHeight = child.getBottom() * 2;//为了更慢的
        }
        //
        // calculate the percentage from start moving to the end
        float percent = dependency.getY() / mToolbarHeight;

        //when the percentage larger than 1, then initialize it to 1
        if (percent >= 1) {
            percent = 1f;
        }

        // calculate the alpha channel
        float alpha = percent * 255;


        //set the backgroundcolor
        child.setBackgroundColor(Color.argb((int) alpha, 251, 192, 45));

        return true;
    }
}
