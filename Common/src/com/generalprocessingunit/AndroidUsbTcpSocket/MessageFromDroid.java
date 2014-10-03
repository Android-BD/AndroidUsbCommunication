package com.generalprocessingunit.AndroidUsbTcpSocket;

public class MessageFromDroid {
    public AndroidSensorData sensorData;
    public CommandMessage commandMessage;

    public MessageFromDroid(CommandMessage commandMessage) {
        this.commandMessage = commandMessage;
    }

    public MessageFromDroid(AndroidSensorData sensorData) {
        this.sensorData = sensorData;
    }
}
