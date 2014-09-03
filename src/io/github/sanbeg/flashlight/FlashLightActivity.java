package io.github.sanbeg.flashlight;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;
import io.github.sanbeg.flashlight.R;


public class FlashLightActivity extends Activity {
    private Flash flash = new Flash();
    

    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

     }
    
    @Override
    public void onResume(){
    	super.onResume();
    	flash.open();
    	 	
        ToggleButton the_button = (ToggleButton) findViewById(R.id.flashlightButton);
        if (the_button.isChecked()){
        	flash.on();
            the_button.setKeepScreenOn(true);
        }
    }
    
    @Override
    public void onPause(){
    	super.onPause();
		flash.close();
    }
    public void onToggleClicked(View v) {   	
        if (((ToggleButton) v).isChecked()) {
        	flash.on();
            v.setKeepScreenOn(true);
        } else {
            flash.off();
            v.setKeepScreenOn(false);
        }
    }

}
