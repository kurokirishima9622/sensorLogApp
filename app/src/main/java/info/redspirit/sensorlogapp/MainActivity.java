package info.redspirit.sensorlogapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener,LocationListener {

    TextView xData;
    TextView yData;
    TextView zData;
    TextView laData;
    TextView loData;

    SensorManager sm;
    LocationManager lm;
    Criteria cr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xData = (TextView)findViewById(R.id.xData);
        yData = (TextView)findViewById(R.id.yData);
        zData = (TextView)findViewById(R.id.zData);
        laData = (TextView)findViewById(R.id.laData);
        loData = (TextView)findViewById(R.id.loData);

        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        lm = (LocationManager)getSystemService(LOCATION_SERVICE);
        cr = new Criteria();

        xData.setText("");
        yData.setText("");
        zData.setText("");
        laData.setText("");
        loData.setText("");

        //GPS
        boolean gpsFlg = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Toast.makeText(this,gpsFlg?"GPS:OK":"GPS:NG",Toast.LENGTH_SHORT).show();

        cr.setAccuracy(Criteria.ACCURACY_COARSE);
        cr.setPowerRequirement(Criteria.POWER_LOW);

        //ロケーションプロバイダの取得
        String provider = lm.getBestProvider(cr,true);
        // LocationListenerを登録
        //lm.requestLocationUpdates(provider, 0, 0, this);
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
    public void onLocationChanged(Location location){
        laData.setText(String.valueOf(location.getLatitude()));
        loData.setText(String.valueOf(location.getLongitude()));
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

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

}
