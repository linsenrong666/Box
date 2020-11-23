package com.box.lib.album;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;

import com.box.lib.R;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.List;

public class Album {

    public interface OnChooseResultListener {
        void onChooseResult(List<Uri> list);
    }

    private static final int REQUEST_CODE_CHOOSE = 1;

    private volatile static Album sAlbum;

    public static Album getInstance() {
        if (sAlbum != null) {
            return sAlbum;
        }
        synchronized (Album.class) {
            if (sAlbum == null) {
                sAlbum = new Album();
            }
        }
        return sAlbum;
    }

    private void choose(Activity activity, int max) {
        if (activity == null) {
            return;
        }
        Matisse.from(activity)
                //选择视频和图片
                .choose(MimeType.ofAll())
                //是否只显示选择的类型的缩略图，就不会把所有图片视频都放在一起，而是需要什么展示什么
                .showSingleMediaType(true)
                //这两行要连用 是否在选择图片中展示照相 和适配安卓7.0 FileProvider
                .capture(true)
                .captureStrategy(new CaptureStrategy(true, "PhotoPicker"))
                //有序选择图片 123456...
                .countable(true)
                //最大选择数量为9
                .maxSelectable(max)
                //选择方向
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                //界面中缩略图的质量
                .thumbnailScale(0.8f)
                //蓝色主题
                .theme(R.style.Matisse_Zhihu)
                //Glide加载方式
                .imageEngine(new GlideEngine())
                //请求码
                .forResult(REQUEST_CODE_CHOOSE);
    }

    public void chooseOne(Activity activity) {
        choose(activity, 1);
    }

    public void chooseResult(int requestCode, int resultCode, Intent data, OnChooseResultListener listener) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHOOSE) {
            List<Uri> pathList = Matisse.obtainResult(data);
            if (listener != null) {
                listener.onChooseResult(pathList);
            }
        }
    }

//    public void init(Activity activity) {
//        Matisse.from(activity)
//                //选择视频和图片
//                .choose(MimeType.ofAll())
//                //选择图片
//                .choose(MimeType.ofImage())
//                //选择视频
//                .choose(MimeType.ofVideo())
//                //自定义选择选择的类型
//                .choose(MimeType.of(MimeType.JPEG,MimeType.AVI))
//                //是否只显示选择的类型的缩略图，就不会把所有图片视频都放在一起，而是需要什么展示什么
//                .showSingleMediaType(true)
//                //这两行要连用 是否在选择图片中展示照相 和适配安卓7.0 FileProvider
//                .capture(true)
//                .captureStrategy(new CaptureStrategy(true, "PhotoPicker"))
//                //有序选择图片 123456...
//                .countable(true)
//                //最大选择数量为9
//                .maxSelectable(9)
//                //选择方向
//                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
//                //界面中缩略图的质量
//                .thumbnailScale(0.8f)
//                //蓝色主题
//                .theme(R.style.Matisse_Zhihu)
//                //黑色主题
//                .theme(R.style.Matisse_Dracula)
//                //Glide加载方式
//                .imageEngine(new GlideEngine())
//                //请求码
//                .forResult(1);
//    }
}
