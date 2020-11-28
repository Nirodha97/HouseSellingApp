package com.nirodha.houseapp;


import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Holder>{

    public static View Holder;
    private Context context;
    private List<Model> List;
    String id1;


    public Adapter(Context context, List<Model> List) {
        this.context = context;
        this.List = List;

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_view2,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        holder.title.setText(List.get(position).getTitle());
        holder.town.setText(List.get(position).getTown()+",");
        holder.district.setText(List.get(position).getDistrict());
        holder.date.setText(List.get(position).getDate());
        holder.id.setText(List.get(position).getId());


    }

    @Override
    public int getItemCount() { return List.size();}



    }

//    public void removeItem(int position)
//    {
//        List.remove(position);
//        notifyItemRemoved(position);
//    }
//
//
//    public void restoreItem(Item item, int position)
//    {
//        List.add(position,item);
//        notifyItemInserted(position);
//    }







class Holder extends RecyclerView.ViewHolder{
    TextView title,town,district,date,id;
    LinearLayout viewB,viewF;
    public Holder(@NonNull View itemView) {
        super(itemView);
        title= itemView.findViewById(R.id.title);
        town= itemView.findViewById(R.id.town);
        district= itemView.findViewById(R.id.district);
        date= itemView.findViewById(R.id.date);
        id= itemView.findViewById(R.id.refno);
//        viewB=itemView.findViewById(R.id.view_background);
//        viewF=itemView.findViewById(R.id.r1);



        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i1 = new Intent(view.getContext(),SellerView.class);
                i1.putExtra("key",id.getText().toString());

                view.getContext().startActivity(i1);

            }
        });

    }



}