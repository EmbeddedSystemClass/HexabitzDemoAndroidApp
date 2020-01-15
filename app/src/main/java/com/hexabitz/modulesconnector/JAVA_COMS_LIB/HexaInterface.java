package com.hexabitz.modulesconnector.JAVA_COMS_LIB;

public class HexaInterface {


  public String Opt8_Next_Message = "0";
  public String Opt67_Response_Options = "01";
  public String Opt5_Reserved = "0";
  public String Opt34_Trace_Options = "00";
  public String Opt2_16_BIT_Code = "1";
  public String Opt1_Extended_Flag = "0";


  // Enum for the codes to be sent to the modules
  public class Message_Codes
  {
    // H01R0 (Led Module)
    public static final int CODE_H01R0_ON			           = 100;
    public static final int CODE_H01R0_OFF			         = 101;
    public static final int CODE_H01R0_TOGGLE		         = 102;
    public static final int CODE_H01R0_COLOR		         = 103;
    public static final int CODE_H01R0_PULSE	           = 104;
    public static final int CODE_H01R0_SWEEP		         = 105;
    public static final int CODE_H01R0_DIM			         = 106;

    // H0FR6x (Relay)
    public static final int CODE_H0FR6_ON		             = 750;
    public static final int CODE_H0FR6_OFF			         = 751;
    public static final int CODE_H0FR6_TOGGLE		         = 752;
    public static final int CODE_H0FR6_PWM			         = 753;

    // H26R0x (Load-Cell Module)
    public static final int CODE_H26R0_STREAM_PORT_GRAM  = 1901;
    public static final int CODE_H26R0_STREAM_PORT_KGRAM = 1902;
    public static final int CODE_H26R0_STREAM_PORT_OUNCE = 1903;
    public static final int CODE_H26R0_STREAM_PORT_POUND = 1904;
    public static final int CODE_H26R0_STOP              = 1905;
    public static final int CODE_H26R0_ZEROCAL           = 1910;
  }

  //region Options_Byte

  // 8th bit (MSB): Long messages flag. If set; then message parameters continue in the next message.
  public class Options8_Next_Message
  {
    public static final int FALSE = 0;
    public static final int TRUE = 1;
  }

  // 6-7th bits:
  public class Options67_Response_Options
  {
    public static final int SEND_BACK_NO_RESPONSE = 00;
    public static final int SEND_RESPONSES_ONLY_MESSAGES = 01;
    public static final int SEND_RESPONSES_ONLY_TO_CLI_COMMANDS = 10;
    public static final int SEND_RESPONSES_TO_EVERYTHING = 11;
  }

  // 5th bit: reserved.
  public class Options5_Reserved
  {
    public static final int FALSE = 0;
    public static final int TRUE = 1;
  }

  // 3rd-4th bits:
  public class Options34_Trace_Options
  {
    public static final int SHOW_NO_TRACE = 00;
    public static final int SHOW_MESSAGE_TRACE = 01;
    public static final int SHOW_MESSAGE_RESPONSE_TRACE = 10;
    public static final int SHOW_TRACE_FOR_BOTH_MESSAGES_AND_THEIR_RESPONSES = 11;
  }

  // 2nd bit: Extended Message Code flag. If set; then message codes are 16 bits.
  public class Options2_16_BIT_Code
  {
    public static final int FALSE = 0;
    public static final int TRUE = 1;
  }

  // 1st bit (LSB): Extended Options flag. If set; then the next byte is an Options byte as well.
  public class Options1_Extended_Flag
  {
    public static final int FALSE = 0;
    public static final int TRUE = 1;
  }
  // endregion


}
