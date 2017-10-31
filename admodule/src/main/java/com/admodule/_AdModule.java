package com.admodule;

import android.content.Context;

import com.admodule.admob.AdMobCore;
import com.admodule.admob.IAdMob;
import com.admodule.dialog.AdMaterialDialogStyle;
import com.admodule.dialog.AdNiftyDialogStyle;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by liyanju on 2017/10/31.
 */

final class _AdModule {

    private static Context sContext;

    private static AdModule.AdCallBack sAdCallBack;

    private static _AdModule sAdModule;

    private AdMobCore mAdMobCore;

    private AdMaterialDialogStyle mAdMaterialDialogStyle;
    private AdNiftyDialogStyle mAdNifyDialogStyle;

    public static void init(AdModule.AdCallBack adCallBack) {
        sContext = adCallBack.getContext();
        sAdCallBack = adCallBack;
        Utils.sContext = sContext;

        MobileAds.initialize(sContext, adCallBack.getAppId());
    }

    private _AdModule() {
        mAdMobCore = new AdMobCore(sContext);
    }

    public static _AdModule getInstance() {
        if (sAdModule == null) {
            synchronized (AdModule.class) {
                if (sAdModule == null) {
                    sAdModule = new _AdModule();
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

    public static AdModule.AdCallBack getAdCallBack() {
        return sAdCallBack;
    }

}
