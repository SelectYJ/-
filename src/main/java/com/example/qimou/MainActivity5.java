package com.example.qimou;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MainActivity5 extends AppCompatActivity {
    private MyJdbc jd;
    private Context context=this;
    private TableLayout mainTabLout;
    private LinearLayout mainLinLout;
    private RadioButton mainMan,mainWoman;
    private RadioGroup mainSex;
    private TextView mainUser,mainName,mainPass,mainUser1,mainPhoto;
    private Button btn1,btn2,btn3,startBtn;
    private ListView mainListView;
    private SQLiteDatabase db;
    private String sex=null;
    private String userSex=null,userPass=null,userName=null,userPhoto=null;//从用户表中查询到的密码
    private String user=null;
    private int fen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        jd=new MyJdbc(context,"user",null,1);//创建数据库“user”
        db=jd.getWritableDatabase();
        initView();
        Intent it=getIntent();
        user = it.getStringExtra("user");
        mainUser.setText(user);
        mainSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.main_man:
                        sex = "男";
                        break;
                    case R.id.main_woman:
                        sex = "女";
                        break;
                }
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = db.rawQuery("select * from login where user=?",new String[]{user});
                if(c.moveToFirst()){//令游标移到第一条记录，同时判断是否查询到记录，若有记录进一步获取密码
                    int n=c.getColumnIndex("pass");//获取用户表中“密码”字段的索引号
                    userPass=c.getString(n);//获取当前记录第n个字段的值
                    userName=c.getString(0);
                    userSex=c.getString(2);
                    userPhoto=c.getString(4);
                }
                mainLinLout.setVisibility(View.GONE);
                mainTabLout.setVisibility(View.VISIBLE);
                mainName.setText(userName);
                mainUser1.setText(user);
                mainPass.setText(userPass);
                mainPhoto.setText(userPhoto);
                if(userSex=="男"){
                    mainWoman.setChecked(true);
                    mainMan.setChecked(false);
                }else {
                    mainMan.setChecked(true);
                    mainWoman.setChecked(false);
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainLinLout.setVisibility(View.VISIBLE);
                mainTabLout.setVisibility(View.GONE);
                sqlQue(user);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn3.getText()=="编辑完成"){
                    btn3.setText("修改信息");
                    mainName.setEnabled(false);
                    mainUser1.setEnabled(false);
                    mainPhoto.setEnabled(false);
                    mainPass.setEnabled(false);
                    mainMan.setEnabled(false);
                    mainWoman.setEnabled(false);
                    String newName=null,newUser=null,newPass=null,newPhoto=null;
                    newName=mainName.getText().toString();
                    newUser=mainUser1.getText().toString();
                    newPass=mainPass.getText().toString();
                    newPhoto=mainPhoto.getText().toString();
                    if(newUser.length()>=5){
//                    ContentValues values = new ContentValues();
                        //存放更新后的人名
//                    values.put("name",newName);
//                    values.put("user",newUser);
//                    values.put("sex",sex);
//                    values.put("pass",newPass);
//                    values.put("photo",newPhoto);
//                    int result = db.update("login",values, "user=?", new String[] {u});
//                    Toast.makeText(MainActivity5.this, "result="+result+",u="+u, Toast.LENGTH_SHORT).show();
                        if(sex==null||newUser.isEmpty()||newName.isEmpty()||newPass.isEmpty()||newPhoto.isEmpty()){
                            Toast.makeText(MainActivity5.this, "用户信息项不能为空！请重新填写！", Toast.LENGTH_SHORT).show();
                        }else {
                            if(!user.equals(newUser)||!userName.equals(newName)||!userPass.equals(newPass)||!userSex.equals(sex)||!userPhoto.equals(newPhoto)){
                                String sql="update login set name='"+newName+"',user='"+newUser+"',sex='"+sex+"',pass='"+newPass+"',photo='"+newPhoto+"' where user='"+user+"'";
                                db.execSQL(sql);
                                if(!user.equals(newUser)){
                                    Intent intent2=new Intent(MainActivity5.this,MainActivity3.class);
                                    startActivityForResult(intent2,2);
                                }
                                Toast.makeText(MainActivity5.this, "信息修改成功！", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(MainActivity5.this, "信息没有变化！", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }else {
                        Toast.makeText(MainActivity5.this, "账号长度小于5位！请重新填写！", Toast.LENGTH_SHORT).show();
                        mainUser1.requestFocus();
                    }
                }else{
                    btn3.setText("编辑完成");
                    mainName.setEnabled(true);
                    mainUser1.setEnabled(true);
                    mainPhoto.setEnabled(true);
                    mainPass.setEnabled(true);
                    mainMan.setEnabled(true);
                    mainWoman.setEnabled(true);
                }
            }
        });
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(MainActivity5.this,MainActivity1.class);
                intent2.putExtra("user",user);
                startActivityForResult(intent2,5);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            switch (requestCode){
                case 5:
                    if(resultCode==RESULT_CANCELED){
                        String str=data.getStringExtra("fenshu");
                        fen=Integer.parseInt(str);
                        dW(user,fen);
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
            String str1 = FileIo.read(MainActivity5.this,user);
            if(str1==null){
                str1="";
            }
            String str2=str1+fen+"?"+sim+",";
            FileIo.write(MainActivity5.this,user,str2);
        }
    }

    public void sqlQue(String user){
//        if(user==null){user="YouKe";}
        String str = FileIo.read(MainActivity5.this,user);
        if(str!=null&&!str.isEmpty()){
            List<Map<String,Object>> listArr=new ArrayList<Map<String,Object>>();
            listArr=FileIo.duWrite(str);
            SimpData arrayAdapter = new SimpData(this,listArr,R.layout.layout,new String[]{"paiM","score","timer"},new int[]{R.id.tv_1,R.id.tv_2,R.id.tv_3});
            mainListView.setAdapter(arrayAdapter);
        }
    }
    public void initView(){
        mainTabLout=findViewById(R.id.main_tabLout);
        mainLinLout=findViewById(R.id.main_linlout);
        mainUser=findViewById(R.id.main_user);
        btn1=findViewById(R.id.main_btn1);
        btn2=findViewById(R.id.main_btn2);
        mainListView=findViewById(R.id.main_listView);
        btn3=findViewById(R.id.main_btn3);
        startBtn=findViewById(R.id.start_btn);
        mainName=findViewById(R.id.main_name);
        mainUser1=findViewById(R.id.main_user1);
        mainSex=findViewById(R.id.main_sex);
        mainMan=findViewById(R.id.main_man);
        mainWoman=findViewById(R.id.main_woman);
        mainPass=findViewById(R.id.main_pass);
        mainPhoto=findViewById(R.id.main_photo);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_logout:  //注销菜单项事件
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("确定注销?");
                builder.setMessage("你确定注销当前用户吗?");
                builder.setIcon(R.drawable.exit);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String sql="delete from login where user="+user;
                        db.execSQL(sql);
                        FileIo.write(MainActivity5.this,user,"");
                        Intent intent2=new Intent(MainActivity5.this,MainActivity3.class);
                        startActivityForResult(intent2,2);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
                break;
        }
        return true;
    }
}