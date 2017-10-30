package com.admodule.dialog;

import android.app.Activity;
import android.util.Log;
import android.view.WindowManager;

import com.admodule.admob.AdMobCore;
import com.admodule.AdModule;
import com.admodule.IPopupDialog;
import com.admodule.Utils;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.VideoOptions;

import java.lang.ref.WeakReference;
import java.util.Random;

/**
 * Created by liyanju on 2017/10/29.
 */

public class AdNiftyDialogStyle implements IPopupDialog {

    private AdMobCore mAdMobCore;

    public AdNiftyDialogStyle(AdMobCore adMobCore) {
        mAdMobCore = adMobCore;
    }

    @Override
    public void showBigAdDialog(Activity activity) {
        if (activity == null) {
            return;
        }
        try {
            showAdDialog(new WeakReference<>(activity), 310, 320,
                    AdModule.getAdCallBack().getNativeAdBigId()[0]);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showMiddleAdDialog(Activity activity) {
        if (activity == null) {
            return;
        }
        try {
            showAdDialog(new WeakReference<>(activity), 310, 132,
                    AdModule.getAdCallBack().getNativeAdMidId()[0]);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static Effectstype EFFECTSTYPES[] = new Effectstype[]{
            Effectstype.Fadein,
            Effectstype.Slideright,
            Effectstype.Slideleft,
            Effectstype.Slidetop,
            Effectstype.SlideBottom,
            Effectstype.Newspager,
            Effectstype.Fall,
            Effectstype.Sidefill,
            Effectstype.Fliph,
            Effectstype.Flipv,
            Effectstype.RotateBottom,
            Effectstype.RotateLeft,
            Effectstype.Slit,
            Effectstype.Shake,
    };

    public void showAdDialog(WeakReference<Activity> activityWR, int adWidth, final int adHeight, String AdID) {
        if (activityWR.get() == null || activityWR.get().isFinishing()) {
            return;
        }

        if (Utils.isFastClick()) {
            return;
        }

        final Activity activity = activityWR.get();
        final NativeExpressAdView adView = new NativeExpressAdView(activity);

        if (Utils.px2dp(Utils.getScreenWidth()) < adWidth) {
            adWidth = Utils.px2dp(Utils.getScreenWidth()) - 20;
        }
        final int newAdWidth = adWidth;
        adView.setAdSize(new AdSize(adWidth, adHeight));

        adView.setAdUnitId(AdID);
        adView.setVideoOptions(new VideoOptions.Builder()
                .setStartMuted(true)
                .build());
        adView.loadAd(mAdMobCore.createAdRequest());
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                try {
                    Log.v("ads", "onAdLoaded>>>>> adHeight " + adHeight
                            + " newAdWidth "+ newAdWidth);
                    if (activity.isFinishing()) {
                        return;
                    }

                    NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(activity);
                    dialogBuilder.setCustomView(adView, activity);
                    dialogBuilder.isCancelableOnTouchOutside(true)
                            .withEffect(EFFECTSTYPES[new Random().nextInt(EFFECTSTYPES.length)])
                            .withDuration(600)
                            .show();

                    WindowManager.LayoutParams p = dialogBuilder.getWindow().getAttributes();
                    p.height = Utils.dp2px(adHeight);
                    p.width = Utils.dp2px(newAdWidth);
                    dialogBuilder.getWindow().setAttributes(p);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
