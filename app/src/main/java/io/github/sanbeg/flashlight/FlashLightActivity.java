package io.github.sanbeg.flashlight;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

public class FlashLightActivity extends Activity {
    private final Flash flash = new Flash();
    private View background;
    private ToggleButton the_button;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        background = findViewById(R.id.backgroundLayout);
        background.setOnLongClickListener(new LongClickListener());
        the_button = (ToggleButton) findViewById(R.id.flashlightButton);
    }

    @Override
    public void onResume() {
        super.onResume();
        //flash.open();
        if (the_button.isChecked()) {
            flash.on();
            the_button.setKeepScreenOn(true);
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
            the_button.setChecked(!the_button.isChecked());
            onToggleClicked(the_button);
            return true;
        }
    }
}
