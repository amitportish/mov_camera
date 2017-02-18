package com.mov.android.camera2video;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class CameraControl extends Activity {

    private SeekBar dur_seekBar;
    private TextView dur_textView;

    private ToggleButton lands_port_toggle;
    private ToggleButton front_back_toggle;

    private Button recButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_control);

        dur_seekBar = (SeekBar) findViewById(R.id.duration_bar);
        dur_textView = (TextView) findViewById(R.id.duration_label);


        // Initialize the textview with '0'.
        dur_textView.setText("Record Duration: " + (dur_seekBar.getProgress() + 1) +"sec");

        dur_seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                //Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                dur_textView.setText("Record Duration: " + (dur_seekBar.getProgress() + 1) + "sec");
                //Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
            }
        });

        lands_port_toggle = (ToggleButton) findViewById(R.id.toggle_orientation);
        front_back_toggle = (ToggleButton) findViewById(R.id.toggle_frontback);

        recButton = (Button) findViewById(R.id.record_button);
        recButton.setOnClickListener(
            new View.OnClickListener() {

                @Override
                //On click function
                public void onClick(View view) {
                    boolean isLandscape   = lands_port_toggle. isChecked();
                    boolean isFrontCamera = !(front_back_toggle. isChecked());
                    int duration = (dur_seekBar.getProgress() + 1);
                    //Create the intent to start another activity
                    Intent intent = new Intent(view.getContext(), CameraActivity.class);
                    intent.putExtra("isLandscape",isLandscape);
                    intent.putExtra("isFrontCamera",isFrontCamera);
                    intent.putExtra("duration",duration*1000);
                    startActivity(intent);
                }
            });

    }

    @Override
    protected void onResume() {
        // Initialize the textview with '0'.
        dur_textView.setText("Record Duration: " + (dur_seekBar.getProgress() + 1) +"sec");
        super.onResume();

    }
}
