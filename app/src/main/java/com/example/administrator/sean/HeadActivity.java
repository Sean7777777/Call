package com.example.administrator.sean;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/11/6.
 */
public class HeadActivity extends Activity {
    public int[] imageID = new int[]{R.drawable.one, R.drawable.two, R.drawable.three,
            R.drawable.four, R.drawable.five, R.drawable.six,
            R.drawable.seven, R.drawable.eight, R.drawable.nine};
    private GridView gridView;
    private SQLiteDatabase db;
    private  ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.head);
        setTitle("选择头像");
        imageView= (ImageView) findViewById(R.id.imageview);
        gridView = (GridView) findViewById(R.id.gridView);

//        db=openOrCreateDatabase("user.db",MODE_PRIVATE,null);
//        db.execSQL("create table if not exists image_tb(_id INTEGER PRIMARY KEY AUTOINCREMENT,img BLOB)");
//        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.one);
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
//        byte[] result = outputStream.toByteArray();
//        ContentValues values = new ContentValues();
//        values.put("img", result);
//        db.insert("image_tb",null,values);
//        Cursor cur=db.query("image_tb", new String[]{"_id","img"}, null, null, null, null, null);
//        byte[] query=null;
//        if(cur.moveToNext()){
//            query=cur.getBlob(cur.getColumnIndex("img"));
//        }
//        Bitmap bitmap=BitmapFactory.decodeByteArray(query, 0, query.length);
//        imageView.setImageBitmap(bitmap);

        BaseAdapter adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return imageID.length;
            }

            @Override
            public Object getItem(int i) {
                return i;
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                ImageView imageview;
                if (view == null) {
                    imageview = new ImageView(HeadActivity.this);
                    imageview.setAdjustViewBounds(true);
                    imageview.setMaxWidth(110);
                    imageview.setMaxHeight(110);
                    imageview.setPadding(2, 2, 2, 2);
                } else {
                    imageview = (ImageView) view;
                }
                imageview.setImageResource(imageID[i]);
                return imageview;
            }
        };
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=getIntent();
                Bundle bundle=new Bundle();
                bundle.putInt("imageID",imageID[i]);
                intent.putExtras(bundle);
                setResult(1,intent);
                finish();
            }
        });

    }
}
