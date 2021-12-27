package hk.edu.cuhk.ie.iems5722.a2_1155164913.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import hk.edu.cuhk.ie.iems5722.a2_1155164913.Chatrooms;
import hk.edu.cuhk.ie.iems5722.a2_1155164913.R;
import hk.edu.cuhk.ie.iems5722.a2_1155164913.WiFiMainActivity;

public class ReportAdding extends AppCompatActivity {

    private RadioButton rbGroup;
    private RadioButton rbWiFi;
    private RadioButton rbReport;
    private RadioButton rbMe;
    private ImageView ivUpload;
    private String report_name;
    private TextView page_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_adding);
        rbGroup = findViewById(R.id.rbGroup);
        rbWiFi = findViewById(R.id.rbContact);
        rbReport = findViewById(R.id.rbSchedule);
        rbMe = findViewById(R.id.rbMe);
        ivUpload = findViewById(R.id.upload_iv);
        report_name = this.getIntent().getExtras().getString("name");
        page_title = findViewById(R.id.report_time);
        page_title.setText(this.getIntent().getExtras().getString("name"));
        rbGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReportAdding.this, Chatrooms.class);
                startActivity(intent);
            }
        });
        rbWiFi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReportAdding.this, WiFiMainActivity.class);
                startActivity(intent);
            }
        });
        rbReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportAdding.this, Report.class);
                startActivity(intent);
            }
        });
        rbMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportAdding.this, Me.class);
                startActivity(intent);
            }
        });
//        ivUpload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

    }
}