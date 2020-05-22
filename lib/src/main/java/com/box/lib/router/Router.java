package com.box.lib.router;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.template.IProvider;
import com.alibaba.android.arouter.launcher.ARouter;
import com.box.lib.R;

import java.io.Serializable;
import java.util.Map;

/**
 * 路由工具
 *
 * @author Linsr 2018/6/29 下午2:22
 */
public class Router {

    public static void startActivityForResult(Activity context,
                                              String activity,
                                              int requestCode,
                                              Params params,
                                              Flags flags) {
        Postcard build = ARouter.getInstance()
                .build(activity)
                .withTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        if (params != null && params.size() > 0) {
            build = handleParams(params, build);
        }
        if (flags != null && flags.size() > 0) {
            build = handleFlags(flags, build);
        }
        build.navigation(context, requestCode);
    }

    public static void startActivity(String activity) {
        startActivity(activity, null, null);
    }

    public static void startActivity(String activity, Params params) {
        startActivity(activity, params, null);
    }

    public static void startActivityWithFlags(Activity context, String activity, Params params, Flags flags) {
        startActivityForResult(context, activity, 0, params, flags);
    }

    public static void startActivityWithFlags(Activity context, String activity, Flags flags) {
        startActivityForResult(context, activity, 0, null, flags);
    }

    public static void startActivity(String activity, Params params, Flags flags) {
        Postcard build = ARouter.getInstance().build(activity);
        if (params != null && params.size() > 0) {
            build = handleParams(params, build);
        }
        if (flags != null && flags.size() > 0) {
            build = handleFlags(flags, build);
        }
        build.navigation();
    }

    public static <S extends IProvider> S startService(String service) {
        try {
            return (S) ARouter.getInstance().build(service).navigation(); //
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Postcard handleFlags(Flags flags, Postcard build) {
        for (int flag : flags) {
            build.withFlags(flag);
        }
        return build;
    }

    public static Fragment findFragment(String fragment, Params params) {
        Postcard build = ARouter.getInstance().build(fragment);
        if (params != null && params.size() > 0) {
            handleParams(params, build);
        }
        return (Fragment) build.navigation();
    }

    public static Fragment findFragment(String fragment) {
        return findFragment(fragment, null);
    }

    public static void inject(Object o) {
        ARouter.getInstance().inject(o);
    }

    private static Postcard handleParams(Params params, Postcard build) {
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                build.withString(key, (String) value);
            } else if (value instanceof Float) {
                build.withFloat(key, (Float) value);
            } else if (value instanceof Double) {
                build.withDouble(key, (Double) value);
            } else if (value instanceof Integer) {
                build.withInt(key, (Integer) value);
            } else if (value instanceof Boolean) {
                build.withBoolean(key, (Boolean) value);
            } else if (value instanceof Bundle) {
                build.withBundle(key, (Bundle) value);
            } else if (value instanceof Serializable) {
                build.withSerializable(key, (Serializable) value);
            } else if (value instanceof Parcelable) {
                build.withParcelable(key, (Parcelable) value);
            } else {
                throw new RuntimeException("参数类型不支持,请补充参数类型.");
            }
        }
        return build;
    }
}
