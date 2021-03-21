package com.broooapps.otpedittext;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.broooapps.otpedittext2.OnChangeListener;
import com.broooapps.otpedittext2.OnCompleteListener;
import com.broooapps.otpedittext2.OtpEditText;

public class MainActivity extends AppCompatActivity {

    TextView textDisplay;
    OtpEditText otpEditText;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        otpEditText = findViewById(R.id.oev_view);
        textDisplay = findViewById(R.id.text_display);
        submitButton = findViewById(R.id.submit_button);

        submitButton.setEnabled(false);

        otpEditText.setOnCompleteListener(new OnCompleteListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(String value) {
                textDisplay.setText("Entered Value: " + value);
            }
        });

        otpEditText.setOnChangeListener(new OnChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChange(String value) {
                submitButton.setEnabled(value.length() == otpEditText.getNumCharsMax());
                Log.v("PassCodeSample", "Entered Value: " + value);
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
