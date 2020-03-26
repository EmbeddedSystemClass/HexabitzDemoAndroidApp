package com.hexabitz.android_demo_app.Modules_Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.hexabitz.android_demo_app.Fragments.Settings;
import com.hexabitz.android_demo_app.JAVA_COMS_LIB.HexaInterface;
import com.hexabitz.android_demo_app.MainActivity;
import com.hexabitz.android_demo_app.R;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;

import java.util.Timer;
import java.util.TimerTask;


public class H01R00_RGB_LED extends Fragment {

  boolean isLocked = false, isOn = false;
  int Code;
  byte[] Payload;
  Timer t = new Timer();

  View rootView;
  ColorPicker picker;
  OpacityBar opacityBar;

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    rootView = inflater.inflate(R.layout.frag_h01r00_rgb_led, container, false);

    picker = rootView.findViewById(R.id.picker);
    opacityBar = rootView.findViewById(R.id.opacitybar);
    opacityBar.setOpacity(50);

    final Switch LedSwitch = rootView.findViewById(R.id.LedSwitch);

    picker.addOpacityBar(opacityBar);
    picker.setOldCenterColor(picker.getColor());
    picker.setShowOldCenterColor(false);
    picker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
      @Override
      public void onColorChanged(int color) {
        int opacity = opacityBar.getOpacity();
        Code = HexaInterface.Message_Codes.CODE_H01R0_COLOR;
        Payload = new byte[]{1, (byte) Color.red(color), (byte) Color.green(color), (byte) Color.blue(color), (byte) opacity};
        SendMessage();
      }
    });

    opacityBar.setOnOpacityChangedListener(new OpacityBar.OnOpacityChangedListener() {
      @Override
      public void onOpacityChanged(int opacity) {
        int color = picker.getColor();
        opacity = opacity * 100 / 255;
        Code = HexaInterface.Message_Codes.CODE_H01R0_COLOR;
        Payload = new byte[]{1, (byte) Color.red(color), (byte) Color.green(color), (byte) Color.blue(color), (byte) opacity};
        SendMessage();
      }
    });

    LedSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!isOn) {
          LedSwitch.setText("On");
          isOn = true;
          Code = HexaInterface.Message_Codes.CODE_H01R0_ON;
          Payload = new byte[]{90};
          SendMessage();
        }
        else
        {
          LedSwitch.setText("Off");
          isOn = false;
          Code = HexaInterface.Message_Codes.CODE_H01R0_OFF;
          Payload = new byte[0];
          SendMessage();
        }
      }
    });

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
