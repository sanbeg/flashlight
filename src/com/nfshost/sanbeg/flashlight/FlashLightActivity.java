package com.nfshost.sanbeg.flashlight;

import java.io.IOException;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

public class FlashLightActivity extends Activity {
    private Camera camera=null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

    }
    public void onToggleClicked(View v) {
    	if (camera==null) camera=Camera.open();

     	// Perform action on clicks
    	Camera.Parameters parameters = camera.getParameters();
        if (((ToggleButton) v).isChecked()) {
        	parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            Toast.makeText(this, "Toggle on", Toast.LENGTH_SHORT).show();
            camera.setParameters(parameters);
            camera.startPreview();

        } else {
        	parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
            Toast.makeText(this, "Toggle off", Toast.LENGTH_SHORT).show();
            camera.setParameters(parameters);
            camera.stopPreview();
            camera.release();
            camera=null;
        }
    }
    
}