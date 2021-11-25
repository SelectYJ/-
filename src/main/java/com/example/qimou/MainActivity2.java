package com.example.qimou;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity {
    private TextView tv_1,tv_2;
    private ImageView iv_1;
    String str="1",uUser="YouKe";
    int Fen=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        viin();
    }
    private void viin(){
        tv_1=findViewById(R.id.tv_1);
        tv_2=findViewById(R.id.tv_2);
        iv_1=findViewById(R.id.iv_1);
    }
    /*
        requestCode ---> 这个整数requestCode提供给onActivityResult，是以便确认返回的数据是从哪个Activity返回的。
                         这个requestCode和startActivityForResult中的requestCode相对应。
        resultCode ----> 这整数resultCode是由子Activity通过其setResult()方法返回。
        data       ----> 一个Intent对象，带有返回的数据。
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            switch (requestCode){
                case 1:
                    if(resultCode==RESULT_CANCELED){
                        str=data.getStringExtra("fenshu");
                        int fen=Integer.parseInt(str);
                        dW(uUser,fen);
                        Fen=fen;
                        tv_1.setText("您的游戏得分为："+str+"分!");
                        if(fen>0&fen<=50){
                            tv_2.setText("超越了"+str+"%的玩家！");
                            iv_1.setImageResource(R.drawable.sl);
                        }else if(fen>50&fen<100){
                            tv_2.setText("超越了"+str+"%的玩家！");
                            iv_1.setImageResource(R.drawable.sls);
                        }else if(fen==0){
                            tv_2.setText("怎么是0分勒，再来一次吧！");
                            iv_1.setImageResource(R.drawable.wenhao);
                        }else if(fen<0){
                            tv_2.setText("怎么是"+str+"分勒，再来一次吧！");
                            iv_1.setImageResource(R.drawable.wenhao);
                        }else {
                            tv_2.setText("超越了100%的玩家！");
                            iv_1.setImageResource(R.drawable.gaofen);
                        }
                    }
                    break;
            }
        }
    }

    public void dW(String user,int fen){
        Date date=new Date();
//                        String time = date.toLocaleString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
        String sim = dateFormat.format(date);
//                        Toast.makeText(MainActivity2.this,"sim="+sim,Toast.LENGTH_SHORT).show();
        if(fen>0){
            String str1 = FileIo.read(MainActivity2.this,user);
            if(str1==null){
                str1="";
            }
            String str2=str1+str+"?"+sim+",";
            FileIo.write(MainActivity2.this,user,str2);
        }
    }

    boolean b;
    public void ddd(View view){
        Intent intent=new Intent(this,MainActivity1.class);
        startActivityForResult(intent,1);
    }
    public void ttt(View view){
        finish();
    }
    public void yyy(View view){
        Intent intent1=new Intent(this,MainActivity3.class);
        startActivityForResult(intent1,2);
    }
}