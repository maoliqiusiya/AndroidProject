package hk.edu.cuhk.ie.iems5722.a2_1155164913.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import hk.edu.cuhk.ie.iems5722.a2_1155164913.Chatrooms;
import hk.edu.cuhk.ie.iems5722.a2_1155164913.R;
import hk.edu.cuhk.ie.iems5722.a2_1155164913.WiFiMainActivity;

public class Report extends AppCompatActivity {

    private ImageView iv_today;
    private ImageView iv_month;
    private ImageView iv_year;
    private RadioButton rbGroup;
    private RadioButton rbWiFi;
    private RadioButton rbReport;
    private RadioButton rbMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        rbGroup = findViewById(R.id.rbGroup);
        rbWiFi = findViewById(R.id.rbContact);
        rbReport = findViewById(R.id.rbSchedule);
        rbMe = findViewById(R.id.rbMe);

        rbGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Report.this, Chatrooms.class);
                startActivity(intent);
            }
        });
        rbWiFi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Report.this, WiFiMainActivity.class);
                startActivity(intent);
            }
        });
        rbReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Report.this, Report.class);
                startActivity(intent);
            }
        });
        rbMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Report.this, Me.class);
                startActivity(intent);
            }
        });


        iv_today = findViewById(R.id.it_report_day_iv);
        iv_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Report.this, ReportAdding.class);
                intent.putExtra("name", "Report for today");
                startActivity(intent);
            }
        });
        iv_month = findViewById(R.id.it_report_month_iv);
        iv_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Report.this, ReportAdding.class);
                intent.putExtra("name", "Report for month");
                startActivity(intent);
            }
        });
        iv_year = findViewById(R.id.it_report_year_iv);
        iv_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Report.this, ReportAdding.class);
                intent.putExtra("name", "Report for year");
                startActivity(intent);
            }
        });
    }
}