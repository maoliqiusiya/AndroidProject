package hk.edu.cuhk.ie.iems5722.a2_1155164913;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import hk.edu.cuhk.ie.iems5722.a2_1155164913.project.Me;
import hk.edu.cuhk.ie.iems5722.a2_1155164913.project.Report;

public class Chatrooms extends AppCompatActivity {


    private ImageView imageView;
    private SlideMenu slideMenu;
    private RadioButton rbGroup;
    private RadioButton rbWiFi;
    private RadioButton rbReport;
    private RadioButton rbMe;
    private ListView listviewChatrooms;
    private List<ChatroomItem> chatroomData = null;
    private Context chatroomContext;
    private ChatroomItemAdapter mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupchat);

        imageView = findViewById(R.id.test);
        slideMenu = findViewById(R.id.slidemenu);

        listviewChatrooms = findViewById(R.id.lv_chatrooms);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                slideMenu.switchMenu();
//            }
//        });

        rbGroup = findViewById(R.id.rbGroup);
        rbWiFi = findViewById(R.id.rbContact);
        rbReport = findViewById(R.id.rbSchedule);
        rbMe = findViewById(R.id.rbMe);


        rbGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Chatrooms.this, Chatrooms.class);
                startActivity(intent);
            }
        });
        rbWiFi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Chatrooms.this, WiFiMainActivity.class);
                startActivity(intent);
            }
        });
        rbReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Chatrooms.this, Report.class);
                startActivity(intent);
            }
        });
        rbMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Chatrooms.this, Me.class);
                startActivity(intent);
            }
        });

        chatroomContext = this;
        chatroomData = new LinkedList<ChatroomItem>();
        mAdapter = new ChatroomItemAdapter(chatroomData, chatroomContext);
//      Toast.makeText(getApplicationContext(),"-----------------===>start!"+ mData.get(3).getDate(),Toast.LENGTH_LONG).show();
        listviewChatrooms.setAdapter(mAdapter);

        listviewChatrooms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(), "message" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Chatrooms.this,ChatActivity.class);
                intent.putExtra("id", ((ChatroomItem)listviewChatrooms.getItemAtPosition(position)).getId());
                intent.putExtra("name", ((ChatroomItem)listviewChatrooms.getItemAtPosition(position)).getName());
                startActivity(intent);
            }
        });

        new ChatroomsList().execute("http://18.217.125.61/api/a3/get_chatrooms");
//        new ChatroomsList().execute("http://34.125.85.22/api/a3/get_chatrooms");
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    class ChatroomsList extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }
        @Override
        protected String doInBackground(String... params) {

            String chatroomNames = "";
            try{
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(30000);
                conn.connect();
                if (conn.getResponseCode() != 200){
                    Toast.makeText(getApplicationContext(), "higher version than 4.0 will not support main thread connection", Toast.LENGTH_SHORT).show();
                    return null;
                }

                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null)
                    chatroomNames += line;

                br.close();
                conn.disconnect();

            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
            return chatroomNames;
        }


        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            if (result != null) {
                try {
                    JSONObject json = new JSONObject(result);
                    String status = json.getString("status");
                    if (!status.equals("OK")) {
                        Toast.makeText(getApplicationContext(), "status not ok", Toast.LENGTH_SHORT).show();
                    } else {
                        JSONArray roomArray = json.getJSONArray("data");
                        for (int count = 0; count < roomArray.length(); count++) {
                            ChatroomItem chatrooms = new ChatroomItem(roomArray.getJSONObject(count).getInt("id"), roomArray.getJSONObject(count).getString("name"));
                            chatroomData.add(chatrooms);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }
    }
}