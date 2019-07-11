package com.broooapps.otpedittext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void displayText(View view) {

        String input = String.valueOf(((EditText) findViewById(R.id.top)).getText());
        ((TextView) findViewById(R.id.textView)).setText(input);

    }
}
