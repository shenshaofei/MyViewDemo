package com.example.demotestecarx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.demotestecarx.view.MyHeaderView;
import com.example.demotestecarx.view.NormalAdapter;
import com.example.demotestecarx.view.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MyHeaderView mHeaderView;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private NormalAdapter adapter;
    private List<String> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHeaderView = findViewById(R.id.header_view);
        mHeaderView.setmTitle("确实是好标题");
        mHeaderView.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addNewItem();
                // 由于Adapter内部是直接在首个Item位置做增加操作，增加完毕后列表移动到首个Item位置
                // 不加移动 滑动中间时添加删除看不到效果
                mLinearLayoutManager.scrollToPosition(0);
                Toast.makeText(getApplicationContext(),"点击了左侧返回按钮",Toast.LENGTH_SHORT).show();
            }
        });
        mHeaderView.setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.deleteItem();

                mLinearLayoutManager.scrollToPosition(0);
                Toast.makeText(getApplicationContext(),"点击了右侧返回按钮",Toast.LENGTH_SHORT).show();
            }
        });

        // recyclerview

        for (int i = 0; i < 20; i++) {
            mDatas.add("item " + i);
        }
        recyclerView = findViewById(R.id.rv);
        mLinearLayoutManager = new LinearLayoutManager(this);
        // 不设置会导致列表显示不出来（必须设置）
        recyclerView.setLayoutManager(mLinearLayoutManager);
        // 方式1：设置默认列表分割线
//        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        // 方式2：自定义分割线通过shap绘制
//        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
//        itemDecoration.setDrawable(ContextCompat.getDrawable(this,R.drawable.custom_divider));
//        recyclerView.addItemDecoration(itemDecoration);

        // 方案3：继承 RecyclerView.ItemDecoration
        //添加默认分割线：高度为2px，颜色为灰色
//        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));

        // //添加自定义分割线：可自定义分割线drawable
//        recyclerView.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.HORIZONTAL,R.drawable.custom_divider_green));

        //添加自定义分割线：可自定义分割线高度和颜色
        recyclerView.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.HORIZONTAL, 10, getResources().getColor(R.color.colorAccent)));

        // 设置item 添加删除动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new NormalAdapter(mDatas);
        recyclerView.setAdapter(adapter);


    }
}