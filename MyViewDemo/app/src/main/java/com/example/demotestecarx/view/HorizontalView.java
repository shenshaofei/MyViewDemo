package com.example.demotestecarx.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 自定义view实现类似viewPager可以左右滑动的布局
 * 继承自viewGroup 除了要对自身的大小和位置进行测量之外，还需要对子View的测量参数负责
 */
public class HorizontalView extends ViewGroup {


    private int childWidth;
    // 上一次的手势位置
    private int lastX;
    private int lastY;

    private int currentIndex;
    private Scroller scroller;
    private VelocityTracker velocityTracker;

    public HorizontalView(Context context) {
        super(context);
        init(context);
    }

    public HorizontalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HorizontalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        scroller = new Scroller(context);
        velocityTracker = VelocityTracker.obtain();
    }

    /**
     * 重写onMeasure() 注意对wrap_content的处理
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取宽高的测量模式和测量值
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        // 测量所有子view
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        // 如果没有子view view大小为0
        if (getChildCount() == 0) {
            setMeasuredDimension(0, 0);
        } else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            View childOne = getChildAt(0);
            // 获取子view的测量尺寸
            int childWidth = childOne.getMeasuredWidth();
            int childHeitht = childOne.getMeasuredHeight();
            // 水平滑动view的宽为 子view数量 * 单个子view的宽度，高度为子view的高度
            setMeasuredDimension(getChildCount() * childWidth, childHeitht);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            View childOne = getChildAt(0);
            int childWidth = childOne.getMeasuredWidth();
            setMeasuredDimension(getChildCount() * childWidth, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            View childOne = getChildAt(0);
            int childHeight = childOne.getMeasuredHeight();
            setMeasuredDimension(widthSize, childHeight);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        View child;
        int left = 0;
        for (int i = 0; i < childCount; i++) {
            child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                childWidth = child.getMeasuredWidth();
                // 布局每一个子view的位置[],[],[] 后一个view的位置为前一个view + 子view的宽度
                child.layout(left, 0, childWidth + left,child.getMeasuredHeight());
                left += childWidth;
            }
        }

    }

    /**
     * 处理滑动事件冲突，在事件最开始位置进行拦截分发 onInterceptTouchEvent
     * 在此根据手势方向滑动判断是否进行拦截处理，水平方向拦截否则不处理
     * */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        // 获取当前手势点击的位置
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - lastX;
                int deltaY = y - lastY;
                // 当x轴方向上的移动距离 > y轴方向的移动距离进行拦截
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    intercept = true;
                }
                break;
        }
        lastX = x;
        lastY = y;

        return intercept;
    }

    /**
     * 当onInterceptTouchEvent 拦截事件时会交付给onTouchEvent处理
     * 在此处理横向view的滑动，实现viewpager效果 [] [] [] []
     * */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 添加加速度事件
        velocityTracker.addMovement(event);
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            // 滑动时只处理view的 scrollBy 距离
            case MotionEvent.ACTION_MOVE:
                int detalX = x - lastX;
                // 根据滑动的距离移动【】【】【】【】向左滑动如 last=200， x= 100  detalX = -100
                scrollBy(-detalX, 0);
                break;
            /**
             * 手势抬起时平滑的滑倒当前完整的child，根据滑动的距离是否大于childWidth ／ 2,或是快速滑动
             */
            case MotionEvent.ACTION_UP:
                int distance = getScrollX() - childWidth * currentIndex;
                if (Math.abs(distance) > childWidth / 2) {
                    if (distance > 0) {
                        currentIndex ++;
                    } else {
                        currentIndex --;
                    }
                } else {
                    // 获取X轴加速度，units为单位，默认为像素，这里为每秒1000个像素点
                    velocityTracker.computeCurrentVelocity(500);
                    float xV = velocityTracker.getXVelocity();
                    // 当x轴加速度 > 50时，产生快速滑动 也会切换view
                    if (Math.abs(xV) > 50) {
                        if (xV < 0) {
                            currentIndex ++;
                        } else {
                            currentIndex --;
                        }
                    }

                }
                // 手势抬起根据currentIndex 移动到一个完整的childView上显示【0】【1】【2】【3】（0 <= currentIndex <=getChildCount-1）
                currentIndex = currentIndex < 0 ? 0 : currentIndex > getChildCount() - 1 ? getChildCount() -1 : currentIndex;
                smoothScrollTo(currentIndex * childWidth, 0);
                velocityTracker.clear();

                break;
        }
        lastX = x;
        lastY = y;

        return true;
    }

    /**
     * 手指抬起时平滑的移动到目标位置，由当前已经滑动的位置 到 要滑动的位置，滑动持续时间
     * */
    private void smoothScrollTo(int destX, int destY) {
        // 由当前位置滑动到目标位置 如childView 显示了一半
        scroller.startScroll(getScrollX(), getScrollY(), destX - getScrollX(), destY - getScrollY(), 1000);
        // invalidate会重新绘制view也就是会调用view 的 onDraw（）而onDraw又会调用computScroll（）
        invalidate();
    }

    /**
     * 重写computeScroll（）方法
     * */
    @Override
    public void computeScroll() {
        super.computeScroll();
        // scroller.computeScrollOffset() = true 表示滑动没有结束
        if (scroller.computeScrollOffset()) {
            // 滑动到scroller 计算到的滑动位置
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            // 没有结束滑动后继续刷新view
            postInvalidate();
        }
    }
}
