package hk.edu.cuhk.ie.iems5722.a2_1155164913.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import hk.edu.cuhk.ie.iems5722.a2_1155164913.Chatrooms;
import hk.edu.cuhk.ie.iems5722.a2_1155164913.R;
import hk.edu.cuhk.ie.iems5722.a2_1155164913.WiFiMainActivity;

public class Me extends AppCompatActivity {
    private RadioButton rbGroup;
    private RadioButton rbWiFi;
    private RadioButton rbReport;
    private RadioButton rbMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        rbGroup = findViewById(R.id.rbGroup);
        rbWiFi = findViewById(R.id.rbContact);
        rbReport = findViewById(R.id.rbSchedule);
        rbMe = findViewById(R.id.rbMe);
        rbGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Me.this, Chatrooms.class);
                startActivity(intent);
            }
        });
        rbWiFi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Me.this, WiFiMainActivity.class);
                startActivity(intent);
            }
        });
        rbReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Me.this, Report.class);
                startActivity(intent);
            }
        });
        rbMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Me.this, Me.class);
                startActivity(intent);
            }
        });
    }
}