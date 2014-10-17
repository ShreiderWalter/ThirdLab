package com.example.walter.thirdlab;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.CheckBox;
import android.widget.LinearLayout;

/**
 * Created by walter on 10.10.14.
 */
public class HorizontalListItem extends LinearLayout {

    private CheckBox checkBox;

    public HorizontalListItem(Context context){
        super(context);
    }

    public HorizontalListItem(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public HorizontalListItem(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /*if(!checkBox.isChecked()){
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }*/
        return super.onTouchEvent(event);
    }
}
