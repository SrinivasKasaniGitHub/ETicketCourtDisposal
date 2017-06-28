package com.example.mtpv.eticketcourt;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

/**
 * Created by mtpv on 6/20/2017.
 */

public class ChargeSheetClose extends Activity {
    TextView compny_Name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.charge_sheet_close);
        compny_Name = (TextView) findViewById(R.id.CompanyName);
        Animation marquee = AnimationUtils.loadAnimation(this, R.anim.marquee);
        compny_Name.startAnimation(marquee);
    }
}
