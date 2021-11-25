package com.example.qimou;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

public class MainActivity3 extends AppCompatActivity {
    private EditText loginUser,loginPass;
    private Button loginDengLu;
    private MyJdbc jd;
    private Context context=this;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        jd=new MyJdbc(context,"user",null,1);//创建数据库“user”
        db=jd.getWritableDatabase();
        initView();
        loginDengLu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=loginUser.getText().toString();
                String pass=loginPass.getText().toString();
                if(user.isEmpty()){
                    Toast.makeText(MainActivity3.this, "账号不能为空！请重新填写！", Toast.LENGTH_SHORT).show();
                    loginUser.requestFocus();
                }else if(user.length()<5){
                    Toast.makeText(MainActivity3.this, "账号长度小于5位！请重新填写！", Toast.LENGTH_SHORT).show();
                    loginUser.requestFocus();
                }else if(pass.isEmpty()){
                    Toast.makeText(MainActivity3.this, "密码不能为空！请重新填写！", Toast.LENGTH_SHORT).show();
                    loginPass.requestFocus();
                }else {
                    Cursor c = db.rawQuery("select * from login where user=?",new String[]{user});
                    String userPass=null;//从用户表中查询到的密码
                    if(c.moveToFirst()){//令游标移到第一条记录，同时判断是否查询到记录，若有记录进一步获取密码
                        int n=c.getColumnIndex("pass");//获取用户表中“密码”字段的索引号
                        userPass=c.getString(n);//获取当前记录第n个字段的值
                        if(pass.equals(userPass)){//判断密码是否相等
                            Toast.makeText(MainActivity3.this, "登录成功", Toast.LENGTH_SHORT).show();
                            Intent intent2=new Intent(MainActivity3.this,MainActivity5.class);
                            intent2.putExtra("user",user);
                            startActivityForResult(intent2,2);
                        }else {
                            Toast.makeText(MainActivity3.this, "密码错误！请重新填写！", Toast.LENGTH_SHORT).show();
                            loginPass.requestFocus();
                        }
                    }else {
                        Toast.makeText(MainActivity3.this, "账号不存在！请重新填写！", Toast.LENGTH_SHORT).show();
                        loginUser.requestFocus();
                    }
                }
            }
        });
    }
    public void initView(){
        loginUser=findViewById(R.id.login_user);
        loginPass=findViewById(R.id.login_pass);
        loginDengLu=findViewById(R.id.login_deng_lu);
    }
    public void fanHui(View view) {
        finish();
    }
    public void zc(View view){
        Intent intent2=new Intent(this,MainActivity4.class);
        startActivityForResult(intent2,2);
    }
}