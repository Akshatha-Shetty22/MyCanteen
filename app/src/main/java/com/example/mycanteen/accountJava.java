package com.example.mycanteen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;

import static com.example.mycanteen.Adapters.MainAdapter.orderid;
import static com.example.mycanteen.MainActivity2.EMAIL;

public class accountJava extends AppCompatActivity {

    DBhelper DB;
    finalOrder fOrder;
    String email;
    TextView fname,lname,emailid,phone,total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);

        email=EMAIL;
        fname=findViewById(R.id.fnamedisplay);
        lname=findViewById(R.id.lnamedisplay);
        emailid=findViewById(R.id.emaildisplay);
        phone=findViewById(R.id.phonedisplay);
        total=findViewById(R.id.totaldisplay);
        DB=new DBhelper(this);
        fOrder=new finalOrder(this);
        Cursor res=DB.getData(email);
        if(res.getCount()==0)
        {
            Toast.makeText(accountJava.this,"No rows found",Toast.LENGTH_LONG).show();
        }
        while(res.moveToNext()){
            fname.setText(res.getString(0));
            lname.setText(res.getString(1));
            emailid.setText(res.getString(2));
            phone.setText(res.getString(4));

        }
        String finall=fOrder.getTotal(email);
        total.setText(finall);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu2,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        switch(id)
        {
            case R.id.id1:

                return true;
            case R.id.id2:
                Intent intent2=new Intent(accountJava.this,cartJava.class);
                startActivity(intent2);
                return true;
            case R.id.id3:

                Intent intent3=new Intent(accountJava.this,MainActivity.class);
                startActivity(intent3);
                return true;
            case R.id.id4:
                Intent intent4=new Intent(accountJava.this,MainActivity2.class);
                startActivity(intent4);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}


