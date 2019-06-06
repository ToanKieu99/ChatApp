package com.toanvq.fpoly.chatapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toanvq.fpoly.chatapp.R;
import com.toanvq.fpoly.chatapp.adapter.UserAdapter;
import com.toanvq.fpoly.chatapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class FriendFragment extends Fragment {

    RecyclerView rclUser;
    UserAdapter userAdapter;
    List<User> userList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friend_fragment, container, false);

        rclUser = view.findViewById(R.id.recyvlerview_user);
        rclUser.setHasFixedSize(true);
        rclUser.setLayoutManager(new LinearLayoutManager(getContext()));

        userList = new ArrayList<>();
        recylUser();
        return view;
    }

    private void recylUser() {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);

                    assert user != null;
                    assert firebaseUser != null;
                    if (!user.getId().equals(firebaseUser.getUid())){
                        userList.add(user);
                    }
                }
                userAdapter = new UserAdapter(getContext(),userList,false);
                rclUser.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
