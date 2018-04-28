package com.yoryo.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;

import com.yoryo.R;
import com.yoryo.util.AppPreferenceStorage;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showUrlDialog();
            }
        }, 1000);
    }

    private void showUrlDialog() {
        String url = "http://192.168.0.103:8080/reset";
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.dialog_server_url, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setCancelable(false);
        final TextInputEditText editText = promptView.findViewById(R.id.editTextUrl);
        AppCompatButton buttonSubmit = promptView.findViewById(R.id.buttonSubmit);

        editText.setText(url);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppPreferenceStorage.saveAppUrl(editText.getText().toString());
                openMainActivity();
            }
        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private void openMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        // Do nothing
    }
}