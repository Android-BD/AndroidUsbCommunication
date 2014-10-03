package com.generalprocessingunit.AndroidUsbTcpSocket;

public class AndroidSensorData {
    public float[] mValuesMagnet      = new float[3];
    public float[] mValuesAccel       = new float[3];
    public float[] mValuesOrientation = new float[3];
    public float[] mRotationMatrix    = new float[9];
    public float light;
    public boolean proximity;
}