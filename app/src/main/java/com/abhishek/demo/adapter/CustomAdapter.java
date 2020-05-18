package com.abhishek.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.abhishek.demo.R;
import com.abhishek.demo.model.Product;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {


    private List<Product> dataSet;
    private Context mContext;
    public CustomAdapter(Context context, List<Product> data ) {
        this.mContext = context;
        this.dataSet = data;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_recyclerview, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view, mContext);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;
        TextView textViewVersion = holder.textViewVersion;
        ImageView imageView = holder.imageViewIcon;

        textViewName.setText("Product Name :" + dataSet.get(listPosition).getName());
        textViewVersion.setText("Product Disc :" + dataSet.get(listPosition).getDescription());
        holder.regularPrice.setText("Regular Price :" + dataSet.get(listPosition).getRegularPrice());
        holder.salePrice.setText("Sale Price :" + dataSet.get(listPosition).getRegularPrice());

   holder.imageViewIcon.setOnTouchListener(new View.OnTouchListener() {
       @Override
       public boolean onTouch(View v, MotionEvent event) {
           if(event.getAction() == MotionEvent.ACTION_DOWN){
               //*TOUCH


               Animation aniSlide = AnimationUtils.loadAnimation(mContext,R.anim.zoomin);
               holder.imageViewIcon.startAnimation(aniSlide);




           }else if(event.getAction() == MotionEvent.ACTION_UP) {
               //*RELEASE
               Animation aniSlide = AnimationUtils.loadAnimation(mContext,R.anim.zoomout);
               holder.imageViewIcon.startAnimation(aniSlide);
           }

           return false;
       }
   });

        // my laptop is real slow and my time is getting over for now so i am hardcoding these thing althoughi know this can be dynamically efficient

        if (dataSet.get(listPosition).getName().equals("Mobile")) {
            // index for phone so we will show phone image
            imageView.setImageResource(R.drawable.phone);

        } else if (dataSet.get(listPosition).getName().equals("Trimmer")) {
            // index for trimmer so we will show phone image
            imageView.setImageResource(R.drawable.trimmer);
        } else if (dataSet.get(listPosition).getName().equals("Earphone")) {
            // index for trimmer so we will show phone image
            imageView.setImageResource(R.drawable.earphone);
        } else {
            // index for trimmer so we will show phone image
            imageView.setImageResource(R.drawable.trimmer);
        }

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewVersion;
        ImageView imageViewIcon;

        TextView regularPrice;
        TextView salePrice;
        Animation zoomin;
        Animation zoomout;

        public MyViewHolder(View itemView, Context mContext) {
            super(itemView);
            this.textViewName = itemView.findViewById(R.id.textViewName);
            this.textViewVersion = itemView.findViewById(R.id.textViewVersion);
            this.imageViewIcon = itemView.findViewById(R.id.imageView);
            this.regularPrice = itemView.findViewById(R.id.tv_regularPrice);
            this.salePrice = itemView.findViewById(R.id.tv_salesPrice);

        }


    }
}
