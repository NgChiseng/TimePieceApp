package com.idbc.ngchiseng.timepieceapp;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

public class MyDonationsFragment extends Fragment implements MyDonationsReceivedFragment.OnMyDonationsReceivedFragmentInteractionListener, MyDonationsDoneFragment.OnMyDonationsDoneFragmentInteractionListener {

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
        final View myDonationsView = inflater.inflate(R.layout.fragment_my_donations, container, false);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) myDonationsView.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) myDonationsView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        return myDonationsView;
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
                case 0: return new MyDonationsReceivedFragment();
                case 1: return new MyDonationsDoneFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.donations_received);
                case 1:
                    return getString(R.string.donations_done);
            }
            return null;
        }
    }

    /* This block will be used for implement all the interfaces's methods corresponding to each
    nested fragment corresponding to this parent fragment.
     */

    /*  Implementation of the method corresponding to the OnMyDonationsReceivedFragmentInteractionListener
    interface in the MyDonationsReceivedFragment, that will invoke the MyDonationsReceivedDetail activity with
    the announce and its data corresponding.
        @date[01/08/2017]
        @author[ChiSeng Ng]
        @param [Announce] announce Announce with the data that will pass to the
        MyDonationsReceivedDetailActivity.
        @return [void]
   */
    @Override
    public void onMyDonationsReceivedFragmentInteraction(Announce announce) {

        /* This will get each value to the Announce objects received */
        int announceImageId = announce.getImage();
        String announceReceivedAmount = announce.getCurrencyPrice();
        String announceName = announce.getName();
        String announceTitle = announce.getTitle();
        String announceRequired = announce.getCurrencyUnit();
        String announceReceptionDate = announce.getAddress();
        String announceDescription = announce.getDescription();

        /* This will handler the Activity corresponding */
        Intent intent = new Intent(getActivity().getBaseContext(), MyDonationsReceivedDetailActivity.class);

        /* This will put each value obtained with the key corresponding, for pass them to the
        MyDonationsReceivedDetailActivity through a bundle object.
         */
        intent.putExtra("ImageId", announceImageId);
        intent.putExtra("Received", announceReceivedAmount);
        intent.putExtra("Name", announceName);
        intent.putExtra("Title", announceTitle);
        intent.putExtra("Required", announceRequired);
        intent.putExtra("ReceptionDate", announceReceptionDate);
        intent.putExtra("Description", announceDescription);

        /* This will will call and execute the activity corresponding */
        getActivity().startActivity(intent);
    }

    /*  Implementation of the method corresponding to the OnMyDonationsDoneFragmentInteractionListener
    interface in the MyDonationsDoneFragment, that will invoke the MyDonationsDoneDetail activity with
    the announce and its data corresponding.
        @date[01/08/2017]
        @author[ChiSeng Ng]
        @param [Announce] announce Announce with the data that will pass to the
        MyDonationsDoneDetailActivity.
        @return [void]
   */
    @Override
    public void onMyDonationsDoneFragmentInteraction(Announce announce) {

        /* This will get each value to the Announce objects received */
        int announceImageId = announce.getImage();
        String announceDoneAmount = announce.getCurrencyPrice();
        String announceName = announce.getName();
        String announceTitle = announce.getTitle();
        String announceRequired = announce.getCurrencyUnit();
        String announceDonationDate = announce.getAddress();
        String announceDescription = announce.getDescription();

        /* This will handler the Activity corresponding */
        Intent intent = new Intent(getActivity().getBaseContext(), MyDonationsDoneDetailActivity.class);

        /* This will put each value obtained with the key corresponding, for pass them to the
        MyDonationsDoneDetailActivity through a bundle object.
         */
        intent.putExtra("ImageId", announceImageId);
        intent.putExtra("Done", announceDoneAmount);
        intent.putExtra("Name", announceName);
        intent.putExtra("Title", announceTitle);
        intent.putExtra("Required", announceRequired);
        intent.putExtra("DonationDate", announceDonationDate);
        intent.putExtra("Description", announceDescription);

        /* This will will call and execute the activity corresponding */
        getActivity().startActivity(intent);
    }
}
