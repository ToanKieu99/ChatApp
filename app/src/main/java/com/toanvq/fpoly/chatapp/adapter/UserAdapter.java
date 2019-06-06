package com.toanvq.fpoly.chatapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.toanvq.fpoly.chatapp.R;
import com.toanvq.fpoly.chatapp.activity.MessageActivity;
import com.toanvq.fpoly.chatapp.model.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context mcontext;
    private List<User> musers;
    private boolean isChat;

    public UserAdapter(Context mcontext, List<User> musers, boolean isChat) {
        this.mcontext = mcontext;
        this.musers = musers;
        this.isChat = isChat;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        View view = inflater.inflate(R.layout.user_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder viewHolder, int i) {
        final User user = musers.get(i);
        viewHolder.ausername.setText(user.getUsername());
        if (user.getImageURL().equals("default")) {
            viewHolder.aprofileImage.setImageResource(R.drawable.avatar);
        } else {
            Picasso.with(mcontext).load(user.getImageURL()).into(viewHolder.aprofileImage);
        }

        if (isChat) {
            if (user.getStatus().equals("online")) {
                viewHolder.img_on.setVisibility(View.VISIBLE);
                viewHolder.img_off.setVisibility(View.GONE);
            } else {
                viewHolder.img_on.setVisibility(View.GONE);
                viewHolder.img_off.setVisibility(View.VISIBLE);
            }
        } else {
            viewHolder.img_on.setVisibility(View.GONE);
            viewHolder.img_off.setVisibility(View.GONE);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, MessageActivity.class);
                intent.putExtra("USERID", user.getId());
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return musers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView aprofileImage;
        private TextView ausername;
        private ImageView img_on, img_off;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            aprofileImage = itemView.findViewById(R.id.aprofile_image);
            ausername = itemView.findViewById(R.id.ausername);
            img_on = itemView.findViewById(R.id.img_on);
            img_off = itemView.findViewById(R.id.img_off);
        }
    }
}
