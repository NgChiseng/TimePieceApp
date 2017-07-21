package com.idbc.ngchiseng.timepieceapp;

import android.content.Intent;
import android.support.design.widget.TabLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PurchasesFragment extends Fragment implements PurchasesProcessFragment.OnPurchasesProcessFragmentInteractionListener, PurchasesDoneFragment.OnPurchasesDoneFragmentInteractionListener {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View purchasesView = inflater.inflate(R.layout.fragment_purchases, container, false);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of this fragment.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) purchasesView.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) purchasesView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        return purchasesView;
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) { super(fm); }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            switch (position) {
                case 0: return new PurchasesProcessFragment();
                case 1: return new PurchasesDoneFragment();
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
                    return getString(R.string.purchases_process);
                case 1:
                    return getString(R.string.purchases_finished);
            }
            return null;
        }
    }

    /* This block will be used for implement all the interfaces's methods corresponding to each
    nested fragment corresponding to this parent fragment.
     */

    /*  Implementation of the method corresponding to the OnPurchasesProcessFragmentInteractionListener
    interface in the PurchasesProcessFragment, that will invoke the InProcessDetail activity with
    the announce and its data corresponding.
        @date[14/07/2017]
        @author[ChiSeng Ng]
        @param [Announce] announce Announce with the data that will pass to the
        PurchasesInProcessDetailActivity.
        @return [void]
   */
    @Override
    public void onPurchasesProcessFragmentInteraction(Announce announce) {

        /* This will get each value to the Announce objects received */
        int announceImageId = announce.getImage();
        String announcePriceComplete = announce.getPriceComplete();
        String announceName = announce.getName();
        String announceAddress = announce.getAddress();
        String announceTitle = announce.getTitle();
        String announceQuantity = announce.getQuantity();
        String announceTotal = announce.getPriceByQuantity();
        String announceDescription = announce.getDescription();

        /* This will handler the Activity corresponding */
        Intent intent = new Intent(getActivity().getBaseContext(), PurchasesInProcessDetailActivity.class);

        /* This will put each value obtained with the key corresponding, for pass them to the
        PurchasesInProcessDetailActivity through a bundle object.
         */
        intent.putExtra("ImageId", announceImageId);
        intent.putExtra("Price", announcePriceComplete);
        intent.putExtra("Name", announceName);
        intent.putExtra("Address", announceAddress);
        intent.putExtra("Title", announceTitle);
        intent.putExtra("Quantity", announceQuantity);
        intent.putExtra("Total", announceTotal);
        intent.putExtra("Description", announceDescription);

        /* This will will call and execute the activity corresponding */
        getActivity().startActivity(intent);
    }

    /*  Implementation of the method corresponding to the OnPurchasesDoneFragmentInteractionListener
    interface in the PurchasesDoneFragment, that will invoke the DoneDetail activity with
    the announce and its data corresponding.
        @date[21/07/2017]
        @author[ChiSeng Ng]
        @param [Announce] announce Announce with the data that will pass to the
        PurchasesDoneDetailActivity.
        @return [void]
   */
    @Override
    public void onPurchasesDoneFragmentInteraction(Announce announce) {

        /* This will get each value to the Announce objects received */
        int announceImageId = announce.getImage();
        String announcePriceComplete = announce.getPriceComplete();
        String announceName = announce.getName();
        String announceAddress = announce.getAddress();
        String announceTitle = announce.getTitle();
        String announceQuantity = announce.getQuantity();
        String announceTotal = announce.getPriceByQuantity();
        String announcePaymentDate = announce.getFirstDate();
        String announceFinishDate = announce.getSecondDate();
        String announceDescription = announce.getDescription();

        /* This will handler the Activity corresponding */
        Intent intent = new Intent(getActivity().getBaseContext(), PurchasesDoneDetailActivity.class);

        /* This will put each value obtained with the key corresponding, for pass them to the
        PurchasesInProcessDetailActivity through a bundle object.
         */
        intent.putExtra("ImageId", announceImageId);
        intent.putExtra("Price", announcePriceComplete);
        intent.putExtra("Name", announceName);
        intent.putExtra("Address", announceAddress);
        intent.putExtra("Title", announceTitle);
        intent.putExtra("Quantity", announceQuantity);
        intent.putExtra("Total", announceTotal);
        intent.putExtra("PaymentDate", announcePaymentDate);
        intent.putExtra("FinishDate", announceFinishDate);
        intent.putExtra("Description", announceDescription);

        /* This will will call and execute the activity corresponding */
        getActivity().startActivity(intent);
    }
}
