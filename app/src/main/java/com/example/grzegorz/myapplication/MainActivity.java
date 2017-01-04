package com.example.grzegorz.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;

import com.example.grzegorz.myapplication.model.Xyz;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.internal.Context;
import io.realm.exceptions.RealmMigrationNeededException;




public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView Xtext, Ytext, Ztext;
    private Sensor mySensor;
    private SensorManager SM;
    private Button SavePosition;
    private Realm realm;
    private TextView log;
    private String id = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SavePosition = (Button) findViewById(R.id.save);
        log = (TextView) findViewById(R.id.textLog);

        realm = Realm.getDefaultInstance();

        //Zapisywanie danych po naciśnięciu

        SavePosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                save_into_database(//Integer.parseInt(id.toString().trim()),
                        Float.parseFloat(Xtext.getText().toString().trim()),
                        Float.parseFloat(Ytext.getText().toString().trim()),
                        Float.parseFloat(Ztext.getText().toString().trim()));
                refresh_views();

            }
        });

        // Creating sensor manager
        SM = (SensorManager)getSystemService(SENSOR_SERVICE);

        //Accelerometer
        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //sensor Listener
        SM.registerListener(this, mySensor, SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM);

        //assigning textviews

        Xtext = (TextView)findViewById(R.id.Xview);
        Ytext = (TextView)findViewById(R.id.Yview);
        Ztext = (TextView)findViewById(R.id.Zview);



    }

    private void refresh_views() {
        //reading from DB, showing log
        RealmResults<Xyz> xyzRealmResult = realm.where(Xyz.class).findAll();
        String output = "";
        for (Xyz xyz: xyzRealmResult){
            output += xyz.toString();

        }
        log.setText(output);

    }


    private void save_into_database(final float x, final float y, final float z) {

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Xyz xyz = bgRealm.createObject(Xyz.class);

               // xyz.setID(id);
                xyz.setX(x);
                xyz.setY(y);
                xyz.setZ(z);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
        Realm.deleteRealm(realm.getConfiguration());
        //deleteDatabase(String.valueOf(realm);
        realm.deleteAll();


    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        Xtext.setText(""+ event.values[0]);
        Ytext.setText(""+ event.values[1]);
        Ztext.setText(""+ event.values[2]);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {




}



}
