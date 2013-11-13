package io.github.sanbeg.flashlight;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;
import io.github.sanbeg.flashlight.R;

public class FlashLightActivity extends Activity {
    private Camera camera=null;
    private Camera.Parameters camera_parameters;
    private String flash_mode;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

     }
    
    @Override
    public void onResume(){
    	super.onResume();
    	if (camera==null)
    		camera=Camera.open();

    	camera_parameters = camera.getParameters();
    	flash_mode = camera_parameters.getFlashMode();
    	camera_parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
    	camera.setParameters(camera_parameters);
    	
        ToggleButton the_button = (ToggleButton) findViewById(R.id.flashlightButton);
        if (the_button.isChecked()){
            camera.startPreview();
            the_button.setKeepScreenOn(true);
        }

    }
    @Override
    public void onPause(){
    	super.onPause();
    	camera_parameters.setFlashMode(flash_mode);
    	camera.setParameters(camera_parameters);
        camera.release();
        camera=null;	
    }
    public void onToggleClicked(View v) {   	
        if (((ToggleButton) v).isChecked()) {
         	camera.setParameters(camera_parameters);
            camera.startPreview();
            v.setKeepScreenOn(true);
        } else {
            camera.stopPreview();
            v.setKeepScreenOn(false);
        }
    }
    
}