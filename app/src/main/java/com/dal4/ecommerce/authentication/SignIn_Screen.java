package com.dal4.ecommerce.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dal4.ecommerce.R;
import com.dal4.ecommerce.data_base.Remote.RetrofitClient;
import com.dal4.ecommerce.models.UserModel;
import com.dal4.ecommerce.ui.First_Screen;
import com.dal4.ecommerce.ui.Frag_Container;
import com.dal4.ecommerce.utils.Constants;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn_Screen extends AppCompatActivity {

    //Views
    TextInputEditText Ed_email, Ed_Pass;
    TextInputLayout Layout_email, Layout_Pass;
    Button Btn_SignIn, Btn_back_SignIn;
    MaterialCheckBox checkBox_RememberMe;
    TextView AdminToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in__screen);
        initViews();

    }

    private void initViews() {
        Ed_email = findViewById(R.id.EditText_Email_SignIn);
        Ed_Pass = findViewById(R.id.EditText_Pass_SignIn);

        Layout_email = findViewById(R.id.TextFiled_Email_SignIn);
        Layout_Pass = findViewById(R.id.TextFiled_Pass_SignIn);

        Btn_SignIn = findViewById(R.id.Btn_SignIn);
        Btn_back_SignIn = findViewById(R.id.Btn_back_SignIn);

        checkBox_RememberMe = findViewById(R.id.checkbox_RememberMe_SignIn);
        AdminToggle = findViewById(R.id.Admin_Toggle);

        Btn_back_SignIn.setOnClickListener(v -> {
            startActivity(new Intent(SignIn_Screen.this, First_Screen.class));
        });

        Btn_SignIn.setOnClickListener(v -> CheckDataInput(Ed_email, Ed_Pass));

        AdminToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn_Screen.this, SignIn_Admin_Screen.class));
            }
        });
    }

    private void CheckDataInput(TextInputEditText ed_email, TextInputEditText ed_password) {
        String email = ed_email.getText().toString();
        String password = ed_password.getText().toString();


        if (email.isEmpty() && password.isEmpty()) {
            Toast.makeText(this, "Please Enter Your Data !", Toast.LENGTH_SHORT).show();
            Layout_Pass.isErrorEnabled();
            Layout_Pass.setError("You did not enter a password");
            Layout_email.isErrorEnabled();
            Layout_email.setError("You did not enter a email");
            WatchListenerEditTextDisableError(Layout_email, Ed_email);
            WatchListenerEditTextDisableError(Layout_Pass, Ed_Pass);
            return;
        } else if (email.isEmpty()) {
            Layout_email.isErrorEnabled();
            Layout_email.setError("You did not enter a email");
            WatchListenerEditTextDisableError(Layout_email, Ed_email);
            return;
        } else if (password.isEmpty()) {
            Layout_Pass.isErrorEnabled();
            Layout_Pass.setError("You did not enter a password");
            WatchListenerEditTextDisableError(Layout_Pass, Ed_Pass);
            return;
        }

        LoginUser(email, password);
    }

    public void WatchListenerEditTextDisableError(TextInputLayout input, TextInputEditText inputEditText) {

        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                input.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {
                input.setErrorEnabled(false);
            }
        });

    }

    public void LoginUser(String email, String password) {
        Map<Object, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);

        Call<UserModel> call = RetrofitClient.getInstance().UserLogin(map);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    Toast.makeText(SignIn_Screen.this, "Successfully Login", Toast.LENGTH_SHORT).show();

                    UserModel userModel = response.body();
                    Constants.saveUserToken(SignIn_Screen.this, userModel.getToken());

                    if (checkBox_RememberMe.isChecked()) {
                        Constants.saveUserData(SignIn_Screen.this, userModel.getEmail(), password, userModel.get_id(), userModel.getPhone());
                    }

                    Intent intent = new Intent(SignIn_Screen.this, Frag_Container.class);
                    startActivity(intent);
                    finish();
                } else {

                    Toast.makeText(SignIn_Screen.this, response.message() + "", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(SignIn_Screen.this, "incorrect a password or email", Toast.LENGTH_SHORT).show();

            }
        });
    }
}