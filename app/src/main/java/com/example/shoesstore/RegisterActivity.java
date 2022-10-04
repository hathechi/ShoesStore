package com.example.shoesstore;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;

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

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {
    private Button btnRegis, btnLogin_regis, btnChoice_date;
    private TextInputLayout text_input_user, text_input_pass, text_input_cfpass, text_input_email, text_input_date;
    private TextInputEditText User, Pass, CFpass, Email, Ngaysinh;
    private final Handler handler = new Handler();
    private CheckBox checkbox_regis;
    private int userID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //ánh xạ
        Init();

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        btnLogin_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

        btnChoice_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                        Ngaysinh.setText(date);
                    }
                };
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this,
                        dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));

                datePickerDialog.show();
            }
        });

        btnRegis.setEnabled(false);
        checkbox_regis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    btnRegis.setEnabled(true);
                    FancyToast.makeText(RegisterActivity.this, "Bạn đã chấp nhận với các điều khoản, dịch vụ."
                            , FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                } else {
                    FancyToast.makeText(RegisterActivity.this, "Bạn cần phải đồng ý với các điều khoản, dịch vụ."
                            , FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                    btnRegis.setEnabled(false);
                }
            }
        });
    }

    private void registerUser() {

        String username = User.getText().toString().trim();
        String pass = Pass.getText().toString().trim();
        String email = Email.getText().toString().trim();
        if (BatLoiSua()) {
            //check trùng username
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("user");
            myRef.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        FancyToast.makeText(getBaseContext(), "Tên Tài Khoản Đã Tồn Tại!!", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                    } else {
                        //check trùng email
                        myRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    FancyToast.makeText(getBaseContext(), "Email Đã Tồn Tại!!", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                                } else {
                                    //Lấy id cuối cùng rồi +1
                                    myRef.orderByChild("id").limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                            for (DataSnapshot a : snapshot.getChildren()) {
                                                User iduser = a.getValue(User.class);
                                                Log.i("id", "onDataChange: " + iduser.getId());
                                                userID = iduser.getId();

                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                        }
                                    });


                                    //Gửi data lên firebase
                                    User user = new User(userID + 1, username, pass, email);
                                    UserDAO userDAO = new UserDAO();
                                    userDAO.insertUser(user);
                                    FancyToast.makeText(RegisterActivity.this,
                                            "ĐĂNG KÍ THÀNH CÔNG !",
                                            FancyToast.LENGTH_LONG,
                                            FancyToast.SUCCESS,
                                            false).show();
                                }
//
                            }


                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });

        }

    }

    private void Init() {
        btnRegis = findViewById(R.id.btnRegister_regis);
        btnLogin_regis = findViewById(R.id.btnLogin_regis);
        btnChoice_date = findViewById(R.id.btnChonngay);
        User = findViewById(R.id.etUsername);
        Pass = findViewById(R.id.etPass);
        CFpass = findViewById(R.id.etCFpass);
        Email = findViewById(R.id.etEmail);
        Ngaysinh = findViewById(R.id.etNgaysinh);
        checkbox_regis = findViewById(R.id.checkbox_regis);

        // textinput layout show error
        text_input_user = findViewById(R.id.text_input_user);
        text_input_pass = findViewById(R.id.text_input_pass);
        text_input_cfpass = findViewById(R.id.text_input_CFpass);
        text_input_email = findViewById(R.id.text_input_email);
        text_input_date = findViewById(R.id.text_input_date);
    }

    boolean BatLoiSua() {
        //Bat loi du lieu
        String username = User.getText().toString().trim();
        String pass = Pass.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String cfpass = CFpass.getText().toString().trim();
        String ngaysinh = Ngaysinh.getText().toString().trim();

        if (username.isEmpty() || pass.isEmpty() || cfpass.isEmpty() || email.isEmpty() || ngaysinh.isEmpty()) {
            text_input_user.setError("KHÔNG BỎ TRỐNG DỮ LIỆU !");
            text_input_pass.setError("KHÔNG BỎ TRỐNG DỮ LIỆU !");
            text_input_cfpass.setError("KHÔNG BỎ TRỐNG DỮ LIỆU !");
            text_input_email.setError("KHÔNG BỎ TRỐNG DỮ LIỆU !");
            text_input_date.setError("KHÔNG BỎ TRỐNG DỮ LIỆU !");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    text_input_user.setError(null);
                    text_input_pass.setError(null);
                    text_input_cfpass.setError(null);
                    text_input_email.setError(null);
                    text_input_date.setError(null);
                }
            }, 4000);
            return false;
        }

        String regexTen = "[A-Za-zÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ ]{0,50}";
        if (!username.matches(regexTen)) {
            text_input_user.setError("CHỈ NHẬP TÊN BẰNG CHỮ !");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    text_input_user.setError(null);
                }
            }, 4000);
            return false;
        }
        if (pass.length() <= 3 || cfpass.length() <= 3) {
            text_input_pass.setError("PASSWORD PHẢI LỚN HƠN 3 KÍ TỰ !!");
            text_input_cfpass.setError("PASSWORD PHẢI LỚN HƠN 3 KÍ TỰ !!");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    text_input_pass.setError(null);
                    text_input_cfpass.setError(null);
                }
            }, 4000);
            return false;
        }
        if (!pass.equalsIgnoreCase(cfpass)) {
            text_input_pass.setError("HAI PASSWORD PHẢI TRÙNG NHAU!");
            text_input_cfpass.setError("HAI PASSWORD PHẢI TRÙNG NHAU!");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    text_input_pass.setError(null);
                    text_input_cfpass.setError(null);
                }
            }, 4000);
            return false;
        }
        String regexEmail = "^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@"
                + "[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$";
        if (!email.matches(regexEmail)) {
            text_input_email.setError("NHẬP ĐÚNG ĐỊNH DẠNG EMAIL !");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    text_input_email.setError(null);
                }
            }, 4000);
            return false;
        }
        String[] checkDate = ngaysinh.split("/");
        if (Integer.parseInt(checkDate[checkDate.length - 1]) > 2007) {
            text_input_date.setError("TUỔI PHẢI LỚN HƠN 16!");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    text_input_date.setError(null);
                }
            }, 4000);
            return false;
        }
        return true;
    }

}