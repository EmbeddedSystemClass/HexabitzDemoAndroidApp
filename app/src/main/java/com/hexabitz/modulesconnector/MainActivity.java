package com.hexabitz.modulesconnector;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hexabitz.modulesconnector.Fragments.Modules;
import com.hexabitz.modulesconnector.Fragments.Settings;
import com.hexabitz.modulesconnector.JAVA_COMS_LIB.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
  private TextView mTextMessage;

  View parentLayout;
  Fragment Modules = new Modules();
  Fragment Settings = new Settings();

  private byte[] mmBuffer;
  private BluetoothSocket bluetoothSocket;
  private BluetoothDevice bluetoothDevice;
  private InputStream inputStream;
  private OutputStream outputStream;

  public String Opt8_Next_Message = "0";
  public String Opt67_Response_Options = "01";
  public String Opt5_Reserved = "0";
  public String Opt34_Trace_Options = "00";
  public String Opt2_16_BIT_Code = "0";
  public String Opt1_Extended_Flag = "0";
  public byte[] AllMessage;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    parentLayout = findViewById(android.R.id.content);

    if (savedInstanceState != null) {
      //Restore the fragment's instance
      Modules = getSupportFragmentManager().getFragment(savedInstanceState, "Modules");
      Settings = getSupportFragmentManager().getFragment(savedInstanceState, "Settings");
    }


    BottomNavigationView navView = findViewById(R.id.nav_view);
    mTextMessage = findViewById(R.id.message);
    navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    String DeviceName = getIntent().getStringExtra("DeviceName");
    String DeviceAddress = getIntent().getStringExtra("DeviceAddress");

    setTitle("Connected to " + DeviceName);

    connectToDevice(DeviceAddress);

    LoadFragment(Modules);

  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);

    //Save the fragment's instance
    getSupportFragmentManager().putFragment(outState, "Modules", Modules);
    getSupportFragmentManager().putFragment(outState, "Settings", Settings);
  }

  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
          = new BottomNavigationView.OnNavigationItemSelectedListener() {

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
      switch (item.getItemId())
      {
        case R.id.navigation_modules: return LoadFragment(Modules);
        case R.id.navigation_settings: return LoadFragment(Settings);
      }
      return false;
    }
  };

  private boolean LoadFragment(Fragment fragment)
  {
    if (fragment != null)
    {
      getSupportFragmentManager()
          .beginTransaction()
          .replace(R.id.fragment_container, fragment)
          .commit();
      return true;
    }
    return false;
  }

  private void connectToDevice(String address) {
    UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard SerialPortService ID
    BluetoothSocket tmp;
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
    try
    {
      tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
    }
    catch (IOException e)
    {
          Snackbar.make(parentLayout, e.getMessage(), Snackbar.LENGTH_LONG)
                  .setAction("IOException", null).show();
      return;
    }
    bluetoothDevice = device;
    bluetoothSocket = tmp;

    mBluetoothAdapter.cancelDiscovery();

    try
    {
      bluetoothSocket.connect();
    }
    catch (IOException connectException)
    {
      Snackbar.make(parentLayout, connectException.getMessage(), Snackbar.LENGTH_LONG)
              .setAction("IOException", null).show();
      try
      {
        bluetoothSocket.close();
      }
      catch (IOException closeException)
      {
        Snackbar.make(parentLayout, closeException.getMessage(), Snackbar.LENGTH_LONG)
            .setAction("IOException", null).show();
      }
      return;
    }

    try
    {
      outputStream = bluetoothSocket.getOutputStream();
      inputStream = bluetoothSocket.getInputStream();
    }
    catch (IOException e)
    {
      Snackbar.make(parentLayout, e.getMessage(), Snackbar.LENGTH_LONG)
          .setAction("IOException", null).show();

    }
  }

  // Method to send the buffer to Hexabitz modules.
  public void SendMessage(byte Destination, byte Source, int Code, byte[] Payload) {
    String optionsString = Opt8_Next_Message +
                          Opt67_Response_Options +
                          Opt5_Reserved +
                          Opt34_Trace_Options +
                          Opt2_16_BIT_Code +
                          Opt1_Extended_Flag;
    byte Options = GetBytes(optionsString)[1];  // 00100000 // 0x20

    Message _Message = new Message(Destination, Source, Options, Code, Payload);
    AllMessage = _Message.GetAll();  // We get the whole buffer bytes to be sent to the Hexabitz modules.

    TextView crcLBL = findViewById(R.id.crcLBL);

    String crc = String.format("%02X ", AllMessage[AllMessage.length -1]);
    crcLBL.setText(crc);


    try
    {
      outputStream.write(AllMessage,0, AllMessage.length);
    }
    catch (IOException e)
    {
      Snackbar.make(parentLayout, e.getMessage(), Snackbar.LENGTH_LONG)
          .setAction("IOException", null).show();
    }

  }

  //get byte from string
  public static byte[] GetBytes(String bitString)
  {
    short a = Short.parseShort(bitString, 2);
    ByteBuffer bytes = ByteBuffer.allocate(2).putShort(a);

    byte[] byteArray = bytes.array();

    return byteArray;
  }

}
