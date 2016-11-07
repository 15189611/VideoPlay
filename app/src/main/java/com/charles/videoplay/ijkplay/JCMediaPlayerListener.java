package com.charles.videoplay.ijkplay;

/**
 * Created by Charles on 16/7/26.
 */
public interface JCMediaPlayerListener {
    void onPrepared();

    void onCompletion();

    void onAutoCompletion();

    void onBufferingUpdate(int percent);

    void onSeekComplete();

    void onError(int what, int extra);

    void onInfo(int what, int extra);

    void onVideoSizeChanged();

    void goBackThisListener();

    boolean backToOtherListener();

    void onScrollChange();

    int getScreenType();

    String getUrl();

    int getState();

    void autoFullscreen(float x);

    void autoQuitFullscreen();

}
