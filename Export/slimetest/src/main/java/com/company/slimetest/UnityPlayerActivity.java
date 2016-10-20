package com.company.slimetest;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.unity3d.player.UnityPlayer;

public class UnityPlayerActivity extends Activity
{
	protected UnityPlayer mUnityPlayer; // don't change the name of this variable; referenced from native code

    LinearLayout.LayoutParams buttonParams;
    UnityPlayer.LayoutParams rlParams;
    LinearLayout rlayout;
    Button myButton;
    Button downButton;

    // Setup activity layout
	@Override protected void onCreate (Bundle savedInstanceState)
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		getWindow().setFormat(PixelFormat.RGBX_8888); // <--- This makes xperia play happy

		mUnityPlayer = new UnityPlayer(this);
        setButton();
		mUnityPlayer.requestFocus();

		mUnityPlayer.addView(rlayout, rlParams);
		setContentView(mUnityPlayer);

	}

    void setButton(){
        rlayout = new LinearLayout(this);
        rlParams = new UnityPlayer.LayoutParams(UnityPlayer.LayoutParams.MATCH_PARENT, UnityPlayer.LayoutParams.WRAP_CONTENT);
        rlayout.setOrientation(rlayout.HORIZONTAL);
        myButton = new Button(this);
        myButton.setText("Back");
        buttonParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UnityPlayerActivity.this, MainActivity.class);
                mUnityPlayer.quit();
				downButton.setEnabled(true);
                startActivity(intent);

            }
        });
        rlayout.addView(myButton, buttonParams);

        downButton = new Button(this);
        downButton.setText("Jump");

        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Instrumentation inst = new Instrumentation();
                            inst.sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_DOWN);
                            Thread.sleep(2000);
							downButton.setEnabled(false);
                        }
                        catch(InterruptedException e){
                        }
                    }
                }).start();

            }
        });
        rlayout.addView(downButton, buttonParams);
    }

	// Quit Unity
	@Override protected void onDestroy ()
	{
		mUnityPlayer.quit();
		downButton.setEnabled(true);
		super.onDestroy();
	}

	// Pause Unity
	@Override protected void onPause()
	{
		super.onPause();
		mUnityPlayer.pause();
	}

	// Resume Unity
	@Override protected void onResume()
	{
		super.onResume();
		mUnityPlayer.resume();
	}

	// This ensures the layout will be correct.
	@Override public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		mUnityPlayer.configurationChanged(newConfig);
	}

	// Notify Unity of the focus change.
	@Override public void onWindowFocusChanged(boolean hasFocus)
	{
		super.onWindowFocusChanged(hasFocus);
		mUnityPlayer.windowFocusChanged(hasFocus);
	}

	// For some reason the multiple keyevent type is not supported by the ndk.
	// Force event injection by overriding dispatchKeyEvent().
	@Override public boolean dispatchKeyEvent(KeyEvent event)
	{
		if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
			return mUnityPlayer.injectEvent(event);
		return super.dispatchKeyEvent(event);
	}

	// Pass any events not handled by (unfocused) views straight to UnityPlayer
	@Override public boolean onKeyUp(int keyCode, KeyEvent event)     { return mUnityPlayer.injectEvent(event); }
	@Override public boolean onKeyDown(int keyCode, KeyEvent event)   { return mUnityPlayer.injectEvent(event); }
	@Override public boolean onTouchEvent(MotionEvent event)          { return mUnityPlayer.injectEvent(event); }
	/*API12*/ public boolean onGenericMotionEvent(MotionEvent event)  { return mUnityPlayer.injectEvent(event); }
}
