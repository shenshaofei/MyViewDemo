package com.example.demotestecarx.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.demotestecarx.R;

/**
 * 自定义组合view，带返回尖头，title，添加按钮的 顶部通用view
 *
 * 自定义属性，根据设置的属性设置view的状态显示
 * */
public class MyHeaderView extends RelativeLayout {

    private RelativeLayout rootLayout;
    private ImageView mLeft,mRight;
    private TextView mTitle;
    private String element;
    private int mShowView;

    public MyHeaderView(Context context) {
        super(context);
        init(context);
    }

    public MyHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        initAttrs(context, attrs);
    }

    public MyHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray mTypearray = context.obtainStyledAttributes(attrs,R.styleable.MyHeaderView);
        // 获取title的文本内容
        String title = mTypearray.getString(R.styleable.MyHeaderView_title_text);
        if (!TextUtils.isEmpty(title)) {
            mTitle.setText(title);
        }
        // 获取title的颜色
        int titleColor = mTypearray.getColor(R.styleable.MyHeaderView_title_text_color,Color.YELLOW);
        mTitle.setTextColor(titleColor);

        mShowView =  mTypearray.getInt(R.styleable.MyHeaderView_show_views, 0x4);
        mTypearray.recycle();
        showView(mShowView);



    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_header, this,true);
        rootLayout = findViewById(R.id.header_root_layout);
        mLeft = findViewById(R.id.back);
        mTitle = findViewById(R.id.title);
        mRight = findViewById(R.id.add);

        // 头部view背景色，title默认颜色
        rootLayout.setBackgroundColor(Color.GRAY);
        mTitle.setTextColor(Color.WHITE);
    }

    /**
     * 根据xml中设置哪些view需要显示
     * */
    private void showView(int showView) {
        Long data = Long.valueOf(Integer.toBinaryString(showView));
        element = String.format("%06d", data);
        for (int i = 0; i < element.length(); i++) {
            if(i == 0) ;
            if(i == 1) mTitle.setVisibility(element.substring(i,i+1).equals("1")? View.VISIBLE:View.GONE);
            if(i == 2) mLeft.setVisibility(element.substring(i,i+1).equals("1")? View.VISIBLE:View.GONE);
            if(i == 3) ;
            if(i == 4) mRight.setVisibility(element.substring(i,i+1).equals("1")? View.VISIBLE:View.GONE);
            if(i == 5) ;
        }

    }

    public void setmTitle(String title) {
        mTitle.setText(title);
    }

    public void setLeftListener(OnClickListener listener) {
        mLeft.setOnClickListener(listener);
    }

    public void setRightListener(OnClickListener listener) {
        mRight.setOnClickListener(listener);
    }

}
