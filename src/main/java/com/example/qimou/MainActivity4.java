package com.example.qimou;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity4 extends AppCompatActivity {
    private TextView zcYzText;
    private EditText zcName,zcUser,zcPass,zcqPass,zcSjh,zcYzm;
    private RadioGroup zcSex;
    private Button Zc;
    private String name1,sex=null,user1,pass1,qPass1,photo=null,yzm;
    private MyJdbc jd;
    private Context context=this;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        initView1();
        String sco=String.valueOf((Math.random()*9000)+1000);
        zcYzText.setText(sco);
        zcSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb1:
                        sex = "男";
                        break;
                    case R.id.rb2:
                        sex = "女";
                        break;
                }
            }
        });
        Zc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name1=zcName.getText().toString();
                user1=zcUser.getText().toString();
                pass1=zcPass.getText().toString();
                qPass1=zcqPass.getText().toString();
                yzm=zcYzm.getText().toString();
                photo=zcSjh.getText().toString();
                if(name1.isEmpty()){
                    Toast.makeText(MainActivity4.this, "姓名不能为空！请重新填写！", Toast.LENGTH_SHORT).show();
                    zcName.requestFocus();
                }else if(sex==null){
                    Toast.makeText(MainActivity4.this, "性别不能为空！请重新填写！", Toast.LENGTH_SHORT).show();
                }else if(user1.isEmpty()){
                    Toast.makeText(MainActivity4.this, "账号不能为空！请重新填写！", Toast.LENGTH_SHORT).show();
                    zcUser.requestFocus();
                }else if(pass1.isEmpty()){
                    Toast.makeText(MainActivity4.this, "密码不能为空！请重新填写！", Toast.LENGTH_SHORT).show();
                    zcPass.requestFocus();
                }else if(qPass1.isEmpty()){
                    Toast.makeText(MainActivity4.this, "确认密码不能为空！请重新填写！", Toast.LENGTH_SHORT).show();
                    zcqPass.requestFocus();
                }else if(photo.isEmpty()){
                    Toast.makeText(MainActivity4.this, "手机号不能为空！请重新填写！", Toast.LENGTH_SHORT).show();
                    zcSjh.requestFocus();
                }else if(yzm.isEmpty()){
                    Toast.makeText(MainActivity4.this, "验证码不能为空！请重新填写！", Toast.LENGTH_SHORT).show();
                    yzmBtn(zcYzText);
                    zcYzm.requestFocus();
                }else{
                    if(user1.length()<5){
                        Toast.makeText(MainActivity4.this, "账号长度小于5位！请重新填写！", Toast.LENGTH_SHORT).show();
                        zcUser.requestFocus();
                    }else{
                        if(!qPass1.equals(pass1)){
                            Toast.makeText(MainActivity4.this, "确认密码与密码不相等！请重新填写！", Toast.LENGTH_SHORT).show();
                        }else {
                            if(!zcYzText.getText().equals(yzm)){
                                Toast.makeText(MainActivity4.this, "验证码不相等！请重新填写！", Toast.LENGTH_SHORT).show();
                                yzmBtn(zcYzText);
                            }else{
                                jd=new MyJdbc(context,"user",null,1);
                                db=jd.getWritableDatabase();
                                Cursor c = db.rawQuery("select * from login where user=?",new String[]{user1});
                                if(c.moveToFirst()){//令游标移到第一条记录，同时判断是否查询到记录，若有记录进一步获取密码
                                    Toast.makeText(MainActivity4.this, "账号名已存在！请重新填写！", Toast.LENGTH_SHORT).show();
                                    zcUser.requestFocus();
                                }else {
                                    db.execSQL("insert into login values('"+name1+"','"+user1+"','"+sex+"','"+pass1+"','"+photo+"',0)");
                                    Toast.makeText(MainActivity4.this, "注册成功", Toast.LENGTH_SHORT).show();
                                    Intent intent2=new Intent(MainActivity4.this,MainActivity3.class);
                                    startActivityForResult(intent2,2);
                                }
                            }
                        }
                    }
                }
            }
        });
    }
    public void initView1(){
        zcName=findViewById(R.id.zc_qName);
        zcUser=findViewById(R.id.zc_qUser);
        zcPass=findViewById(R.id.zc_qPass);
        zcqPass=findViewById(R.id.zc_qqPass);
        zcSjh=findViewById(R.id.zc_sJh);
        zcYzm=findViewById(R.id.zc_yZ);
        Zc=findViewById(R.id.zc);
        zcYzText=findViewById(R.id.zc_yzText);
        zcSex=findViewById(R.id.zc_sex);
    }
    public void yzmBtn(View view){
        String sco=String.valueOf((Math.random()*9000)+1000);
        zcYzText.setText(sco);
    }
    public void qux1(View view){
        finish();
    }
}