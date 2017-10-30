package com.admodule.admob;

import android.app.Activity;
import android.view.View;

import com.google.android.gms.ads.AdView;

/**
 * Created by liyanju on 2017/10/29.
 */

public interface IAdMob {

    AdView createBannerAdView();

    /**
     * 初始化插屏广告
     */
    void initInterstitialAd();

    /**
     * 请求插屏广告
     */
    void requestNewInterstitial();

    /**
     * 显示插屏广告
     * @return
     */
    boolean interstitialAdShow();

    void setInterstitialAdCloseListener(Runnable runnable);

    void addAdBanner(Class clazz, View adView);

    void onAdBannerDestroy(Activity activity);

    void onAdBannerResume(Activity activity);

    void onAdBannerPause(Activity activity);
}
