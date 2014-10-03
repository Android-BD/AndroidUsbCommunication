package com.generalprocessingunit.connectivity.demo;

import com.generalprocessingunit.AndroidUsbTcpSocket.AndroidSensorData;
import com.generalprocessingunit.connectivity.PAppletDroidController;
import processing.core.PConstants;

public class DroidControlSurface extends PAppletDroidController {
    private float[] orientation = new float[3];

    @Override
    public void onSensorDataChanged(AndroidSensorData sensorData) {
        orientation = sensorData.mValuesOrientation;
    }

    @Override
    public void draw() {
        super.draw();

        // cycle background just to know it's running
        colorMode(PConstants.HSB);
        background((millis() / 100) % 255, 255, 255 );

//        translate(width / 2, height / 2);
//        if(null != orientation){
//            rotateY(-orientation[0]);
//            rotateX(orientation[1]);
//            rotateZ(orientation[2]);
//        }
//
//        directionalLight(255, 200, 200, .5f, 1f, .5f);
//        directionalLight(200, 200, 255, 1f, 0f, -1f);
//        fill(255);
//
//        box(200);
    }
}
