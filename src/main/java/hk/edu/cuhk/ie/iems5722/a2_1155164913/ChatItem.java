package hk.edu.cuhk.ie.iems5722.a2_1155164913;

public class ChatItem {

    private int id;
    private String name;
    private String date;
    private String text;
    //是否为对方发来的信息
    private boolean meOrOthers;
    private int aIcon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getMsgType() {
        return meOrOthers;
    }

    public void setMsgType(boolean meOrOthers) {
        meOrOthers = meOrOthers;
    }

    public int getaIcon() {
        return aIcon;
    }

    public void setaIcon(int aIcon) {
        this.aIcon = aIcon;
    }

    public ChatItem() {
    }

    public ChatItem(int id, String name, String date, String text, boolean meOrOthers, int aIcon) {

        this.id = id;
        this.name = name;
        this.date = date;
        this.text = text;
        this.meOrOthers = meOrOthers;
        this.aIcon = aIcon;
    }
}