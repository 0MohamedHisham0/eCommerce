package com.dal4.ecommerce.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dal4.ecommerce.R;
import com.dal4.ecommerce.data_base.Remote.RetrofitClient;
import com.dal4.ecommerce.models.UserModel;
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

public class SignIn_Admin_Screen extends AppCompatActivity {
    //Views
    TextInputEditText Ed_email, Ed_Pass;
    TextInputLayout Layout_email, Layout_Pass;
    Button Btn_SignIn, Btn_back_SignIn;
    MaterialCheckBox checkBox_RememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in__admin__screen);

        initViews();
    }

    private void initViews() {

        Ed_email = findViewById(R.id.EditText_Email_SignIn_Admin);
        Ed_Pass = findViewById(R.id.EditText_Pass_SignIn_Admin);

        Layout_email = findViewById(R.id.TextFiled_Email_SignIn_Admin);
        Layout_Pass = findViewById(R.id.TextFiled_Pass_SignIn_Admin);

        Btn_SignIn = findViewById(R.id.Btn_SignIn_Admin);
        Btn_back_SignIn = findViewById(R.id.Btn_back_SignIn_Admin);

        checkBox_RememberMe = findViewById(R.id.checkbox_RememberMe_SignIn_Admin);

        Btn_back_SignIn.setOnClickListener(v -> {
            onBackPressed();
        });

        Btn_SignIn.setOnClickListener(v -> CheckDataInput(Ed_email, Ed_Pass));

    }

    private void CheckDataInput(TextInputEditText ed_email, TextInputEditText ed_password) {
        String email = ed_email.getText().toString();
        String password = ed_password.getText().toString();

        if (email.isEmpty() && password.isEmpty()) {
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
                    UserModel userModel = response.body();
                    if (userModel.getIsAdmin().equals("true")) {
                        Toast.makeText(SignIn_Admin_Screen.this, "Successfully Login", Toast.LENGTH_SHORT).show();

                        Constants.saveUserToken(SignIn_Admin_Screen.this, userModel.getToken());

                        if (checkBox_RememberMe.isChecked()) {
                            Constants.saveUserData(SignIn_Admin_Screen.this, userModel.getEmail(), password, userModel.get_id(), userModel.getPhone());
                        }

                        Toast.makeText(SignIn_Admin_Screen.this, userModel.getEmail() + "" + userModel.getPassword(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignIn_Admin_Screen.this, Frag_Container.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SignIn_Admin_Screen.this, "Sorry, Your are not Admin", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignIn_Admin_Screen.this, response.message() + "", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(SignIn_Admin_Screen.this,  "incorrect a password or email", Toast.LENGTH_SHORT).show();
            }
        });
    }

}