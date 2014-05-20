package com.generalprocessingunit.connectivity;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.List;

public class AndroidSensorListener {
    private AndroidSensorData sensorData = new AndroidSensorData();

    // TODO: break this out into a generic Android utils project
    public AndroidSensorListener(Activity activityInstance) {
        SensorManager sensorManager = (SensorManager) activityInstance.getSystemService(Context.SENSOR_SERVICE);

        // Log the list of available sensors
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor s : sensorList) {
            Log.i("Sensor", s.getName());
        }

        final SensorEventListener eventListener = new SensorEventListener() {
            public void onAccuracyChanged(Sensor sensor, int accuracy) { }

            public void onSensorChanged(SensorEvent event) {
                boolean orientationChanged = false;

                // Handle the events for which we registered
                switch (event.sensor.getType()) {
                    case Sensor.TYPE_ACCELEROMETER:
                        System.arraycopy(event.values, 0, sensorData.mValuesAccel, 0, 3);
                        orientationChanged = true;
                        break;

                    case Sensor.TYPE_MAGNETIC_FIELD:
                        System.arraycopy(event.values, 0, sensorData.mValuesMagnet, 0, 3);
                        orientationChanged = true;
                        break;

                    case Sensor.TYPE_LIGHT:
                        sensorData.light = event.values[0];
                        Log.d("Light", "" + sensorData.light);
                        break;

                    case Sensor.TYPE_PROXIMITY:
                        sensorData.proximity = (int) event.values[0] == 0;
                        Log.d("Proximity", "" + sensorData.proximity);
                        break;
                }

                if (orientationChanged) {
                    SensorManager.getRotationMatrix(sensorData.mRotationMatrix, null, sensorData.mValuesAccel, sensorData.mValuesMagnet);
                    SensorManager.getOrientation(sensorData.mRotationMatrix, sensorData.mValuesOrientation);
                }

                onSensorDataChanged(sensorData);
            }
        };

        setListners(sensorManager, eventListener);
    }

    // Register the event listener and sensor type.
    private static void setListners(SensorManager sensorManager, SensorEventListener mEventListener) {
        sensorManager.registerListener(mEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(mEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(mEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(mEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_FASTEST);
    }

    /**
     * Override to get sensor data
     * @param sensorData
     */
    public void onSensorDataChanged(AndroidSensorData sensorData) { }
}
