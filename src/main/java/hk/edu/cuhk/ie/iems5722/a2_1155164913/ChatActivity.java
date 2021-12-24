package hk.edu.cuhk.ie.iems5722.a2_1155164913;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class ChatActivity extends AppCompatActivity {
    private List<ChatItem> mData = null;
    private Context mContext;
    private ChatItemAdapter mAdapter = null;
    private ListView listView;
    private Button backBt;
    private Button sendBt;
    private Button refreshBt;
    private EditText etMsg;
    private int currentRoomId;
    private String currentRoomName;
    private int nextPage;
    private int totalPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.listview_chats);
//        if (getSupportActionBar()!=null)
//            getSupportActionBar().hide();
        mContext = this;
        listView = findViewById(R.id.listview);
        mData = new LinkedList<ChatItem>();
        mAdapter = new ChatItemAdapter(mData, mContext);
        listView.setAdapter(mAdapter);

        etMsg = findViewById(R.id.et_sendmessage);
//        backBt = findViewById(R.id.btn_back);
        sendBt = findViewById(R.id.bt_send);
//        refreshBt = findViewById(R.id.btn_refresh);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        currentRoomId = this.getIntent().getExtras().getInt("id");
        currentRoomName = this.getIntent().getExtras().getString("name");
//设置动态获取textview

//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setTitle(currentRoomName);

        String get_url = "http://18.217.125.61/api/a3/get_messages?chatroom_id=";
        new ChatsList().execute(get_url + currentRoomId + "&page=1");
        mAdapter.notifyDataSetChanged();
//        refreshBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mData.clear();
//                new ChatsList().execute(get_url + currentRoomId + "&page=1");
//            }
//        });


//        backBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ChatActivity.this, Chatrooms.class);
//                startActivity(intent);
//            }
//        });

        sendBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMsg(view);
            }
        });




        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            private boolean isTop = false;

//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                if(listView.getChildAt(firstVisibleItem)!=null) {
//                    isTop = false;
//                    if (listView.getChildAt(firstVisibleItem).getTop() == 0 && firstVisibleItem == 0)
//                        isTop = true;
//                }
//            }
//
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                if (isTop && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
//                    if (nextPage <= totalPages) {
//                        new ChatsList().execute(get_url + currentRoomId + "&page=" + nextPage);
//                    }
//                }
//            }
//        });


        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if(listView.getChildAt(firstVisibleItem)!=null) {
                isTop = false;

                int top = listView.getChildAt(firstVisibleItem).getTop();
                //    Log.d("Chatactivity", "topmessage"+ mChatMessages.get(0).getMessage());
                if (firstVisibleItem == 0 && top == 0) {
                    isTop = true;
                }
            }

        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (isTop && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                if (nextPage <= totalPages) {

//                    List<ChatItem> mDataNew = null;
//                    mDataNew = new LinkedList<ChatItem>();
//                    for (ChatItem mDatum : mData)
//                        mDataNew.add(mDatum);
//
//
//                    mData = new LinkedList<ChatItem>();
//                    new ChatsList().execute(get_url + currentRoomId + "&page=" + nextPage);
//                    for(int j = 0; j < mDataNew.size();j++)
//                        mData.add(mDataNew.get(j));


                    new ChatsList().execute(get_url + currentRoomId + "&page=" + nextPage);
//                    mAdapter.notifyDataSetChanged();
//                    String chatItemsApi = getString(R.string.chatitemlist_api);
//                    chatItemsApi = chatItemsApi + '?' + getString(R.string.chatitemlist_param1) + '=' + mChatRoomId
//                            + '&' + getString(R.string.chatitemlist_param2) + '=' + nextPage;
//                    new getChatItemsTask().execute(chatItemsApi);
                    //mListview.setSelection(5);
                    //Log.d("ChatActivity","set selection to 5");
                }
            }
        }
    });



    }



    public void sendMsg(View view){
        String message = etMsg.getText().toString();
        if (message.equals("") || message.trim().equals("") || message.length() > 200){
            Toast.makeText(getApplicationContext(),"do not input blank or oversized data or no-input ",Toast.LENGTH_SHORT).show();
            return;
        }
        etMsg.setText("");
        String send_url = "http://18.217.125.61/api/a3/send_message";
        new postChatItem().execute(send_url, "" + currentRoomId, "666", "abc", message);

        mData.add(new ChatItem(666, "abc", DateFormat.format("yyyy-MM-dd hh:mm:ss", System.currentTimeMillis()).toString(),message, true, R.drawable.background3));
        mAdapter.notifyDataSetChanged();

    }


    class postChatItem extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            String postMsg =  "";
            try{
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(30000);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                Uri.Builder builder = new Uri.Builder();
                builder.appendQueryParameter("chatroom_id",params[1]);
                builder.appendQueryParameter("user_id", params[2]);
                builder.appendQueryParameter("name", params[3]);
                builder.appendQueryParameter("message", params[4]);

                String query = builder.build().getEncodedQuery();
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                int response = conn.getResponseCode();
                if (response != 200){
                    Toast.makeText(getApplicationContext(),"higher version than 4.0 will not support main thread connection",Toast.LENGTH_SHORT).show();
                    return null;
                }
                String line;
                InputStream is = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null)
                    postMsg += line;

            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
            return postMsg;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null){
                try {
                    JSONObject json = new JSONObject(result);
                    String status = json.getString("status");
                    if (!status.equals("OK"))
                        Toast.makeText(getApplicationContext(), "status not ok", Toast.LENGTH_SHORT).show();

                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }
    }

    class ChatsList extends AsyncTask<String, Void, String>{

        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            String chatItem = "";

            try{
                InputStream is = null;
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.setRequestMethod("GET");
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(30000);
                conn.connect();

                int response = conn.getResponseCode();
                if (response != 200){
                    Toast.makeText(getApplicationContext(),"higher version than 4.0 will not support main thread connection",Toast.LENGTH_SHORT).show();
                    return null;
                }
                String line;
                is = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null)
                    chatItem += line;

                br.close();
                conn.disconnect();

            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }

            return chatItem;
        }

        @Override
        protected void onPostExecute(String result) {

            boolean meOrOthers;
            if (result != null) {
                try {
                    JSONObject json = new JSONObject(result);
                    String status = json.getString("status");
                    if (!status.equals("OK")) {
                        Toast.makeText(getApplicationContext(), "status not ok", Toast.LENGTH_SHORT).show();
                    } else {
                        JSONObject data = json.getJSONObject("data");
                        JSONArray chatArray = data.getJSONArray("messages");
                        nextPage = data.getInt("current_page") + 1;
                        totalPages = data.getInt("total_pages");
//                        for (int count = 0; count < chatArray.length(); count++) {

                        if(data.getInt("current_page")!=1){
//                            listView.removeAllViewsInLayout();
                            List<ChatItem> mDataNew = null;
                            mDataNew = new LinkedList<ChatItem>();
                            for (ChatItem mDatum : mData)
                                mDataNew.add(mDatum);
                            mData.clear();

                            for (int count = chatArray.length() - 1; count >= 0; count--) {

                                String name = chatArray.getJSONObject(count).getString("name");
                                if (name.equals("abc"))
                                    meOrOthers = true;
                                else
                                    meOrOthers = false;
                                ChatItem item = new ChatItem(chatArray.getJSONObject(count).getInt("user_id"), chatArray.getJSONObject(count).getString("name"),
                                        chatArray.getJSONObject(count).getString("message_time"), chatArray.getJSONObject(count).getString("message"), meOrOthers, R.drawable.background3);
                                mData.add(item);
                                mAdapter.notifyDataSetChanged();
                            }


                            for (int j = 0; j < mDataNew.size(); j++){
                                mData.add(mDataNew.get(j));
                                mAdapter.notifyDataSetChanged();}


                            mAdapter.notifyDataSetChanged();
                        }else {


                            for (int count = chatArray.length() - 1; count >= 0; count--) {

                                String name = chatArray.getJSONObject(count).getString("name");
                                if (name.equals("abc"))
                                    meOrOthers = true;
                                else
                                    meOrOthers = false;
                                ChatItem item = new ChatItem(chatArray.getJSONObject(count).getInt("user_id"), chatArray.getJSONObject(count).getString("name"),
                                        chatArray.getJSONObject(count).getString("message_time"), chatArray.getJSONObject(count).getString("message"), meOrOthers, R.drawable.background3);
                                mData.add(item);
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }



}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if(item.getItemId() == R.id.action_refresh){
            mData.clear();
            new ChatsList().execute("http://18.217.125.61/api/a3/get_messages?chatroom_id=" + currentRoomId + "&page=1");
            mAdapter.notifyDataSetChanged();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_actions, menu);
        return true;
    }

}
