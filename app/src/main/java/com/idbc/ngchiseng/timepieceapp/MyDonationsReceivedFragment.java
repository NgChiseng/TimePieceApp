package com.idbc.ngchiseng.timepieceapp;


import android.content.Context;
import android.content.res.Resources;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class MyDonationsReceivedFragment extends Fragment {

    /*  This will declare the variables that are going to use on the fragment. */
    private ListView listTabItems;
    private String text;

    /* Declaration of the interface that will use to call and pass the detail data to the
    MyDonationsReceivedDetailActivity through the MyDonationsFragment.
     */
    OnMyDonationsReceivedFragmentInteractionListener myDonationsReceivedInterface;

    /*  Method that will onCreate the fragment, inflate its View, link its component, and will return
    the render to the main Fragment.
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
        final View myDonationsReceivedView = inflater.inflate(R.layout.fragment_my_donations_received, container, false);

        /* This will obtain the resource for setting the formatting String in each Item view */
        Resources res = getResources();
        final String donationReceived = res.getString(R.string.donation_item_received);
        final String donationRequired = res.getString(R.string.donation_item_required);
        final String receptionDate = res.getString(R.string.reception_date);

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
        data.add(new Announce(R.drawable.first_item, "Clases de cocina para niños de la comunidad la Sierra", "José García", "11-09-2016", "$", "44", "59" , description));

        /*  This will handler the ArrayList of Announces and the data inside its with the screen list
        and components o widgets, using the AnnouncesAdapter.
         */
        listTabItems = (ListView) myDonationsReceivedView.findViewById(R.id.my_donations_received_list);
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
                    text = String.format(donationReceived, ((Announce) entry).getCurrencyPrice());
                    tabItemPrice.setText(text);

                    TextView tabItemFirstDate = (TextView) view.findViewById(R.id.tab_item_first_date);
                    text = String.format(donationRequired, ((Announce) entry).getCurrencyUnit());
                    tabItemFirstDate.setText(text);

                    TextView tabItemSecondDate = (TextView) view.findViewById(R.id.tab_item_second_date);
                    text = String.format(receptionDate, ((Announce) entry).getAddress());
                    tabItemSecondDate.setText(text);
                }
            }
        });

        /* Declaration and implementation of the item listener that will get the item that was
        clicked and call the myDonations interface method that will be implemented in the myDonationsFragment.
         */
        listTabItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Announce selected = (Announce) parent.getItemAtPosition(position);
                myDonationsReceivedInterface.onMyDonationsReceivedFragmentInteraction(selected);
            }
        });

        return myDonationsReceivedView;
    }

    /*  Method that will be called by the system when is associated with the myDonationsFragment, will
    validate the interface called, and will return an error if fail.
        @date[01/08/2017]
        @author[ChiSeng Ng]
        @param [Context] context Context that call it, in this case corresponding to the MainActivity.
        @return [Void]
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            myDonationsReceivedInterface = (OnMyDonationsReceivedFragmentInteractionListener) getParentFragment();
        } catch (ClassCastException exception) {
            throw new ClassCastException("Error in retrieving my donations received data. Please try again.");
        }
    }

    /*
     * This interface must be implemented by parent fragments that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the parent fragment and potentially other fragments contained in that
     * parent.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnMyDonationsReceivedFragmentInteractionListener {
        void onMyDonationsReceivedFragmentInteraction(Announce announce);
    }
}
