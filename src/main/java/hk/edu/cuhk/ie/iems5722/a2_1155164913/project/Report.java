package hk.edu.cuhk.ie.iems5722.a2_1155164913.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import hk.edu.cuhk.ie.iems5722.a2_1155164913.R;

public class Report extends AppCompatActivity {

    private ImageView iv_today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        iv_today = findViewById(R.id.it_report_day_iv);
        iv_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Report.this, ReportAdding.class);
                startActivity(intent);
            }
        });
    }
}