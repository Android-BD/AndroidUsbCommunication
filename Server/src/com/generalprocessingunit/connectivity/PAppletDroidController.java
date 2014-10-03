package com.generalprocessingunit.connectivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import com.generalprocessingunit.AndroidUsbTcpSocket.AndroidSensorData;
import com.generalprocessingunit.AndroidUsbTcpSocket.CommandMessage;
import com.generalprocessingunit.AndroidUsbTcpSocket.MessageFromPc;
import com.generalprocessingunit.AndroidUsbTcpSocket.MessageFromDroid;
import com.google.gson.reflect.TypeToken;
import processing.core.PApplet;

import java.lang.reflect.Type;

public abstract class PAppletDroidController extends PApplet {
    /* Server */
    TcpServer<MessageFromPc> server;
    boolean exit;

    Vibrator vibrator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final PAppletDroidController thisDroidController = this;

        // TODO: passing msgReceivedType seems redundant but I don't know how to get the Type parameter from the TcpServer instance
        Type msgReceivedType = new TypeToken<MessageFromPc>(){}.getType();

        server = new TcpServer<MessageFromPc>(msgReceivedType) {
            @Override
            public void msgReceived(MessageFromPc msg) {
                 thisDroidController.onMessageReceived(msg);
            }

            @Override
            public void exit() {
                exit = true;
            }
        };
    }

    @Override
    public void setup()
    {
        orientation(PORTRAIT);

        vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

        final PAppletDroidController thisDroidController = this;

        new AndroidSensorListener(this) {
            @Override
            public void onSensorDataChanged(AndroidSensorData sensorData) {
                thisDroidController.onSensorDataChanged(sensorData);
                server.sendMsg(new MessageFromDroid(sensorData));
            }
        };
    }

    public abstract void onSensorDataChanged(AndroidSensorData sensorData);

    private void onMessageReceived(MessageFromPc msg) {
        if(null != msg.vibrateDuration) {
            vibrator.vibrate(msg.vibrateDuration);
        }

        if(null != msg.vibratePattern) {
            vibrator.vibrate(msg.vibratePattern, -1);
        }
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
        server.sendMsg(new MessageFromDroid(cmd));
    }

    @Override
    public boolean surfaceKeyUp(int code, KeyEvent event) {
        return true;  // disable volume keys
    }

    @Override
    public void draw() {
        if(exit) {
            exit();
        }
    }

    @Override
    public String sketchRenderer() {
        return PApplet.P3D;
    }

}
