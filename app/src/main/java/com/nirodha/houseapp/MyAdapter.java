package com.nirodha.houseapp;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<ImageViewHolder>{

    private Context context;
    private List<ModelImage> imageList;
    String id1;

    public MyAdapter(Context context, List<ModelImage> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_view1,parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        Glide.with(context).load(imageList.get(position).getImageurl() ).centerCrop().into(holder.img1);
        holder.title.setText(imageList.get(position).getTitle());
        holder.town.setText(imageList.get(position).getTown());
        holder.district.setText(imageList.get(position).getDistrict());
        holder.price.setText(imageList.get(position).getPrice());
        holder.date.setText(imageList.get(position).getDate());
        holder.id.setText(imageList.get(position).getId());
        id1=imageList.get(position).getId();

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }


}
class ImageViewHolder extends RecyclerView.ViewHolder{
    ImageView img1;
    TextView title,town,district,price,date,id;
    public ImageViewHolder(@NonNull View itemView) {
        super(itemView);
        img1= itemView.findViewById(R.id.img1);
        title= itemView.findViewById(R.id.title);
        town= itemView.findViewById(R.id.town);
        district= itemView.findViewById(R.id.district);
        price= itemView.findViewById(R.id.price);
        date= itemView.findViewById(R.id.date);
        id= itemView.findViewById(R.id.id);


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(view.getContext(),viewHouse.class);
                i1.putExtra("key",id.getText().toString());
                view.getContext().startActivity(i1);


            }
        });
    }


}