package com.generalprocessingunit.AndroidUsbTcpSocket.Client;

import com.generalprocessingunit.AndroidUsbTcpSocket.AndroidSensorData;
import com.generalprocessingunit.AndroidUsbTcpSocket.CommandMessage;
import com.generalprocessingunit.AndroidUsbTcpSocket.MessageFromDroid;
import com.generalprocessingunit.AndroidUsbTcpSocket.MessageFromPc;
import com.google.gson.reflect.TypeToken;
import processing.core.PApplet;

import java.lang.reflect.Type;

// TODO: turn this into Scala trait
public abstract class PAppletDroidCommunicator extends PApplet {
    public AndroidSensorData sensorData;

    private Client<MessageFromDroid> client;

    @Override
    public void setup() {
        final PAppletDroidCommunicator thisDroidCommunicator = this;
        Type msgReceivedType = new TypeToken<MessageFromDroid>(){}.getType();

        client = new Client<MessageFromDroid>(msgReceivedType) {
            @Override
            public void msgReceived(MessageFromDroid msg) {
                if(null != msg.sensorData) {
                    sensorData = msg.sensorData;
                }

                if(null != msg.commandMessage) {
                    thisDroidCommunicator.commandReceived(msg.commandMessage);
                }
            }

            @Override
            public void exit() { /*no-op*/}
        };
    }

    private void sendMsg(MessageFromPc msg) {
        client.sendMsg(msg);
    }

    public void vibrate(long millis) {
        sendMsg(new MessageFromPc(millis));
    }

    public abstract void commandReceived(CommandMessage cmd);

}
