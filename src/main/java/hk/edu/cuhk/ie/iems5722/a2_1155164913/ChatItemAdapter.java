package hk.edu.cuhk.ie.iems5722.a2_1155164913;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ChatItemAdapter extends BaseAdapter {
    private List<ChatItem> mData;
    private Context mContext;
    public ChatItemAdapter(List<ChatItem> mData, Context mContext) {
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
        ImageView img_icon;
        TextView title;
        TextView content;
        TextView user;
        convertView=null;
//        convertView = LayoutInflater.from(mContext).inflate(R.layout.chat_term_myself, parent, false);
        if(mData.equals(" ")){
            return convertView;
        }
        else {
            if (mData.get(position).getMsgType() == true) {
//            if (mData. == true) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.chat_item_myself, parent, false);
//                Toast.makeText(mContext.getApplicationContext(), "呜呼呜呼芜湖", Toast.LENGTH_LONG).show();
                img_icon = (ImageView)
                        convertView.findViewById(R.id.tv_user_head_me);
                title = (TextView)
                        convertView.findViewById(R.id.tv_title_me);
                content = (TextView)
                        convertView.findViewById(R.id.tv_content_me);
                user = (TextView)
                    convertView.findViewById(R.id.tv_title_user);
            } else {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.chat_item_others, parent, false);
                img_icon = (ImageView)
                        convertView.findViewById(R.id.tv_user_head_others);
                title = (TextView)
                        convertView.findViewById(R.id.tv_title_others);
                content = (TextView)
                        convertView.findViewById(R.id.tv_content_others);
                user = (TextView)
                        convertView.findViewById(R.id.tv_title_others_user);
            }
            img_icon.setBackgroundResource(mData.get(position).getaIcon());
            title.setText(mData.get(position).getText());
            content.setText(mData.get(position).getDate());
            user.setText(mData.get(position).getName());
            return convertView;
        }
    }

}
