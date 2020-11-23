package com.box.uikit.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.box.uikit.R;

import java.io.Serializable;
import java.util.List;


public class PopMenuLayout extends FrameLayout {

    private RecyclerView mRecyclerView;
    private PopMenuAdapter mAdapter;

    private Context mContext;

    public PopMenuLayout(@NonNull Context context) {
        this(context, null, 0);
    }

    public PopMenuLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopMenuLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.dd_widget_pop_menu_layout, this, false);
        mContext = context;
        mRecyclerView = view.findViewById(R.id.pop_menu_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new PopMenuAdapter(mContext, R.layout.dd_widget_pop_menu_item);
        mRecyclerView.setAdapter(mAdapter);
        addView(view);
    }

    public void setData(List<PopMenuPojo> menuPojos) {
        if (mAdapter != null) {
            mAdapter.setData(menuPojos);
            mAdapter.notifyDataSetChanged();
        }
    }

    private static class PopMenuAdapter extends RecyclerView.Adapter<PopMenuHolder> {

        private LayoutInflater mInflater;
        private Context mContext;
        private List<PopMenuPojo> mList;
        private int mLayoutResId;

        public PopMenuAdapter(Context context, int layoutResId) {
            mContext = context;
            mLayoutResId = layoutResId;
            mInflater = LayoutInflater.from(mContext);
        }

        public void setData(List<PopMenuPojo> list) {
            mList = list;
        }

        @NonNull
        @Override
        public PopMenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = mInflater.inflate(mLayoutResId, parent, false);
            return new PopMenuHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PopMenuHolder holder, int position) {
            PopMenuPojo pojo = mList.get(position);
            if (holder.menuText != null) {
                holder.menuText.setText(pojo.menuText);
            }
            if (holder.menuText != null) {
                if (pojo.menuIcon != null) {
                    holder.menuIcon.setVisibility(VISIBLE);
                    holder.menuIcon.setImageDrawable(pojo.menuIcon);
                } else {
                    holder.menuIcon.setVisibility(GONE);
                }
            }
            if (holder.menuLayout != null) {
                holder.menuLayout.setOnClickListener(pojo.clickListener);
            }
        }

        @Override
        public int getItemCount() {
            if (mList == null) {
                return 0;
            }
            return mList.size();
        }
    }

    private static class PopMenuHolder extends RecyclerView.ViewHolder {
        View menuLayout;
        TextView menuText;
        ImageView menuIcon;
        View menuDot;

        public PopMenuHolder(@NonNull View itemView) {
            super(itemView);
            try {
                menuLayout = itemView.findViewById(R.id.pop_menu_item_layout);
                menuText = itemView.findViewById(R.id.pop_menu_item_text);
                menuIcon = itemView.findViewById(R.id.pop_menu_item_icon);
                menuDot = itemView.findViewById(R.id.pop_menu_item_dot);
            } catch (Exception e) {
                Log.e("PopMenuLayout", e.toString());
            }
        }
    }

    public static class PopMenuPojo implements Serializable {
        String menuText;
        Drawable menuIcon;
        View.OnClickListener clickListener;

        public PopMenuPojo() {

        }

        public PopMenuPojo(String menuText, OnClickListener clickListener) {
            this.menuText = menuText;
            this.clickListener = clickListener;
        }
    }
}
