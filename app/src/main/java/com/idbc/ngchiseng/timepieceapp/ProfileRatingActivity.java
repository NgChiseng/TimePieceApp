package com.idbc.ngchiseng.timepieceapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProfileRatingActivity extends AppCompatActivity implements View.OnClickListener {

    /* This will define the variables that will use in the creation of the product */
    private ListView listAnnounces;
    private TextView profileName, profileEmail, profilePhone, profileAddress;
    private ImageView profileImage, profileEdit;
    private String text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        This will define the fonts calling CalligraphyConfig() method to change the letter font
        defined in the assets/fonts/ directory.
         */
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/MyriadPro-SemiExt.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        setContentView(R.layout.activity_profile_rating);

        Resources res = getResources();
        final String operation = res.getString(R.string.profile_rating_operation);

        /* This will manage the toolbar configuration and addressing. */
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(this);

        /* This will handler the components corresponding of the layout with the variables defined. */
        profileName = (TextView) findViewById(R.id.profile_name);
        profileEmail = (TextView) findViewById(R.id.profile_email);
        profilePhone = (TextView) findViewById(R.id.profile_phone);
        profileAddress = (TextView) findViewById(R.id.profile_address);
        profileImage = (ImageView) findViewById(R.id.profile_image);
        profileEdit = (ImageView) findViewById(R.id.profile_edit);

        // Initialized ArrayList of Announces
        ArrayList<Announce> data = new ArrayList<Announce>();

        /*  This block will obtain and add the date corresponding to each Announce object on the
        ArrayList of Announces.
         */
        data.add(new Announce(R.drawable.circulo_1, "Clases de Cocina", "creó", "1", "12", "MAR", "2017"));
        data.add(new Announce(R.drawable.circulo_1, "Manejo de Redes Sociales", "creó", "1", "12", "MAR", "2017"));
        data.add(new Announce(R.drawable.circulo_3, "Clases de Guitarra", "compró", "1", "12", "MAR", "2017"));
        data.add(new Announce(R.drawable.circulo_3, "Zapatos Artesanales", "compró", "1", "12", "MAR", "2017"));
        data.add(new Announce(R.drawable.circulo_2, "Medicinas para niños", "compró", "2", "12", "MAR", "2017"));

        /*  This will handler the ArrayList of Announces and the data inside its with the screen list
        and components o widgets, using the AnnouncesAdapter.
         */
        listAnnounces = (ListView) findViewById(R.id.profile_rating_list);
        listAnnounces.setAdapter(new AnnouncesAdapter(this, R.layout.profile_rating_item, data) {

            /* This will implement the abstract method onEntry(Implemented in AnnounceAdapter), with
            the respective elements and handlers.
             */
            @Override
            public void onEntry(Object entry, View view) {

                if (entry != null){

                    ImageView announceImage = (ImageView) view.findViewById(R.id.profile_rating_item_image);
                    announceImage.setImageResource(((Announce) entry).getImage());

                    TextView announceOperation = (TextView) view.findViewById(R.id.profile_rating_item_operation);
                    text = String.format(operation, ((Announce) entry).getName(), ((Announce) entry).getTitle());
                    announceOperation.setText(text);

                    TextView announcePoint = (TextView) view.findViewById(R.id.profile_rating_item_point);
                    announcePoint.setText(((Announce) entry).getPoint());

                    TextView announceDay = (TextView) view.findViewById(R.id.profile_rating_item_day);
                    announceDay.setText(((Announce) entry).getCurrency());

                    TextView announceMonth = (TextView) view.findViewById(R.id.profile_rating_item_month);
                    announceMonth.setText(((Announce) entry).getPrice());

                    TextView announceYear = (TextView) view.findViewById(R.id.profile_rating_item_year);
                    announceYear.setText(((Announce) entry).getUnit());
                }
            }
        });

        profileEdit.setOnClickListener(this);
    }

    /*  Method that handler the event handler with the event listener defined corresponding, in this
      case is used for to start the activity corresponding.
          @date[06/08/2017]
          @author[ChiSeng Ng]
          @param [View] view View or widget that represent the button corresponding in this context.
          @return [void]
  */
    @Override
    public void onClick(View v) {

        switch(v.getId()) {

            case R.id.profile_edit:
                Intent intent = new Intent(this, ProfileEditActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //finish();
                break;
            default:
                super.onBackPressed();
                break;
        }
    }

    /*  Method that use the Calligraphy dependence to attach the base context and to can change the
    letter's font corresponding.
           @date[06/08/2017]
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
