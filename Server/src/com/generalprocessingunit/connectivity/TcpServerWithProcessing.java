package com.generalprocessingunit.connectivity;

import android.os.Bundle;
import android.view.KeyEvent;
import com.google.gson.reflect.TypeToken;
import processing.core.PApplet;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TcpServerWithProcessing extends PApplet {
    /* Server */
    TcpServer<List<Float>> server;
    List<Float> valsFromClient;
    boolean exit;

    /* Sensor Demo */
    ProcessingSensorDemo sensorDemo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Type msgReceivedType = new TypeToken<ArrayList<Float>>(){}.getType();
        // TODO: passing msgReceivedType seems redundent but I don't know how to get the Type parameter from the TcpServer instance
        server = new TcpServer<List<Float>>(msgReceivedType) {
            @Override
            public void msgReceived(List<Float> msg) {
                valsFromClient = msg;
            }

            @Override
            public void exit() {
                exit = true;
            }
        };
    }

    @Override
    public boolean surfaceKeyDown(int code, KeyEvent event) {
        return true; // disable menu, back, search keys
    }


    @Override
    public void setup()
    {
        orientation(PORTRAIT);
        super.setup();

        sensorDemo = new ProcessingSensorDemo(this) {
            @Override
            public void onSensorDataChanged(AndroidSensorData sensorData) {
                super.onSensorDataChanged(sensorData);
                server.sendMsg(Arrays.asList(sensorData.mValuesOrientation[0], sensorData.mValuesOrientation[1], sensorData.mValuesOrientation[2]));
            }
        };
    }

    @Override
    public void draw() {
        sensorDemo.draw();

        if(exit) {
            exit();
        }
    }

    @Override
    public String sketchRenderer() {
        return PApplet.P3D;
    }

}
