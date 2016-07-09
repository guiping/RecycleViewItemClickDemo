package lvy.so.app.recycleviewitemclickdemo;

import android.app.Service;
import android.content.ClipData;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;
import lvy.so.app.recycleviewitemclickdemo.adapter.RecycleItemCallbackAdapter;
import lvy.so.app.recycleviewitemclickdemo.adapter.RecycleItemTouchAdapter;
import lvy.so.app.recycleviewitemclickdemo.customize.OnRecycleItemClickListener;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getName();

    @Bind(R.id.recycleview_view)
    RecyclerView recycleviewView;

    /**
     * 定义一个标量 判断采用哪种Adapter
     */
    private static boolean isRecycleViewTouchClick = true;
    private RecycleItemCallbackAdapter mRecycleItemCallbackAdapter;
    private RecycleItemTouchAdapter mRecycleItelTouchAdapter;
    private ArrayList<String> mList;
    private ItemTouchHelper mItemTouchHelper;

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
        if (isRecycleViewTouchClick) {    //采用addItemTouch方式 实现点击长按 比较强大
            OnRecycleItemClickListener mRecycleItemClickListener = new OnRecycleItemClickListener(recycleviewView);
            mRecycleItemClickListener.setOnRvItemClickListener(new OnRecycleItemClickListener.OnRvItemClickListener() { //设置点击事件
                @Override
                public void onRvItemClick(RecyclerView.ViewHolder vh) {
                    ShowSnackbar("通过addOnItemTouchListener实现点击第" + vh.getLayoutPosition() + "项" + mList.get(vh.getLayoutPosition()));
                }
            });
//            mRecycleItemClickListener.setOnRvItemLongClickListener(new OnRecycleItemClickListener.OnRvItemLongClickListener() {
//                @Override
//                public void onRvItemLongClick(RecyclerView.ViewHolder vh) {   //设置长按事件
//                    ShowSnackbar("通过addOnItemTouchListener实现长按第" + vh.getLayoutPosition() + "项" + mList.get(vh.getLayoutPosition()));
//                }
//            });
            mRecycleItemClickListener.setOnDoubleTapListener(new OnRecycleItemClickListener.OnDoubleTapListener() {
                @Override
                public void onDoubleTap(RecyclerView.ViewHolder vh) {
                    ShowSnackbar("通过addOnItemTouchListener实现双击第" + vh.getLayoutPosition() + "项" + mList.get(vh.getLayoutPosition()));
                }
            });
            recycleviewView.addOnItemTouchListener(mRecycleItemClickListener);
            recycleviewView.setAdapter(mRecycleItelTouchAdapter);


        } else {   //通过给ItemView 设置点击长按事件通过接口回掉 实现点击长按 局限性比较大 但是简单
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


        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                /** 设置是否处理拖拽事件和欢动事件，以及拖拽和滑动操作的方向有两种情况:
                 *1。列表类型 拖拽只有UP，DOWN,    2. 网格类型有UP，DOWN，LEFT，RIGHT四个方向
                 */
                Log.e(TAG, "getMovementFlags");
                int dragFlags;   //拖拽标志
                int swipeFlags;   //滑动标志
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {  //网格类型
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    swipeFlags = 0;
                } else {   // 列表类型
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                }
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Log.e(TAG, "onMeve()");
                //获得拖动ViewHolder 的Position
                int fromPosition = viewHolder.getAdapterPosition();
                //得到拖动目标ViewHolder 的Position
                int toPosition = target.getAdapterPosition();

                Log.e(TAG, "onMove--->" + fromPosition + "---" + toPosition);

                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(mList, i, i + 1);   //循环改变实际数据集
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(mList, i, i - 1);
                    }
                }
                if (isRecycleViewTouchClick) {
                    mRecycleItelTouchAdapter.notifyItemMoved(fromPosition, toPosition);
                } else {
                    mRecycleItemCallbackAdapter.notifyItemMoved(fromPosition, toPosition);
                }

                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Log.e(TAG, "onSwiped()");
                /** 侧滑删除 */
                int position = viewHolder.getAdapterPosition();
                mList.remove(position);
                if (isRecycleViewTouchClick) {
                    mRecycleItelTouchAdapter.notifyItemRemoved(position);
                } else {
                    mRecycleItemCallbackAdapter.notifyItemRemoved(position);
                }
            }

            /** 长按选中的Item时候 （拖拽开始的时候）调用 */
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {

                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
                }
                super.onSelectedChanged(viewHolder, actionState);
                /**TODO 拖拽开始的时候 震动提示 */
                Vibrator vib = (Vibrator) MainActivity.this.getSystemService(Service.VIBRATOR_SERVICE);
                vib.vibrate(50);

            }

            /** 当手指松开的时候 （拖拽完成的时候） 调用 */
            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setBackgroundColor(0);

            }

            @Override
            public boolean isLongPressDragEnabled() {
                return super.isLongPressDragEnabled();
            }
        });
        mItemTouchHelper.attachToRecyclerView(recycleviewView);

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
