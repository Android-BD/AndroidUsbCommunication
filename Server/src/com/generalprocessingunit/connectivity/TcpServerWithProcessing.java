package com.generalprocessingunit.connectivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import com.generalprocessingunit.AndroidUsbTcpSocket.CommandMessage;
import com.generalprocessingunit.AndroidUsbTcpSocket.MessageFromPc;
import com.generalprocessingunit.AndroidUsbTcpSocket.MessageFromDroid;
import com.google.gson.reflect.TypeToken;
import processing.core.PApplet;

import java.lang.reflect.Type;

public class TcpServerWithProcessing extends PApplet {
    /* Server */
    TcpServer<MessageFromPc> server;
    MessageFromPc messageFromClient;
    boolean exit;

    /* Sensor Demo */
    ProcessingSensorDemo sensorDemo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Type msgReceivedType = new TypeToken<MessageFromPc>(){}.getType();
        // TODO: passing msgReceivedType seems redundent but I don't know how to get the Type parameter from the TcpServer instance
        server = new TcpServer<MessageFromPc>(msgReceivedType) {
            @Override
            public void msgReceived(MessageFromPc msg) {
                messageFromClient = msg;
            }

            @Override
            public void exit() {
                exit = true;
            }
        };
    }

    @Override
    public boolean surfaceKeyDown(int code, KeyEvent event) {
        Log.d("KeyDown", ""+code);
        switch (code) {
            case KeyEvent.KEYCODE_BACK:
                sendCommand(CommandMessage.BTN_BACK);
                break;
            case KeyEvent.KEYCODE_SEARCH:
                sendCommand(CommandMessage.BTN_SEARCH);
                break;
            case KeyEvent.KEYCODE_MENU:
                sendCommand(CommandMessage.BTN_MENU);
                break;
            case KeyEvent.KEYCODE_VOLUME_UP:
                sendCommand(CommandMessage.VOL_UP);
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                sendCommand(CommandMessage.VOL_DOWN);
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                sendCommand(CommandMessage.BTN_ENTER);
                break;
        }

        return true; // disable menu, back, search keys
    }

    private void sendCommand(CommandMessage cmd) {
        server.sendMsg(new MessageFromDroid(null, cmd));
    }

    @Override
    public boolean surfaceKeyUp(int code, KeyEvent event) {
        return true;  // disable volume keys
    }

    @Override
    public void setup()
    {
        orientation(PORTRAIT);
        super.setup();

        sensorDemo = new ProcessingSensorDemo(this);

        new AndroidSensorListener(this) {
            @Override
            public void onSensorDataChanged(AndroidSensorData sensorData) {
                sensorDemo.onSensorDataChanged(sensorData);
                server.sendMsg(new MessageFromDroid(sensorData.mValuesOrientation, null));
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
