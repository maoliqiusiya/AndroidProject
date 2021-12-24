package hk.edu.cuhk.ie.iems5722.a2_1155164913;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ChatroomItemAdapter extends BaseAdapter {
    private List<ChatroomItem> mData;
    private Context mContext;
    public ChatroomItemAdapter(List<ChatroomItem> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }
    @Override
    public int getCount() {
        return mData.size();
    }
    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView title;
        convertView = null;
        if(mData.equals(" "))
            return convertView;

        else {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.chatroom_item, parent, false);
            title = (TextView)convertView.findViewById(R.id.chatroom_name);
            title.setText(mData.get(position).getName());
            return convertView;
        }
    }

}
