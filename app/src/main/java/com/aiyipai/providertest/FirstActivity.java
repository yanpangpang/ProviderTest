package com.aiyipai.providertest;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FirstActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tv_display;
    private ContentResolver resolver;
    private Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_first);

        Button btn_add = findViewById(R.id.btn_add);
        Button btn_query=findViewById(R.id.btn_query);
        tv_display = findViewById(R.id.tv_display);
        btn_add.setOnClickListener(this);
        btn_query.setOnClickListener(this);

        resolver = getContentResolver();
    }

    @Override
    public void onClick(View view){
        String address;
        long number;
        switch(view.getId()){
            case R.id.btn_add:
                long nowTime = System.currentTimeMillis();
                address = "testAddress";
                uri = Uri.parse(MyProvider.PREFIX+MyProvider.AUTHORITY+"/Contacts/"+nowTime);
                ContentValues values = new ContentValues();
                values.put("address",address);
                values.put("number",nowTime);
                resolver.insert(uri,values);
                tv_display.setText("add succeed");
                break;
            case R.id.btn_query:
                Cursor cursor = resolver.query(uri,null,null,null,null);
                if(cursor.moveToFirst()){
                    number = cursor.getLong(cursor.getColumnIndex("number"));
                    address= cursor.getString(cursor.getColumnIndex("address"));
                    tv_display.setText("number:"+number+" address:"+address);
                }
                break;
            default:
                break;
        }
    }
}
