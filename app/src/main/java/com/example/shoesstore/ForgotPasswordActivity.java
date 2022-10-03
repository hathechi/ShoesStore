package com.example.shoesstore;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shoesstore.DAO.UserDAO;
import com.example.shoesstore.Moder.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.jetbrains.annotations.NotNull;

public class ForgotPasswordActivity extends AppCompatActivity {
    boolean checkerror = true;
    private TextInputLayout textInputLayout;
    private TextInputEditText textInputEditText;
    private Button btnResetpass, btnResendpassword;
    private EditText etPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        btnResetpass = findViewById(R.id.btnResendpass);
        textInputEditText = findViewById(R.id.etEmail);
        textInputLayout = findViewById(R.id.text_input_resetpass);
        btnResetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = textInputEditText.getText().toString().trim();
                if (email.isEmpty()) {
                    textInputLayout.setError("Chưa Nhập Email!");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            textInputLayout.setError(null);
                        }
                    }, 3000);
                } else {
                    String regexEmail = "^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@"
                            + "[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$";
                    if (!email.matches(regexEmail)) {
                        textInputLayout.setError("NHẬP ĐÚNG ĐỊNH DẠNG EMAIL !");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                textInputLayout.setError(null);
                            }
                        }, 3000);
                    } else {


                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("user");
                        myRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    User user = dataSnapshot.getValue(User.class);
                                    Log.i("email", "onDataChange: " + user.getUsername() + user.getEmail());


                                    FancyToast.makeText(getBaseContext(), "Email Hợp Lệ",
                                            FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();

                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(ForgotPasswordActivity.this);
                                    LayoutInflater layoutInflater1 = ForgotPasswordActivity.this.getLayoutInflater();

                                    //Nhúng layout vào dialog alert
                                    View view1 = layoutInflater1.inflate(R.layout.layout_dialog_resetpass, null);
                                    builder1.setView(view1);
                                    builder1.show();

                                    //ánh xạ
                                    Button btnLogin_resetpass = view1.findViewById(R.id.btnLogin_resetpass);
                                    btnLogin_resetpass.setVisibility(View.INVISIBLE);

                                    btnResendpassword = view1.findViewById(R.id.btnResendpassword);
                                    etPass = view1.findViewById(R.id.etPass);
                                    btnResendpassword.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String pass = etPass.getText().toString().trim();
                                            user.setPassword(pass);
                                            UserDAO userDAO = new UserDAO();
                                            userDAO.updatePassword(user);
                                            btnLogin_resetpass.setVisibility(View.VISIBLE);
                                            btnResendpassword.setVisibility(View.INVISIBLE);
                                            FancyToast.makeText(getBaseContext(), "Đổi Mật Khẩu Thành Công !",
                                                    FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                                            checkerror = false;
//                                        checkerror = true;
                                        }
                                    });

                                    btnLogin_resetpass.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });

                        if (checkerror) {
                            textInputLayout.setError("Email Không Đúng ");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    textInputLayout.setError(null);
                                }
                            }, 3000);
                        }
                    }
                }

            }

        });
    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}