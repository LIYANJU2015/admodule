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

    private static AdModule sAdModule;

    public static synchronized void init(AdCallBack adCallBack) {
       _AdModule.init(adCallBack);
    }

    private AdModule() {
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
        return _AdModule.getInstance().createMaterialDialog();
    }

    public IPopupDialog createNiftyDialog() {
        return _AdModule.getInstance().createNiftyDialog();
    }

    public IAdMob getAdMob() {
        return _AdModule.getInstance().getAdMob();
    }

    public static AdCallBack getAdCallBack() {
        return _AdModule.getAdCallBack();
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
