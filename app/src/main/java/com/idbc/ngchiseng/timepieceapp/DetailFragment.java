package com.idbc.ngchiseng.timepieceapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class DetailFragment extends Fragment implements View.OnClickListener {

    /*  This will declare the global variables that are going to use on the fragment. */
    private TextView numberItems;
    private int numberItemsActual = 1;

    /*  Method that will onCreate the fragment, inflate its View, link its component, and will return
    the render to the main Activity.
        @date[05/07/2017]
        @author[ChiSeng Ng]
        @param [LayoutInflater] inflater The layout inflater corresponding of the caller Activity.
        @param [ViewGroup] container The viewGroup that will content the fragment on the layout.
        @param [Bundle] savedInstanceState InstanceState of the MainActivity(contain the data).
        @return [View] The inflated an rendered view that will show its on the screen.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /* Data that received through the bundle corresponding in the MainActivity of each methods´s
        interfaces.
         */
        int imageId = getArguments().getInt("ImageId");
        String price = getArguments().getString("Price");
        String name = getArguments().getString("Name");
        String address = getArguments().getString("Address");
        String title = getArguments().getString("Title");
        String description = getArguments().getString("Description");

        // Inflate the layout for this fragment
        final View detailView = inflater.inflate(R.layout.fragment_detail, container, false);

        /* This will handler the variables with the corresponding layout views, elements or widgets */
        ImageView announceImage =(ImageView) detailView.findViewById(R.id.detail_image);
        TextView announcePrice = (TextView) detailView.findViewById(R.id.detail_price);
        TextView announceName = (TextView) detailView.findViewById(R.id.detail_name);
        TextView announceAddress = (TextView) detailView.findViewById(R.id.detail_address);
        TextView announceTitle = (TextView) detailView.findViewById(R.id.detail_title);
        Button announceButton = (Button) detailView.findViewById(R.id.detail_button);
        TextView announceDescription = (TextView) detailView.findViewById(R.id.detail_description);
        numberItems = (TextView) detailView.findViewById(R.id.detail_num_items);
        ImageView sumBtn = (ImageView) detailView.findViewById(R.id.detail_sum);
        ImageView subtractionBtn = (ImageView) detailView.findViewById(R.id.detail_subtraction);

        /* This will set the date corresponding to show it on the screen */
        announceImage.setImageResource(imageId);
        announcePrice.setText(price);
        announceName.setText(name);
        announceAddress.setText(address);
        announceTitle.setText(title);
        announceDescription.setText(description);
        numberItems.setText(Integer.toString(numberItemsActual));

        announceButton.setOnClickListener(this);
        sumBtn.setOnClickListener(this);
        subtractionBtn.setOnClickListener(this);

        return detailView;
    }

    /*  Method that handler the event handler with the event listener defined corresponding, in this
       case is used for to start the activity corresponding.
           @date[06/07/2017]
           @author[ChiSeng Ng]
           @param [View] view View or widget that represent the button corresponding in this context.
           @return [void]
   */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.detail_sum):
                numberItemsActual = numberItemsActual + 1;
                numberItems.setText(Integer.toString(numberItemsActual));
                break;
            case (R.id.detail_subtraction):
                if (numberItemsActual > 1){
                    numberItemsActual = numberItemsActual - 1;
                    numberItems.setText(Integer.toString(numberItemsActual));
                }
                break;
            default:
                Toast.makeText(view.getContext(), "Click add to Shopping Bag" , Toast.LENGTH_LONG).show();
                break;
        }
    }
}
