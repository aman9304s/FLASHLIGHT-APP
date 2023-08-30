package com.example.flashlightapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    Switch aSwitch;
    TextView tv_result;
    CameraManager cameraManager;
    String cameraId, result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views using findViewById
        aSwitch = findViewById(R.id.myswitchid);
        tv_result = findViewById(R.id.mytextviewid);

        // Get the camera manager and check if the device has a flash
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        boolean isFlashAvailable = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!isFlashAvailable) {
            // If the device doesn't have a flash, update the TextView
            tv_result.setText("Flashlight is not available on this device.");
            aSwitch.setEnabled(false);
            return;
        }

        try {
            cameraId = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    turnFlashlightOn();
                } else {
                    turnFlashlightOff();
                }
            }
        });
    }

    private void turnFlashlightOn() {
        try {
            cameraManager.setTorchMode(cameraId, true);
            result = "Flashlight is ON";
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        tv_result.setText(result);
    }

    private void turnFlashlightOff() {
        try {
            cameraManager.setTorchMode(cameraId, false);
            result = "Flashlight is OFF";
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        tv_result.setText(result);
    }
}
