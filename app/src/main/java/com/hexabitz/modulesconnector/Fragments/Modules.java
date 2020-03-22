package com.hexabitz.modulesconnector.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hexabitz.modulesconnector.Modules_Fragments.*;
import com.hexabitz.modulesconnector.R;
import com.hexabitz.modulesconnector.ViewPagerAdapter;


public class Modules extends Fragment {

  View rootView;

  Fragment Settings = new Settings();
  Fragment H01R00_RGB_LED = new H01R00_RGB_LED();
  Fragment H0FR60_RELAY = new H0FR60_RELAY_AC();
  Fragment H08R6_IR_SENSOR = new H08R6_IR_SENSOR();
  Fragment H26R0_LOAD_CELL = new H26R0_LOAD_CELL();
  Fragment H0BR40_IMU = new H0BR40_IMU();

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    rootView = inflater.inflate(R.layout.frag_modules, container, false);

    ViewPager viewPager = rootView.findViewById(R.id.pager);
    ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

    adapter.addFragment(Settings, "Settings");
//    adapter.addFragment(H01R00_RGB_LED, "RGB_LED");
//    adapter.addFragment(H0FR60_RELAY, "RELAY");
//    adapter.addFragment(H26R0_LOAD_CELL, "LOAD_CELL");
    adapter.addFragment(H08R6_IR_SENSOR, "IR_SENSOR");
//    adapter.addFragment(H0BR40_IMU, "IMU");

    viewPager.setAdapter(adapter);

    final TabLayout tabLayout = rootView.findViewById(R.id.tabs);
    tabLayout.setupWithViewPager(viewPager);
//    int[] tabIcons = {
//        R.drawable.module_h01r00_led,
//        R.drawable.module_h09r00_relay,
//        R.drawable.module_h08r6_ir_sensor,
//    };
//    tabLayout.getTabAt(0).setIcon(tabIcons[0]);
//    tabLayout.getTabAt(1).setIcon(tabIcons[1]);
//    tabLayout.getTabAt(2).setIcon(tabIcons[2]);

    return rootView;
  }

}
