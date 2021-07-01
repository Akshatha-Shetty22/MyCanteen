package com.example.mycanteen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBhelper extends SQLiteOpenHelper {
    public static final String DBNAME="Canteen.db";
    public DBhelper(Context context) {
        super(context,"Canteen.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("Create table Users(firstName TEXT,lastName TEXT,email TEXT primary key,password TEXT,phoneNo TEXT)");
        MyDB.execSQL("Create table Orders(orderId INT,email TEXT,itemName TEXT,itemPrice TEXT,itemQuantity TEXT)");
        MyDB.execSQL("create table finalOrder(cur_date date Default CURRENT_DATE,cur_time time Default CURRENT_TIME,email TEXT,orderName Text,orderQuantity Text,orderPrice Text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("Drop table if exists Users");
        MyDB.execSQL("Drop table if exists Orders");
        MyDB.execSQL("Drop table if exists finalOrder");
    }
    public boolean insertData(String firstName,String lastName,String email,String password,String phoneNo)
    {
        SQLiteDatabase MyDB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("firstName",firstName);
        contentValues.put("lastName",lastName);
        contentValues.put("email",email);
        contentValues.put("password",password);
        contentValues.put("phoneNo",phoneNo);
        long result=MyDB.insert("Users",null,contentValues);
        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public boolean checkEmail(String email) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from Users where email=?", new String[]{email});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
    public boolean checkEmailPassword(String email,String password)
    {
        SQLiteDatabase MyDB=this.getWritableDatabase();
        Cursor cursor=MyDB.rawQuery("Select * from Users where email=? and password=?",new String[]{email,password});
        if(cursor.getCount()>0)
        {
            return true;
        }
        else
        {
            return  false;
        }
    }
    public Cursor getData(String email)
    {
        SQLiteDatabase MyDB=this.getWritableDatabase();
        Cursor cursor=MyDB.rawQuery("Select * from Users where email=?",new String[]{email});

        return cursor;
    }
}
