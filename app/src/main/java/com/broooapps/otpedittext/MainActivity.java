package com.broooapps.otpedittext;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.broooapps.otpedittext2.OnCompleteListener;
import com.broooapps.otpedittext2.OtpEditText;

public class MainActivity extends AppCompatActivity {

    TextView textDisplay;
    OtpEditText otpEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        otpEditText = findViewById(R.id.oev_view);

        textDisplay = findViewById(R.id.text_display);


        otpEditText.setOnCompleteListener(new OnCompleteListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(String value) {
                textDisplay.setText("Entered Value: " + value);
            }
        });


    }

    public void displayText(View view) {
        String otpValue = otpEditText.getOtpValue();
        if (otpValue != null) {
            otpEditText.triggerErrorAnimation();
            textDisplay.setText("Entered Value: " + otpEditText.getOtpValue());
        }
    }
}
