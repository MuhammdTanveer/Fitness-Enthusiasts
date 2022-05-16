package com.example.fitnessenthusiasts.activities.HelperClasses.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Common.Messages.ChatActivity;
import com.example.fitnessenthusiasts.activities.Databases.UserHelperClass;
import com.example.fitnessenthusiasts.databinding.MessageUserviewLayoutBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MessageUserAdapter2 extends RecyclerView.Adapter<MessageUserAdapter2.MyViewHolder> {

    Context context;
    ArrayList<UserHelperClass> user;

    public MessageUserAdapter2(Context context, ArrayList<UserHelperClass> user) {
        this.context = context;
        this.user = user;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_userview_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserHelperClass model = user.get(position);


        holder.binding.msgProfileName.setText(model.getFullName());
        Picasso.get()
                .load(model.getProfilePhoto())
                .placeholder(R.drawable.placeholder_avatar)
                .into(holder.binding.msgProfilePic);
        holder.binding.msgTime.setVisibility(View.GONE);

        holder.binding.chatUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("userId",model.getUserID());
                intent.putExtra("userFName",model.getFullName());
                intent.putExtra("userPhoto",model.getProfilePhoto());
                intent.putExtra("token",model.getToken());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return user.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        MessageUserviewLayoutBinding binding;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = MessageUserviewLayoutBinding.bind(itemView);
        }
    }
}

