package com.yi.dllo.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.yi.dllo.adapter.AllBaseAdapter;

/**
 * Created by dllo on 16/1/19.
 */
public class IndexView extends View {
    private static String WORDS[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private int perHight = 20;
    private int blankHeight;
    private AllBaseAdapter allBaseAdapter;
    private ExpandableListView expandableListView;
    private Paint myPaint;

    public void setExpandableListView(ExpandableListView exp) {
        expandableListView = exp;
        allBaseAdapter = (AllBaseAdapter) expandableListView.getExpandableListAdapter();
    }

    public IndexView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public IndexView(Context context) {
        super(context);
        init();//设置画笔
    }

    private void init() {
        myPaint = new Paint();
        myPaint.setColor(Color.BLACK);
        myPaint.setAntiAlias(true);
        perHight = dpTopx(perHight, getContext());
        myPaint.setTextSize(perHight);

    }

    @Override//设置自定义组件宽高
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        blankHeight = (getMeasuredHeight() - (WORDS.length) * perHight) / (WORDS.length - 1); //留白长度计算
    }

    @Override//组件点击方法
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int index = (int) (event.getY() / (perHight + blankHeight));//获取索引位置
            if (index >= WORDS.length) {
                index = WORDS.length - 1;
            } else if (index < 0) {
                index = 0;
            }
            int postion = allBaseAdapter.getIndexFromString(WORDS[index]);
            expandableListView.setSelection(postion);
        }

        return super.onTouchEvent(event);
    }

    //画自定义组件
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < WORDS.length; i++) {
            canvas.drawText(WORDS[i], getWidth() / 2, (i + 1) * (perHight + blankHeight), myPaint);
        }
    }

    //转换像素单位
    private int dpTopx(int dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        int px = (int) ((dp * metrics.densityDpi) / (160f + 0.5f));
        return px;
    }
}
