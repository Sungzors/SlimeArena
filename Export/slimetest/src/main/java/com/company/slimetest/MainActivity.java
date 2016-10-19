package com.company.slimetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button mStartButton;
    Button mDebugButton;
    ImageView mSlime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStartButton = (Button)findViewById(R.id.startButton);
        mDebugButton = (Button)findViewById(R.id.debugButton);
        mSlime = (ImageView)findViewById(R.id.slimeguy);

        mStartButton.setOnClickListener(this);
        mDebugButton.setOnClickListener(this);
        mSlime.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.startButton:
                Intent intent = new Intent(MainActivity.this, UnityPlayerActivity.class);
                startActivity(intent);
                break;
            case R.id.debugButton:
                openDialog();
                break;
            case R.id.slimeguy:

        }
    }

    private void openDialog() {
        //TODO: Open Fragment
    }
}
