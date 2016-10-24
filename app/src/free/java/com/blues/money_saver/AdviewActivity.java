package com.blues.money_saver;

import android.content.Context;
import android.view.View;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by Blues on 23/10/2016.
 */

public class AdviewActivity {
    View rootView;
    Context mContext;

    public AdviewActivity(View view, Context context){this.rootView = view;
    this.mContext = context;}

    public void showad(){
        AdView mAdView = (AdView) rootView.findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();
            mAdView.loadAd(adRequest);
        mAdView.setVisibility(View.VISIBLE);
    }

}
