package com.hexabitz.modulesconnector.Modules_Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.hexabitz.modulesconnector.Fragments.Settings;
import com.hexabitz.modulesconnector.JAVA_COMS_LIB.HexaInterface;
import com.hexabitz.modulesconnector.MainActivity;
import com.hexabitz.modulesconnector.R;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;


public class H08R6_IR_SENSOR extends Fragment {

  boolean isLocked = false, isOn = false;
  int Code;
  byte[] Payload;
  Timer t = new Timer();

  View rootView;
  Spinner unitSpinner;


  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    rootView = inflater.inflate(R.layout.frag_h08r6_ir_sensor, container, false);

    unitSpinner = rootView.findViewById(R.id.unitSpinner);
    List<String> spinnerArray = new ArrayList<>();
    spinnerArray.add("MM");
    spinnerArray.add("CM");
    spinnerArray.add("Inch");

    ArrayAdapter<String> adapter = new ArrayAdapter<>(
        getActivity(), android.R.layout.simple_spinner_item, spinnerArray);

    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    unitSpinner.setAdapter(adapter);

    unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Code = HexaInterface.Message_Codes.CODE_H08R6_SET_UNIT;
        switch (position) {
          case 0: Payload = new byte[]{0}; break;
          case 1: Payload = new byte[]{1}; break;
          case 2: Payload = new byte[]{2}; break;
        }

//        SendMessage();
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    Button setUnitBTN = rootView.findViewById(R.id.setUnitBTN);

    setUnitBTN.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        SendMessage();
      }
    });

    final TextView PeriodTV = rootView.findViewById(R.id.PeriodTV);
    final TextView TimeOutTV = rootView.findViewById(R.id.TimeOutTV);
    final Switch IRSwitch = rootView.findViewById(R.id.IRSwitch);


    IRSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (isChecked) {
          IRSwitch.setText("On");

          int period = Integer.parseInt(PeriodTV.getText().toString());
          int time = Integer.parseInt(TimeOutTV.getText().toString());

          byte[] periodBytes = ByteBuffer.allocate(4).putInt(period).array();
          byte[] timeBytes = ByteBuffer.allocate(4).putInt(time).array();

          Code = HexaInterface.Message_Codes.CODE_H08R6_STREAM_PORT;
          Payload = new byte[]{
              periodBytes[0], // we reverse them here because in Java the allocate reversed their order
              periodBytes[1],
              periodBytes[2],
              periodBytes[3],

              timeBytes[0],
              timeBytes[1],
              timeBytes[2],
              timeBytes[3],

              6,
              1};
          SendMessage();
          ReceiveMessage();

        }
        else
        {
          IRSwitch.setText("Off");
          isOn = false;
          Code = HexaInterface.Message_Codes.CODE_H08R6_STOP_RANGING;
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

  private void ReceiveMessage() {
    ((MainActivity) Objects.requireNonNull(getActivity())).ReceiveMessage();
  }
}
