package lvy.so.app.recycleviewitemclickdemo.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import lvy.so.app.recycleviewitemclickdemo.R;

/**
 * Created by ping on 2016/5/12.
 * 通过接口回掉实现Item点击的Adapter
 */
public class RecycleItemCallbackAdapter extends RecyclerView.Adapter<RecycleItemCallbackAdapter.ViewHolder> {

    private ArrayList<String> mList;
    private Context mContext;
    private LayoutInflater inflater;
    private final String TAG = this.getClass().getName();

    public RecycleItemCallbackAdapter(Context context, ArrayList<String> list) {
        super();
        this.mContext = context;
        this.mList = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_recycleview, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.itemRecycleCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRvItemClickListener != null) {
                    onRvItemClickListener.onRvItemClick(holder.getLayoutPosition());
                    Log.e(TAG,"Adapter点击事件---->>>>");
                }
            }
        });
        holder.itemRecycleCardview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onRvItemLongClickListener != null) {
                    onRvItemLongClickListener.onRvItemLongClick(holder.getLayoutPosition());
                    Log.e(TAG,"Adapter长按事件---->>>>");
                    return true;   // 截取长按时间
                }
                return false;
            }
        });
        holder.itemRecycleTv.setText(holder.getLayoutPosition() + ". " + mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_recycle_tv)
        TextView itemRecycleTv;
        @Bind(R.id.item_recycle_cardview)
        CardView itemRecycleCardview;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /***
     * RecycleView 点击事件接口
     */
    public interface OnRvItemClickListener {
        void onRvItemClick(int position);  //item 点击
    }

    private OnRvItemClickListener onRvItemClickListener;

    public void setOnRecycleItemClickListener(OnRvItemClickListener rvItemClickListener) {
        this.onRvItemClickListener = rvItemClickListener;
    }

    /***
     * RecycleView 长按事件接口
     */
    public interface OnRvItemLongClickListener {
        void onRvItemLongClick(int position);
    }

    private OnRvItemLongClickListener onRvItemLongClickListener;

    public void setOnRecycleItemLongClickListener(OnRvItemLongClickListener rvItemLongClickListener) {
        this.onRvItemLongClickListener = rvItemLongClickListener;
    }
}
