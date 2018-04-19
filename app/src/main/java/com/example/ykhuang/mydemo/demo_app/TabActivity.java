package com.example.ykhuang.mydemo.demo_app;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.ykhuang.mydemo.R;

public class TabActivity extends AppCompatActivity {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        TabLayout mTablayout = findViewById(R.id.timeline_tablayout);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mTablayout.setupWithViewPager(mViewPager);

        //添加标题和图标
        for(int i = 0;i<mTablayout.getTabCount();i++){
            mTablayout.getTabAt(i).setCustomView(getTabView(i));
        }
        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView textView = tab.getCustomView().findViewById(R.id.tab_text);
                textView.setTextColor(getResources().getColor(R.color.content_color));
                ((ImageView)(tab.getCustomView().findViewById(R.id.tab_icon))).setImageResource(R.drawable.a1);
            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
//                ((TextView)(tab.getCustomView().findViewById(R.id.tab_text))).setTextColor(R.color.colorAccent);
                ((ImageView)(tab.getCustomView().findViewById(R.id.tab_icon))).setImageResource(R.drawable.aaa);
                TextView textView = tab.getCustomView().findViewById(R.id.tab_text);
                textView.setTextColor(getResources().getColor(R.color.colorAccent));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tab, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_tab, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
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
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
//        @Override
//        public CharSequence getPageTitle(int position) {
//            Drawable drawable;
//            String title;
//            switch (position) {
//                case 0:
//                    drawable = ContextCompat.getDrawable(TabActivity.this, R.drawable.a1);
//                    title = "广场";
//                    break;
//                case 1:
//                    drawable = ContextCompat.getDrawable(TabActivity.this, R.drawable.a2);
//                    title = "好友";
//                    break;
//                case 2:
//                    drawable = ContextCompat.getDrawable(TabActivity.this, R.drawable.a3);
//                    title = "我";
//                    break;
//                default:
//                    drawable = ContextCompat.getDrawable(TabActivity.this, R.drawable.a4);
//                    title = "微博";
//                    break;
//            }
//
////            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//            ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
//            SpannableString spannableString = new SpannableString(" " + title);
//            spannableString.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//            return spannableString;
//
//        }
    }


    private View getTabView(int position){
        View view = LayoutInflater.from(this).inflate(R.layout.tab_itme, null);
        TextView txt_title = (TextView) view.findViewById(R.id.tab_text);

        ImageView img_title = (ImageView) view.findViewById(R.id.tab_icon);

            switch (position) {
                case 0:
                    txt_title.setText("广场");
                    img_title.setImageResource(R.drawable.a1);
                    break;
                case 1:
                    txt_title.setText("好友");
                    img_title.setImageResource(R.drawable.aaa);
                    break;
                case 2:
                    txt_title.setText("我");
                    img_title.setImageResource(R.drawable.a1);
                    break;
                default:
                    txt_title.setText("广场");
                    img_title.setImageResource(R.drawable.a1);
                    break;
            }
        return view;

    }
}
