package test;

import android.app.Application;
import android.content.Context;

import com.admodule.AdModule;

/**
 * Created by liyanju on 2017/10/31.
 */

public class AppApplication extends Application{

    public static boolean isDebug;

    @Override
    public void onCreate() {
        super.onCreate();
        AdModule.init(new AdModule.AdCallBack() {
            @Override
            public Context getContext() {
                return getApplicationContext();
            }

            @Override
            public String getAppId() {
                return "ca-app-pub-9880857526519562~4005088930";
            }

            @Override
            public boolean isAdDebug() {
                return isDebug;
            }

            @Override
            public boolean isLogDebug() {
                return false;
            }

            @Override
            public String[] getNativeAdMidId() {
                return new String[]{"ca-app-pub-9880857526519562/6958555332"};
            }

            @Override
            public String[] getNativeAdBigId() {
                return new String[]{"ca-app-pub-9880857526519562/9912021734"};
            }

            @Override
            public String getBannerAdId() {
                return "ca-app-pub-9880857526519562/6818954535";
            }

            @Override
            public String getTestDevice() {
                return "2B78D0808026832B1F890A41E51538C8";
            }
        });
    }
}
