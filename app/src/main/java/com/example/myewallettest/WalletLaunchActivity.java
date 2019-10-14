package com.example.myewallettest;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WalletLaunchActivity extends AppCompatActivity implements View.OnClickListener {

    Button TopUp_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_launch);

        InitializeViews();
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
