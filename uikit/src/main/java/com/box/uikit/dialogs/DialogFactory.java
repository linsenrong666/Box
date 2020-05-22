package com.box.uikit.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

import com.box.uikit.R;

/**
 * Description
 *
 * @author Linsr 2018/6/28 下午3:29
 */
public class DialogFactory {

    private static volatile DialogFactory mInstance;

    private DialogFactory() {

    }

    public static DialogFactory getInstance() {
        if (mInstance == null) {
            synchronized (DialogFactory.class) {
                if (mInstance == null) {
                    mInstance = new DialogFactory();
                }
            }
        }
        return mInstance;
    }

    public void dismissDialog(Dialog dialog) {
        try {
            dialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showDialog(Dialog dialog) {
        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showDialog(FragmentManager manager, String tag, DialogFragment dialog) {
        try {
            dialog.show(manager, tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showDialog(FragmentManager manager, DialogFragment dialog) {
        showDialog(manager, "", dialog);
    }

    public void dismissDialog(DialogFragment dialog) {
        try {
            dialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class TransparentProgressDialog extends Dialog {
        TransparentProgressDialog(Context context) {
            super(context);
            Window window = getWindow();
            if (window != null) {
                window.setBackgroundDrawableResource(android.R.color.transparent);
                window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
            setContentView(R.layout.ui_transparent_progress_dialog);
        }
    }

    /**
     * 创建透明进度对话框
     * @param activity activity
     * @return 对话框
     */
    public Dialog createTransparentProgressDialog(Activity activity) {
        return new TransparentProgressDialog(activity);
    }

}
