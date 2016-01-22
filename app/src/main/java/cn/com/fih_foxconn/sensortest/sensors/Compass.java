package cn.com.fih_foxconn.sensortest.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.conn.ConnectTimeoutException;

import cn.com.fih_foxconn.sensortest.LogUtil;

/**
 * Created by waynelu on 21/1/16.
 */
public class Compass {
    public Context mContext;

    String LOG_TAG = Compass.class.getSimpleName();
//    private ImageView image;
    // init degree
    private float currentDegree = 0f;

    private SensorManager mSensorManager;
    float[] mGravity;
    float[] mGeomagnetic;
    float Rotation[] = new float[9];
    float[] degree = new float[3];

    public Compass(Context context){
        mContext = context;


        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        // register sensor

        Sensor accelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor magnetometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        mSensorManager.registerListener(listener, accelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(listener, magnetometer,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    // stop listening
    public void stop(){
        mSensorManager.unregisterListener(listener);
    }

    SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                mGravity = event.values;
            }
            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                mGeomagnetic = event.values;
            }
            if (mGravity != null && mGeomagnetic != null) {

                SensorManager.getRotationMatrix(Rotation, null, mGravity,
                        mGeomagnetic);
                SensorManager.getOrientation(Rotation, degree);

                degree[0] = (float) Math.toDegrees(degree[0]);

                onCompassChange(degree,mGravity);

                RotateAnimation ra = new RotateAnimation(currentDegree, -degree[0],
                        Animation.RELATIVE_TO_SELF, 0.5f, // x
                        Animation.RELATIVE_TO_SELF, 0.5f); // y

                ra.setDuration(210);

                ra.setFillAfter(true);

//            image.startAnimation(ra);
                currentDegree = -degree[0];
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    // should override
    public void onCompassChange(float[] degree, float[] mGravity){
        LogUtil.d(LOG_TAG,"Heading: " + (int) degree[0] + " degrees");
        degree.toString();
    }
}
