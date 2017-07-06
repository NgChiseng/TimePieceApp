package com.idbc.ngchiseng.timepieceapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class DetailFragment extends Fragment {

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
        String title = getArguments().getString("Title");

        // Inflate the layout for this fragment
        final View detailView = inflater.inflate(R.layout.fragment_detail, container, false);

        /* This will handler the variables with the corresponding layout views, elements or widgets */
        ImageView announceImage =(ImageView) detailView.findViewById(R.id.detail_image);
        TextView announceTitle = (TextView) detailView.findViewById(R.id.detail_title);

        /* This will set the date corresponding to show it on the screen */
        announceImage.setImageResource(imageId);
        announceTitle.setText(title);

        return detailView;
    }
}
