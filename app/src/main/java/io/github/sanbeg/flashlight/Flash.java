package io.github.sanbeg.flashlight;

import android.hardware.Camera;
import android.util.Log;

import java.io.Closeable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Flash implements Closeable {
    private static final String TAG = "FLASH";

    private static class CameraHolder {
        public final Camera camera;
        private final Camera.Parameters cameraParameters;

        public CameraHolder(Camera camera) {
            this.camera = camera;
            this.cameraParameters = camera.getParameters();
            cameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        }

        public void on() {
            camera.setParameters(cameraParameters);
            camera.startPreview();
        }
    }

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Future<CameraHolder> futureCamera;

    public static class CameraCallable implements Callable<CameraHolder> {
        private final Camera oldCamera;

        public CameraCallable(Camera camera) {
            oldCamera = camera;
        }
        @Override
        public CameraHolder call() throws Exception {
            if (oldCamera != null) {
                oldCamera.release();
            }
            return new CameraHolder(Camera.open());
        }
    }

    public synchronized void off() {
        if (futureCamera == null) {
            futureCamera = executorService.submit(new CameraCallable(null));
        } else if (futureCamera.isDone()) {
            Camera prev;
            try {
                prev = futureCamera.get().camera;
            } catch (Exception e) {
                prev = null;
            }
            futureCamera = executorService.submit(new CameraCallable(prev));
        }
    }

    public synchronized boolean on() {
        Log.i(TAG, "Got button press");
        if (futureCamera == null) {
            Log.w(TAG, "Camera is null");
            futureCamera = executorService.submit(new CameraCallable(null));
        }
        if (!futureCamera.isDone()) {
            Log.i(TAG, "Waiting for camera");
        }
        try {
            futureCamera.get().on();
            Log.i(TAG, "Light is on");
        } catch (Exception e) {
            Log.e(TAG, "Failed to activate flash", e);
            return false;
        }
        return true;
    }

    public synchronized void close() {
        if (futureCamera != null) {
            try {
                futureCamera.get().camera.release();
            } catch (Exception e) {
                Log.e(TAG, "Failed to release camera", e);
            }
        }
        futureCamera = null;
    }

}
