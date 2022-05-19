package com.dal4.ecommerce.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dal4.ecommerce.R;
import com.dal4.ecommerce.data_base.Remote.RetrofitClient;
import com.dal4.ecommerce.models.UserModel;
import com.dal4.ecommerce.utils.Constants;

import org.w3c.dom.Text;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Frag_Profile extends Fragment {
    View view;
    private TextView userName, userEmail, userPhone, userIsAdmin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_profile, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }


    private void initViews() {
        userName = view.findViewById(R.id.userName_Profile);
        userEmail = view.findViewById(R.id.userEmail_Profile);
        userPhone = view.findViewById(R.id.userPhone_profile);
        userIsAdmin = view.findViewById(R.id.userIsAdmin_profile);



        getUserProfile();
    }

    private void getUserProfile() {
        Call<UserModel> call = RetrofitClient.getInstance().GetUserProfile(Constants.getUserToken(getActivity()));
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    UserModel userModel = response.body();

                    userName.setText(userModel.getName());
                    userEmail.setText(userModel.getEmail());
                    userPhone.setText(userModel.getPhone());
                    if (userModel.getIsAdmin().equals("true")) {
                        userIsAdmin.setText("You are an admin");
                    } else {
                        userIsAdmin.setText("User");
                    }
                }
                else  {
                    Toast.makeText(getActivity(), response.message().toString(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });

    }

}
