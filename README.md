//我选择开发一个简单的通讯录软件，使用技术栈为java+SQlite作为安卓前后端
 
前端使用线性布局创建输入框
代码与先前作业相近不再另行展示。
后端使用Androidstudio自带的数据库SQlite，首先在aapp/app/src/main/java/com.reputation.aapp路径下创建MyHelper.java的新class文件用于创建数据库，核心代码如下：
public void onCreate(SQLiteDatabase db) {
    String create_table="create table info(id integer primary key autoincrement,uname varchar(20),phone varchar(20))";
    db.execSQL(create_table);
}
可以看到我选择编号自增，保留输入的姓名与电话号码。
随后在mainactivity.java文件下初始化按键并设置监听以便随时响应
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
设置触发按钮时执行的函数：
public void onClick(View view){
    if(view.getId()==R.id.bth_add)
    {
        add();//添加
    }
    else if(view.getId()==R.id.bth_update)
    {
        update();//修改
    }
    else if(view.getId()==R.id.bth_delete)
    {
        delete();//删除
    }
    else if(view.getId()==R.id.bth_query)
    {
        query();//查询
    }
};
接下来是各个功能实现与运行展示：
1.添加功能代码如下：
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

 


2然后是数据查询功能：
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

 
接着是数据修改功能：
需要修改姓名一样的电话号码
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

 



再次查询时会发现姓名为xiaochen的电话号码已经从112255更改为112233
 






4.最后是数据删除功能：
根据姓名删除对应的数据
private void delete()
{
    name=etName.getText().toString().trim();
    db = myHelper.getWritableDatabase();
    db.delete(TABLE_NAME,"uname=?",new String[]{name});
    showToast("信息删除成功");
    db.close();
}

 
可以看到删除成功后再次查询xiaochen的电话号码已经不在上面，从数据库完全删除。
 
