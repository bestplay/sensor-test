package cn.com.fih_foxconn.sensortest.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import java.util.List;

import cn.com.fih_foxconn.sensortest.LogUtil;

/**
 * Created by waynelu on 21/1/16.
 */
public class SensorsList {
    private Context mContext;

    public SensorsList(Context context){
        mContext = context;
    }

    public List<Sensor> getAllSensors() {
        SensorManager sm;
        List<Sensor> allSensors;
        sm = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        allSensors = sm.getSensorList(Sensor.TYPE_ALL);
        StringBuffer str;
        Sensor s;

        str = new StringBuffer();
        str.append("该手机有" + allSensors.size() + "个传感器,分别是:\n");
        for (int i = 0; i < allSensors.size(); i++) {
            s = allSensors.get(i);
            str.append("->>设备名称:" + s.getName() + "\n");
            str.append("设备版本:" + s.getVersion() + "\n");
            str.append("通用类型号:" + s.getType() + "\n");
            str.append("设备商名称:" + s.getVendor() + "\n");
            str.append("传感器功耗:" + s.getPower() + "\n");
            str.append("传感器分辨率:" + s.getResolution() + "\n");
            str.append("传感器最大量程:" + s.getMaximumRange() + "\n");
            switch (s.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    str.append(i + "加速度传感器");
                    break;
                case Sensor.TYPE_GYROSCOPE:
                    str.append(i + "陀螺仪传感器");
                    break;
                case Sensor.TYPE_LIGHT:
                    str.append(i + "环境光线传感器");
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    str.append(i + "电磁场传感器");
                    break;
                case Sensor.TYPE_ORIENTATION:
                    str.append(i + "方向传感器");
                    break;
                case Sensor.TYPE_PRESSURE:
                    str.append(i + "压力传感器");
                    break;
                case Sensor.TYPE_PROXIMITY:
                    str.append(i + "距离传感器");
                    break;
                case Sensor.TYPE_TEMPERATURE:
                    str.append(i + "温度传感器");
                    break;
                default:
                    str.append(i + "未知传感器");
                    break;
            }
        }
        LogUtil.d("All sensors result:", str.toString());
        return allSensors;
    }
}
