package com.example.rolegame.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rolegame.Objects.GlobalClass;
import com.example.rolegame.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.MyViewHolder> {

    //variables
    private int pos;
    private Context context;
    private BottomSheetDialog dialog;
    private ImageView roleImg;
    private ArrayList<Integer> roleImgList = GlobalClass.getImages();

    public DialogAdapter (Context context, BottomSheetDialog dialog, ImageView roleImg){
        this.context = context;
        this.dialog = dialog;
        this.roleImg = roleImg;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_dialog,parent,false);
        return new DialogAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.imageView.setImageResource(roleImgList.get(position));

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roleImg.setImageResource(roleImgList.get(position));
                dialog.cancel();
                pos = position;
            }
        });
    }

    @Override
    public int getItemCount() {
        return roleImgList.size();
    }

    //image position
    public int getImagePosition ()
    {
        return pos;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
