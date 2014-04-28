package com.generalprocessingunit.connectivity;

import processing.core.PApplet;
import processing.core.PConstants;

public class ProcessingSensorDemo {
    private final PApplet p5;

    private AndroidSensorListener sensorListener;
    float[] orientation = new float[3];

    ProcessingSensorDemo(PApplet p5) {
        this.p5 = p5;
        final ProcessingSensorDemo self = this;

        sensorListener = new AndroidSensorListener(p5){
            @Override
            public void onSensorDataChanged(AndroidSensorData sensorData) {
                self.onSensorDataChanged(sensorData);
            }
        };
    }

    void onSensorDataChanged(AndroidSensorData sensorData) {
        orientation = sensorData.mValuesOrientation;
    }

    void draw() {
        // cycle background just to know it's running
        p5.colorMode(PConstants.HSB);
        p5.background((p5.millis() / 100) % 255, 255, 255 );

        p5.translate(p5.width / 2, p5.height / 2);
        if(null != orientation){
            p5.rotateY(-orientation[0]);
            p5.rotateX(orientation[1]);
            p5.rotateZ(orientation[2]);
        }

        p5.directionalLight(255, 200, 200, .5f, 1f, .5f);
        p5.directionalLight(200, 200, 255, 1f, 0f, -1f);
        p5.fill(255);

        p5.box(200);
    }
}
