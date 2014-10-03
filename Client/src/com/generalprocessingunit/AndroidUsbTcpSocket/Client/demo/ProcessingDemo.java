package com.generalprocessingunit.AndroidUsbTcpSocket.Client.demo;

import com.generalprocessingunit.AndroidUsbTcpSocket.Client.PAppletDroidCommunicator;
import com.generalprocessingunit.AndroidUsbTcpSocket.CommandMessage;
import com.generalprocessingunit.AndroidUsbTcpSocket.MessageFromPc;
import processing.core.PApplet;

public class ProcessingDemo extends PAppletDroidCommunicator {
    float[] orientation = new float[3];

    public static void main(String[] args){
        PApplet.main(new String[] { /*"--present",*/ ProcessingDemo.class.getCanonicalName() });
    }

    @Override
    public void setup() {
        size(1000, 720, PApplet.OPENGL);
        super.setup();
    }

    int prevMillis = 0;
    @Override
    public void draw() {
        background(50);
        if(null != sensorData) {
            orientation = sensorData.mValuesOrientation;
        }

        translate(width/2, height/2);
        if(null != orientation){
            rotateY(-orientation[0]);
            rotateX(orientation[1]);
            rotateZ(orientation[2]);
        }

        directionalLight(255, 200, 200, .5f, 1f, .5f);
        directionalLight(200, 200, 255, 1f, 0f, -1f);
        fill(255);

        int vibeDur = 50;
        if((millis() - prevMillis) > 500) {
            vibrate(vibeDur);
            prevMillis = millis();
        }

        if((millis() - prevMillis) < vibeDur) {
            box(200);

        } else {
            box(190);
        }
    }

    public void commandReceived(CommandMessage cmd) {
        switch (cmd) {
            case VOL_UP:
                break;
            case VOL_DOWN:
                break;
            case SWIPE_UP:
                break;
            case SWIPE_DOWN:
                break;
            case SWIPE_LEFT:
                break;
            case SWIPE_RIGHT:
                break;
            case ZOOM_IN:
                break;
            case ZOOM_OUT:
                break;
            case BTN_BACK:
                break;
            case BTN_MENU:
                break;
            case BTN_SEARCH:
                break;
            case TAP:
                break;
        }
    }
}
