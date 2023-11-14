package com.example.stockinproduct;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorSpace;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    Context context;
    ArrayList names, description, prices;

    CardView cardView;

    CustomAdapter(Context context , ArrayList names, ArrayList description , ArrayList prices){
        this.context=context;
        this.names=names;
        this.description=description;
        this.prices=prices;

    }


    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
       View view= inflater.inflate(R.layout.productrow,parent,false);

       return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {

        holder.name.setText(String.valueOf(names.get(position)));
        holder.description.setText(String.valueOf(description.get(position)));
        holder.price.setText(String.valueOf(prices.get(position))+ " DT");

    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder{

        TextView name,description,price;
        ImageView imageprod;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.nameprodcut);
            description=itemView.findViewById(R.id.description);
            price=itemView.findViewById(R.id.priceproduct);
            imageprod=(ImageView) itemView.findViewById(R.id.imageproduct);
            imageprod.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = itemView.getContext();

                    Intent intent = new Intent(context,productdetailss.class);
                    intent.putExtra("name", name.getText().toString());

                    context.startActivity(intent);
                }
            });



        }
    }
}
