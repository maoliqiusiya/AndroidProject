package hk.edu.cuhk.ie.iems5722.a2_1155164913.project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import hk.edu.cuhk.ie.iems5722.a2_1155164913.Chatrooms;
import hk.edu.cuhk.ie.iems5722.a2_1155164913.R;
import hk.edu.cuhk.ie.iems5722.a2_1155164913.WiFiMainActivity;

public class MainInterface extends AppCompatActivity {

    private RadioButton rbGroup;
    private RadioButton rbContact;
    private RadioButton rbReport;
    private RadioButton rbMe;
    private List<View> viewList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_interface);


        rbGroup = findViewById(R.id.rbGroup);
        rbContact = findViewById(R.id.rbContact);
        rbReport = findViewById(R.id.rbSchedule);
        rbMe = findViewById(R.id.rbMe);
        viewList = new ArrayList<>();

        rbGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainInterface.this, Chatrooms.class);
                startActivity(intent);
            }
        });
        rbContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainInterface.this, WiFiMainActivity.class);
                startActivity(intent);
            }
        });
        rbReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainInterface.this, Report.class);
                startActivity(intent);
            }
        });
        rbMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainInterface.this, Me.class);
                startActivity(intent);
            }
        });
// server client 1 button
        View view1= new View(this);
        view1.setBackgroundColor(Color.GRAY);
        View view2= new View(this);
        view2.setBackgroundColor(Color.CYAN);
        View view3= new View(this);
        view3.setBackgroundColor(Color.BLACK);
        View view4= new View(this);
        view4.setBackgroundColor(Color.TRANSPARENT);

        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);

        ViewPager viewPager = findViewById(R.id.main_vp);
        viewPager.setAdapter(new ViewPageAdapter(viewList));






    }
}