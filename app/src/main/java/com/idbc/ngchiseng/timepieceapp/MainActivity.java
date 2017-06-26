package com.idbc.ngchiseng.timepieceapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Boolean executed;
    private RelativeLayout mainProducts,mainServices,mainDonations;
    private ImageView shoppingBag;
    private TextView productsAnnounces,servicesAnnounces,donationsAnnounces;

    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*  Set the original theme style after used the SplashTheme style defined in the Manifest for
        the MainActivity.
         */
        setTheme(R.style.AppTheme);

         /*
        This will define the fonts calling CalligraphyConfig() method to change the letter font
        defined in the assets/fonts/ directory.
         */
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("MyriadPro-SemiExt.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        mFragmentManager = getSupportFragmentManager();

        executed = TimePieceSharedPreferences.getExecuted(MainActivity.this);
        if (!executed){
            /* --- This will create the intent to pass information of the context to the TourActivity,
            close the old activities on the Activities stack, put the TourActivity in top of the
            stack after the its start, and finally call onDestroy() the present activity.
             */
            Intent i = new Intent(this, TourActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        } else {
            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            /* This will handler the elements of the Main screen with the variables corresponding,
            that will use later.
             */
            mainProducts = (RelativeLayout) findViewById(R.id.main_products);
            mainServices = (RelativeLayout) findViewById(R.id.main_services);
            mainDonations = (RelativeLayout) findViewById(R.id.main_donations);
            shoppingBag = (ImageView) findViewById(R.id.bag_icon);
            productsAnnounces = (TextView) findViewById(R.id.main_products_announces);
            servicesAnnounces = (TextView) findViewById(R.id.main_services_announces);
            donationsAnnounces = (TextView) findViewById(R.id.main_donations_announces);

            /* This will put the value corresponding to the number of announce that will result of
            the query on the API.
             */
            Resources res = getResources();
            String text = String.format(res.getString(R.string.main_announces), 0);
            productsAnnounces.setText(text);
            text = String.format(res.getString(R.string.main_announces), 4);
            servicesAnnounces.setText(text);
            text = String.format(res.getString(R.string.main_announces), 1);
            donationsAnnounces.setText(text);

            mainProducts.setOnClickListener(this);
            mainServices.setOnClickListener(this);
            mainDonations.setOnClickListener(this);
            shoppingBag.setOnClickListener(this);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_publications) {

        } else if (id == R.id.nav_shopping) {

        } else if (id == R.id.nav_sales) {

        } else if (id == R.id.nav_donations) {

        } else if (id == R.id.nav_log_out) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*  Method that handler the event handler with the event listener defined corresponding, in this
       case is used for to start the activity corresponding.
           @date[19/06/2017]
           @author[ChiSeng Ng]
           @param [View] view View or widget that represent the button corresponding in this context.
           @return [void]
   */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case (R.id.main_products):
                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,new ProductsFragment()).commit();
                break;
            case (R.id.main_services):
                FragmentTransaction fragmentTransactions = mFragmentManager.beginTransaction();
                fragmentTransactions.replace(R.id.fragment_container,new ServicesFragment()).commit();
                break;
            case (R.id.main_donations):
                Toast.makeText(view.getContext(), "Click Donations" , Toast.LENGTH_LONG).show();
                break;
            case (R.id.bag_icon):
                Toast.makeText(view.getContext(), "Click Shopping Bag" , Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

    /*  Method that use the Calligraphy dependence to attach the base context and to can change the
    letter's font corresponding.
           @date[13/06/2017]
           @reference [https://github.com/chrisjenx/Calligraphy/]
           @author[ChiSeng Ng]
           @param [Context] newBase Base Context of the Parent Activity.
           @return [void]
   */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
