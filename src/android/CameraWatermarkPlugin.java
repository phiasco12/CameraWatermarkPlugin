package com.cordova.camerawatermarkplugin;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Camera;
import android.view.SurfaceHolder;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CameraWatermarkPlugin extends CordovaPlugin {
    private Camera camera;
    private SurfaceHolder.Callback surfaceCallback;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        // Initialize your plugin here
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("addWatermark")) {
            JSONObject options = args.getJSONObject(0);
            String watermarkPath = options.getString("watermarkPath");

            addWatermark(watermarkPath, callbackContext);
            return true;
        }

        return false;
    }

    private void addWatermark(String watermarkPath, CallbackContext callbackContext) {
        // Retrieve the camera and apply the watermark
        camera = Camera.open();

        surfaceCallback = new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    camera.setPreviewDisplay(holder);
                    camera.startPreview();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                // Adjust the camera parameters if needed
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                camera.stopPreview();
                camera.release();
            }
        };

        webView.getPluginManager().postMessage("CameraWatermarkPlugin", "addWatermark", watermarkPath);
        callbackContext.success();
    }
}
