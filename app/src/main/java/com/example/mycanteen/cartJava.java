package com.example.mycanteen;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycanteen.Adapters.CartAdapter;

import com.example.mycanteen.Models.CartModel;

import com.example.mycanteen.databinding.ActivityMain2Binding;
import com.example.mycanteen.databinding.ActivityMain3Binding;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import static com.example.mycanteen.Adapters.MainAdapter.orderid;
import static com.example.mycanteen.MainActivity2.EMAIL;
import static java.security.AccessController.getContext;

public class cartJava extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    orderDBhelper OrderDB;
    finalOrder fo;
    ActivityMain3Binding binding;
    String emailId;
    Integer orderno;
    TextView t;
    Button order;
    DBhelper DBH;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBH=new DBhelper(this);


        ActivityCompat.requestPermissions(cartJava.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS,Manifest.permission.READ_PHONE_STATE}, PackageManager.PERMISSION_GRANTED);
        emailId=EMAIL;
        orderno=orderid;
        binding= ActivityMain3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        final ArrayList<CartModel> list=new ArrayList<>();

        final CartAdapter adapter = new CartAdapter(list,this);
        binding.cartRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.cartRecycler.setLayoutManager(layoutManager);

        OrderDB=new orderDBhelper(this);
        fo=new finalOrder(this);
        t=findViewById(R.id.tatalBill);
        String ans=OrderDB.calculateTotal(EMAIL);
        t.setText(ans);
        order=findViewById(R.id.orderButton);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor ans=OrderDB.getCartInfo(orderno,EMAIL);
                if(ans.getCount()==0)
                {
                    Toast.makeText(cartJava.this, "No Item in the cart", Toast.LENGTH_LONG).show();
                }
                else
                {
                    boolean finalres=false;
                    while(ans.moveToNext()) {
                        String n = ans.getString(2);
                        String p = ans.getString(3);
                        String q = ans.getString(4);
                        finalres = fo.insertOrder(EMAIL, n, q, p);
                    }

                        if(finalres==true)
                        {
                            Toast.makeText(cartJava.this,"Order Placed",Toast.LENGTH_LONG).show();
                            for (int i = list.size()-1 ; i >= 0 ; i--){
                                adapter.removeItem(i);
                            }

                            boolean ans2=OrderDB.deleteCart(EMAIL);
                            if(ans2==true) {
                                Cursor res=DBH.getData(EMAIL);
                                if(res.getCount()==0)
                                {
                                    Toast.makeText(cartJava.this,"No rows found",Toast.LENGTH_LONG).show();
                                }
                                while(res.moveToNext()){
                                    String f=res.getString(0).trim();
                                    String l=res.getString(1).trim();
                                    String p=res.getString(4).trim();
                                    String source="9591893938";
                                    Log.d("Message feature","sent");
                                SmsManager smsManager=SmsManager.getDefault();
                                smsManager.sendTextMessage(p,null,"Hello "+f+" "+l+"\n"+"Your order has been confirmed\n",null,null);
                                }
                                

                            }
                            else
                            {
                                Toast.makeText(cartJava.this,"Error deleting cart items",Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(cartJava.this,"Error placing order",Toast.LENGTH_LONG).show();
                        }

                }

            }
        });



            Cursor result = OrderDB.getCartInfo(orderno,EMAIL);
            if (result.getCount() == 0) {
                Toast.makeText(cartJava.this, "No Item in the cart", Toast.LENGTH_LONG).show();
            } else {
                while (result.moveToNext()) {
                    String cname = result.getString(2);
                    String cprice = result.getString(3);
                    String cquantity = result.getString(4);
                    
                    list.add(new CartModel(cname, cprice, cquantity));
                }


            }
        drawerLayout=findViewById(R.id.drawableLayout);
        navigationView=findViewById(R.id.nav_views);
        toolbar=findViewById(R.id.tools);
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(cartJava.this);





    }
 /*   @Override
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
                Intent intent2=new Intent(cartJava.this,accountJava.class);
                startActivity(intent2);
                return true;
            case R.id.id2:

                return true;
            case R.id.id3:

                Intent intent3=new Intent(cartJava.this,MainActivity.class);
                startActivity(intent3);
                return true;
            case R.id.id4:
                Intent intent4=new Intent(cartJava.this,MainActivity2.class);
                startActivity(intent4);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }*/
 public void onBackPressed(){
     DrawerLayout drawer=findViewById(R.id.drawableLayout);
     if(drawer.isDrawerOpen(GravityCompat.START))
     {
         drawer.closeDrawer(GravityCompat.START);
     }
     else
     {
         super.onBackPressed();
     }
 }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent intent4=new Intent(cartJava.this,MainActivity2.class);
                startActivity(intent4);
                break; case R.id.nav_cart:

                break;
            case R.id.nav_profile:
                Intent intent2=new Intent(cartJava.this,accountJava.class);
                startActivity(intent2);
                break;
            case R.id.nav_logout:
                Intent intent3=new Intent(cartJava.this,MainActivity.class);
                startActivity(intent3);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

}
