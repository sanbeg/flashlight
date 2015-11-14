package io.github.sanbeg.flashlight;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

public class FlashLightActivity extends Activity {
    private final Flash flash = new Flash();
    private View background;
    private ToggleButton theButton;
    private Drawable dark;

    public class FlashTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            return flash.on();
        }
        @Override
        protected void onPostExecute(Boolean success) {
            theButton.setEnabled(true);
            if (! success) {
                Toast.makeText(FlashLightActivity.this, "Failed to access camera.", Toast.LENGTH_SHORT);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        background = findViewById(R.id.backgroundLayout);
        background.setOnLongClickListener(new LongClickListener());
        dark = background.getBackground();
        theButton = (ToggleButton) findViewById(R.id.flashlightButton);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (theButton.isChecked()) {
            theButton.setEnabled(false);
            new FlashTask().execute();
            background.setBackgroundColor(Color.WHITE);
            theButton.setKeepScreenOn(true);
        } else {
            flash.off();
            background.setBackgroundDrawable(dark);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        flash.close();
    }

    public void onToggleClicked(View v) {
        if (((ToggleButton) v).isChecked()) {
            new FlashTask().execute();
            v.setKeepScreenOn(true);
            background.setBackgroundColor(Color.WHITE);
        } else {
            flash.off();
            v.setKeepScreenOn(false);
            background.setBackgroundDrawable(dark);
        }
    }

    public class LongClickListener implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View view){
            theButton.setChecked(!theButton.isChecked());
            onToggleClicked(theButton);
            return true;
        }
    }
}
