package com.idbc.ngchiseng.timepieceapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
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

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener,
        ProductsFragment.OnProductsFragmentInteractionListener,
        ServicesFragment.OnServicesFragmentInteractionListener,
        DonationsFragment.OnDonationsFragmentInteractionListener {

    /*  This will declare the global variables that are going to use in the main Activity */
    private Boolean executed;
    private RelativeLayout mainProducts,mainServices,mainDonations;
    private ImageView shoppingBag;
    private TextView productsAnnounces,servicesAnnounces,donationsAnnounces;

    /*  This will declare the fragment manager variable for the fragments invocations. */
    FragmentManager mFragmentManager;

    /*  Method that will onCreate the main activity, link its component, and implements the
    onClickListener for receive the click request. In this case also will:
    - Implement the App's SplashTheme function.
    - Verify if is the first time that the user start the App for to show the Initial Tour.
    - Verify if the user is logged.
    - Implement and render the main screen.
    - Call the activities and fragments corresponding(in the navigation menu and other).
        @date[07/06/2017]
        @author[ChiSeng Ng]
        @param [Bundle] savedInstanceState InstanceState of the activity.
        @return [void]
     */
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
                .setDefaultFontPath("fonts/MyriadPro-SemiExt.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        /*  This will initialize the fragment manager */
        mFragmentManager = getSupportFragmentManager();

        /*  This will get the executed preferences value(true or false) */
        executed = TimePieceSharedPreferences.getExecuted(MainActivity.this);

        /*  This will verify if is the first time that the user start the App for show the Initial
        Tour.
         */
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
            /*  This will verify if the user is logged then start the main screen, and if him not
            then start the loginActivity.
             */
            if (AccessToken.getCurrentAccessToken() != null) {
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
            } else {
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //finish();
            }
        }
    }

    /*  Method that will implements the onBackPressed events(when pressed the default back button on
    the mobile) for the main activity. In this case, if the navigation's menu is opened will close
    it and if navigation's menu is closed will close the App or will return to the previous activity
    existing.
        @date[07/06/2017]
        @author[ChiSeng Ng]
        @param [none]
        @return [void]
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*  Method that will call the navigation's menu fragments and events corresponding.
        @date[07/06/2017]
        @author[ChiSeng Ng]
        @param [MenuItem] item Item corresponding on the navigation's menu.
        @return [boolean] Always is true in this case.
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            //finish();
        } else if (id == R.id.nav_profile) {
            fragmentTransaction.replace(R.id.fragment_container,new ProfileFragment()).addToBackStack(null).commit();

        } else if (id == R.id.nav_publications) {
            fragmentTransaction.replace(R.id.fragment_container,new PublicationFragment()).addToBackStack(null).commit();

        } else if (id == R.id.nav_shopping) {
            fragmentTransaction.replace(R.id.fragment_container,new PurchasesFragment()).addToBackStack(null).commit();

        } else if (id == R.id.nav_sales) {

        } else if (id == R.id.nav_donations) {

        } else if (id == R.id.nav_log_out) {
            LoginManager.getInstance().logOut();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
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

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        switch (view.getId()){

            case (R.id.main_products):
                fragmentTransaction.replace(R.id.fragment_container,new ProductsFragment()).addToBackStack(null).commit();
                break;
            case (R.id.main_services):
                fragmentTransaction.replace(R.id.fragment_container,new ServicesFragment()).addToBackStack(null).commit();
                break;
            case (R.id.main_donations):
                fragmentTransaction.replace(R.id.fragment_container,new ProductsFragment()).addToBackStack(null).commit();
                break;
            case (R.id.bag_icon):
                Intent intent = new Intent(this, ShoppingBagActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //finish();
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

    /* This block will be used for implement all the interfaces's methods corresponding to each
    fragment in the App.
     */

    /*  Implementation of the method corresponding to the OnProductsFragmentInteractionListener
    interface in the ProductsFragment, that will invoke the detail fragment with the announce and its
    data corresponding.
           @date[05/07/2017]
           @author[ChiSeng Ng]
           @param [Announce] announce Announce with the data that will pass to the DetailFragment.
           @return [void]
   */
    @Override
    public void onProductsFragmentInteraction(Announce announce) {

        /* This will get each value to the Announce objects received */
        int announceImageId = announce.getImage();
        String announcePrice = announce.getAnnouncePriceComplete();
        String announceName = announce.getAnnounceOwner();
        String announceAddress = announce.getAnnounceAddress();
        String announceTitle = announce.getAnnounceTitle();
        String announceDescription = announce.getAnnounceOthers();

        /* This will put each value obtained with the key corresponding, for pass them to the
        DetailFragment through a bundle object.
         */
        Bundle bundle = new Bundle();
        bundle.putInt("ImageId", announceImageId);
        bundle.putString("Price", announcePrice);
        bundle.putString("Name", announceName);
        bundle.putString("Address", announceAddress);
        bundle.putString("Title",announceTitle);
        bundle.putString("Description", announceDescription);
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(bundle);

        /* This will create and execute the manager transaction, that will call and replace the
         actual Fragment with the fragment corresponding.
          */
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, detailFragment).addToBackStack(null).commit();
    }

    /*  Implementation of the method corresponding to the OnServicesFragmentInteractionListener
    interface in the ServicesFragment, that will invoke the detail fragment with the announce and its
    data corresponding.
           @date[06/07/2017]
           @author[ChiSeng Ng]
           @param [Announce] announce Announce with the data that will pass to the DetailFragment.
           @return [void]
   */
    @Override
    public void onServicesFragmentInteraction(Announce announce) {

        /* This will get each value to the Announce objects received */
        int announceImageId = announce.getImage();
        String announcePrice = announce.getAnnouncePriceComplete();
        String announceName = announce.getAnnounceOwner();
        String announceAddress = announce.getAnnounceAddress();
        String announceTitle = announce.getAnnounceTitle();
        String announceDescription = announce.getAnnounceOthers();

        /* This will put each value obtained with the key corresponding, for pass them to the
        DetailFragment through a bundle object.
         */
        Bundle bundle = new Bundle();
        bundle.putInt("ImageId", announceImageId);
        bundle.putString("Price", announcePrice);
        bundle.putString("Name", announceName);
        bundle.putString("Address", announceAddress);
        bundle.putString("Title",announceTitle);
        bundle.putString("Description", announceDescription);
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(bundle);

        /* This will create and execute the manager transaction, that will call and replace the
         actual Fragment with the fragment corresponding.
          */
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, detailFragment).addToBackStack(null).commit();
    }

    /*  Implementation of the method corresponding to the OnDonationsFragmentInteractionListener
    interface in the DonationsFragment, that will invoke the detail fragment with the announce and its
    data corresponding.
           @date[06/07/2017]
           @author[ChiSeng Ng]
           @param [Announce] announce Announce with the data that will pass to the DetailFragment.
           @return [void]
   */
    @Override
    public void onDonationsFragmentInteraction(Announce announce) {

        /* This will get each value to the Announce objects received */
        int announceImageId = announce.getImage();
        String announcePrice = announce.getAnnouncePriceComplete();
        String announceName = announce.getAnnounceOwner();
        String announceAddress = announce.getAnnounceAddress();
        String announceTitle = announce.getAnnounceTitle();
        String announceDescription = announce.getAnnounceOthers();

        /* This will put each value obtained with the key corresponding, for pass them to the
        DetailFragment through a bundle object.
         */
        Bundle bundle = new Bundle();
        bundle.putInt("ImageId", announceImageId);
        bundle.putString("Price", announcePrice);
        bundle.putString("Name", announceName);
        bundle.putString("Address", announceAddress);
        bundle.putString("Title",announceTitle);
        bundle.putString("Description", announceDescription);
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(bundle);

        /* This will create and execute the manager transaction, that will call and replace the
         actual Fragment with the fragment corresponding.
          */
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, detailFragment).addToBackStack(null).commit();
    }
}
