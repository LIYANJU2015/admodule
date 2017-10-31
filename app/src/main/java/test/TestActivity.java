package test;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.admodule.AdModule;



/**
 * Created by liyanju on 2017/10/30.
 */

public class TestActivity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test_item);

        final CheckBox checkBox1 = (CheckBox)findViewById(R.id.checkbox1);
        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AppApplication.isDebug = isChecked;
            }
        });
        final CheckBox checkBox2 = (CheckBox)findViewById(R.id.checkbox2);

        findViewById(R.id.mid_ad_btn).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (checkBox2.isChecked()) {
                    AdModule.getInstance().createMaterialDialog().showMiddleAdDialog(TestActivity.this);
                } else {
                    AdModule.getInstance().createNiftyDialog().showMiddleAdDialog(TestActivity.this);
                }
            }
        });

        findViewById(R.id.big_btn).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (checkBox2.isChecked()) {
                    AdModule.getInstance().createMaterialDialog().showBigAdDialog(TestActivity.this);
                } else {
                    AdModule.getInstance().createNiftyDialog().showBigAdDialog(TestActivity.this);
                }
            }
        });

    }
}
