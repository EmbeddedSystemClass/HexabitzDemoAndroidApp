package com.hexabitz.android_demo_app.Modules_Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hexabitz.android_demo_app.Fragments.Settings;
import com.hexabitz.android_demo_app.MainActivity;
import com.hexabitz.android_demo_app.R;

import java.util.Timer;
import java.util.TimerTask;


public class H0BR40_IMU extends Fragment {

  boolean isLocked = false, isOn = false;
  int Code;
  byte[] Payload;
  Timer t = new Timer();

  View rootView;


  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    rootView = inflater.inflate(R.layout.frag_h0br40_imu, container, false);


    return rootView;
  }

  private void SendMessage() {
    if (!isLocked) {
      ((MainActivity) getActivity()).SendMessage((byte) Settings.Destination, (byte) Settings.Source, Code, Payload);
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
