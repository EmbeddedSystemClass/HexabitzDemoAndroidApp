package com.hexabitz.modulesconnector.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hexabitz.modulesconnector.Modules_Fragments.H01R00_RGB_LED;
import com.hexabitz.modulesconnector.Modules_Fragments.H0FR60_RELAY;
import com.hexabitz.modulesconnector.R;
import com.hexabitz.modulesconnector.ViewPagerAdapter;


public class Modules extends Fragment {

  View rootView;


  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    rootView = inflater.inflate(R.layout.frag_modules, container, false);


    ViewPager viewPager = rootView.findViewById(R.id.pager);
    ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

    adapter.addFragment(new H01R00_RGB_LED(), "LED");
    adapter.addFragment(new H0FR60_RELAY(), "Relay");

    viewPager.setAdapter(adapter);

    TabLayout tabLayout = rootView.findViewById(R.id.tabs);
    tabLayout.setupWithViewPager(viewPager);
//    int[] tabIcons = {
//        R.drawable.h01r00_photo_top,
//        R.drawable.h09r00_photo_top,
//    };
//    tabLayout.getTabAt(0).setIcon(tabIcons[0]);
//    tabLayout.getTabAt(1).setIcon(tabIcons[1]);

    return rootView;
  }

  @Override
  public void onResume() {
    super.onResume();

  }
}
