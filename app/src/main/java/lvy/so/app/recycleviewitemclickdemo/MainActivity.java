package lvy.so.app.recycleviewitemclickdemo;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import lvy.so.app.recycleviewitemclickdemo.adapter.RecycleItemCallbackAdapter;
import lvy.so.app.recycleviewitemclickdemo.adapter.RecycleItemTouchAdapter;
import lvy.so.app.recycleviewitemclickdemo.customize.OnRecycleItemClickListener;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.recycleview_view)
    RecyclerView recycleviewView;

    /**
     * 定义一个标量 判断采用哪种Adapter
     */
    private static boolean isRecycleViewTouchClick = true;
    private RecycleItemCallbackAdapter mRecycleItemCallbackAdapter;
    private RecycleItemTouchAdapter mRecycleItelTouchAdapter;
    private ArrayList<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initView();

    }

    private void initView() {
        recycleviewView.setLayoutManager(new LinearLayoutManager(this));
        recycleviewView.setHasFixedSize(true);

        mRecycleItemCallbackAdapter = new RecycleItemCallbackAdapter(this, mList);
        mRecycleItelTouchAdapter = new RecycleItemTouchAdapter(this, mList);
        if (isRecycleViewTouchClick) {
            OnRecycleItemClickListener mRecycleItemClickListener = new OnRecycleItemClickListener(recycleviewView);
            mRecycleItemClickListener.setOnRvItemClickListener(new OnRecycleItemClickListener.OnRvItemClickListener() { //设置点击事件
                @Override
                public void onRvItemClick(RecyclerView.ViewHolder vh) {
                    ShowSnackbar("通过addOnItemTouchListener实现点击第" + vh.getLayoutPosition() + "项" + mList.get(vh.getLayoutPosition()));
                }
            });
            mRecycleItemClickListener.setOnRvItemLongClickListener(new OnRecycleItemClickListener.OnRvItemLongClickListener() {
                @Override
                public void onRvItemLongClick(RecyclerView.ViewHolder vh) {   //设置长按事件
                    ShowSnackbar("通过addOnItemTouchListener实现长按第" + vh.getLayoutPosition() + "项" + mList.get(vh.getLayoutPosition()));
                }
            });
            mRecycleItemClickListener.setOnDoubleTapListener(new OnRecycleItemClickListener.OnDoubleTapListener() {
                @Override
                public void onDoubleTap(RecyclerView.ViewHolder vh) {
                    ShowSnackbar("通过addOnItemTouchListener实现双击第" + vh.getLayoutPosition() + "项" + mList.get(vh.getLayoutPosition()));
                }
            });
            recycleviewView.addOnItemTouchListener(mRecycleItemClickListener);
            recycleviewView.setAdapter(mRecycleItelTouchAdapter);
        } else {
            recycleviewView.setAdapter(mRecycleItemCallbackAdapter);
            mRecycleItemCallbackAdapter.setOnRecycleItemClickListener(new RecycleItemCallbackAdapter.OnRvItemClickListener() {
                @Override
                public void onRvItemClick(int position) {
                    ShowSnackbar("通过在Adapter中的接口回调实现点击第" + position + "项" + mList.get(position));
                }
            });
            mRecycleItemCallbackAdapter.setOnRecycleItemLongClickListener(new RecycleItemCallbackAdapter.OnRvItemLongClickListener() {
                @Override
                public void onRvItemLongClick(int position) {
                    ShowSnackbar("通过在Adapter中的接口回调实现长按第" + position + "项" + mList.get(position));
                }
            });
        }

    }

    private void initData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mList.add("第" + i + "条测试数据");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void ShowSnackbar(String text) {
        Snackbar snackbar = Snackbar.make(recycleviewView, text, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
