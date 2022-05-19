package com.dal4.ecommerce.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.dal4.ecommerce.R;
import com.dal4.ecommerce.fragments.Frag_Cart;
import com.dal4.ecommerce.fragments.Frag_Home;
import com.dal4.ecommerce.fragments.Frag_Profile;
import com.dal4.ecommerce.models.Products_Model;
import com.dal4.ecommerce.models.Products_Model_Detail;
import com.dal4.ecommerce.utils.Communicator;
import com.dal4.ecommerce.utils.Connector;
import com.dal4.ecommerce.utils.Constants;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Frag_Container extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Connector {
    //Views
    @BindView(R.id.Btn_menu)
    Button BtnMenu;
    @BindView(R.id.Btn_menu_layout)
    FrameLayout BtnMenuLayout;
    @BindView(R.id.Btn_Cart_FragContainer)
    Button BtnCartFragContainer;
    @BindView(R.id.badge_FragContainer)
     NotificationBadge badgeFragContainer;
    @BindView(R.id.Frame_Btn_Cart_FragContainer)
    FrameLayout FrameBtnCartFragContainer;
    @BindView(R.id.your_placeholder)
    FrameLayout yourPlaceholder;
    @BindView(R.id.bottom_navigation)
     AHBottomNavigation bottomNavigation;
    @BindView(R.id.navigationView)
     NavigationView navigationView;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.BtnSearch_FragContainer)
    Button Btn_Search;
    @BindView(R.id.BtnSearch_Frame_FragContainer)
    FrameLayout SearchFrame;

    //Var
    private Frag_Cart frag_cart;
    static List<Products_Model_Detail> UserCartProducts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag_container);
        ButterKnife.bind(this);

        initViews();
        intiNav();
    }

    private void initViews() {
        frag_cart = (Frag_Cart) getSupportFragmentManager().findFragmentById(R.id.Frag_Cart);

        AddItemToBottomNav(bottomNavigation);

        SetUserCartNumberToBadgeAndBottomNav(getApplicationContext());
        BtnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });
        Btn_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Frag_Container.this, Search_Screen.class));
            }
        });

        bottomNavigation.setCurrentItem(1);
        FrameBtnCartFragContainer.setVisibility(View.VISIBLE);

        BtnCartFragContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrameBtnCartFragContainer.setVisibility(View.GONE);
                SearchFrame.setVisibility(View.GONE);

                getSupportFragmentManager().beginTransaction().replace(R.id.your_placeholder, new Frag_Cart()).commit();
                bottomNavigation.setCurrentItem(2);


            }
        });

        navigationView.setNavigationItemSelectedListener(this);

        // Manage titles
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);
        bottomNavigation.setCurrentItem(1);
        bottomNavigation.setColored(true);
        bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#F63D2B"));
        bottomNavigation.setItemDisableColor(Color.parseColor("#3A000000"));
        bottomNavigation.setBackgroundColor(getResources().getColor(R.color.MainColor));
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                Fragment selected_Frag = null;
                switch (position) {
                    case 0:
                        selected_Frag = new Frag_Profile();
                        FrameBtnCartFragContainer.setVisibility(View.VISIBLE);
                        SetUserCartNumberToBadgeAndBottomNav(getApplicationContext());

                        break;
                    case 1:
                        selected_Frag = new Frag_Home();
                        FrameBtnCartFragContainer.setVisibility(View.VISIBLE);
                        SetUserCartNumberToBadgeAndBottomNav(getApplicationContext());

                        break;

                    case 2:
                        selected_Frag = new Frag_Cart();
                        FrameBtnCartFragContainer.setVisibility(View.GONE);
                        SetUserCartNumberToBadgeAndBottomNav(getApplicationContext());

                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.your_placeholder, selected_Frag).commit();
                return true;
            }
        });

    }

    private void AddItemToBottomNav(AHBottomNavigation bottomNavigation) {
        // Add items

        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Home", R.drawable.icon_home, R.color.MainColor);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Profile", R.drawable.icon_name, R.color.MainColor);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Cart", R.drawable.icon_cart, R.color.MainColor);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
    }

    private void intiNav() {
        int[] tabColors = getApplicationContext().getResources().getIntArray(R.array.tab_colors);
        AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        AHBottomNavigationAdapter navigationAdapter = new AHBottomNavigationAdapter(this, R.menu.menu_nav);
        navigationAdapter.setupWithBottomNavigation(bottomNavigation, tabColors);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportFragmentManager().beginTransaction().replace(R.id.your_placeholder, new Frag_Home()).commit();
        //GetListCart
//        new GetCartList(UserCartProducts, getApplicationContext()).execute();
        SetUserCartNumberToBadgeAndBottomNav(getApplicationContext());

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.your_placeholder, new Frag_Profile()).commit();
                drawerLayout.close();
                SetUserCartNumberToBadgeAndBottomNav(getApplicationContext());
                bottomNavigation.setCurrentItem(0);
                break;

            case R.id.nav_logout:
                startActivity(new Intent(Frag_Container.this, First_Screen.class));
                Constants.RemoveSharedPreData(Frag_Container.this, "UserData");
                Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                finish();
                break;


        }

        return true;
    }

    public  void SetUserCartNumberToBadgeAndBottomNav(Context context) {
        int CurrentCartSize = Constants.getCartSize(context);
        bottomNavigation.setNotification(CurrentCartSize, 2);
        badgeFragContainer.setNumber(CurrentCartSize);
    }

    @Override
    public void GetListProduct(List<Products_Model_Detail> products_model_details) {
        UserCartProducts = products_model_details;
        SetUserCartNumberToBadgeAndBottomNav(getApplicationContext());
    }

    public static List<Products_Model_Detail> getUserCartProducts() {
        return UserCartProducts;
    }
}