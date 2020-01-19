package com.hexabitz.modulesconnector.Modules_Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hexabitz.modulesconnector.R;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;

import java.util.Timer;


public class H0FR60_RELAY extends Fragment {

  boolean isLocked = false, isOn = false;
  int Code;
  Timer t = new Timer();

  View rootView;
  ColorPicker picker;
  OpacityBar opacityBar;

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    rootView = inflater.inflate(R.layout.frag_h0fr60_relay, container, false);




    return rootView;
  }

  @Override
  public void onResume() {
    super.onResume();

  }

}
