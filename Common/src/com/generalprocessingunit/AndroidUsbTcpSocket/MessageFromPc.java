package com.generalprocessingunit.AndroidUsbTcpSocket;

public class MessageFromPc {
    public long[] vibratePattern = null;
    public Long vibrateDuration = null;

    public MessageFromPc(long[] vibratePattern) {
        this.vibratePattern = vibratePattern;
    }

    public MessageFromPc(long vibrateDuration) {
        this.vibrateDuration = vibrateDuration;
    }
}
