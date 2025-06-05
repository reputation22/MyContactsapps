package com.reputation.aapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText etName,etPhone;
    private Button btnAdd,btnUpdate,btnDelete,btnQuery;
    private TextView tvshow;
    private String name,phone;
    private  MyHelper myHelper;
    private SQLiteDatabase db;
    private  ContentValues values;
    private static final String TABLE_NAME="info";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        initview();
    }
    public void initview()
    {
        myHelper= new MyHelper(MainActivity.this);

        etName=findViewById(R.id.et_name);
        etPhone=findViewById(R.id.et_phone);
        tvshow=findViewById(R.id.tv_show);

        btnAdd=findViewById(R.id.bth_add);
        btnDelete=findViewById(R.id.bth_delete);
        btnUpdate=findViewById(R.id.bth_update);
        btnQuery=findViewById(R.id.bth_query);

        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnQuery.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        if(view.getId()==R.id.bth_add)
        {
            add();
        }
        else if(view.getId()==R.id.bth_update)
        {
            update();
        }
        else if(view.getId()==R.id.bth_delete)
        {
            delete();
        }
        else if(view.getId()==R.id.bth_query)
        {
            query();
        }
    };
    private void showToast(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();

    }


    private void add()
    {
        name=etName.getText().toString().trim();
        phone=etPhone.getText().toString().trim();
        db = myHelper.getWritableDatabase();
        values=new ContentValues();
        values.put("uname",name);
        values.put("phone",phone);
        db.insert(TABLE_NAME,null,values);
        showToast("信息插入成功");
        db.close();
    }
    private void update()
    {
        name=etName.getText().toString().trim();
        phone=etPhone.getText().toString().trim();
        db = myHelper.getWritableDatabase();
        values=new ContentValues();
        values.put("phone",phone);
        db.update(TABLE_NAME,values,"uname=?",new String[]{name});
        showToast("信息修改成功");
        db.close();
    }
    private void delete()
    {
        name=etName.getText().toString().trim();
        db = myHelper.getWritableDatabase();
        db.delete(TABLE_NAME,"uname=?",new String[]{name});
        showToast("信息删除成功");
        db.close();
    }
    private void query()
    {
        db=myHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        if(cursor.getCount()==0)
        {
           showToast("数据库表为空！");
        }
        else {
            String str ="";
            while(cursor.moveToNext())
            {
                String str1=cursor.getString(0);
                String str2=cursor.getString(1);
                String str3=cursor.getString(2);
                str=str+str1+"--"+str2+"--"+str3+"\n";
            }
            tvshow.setText(str);
        }
    }

}