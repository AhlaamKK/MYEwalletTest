package com.example.myewallettest;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import company.tap.gosellapi.GoSellSDK;
import company.tap.gosellapi.internal.activities.GoSellPaymentActivity;
import company.tap.gosellapi.internal.api.facade.GoSellAPI;
import company.tap.gosellapi.internal.fragments.GoSellPaymentOptionsFragment;
import company.tap.gosellapi.internal.interfaces.GoSellPaymentDataSource;

public class WalletLaunchActivity extends AppCompatActivity implements View.OnClickListener {

    Button TopUp_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_launch);

        InitializeViews();


//CallinG AUTHENTICATION API
          GoSellSDK.init(getApplicationContext(),"sk_test_SPoE04D3brxqZF9t6s7dMyRG","com.tap.company.assessment");

    }

    public void InitializeViews(){
        TopUp_Button = findViewById(R.id.Topup_button);
        TopUp_Button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.Topup_button){
            showTopupAlert();
        }
    }

    public void showTopupAlert(){

        AlertDialog alertDialog = new AlertDialog.Builder(WalletLaunchActivity.this).create();
        alertDialog.setTitle("Wallet Top up");
        alertDialog.setMessage("Add 100 KWD to your wallet! ");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
