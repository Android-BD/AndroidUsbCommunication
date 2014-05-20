package com.generalprocessingunit.AndroidUsbTcpSocket;

public class MessageFromDroid {
    public float [] ewiOrientation;
    public CommandMessage commandMessage;

    public MessageFromDroid(float[] ewiOrientation, CommandMessage commandMessage) {
        this.ewiOrientation = ewiOrientation;
        this.commandMessage = commandMessage;
    }
}
