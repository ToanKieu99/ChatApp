package com.toanvq.fpoly.chatapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.toanvq.fpoly.chatapp.R;
import com.toanvq.fpoly.chatapp.activity.MessageActivity;
import com.toanvq.fpoly.chatapp.model.Chat;
import com.toanvq.fpoly.chatapp.model.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;


    private Context mcontext;
    private List<Chat> mChat;
    private String imageurl;

    FirebaseUser fuser;


    public MessageAdapter(Context mcontext, List<Chat> mChat, String imageurl) {
        this.mcontext = mcontext;
        this.mChat = mChat;
        this.imageurl = imageurl;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == MSG_TYPE_RIGHT) {

            LayoutInflater inflater = LayoutInflater.from(mcontext);
            View view = inflater.inflate(R.layout.chat_item_right, viewGroup, false);
            return new MessageAdapter.ViewHolder(view);
        } else {
            LayoutInflater inflater = LayoutInflater.from(mcontext);
            View view = inflater.inflate(R.layout.chat_item_left, viewGroup, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder viewHolder, int i) {
        Chat chat = mChat.get(i);
        viewHolder.show_message.setText(chat.getMessage());
        if (imageurl.equals("default")){
            viewHolder.aprofileImage.setImageResource(R.drawable.avatar);
        }else {
            Picasso.with(mcontext).load(imageurl).into(viewHolder.aprofileImage);
        }
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView aprofileImage;
        private TextView show_message;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            aprofileImage = (CircleImageView) itemView.findViewById(R.id.profile_image);
            show_message = (TextView) itemView.findViewById(R.id.show_message);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if (mChat.get(position).getSender().equals(fuser.getUid())) {
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}