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
 * 通过RecycleView添加Touch事件实现Item点击的Adapter
 */
public class RecycleItemTouchAdapter extends RecyclerView.Adapter<RecycleItemTouchAdapter.ViewHolder> {

    private ArrayList<String> mList;
    private Context mContext;
    private LayoutInflater inflater;

    public RecycleItemTouchAdapter(Context context, ArrayList<String> list) {
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
        Log.e("RecycleItemTouchAdapter", position + "---" + holder.getAdapterPosition() + "---" + holder.getLayoutPosition() + "---" + holder.getOldPosition());
        holder.itemRecycleTv.setText(mList.get(position));
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

}

