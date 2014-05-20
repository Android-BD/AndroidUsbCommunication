package com.generalprocessingunit.AndroidUsbTcpSocket.Client;

import com.generalprocessingunit.AndroidUsbTcpSocket.CommandMessage;
import com.generalprocessingunit.AndroidUsbTcpSocket.MessageFromDroid;
import com.generalprocessingunit.AndroidUsbTcpSocket.MessageFromPc;
import com.generalprocessingunit.AndroidUsbTcpSocket.Settings;
import com.google.gson.reflect.TypeToken;
import processing.core.PApplet;

import java.lang.reflect.Type;
import java.util.Arrays;

public class ProcessingDemo extends PApplet {

    Client<MessageFromDroid> client;
    float[] orientation;

    public static void main(String[] args){
        PApplet.main(new String[] { "--present", ProcessingDemo.class.getCanonicalName() });
    }

    @Override
    public void setup() {
        size(displayWidth, displayHeight, PApplet.OPENGL);

        final ProcessingDemo self = this;

        Type msgReceivedType = new TypeToken<MessageFromDroid>(){}.getType();
        client = new Client<MessageFromDroid>(msgReceivedType) {
            @Override
            public void msgReceived(MessageFromDroid msg) {
                if(null != msg.ewiOrientation) {
                    orientation = msg.ewiOrientation;
                }

                if(null != msg.commandMessage) {
                    self.commandReceived(msg.commandMessage);
                }
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
            rotateY(-orientation[0]);
            rotateX(orientation[1]);
            rotateZ(orientation[2]);
        }

        directionalLight(255, 200, 200, .5f, 1f, .5f);
        directionalLight(200, 200, 255, 1f, 0f, -1f);
        fill(255);

        box(200);

        if(null != orientation){
            client.sendMsg(new MessageFromPc());
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
