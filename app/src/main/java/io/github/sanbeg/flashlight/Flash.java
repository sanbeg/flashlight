package io.github.sanbeg.flashlight;

import android.hardware.Camera;

public class Flash {
    private Camera camera = null;
    private Camera.Parameters cameraParameters;

    private String previousFlashMode = null;

    public synchronized void open() {
        camera = Camera.open();
        if (camera != null) {
            cameraParameters = camera.getParameters();
            previousFlashMode = cameraParameters.getFlashMode();
        }
        if (previousFlashMode == null) {
            // could be null if no flash, i.e. emulator
            previousFlashMode = Camera.Parameters.FLASH_MODE_OFF;
        }
    }

    public synchronized void close() {
        if (camera != null) {
            cameraParameters.setFlashMode(previousFlashMode);
            camera.setParameters(cameraParameters);
            camera.release();
            camera = null;
        }
    }

    public synchronized void on() {
        if (camera != null) {
            cameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(cameraParameters);
            camera.startPreview();
        }
    }

    public synchronized void off() {
        if (camera != null) {
            camera.stopPreview();
            cameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(cameraParameters);
        }
    }

}
