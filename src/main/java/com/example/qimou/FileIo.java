package com.example.qimou;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileIo {
    private static String[] paiM,scoRe,timeR;
    public static void write(Context context,String fileNme,String fileContent){
        FileOutputStream fos=null;
        try{
            fos=context.openFileOutput(fileNme,Context.MODE_PRIVATE);
            fos.write(fileContent.getBytes());
            //Toast.makeText(context,"文件保存成功",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            //Toast.makeText(context,"文件保存失败",Toast.LENGTH_SHORT).show();
        }finally {
            if (fos!=null){
                try{
                    fos.flush();
                    fos.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
    public static String read(Context context,String fileNme){
        FileInputStream fis=null;
        String fileContent=null;
        try{
            fis=context.openFileInput(fileNme);
            byte[] buffer=new byte[fis.available()];
            fis.read(buffer);
            fileContent=new String(buffer);
        }catch (Exception e){
            //Toast.makeText(context,"文件读取失败",Toast.LENGTH_SHORT).show();
        }finally {
            if (fis!=null){
                try {
                    fis.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return fileContent;
    }

    public static List<Map<String,Object>> duWrite(String str){
        List<Map<String,Object>> listArr1=new ArrayList<Map<String,Object>>();
        String[] str1=str.split(",");
        int s;
        paiM=new String[str1.length];
        scoRe=new String[str1.length];
        timeR=new String[str1.length];
        for(int i=0;i<str1.length;i++){
            paiM[i]= String.valueOf(i+1);
            String[] sco=new String[2];
            sco=str1[i].split("\\?");
            scoRe[i]=sco[0];
            timeR[i]=sco[1];
        }
        Arrays.sort(scoRe, Collections.reverseOrder());
        List<Map<String,Object>> listArr=new ArrayList<Map<String,Object>>();
        for(int i=0;i<paiM.length;i++){
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("paiM",paiM[i]);
            map.put("score",scoRe[i]);
            map.put("timer",timeR[i]);
            listArr.add(map);
        }
        if(str1.length>=10){
            s=10;
        }else
            s=str1.length;
        listArr1=listArr.subList(0,s);
        return listArr1;
    }

}
