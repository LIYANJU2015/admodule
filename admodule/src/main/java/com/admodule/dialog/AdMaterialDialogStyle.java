package com.admodule.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.admodule.AdModule;
import com.admodule.IPopupDialog;
import com.admodule.Utils;
import com.admodule.admob.AdMobCore;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.VideoOptions;

import java.lang.ref.WeakReference;

/**
 * Created by liyanju on 2017/10/29.
 */

public class AdMaterialDialogStyle implements IPopupDialog {

    private AdMobCore mAdMobCore;

    public AdMaterialDialogStyle(AdMobCore adMobCore) {
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
                    if (activity.isFinishing()) {
                        return;
                    }
                    AdMaterialDialog dialog = new AdMaterialDialog(activity);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.setView(adView);
                    dialog.show();

                    dialog.getAlertDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                dialog.dismiss();
                            }
                            return false;
                        }
                    });
                    WindowManager.LayoutParams p = dialog.getAlertDialog().getWindow().getAttributes();
                    p.height = Utils.dp2px(adHeight);
                    p.width = Utils.dp2px(newAdWidth);
                    dialog.getAlertDialog().getWindow ().setAttributes(p);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
