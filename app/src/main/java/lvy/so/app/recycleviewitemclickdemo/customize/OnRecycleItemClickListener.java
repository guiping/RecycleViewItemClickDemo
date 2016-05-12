package lvy.so.app.recycleviewitemclickdemo.customize;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ping on 2016/5/13.
 */
public class OnRecycleItemClickListener implements RecyclerView.OnItemTouchListener {
    private RecyclerView rv;
    private GestureDetectorCompat gestureDetectorCompat;
    private final String TAG = this.getClass().getName();

    public OnRecycleItemClickListener(RecyclerView recyclerView) {
        this.rv = recyclerView;
        gestureDetectorCompat = new GestureDetectorCompat(rv.getContext(),
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) { //单击事件
                        View childView = rv.findChildViewUnder(e.getX(), e.getY()); //根据手指的点击的 x , y 坐标值 获取RecycleView 的子View
                        if (childView != null) {
                            RecyclerView.ViewHolder vh = rv.getChildViewHolder(childView);  //获取子View的ViewHolder 通过onItemClick 方法回调相应
                            if (onRvItemClickListener != null) {
                                onRvItemClickListener.onRvItemClick(vh);
                                Log.e(TAG, "点击事件--->>>");
                                return true;
                            }

                        }
                        return super.onSingleTapUp(e);
                    }

                    @Override
                    public void onLongPress(MotionEvent e) { //长按事件
                        super.onLongPress(e);
                        View childView = rv.findChildViewUnder(e.getX(), e.getY());
                        if (childView != null) {
                            RecyclerView.ViewHolder vh = rv.getChildViewHolder(childView);
                            if (onRvItemLongClickListener != null) {
                                onRvItemLongClickListener.onRvItemLongClick(vh);
                                Log.e(TAG, "长按事件--->>>");
                            }
                        }

                    }

                    @Override
                    public boolean onDoubleTap(MotionEvent e) {  //双击事件
                        View childView = rv.findChildViewUnder(e.getX(), e.getY());
                        if (childView != null) {
                            RecyclerView.ViewHolder vh = rv.getChildViewHolder(childView);
                            if (onDoubleTapListener != null) {
                                onDoubleTapListener.onDoubleTap(vh);
                                Log.e(TAG, "双击事件--->>>");
                                return true;
                            }
                        }
                        return super.onDoubleTap(e);
                    }
                });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetectorCompat.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetectorCompat.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    private OnRvItemClickListener onRvItemClickListener;

    /***
     * RecycleView 点击事件
     */
    public interface OnRvItemClickListener {
        void onRvItemClick(RecyclerView.ViewHolder vh);
    }

    /***
     * 设置RecycleView 点击事件
     *
     * @param rvItemClickListener
     */
    public void setOnRvItemClickListener(OnRvItemClickListener rvItemClickListener) {
        this.onRvItemClickListener = rvItemClickListener;
    }

    private OnRvItemLongClickListener onRvItemLongClickListener;

    /***
     * RecycleView 长按事件
     */
    public interface OnRvItemLongClickListener {
        void onRvItemLongClick(RecyclerView.ViewHolder vh);
    }

    /***
     * 设置RecycleView 长按事件
     *
     * @param rvItemLongClickListener
     */
    public void setOnRvItemLongClickListener(OnRvItemLongClickListener rvItemLongClickListener) {
        this.onRvItemLongClickListener = rvItemLongClickListener;
    }

    private OnDoubleTapListener onDoubleTapListener;

    /***
     * 双击事件的接口
     */
    public interface OnDoubleTapListener {
        void onDoubleTap(RecyclerView.ViewHolder vh);
    }

    /***
     * 设置双击事件
     *
     * @param doubleTapListener
     */
    public void setOnDoubleTapListener(OnDoubleTapListener doubleTapListener) {
        this.onDoubleTapListener = doubleTapListener;
    }
}
