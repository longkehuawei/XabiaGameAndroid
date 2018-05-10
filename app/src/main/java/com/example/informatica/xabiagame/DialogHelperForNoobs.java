package com.example.informatica.xabiagame;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Informatica on 18/03/2017.
 */

public class DialogHelperForNoobs extends AppCompatActivity {

    String personName;
    String personGivenName;
    String personFamilyName;
    String personEmail;
    String personId;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    FloatingActionButton fabAtras;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_dialog_helper_for_noobs);


            //Datos Usuario
            personName = getIntent().getStringExtra("personName");
            personGivenName = getIntent().getStringExtra("personGivenName");
            personFamilyName = getIntent().getStringExtra("personFamilyName");
            personEmail = getIntent().getStringExtra("personEmail");
            personId = getIntent().getStringExtra("personId");

            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

            // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) findViewById(R.id.ViewPager);
            mViewPager.setAdapter(mSectionsPagerAdapter);

            fabAtras = (FloatingActionButton)findViewById(R.id.fabButAtrasHelper);
            fabAtras.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(DialogHelperForNoobs.this, InicioJuegoActivity.class);
                    i.putExtra("personName", personName);
                    i.putExtra("personGivenName", personGivenName);
                    i.putExtra("personFamilyName", personFamilyName);
                    i.putExtra("personEmail", personEmail);
                    i.putExtra("personId", personId);

                    startActivity(i);
                    finish();
                }
            });


        }

        /**
         * A placeholder fragment containing a simple view.
         */
        public static class PlaceholderFragment extends Fragment {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private static final String ARG_SECTION_NUMBER = "section_number";

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

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                View rootView = inflater.inflate(R.layout.fragment_view_pager_noobs, container, false);
                return rootView;
            }
        }

        public static class PlaceholderFragment2 extends Fragment {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private static final String ARG_SECTION_NUMBER = "section_number";

            public PlaceholderFragment2() {
            }

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            public static PlaceholderFragment2 newInstance(int sectionNumber) {
                PlaceholderFragment2 fragment2 = new PlaceholderFragment2();
                Bundle args = new Bundle();
                args.putInt(ARG_SECTION_NUMBER, sectionNumber);
                fragment2.setArguments(args);
                return fragment2;
            }

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                View rootView = inflater.inflate(R.layout.fragment_view_pager_noobs2, container, false);

                return rootView;
            }
        }

    public static class PlaceholderFragment3 extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment3() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment3 newInstance(int sectionNumber) {
            PlaceholderFragment3 fragment2 = new PlaceholderFragment3();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment2.setArguments(args);
            return fragment2;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_view_pager_noobs3, container, false);

            return rootView;
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

                if(position == 0){
                    return PlaceholderFragment.newInstance(position + 1);
                }else if (position == 1){
                    return PlaceholderFragment2.newInstance(position + 1);
                }else {
                    return PlaceholderFragment3.newInstance(position + 1);
                }


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
                        return "Ayuda 1";
                    case 1:
                        return "Ayuda 2";
                    case 2:
                        return "Ayuda 3";
                }
                return null;
            }
        }
    }

