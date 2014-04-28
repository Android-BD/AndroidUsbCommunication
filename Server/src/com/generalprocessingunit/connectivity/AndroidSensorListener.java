package com.generalprocessingunit.connectivity;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class AndroidSensorListener {
    Activity activityInstance;

    AndroidSensorData sensorData = new AndroidSensorData();

    SensorManager sensorManager;

    // TODO: break this out into a generic Android utils project
    AndroidSensorListener(Activity activityInstance) {
        this.activityInstance = activityInstance;

        sensorManager = (SensorManager) activityInstance.getSystemService(Context.SENSOR_SERVICE);

        final SensorEventListener mEventListener = new SensorEventListener() {
            public void onAccuracyChanged(Sensor sensor, int accuracy) { }

            public void onSensorChanged(SensorEvent event) {
                // Handle the events for which we registered
                switch (event.sensor.getType()) {
                    case Sensor.TYPE_ACCELEROMETER:
                        System.arraycopy(event.values, 0, sensorData.mValuesAccel, 0, 3);
                        break;

                    case Sensor.TYPE_MAGNETIC_FIELD:
                        System.arraycopy(event.values, 0, sensorData.mValuesMagnet, 0, 3);
                        break;
                }

                SensorManager.getRotationMatrix(sensorData.mRotationMatrix, null, sensorData.mValuesAccel, sensorData.mValuesMagnet);
                SensorManager.getOrientation(sensorData.mRotationMatrix, sensorData.mValuesOrientation);
                onSensorDataChanged(sensorData);
            };
        };

        setListners(sensorManager, mEventListener);
    }

    // Register the event listener and sensor type.
    private void setListners(SensorManager sensorManager, SensorEventListener mEventListener)
    {
        sensorManager.registerListener(mEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(mEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void onSensorDataChanged(AndroidSensorData sensorData) {
    }
}
