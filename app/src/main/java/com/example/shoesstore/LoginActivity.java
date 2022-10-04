package com.example.shoesstore;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shoesstore.Moder.User;
import com.example.shoesstore.SharedPreferences.MySharedPreferences;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {
    public TextInputEditText username, password;
    public CheckBox checkBox;
    public TextView forgotpass;
    boolean check = true;
    private TextInputLayout text_input_user, text_input_pass;
    private Button btnLogin, btnSignup;
    final MySharedPreferences mySharedPreferences = new MySharedPreferences(LoginActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.etUserName);
        password = findViewById(R.id.etPassword);
        checkBox = findViewById(R.id.cb_login);
        btnSignup = findViewById(R.id.btnRegister);
        forgotpass = findViewById(R.id.forgotpassword);
        text_input_user = findViewById(R.id.text_input_user);
        text_input_pass = findViewById(R.id.text_input_pass);
        btnLogin = findViewById(R.id.btnLogin);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login_onclick();
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);

            }
        });
        //Check dữ liệu  ở dưới checkbox rồi setText lên user và pass
        MySharedPreferences mySharedPreferences = new MySharedPreferences(LoginActivity.this);
        String remember_username = mySharedPreferences.getValue("remember_username");
        String remember_password = mySharedPreferences.getValue("remember_password");
        Log.i("HTC", remember_username + " " + remember_password);
        if (remember_password != null && remember_username != null) {
            username.setText(remember_username);
            password.setText(remember_password);
            checkBox.setChecked(true);
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    FancyToast.makeText(LoginActivity.this, "isChecked", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                } else {
                    FancyToast.makeText(LoginActivity.this, "UnChecked", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                }
            }
        });
    }

    public void Login_onclick() {
        String user = username.getText().toString().trim();
        String pass = password.getText().toString().trim();

        //tạo Handler làm khoảng delay cho sự kiện
        Handler handler = new Handler();
        if (user.equals("") && pass.equals("")) {
            text_input_user.setError("KHÔNG BỎ TRỐNG USER !");
            text_input_pass.setError("KHÔNG BỎ TRỐNG PASSWORD !");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    text_input_user.setError(null);
                    text_input_pass.setError(null);
                }
            }, 4000);

        }
        if (user.equals("")) {
            text_input_user.setError("KHÔNG BỎ TRỐNG USER !");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    text_input_user.setError(null);
                }
            }, 4000);

        } else if (pass.equals("")) {
            text_input_pass.setError("KHÔNG BỎ TRỐNG PASSWORD !");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    text_input_pass.setError(null);
                }
            }, 4000);
        } else {

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("user");
            Query query = myRef.orderByChild("username").equalTo(user);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        User getuser = dataSnapshot.getValue(User.class);

                        if (getuser.getUsername().equalsIgnoreCase(user) && getuser.getPassword().equalsIgnoreCase(pass)) {
                            //remember login
                            if (checkBox.isChecked()) {
                                MySharedPreferences mySharedPreferences = new MySharedPreferences(LoginActivity.this);
                                mySharedPreferences.putValue("remember_username", getuser.getUsername());
                                mySharedPreferences.putValue("remember_password", getuser.getPassword());
//                                FancyToast.makeText(LoginActivity.this, "isChecked", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                            } else {
                                MySharedPreferences mySharedPreferences = new MySharedPreferences(LoginActivity.this);
                                mySharedPreferences.putValue("remember_username", null);
                                mySharedPreferences.putValue("remember_password", null);
//                                FancyToast.makeText(LoginActivity.this, "UnChecked", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                            }
                            // CHuyển trang
                            if (getuser.getUsername().equalsIgnoreCase("admin")) {
                                mySharedPreferences.putBooleanValue("permission_admin", true);
                            }

                            mySharedPreferences.putBooleanValue("login", true);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                            intent.putExtra("user", user);
                            startActivity(intent);
                            finish();
                            check = false;
                        } else {
                            text_input_user.setError("SAI TÀI KHOẢN HOẶC MẬT KHẨU !");
                            text_input_pass.setError("SAI TÀI KHOẢN HOẶC MẬT KHẨU !");
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    text_input_user.setError(null);
                                    text_input_pass.setError(null);
                                }
                            }, 4000);
                            check = true;
                        }
                        Log.i("c", "onDataChange: " + getuser.getUsername() + getuser.getPassword());
                    }

                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
            if (check) {
                text_input_user.setError("SAI TÀI KHOẢN !");
                text_input_pass.setError("SAI MẬT KHẨU !");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        text_input_user.setError(null);
                        text_input_pass.setError(null);
                    }
                }, 3000);
            }
        }
    }
}