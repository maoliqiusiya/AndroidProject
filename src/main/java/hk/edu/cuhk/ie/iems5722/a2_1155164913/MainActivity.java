package hk.edu.cuhk.ie.iems5722.a2_1155164913;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import hk.edu.cuhk.ie.iems5722.a2_1155164913.project.MainInterface;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btLogin;
    private EditText etId;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btLogin = findViewById(R.id.bt_login);
        etId = findViewById(R.id.et_1);
        etPassword = findViewById(R.id.et_2);

        btLogin.setOnClickListener(this);
    }



    public void onClick(View v){
        String username = etId.getText().toString();
        String password = etPassword.getText().toString();
        Intent intent = null;

//        if(username.equals("cyd")&&password.equals("1")){
            Toast.makeText(getApplicationContext(),"login in successfully",Toast.LENGTH_SHORT).show();
//            intent = new Intent(MainActivity.this, Chatrooms.class);
            intent = new Intent(MainActivity.this, MainInterface.class);
            startActivity(intent);
//        }else{
//            ToastUtil.showMsg(MainActivity.this,"login failed");
//        }


    }

//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//
//    }

}