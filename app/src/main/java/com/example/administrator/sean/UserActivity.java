package com.example.administrator.sean;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Administrator on 2016/11/6.
 */
public class UserActivity extends Activity{
    private SQLiteDatabase db;
    private ListView listView1;
    private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    private SimpleAdapter simpleAdapter;
    private Button btn_add,btn_del,btn_search,btn_call;
    private EditText edit_search;
    LinearLayout update;
    EditText text1,text2,text3;
    int num= 1;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user);
        listView1= (ListView) findViewById(R.id.listView1);
        btn_add= (Button) findViewById(R.id.btn_add);
        btn_del= (Button) findViewById(R.id.btn_del);
        btn_search= (Button) findViewById(R.id.btn_search);
        btn_call= (Button) findViewById(R.id.btn_call);
        edit_search= (EditText) findViewById(R.id.edit_search);

        update= (LinearLayout) getLayoutInflater().inflate(R.layout.layout_update,null);
        text1= (EditText) update.findViewById(R.id.sno);
        text2= (EditText) update.findViewById(R.id.name);
        text3= (EditText) update.findViewById(R.id.grade);

        db=openOrCreateDatabase("student.db",MODE_PRIVATE,null);
       // db.execSQL("create table if not exists image_tb(id text not null ,img BLOB)");
        db.execSQL("create table if not exists student_tb(_id Integer Primary key,sno text not null,name text not null,sex text not null,grade text not null)");
        InputStream inputStream = getResources().openRawResource(R.raw.student);
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "gbk");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                StringTokenizer st=new StringTokenizer(line,"  ");
                ContentValues values=new ContentValues();
                int i=0;
                while(st.hasMoreTokens()){
                    String current=st.nextToken();
                    if(i==0){
                        values.put("sno",current);
                    }
                    if(i==1){
                        values.put("name",current);
                    }
                    if(i==2) {
                        values.put("sex", current);
                    }
                    if(i==3){
                        values.put("grade",current);
                    }
                    ++i;
                }
                db.insert("student_tb",null,values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Cursor curson=db.query(true,"student_tb",new  String[]{"sno","name","grade"},null,null,null,null,"sno asc",null);
//        String img_name;
//        int id;
//        byte[] result;
//        while(curson.moveToNext()) {
//            img_name="a"+curson.getString(curson.getColumnIndex("sno"));
//            id = getResources().getIdentifier(img_name, "drawable", this.getBaseContext().getPackageName());
//            if(id==0){
//                id=R.drawable.five;
//            }
//            Bitmap bm = BitmapFactory.decodeResource(getResources(), id);
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            bm.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
//            result = outputStream.toByteArray();
//            ContentValues value = new ContentValues();
//            value.put("id",curson.getString(curson.getColumnIndex("sno")));
//            value.put("img", result);
//            db.insert("image_tb", null, value);
//            outputStream.reset();bm.recycle();
//        }
        simpleAdapter=new SimpleAdapter(this,list,R.layout.item,new String[]{"img","sno","name","grade"},new int[]{R.id.img,R.id.sno,R.id.name,R.id.grade});
        listView1.setAdapter(simpleAdapter);
        Cursor cur=db.query(true,"student_tb",new  String[]{"sno","name","grade"},null,null,null,null,"sno asc",null);
        //Cursor c=db.query(true,"image_tb",new String[]{"id","img"},null,null,null,null,null,null);
        list.clear();
        while(cur.moveToNext()){
            String sno=cur.getString(cur.getColumnIndex("sno"));
            String name=cur.getString(cur.getColumnIndex("name"));
            String grade=cur.getString(cur.getColumnIndex("grade"));
//            byte[] draw=c.getBlob(c.getColumnIndex("img"));
//            Bitmap btm=BitmapFactory.decodeByteArray(draw,0,draw.length);
//            BitmapDrawable btd=new BitmapDrawable(getResources(),btm);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img",R.drawable.five);
            map.put("sno",sno);
            map.put("name",name);
            map.put("grade",grade);
            list.add(map);
        }
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout add= (LinearLayout) getLayoutInflater().inflate(R.layout.layout_add,null);
                final EditText text1= (EditText) add.findViewById(R.id.text1);
                final EditText text2= (EditText) add.findViewById(R.id.text2);
                final EditText text3= (EditText) add.findViewById(R.id.text3);
                final EditText text4= (EditText) add.findViewById(R.id.text4);
                AlertDialog alter=new AlertDialog.Builder(UserActivity.this).create();
                alter.setTitle("添加学生信息");
                alter.setView(add);
                alter.setButton(DialogInterface.BUTTON_POSITIVE,"添加", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ContentValues newRow=new ContentValues();
                        newRow.put("sno",text1.getText().toString());
                        newRow.put("name",text2.getText().toString());
                        newRow.put("sex",text3.getText().toString());
                        newRow.put("grade",text4.getText().toString());
                        if(text1.getText().toString().equals("")||text2.getText().toString().equals("")) {
                            Toast.makeText(UserActivity.this, "请完善需要添加的信息", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            db.insert("student_tb", null, newRow);
                        }
                        Cursor cur=db.query(true,"student_tb",new  String[]{"sno","name","grade"},null,null,null,null,"sno asc",null);
                        list.clear();
                        while(cur.moveToNext()){
                            String sno=cur.getString(cur.getColumnIndex("sno"));
                            String name=cur.getString(cur.getColumnIndex("name"));
                            String grade=cur.getString(cur.getColumnIndex("grade"));
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("img",R.drawable.one);
                            map.put("sno",sno);
                            map.put("name",name);
                            map.put("grade",grade);
                            list.add(map);
                        }
                    }
                });
                alter.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                alter.show();
            }
        });

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog dialog=new AlertDialog.Builder(UserActivity.this).create();
                dialog.setView(update);
                final String str=list.get(i).toString().substring(list.get(i).toString().indexOf("=6"),list.get(i).toString().indexOf("}"));
                final String str1=str.substring(1);
                text1.setText(str1);
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确认修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ContentValues values=new ContentValues();
                        if(!text2.getText().toString().equals("")&&!text3.getText().toString().equals("")) {
                            values.put("name", text2.getText().toString());
                            values.put("grade", text3.getText().toString());
                            db.update("student_tb", values, "sno=?", new String[]{text1.getText().toString()});
                        }else{
                            Toast.makeText(UserActivity.this,"请完善修改信息",Toast.LENGTH_SHORT).show();
                        }
                        Cursor cur=db.query(true,"student_tb",new  String[]{"sno","name","grade"},null,null,null,null,"sno asc",null);
                        if(cur==null)
                            return;
                        list.clear();
                        while(cur.moveToNext()){
                            String sno=cur.getString(cur.getColumnIndex("sno"));
                            String name=cur.getString(cur.getColumnIndex("name"));
                            String grade=cur.getString(cur.getColumnIndex("grade"));
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("img",R.drawable.one);
                            map.put("sno",sno);
                            map.put("name",name);
                            map.put("grade",grade);
                            list.add(map);
                        }
                    }
                });
                dialog.show();
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cur=null;
                if(!edit_search.getText().toString().equals("")) {
                    cur = db.query(true, "student_tb", new String[]{"_id", "sno", "name", "sex", "grade"}, "name=" + "\"" + edit_search.getText().toString() + "\"", null, null, null, "sno asc", null);
                }
                if(cur==null)
                    return;
                if(cur.getCount()==0) {
                    Toast.makeText(UserActivity.this, "没有同学信息", Toast.LENGTH_SHORT).show();
                }else{
                    while(cur.moveToNext()) {
                        Toast.makeText(UserActivity.this, cur.getString(cur.getColumnIndex("sno")) + "  " + cur.getString(cur.getColumnIndex("name")) + " \n"
                                + cur.getString(cur.getColumnIndex("sex")) + "  " + cur.getString(cur.getColumnIndex("grade")), Toast.LENGTH_SHORT).show();
                    }
                }
                edit_search.setText("");
            }
        });

        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout call= (LinearLayout) getLayoutInflater().inflate(R.layout.layout_call,null);
                final ImageView imageView= (ImageView) call.findViewById(R.id.call_img);
                final TextView textView1= (TextView) call.findViewById(R.id.call_sno);
                final TextView textView2= (TextView) call.findViewById(R.id.call_name);
                CheckBox checkBox_in= (CheckBox) call.findViewById(R.id.checkbox_in);
                CheckBox checkBox_late= (CheckBox) call.findViewById(R.id.checkbox_late);
                CheckBox checkBox_soon= (CheckBox) call.findViewById(R.id.checkbox_soon);
                CheckBox checkBox_off= (CheckBox) call.findViewById(R.id.checkbox_off);
                Button btn_next= (Button) call.findViewById(R.id.call_next);
                final String []sno=new String[200];
                final String []name=new String[200];
                //final String []id=new String[200];
                int count=0;
                Cursor cur=db.query(true,"student_tb",new  String[]{"sno","name","grade"},null,null,null,null,"sno asc",null);
                while(cur.moveToNext()&&count<100){
                    //id[count]=cur.getString(cur.getColumnIndex("sno"));
                    sno[count]= cur.getString(cur.getColumnIndex("sno"));
                    name[count]= cur.getString(cur.getColumnIndex("name"));
                    count++;
                }
                textView1.setText(sno[0]);
                textView2.setText(name[0]);
//                Cursor c=db.query(true,"image_tb",new String[]{"img"},"id="+"\""+id[0]+"\"",null,null,null,null,null);
//                byte[] draw = c.getBlob(c.getColumnIndex("img"));
//                Bitmap btm = BitmapFactory.decodeByteArray(draw, 0, draw.length);
//                BitmapDrawable btd = new BitmapDrawable(getResources(), btm);
//                imageView.setImageDrawable(btd);
                AlertDialog dialog=new AlertDialog.Builder(UserActivity.this).create();
                dialog.setView(call);
                btn_next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Cursor c=db.query(true,"image_tb",new String[]{"img"},"id="+"\""+id[num]+"\"",null,null,null,null,null);
//                        c.moveToFirst();
//                        byte[] draw=c.getBlob(c.getColumnIndex("img"));
//                        Bitmap btm=BitmapFactory.decodeByteArray(draw,0,draw.length);
//                        BitmapDrawable btd=new BitmapDrawable(getResources(),btm);
//                        imageView.setImageDrawable(btd);
                        textView1.setText(sno[num]);
                        textView2.setText(name[num]);
                        num++;
                    }
                });
                dialog.show();
            }
        });
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        db.close();
    }
}
