package com.idbc.ngchiseng.timepieceapp;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PurchasesDoneFragment extends Fragment {

    /*  This will declare the variables that are going to use on the fragment. */
    private ListView listTabItems;
    private String text;

    /*  Method that will onCreate the fragment, inflate its View, link its component, and will return
    the render to the main Activity.
        @date[13/07/2017]
        @author[ChiSeng Ng]
        @param [LayoutInflater] inflater The layout inflater corresponding of the caller Activity.
        @param [ViewGroup] container The viewGroup that will content the fragment on the layout.
        @param [Bundle] savedInstanceState InstanceState of the MainActivity.
        @return [View] The inflated an rendered view that will show its on the screen.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View purchasesDoneView = inflater.inflate(R.layout.fragment_purchases_done, container, false);

        /* This will obtain the resource for setting the formatting String in each Item view */
        Resources res = getResources();
        final String acquisitionsArg = res.getString(R.string.price_and_acquisitions);
        final String finishDateArg = res.getString(R.string.finish_date);

        // Initialized ArrayList of Announces
        ArrayList<Announce> data = new ArrayList<Announce>();

        /*  This block will obtain and add the date corresponding to each Announce object on the
        ArrayList of Announces.
         */
        String description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras eget " +
                "nunc finibus, vehicula felis ac, pulvinar eros. Sed lobortis eu metus eu tristique. " +
                "Nam nec justo ut velit accumsan laoreet. Fusce ut leo vitae metus porta tempor sed " +
                "venenatis odio. Quisque suscipit ligula a risus pharetra dapibus. Nunc sit amet " +
                "neque odio. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere " +
                "cubilia Curae; Nunc ut vulputate ante, vitae rutrum nisi. Duis sed metus sapien. " +
                "Aenean gravida ac metus accumsan consequat. Ut laoreet, sapien sed molestie tempus, " +
                "lacus justo gravida felis, eget sagittis arcu est non dolor. Aenean vel dignissim " +
                "erat, vitae malesuada nisl.";
        data.add(new Announce(R.drawable.second_item, "El mejor servicio de plomeria", "Francisco Javier Rodriguez", "A domicilio", "$", "126", "hrs", description, "3", "Servicio", "10-07-2017", "13-07-2017"));
        data.add(new Announce(R.drawable.third_item, "Clases de guitarra acustica", "Carloz Lopez", "A domicilio", "$", "100", "hrs", description, "4", "Servicio", "11-07-2010", "27-08-2010"));

        /*  This will handler the ArrayList of Announces and the data inside its with the screen list
        and components o widgets, using the AnnouncesAdapter.
         */
        listTabItems = (ListView) purchasesDoneView.findViewById(R.id.purchases_done_list);
        listTabItems.setAdapter(new AnnouncesAdapter(getContext(), R.layout.tab_item, data) {

            /* This will implement the abstract method onEntry(Implemented in AnnounceAdapter), with
            the respective elements and handlers.
             */
            @Override
            public void onEntry(Object entry, View view) {

                if (entry != null){

                    ImageView tabItemImage = (ImageView) view.findViewById(R.id.tab_item_image);
                    tabItemImage.setImageResource(((Announce) entry).getImage());

                    TextView tabItemTitle = (TextView) view.findViewById(R.id.tab_item_title);
                    tabItemTitle.setText(((Announce) entry).getTitle());

                    TextView tabItemPrice = (TextView) view.findViewById(R.id.tab_item_others);
                    text = String.format(acquisitionsArg, ((Announce) entry).getPriceComplete(), ((Announce) entry).getQuantity());
                    tabItemPrice.setText(text);

                    TextView tabItemType = (TextView) view.findViewById(R.id.tab_item_first_date);
                    tabItemType.setText(((Announce) entry).getType());

                    TextView tabItemPaymentDate = (TextView) view.findViewById(R.id.tab_item_second_date);
                    text = String.format(finishDateArg, ((Announce) entry).getSecondDate());
                    tabItemPaymentDate.setText(text);
                }
            }
        });

        return purchasesDoneView;
    }

}
