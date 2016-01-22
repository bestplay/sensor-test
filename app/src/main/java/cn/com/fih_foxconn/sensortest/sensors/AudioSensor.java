package cn.com.fih_foxconn.sensortest.sensors;

import android.media.MediaRecorder;
import android.util.Log;

import cn.com.fih_foxconn.sensortest.LogUtil;

/**
 * Created by waynelu on 21/1/16.
 */
public class AudioSensor {
    String LOG_TAG = AudioSensor.class.getSimpleName();
    private MediaRecorder mRecorder;

    private long startTime;
    private long endTime;

    public void startRecording (String path){
        String filePath;
        if(path != null && !path.isEmpty()){
            filePath = path;
        }else {
            filePath = "/dev/null";
        }
        int MAX_LENGTH = 1000 * 60 * 10; // max record time
        if(mRecorder == null){
            mRecorder = new MediaRecorder();
        }
        try{
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            mRecorder.setOutputFile(filePath);
            mRecorder.setMaxDuration(MAX_LENGTH);

            mRecorder.prepare();

            mRecorder.start();

            // get startTime
            startTime = System.currentTimeMillis();
            LogUtil.i(LOG_TAG, "startTime" + startTime);
            updateMicStatus();


        }catch (Exception e){
            LogUtil.e(LOG_TAG, e.toString());
        }

    }

    // stop recording
    public long stopRecord() {
        if (mRecorder == null)
            return 0L;
        endTime = System.currentTimeMillis();
        Log.i(LOG_TAG, "endTime" + endTime);
        mRecorder.stop();
        mRecorder.reset();
        mRecorder.release();
        mRecorder = null;
        Log.i(LOG_TAG, "Duration Time" + (endTime - startTime));
        return endTime - startTime;
    }

    // show override
    public void onUpdateDecibel(double db){

    }


    private final android.os.Handler mHandler = new android.os.Handler();

    private Runnable mUpdateMicStatusTimer = new Runnable() {
        @Override
        public void run() {
            updateMicStatus();
        }
    };

    public void updateMicStatus(){
        int BASE = 1;
        int SPACE = 40;
        if(mRecorder != null){
            double ratio = (double) mRecorder.getMaxAmplitude() / BASE;
            double db = 0;
            if (ratio > 1){
                db = 20 * Math.log10(ratio);
            }

            onUpdateDecibel(db);
            mHandler.postDelayed(mUpdateMicStatusTimer,SPACE);
        }
    }

}
