package com.example.mycanteen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.mycanteen.Adapters.CartAdapter;
import com.example.mycanteen.Models.CartModel;

import java.util.ArrayList;
import java.util.List;


import static com.example.mycanteen.MainActivity2.EMAIL;

public class orderDBhelper extends SQLiteOpenHelper {
    ArrayList<CartModel> cartModellist=new ArrayList<>();

    public orderDBhelper(Context context) {
        super(context,"Canteen.db",null,1);
        Log.d("checking","Inside constructor");
    }


    @Override
    public void onCreate(SQLiteDatabase myOrder) {
        Log.d("checking","Inside onCreate");
        myOrder.execSQL("Create table Orders(orderId INT primary key,email TEXT,itemName TEXT,itemPrice TEXT,itemQuantity TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase myOrder, int i, int i1) {
        myOrder.execSQL("Drop table if exists Orders");
    }
    public boolean insertOrder(int orderid,String emailid,String itemname,String itemprice,String itemquantity)
    {


        SQLiteDatabase myOrder=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("orderId",orderid);
        contentValues.put("email",EMAIL);
        contentValues.put("itemName",itemname);
        contentValues.put("itemPrice",itemprice);
        contentValues.put("itemQuantity",itemquantity);

        long result=myOrder.insert("Orders",null,contentValues);
        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public Cursor getCartInfo(Integer orderno,String ema)
    {
        String or=String.valueOf(orderno);
        SQLiteDatabase myOrder=this.getWritableDatabase();
        Cursor cursor3=myOrder.rawQuery("Select * from Orders where email=? and orderId=?",new String[]{EMAIL,or});

        return cursor3;
    }
    public boolean deleteOrder(Integer ordId,String email,String itemname,String Quantity)
    {
        String o=String.valueOf(ordId);
        SQLiteDatabase myOrder=this.getWritableDatabase();
        int ans=myOrder.delete("Orders","orderId=? and email=? and itemName=? and itemQuantity=?",new String[]{o,EMAIL,itemname,Quantity});
        if(ans==0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public String calculateTotal(String email)
    {
        float total=0;
        SQLiteDatabase myOrder=this.getWritableDatabase();
        Cursor cursor4=myOrder.rawQuery("Select itemPrice from Orders where email=?",new String[]{email});
        while(cursor4.moveToNext())
        {
            String p=cursor4.getString(0);
            Float n=Float.parseFloat(p);
            total=total+n;

        }
        return String.valueOf(total);
    }
    public boolean deleteCart(String email)
    {
        SQLiteDatabase myOrder=this.getWritableDatabase();
        int ans=myOrder.delete("Orders","email=?",new String[]{EMAIL});
        if(ans==0)
        {
            return false;
        }
        else
        {
            return true;
        }

    }
}
