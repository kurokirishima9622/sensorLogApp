package info.redspirit.sensorlogapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView xData;
    TextView yData;
    TextView zData;

    SensorManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xData = (TextView)findViewById(R.id.xData);
        yData = (TextView)findViewById(R.id.yData);
        zData = (TextView)findViewById(R.id.zData);


        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        xData.setText("");
        yData.setText("");
        zData.setText("");

    }

    @Override
    public void onSensorChanged(SensorEvent event){
        float x = 0.0f;
        float y = 0.0f;
        float z = 0.0f;

        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];

            xData.setText(String.valueOf(x));
            yData.setText(String.valueOf(y));
            zData.setText(String.valueOf(z));

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor,int accuracy){

    }

    @Override
    protected void onPause(){
        if(sm != null){
            sm.unregisterListener(this);
        }
        super.onPause();
    }

    @Override
    protected void onResume(){
        sm.registerListener(this,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_GAME);
        super.onResume();
    }

}
