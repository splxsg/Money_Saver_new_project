package com.blues.money_saver;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TableLayout;

import java.lang.reflect.Method;

/**
 * Created by Blues on 02/09/2016.
 */
public class TabFragment extends Fragment  {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static Method animateToTab;
    public static int int_items ;
    private String monthindex,categoryindex;
    private int currentPage;


    private void changeto(int i)
    {
       // TabLayout.Tab tab = ;
        //tab = tabLayout.getTabAt(i);
       // tab.select();
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View x =  inflater.inflate(R.layout.tab_layout,null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);

        /**
         *Set an Apater for the View Pager
         */
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        PageListener pageListener = new PageListener();
        viewPager.setOnPageChangeListener(pageListener);

        viewPager.setCurrentItem(Utility.getTabindex());
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });



        return x;

    }


    private static class PageListener extends ViewPager.SimpleOnPageChangeListener {
        public void onPageSelected(int position) {
            Utility.setTabindex(position);
        }

        public void onPageScrollStateChanged(int state)
        {
            if(state == ViewPager.SCROLL_STATE_SETTLING)
            {
               // viewPager.setCurrentItem(0);
            }
        }
    }




    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {

            if (Utility.getCategory() != getString(R.string.nav_overview_str)) {
                CategoryFragment categoryFragment = new CategoryFragment();
                Bundle bundle = new Bundle();
                bundle.putString("monthFragment", Utility.Monthontab[position]);
                categoryFragment.setArguments(bundle);
                return categoryFragment;
            }
            else
            {
                OverviewFragment overviewFragment = new OverviewFragment();
                Bundle bundle = new Bundle();
                bundle.putString("monthFragment", Utility.Monthontab[position]);
                overviewFragment.setArguments(bundle);
                return overviewFragment;
            }
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return Utility.Monthontab.length;
        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {
            return Utility.Monthontab[position];
        }
    }



}
