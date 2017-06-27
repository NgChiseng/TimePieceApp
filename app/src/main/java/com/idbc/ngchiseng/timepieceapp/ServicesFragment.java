package com.idbc.ngchiseng.timepieceapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ServicesFragment extends Fragment {

    /*  This will declare the variables that are going to use on the fragment. */
    private ListView listAnnounces;

    /*  Method that will onCreate the fragment, inflate its View, link its component, and will return
    the render to the main Activity.
        @date[27/06/2017]
        @author[ChiSeng Ng]
        @param [LayoutInflater] inflater The layout inflater corresponding of the caller Activity.
        @param [ViewGroup] container The viewGroup that will content the fragment on the layout.
        @param [Bundle] savedInstanceState InstanceState of the tour activity.
        @return [View] The inflated an rendered view that will show its on the screen.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View servicesView = inflater.inflate(R.layout.fragment_services, container, false);

        // Initialized ArrayList of Announces
        ArrayList<Announce> data = new ArrayList<Announce>();

        /*  This block will obtain and add the date corresponding to each Announce object on the
        ArrayList of Announces.
         */
        data.add(new Announce(R.drawable.first_item, "Clases de Cocina", "Carlos Lopez", "Altamira,Caracas", "$25/Persona" ));
        data.add(new Announce(R.drawable.second_item, "El mejor servicio de plomeria", "Francisco Javier Rodriguez", "A domicilio", "$42/hr" ));
        data.add(new Announce(R.drawable.third_item, "Clases de guitarra acustica", "Carlos Lopez", "A domicilio", "$25/hr" ));

        /*  This will handler the ArrayList of Announces and the data inside its with the screen list
        and components o widgets, using the AnnouncesAdapter.
         */
        listAnnounces = (ListView) servicesView.findViewById(R.id.services_list);
        listAnnounces.setAdapter(new AnnouncesAdapter(getContext(), R.layout.announce, data) {

            /* This will implement the abstract method onEntry(Implemented in AnnounceAdapter), with
            the respective elements and handlers.
             */
            @Override
            public void onEntry(Object entry, View view) {

                if (entry != null){

                    ImageView announceImage = (ImageView) view.findViewById(R.id.announce_image);
                    announceImage.setImageResource(((Announce) entry).getImage());

                    TextView announceTitle = (TextView) view.findViewById(R.id.announce_title);
                    announceTitle.setText(((Announce) entry).getAnnounceTitle());

                    TextView announceOwner = (TextView) view.findViewById(R.id.announce_owner);
                    announceOwner.setText(((Announce) entry).getAnnounceOwner());

                    TextView announceAddress = (TextView) view.findViewById(R.id.announce_address);
                    announceAddress.setText(((Announce) entry).getAnnounceAddress());

                    TextView announcePrice = (TextView) view.findViewById(R.id.announce_price);
                    announcePrice.setText(((Announce) entry).getAnnouncePrice());
                }
            }
        });

        return servicesView;
    }

}