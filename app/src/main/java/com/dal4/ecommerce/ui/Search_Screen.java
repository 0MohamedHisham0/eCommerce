package com.dal4.ecommerce.ui;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dal4.ecommerce.R;
import com.dal4.ecommerce.adapters.Adapter_Search;
import com.dal4.ecommerce.data_base.Remote.RetrofitClient;
import com.dal4.ecommerce.models.Products_Model;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Search_Screen extends AppCompatActivity {

    private static final String TAG = "ss";
    private EditText ET_Search;
    private Button Btn_Back;
    private RecyclerView RV_Search;
    private SpinKitView ProBar;
    private ImageView NoSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__screen);

        initViews();
    }

    private void initViews() {
        ET_Search = findViewById(R.id.ET_Search);
        RV_Search = findViewById(R.id.RV_Search);
        ProBar = findViewById(R.id.progress_Search);
        Btn_Back = findViewById(R.id.Btn_back_Search);
        NoSearch = findViewById(R.id.TextNoSearch);

        Btn_Back.setOnClickListener(v -> onBackPressed());

        Observer observer = new Observer() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Object o) {
                SearchProduct(o.toString());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };


        RxSearchObservable.fromView(ET_Search)
                .debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String text) {
                        if (text.isEmpty()) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                })
                .subscribe(observer);

    }


    public void SearchProduct(String keyword) {
        ProBar.setVisibility(View.VISIBLE);

        Call<Products_Model> call = RetrofitClient.getInstance().GetProductByName(keyword);
        call.enqueue(new Callback<Products_Model>() {
            @Override
            public void onResponse(Call<Products_Model> call, Response<Products_Model> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    Products_Model list = response.body();
                    if (list != null) {
                        if (list.getProducts().size() == 0) {
                            NoSearch.setVisibility(View.VISIBLE);
                            ProBar.setVisibility(View.GONE);
                            RV_Search.setVisibility(View.GONE);
                        } else {
                            RV_Search.setVisibility(View.VISIBLE);
                            NoSearch.setVisibility(View.GONE);
                            ProBar.setVisibility(View.GONE);
                            RV_Search.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            RV_Search.setAdapter(new Adapter_Search(list.getProducts(), getApplicationContext()));
                        }
                    } else {
                        Toast.makeText(Search_Screen.this, "There is no result for your Search", Toast.LENGTH_SHORT).show();
                        ProBar.setVisibility(View.GONE);

                    }
                } else
                    Toast.makeText(Search_Screen.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                ProBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<Products_Model> call, Throwable t) {
                Toast.makeText(Search_Screen.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                ProBar.setVisibility(View.GONE);

            }
        });
    }

    public static class RxSearchObservable {

        public static Observable<String> fromView(EditText editText) {

            final PublishSubject<String> subject = PublishSubject.create();

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() != 0)
                        subject.onNext(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            return subject;
        }
    }

}