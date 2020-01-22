package com.hexabitz.modulesconnector.Modules_Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hexabitz.modulesconnector.Fragments.Settings;
import com.hexabitz.modulesconnector.MainActivity;
import com.hexabitz.modulesconnector.R;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;


public class H08R6_IR_SENSOR extends Fragment {

  boolean isLocked = false, isOn = false;
  int Code;
  byte[] Payload;
  Timer t = new Timer();

  View rootView;


  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    rootView = inflater.inflate(R.layout.frag_h08r6_ir_sensor, container, false);


    return rootView;
  }

  private void SendMessage() {
    if (!isLocked) {
      ((MainActivity) Objects.requireNonNull(getActivity())).SendMessage((byte) Settings.Destination, (byte) Settings.Source, Code, Payload);
      isLocked = true;
      t.schedule(new TimerTask() {
        @Override
        public void run() {
          isLocked = false;
        }
      }, 100);
    }
  }

}
