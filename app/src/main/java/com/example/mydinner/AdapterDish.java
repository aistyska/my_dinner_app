package com.example.mydinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterDish extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Dinner> dinnerObjList;

    Dinner current;
    int currentPosition = 0;

    class MyHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/ {
        TextView textName;
        TextView textPrice;
        TextView textType;
        TextView textDelivery;
        TextView textPayment;

        public MyHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textPrice = itemView.findViewById(R.id.textPrice);
            textType = itemView.findViewById(R.id.textType);
            textDelivery = itemView.findViewById(R.id.textDelivery);
            textPayment = itemView.findViewById(R.id.textPayment);

        }

    }


    public AdapterDish(List<Dinner> dinnerObjList) {
        this.dinnerObjList = dinnerObjList;
    }

    //Inflate the layout when ViewHolder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.container_dish, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        Dinner current = dinnerObjList.get(position);
        myHolder.textName.setText(current.getDishName());
        myHolder.textPrice.setText("Kaina:" + current.getPrice());
        myHolder.textType.setText(current.getDishType());
        myHolder.textDelivery.setText("Pristatymas:" + current.isDeliverable());
        myHolder.textPayment.setText("Mokėjimo būdas:" + current.getPaymentType());
    }

    @Override
    public int getItemCount() {
        return dinnerObjList.size();
    }

}
