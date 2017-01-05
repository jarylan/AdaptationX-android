package com.jary.adaptation_android.widget;

import android.text.TextPaint;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by jary on 2017/1/4.
 */

public class TextViewManager {

    /**
     *返回 TextView 里面 String 所占的行数
     * @param textView
     * @param tvWidth textView 在屏幕上显示的宽（也就是 textView 父容器赐予的最大宽）
     * @return 行数
     */
    public static int getRow(TextView textView,int tvWidth){
        TextPaint paint = textView.getPaint();
        float len = paint.measureText(textView.getText().toString());
        // 行数 = textWidth/控件显示在屏幕上的宽(父容器允许的最大宽度) ;
        int width = tvWidth - textView.getPaddingLeft() - textView.getPaddingRight();// 需要去掉每一行的 padding 值
        return (int)Math.ceil(len/width);
    }

    /**
     * 在某些特定场合下； TextView 的 wrap_content 属性不生效；只好手动设置
     * @param textView
     * @param tvWidth textView 在屏幕上显示的宽（也就是 textView 父容器赐予的最大宽）
     */
    public static void setTvWrapHeight(TextView textView,int tvWidth){
        textView.measure(0,0);
        int height = textView.getMeasuredHeight();
        ViewGroup.LayoutParams params = textView.getLayoutParams();
        params.height = height * getRow(textView, tvWidth) ;
        textView.setLayoutParams(params);
    }

}
