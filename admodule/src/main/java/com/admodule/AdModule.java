package com.admodule;

import android.content.Context;

import com.admodule.admob.AdMobCore;
import com.admodule.admob.IAdMob;
import com.admodule.dialog.AdMaterialDialogStyle;
import com.admodule.dialog.AdNiftyDialogStyle;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by liyanju on 2017/10/29.
 */

public class AdModule {

    private static Context sContext;

    private static AdCallBack sAdCallBack;

    private static AdModule sAdModule;

    private AdMobCore mAdMobCore;

    private AdMaterialDialogStyle mAdMaterialDialogStyle;
    private AdNiftyDialogStyle mAdNifyDialogStyle;

    public static void init(AdCallBack adCallBack) {
        sContext = adCallBack.getContext();
        sAdCallBack = adCallBack;
        Utils.sContext = sContext;

        MobileAds.initialize(sContext, adCallBack.getAppId());
    }

    private AdModule() {
        mAdMobCore = new AdMobCore(sContext);
    }

    public static AdModule getInstance() {
        if (sAdModule == null) {
            synchronized (AdModule.class) {
                if (sAdModule == null) {
                    sAdModule = new AdModule();
                }
            }
        }
        return sAdModule;
    }

    public IPopupDialog createMaterialDialog() {
        if (mAdMaterialDialogStyle == null) {
            mAdMaterialDialogStyle = new AdMaterialDialogStyle(mAdMobCore);
        }
        return mAdMaterialDialogStyle;
    }

    public IPopupDialog createNiftyDialog() {
        if (mAdNifyDialogStyle == null) {
            mAdNifyDialogStyle = new AdNiftyDialogStyle(mAdMobCore);
        }
        return mAdNifyDialogStyle;
    }

    public IAdMob getAdMob() {
        return mAdMobCore;
    }

    public static AdCallBack getAdCallBack() {
        return sAdCallBack;
    }

    public interface AdCallBack {

        Context getContext();

        String getAppId();

        boolean isAdDebug();

        boolean isLogDebug();

        String[] getNativeAdMidId();

        String[] getNativeAdBigId();

        String getBannerAdId();

        String getTestDevice();
    }
}
