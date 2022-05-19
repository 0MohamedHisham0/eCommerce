package com.dal4.ecommerce.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dal4.ecommerce.R;
import com.dal4.ecommerce.authentication.SignIn_Screen;
import com.dal4.ecommerce.authentication.SignUp_Screen;
import com.dal4.ecommerce.data_base.Remote.RetrofitClient;
import com.dal4.ecommerce.models.UserModel;
import com.dal4.ecommerce.utils.Constants;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class First_Screen extends AppCompatActivity {
    private static final String TAG = "Mohamed";
    //Views
    Button Btn_SignUp, Btn_SignUpWithGoogle;
    TextView Toggle_SignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);
        initViews();
    }

    private void initViews() {

        Btn_SignUp = findViewById(R.id.Btn_SignUp);
        Btn_SignUpWithGoogle = findViewById(R.id.Btn_SignUpWithGoogle);
        Toggle_SignIn = findViewById(R.id.Toggle_SignIn);

        if (!Constants.getUserData(First_Screen.this, "Email").equals("empty") && !Constants.getUserData(First_Screen.this, "Pass").equals("empty")) {
            LoginUser(Constants.getUserData(First_Screen.this, "Email"), Constants.getUserData(First_Screen.this, "Pass"));
            Intent intent = new Intent(First_Screen.this, Frag_Container.class);
            startActivity(intent);
            finish();
        }

        Btn_SignUp.setOnClickListener(v -> {
            Intent intent = new Intent(First_Screen.this, SignUp_Screen.class);
            startActivity(intent);
        });

        Btn_SignUpWithGoogle.setOnClickListener(v -> {

        });

        Toggle_SignIn.setOnClickListener(v -> startActivity(new Intent(First_Screen.this, SignIn_Screen.class)));
    }

    public void LoginUser(String email, String password) {
        Map<Object, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        String basic = Credentials.basic(email, password);

        Call<UserModel> call = RetrofitClient.getInstance().UserLogin(map);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    UserModel userModel = response.body();
                    Constants.saveUserData(First_Screen.this, userModel.getEmail(), password, userModel.get_id(), userModel.getPhone());
                } else {
                    Toast.makeText(First_Screen.this, response.message() + "", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(First_Screen.this, t.getMessage() + "", Toast.LENGTH_SHORT).show();

            }
        });
    }

}