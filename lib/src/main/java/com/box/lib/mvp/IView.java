package com.box.lib.mvp;


import android.content.Context;

/**
 * Description
 *
 * @author Linsr 2018/7/9 下午4:41
 */
public interface IView {

    /**
     * 展示空数据托底布局
     */
    void showBlankLayout();

    /**
     * 隐藏空数据托底布局
     */
    void hideBlankLayout();

    void showLoading();

    void hideLoading();

    void showError(String text);

    Context getSelf();

}
