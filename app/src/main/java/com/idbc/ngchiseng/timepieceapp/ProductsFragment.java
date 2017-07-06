package com.idbc.ngchiseng.timepieceapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;



public class ProductsFragment extends Fragment {

    /*  This will declare the variables that are going to use on the fragment. */
    private ListView listAnnounces;

    /* Declaration of the interface that will use to call and pass the detail data to the
    DetailFragment through the MainActivity.
     */
    OnProductsFragmentInteractionListener productsInterface;

    /*  Method that will onCreate the fragment, inflate its View, link its component, and will return
    the render to the main Activity.
        @date[27/06/2017]
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
        final View productsView = inflater.inflate(R.layout.fragment_products, container, false);

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
        listAnnounces = (ListView) productsView.findViewById(R.id.products_list);
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

        /* Declaration and implementation of the item listener that will get the item that was
        clicked and call the products interface method that will be implemented in the MainActivity.
         */
        listAnnounces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Announce selected = (Announce) parent.getItemAtPosition(position);
                productsInterface.onProductsFragmentInteraction(selected);
            }
        });

        return productsView;
    }

    /*  Method that will be called by the system when is associated with the MainActivity, will
    validate the interface called, and will return an error if fail.
        @date[05/07/2017]
        @author[ChiSeng Ng]
        @param [Context] context Context that call it, in this case corresponding to the MainActivity.
        @return [Void]
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            productsInterface = (OnProductsFragmentInteractionListener) getActivity();
        } catch (ClassCastException exception) {
            throw new ClassCastException("Error in retrieving products data. Please try again.");
        }
    }

    /*
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnProductsFragmentInteractionListener {
        void onProductsFragmentInteraction(Announce announce);
    }
}
