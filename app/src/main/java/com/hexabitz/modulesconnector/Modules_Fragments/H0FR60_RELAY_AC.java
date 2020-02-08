package com.hexabitz.modulesconnector.Modules_Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.hexabitz.modulesconnector.Fragments.Settings;
import com.hexabitz.modulesconnector.JAVA_COMS_LIB.HexaInterface;
import com.hexabitz.modulesconnector.MainActivity;
import com.hexabitz.modulesconnector.R;

import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;


public class H0FR60_RELAY_AC extends Fragment {

  boolean isLocked = false, isOn = false;
  int Code;
  byte[] Payload;
  Timer t = new Timer();

  View rootView;


  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    rootView = inflater.inflate(R.layout.frag_h0fr60_relay, container, false);

    final TextView TimeOutTV = rootView.findViewById(R.id.TimeOutTV);
    final Switch RelaySwitch = rootView.findViewById(R.id.RelaySwitch);


    RelaySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (!isOn) {
          RelaySwitch.setText("On");
          isOn = true;

          int time = Integer.parseInt(TimeOutTV.getText().toString());

          byte[] timeBytes = ByteBuffer.allocate(4).putInt(time).array();

          Code = HexaInterface.Message_Codes.CODE_H0FR6_ON;
          Payload = new byte[]{timeBytes[3], timeBytes[2], timeBytes[1], timeBytes[0]};
//          Payload = new byte[0];
          SendMessage();


        }
        else
        {
          RelaySwitch.setText("Off");
          isOn = false;
          Code = HexaInterface.Message_Codes.CODE_H0FR6_TOGGLE;
          Payload = new byte[0];
          SendMessage();
        }

      }
    });


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
