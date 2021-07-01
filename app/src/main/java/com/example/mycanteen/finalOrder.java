package com.example.mycanteen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class finalOrder extends SQLiteOpenHelper {
    public static final String DBNAME="Canteen.db";
    public finalOrder(Context context) {
        super(context,"Canteen.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase finalDB) {
        Log.d("finalOrder","Inside onCreate");
        finalDB.execSQL("create table finalOrder(cur_date date Default CURRENT_DATE,cur_time time Default CURRENT_TIME,email TEXT,orderName Text,orderQuantity Text,orderPrice Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase finalDB, int i, int i1) {
        finalDB.execSQL("Drop table if exists finalOrder");
    }
    public boolean insertOrder(String email,String orderName,String orderQuantity,String orderPrice)
    {
        SQLiteDatabase finalDB=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();

        contentValues.put("email",email);
        contentValues.put("orderName",orderName);
        contentValues.put("orderQuantity",orderQuantity);
        contentValues.put("orderPrice",orderPrice);
        long result=finalDB.insert("finalOrder",null,contentValues);
        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public String getTotal(String email)
    {
        float total=0;
        SQLiteDatabase finalOrder=this.getWritableDatabase();
        Cursor cursor5=finalOrder.rawQuery("Select orderPrice from finalOrder where email=?",new String[]{email});
        while(cursor5.moveToNext())
        {
            String p=cursor5.getString(0);
            Float n=Float.parseFloat(p);
            total=total+n;

        }
        return String.valueOf(total);
    }

}
