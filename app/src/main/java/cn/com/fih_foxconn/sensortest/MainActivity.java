package cn.com.fih_foxconn.sensortest;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;

import cn.com.fih_foxconn.sensortest.sensors.AudioSensor;
import cn.com.fih_foxconn.sensortest.sensors.Compass;
import cn.com.fih_foxconn.sensortest.sensors.LocationInfo;
import cn.com.fih_foxconn.sensortest.sensors.SensorsList;


public class MainActivity extends AppCompatActivity {
    public Button startBtn;
    public TextView content;
    public AudioSensor audioSensor;

    String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startBtn = (Button) findViewById(R.id.startBtn);
        content = (TextView) findViewById(R.id.contentText);

        audioSensor = new AudioSensor(){
            @Override
            public void onUpdateDecibel(double db){
                String dbSize = "";
                int i = (int)db;
                content.setTextColor( 0xff003333 + i * 255 / 100 * 0x10000 );
                for(;i>0;i = i - 2){
                    dbSize += ".";
                }

//                LogUtil.d(LOG_TAG, "Decibel: " + dbSize);
                content.setText("Decibel: " + db + "\n" + dbSize);
            }
        };

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startBtn.getText().equals(getResources().getString(R.string.start_audio))) {
                    audioSensor.startRecording(null);
                    startBtn.setText(R.string.stop_audio);
                } else if (startBtn.getText().equals(getResources().getString(R.string.stop_audio))) {
                    audioSensor.stopRecord();
                    startBtn.setText(R.string.start_audio);
                }
            }
        });

        // start compass
        Compass compass = new Compass(this){
            @Override
            public void onCompassChange(float[] degree,float[] mGravity){
                TextView compassText = (TextView)findViewById(R.id.compassText);
                compassText.setText(Arrays.toString(degree) + "\n" + Arrays.toString(mGravity));


            }
        };

        // get Location
        final TextView locationText = (TextView) findViewById(R.id.locationText);
        LocationInfo locationInfo = new LocationInfo(this){
            @Override
            public void showLocation(Location location){
                String currentPosition = "latitude is " + location.getLatitude() + "\n" + "longitude is " + location.getLongitude();
                locationText.setText(currentPosition);
            }
        };


    }


}
