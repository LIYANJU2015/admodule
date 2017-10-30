package test;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.admodule.AdModule;

import magiclivewall.wall.com.smsdemo.R;

/**
 * Created by liyanju on 2017/10/30.
 */

public class TestActivity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test_item);

        findViewById(R.id.text11).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AdModule.getInstance().createNiftyDialog().showMiddleAdDialog(TestActivity.this);
            }
        });
    }
}
