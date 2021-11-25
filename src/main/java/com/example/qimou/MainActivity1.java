package com.example.qimou;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity1 extends AppCompatActivity implements View.OnClickListener {
    private TextView yxgz, tv_tt;
    private ImageView tv_timeShow, imge1, imge2, imge3, imge4, imge5, imge6, imge7, imge8, imge9;
    private LinearLayout yxgz_show, yxgz_gameOver,lin_mos,One;
    private TableLayout tWo;
    private ListView lv1;
    private Button yxgz_gb, btn_start, btn_cxks, btn_tcyx,btn_xuanz,btn_syz,btn_kn,btn_kns,btn_quer,oldScore;
    private int[] h = {R.drawable.konbai, R.drawable.h0, R.drawable.h1, R.drawable.h2, R.drawable.h3, R.drawable.h4, R.drawable.h5, R.drawable.h6, R.drawable.h7, R.drawable.h8, R.drawable.h9};
    private int x[] = {R.drawable.konbai, R.drawable.x0, R.drawable.x1, R.drawable.x2, R.drawable.x3, R.drawable.x4, R.drawable.x5, R.drawable.x6, R.drawable.x7, R.drawable.x8, R.drawable.x9};
    Timer timer;
    TimerTask timerTask;
    int i = 0, score = 0, index = 0, p = 0, imgeW = 0, oo = 0, sDu = 300,ss=7,fanhui=0;
    int[] tele = h;
    ImageView[] y;
    boolean flag = true;
    Handler mHandler = new Handler();
    Handler mHandler1 = new Handler();
    Handler mHandler2 = new Handler();
    Animation animation;
    boolean flagg = true;
    private String user=null;

    public void rrr(View view){//点击返回，回传得分
        Intent it1=new Intent();
        it1.putExtra("fenshu",""+score);
        setResult(RESULT_CANCELED,it1);
        this.finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        Intent it=getIntent();
        user = it.getStringExtra("user");
        Vini();
        time2();
        y = new ImageView[]{imge1, imge2, imge3, imge4, imge5, imge6, imge7, imge8, imge9};
        yxgz.setOnClickListener(this);
        yxgz_gb.setOnClickListener(this);
        btn_start.setOnClickListener(this);
        btn_cxks.setOnClickListener(this);
        btn_tcyx.setOnClickListener(this);
        btn_xuanz.setOnClickListener(this);
        btn_quer.setOnClickListener(this);
        btn_syz.setOnClickListener(this);
        btn_kn.setOnClickListener(this);
        btn_kns.setOnClickListener(this);
        oldScore.setOnClickListener(this);
        for (int j = 0; j < y.length; j++)
            y[j].setOnClickListener(this);
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 0x01010101) {
                    y[imgeW].setEnabled(true);
                    y[imgeW].setImageResource(tele[index]);//随机出现在一个洞口并使灰太狼或者小灰灰慢慢上升
                }
            }
        };
        mHandler1 = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 0x01010102) {
                    y[oo].setImageDrawable(null);//使图片框内的图片设置为null
                }
            }
        };
        mHandler2 = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 0x01010103) {
                    y[imgeW].setImageResource(tele[ss]);
                }
            }
        };
    }

    private void Vini() {
        yxgz = (TextView) findViewById(R.id.yxgz);
        yxgz_show = (LinearLayout) findViewById(R.id.yxgz_show);
        yxgz_gb = (Button) findViewById(R.id.yxgz_gb);
        btn_start = (Button) findViewById(R.id.btn_start);
        tv_timeShow = (ImageView) findViewById(R.id.tv_timeShow);
        yxgz_gameOver = (LinearLayout) findViewById(R.id.yxgz_gameOver);
        lin_mos = (LinearLayout) findViewById(R.id.lin_mos);
        btn_cxks = (Button) findViewById(R.id.btn_cxks);
        btn_tcyx = (Button) findViewById(R.id.btn_tcyx);
        btn_xuanz = (Button) findViewById(R.id.btn_xuanz);
        btn_quer = (Button) findViewById(R.id.btn_quer);
        btn_syz = (Button) findViewById(R.id.btn_syz);
        btn_kn = (Button) findViewById(R.id.btn_kn);
        btn_kns = (Button) findViewById(R.id.btn_kns);
        imge1 = (ImageView) findViewById(R.id.imge1);
        imge2 = (ImageView) findViewById(R.id.imge2);
        imge3 = (ImageView) findViewById(R.id.imge3);
        imge4 = (ImageView) findViewById(R.id.imge4);
        imge5 = (ImageView) findViewById(R.id.imge5);
        imge6 = (ImageView) findViewById(R.id.imge6);
        imge7 = (ImageView) findViewById(R.id.imge7);
        imge8 = (ImageView) findViewById(R.id.imge8);
        imge9 = (ImageView) findViewById(R.id.imge9);
        tv_tt = (TextView) findViewById(R.id.tv_tt);
        One=findViewById(R.id.one);
        tWo=findViewById(R.id.two);
        oldScore=findViewById(R.id.old_score);
        lv1=findViewById(R.id.lv_1);
    }

    public void time2() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {//让文本框内的图片慢慢上升的定时器
                if (p == 1) {//判断是否点击了灰太狼或者小灰灰
                    index = 7;
                    if(ss<10) {
                        y[imgeW].setEnabled(false);
                        Message message2 = new Message();
                        message2.what = 0x01010103;
                        mHandler2.sendMessage(message2);
                        ss++;
                    }else {
                        p = 0;
                        ss=7;
                    }
                }
                else if(p==0){
                    flagg = true;
                    index++;
                    Message message = new Message();
                    message.what = 0x01010101;
                    mHandler.sendMessage(message);
                    if (index >= 7) {
                        oo = imgeW;
                        Message message1 = new Message();
                        message1.what = 0x01010102;
                        mHandler1.sendMessage(message1);
                        imgeW = (int) (Math.random() * 8);
                        flag = Math.random() < 0.7;
                        tele = flag ? h : x;
                        index = 0;
                    }
                }
            }
        };
    }
    @Override
    public void onClick(View v) {//对软件的每一次点击事件做出的反应
        switch (v.getId()) {
            case R.id.yxgz://点击游戏规则
                btn_xuanz.setVisibility(View.GONE);
                yxgz_show.setVisibility(View.VISIBLE);
                yxgz.setVisibility(View.GONE);
                btn_start.setVisibility(View.GONE);
                break;
            case R.id.yxgz_gb://点击关闭游戏规则
                btn_xuanz.setVisibility(View.VISIBLE);
                yxgz_show.setVisibility(View.GONE);
                btn_start.setVisibility(View.VISIBLE);
                yxgz.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_xuanz://选择游戏模式
                lin_mos.setVisibility(View.VISIBLE);
                btn_start.setVisibility(View.GONE);
                btn_xuanz.setVisibility(View.GONE);
                yxgz.setVisibility(View.GONE);
                break;
            case R.id.btn_syz://选择游戏模式为简单
                sDu=300;
                Toast.makeText(MainActivity1.this, "选择模式为简单", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_kn://选择游戏模式为普通
                sDu=200;
                Toast.makeText(MainActivity1.this, "选择模式为普通", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_kns://选择游戏模式为困难
                sDu=100;
                Toast.makeText(MainActivity1.this, "选择模式为困难", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_quer://点击选择模式确认
                lin_mos.setVisibility(View.GONE);
                btn_start.setVisibility(View.VISIBLE);
                btn_xuanz.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_start://点击开始游戏
                btn_xuanz.setVisibility(View.GONE);
                tv_timeShow.setVisibility(View.VISIBLE);
                btn_start.setVisibility(View.GONE);
                yxgz.setVisibility(View.GONE);
                Animation scalegetAnim = getScaleAnimation();
                tv_timeShow.startAnimation(scalegetAnim);
                time2();
                timer.schedule(timerTask, 0, sDu);
                break;
            case R.id.btn_cxks://点击重新开始
                tv_tt.setText("0");
                score = 0;
                i++;
                yxgz_gameOver.setVisibility(View.GONE);
                tv_timeShow.setVisibility(View.VISIBLE);
                Animation scalegetAnim1 = getScaleAnimation();
                tv_timeShow.startAnimation(scalegetAnim1);
                time2();
                timer.schedule(timerTask, 0, sDu);
                break;
            case R.id.btn_tcyx://点击退出游戏
                Intent it=new Intent();
                it.putExtra("fenshu",""+fanhui);
                setResult(RESULT_CANCELED,it);
                this.finish();
                break;
            case R.id.old_score:
                if(oldScore.getText()=="历史战绩"){
                    One.setVisibility(View.GONE);
                    tWo.setVisibility(View.VISIBLE);
                    sqlQue(user);
                    oldScore.setText("返回");
                }else {
                    One.setVisibility(View.VISIBLE);
                    tWo.setVisibility(View.GONE);
                    oldScore.setText("历史战绩");
                }
                break;
        }
        //判断是否点击了灰太狼或者小灰灰
        if (v.getId() == R.id.imge1 || v.getId() == R.id.imge2 || v.getId() == R.id.imge3 || v.getId() == R.id.imge4 || v.getId() == R.id.imge5 || v.getId() == R.id.imge6 || v.getId() == R.id.imge7 || v.getId() == R.id.imge8 || v.getId() == R.id.imge9) {
            if(CommonUtils.isFastDoubleClick()){
                return;
            }else {
                if (flagg&&index!=0&&index!=1) {
                    flagg = false;
                    p = 1;
                    if (tele == h) score = score + 10;
                    else if (tele == x) score = score - 10;
                    tv_tt.setText(String.valueOf(score));
                }
            }
        }
    }
    public void sqlQue(String user){
        if(user==null){user="YouKe";}
        String str = FileIo.read(MainActivity1.this,user);
        if(str!=null){
            List<Map<String,Object>> listArr=new ArrayList<Map<String,Object>>();
            listArr=FileIo.duWrite(str);
            SimpData arrayAdapter = new SimpData(this,listArr,R.layout.layout,new String[]{"paiM","score","timer"},new int[]{R.id.tv_1,R.id.tv_2,R.id.tv_3});
            lv1.setAdapter(arrayAdapter);
        }
    }

    public Animation getScaleAnimation() {//时间条的减小的动画效果
        animation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 1.0f);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(30000);//设置动画的时间
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {//动画开始
                yxgz_gameOver.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {//动画结束
                timer.cancel();
                fanhui=score;
                Toast.makeText(MainActivity1.this, "得分为:" + score, Toast.LENGTH_SHORT).show();
                oo = imgeW;
                Message message1 = new Message();
                message1.what = 0x01010102;
                mHandler1.sendMessage(message1);
                imgeW = (int) (Math.random() * 8);
                flag = Math.random() < 0.7;
                tele = flag ? h : x;
                index = 0;
                p=0;
                y[imgeW].setImageDrawable(null);
                tv_timeShow.setVisibility(View.GONE);
                yxgz_gameOver.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        return animation;
    }
}

class CommonUtils {//判断两次点击的间隔时间
    private static long lastClickTime;
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        lastClickTime = time;
        if (0 < timeD && timeD < 400) {
            return true;
        } else return false;
    }
}