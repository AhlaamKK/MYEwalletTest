package com.example.myewallettest;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.mygoselllibrary.ApiConnection.ApiInterfaces;
import com.example.mygoselllibrary.models.CreditResponseParser;
import com.example.mygoselllibrary.models.UserResponseParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import company.tap.gosellapi.GoSellSDK;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mygoselllibrary.ApiConnection.ApiClient.*;


public class WalletLaunchActivity extends AppCompatActivity implements View.OnClickListener {

    Button TopUp_Button,Transfer_button;
    TextView Hello_textView;
    TextView Balance_editText;
    TextView TopAmount_textView;
    TextView Receipt_textView;
    TextView Id_textView;
    ApiInterfaces apiService;
    String currency,balanceamnt;
    String amount;
    ScrollView mainScrollview;
    EditText  Balance_editTexts;
    TextView transactionHistry_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_launch);

        InitializeViews();


//CallinG AUTHENTICATION API
          GoSellSDK.init(getApplicationContext(),"sk_test_SPoE04D3brxqZF9t6s7dMyRG","com.tap.company.assessment");

         apiService = getClient().create(ApiInterfaces.class);
       // List<UserResponseParser> test = apiService.getUserDetails();
        Call<UserResponseParser> call = apiService.getUserDetails();
        call.enqueue(new Callback<UserResponseParser>() {
            @Override
            public void onResponse(Call<UserResponseParser> call, Response<UserResponseParser> response) {

                int statusCode = response.code();

                if (statusCode == 200) {
                    response.body().getCustomer().getName();
                    currency=response.body().getCurrency();
                    amount=response.body().getBalance().toString();
                    Hello_textView.setText("Welcome Back "+response.body().getCustomer().getName().getFirst()+"!");
                    Balance_editTexts.setText(amount+"  "+currency);

                   System.out.println("val of Resp is"+response.body());
                } else {

                }
            }

            @Override
            public void onFailure(Call<UserResponseParser> call, Throwable t) {
               // showError(t.getMessage());
            }
        });
    }

    public void InitializeViews(){
        TopUp_Button = findViewById(R.id.Topup_button);
        TopUp_Button.setOnClickListener(this);
        Transfer_button = findViewById(R.id.Transfer_button);
        Transfer_button.setOnClickListener(this);
        Hello_textView = findViewById(R.id.Hello_textView);
       // Balance_editText = findViewById(R.id.Balance_editText);
        mainScrollview = findViewById(R.id.mainScrollview);
        TopAmount_textView = findViewById(R.id.TopAmount_textView);
        Receipt_textView = findViewById(R.id.Receipt_textView);
        Id_textView = findViewById(R.id.Id_textView);

        transactionHistry_textView = findViewById(R.id.transactionHistry_textView);
        Balance_editTexts = findViewById(R.id.Balance_editTexts);


    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.Topup_button){
            showTopupAlert();
        }else if(v.getId() == R.id.Transfer_button){
            showTopupAlertTransfer();
        }
    }

    public void showTopupAlert(){

        AlertDialog alertDialog = new AlertDialog.Builder(WalletLaunchActivity.this).create();
        alertDialog.setTitle("Wallet Top up");
        alertDialog.setMessage("Add 100 KWD to your wallet! ");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Top Up",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        Map jsonObject = new HashMap();

                            jsonObject.put("id","chg_i9OK5220191557h9D20708989");


                        String sourceid = jsonObject.toString();
                        Call<CreditResponseParser> call = apiService.getCreditTransactions("1",currency,"100",sourceid);
                        call.enqueue(new Callback<CreditResponseParser>() {
                            @Override
                            public void onResponse(Call<CreditResponseParser> call, Response<CreditResponseParser> response) {

                                int statusCode = response.code();

                                if (statusCode == 201) {


                                    mainScrollview.setVisibility(View.VISIBLE);
                                    transactionHistry_textView.setVisibility(View.VISIBLE);
                                    Id_textView.setText("ID # "+response.body().getId());
                                    balanceamnt = response.body().getBalance().toString();
                                    TopAmount_textView.setText(response.body().getBalance()+"  "+currency);

                                    Receipt_textView.setText("Receipt:"+response.body().getTransactions().get(0).getReceipt().getId());
                                    showTopupAlert1();
                                    Balance_editTexts.setText(balanceamnt +" "+currency);
                                    Transfer_button.setVisibility(View.VISIBLE);
                                    System.out.println("val of Resp is"+response.body().getTransactions().toString());
                                } else {

                                }
                            }

                            @Override
                            public void onFailure(Call<CreditResponseParser> call, Throwable t) {
                                // showError(t.getMessage());
                            }
                        });
                    }



                });
        alertDialog.show();
    }

    public void showTopupAlert1(){

        AlertDialog alertDialog = new AlertDialog.Builder(WalletLaunchActivity.this).create();
        alertDialog.setTitle("Wallet Topped Up!");
        alertDialog.setMessage("You have added "+balanceamnt+currency+"to your wallett successfully !");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Back",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
        alertDialog.show();
    }

    public void showTopupAlertTransfer(){

        AlertDialog alertDialog = new AlertDialog.Builder(WalletLaunchActivity.this).create();
        alertDialog.setTitle("Transfer amount"+balanceamnt + currency);
        alertDialog.setMessage("Send Money to Mariam!" );
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Send",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
        alertDialog.show();
    }
}
