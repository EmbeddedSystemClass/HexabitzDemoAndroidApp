package com.hexabitz.modulesconnector.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Switch;

import com.hexabitz.modulesconnector.JAVA_COMS_LIB.HexaInterface;
import com.hexabitz.modulesconnector.MainActivity;
import com.hexabitz.modulesconnector.R;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;


public class H01R00_RGB_LED extends Fragment {

  boolean isLocked = false, isOn = false;
  int Code;
  Timer t = new Timer();

  View rootView;
  ColorPicker picker;
  OpacityBar opacityBar;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    rootView = inflater.inflate(R.layout.h01r00_rgb_led, container, false);

    picker = rootView.findViewById(R.id.picker);
    opacityBar = rootView.findViewById(R.id.opacitybar);
    final Switch LedSwitch = rootView.findViewById(R.id.LedSwitch);
    final NumberPicker destinationNP = rootView.findViewById(R.id.destinationNP);
    final NumberPicker sourceNP = rootView.findViewById(R.id.sourceNP);

    String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
    destinationNP.setMinValue(0);
    destinationNP.setMaxValue(numbers.length-1);
    destinationNP.setDisplayedValues(numbers);
    destinationNP.setWrapSelectorWheel(true);
    destinationNP.setValue(1);

    sourceNP.setMinValue(0);
    sourceNP.setMaxValue(numbers.length-1);
    sourceNP.setDisplayedValues(numbers);
    sourceNP.setWrapSelectorWheel(true);
    sourceNP.setValue(0);



    picker.addOpacityBar(opacityBar);
    picker.getColor();
    picker.setOldCenterColor(picker.getColor());
    picker.setShowOldCenterColor(false);
    picker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
      @Override
      public void onColorChanged(int color) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int opacity = opacityBar.getOpacity();

        Code = HexaInterface.Message_Codes.CODE_H01R0_COLOR;
        byte[] Payload = {1, (byte) red, (byte) green, (byte) blue, (byte) opacity};

        if (!isLocked) {
          ((MainActivity) Objects.requireNonNull(getActivity())).SendMessage((byte) (destinationNP.getValue() + 1), (byte) (sourceNP.getValue() + 1), Code, Payload);
          isLocked = true;
          t.schedule(new TimerTask() {
            @Override
            public void run() {
              isLocked = false;
            }
          }, 200);
        }
      }
    });

    opacityBar.setOnOpacityChangedListener(new OpacityBar.OnOpacityChangedListener() {
      @Override
      public void onOpacityChanged(int opacity) {
        int color = picker.getColor();
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        opacity = opacity * 100 / 255;

        Code = HexaInterface.Message_Codes.CODE_H01R0_COLOR;
        byte[] Payload = {1, (byte) red, (byte) green, (byte) blue, (byte) opacity};

        if (!isLocked) {
          ((MainActivity) Objects.requireNonNull(getActivity())).SendMessage((byte) (destinationNP.getValue() + 1), (byte) (sourceNP.getValue() + 1), Code, Payload);
          isLocked = true;
          t.schedule(new TimerTask() {
            @Override
            public void run() {
              isLocked = false;
            }
          }, 200);
        }
      }
    });

    LedSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!isOn) {
          LedSwitch.setText("On");
          isOn = true;
          int color = picker.getColor();
          int red = Color.red(color);
          int green = Color.green(color);
          int blue = Color.blue(color);
          int opacity = opacityBar.getOpacity();

          byte[] Payload = {1, (byte) red, (byte) green, (byte) blue, (byte) opacity};
          Code = HexaInterface.Message_Codes.CODE_H01R0_COLOR;

          if (!isLocked) {
            ((MainActivity) Objects.requireNonNull(getActivity())).SendMessage((byte) (destinationNP.getValue() + 1), (byte) (sourceNP.getValue() + 1), Code, Payload);
            isLocked = true;
            t.schedule(new TimerTask() {
              @Override
              public void run() {
                isLocked = false;
              }
            }, 200);
          }

        }
        else
        {
          LedSwitch.setText("Off");
          isOn = false;
          Code = HexaInterface.Message_Codes.CODE_H01R0_OFF;
          byte[] Payload = new byte[0];
          if (!isLocked) {
            ((MainActivity) Objects.requireNonNull(getActivity())).SendMessage((byte) (destinationNP.getValue() + 1), (byte) (sourceNP.getValue() + 1), Code, Payload);
            isLocked = true;
            t.schedule(new TimerTask() {
              @Override
              public void run() {
                isLocked = false;
              }
            }, 200);
          }
        }
      }
    });





    return rootView;
  }

}
