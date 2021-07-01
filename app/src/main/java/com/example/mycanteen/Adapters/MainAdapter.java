package com.example.mycanteen.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.mycanteen.MainActivity2;
import com.example.mycanteen.Models.MainModel;
import com.example.mycanteen.R;
import com.example.mycanteen.cartJava;
import com.example.mycanteen.orderDBhelper;
import com.example.mycanteen.signupJava;

import java.util.ArrayList;

import static com.example.mycanteen.MainActivity2.EMAIL;
import static java.security.AccessController.getContext;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.viewholder>{
    public static int orderid=1;
    ArrayList<MainModel> list;
    Context context;

    public MainAdapter(ArrayList<MainModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.foodlist_page,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        final MainModel model=list.get(position);
        holder.foodimage.setImageResource(model.getImage());
        holder.mainName.setText(model.getName());
        holder.price.setText(model.getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        ImageView foodimage;
        TextView mainName,price;
        Button b;
        ElegantNumberButton e;
        orderDBhelper OrderDB;

        public viewholder(@NonNull final View itemView) {
            super(itemView);
            OrderDB=new orderDBhelper(context);
            foodimage=itemView.findViewById(R.id.imageView);
            mainName=itemView.findViewById(R.id.orderName);
            price=itemView.findViewById(R.id.orderPrice);
            b=itemView.findViewById(R.id.addButton);
            e=itemView.findViewById(R.id.quantityButton);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String Itemname=mainName.getText().toString();
                    String p=price.getText().toString();
                    String Quantity=e.getNumber();
                    int i=Integer.parseInt(p);
                    int j=Integer.parseInt(Quantity);
                    String Price=String.valueOf(i*j);

                    String emailId=EMAIL;
                    if(Quantity.equals("0"))
                    {
                        Toast.makeText(view.getContext(),"Please select valid quantity",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Boolean res=OrderDB.insertOrder(orderid,emailId,Itemname,Price,Quantity);
                        if(res==true)
                        {
                            Toast.makeText(view.getContext(),"Order added to cart",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(itemView.getContext(),"Unsuccessful",Toast.LENGTH_LONG).show();
                        }

                    }



                }
            });

        }
    }

}
