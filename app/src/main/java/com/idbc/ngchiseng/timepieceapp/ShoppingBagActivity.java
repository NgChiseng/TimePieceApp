package com.idbc.ngchiseng.timepieceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShoppingBagActivity extends AppCompatActivity implements View.OnClickListener {

    /*  This will declare the variables that are going to use on the activity. */
    private ListView listArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_bag);

        /* This will manage the toolbar configuration and addressing. */
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(this);

        // Initialized ArrayList of Articles
        ArrayList<Announce> data = new ArrayList<Announce>();

         /*  This block will obtain and add the date corresponding to each Announce object on the
        ArrayList of Announces.
         */
        data.add(new Announce(R.drawable.first_item, "Clases de Cocina", "Carlos Lopez", "Altamira,Caracas", "$25/Persona" ));
        data.add(new Announce(R.drawable.fourth_item, "Zapatos punta negra", "José García", "Coordinar envío con el vendedor", "$44/Unidad" ));
        data.add(new Announce(R.drawable.second_item, "El mejor servicio de plomeria", "Francisco Javier Rodriguez", "A domicilio", "$42/hr" ));
        data.add(new Announce(R.drawable.third_item, "Clases de guitarra acustica", "Carlos Lopez", "A domicilio", "$25/hr" ));

        /*  This will handler the ArrayList of Announces and the data inside its with the screen list
        and components o widgets, using the AnnouncesAdapter.
         */
        listArticles = (ListView) findViewById(R.id.shopping_list);
        listArticles.setAdapter(new AnnouncesAdapter(this, R.layout.article, data) {

            /* This will implement the abstract method onEntry(Implemented in AnnounceAdapter), with
            the respective elements and handlers.
             */
            @Override
            public void onEntry(Object entry, View view) {

                if (entry != null){

                    ImageView articleImage = (ImageView) view.findViewById(R.id.article_image);
                    articleImage.setImageResource(((Announce) entry).getImage());

                    TextView articleTitle = (TextView) view.findViewById(R.id.article_title);
                    articleTitle.setText(((Announce) entry).getAnnounceTitle());

                    TextView articleSeller = (TextView) view.findViewById(R.id.article_seller);
                    articleSeller.setText(((Announce) entry).getAnnounceOwner());

                    TextView articleAddress = (TextView) view.findViewById(R.id.article_address);
                    articleAddress.setText(((Announce) entry).getAnnounceAddress());

                    TextView articlePrice = (TextView) view.findViewById(R.id.article_price);
                    articlePrice.setText(((Announce) entry).getAnnouncePrice());
                }
            }
        });

        Button shoppingBagPayBtn = (Button) findViewById(R.id.shopping_bag_btn);
        shoppingBagPayBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.shopping_bag_btn){
            Toast.makeText(v.getContext(), "Click Shopping Bag Pay" , Toast.LENGTH_LONG).show();
        } else {
            super.onBackPressed();
        }
    }
}
