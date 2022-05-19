package com.dal4.ecommerce.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp_Screen extends AppCompatActivity {

    //Views
    private Button Btn_back, Btn_CreateAcc;
    private TextInputLayout Layout_email, Layout_name, Layout_password, Layout_confirmPss, Layout_Phone;
    private TextInputEditText Ed_email, Ed_name, Ed_password, Ed_confirmPss, Ed_Phone;
    private ProgressBar progressBar;
    private LinearLayout Layout_SignUp;
    private MaterialCheckBox CheckBox_RememberMe;

    //Var
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__screen);
        initViews();
    }

    private void initViews() {
        Btn_back = findViewById(R.id.Btn_Back_SignUp);
        Btn_CreateAcc = findViewById(R.id.Btn_CreateAcc);

        Ed_email = findViewById(R.id.EditText_Email_CreateAcc);
        Ed_name = findViewById(R.id.EditText_Name_CreateAcc);
        Ed_password = findViewById(R.id.EditText_Pass_CreateAcc);
        Ed_confirmPss = findViewById(R.id.EditText_ConfirmPass_CreateAcc);
        Ed_Phone = findViewById(R.id.EditText_Phone_CreateAcc);

        Layout_email = findViewById(R.id.TextFiled_Email_CreateAcc);
        Layout_name = findViewById(R.id.TextFiled_Name_CreateAcc);
        Layout_password = findViewById(R.id.TextFiled_Pass_CreateAcc);
        Layout_confirmPss = findViewById(R.id.TextFiled_ConfirmPass_CreateAcc);
        Layout_Phone = findViewById(R.id.TextFiled_Phone_CreateAcc);

        Layout_SignUp = findViewById(R.id.Layout_SignUp);
        progressBar = findViewById(R.id.spin_kit);


        Btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp_Screen.this, First_Screen.class);
                startActivity(intent);
            }
        });

        Btn_CreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Fun Create ACC
                CheckDataInput(Ed_email, Ed_name, Ed_password, Ed_confirmPss, Ed_Phone);

            }

        });

    }

    private void CheckDataInput(TextInputEditText ed_email, TextInputEditText ed_name, TextInputEditText ed_password, TextInputEditText ed_confirmPss, TextInputEditText ed_Phone) {
        String email = ed_email.getText().toString();
        String name = ed_name.getText().toString();
        String password = ed_password.getText().toString();
        String confirm_Pass = ed_confirmPss.getText().toString();
        String phone = ed_Phone.getText().toString();


        if (email.isEmpty() && name.isEmpty() && password.isEmpty() && confirm_Pass.isEmpty() && phone.isEmpty()) {

            Layout_email.isErrorEnabled();
            Layout_email.setError("You did not enter a email");
            WatchListenerEditTextDisableError(Layout_email, Ed_email);

            Layout_name.isErrorEnabled();
            Layout_name.setError("You did not enter a name");
            WatchListenerEditTextDisableError(Layout_name, Ed_name);

            Layout_password.isErrorEnabled();
            Layout_password.setError("You did not enter a password");
            WatchListenerEditTextDisableError(Layout_password, Ed_password);

            Layout_confirmPss.isErrorEnabled();
            Layout_confirmPss.setError("You did not enter a confirm password");
            WatchListenerEditTextDisableError(Layout_confirmPss, Ed_confirmPss);

            Layout_confirmPss.isErrorEnabled();
            Layout_confirmPss.setError("You did not enter a Phone");
            WatchListenerEditTextDisableError(Layout_Phone, Ed_Phone);

            return;

        } else if (email.isEmpty()) {
            Layout_email.isErrorEnabled();
            Layout_email.setError("You did not enter a email");

            WatchListenerEditTextDisableError(Layout_email, Ed_email);
            return;
        } else if (name.isEmpty()) {
            Layout_name.isErrorEnabled();
            Layout_name.setError("You did not enter a name");

            WatchListenerEditTextDisableError(Layout_name, Ed_name);
            return;
        } else if (password.isEmpty()) {
            Layout_password.isErrorEnabled();
            Layout_password.setError("You did not enter a password");
            WatchListenerEditTextDisableError(Layout_password, Ed_password);

            return;
        } else if (confirm_Pass.isEmpty()) {
            Layout_confirmPss.isErrorEnabled();
            Layout_confirmPss.setError("You did not enter a confirm password");
            WatchListenerEditTextDisableError(Layout_confirmPss, Ed_confirmPss);

            return;
        }
        if (phone.isEmpty()) {
            Layout_Phone.isErrorEnabled();
            Layout_Phone.setError("You did not enter Phone");
            WatchListenerEditTextDisableError(Layout_Phone, Ed_Phone);

            return;
        }
        if (!password.equals(confirm_Pass)) {
            Layout_password.isErrorEnabled();
            Layout_confirmPss.isErrorEnabled();

            Layout_confirmPss.setError("Passwords do not match");
            Layout_password.setError("Passwords do not match");

            WatchListenerEditTextDisableError_4Para(Layout_password, Ed_password, Layout_confirmPss, Ed_confirmPss);

            return;
        }

        UserModel userModel = new UserModel(name, email, password, phone);
        CreateNewUser(userModel);
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

    public void WatchListenerEditTextDisableError_4Para(TextInputLayout input, TextInputEditText inputEditText, TextInputLayout input2, TextInputEditText inputEditText2) {

        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                input.setErrorEnabled(false);
                input2.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {
                input.setErrorEnabled(false);
                input2.setErrorEnabled(false);


            }
        });
        inputEditText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                input.setErrorEnabled(false);
                input2.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {
                input.setErrorEnabled(false);
                input2.setErrorEnabled(false);


            }
        });


    }

    public void CreateNewUser(UserModel userModel) {
        Map<Object, Object> hashMap = new HashMap<>();
        hashMap.put("name", userModel.getName());
        hashMap.put("email", userModel.getEmail());
        hashMap.put("password", userModel.getPassword());
        hashMap.put("phone", userModel.getPhone());

        Call<UserModel> call = RetrofitClient.getInstance().CreateNewUser(hashMap);

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful() && response.code() == 201) {
                    Toast.makeText(SignUp_Screen.this, "You, Successfully Create New Account", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SignUp_Screen.this, SignIn_Screen.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(SignUp_Screen.this, response.message() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(SignUp_Screen.this, t.getMessage() + "", Toast.LENGTH_SHORT).show();

            }
        });
    }
}