package com.example.administrator.sean;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/11/6.
 */
public class RegisterActivity extends Activity{
    private ImageButton btn_head;
    private Button btn_regis;
    private EditText edit_re_user,edit_re_password,edit_tel,edit_email;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        btn_head= (ImageButton) findViewById(R.id.image);
        btn_regis= (Button) findViewById(R.id.btn_register);
        edit_re_user= (EditText) findViewById(R.id.res_name);
        edit_re_password= (EditText) findViewById(R.id.res_password);
        edit_tel= (EditText) findViewById(R.id.res_tel);
        edit_email= (EditText) findViewById(R.id.res_email);

        btn_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterActivity.this,HeadActivity.class);
                startActivityForResult(intent,1);
            }
        });
        btn_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterActivity.this,UserActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1&&resultCode==1){
            Bundle bundle=data.getExtras();
            int imageID=bundle.getInt("imageID");
            btn_head.setImageResource(imageID);
        }
    }
}
