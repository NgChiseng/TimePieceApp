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



public class PublicationDonationsFragment extends Fragment {


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
        final View donationsPublicationView = inflater.inflate(R.layout.fragment_publication_donations, container, false);

        // Initialized ArrayList of Announces
        ArrayList<Announce> data = new ArrayList<Announce>();

        /*  This block will obtain and add the date corresponding to each Announce object on the
        ArrayList of Announces.
         */
        //data.add(new Announce(R.drawable.fourth_item, "Zapatos punta negra", "Fecha creacion: 24-03-2017", "Fecha modificacion: 24-03-2017", "$", "44", "Unidad"));

        /*  This will handler the ArrayList of Announces and the data inside its with the screen list
        and components o widgets, using the AnnouncesAdapter.
         */
        listTabItems = (ListView) donationsPublicationView.findViewById(R.id.publication_donations_list);
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

        return donationsPublicationView;
    }

}
