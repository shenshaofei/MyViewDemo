package com.example.demotestecarx.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

/**
 * 自定义TextView 文字上添加矩形蓝色色块中间添加白色线条
 * */
public class MyTextView extends androidx.appcompat.widget.AppCompatTextView {

    // 定义画笔，用来绘制中心曲线
    private Paint mPaint;
    public MyTextView(Context context) {
        super(context);
        init();
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint =  new Paint();
        mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        mPaint.setColor(Color.BLUE);

        // 绘制蓝色矩形大小为textview宽度的一半
        RectF rectF = new RectF(0, 0, width/2, height);
        canvas.drawRect(rectF, mPaint);

        // 绘制白色线
        mPaint.setColor(Color.WHITE);
        canvas.drawLine(0,height/2, width,height/2,mPaint);


    }
}
