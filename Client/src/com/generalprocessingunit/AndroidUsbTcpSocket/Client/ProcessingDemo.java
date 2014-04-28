package com.generalprocessingunit.AndroidUsbTcpSocket.Client;

import com.google.gson.reflect.TypeToken;
import processing.core.PApplet;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProcessingDemo extends PApplet {

    Client<List<Float>> client;
    List<Float> orientation;

    public static void main(String[] args){
        PApplet.main(new String[] { "--present", ProcessingDemo.class.getCanonicalName() });
    }

    @Override
    public void setup() {
        size(displayWidth, displayHeight, PApplet.OPENGL);

        Type msgReceivedType = new TypeToken<ArrayList<Float>>(){}.getType();
        client = new Client<List<Float>>(msgReceivedType) {
            @Override
            public void msgReceived(List<Float> msg) {
                orientation = msg;
            }

            @Override
            public void exit() { /*no-op*/}
        };
    }

    @Override
    public void draw() {
        background(50);

        translate(width/2, height/2);
        if(null != orientation){
            rotateY(-orientation.get(0));
            rotateX(orientation.get(1));
            rotateZ(orientation.get(2));
        }

        directionalLight(255, 200, 200, .5f, 1f, .5f);
        directionalLight(200, 200, 255, 1f, 0f, -1f);
        fill(255);

        box(200);

        if(null != orientation){
            client.sendMsg(Arrays.asList(orientation.get(1) * 255f, orientation.get(2) * 255f));
        }

//        if(millis() > 10000) {
//            client.sendMsg(Settings.EXIT_SERVER);
//        }
    }
}
