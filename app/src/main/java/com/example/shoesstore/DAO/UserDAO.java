package com.example.shoesstore.DAO;

import com.example.shoesstore.Moder.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserDAO {


    public void insertUser(User user) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("user");
        myRef.child(String.valueOf(user.getId())).setValue(user);
    }

    public void updatePassword(User user) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("user");
        myRef.child(String.valueOf(user.getId())).setValue(user);
    }
}
