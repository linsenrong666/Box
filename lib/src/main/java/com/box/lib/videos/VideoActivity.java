package com.box.lib.videos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.box.lib.R;
import com.box.lib.mvp.ActivityEx;
import com.box.lib.router.url.CommonModule;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

@Route(path = CommonModule.Activity.VIDEO_PAGE)
public class VideoActivity extends ActivityEx<VideoPresenter> implements VideoContracts.View {

    private PlayerView playerView;
    private Uri mVideoUri;

    @Override
    protected int getLayoutId() {
        return R.layout.box_activity_video;
    }

    @Override
    protected void init(Intent intent) {
        super.init(intent);
        if (intent != null) {
            mVideoUri = intent.getParcelableExtra(CommonModule.Activity.VideoParams.VIDEO_PATH);
        }
    }

    @Override
    protected void initView() {
        playerView = findViewById(R.id.video_view);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        SimpleExoPlayer player = new SimpleExoPlayer.Builder(this).build();
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "yourApplicationName"));
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mVideoUri);
        // Prepare the player with the source.
        player.prepare(videoSource);
        playerView.setPlayer(player);
    }

}
