package com.generalprocessingunit.AndroidUsbTcpSocket;

public interface UsbListener<T> {
    void msgReceived(T obj);

    void exit();
}
