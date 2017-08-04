package com.idbc.ngchiseng.timepieceapp;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.swipeactionadapter.SwipeActionAdapter;
import com.wdullaer.swipeactionadapter.SwipeDirection;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ShoppingBagActivity extends AppCompatActivity implements View.OnClickListener {

    /*  This will declare the variables that are going to use on the activity. */
    private ListView listArticles;
    private int totalCost = 0;
    private TextView totalPay, articleNum;
    private SwipeActionAdapter swipeAdapter;

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

        setContentView(R.layout.activity_shopping_bag);

        /* This will manage the toolbar configuration and addressing. */
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(this);

        // Initialized ArrayList of Articles
        final ArrayList<Announce> data = new ArrayList<Announce>();

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
                    articleTitle.setText(((Announce) entry).getTitle());

                    TextView articleSeller = (TextView) view.findViewById(R.id.article_seller);
                    articleSeller.setText(((Announce) entry).getName());

                    TextView articleAddress = (TextView) view.findViewById(R.id.article_address);
                    articleAddress.setText(((Announce) entry).getAddress());

                    TextView articlePrice = (TextView) view.findViewById(R.id.article_price);
                    articlePrice.setText(((Announce) entry).getPriceComplete());

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
                            totalCost = totalCost + ((Announce) entry).getPriceInt();
                            articleNum.setText(((Announce) entry).getDescription());
                            totalPay.setText("$" + totalCost);

                        }
                    });

                    articleNum = (TextView) view.findViewById(R.id.article_num);
                    articleNum.setText(((Announce) entry).getDescription());

                    /* This will handler the subtract button with the xml component corresponding,
                    subtract one quantity to the entry object corresponding and subtract the entry's
                    cost to the total that will have to pay.
                     */
                    ImageView subtractionBtn = (ImageView) view.findViewById(R.id.article_subtraction);
                    subtractionBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (((Announce) entry).getOrQuantity() > 1) {
                                ((Announce) entry).setSubtractOne();
                                articleNum = (TextView) view.findViewById(R.id.article_num);
                                totalCost = totalCost - ((Announce) entry).getPriceInt();
                                articleNum.setText(((Announce) entry).getDescription());
                                totalPay.setText("$" + totalCost);
                            }
                        }
                    });
                }
            }
        };

        /* This will create the SwipeAdapter that is going to give the sibling effect to each item
         of the ListView and will permit delete it updating the total payment value. This function
         is implemented using a SwipeActionAdapter library:
         @reference https://github.com/wdullaer/SwipeActionAdapter
        */
        // Wrap your content in a SwipeActionAdapter
        swipeAdapter = new SwipeActionAdapter(myAdapter);

        // Pass a reference of your ListView to the SwipeActionAdapter
        swipeAdapter.setListView(listArticles);

        // Set the SwipeActionAdapter as the Adapter for your ListView
        listArticles.setAdapter(swipeAdapter);

        // Set backgrounds for the swipe directions
        swipeAdapter.addBackground(SwipeDirection.DIRECTION_NORMAL_LEFT, R.layout.shopping_item_left);
        swipeAdapter.addBackground(SwipeDirection.DIRECTION_FAR_LEFT, R.layout.shopping_item_left_far);


        /* Allows you to set the fraction of the view width that must be swiped before it is counted
        as a far swipe. The float must be between 0 and 1. 0 makes every swipe a far swipe, 1
        effectively disables a far swipe.
        */
        swipeAdapter.setFarSwipeFraction((float) 0.5);

        /* Setting this to true will make the backgrounds static behind the ListView item instead of
        sliding in from the side.
        */
        swipeAdapter.setFixedBackgrounds(true);

        /* This is the interface that will listen the swipe gesture, and is going to work depending
        the configuration of its methods.
         */
        swipeAdapter.setSwipeActionListener(new SwipeActionAdapter.SwipeActionListener() {

            /*  Method that is going to return true if you want this item to be swipeable in this
            direction.
                @date[12/07/2017]
                @author[ChiSeng Ng]
                @param [int] position Position on the SwipeListViewArray.
                @param [SwipeDirection] direction Direction read actually.
                @reference https://github.com/wdullaer/SwipeActionAdapter
                @return [boolean] True if the actual direction is permitted.
            */
            @Override
            public boolean hasActions(int position, SwipeDirection direction) {
                return direction.isLeft();
            }

            /*  Method that is going to return true if you want the item to be dismissed, return
            false if it should stay visible. This method runs on the interface thread so if you want
            to trigger any heavy actions here, put them on an ASyncThread.
                @date[12/07/2017]
                @author[ChiSeng Ng]
                @param [int] position Position on the SwipeListViewArray.
                @param [SwipeDirection] direction Direction read actually.
                @reference https://github.com/wdullaer/SwipeActionAdapter
                @return [boolean] True if the actual direction is permitted.
            */
            @Override
            public boolean shouldDismiss(int position, SwipeDirection direction) {
                // Only dismiss an item when swiping normal left
                return direction == SwipeDirection.DIRECTION_FAR_LEFT;
            }

            /*  Method that triggered when all animations on the swiped items have finished. You
            will receive an array of all swiped items, sorted in descending order with their
            corresponding directions.
                @date[12/07/2017]
                @author[ChiSeng Ng]
                @param [int array] positionList Array that will contain the position of each item.
                @param [SwipeDirection array] directionList Array that will contain the direction of
                each item according to the position of the positionList.
                @reference https://github.com/wdullaer/SwipeActionAdapter
                @return [void]
            */
            @Override
            public void onSwipe(int[] positionList, SwipeDirection[] directionList) {
                for (int i = 0; i < positionList.length; i++) {
                    SwipeDirection direction = directionList[i];
                    int position = positionList[i];

                    /* This will handler que Direction_Far_Left event, obtain the item corresponding,
                    calculate its cost per quantity, subtract that cost of the total cost and update
                    its in the screen, delete the object of the array, and call to the
                    notifyDateSetChanged() method for that the Adapter update the screen.
                    */
                    if (direction == SwipeDirection.DIRECTION_FAR_LEFT) {
                        Announce item = (Announce) swipeAdapter.getItem(position);
                        int articlesCost = item.getOrQuantity()*item.getPriceInt();
                        totalCost = totalCost - articlesCost;
                        totalPay.setText("$" + totalCost);
                        data.remove(position);
                    }
                    swipeAdapter.notifyDataSetChanged();
                }
            }
        });

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
            Intent intent = new Intent(this, PaymentActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            //finish();
        } else {
            super.onBackPressed();
        }
    }

    /*  Method that use the Calligraphy dependence to attach the base context and to can change the
    letter's font corresponding.
           @date[25/07/2017]
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
