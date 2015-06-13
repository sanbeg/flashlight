package io.github.sanbeg.flashlight;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

public class FlashLightActivity extends Activity {
    private final Flash flash = new Flash();
    private View background;
    private ToggleButton theButton;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        background = findViewById(R.id.backgroundLayout);
        background.setOnLongClickListener(new LongClickListener());
        theButton = (ToggleButton) findViewById(R.id.flashlightButton);
    }

    @Override
    public void onResume() {
        super.onResume();
        //flash.open();
        if (theButton.isChecked()) {
            flash.on();
            theButton.setKeepScreenOn(true);
            background.setBackgroundColor(Color.WHITE);
        } else {
            background.setBackgroundColor(Color.BLACK);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        flash.close();
    }

    public void onToggleClicked(View v) {
        if (((ToggleButton) v).isChecked()) {
            flash.open();
            flash.on();
            v.setKeepScreenOn(true);
            background.setBackgroundColor(Color.WHITE);
        } else {
            //flash.off();
            flash.close();
            v.setKeepScreenOn(false);
            background.setBackgroundColor(Color.BLACK);
        }
    }

    public class LongClickListener implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View view){
            //view.setBackgroundColor(Color.WHITE);
            theButton.setChecked(!theButton.isChecked());
            onToggleClicked(theButton);
            return true;
        }
    }
}
