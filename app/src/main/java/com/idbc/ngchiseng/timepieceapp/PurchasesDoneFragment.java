package com.idbc.ngchiseng.timepieceapp;


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


        // Initialized ArrayList of Announces
        ArrayList<Announce> data = new ArrayList<Announce>();

        /*  This block will obtain and add the date corresponding to each Announce object on the
        ArrayList of Announces.
         */
        data.add(new Announce(R.drawable.second_item, "El mejor servicio de plomeria", "Servicio", "Fecha pago: 13-07-2017", "$", "126", "3 hrs"));
        data.add(new Announce(R.drawable.third_item, "Clases de guitarra acustica", "Servicio", "Fecha pago: 11-01-2010", "$", "100", "4 hrs"));

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
                    tabItemTitle.setText(((Announce) entry).getAnnounceTitle());

                    TextView tabItemFirstDate = (TextView) view.findViewById(R.id.tab_item_first_date);
                    tabItemFirstDate.setText(((Announce) entry).getAnnounceOwner());

                    TextView tabItemSecondDate = (TextView) view.findViewById(R.id.tab_item_second_date);
                    tabItemSecondDate.setText(((Announce) entry).getAnnounceAddress());

                    TextView tabItemPrice = (TextView) view.findViewById(R.id.tab_item_others);
                    tabItemPrice.setText(((Announce) entry).getAnnouncePriceComplete());
                }
            }
        });

        return purchasesDoneView;
    }

}
