package com.broooapps.otpedittext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.broooapps.otpedittext2.OnCompleteListener;
import com.broooapps.otpedittext2.OtpEditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OtpEditText editText = findViewById(R.id.top);

        editText.setOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(String value) {
                Toast.makeText(MainActivity.this, "Completed " + value, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void displayText(View view) {

        String input = String.valueOf(((EditText) findViewById(R.id.top)).getText());
        ((TextView) findViewById(R.id.textView)).setText(input);

    }
}
