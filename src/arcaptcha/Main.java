// dont forge the font size and the square size relation
// Do a global font
// OutlineNoiseFilter do a slider for randomization tuning

// do a button to see the generated samples will look like , button called crazy
// add also a silder randomizor for every type of distortion
// if you have the time do an inlineNoise or bezier curve
// 150 * 57
package arcaptcha;

import processing.xml.*;
import processing.core.*;
import controlP5.*;
import java.util.Arrays;
import java.io.*;
import java.awt.*;

// please see controlFont,ControlP5dropdownList,list box clear ,
//last to begin with is controlp5matrix
// also in gemerative: hello tngents,hello adapt,
//hello adapt children,intersection,closest,helloworld getpoints,
//helloworld rotate firtst letter
//------------------------------------------------------------------------------
public class Main extends PApplet
{
  //Captcha cptsh ;//testing
  // setting the size of the program to be smaller by 10% from the screen size.
  int programWidth = screen.width-(int)(screen.width*0.1);
  int programHeight = screen.height-(int)(screen.height*0.2);
  // setting the size of the "left panel" to be 20% of the applet size
  int leftPanelSize = programWidth-(int)(programWidth*0.78);
  int leftPanelStarting = programWidth-(int)(programWidth*0.22);
  int leftPanelEnding = programWidth;

//  //Sample Captchas
//  Captcha[] captchaArray = new Captcha[6];
//  String[] sampleWords = { "العربية","نهضة","جامعة" ,"معادن" ,"بترول" ,"كابتشا" };
  CaptchaEngine geny ;

  String[] fontList = PFont.list();
  PFont defaultFont;
  
  // Captcha global vars , sorry no naming convention, no good spelling also
  Captcha captchaSample;
  String defaultFontName = "Arial";
  Font fontGlobal;
  int CaptchaWidth = 300;
  Slider widthOfCaptcha;
  int CaptchaHeight = 300;
  Slider HeightOfCaptcha;
  int CaptchaFontSize = 42;
  Slider fontSizeSlider;
  int CaptchaCenterX = 150;
  Slider CaptchaCenterXSlider;
  int CaptchaCenterY = 150;
  Slider CaptchaCenterYSlider;
  int CaptchaNumOfLetters = 6;
  int amountOfCaptchas = 10;
  Textfield amountOfCaptchasTextfield;
  Textfield prefixTextFiled;
  CheckBox FiltersCheckBoxes;
  RadioButton radioButtonsImgType;
  RadioButton radioButtonsRecommended;
  controlP5.Button buttonGenerate;
  controlP5.Button realtimeButton;
  DropdownList listOfSystemFonts;
  Slider lettersSlider;
  float squarMinX = 0;
  float squarMinY = 0;
  String sampleWord = "نهضة العربية";
  PImage samplePImage;
  public boolean[] filterFlags = {false,false,false,false,false,false,false,false,false,false,false,false,false};
  float[] toleranceArgs = {9,0.5f,0.051f,0.1f,0.5f,2,0.5f,1,2,4,3};
  String imageType= "PNG";
  String prefix = "";
  String[] smapleWordsArray = {"ن","أب","عرب","عربي","عربية","عريبية","العربية","تعريبيات","للعريبيات","نهضة عربية","نهضة عريبية","لغتي العربية","لغتنا العربية"};
  // ----------------------1---2-----3-----4------5-------6---------7----------8---------9------------10-----------11----------------12--------13--------------
//  public int progressAmount = 0;
//  public boolean progressFlag = false;
  
  boolean realtimeFlag = false;

  // main GUI object from controlP5
  ControlP5 controlP5;
//------------------------------------------------------------------------------
private void initLeftPanel()
{


  // init the Main GUI object to handle every GUI
  controlP5 = new ControlP5(this);
  //controlP5.setControlFont(new PFont(new Font("Tahoma", Font.PLAIN, 12), true));
  //controlP5.setAutoDraw(false);
  widthOfCaptcha= controlP5.addSlider("Width",10,500,CaptchaWidth,leftPanelStarting+25,20,170,15);
  //widthOfCaptcha.setNumberOfTickMarks(490);
  HeightOfCaptcha= controlP5.addSlider("Height",10,500,CaptchaHeight,leftPanelStarting+25,50,170,15);
  //HeightOfCaptcha.setNumberOfTickMarks(489);
  //addSlider(java.lang.String theName, float theMin, float theMax, float theDefaultValue, int theX, int theY, int theW, int theH) 

    amountOfCaptchasTextfield = controlP5.addTextfield("Amount Of Captchas", leftPanelStarting+25, 80, 70, 20);
    //addTextfield(java.lang.String theName, int theX, int theY, int theW, int theH)
    amountOfCaptchasTextfield.setText("10");
    
    prefixTextFiled = controlP5.addTextfield("Prefix", leftPanelStarting+125, 80, 70, 20);
    //addTextfield(java.lang.String theName, int theX, int theY, int theW, int theH)
    prefixTextFiled.setText(PApplet.day()+"_"+PApplet.month()+"_"+PApplet.year()+"_");

  // CheckBox ------------------------------------------------------------
  // filters
    
    FiltersCheckBoxes = controlP5.addCheckBox("CheckBoxs", leftPanelStarting+25, 130);
                           //addCheckBox(java.lang.String theName, int theX, int theY)
      FiltersCheckBoxes.setItemsPerRow(1);
      FiltersCheckBoxes.setSpacingColumn(60);
      FiltersCheckBoxes.setSpacingRow(9);
      // add items to a checkbox.
      Toggle checkBox1 = FiltersCheckBoxes.addItem("0",0);
      checkBox1.captionLabel().set("Lines");
      Toggle checkBox2 = FiltersCheckBoxes.addItem("1",1);
      checkBox2.captionLabel().set("Twirl");
      Toggle checkBox3 = FiltersCheckBoxes.addItem("2",2);
      checkBox3.captionLabel().set("Ripple");
      Toggle checkBox4 = FiltersCheckBoxes.addItem("3",3);
      checkBox4.captionLabel().set("Pinch");
      Toggle checkBox5 = FiltersCheckBoxes.addItem("4",4);
      checkBox5.captionLabel().set("Marble");
      Toggle checkBox6 = FiltersCheckBoxes.addItem("5",5);
      checkBox6.captionLabel().set("Blur");
      Toggle checkBox7 = FiltersCheckBoxes.addItem("6",6);
      checkBox7.captionLabel().set("Thershold");
      Toggle checkBox8 = FiltersCheckBoxes.addItem("7",7);
      checkBox8.captionLabel().set("JCaptcha");
      Toggle checkBox9 = FiltersCheckBoxes.addItem("8",8);
      checkBox9.captionLabel().set("Halftone");
      Toggle checkBox10 = FiltersCheckBoxes.addItem("9",9);
      checkBox10.captionLabel().set("Block");
      Toggle checkBox11 = FiltersCheckBoxes.addItem("10",10);
      checkBox11.captionLabel().set("Pointillize");


      controlP5.addSlider("Lines",1,30,9,leftPanelStarting+100,130,70,10);
      //addSlider(java.lang.String theName, float theMin, float theMax, float theDefaultValue, int theX, int theY, int theW, int theH)
      controlP5.addSlider("Twirl",0,2,0.5f,leftPanelStarting+100,150,70,10);
      //addSlider(java.lang.String theName, float theMin, float theMax, float theDefaultValue, int theX, int theY, int theW, int theH)
      controlP5.addSlider("Ripple",0.0001f,1,0.051f,leftPanelStarting+100,170,70,10);
      //addSlider(java.lang.String theName, float theMin, float theMax, float theDefaultValue, int theX, int theY, int theW, int theH)
      controlP5.addSlider("Pinch",0.001f,0.99f,0.1f,leftPanelStarting+100,190,70,10);
      //addSlider(java.lang.String theName, float theMin, float theMax, float theDefaultValue, int theX, int theY, int theW, int theH)
      controlP5.addSlider("Marble",0.01f,2,0.5f,leftPanelStarting+100,210,70,10);
      //addSlider(java.lang.String theName, float theMin, float theMax, float theDefaultValue, int theX, int theY, int theW, int theH)
      controlP5.addSlider("Blur",0.01f,10,2,leftPanelStarting+100,230,70,10);
      //addSlider(java.lang.String theName, float theMin, float theMax, float theDefaultValue, int theX, int theY, int theW, int theH)
      controlP5.addSlider("Thershold",0.01f,1,0.5f,leftPanelStarting+100,250,70,10);
      //addSlider(java.lang.String theName, float theMin, float theMax, float theDefaultValue, int theX, int theY, int theW, int theH)
      controlP5.addSlider("JCaptcha",0.01f,10,2,leftPanelStarting+100,270,70,10);
      //addSlider(java.lang.String theName, float theMin, float theMax, float theDefaultValue, int theX, int theY, int theW, int theH)
      controlP5.addSlider("Halftone",0.01f,5,1,leftPanelStarting+100,290,70,10);
      //addSlider(java.lang.String theName, float theMin, float theMax, float theDefaultValue, int theX, int theY, int theW, int theH)
      controlP5.addSlider("Block",1,20,4,leftPanelStarting+100,310,70,10);
      //addSlider(java.lang.String theName, float theMin, float theMax, float theDefaultValue, int theX, int theY, int theW, int theH)
      controlP5.addSlider("Pointillize",0,10,3,leftPanelStarting+100,330,70,10);
      //addSlider(java.lang.String theName, float theMin, float theMax, float theDefaultValue, int theX, int theY, int theW, int theH)



   //---------------------------------------------------------------------




  fontSizeSlider= controlP5.addSlider("Font Size",2,100,CaptchaFontSize,leftPanelStarting+25,390,100,15);
  //addSlider(java.lang.String theName, float theMin, float theMax, float theDefaultValue, int theX, int theY, int theW, int theH)



//  CaptchaCenterXSlider = controlP5.addSlider("CaptchaCenterXSlider",0,500,0,leftPanelStarting+25,570,100,15);
//  //addSlider(java.lang.String theName, float theMin, float theMax, float theDefaultValue, int theX, int theY, int theW, int theH)
//  CaptchaCenterXSlider.setLabel("X axis of Captcha");
//  CaptchaCenterYSlider= controlP5.addSlider("CaptchaCenterYSlider",0,500,0,leftPanelStarting+25,590,100,15);
//  //addSlider(java.lang.String theName, float theMin, float theMax, float theDefaultValue, int theX, int theY, int theW, int theH)
//  CaptchaCenterYSlider.setLabel("Y axis of Captcha");

  // Radio Buttons
  // for Image Type
  radioButtonsImgType = controlP5.addRadioButton("RadioButton", leftPanelStarting+25, 420);
  radioButtonsImgType.setItemsPerRow(3);
  radioButtonsImgType.setSpacingColumn(30);
  radioButtonsImgType.addItem("JPEG", 2);
  radioButtonsImgType.addItem("GIF", 3);
  Toggle pngRadio = radioButtonsImgType.addItem("PNG", 1);
  pngRadio.setLabel("PNG- default");
  pngRadio.setState(true);

  // for recommedned settings
  radioButtonsRecommended = controlP5.addRadioButton("RadioButtonRecommended", leftPanelStarting+25, 490);
  radioButtonsRecommended.setItemsPerRow(2);
  radioButtonsRecommended.setSpacingColumn(110);
  radioButtonsRecommended.setSpacingRow(10);
  radioButtonsRecommended.addItem("ArCaptcha SMAll", 4);
  radioButtonsRecommended.addItem("ArCaptcha", 5);
  radioButtonsRecommended.addItem("ArCaptcha Colorful", 6);
  Toggle recaptchaRadio = radioButtonsRecommended.addItem("ReCaptcha", 7);
  recaptchaRadio.setLabel("ReCaptcha Size");
  //recaptchaRadio.setState(true);

//  // Number box for max letters
//  Numberbox lettersNumberBox = controlP5.addNumberbox("lettersNumberBox", 6, leftPanelStarting+25, 450, 50, 20);
//  lettersNumberBox.setMin(1);
//  lettersNumberBox.setMax(15);

  lettersSlider = controlP5.addSlider("lettersSlider",1,20,CaptchaNumOfLetters,leftPanelStarting+25,450,50,20);
  //addSlider(java.lang.String theName, float theMin, float theMax, float theDefaultValue, int theX, int theY, int theW, int theH)
  lettersSlider.setLabel("Max Number of Captcha Letters");
  lettersSlider.setNumberOfTickMarks(20);


  // Buttons ------------------------------------------------------------
  // Generate

    buttonGenerate = controlP5.addButton("Generate",1f, leftPanelStarting+25, 540, 80, 20);
    
    realtimeButton = controlP5.addButton("realtime",1f, leftPanelStarting+125, 540, 80, 20);
    realtimeButton.setSwitch(true);
    //addButton(java.lang.String theName,float theValue,int theX,int theY,int theW,int theH)
   //---------------------------------------------------------------------
    // DropDown list ------------------------------------------------------------
  // ListOfFonts
    listOfSystemFonts = controlP5.addDropdownList("listOfSystemFonts", leftPanelStarting+25, 380, 200,200);
    //addDropdownList(java.lang.String theName, int theX, int theY, int theW, int theH)

    listOfSystemFonts.setItemHeight(10);
    listOfSystemFonts.setBarHeight(20);
    listOfSystemFonts.captionLabel().set("Choose a Font");
    listOfSystemFonts.captionLabel().style().marginTop = 5;
    listOfSystemFonts.captionLabel().style().marginLeft = 5;
    listOfSystemFonts.valueLabel().style().marginTop = 5;
    //adding items to the dropdownlist ---
    //addItem(java.lang.String theName, int theValue) ;
    for(int i = 0 ; i<fontList.length ; i++)
    {
       listOfSystemFonts.addItem(fontList[i], i);
       //addItem(java.lang.String theName, int theValue)
       //println(fontList[i]+" , "+ i);
    }

}
//------------------------------------------------------------------------------
// For fonts drop dwon list since they dont extend the controler class !!
public void controlEvent(ControlEvent theEvent)
{
  // PulldownMenu is of type ControlGroup.
  // A controlEvent will be triggered from within the ControlGroup.
  // therefore you need to check the originator of the Event with
  // if (theEvent.isGroup())
  // to avoid an error message from controlP5.

  //------------------------------------------------
  if (theEvent.isGroup()&& theEvent.group().name().equalsIgnoreCase("listOfSystemFonts")) 
  {
    // check if the Event was triggered from a ControlGroup
    this.defaultFontName = this.fontList[(int)theEvent.group().value()];
    this.geny = new CaptchaEngine(defaultFontName,CaptchaFontSize);
    this.updateSample();
    //println(fontList[(int)theEvent.group().value()]+" from "+theEvent.group().name());
  }
  //------------------------------------------------

  else if(theEvent.isController() && theEvent.controller().name().equalsIgnoreCase("Width")) 
  {
    //println("ControlEvent CaptchaWidth 1: "+theEvent.controller().value());
    this.CaptchaWidth = (int)(theEvent.controller().value());
//    if( (int)this.captchaSample.wordWidth > this.CaptchaWidth)
//    {
//        this.CaptchaWidth = (int)(this.captchaSample.wordWidth);
//    }
    this.updateSample();
    //println("ControlEvent CaptchaWidth 2:"+this.CaptchaWidth);
  }
    //------------------------------------------------

  else if(theEvent.isController() && theEvent.controller().name().equalsIgnoreCase("Height"))
  {
    //println(theEvent.controller().value()+" from "+theEvent.controller());
    CaptchaHeight = (int)(theEvent.controller().value());
//    if((int)(captchaSample.wordHeieght) > CaptchaHeight)
//    {
//        CaptchaHeight = (int)(captchaSample.wordHeieght);
//    }
    updateSample();
    //println("ControlEvent CaptchaHeight :"+CaptchaHeight);
  }
    //------------------------------------------------

  else if(theEvent.isController() && theEvent.controller().name().equalsIgnoreCase("Generate")) 
  {
    amountOfCaptchas = Integer.parseInt(amountOfCaptchasTextfield.getText());
    prefix = prefixTextFiled.getText();
    captchaSample.outputCaptchas(prefix,imageType,amountOfCaptchas,CaptchaNumOfLetters);
    //println("amountOfCaptchas :"+amountOfCaptchas);
    //println(theEvent.controller().value()+" from "+theEvent.controller());
  }
  else if(theEvent.isController() && theEvent.controller().name().equalsIgnoreCase("realtime"))
  {
    if(realtimeFlag == false)
    {
        realtimeFlag = true;
    }
    else
    {
        realtimeFlag = false;
    }
    this.updateSample();
    //println("realtime: "+realtimeFlag);
    //println(theEvent.controller().value()+" from "+theEvent.controller());
  }
  else if(theEvent.isController() && theEvent.controller().name().equalsIgnoreCase("lettersSlider"))
  {
    CaptchaNumOfLetters = (int)(theEvent.controller().value());
    this.updateSample();
    println("CaptchaNumOfLetters: "+CaptchaNumOfLetters);
    //println(theEvent.controller().value()+" from "+theEvent.controller());
  }

    //------------------------------------------------

  else if(theEvent.isController() && theEvent.controller().name().equalsIgnoreCase("Font Size")) 
  {

    CaptchaFontSize = (int)(theEvent.controller().value());

    this.geny = new CaptchaEngine(defaultFontName,CaptchaFontSize);
    this.updateSample();
    //println("ControlEvent CaptchaFontSize :"+CaptchaFontSize);
  }   

    //------------------------------------------------

  else if(theEvent.isController() && theEvent.controller().name().equalsIgnoreCase("CaptchaCenterXSlider")) 
  {
    CaptchaCenterX = (int)(theEvent.controller().value());
    this.updateSample();
    //println("ControlEvent CaptchaCenterX :"+CaptchaCenterX);
  }

    //------------------------------------------------

  else if(theEvent.isController() && theEvent.controller().name().equalsIgnoreCase("CaptchaCenterYSlider")) 
  {
    CaptchaCenterY = (int)(theEvent.controller().value());
    this.updateSample();
    //println("ControlEvent CaptchaCenterY :"+CaptchaCenterY);
  }
  //------------------------------------------------
  //------------------------------------------------
  //chech boxes:
  else if(theEvent.isGroup()&& theEvent.group().name().equalsIgnoreCase("CheckBoxs"))
  {
//    CaptchaCenterY = (int)(theEvent.controller().value());
    // see what are the ones and zeros in check boxes and filterFlags
    if(theEvent.group().arrayValue()[0] == 1)
    {
        filterFlags[0]= true;
    }
    else
    {
        filterFlags[0]= false;
    }
    if(theEvent.group().arrayValue()[1] == 1)
    {
        filterFlags[1]= true;
    }
    else
    {
        filterFlags[1]= false;
    }
    if(theEvent.group().arrayValue()[2] == 1)
    {
        filterFlags[2]= true;
    }
    else
    {
        filterFlags[2]= false;
    }
    if(theEvent.group().arrayValue()[3] == 1)
    {
        filterFlags[3]= true;
    }
    else
    {
        filterFlags[3]= false;
    }
    if(theEvent.group().arrayValue()[4] == 1)
    {
        filterFlags[4]= true;
    }
    else
    {
        filterFlags[4]= false;
    }
    if(theEvent.group().arrayValue()[5] == 1)
    {
        filterFlags[5]= true;
    }
    else
    {
        filterFlags[5]= false;

    }
        if(theEvent.group().arrayValue()[6] == 1)
    {
        filterFlags[6]= true;
    }
    else
    {
        filterFlags[6]= false;

    }
        if(theEvent.group().arrayValue()[7] == 1)
    {
        filterFlags[7]= true;
    }
    else
    {
        filterFlags[7]= false;

    }
        if(theEvent.group().arrayValue()[8] == 1)
    {
        filterFlags[8]= true;
    }
    else
    {
        filterFlags[8]= false;

    }
        if(theEvent.group().arrayValue()[9] == 1)
    {
        filterFlags[9]= true;
    }
    else
    {
        filterFlags[9]= false;

    }
        if(theEvent.group().arrayValue()[10] == 1)
    {
        filterFlags[10]= true;
    }
    else
    {
        filterFlags[10]= false;

    }
    //println(filterFlags);
    this.updateSample();// do a loop to see trues in chech boxes
    
    //println("ControlEvent check box # "+theEvent.group().toString()+" values to:"+ (int)(theEvent.group().controller("0").value()));
  }

  else if(theEvent.isController() && theEvent.controller().name().equalsIgnoreCase("Lines"))
  {
    toleranceArgs[0] = (float)(theEvent.controller().value());
    this.updateSample();
    //println("ControlEvent CaptchaCenterY :"+CaptchaCenterY);
  }
  else if(theEvent.isController() && theEvent.controller().name().equalsIgnoreCase("Twirl"))
  {
    toleranceArgs[1] = (float)(theEvent.controller().value());
    this.updateSample();
    //println("ControlEvent CaptchaCenterY :"+CaptchaCenterY);
  }
  else if(theEvent.isController() && theEvent.controller().name().equalsIgnoreCase("Ripple"))
  {
    toleranceArgs[2] = (float)(theEvent.controller().value());
    this.updateSample();
    //println("ControlEvent CaptchaCenterY :"+CaptchaCenterY);
  }
  else if(theEvent.isController() && theEvent.controller().name().equalsIgnoreCase("Pinch"))
  {
    toleranceArgs[3] = (float)(theEvent.controller().value());
    this.updateSample();
    //println("ControlEvent CaptchaCenterY :"+CaptchaCenterY);
  }
  else if(theEvent.isController() && theEvent.controller().name().equalsIgnoreCase("Marble"))
  {
    toleranceArgs[4] = (float)(theEvent.controller().value());
    this.updateSample();
    //println("ControlEvent CaptchaCenterY :"+CaptchaCenterY);
  }
  else if(theEvent.isController() && theEvent.controller().name().equalsIgnoreCase("Blur"))
  {
    toleranceArgs[5] = (float)(theEvent.controller().value());
    this.updateSample();
    //println("ControlEvent CaptchaCenterY :"+CaptchaCenterY);
  }
  else if(theEvent.isController() && theEvent.controller().name().equalsIgnoreCase("Thershold"))
  {
    toleranceArgs[6] = (float)(theEvent.controller().value());
    this.updateSample();
    //println("ControlEvent CaptchaCenterY :"+CaptchaCenterY);
  }
  else if(theEvent.isController() && theEvent.controller().name().equalsIgnoreCase("JCaptcha"))
  {
    toleranceArgs[7] = (float)(theEvent.controller().value());
    this.updateSample();
    //println("ControlEvent CaptchaCenterY :"+CaptchaCenterY);
  }
  else if(theEvent.isController() && theEvent.controller().name().equalsIgnoreCase("Halftone"))
  {
    toleranceArgs[8] = (float)(theEvent.controller().value());
    this.updateSample();
    //println("ControlEvent CaptchaCenterY :"+CaptchaCenterY);
  }
  else if(theEvent.isController() && theEvent.controller().name().equalsIgnoreCase("Block"))
  {
    toleranceArgs[9] = (int)(theEvent.controller().value());
    this.updateSample();
    //println("ControlEvent CaptchaCenterY :"+CaptchaCenterY);
  }
  else if(theEvent.isController() && theEvent.controller().name().equalsIgnoreCase("Pointillize"))
  {
    toleranceArgs[10] = (float)(theEvent.controller().value());
    this.updateSample();
    //println("ControlEvent CaptchaCenterY :"+CaptchaCenterY);
  }
  else if(theEvent.isGroup()&& theEvent.group().name().equalsIgnoreCase("RadioButton"))
  {
    if(theEvent.group().value() == 1)
    {
        imageType = "PNG";
    }
    else if(theEvent.group().value() == 2)
    {
        imageType = "JPEG";
    }
    else if(theEvent.group().value() == 3)
    {
        imageType = "GIF";
    }
  }

  else if(theEvent.isGroup()&& theEvent.group().name().equalsIgnoreCase("RadioButtonRecommended"))
  {
    if(theEvent.group().value() == 4)
    {
        setSetting(4);
    }
    else if(theEvent.group().value() == 5)
    {
        setSetting(5);
    }
    else if(theEvent.group().value() == 6)
    {
        setSetting(6);
    }
    else if(theEvent.group().value() == 7)
    {
        setSetting(7);
    }

  }
    //------------------------------------------------

}
//------------------------------------------------------------------------------
public void setSetting(int type)
{
    if(type== 4)
    {
        CaptchaWidth = 100;
        CaptchaHeight = 50;
        int i;
        for(i = 0 ; i<filterFlags.length;i++)
        {
            filterFlags[i] = false;
        }
        toleranceArgs[0] = 23;//lines
        toleranceArgs[3] = 0.1f;//pinch
        filterFlags[0] = true;
        filterFlags[3] = true;
        CaptchaFontSize = 28;
        
        widthOfCaptcha.setValue(100);
        HeightOfCaptcha.setValue(50);
        controlP5.controller("Font Size").setValue(28);
        FiltersCheckBoxes.controller("0").setValue(1);
        FiltersCheckBoxes.controller("3").setValue(1);
        FiltersCheckBoxes.controller("8").setValue(0);
        controlP5.controller("Lines").setValue(23);
        controlP5.controller("Pinch").setValue(0.1f);

//        if(controlP5.controller("realtime").value() > 0)
//        {controlP5.controller("realtime").setValue(1);}
        updateSample();
    }
    else if(type == 5) // mid
    {
        CaptchaWidth = 150;
        CaptchaHeight = 75;
        int i;
        for(i = 0 ; i<filterFlags.length;i++)
        {
            filterFlags[i] = false;
        }
        toleranceArgs[0] = 14.25f;
        toleranceArgs[3] = 0.1f;
        filterFlags[0] = true;
        filterFlags[3] = true;
        CaptchaFontSize = 42;

        widthOfCaptcha.setValue(150);
        HeightOfCaptcha.setValue(75);
        controlP5.controller("Font Size").setValue(42);
        FiltersCheckBoxes.controller("0").setValue(1);
        FiltersCheckBoxes.controller("3").setValue(1);
        FiltersCheckBoxes.controller("8").setValue(0);
        controlP5.controller("Lines").setValue(14.25f);
        controlP5.controller("Pinch").setValue(0.1f);

        updateSample();
    }
    else if(type == 6)
    {
        CaptchaWidth = 200;
        CaptchaHeight = 75;
        int i;
        for(i = 0 ; i<filterFlags.length;i++)
        {
            filterFlags[i] = false;

        }
        toleranceArgs[8] =1;
        toleranceArgs[3] = 0.1f;
        filterFlags[8] = true;
        filterFlags[3] = true;
        CaptchaFontSize = 44;

        widthOfCaptcha.setValue(200);
        HeightOfCaptcha.setValue(75);
        controlP5.controller("Font Size").setValue(44);
        FiltersCheckBoxes.controller("8").setValue(1);
        FiltersCheckBoxes.controller("3").setValue(1);
        FiltersCheckBoxes.controller("0").setValue(0);
        controlP5.controller("Halftone").setValue(1);
        controlP5.controller("Pinch").setValue(0.1f);

        updateSample();
    }
    else
    {
        CaptchaWidth = 300;
        CaptchaHeight = 57;
        widthOfCaptcha.setValue(300);
        HeightOfCaptcha.setValue(57);
        updateSample();
    }

}



//------------------------------------------------------------------------------

//------------------------------------------------------------------------------
public void updateSample()
{
//    samplePImage = new PImage(geny.generateArabicImage( CaptchaWidth,CaptchaHeight,defaultFontName,CaptchaFontSize,sampleWord));
    fontGlobal = new Font(defaultFontName, Font.PLAIN, CaptchaFontSize);
    if(realtimeFlag == true)
    {
        sampleWord = captchaSample.wordGen(CaptchaNumOfLetters, 1, false);
    }
    else if(CaptchaNumOfLetters<13)
    {
         sampleWord = smapleWordsArray[(int)(CaptchaNumOfLetters)-1];
    }
    else
    {
        sampleWord = "نهضة العربية";
    }
    captchaSample = new Captcha(this,CaptchaCenterX,
                                CaptchaCenterY,
                                CaptchaWidth,
                                CaptchaHeight,
                                fontGlobal,
                                sampleWord,
                                filterFlags,
                                toleranceArgs);
    
}

//------------------------------------------------------------------------------
//////////////////////////// -- SETUP -- ////////////////
@Override
  public void setup()
  {
    frame.setResizable(true);
    size(programWidth, programHeight);

    noStroke();
    smooth();
    background(100);
    frameRate(30);//revise this
    initLeftPanel(); // init the left control panel
    defaultFont = createFont("Tahoma", 30);
    
    imageMode(CENTER);
    fontGlobal = new Font(defaultFontName, Font.PLAIN, CaptchaFontSize);
    captchaSample = new Captcha(this,CaptchaCenterX,
                                    CaptchaCenterY,
                                    CaptchaWidth,
                                    CaptchaHeight,
                                    fontGlobal,
                                    sampleWord,
                                    filterFlags,
                                    toleranceArgs);

  }

  //------------------------------------------------------------------------------
  ///////////////////////// draw ////////////////////////////////////////////////////
@Override
  public void draw()
  {
    background(100);
    //image(cptsh.srcImg, (int)(programWidth/2), (int)(programHeight/2));
    fill(140);
    
    //	rect(x, y, width, height)
    rect(leftPanelStarting,0,leftPanelSize,frame.getHeight());
    fill(255);
    if(realtimeFlag == true)
    {
        updateSample();
    }
    image(captchaSample.distortedImg,((programWidth-leftPanelSize)/2),((programHeight)/2));
//    textSize(2);
//    if(progressFlag)
//    {
//        text("creating Captcha # "+progressAmount,10,10);
//    }

    //    rectMode(CENTER);
    //image(temp, 80, 90);
    //drawSample();
//    textFont(defaultFont);
//    text("نهضة العربية", 20, 40);
    // for optimization change this to update only if change controls
    //controlP5.draw();
    
  }
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------

  public static void main(String args[])
  {

    PApplet.main(new String[] {"arcaptcha.Main" });

    //PApplet.main(new String[] { "--present", "arcaptcha.Main" });
  }
//------------------------------------------------------------------------------
}