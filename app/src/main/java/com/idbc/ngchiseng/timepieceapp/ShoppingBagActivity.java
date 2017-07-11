package com.idbc.ngchiseng.timepieceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.swipeactionadapter.SwipeActionAdapter;

import java.util.ArrayList;

public class ShoppingBagActivity extends AppCompatActivity implements View.OnClickListener {

    /*  This will declare the variables that are going to use on the activity. */
    private ListView listArticles;
    private int totalCost = 0;
    private TextView totalPay, articleNum;

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
        data.add(new Announce(R.drawable.first_item, "Clases de Cocina", "Carlos Lopez", "Altamira,Caracas", "$", "25", "Persona", "1" ));
        data.add(new Announce(R.drawable.fourth_item, "Zapatos punta negra", "José García", "Coordinar envío con el vendedor", "$", "44", "Unidad", "1" ));
        data.add(new Announce(R.drawable.second_item, "El mejor servicio de plomeria", "Francisco Javier Rodriguez", "A domicilio", "$", "42", "hr", "2" ));
        data.add(new Announce(R.drawable.third_item, "Clases de guitarra acustica", "Carlos Lopez", "A domicilio", "$", "25", "hr", "1" ));
        totalCost = 25 + 44 + (42*2) + 25;

        /*  This will handler the ArrayList of Announces and the data inside its with the screen list
        and components o widgets, using the AnnouncesAdapter.
         */
        listArticles = (ListView) findViewById(R.id.shopping_list);

        AnnouncesAdapter myAdapter = new AnnouncesAdapter(this, R.layout.article, data) {

            /* This will implement the abstract method onEntry(Implemented in AnnounceAdapter), with
            the respective elements and handlers.
             */
            @Override
            public void onEntry(final Object entry, final View view) {

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
                    articlePrice.setText(((Announce) entry).getAnnouncePriceComplete());

                    /* This will handler the sum button with the xml component corresponding, sum
                    one quantity to the entry object corresponding and sum the entry's cost to the
                    total that will have to pay.
                     */
                    ImageView sumBtn = (ImageView) view.findViewById(R.id.article_sum);
                    sumBtn.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            ((Announce) entry).setSumOne();
                            articleNum = (TextView) view.findViewById(R.id.article_num);
                            totalCost = totalCost + ((Announce) entry).getAnnouncePrice();
                            articleNum.setText(((Announce) entry).getAnnounceOthers());
                            totalPay.setText("$" + totalCost);

                        }
                    });

                    articleNum = (TextView) view.findViewById(R.id.article_num);
                    articleNum.setText(((Announce) entry).getAnnounceOthers());

                    /* This will handler the subtract button with the xml component corresponding,
                    subtract one quantity to the entry object corresponding and subtract the entry's
                    cost to the total that will have to pay.
                     */
                    ImageView subtractionBtn = (ImageView) view.findViewById(R.id.article_subtraction);
                    subtractionBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (((Announce) entry).getAnnounceNumOthers() > 1) {
                                ((Announce) entry).setSubtractOne();
                                articleNum = (TextView) view.findViewById(R.id.article_num);
                                totalCost = totalCost - ((Announce) entry).getAnnouncePrice();
                                articleNum.setText(((Announce) entry).getAnnounceOthers());
                                totalPay.setText("$" + totalCost);
                            }
                        }
                    });
                }
            }
        };

        // Wrap your content in a SwipeActionAdapter
        SwipeActionAdapter swipeAdapter = new SwipeActionAdapter(myAdapter);

        // Pass a reference of your ListView to the SwipeActionAdapter
        swipeAdapter.setListView(listArticles);

        // Set the SwipeActionAdapter as the Adapter for your ListView
        //setListAdapter(swipeAdapter);
        listArticles.setAdapter(swipeAdapter);


        totalPay = (TextView) findViewById(R.id.shopping_total);
        totalPay.setText("$" + totalCost);
        Button shoppingBagPayBtn = (Button) findViewById(R.id.shopping_bag_btn);
        shoppingBagPayBtn.setOnClickListener(this);
    }

    /*  Method that handler the event handler with the event listener defined corresponding, in this
       case is used for to start the activity corresponding.
           @date[11/07/2017]
           @author[ChiSeng Ng]
           @param [View] view View or widget that represent the button corresponding in this context.
           @return [void]
   */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.shopping_bag_btn){
            Toast.makeText(v.getContext(), "Click Shopping Bag Pay" , Toast.LENGTH_LONG).show();
        } else {
            super.onBackPressed();
        }
    }
}
