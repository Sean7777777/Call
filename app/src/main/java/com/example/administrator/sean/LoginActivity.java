package com.example.administrator.sean;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private Button btn_login,btn_register;
    private EditText edit_user,edit_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login= (Button) findViewById(R.id.login);
        btn_register= (Button) findViewById(R.id.register);
        edit_user= (EditText) findViewById(R.id.user);
        edit_password= (EditText) findViewById(R.id.password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edit_user.getText().toString().equals("卡西莫多是疯子")&&edit_password.getText().toString().equals("070716lqq")){
                    Intent intent=new Intent(LoginActivity.this,UserActivity.class);
                    startActivity(intent);
                }else if(edit_user.getText().toString().equals("")&&edit_password.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this,"用户名、密码不能为空！",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent=new Intent(LoginActivity.this,AdminActivity.class);
                    startActivity(intent);
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
