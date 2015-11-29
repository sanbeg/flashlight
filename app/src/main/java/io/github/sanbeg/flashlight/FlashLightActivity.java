package io.github.sanbeg.flashlight;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.Toast;
import android.widget.ToggleButton;

public class FlashLightActivity extends Activity {
    private final Flash flash = new Flash();
    private View background;
    private ToggleButton theButton;
    private Drawable dark;
    private SharedPreferences sharedPreferences;

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

        theButton = (ToggleButton) findViewById(R.id.flashlightButton);

        ViewParent vp = theButton.getParent();
        if (vp instanceof View) {
            background = (View) vp;
            background.setOnLongClickListener(new LongClickListener());
            dark = background.getBackground();
        }

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        ImageSpan imageSpan = new ImageSpan(this, R.drawable.power_symbol);
        SpannableString content = new SpannableString("X");
        content.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        theButton.setText(content);
        theButton.setTextOn(content);
        theButton.setTextOff(content);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (theButton.isChecked()) {
            theButton.setEnabled(false);
            new FlashTask().execute();
            if (sharedPreferences.getBoolean("white", true) && background != null) {
                background.setBackgroundColor(Color.WHITE);
            }
            theButton.setKeepScreenOn(true);
        } else {
            flash.off();
            if (background != null) {
                background.setBackgroundDrawable(dark);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        flash.close();
    }

    public void onToggleClicked(View v) {
        if (theButton.isChecked()) {
            new FlashTask().execute();
            v.setKeepScreenOn(true);
            if (sharedPreferences.getBoolean("white", true) && background != null) {
                background.setBackgroundColor(Color.WHITE);
            }
        } else {
            flash.off();
            v.setKeepScreenOn(false);
            if (background != null) {
                background.setBackgroundDrawable(dark);
            }
        }
    }

    public class LongClickListener implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View view){

            boolean long_press = sharedPreferences.getBoolean("long_press", false);
            if (long_press) {
                theButton.setChecked(!theButton.isChecked());
                onToggleClicked(theButton);
            }
            return true;
        }
    }
}
