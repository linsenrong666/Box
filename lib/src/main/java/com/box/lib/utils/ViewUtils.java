package com.box.lib.utils;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.box.lib.log.JLog;

public class ViewUtils {
    private static final String TAG = ViewUtils.class.getSimpleName();


    //重设listView的高度
    public static int setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return 0;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        return params.height;
    }

    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    public static String getEditTextContent(EditText editText) {
        if (editText == null) {
            JLog.e(TAG, "Error: EditText is null !!!");
            return "";
        }
        return editText.getText().toString().trim();
    }

    public static void setText(TextView tv, CharSequence charSequence) {
        if (tv == null) {
            return;
        }
        tv.setText(charSequence);
    }

    public static void strikethrough(TextView tv) {
        if (tv == null) {
            return;
        }
        tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public static void drawableTop(TextView tv, Drawable img) {
        if (tv == null || img == null) {
            return;
        }
        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
        tv.setCompoundDrawables(null, img, null, null);
    }

    public static void drawableTop(Context context, TextView tv, int resId) {
        if (context == null) {
            return;
        }
        Drawable drawable = context.getResources().getDrawable(resId);
        drawableTop(tv, drawable);
    }


}
