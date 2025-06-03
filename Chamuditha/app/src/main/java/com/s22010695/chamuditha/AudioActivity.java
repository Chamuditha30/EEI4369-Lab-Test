package com.s22010695.chamuditha;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AudioActivity extends AppCompatActivity implements SensorEventListener {

    //create objects
    private TextView audioStatus;
    private ImageView tempIcon;
    private SensorManager sensorManager;
    private Sensor sensor;
    private MediaPlayer mediaPlayer;

    //create variables
    private float temperature;
    private int tempThreshold = 95; //SID = s22010695

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_audio);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //accessing sensor manager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //get audio file, audio status by id
        mediaPlayer = MediaPlayer.create(this, R.raw.audio);
        audioStatus = findViewById(R.id.audioStatus);
        tempIcon = findViewById(R.id.tempIcon);
    }

    /*navigate back to google map activity using intent*/
    public void navToLoginActivity(View view) {
        Intent intent = new Intent(this, GoogleMapActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        temperature = sensorEvent.values[0];

        //change audio status and status icon according to temp change
        if (temperature > tempThreshold){
            mediaPlayer.start();
            audioStatus.setText(String.valueOf("Playing"));
            tempIcon.setImageResource(R.drawable.high_temp);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    //add sensor event listener in on resume
    @Override
    protected void onResume() {
        super.onResume();
        //use temperature sensor
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE), SensorManager.SENSOR_DELAY_NORMAL);
    }

    //remove sensor event listener in on resume

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}