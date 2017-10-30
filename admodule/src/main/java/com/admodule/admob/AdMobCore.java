package com.admodule.admob;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.admodule.AdModule;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.NativeExpressAdView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by liyanju on 2017/10/29.
 */

public class AdMobCore implements IAdMob{

    private InterstitialAd mInterstitialAd;

    private Context mContext;

    private Runnable closeRunnable;

    private HashMap<Class, ArrayList<View>> mAdViewMaps = new HashMap();

    public AdMobCore(Context context) {
        mContext = context;
    }

    public AdRequest createAdRequest() {
        AdRequest.Builder builder = new AdRequest.Builder();
        if (AdModule.getAdCallBack().isAdDebug()) {
            builder.addTestDevice(AdModule.getAdCallBack().getTestDevice());
        }
        return builder.build();
    }

    @Override
    public void initInterstitialAd() {
        mInterstitialAd = new InterstitialAd(mContext);
        mInterstitialAd.setAdUnitId("ca-app-pub-9880857526519562/2104706554");
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                if (closeRunnable != null) {
                    closeRunnable.run();
                    closeRunnable = null;
                }
                requestNewInterstitial();
            }
        });
    }

    @Override
    public void requestNewInterstitial() {
        mInterstitialAd.loadAd(createAdRequest());
    }

    @Override
    public boolean interstitialAdShow() {
        boolean isLoaded = mInterstitialAd.isLoaded();
        if (AdModule.getAdCallBack().isLogDebug()) {
            Log.v("admodule", "interstitialAdShow " + isLoaded);
        }
        if (isLoaded) {
            mInterstitialAd.show();
        }
        return isLoaded;
    }

    @Override
    public void setInterstitialAdCloseListener(Runnable runnable) {
        closeRunnable = runnable;
    }

    @Override
    public AdView createBannerAdView() {
        AdView adView = new AdView(mContext);
        adView.setAdUnitId(AdModule.getAdCallBack().getBannerAdId());
        adView.setAdSize(AdSize.BANNER);
        return adView;
    }

    @Override
    public synchronized void addAdBanner(Class clazz, View adView) {
        ArrayList<View> adViews = mAdViewMaps.get(clazz);
        if (adViews != null){
            if (adView instanceof AdView) {
                ((AdView)adView).loadAd(createAdRequest());
            } else if (adView instanceof NativeExpressAdView) {
                ((NativeExpressAdView)adView).loadAd(createAdRequest());
            }
            adViews.add(adView);
            return;
        }

        if (adView instanceof AdView) {
            ((AdView)adView).loadAd(createAdRequest());
        } else if (adView instanceof NativeExpressAdView) {
            ((NativeExpressAdView)adView).loadAd(createAdRequest());
        }
        adViews = new ArrayList<>();
        adViews.add(adView);
        mAdViewMaps.put(clazz, adViews);
    }

    @Override
    public synchronized void onAdBannerDestroy(Activity activity) {
        try {
            if (mAdViewMaps.get(activity.getClass()) == null) {
                return;
            }

            for (Class clazz : mAdViewMaps.keySet()) {
                if (clazz == activity.getClass()) {
                    for (View adView : mAdViewMaps.get(clazz)) {
                        if (adView instanceof AdView) {
                            ((AdView) adView).destroy();
                        }
                    }
                }
            }
            mAdViewMaps.remove(activity.getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void onAdBannerResume(Activity activity) {
        try {
            if (mAdViewMaps.get(activity.getClass()) == null) {
                return;
            }

            for (Class clazz : mAdViewMaps.keySet()) {
                if (clazz == activity.getClass()) {
                    for (View adView : mAdViewMaps.get(clazz)) {
                        if (adView instanceof AdView) {
                            ((AdView)adView).resume();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void onAdBannerPause(Activity activity) {
        try {
            if (mAdViewMaps.get(activity.getClass()) == null) {
                return;
            }

            for (Class clazz : mAdViewMaps.keySet()) {
                if (clazz == activity.getClass()) {
                    for (View adView : mAdViewMaps.get(clazz)) {
                        if (adView instanceof AdView) {
                            ((AdView)adView).pause();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
