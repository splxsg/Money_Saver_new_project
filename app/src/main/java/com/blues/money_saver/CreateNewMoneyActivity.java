package com.blues.money_saver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Blues on 04/09/2016.
 */
public class CreateNewMoneyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new);

        if (savedInstanceState == null) {

            CreateNewMoneyFragment fragment = new CreateNewMoneyFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.Create_New_Money, fragment)
                    .commit();
        }
    }
}
