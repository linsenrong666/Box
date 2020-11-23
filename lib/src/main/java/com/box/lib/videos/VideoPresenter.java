package com.box.lib.videos;

import com.box.lib.mvp.PresenterEx;

public class VideoPresenter extends PresenterEx<VideoContracts.View> implements
        VideoContracts.Presenter {

    public VideoPresenter(VideoContracts.View IView) {
        super(IView);
    }
}
