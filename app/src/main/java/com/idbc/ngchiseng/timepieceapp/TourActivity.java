package com.idbc.ngchiseng.timepieceapp;

import android.content.Intent;
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

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TourActivity extends AppCompatActivity implements View.OnClickListener {

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
    /**
     * This will define the elements used in each screen of the tour
     */
    private TextView end;
    private TextView skip;
    private ImageView dotPage0;
    private ImageView dotPage1;
    private ImageView dotPage2;
    private ImageView dotPage3;
    private ImageView[] dots;

    /*  Method that will onCreate the tout activity, link its component, and implements the
    onClickListener for receive the click request.
        @date[07/06/2017]
        @author[ChiSeng Ng]
        @param [Bundle] savedInstanceState InstanceState of the tour activity.
        @return [void]
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        /* --- This will to link the layout elements with this activity for their future use, and
        create the dots array and append in this, all the pages.
         */
        end = (TextView) findViewById(R.id.end);
        skip = (TextView) findViewById(R.id.skip);
        dotPage0 = (ImageView) findViewById(R.id.dot_page0);
        dotPage1 = (ImageView) findViewById(R.id.dot_page1);
        dotPage2 = (ImageView) findViewById(R.id.dot_page2);
        dotPage3 = (ImageView) findViewById(R.id.dot_page3);
        dots = new ImageView[]{dotPage0, dotPage1, dotPage2, dotPage3};

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        /**
         * This will instance the tour's pages listener and define the behavior of each them
         */
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            /*  Method that will be invoked when the current page is scrolled, either as part of a
            programmatically initiated smooth scroll or a user initiated touch scroll.
                @date[07/06/2017]
                @author[ChiSeng Ng]
                @param [int] position Position index of the first page currently being displayed.
            Page position+1 will be visible if positionOffset is nonzero.
                @param [float] positionOffset Value from [0, 1) indicating the offset from the page at
            position.
                @param [int] positionOffsetPixels Value in pixels indicating the offset from position.
                @return [void]
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            /*  Method that will be invoked when each page becomes selected. Animation is not
            necessarily complete. In this case is set the visibility of LogIn Link and the SKIP,
            Join and End buttons.
                @date[07/06/2017]
                @author[ChiSeng Ng]
                @param [int] position Position index of the new selected page.
                @return [void]
             */
            @Override
            public void onPageSelected(int position) {

                /* --- This will mark the button corresponding toque page marked in the scroll bar
                of the screen.
                 */
                for (int i = 0; i <= 3; i++) {
                    dots[i].setImageResource(
                            i == position ? android.R.drawable.radiobutton_on_background :
                                    android.R.drawable.radiobutton_off_background
                    );
                }

                /* --- This will configure the visibility of the fragments elements in each screen
                of the app.
                 */
                skip.setVisibility(position == 3 ? View.INVISIBLE : View.VISIBLE);
                end.setVisibility(position == 3 ? View.VISIBLE : View.INVISIBLE);

            }

            /*  Method called when the scroll state changes. Useful for discovering when the user
            begins dragging, when the pager is automatically settling to the current page, or when
            it is fully stopped/idle.
                @date[07/06/2017]
                @author[ChiSeng Ng]
                @param [int] state The new scroll state.
                @return [void]
             */
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        /* --- This will activate the listener to the START button
         */
        end.setOnClickListener(this);

        /* --- This will activate the listener to the SKIP button
         */
        skip.setOnClickListener(this);

    }


    /*  Method that handler the event handler with the event listener defined corresponding, in this
    case is used for to edit the TimePieceSharePreferences's "executed" preference in "true", and
    start the activity corresponding.
        @date[08/06/2017]
        @author[ChiSeng Ng]
        @param [View] view View or widget that represent the button corresponding in this context.
        @return [void]
    */
    @Override
    public void onClick(View view) {
        TimePieceSharedPreferences.setExecuted(TourActivity.this, true);
        if (view.getId() == R.id.end) {
            Intent i = new Intent(TourActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        } else if (view.getId() == R.id.skip) {
            Intent i = new Intent(TourActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tour, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view used for to change the images and information
     * of each screen in the tour.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /* ---This will declare the variable img that will save each image will show in each fragment
        corresponding and also will declare the tour_imgs array to save the references of the draw
        images.
        */
        private ImageView img;
        private Integer[] tour_imgs = {R.drawable.products, R.drawable.services, R.drawable.donates, R.drawable.rating};
        private TextView loginQuestion;
        private TextView loginLink;
        private Button joinUp;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        /*  Method that search in the tour_imgs array and link the image corresponding in the fragments
        container, for show their.
            @date[23/05/2017]
            @author[ChiSeng Ng]
            @param [LayoutInfater] inflater Receive the .xml context and will instance it, for permit to
            add it into the Views Hierarchy
            @return [void]
        */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tour, container, false);

            loginQuestion = (TextView) rootView.findViewById(R.id.login_question);
            loginLink = (TextView) rootView.findViewById(R.id.login_link);
            joinUp = (Button) rootView.findViewById(R.id.join);

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1){
                loginQuestion.setVisibility(View.VISIBLE);
                loginLink.setVisibility(View.VISIBLE);
                joinUp.setVisibility(View.VISIBLE);

                loginLink.setOnClickListener(this);
                joinUp.setOnClickListener(this);

            } else {
                loginQuestion.setVisibility(View.INVISIBLE);
                loginLink.setVisibility(View.INVISIBLE);
                joinUp.setVisibility(View.INVISIBLE);
            }
            /* ---Declare and link the container widget ImageView, that will contain the background
            image corresponding to show in the tour_imgs array.
             */
            img = (ImageView) rootView.findViewById(R.id.app_img);
            img.setBackgroundResource(tour_imgs[getArguments().getInt(ARG_SECTION_NUMBER) - 1]);

            return rootView;
        }

        @Override
        public void onClick(View view) {
            TimePieceSharedPreferences.setExecuted(getContext(), true);
            if (view.getId() == R.id.join) {
                Intent i = new Intent(getContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            } else if (view.getId() == R.id.login_link) {
                Intent i = new Intent(getContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        }
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
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
                case 3:
                    return "SECTION 4";
            }
            return null;
        }
    }
}
