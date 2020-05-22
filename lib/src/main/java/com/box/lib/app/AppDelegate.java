package com.box.lib.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.box.lib.log.JLog;
import com.box.lib.utils.CollectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


/**
 * AppDelegate简介
 *
 * @author zhengyunguang
 * @date 2018-8-26 16:20:30
 */
public final class AppDelegate extends AbsAppLife {

    private static final String MODULE_VALUE = "AppLife";

    /**
     * appLifes
     */
    private List<IAppLife> appLifes;

    /**
     * 构造函数
     */
    public AppDelegate() {
    }


    @Override
    public void onCreate(Application baseApp) {
        appLifes = parse(baseApp);
        if (appLifes != null && appLifes.size() > 0) {
            for (IAppLife life : appLifes) {
                life.onCreate(baseApp);
            }
        }
    }

    @Override
    public void onChildProcessCreate(Application app, String processName) {
        appLifes = parse(app);
        if (CollectionUtils.isListNotEmpty(appLifes)) {
            for (IAppLife life : appLifes) {
                life.onChildProcessCreate(app, processName);
            }
        }
    }

    @Override
    public void onTerminate(Application application) {
        if (appLifes != null && appLifes.size() > 0) {
            for (IAppLife life : appLifes) {
                life.onTerminate(application);
            }
        }
    }

    /**
     * 解析xml
     *
     * @param context
     * @return
     */
    public List<IAppLife> parse(Context context) {
        List<IAppLife> modules = new ArrayList<>();
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo.metaData != null) {
                for (String key : appInfo.metaData.keySet()) {
                    if (MODULE_VALUE.equals(appInfo.metaData.get(key))) {
                        IAppLife iAppLife = parseModule(key);
                        if (null != iAppLife) {
                            modules.add(iAppLife);
                        }
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("AppDelegate parse xml error", e);
        } catch (RuntimeException e) {
            // 获取数据量过大 发生异常
        }
        return modules;
    }


    /**
     * 生成实体类
     *
     * @param className
     * @return
     */
    private static IAppLife parseModule(String className) {
        Class<?> clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
        IAppLife module = null;
        try {
            Constructor<?> declaredConstructor = clazz.getDeclaredConstructor();
            declaredConstructor.setAccessible(true);
            module = (IAppLife) declaredConstructor.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            JLog.d("appdelegate", className + " 反射失败-- 请添加无参构造");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {

        }
        return module;
    }
}
