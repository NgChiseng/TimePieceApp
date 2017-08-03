package com.idbc.ngchiseng.timepieceapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PublicationFragment extends Fragment implements PublicationProductsFragment.OnPublicationProductsFragmentInteractionListener, PublicationServicesFragment.OnPublicationServicesFragmentInteractionListener, PublicationDonationsFragment.OnPublicationDonationsFragmentInteractionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View publicationsView = inflater.inflate(R.layout.fragment_publication, container, false);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of this fragment.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) publicationsView.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) publicationsView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) publicationsView.findViewById(R.id.publications_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Declaration of the alert dialog
                AlertDialog.Builder publicationSelector = new AlertDialog.Builder(getActivity(),R.style.ListAlertDialogStyle);
                publicationSelector.setTitle(R.string.create_question);

                String [] optionsArray = { getString(R.string.main_products), getString(R.string.main_services), getString(R.string.main_donations) };
                // This will handler the behavior corresponding to each option.
                publicationSelector.setItems(optionsArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intentProduct = new Intent(getContext(), CreateProductActivity.class);
                                intentProduct.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intentProduct);
                                //finish();
                                break;
                            case 1:
                                Intent intentService = new Intent(getContext(), CreateServiceActivity.class);
                                intentService.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intentService);
                                //finish();
                                break;
                            case 2:
                                Intent intentDonation = new Intent(getContext(), CreateDonationActivity.class);
                                intentDonation.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intentDonation);
                                //finish();
                                break;
                        }
                    }
                });
                // This will define and handler the cancel option.
                publicationSelector.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                publicationSelector.create().show();
            }
        });

        return publicationsView;
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            switch (position) {
                case 0: return new PublicationProductsFragment();
                case 1: return new PublicationServicesFragment();
                case 2: return new PublicationDonationsFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.main_products);
                case 1:
                    return getString(R.string.main_services);
                case 2:
                    return getString(R.string.main_donations);
            }
            return null;
        }
    }

    /* This block will be used for implement all the interfaces's methods corresponding to each
    nested fragment corresponding to this parent fragment.
     */

    /*  Implementation of the method corresponding to the OnPublicationProductsFragmentInteractionListener
    interface in the PublicationProductsFragment, that will invoke the PublicationProductsEdition activity with
    the announce and its data corresponding.
        @date[02/08/2017]
        @author[ChiSeng Ng]
        @param [Announce] announce Announce with the data that will pass to the
        PublicationProductsActivity.
        @return [void]
   */
    @Override
    public void onPublicationProductsFragmentInteraction(Announce announce) {

        /* This will get each value to the Announce objects received */
        int announceImageId = announce.getImage();
        String announcePrice = announce.getPrice();
        String announceUnit = announce.getUnit();
        String announceAddress = announce.getAddress();
        String announceTitle = announce.getTitle();
        String announceDescription = announce.getDescription();

        /* This will handler the Activity corresponding */
        Intent intent = new Intent(getActivity().getBaseContext(), PublicationProductsEditionActivity.class);

        /* This will put each value obtained with the key corresponding, for pass them to the
        PublicationProductsEditionActivity through a bundle object.
         */
        intent.putExtra("ImageId", announceImageId);
        intent.putExtra("Price", announcePrice);
        intent.putExtra("Unit", announceUnit);
        intent.putExtra("Address", announceAddress);
        intent.putExtra("Title", announceTitle);
        intent.putExtra("Description", announceDescription);

        /* This will will call and execute the activity corresponding */
        getActivity().startActivity(intent);
    }

    /*  Implementation of the method corresponding to the OnPublicationServicesFragmentInteractionListener
    interface in the PublicationServicesFragment, that will invoke the PublicationServicesEdition activity with
    the announce and its data corresponding.
        @date[02/08/2017]
        @author[ChiSeng Ng]
        @param [Announce] announce Announce with the data that will pass to the
        PublicationServicesActivity.
        @return [void]
   */
    @Override
    public void onPublicationServicesFragmentInteraction(Announce announce) {

        /* This will get each value to the Announce objects received */
        int announceImageId = announce.getImage();
        String announcePrice = announce.getPrice();
        String announceUnit = announce.getUnit();
        String announceAddress = announce.getAddress();
        String announceTitle = announce.getTitle();
        String announceDescription = announce.getDescription();

        /* This will handler the Activity corresponding */
        Intent intent = new Intent(getActivity().getBaseContext(), PublicationServicesEditionActivity.class);

        /* This will put each value obtained with the key corresponding, for pass them to the
        PublicationServicesEditionActivity through a bundle object.
         */
        intent.putExtra("ImageId", announceImageId);
        intent.putExtra("Price", announcePrice);
        intent.putExtra("Unit", announceUnit);
        intent.putExtra("Address", announceAddress);
        intent.putExtra("Title", announceTitle);
        intent.putExtra("Description", announceDescription);

        /* This will will call and execute the activity corresponding */
        getActivity().startActivity(intent);
    }

    /*  Implementation of the method corresponding to the OnPublicationDonationsFragmentInteractionListener
    interface in the PublicationDonationsFragment, that will invoke the PublicationDonationsEdition activity with
    the announce and its data corresponding.
        @date[02/08/2017]
        @author[ChiSeng Ng]
        @param [Announce] announce Announce with the data that will pass to the
        PublicationDonationsActivity.
        @return [void]
   */
    @Override
    public void onPublicationDonationsFragmentInteraction(Announce announce) {

        /* This will get each value to the Announce objects received */
        int announceImageId = announce.getImage();
        String announceTitle = announce.getTitle();
        String announceRequired = announce.getUnit();
        String announceCollected = announce.getCurrencyPrice();
        String announceDescription = announce.getDescription();

        /* This will handler the Activity corresponding */
        Intent intent = new Intent(getActivity().getBaseContext(), PublicationDonationsEditionActivity.class);

        /* This will put each value obtained with the key corresponding, for pass them to the
        PublicationServicesEditionActivity through a bundle object.
         */
        intent.putExtra("ImageId", announceImageId);
        intent.putExtra("Required", announceRequired);
        intent.putExtra("Collected", announceCollected);
        intent.putExtra("Title", announceTitle);
        intent.putExtra("Description", announceDescription);

        /* This will will call and execute the activity corresponding */
        getActivity().startActivity(intent);
    }
}
