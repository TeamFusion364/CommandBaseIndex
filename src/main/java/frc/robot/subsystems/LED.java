
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LED extends SubsystemBase{    
    private AddressableLED ledStrip;
    private AddressableLEDBuffer ledBuffer;

    private boolean override = false;
    private Color overrideColor;
    private double overrideTime;
    private double overrideStartTime;    
    private int m_rainbowFirstPixelHue = 0;
    

    double length; //TODO - total number of LEDS in the strip
    double port;  //TODO - PWM port the LED strip is connected to

    

    public LED() {
      
        ledStrip = new AddressableLED(port);
        ledBuffer = new AddressableLEDBuffer(length);
        ledStrip.setLength(ledBuffer.getLength());
        ledStrip.setData(ledBuffer);
        ledStrip.start();
    }

    /**
     * Sets the Entire Led strip a solid color
     * */
    public void setSolidColor(Color color){
        for (var i = 0; i < ledBuffer.getLength(); i++) {
            ledBuffer.setLED(i, color);
         }
        ledStrip.setData(ledBuffer);
    }

    /**
     * Ovveride the LED Logic with a staic color for a set time
     * @param color Color
     * @param time Time to ovveride in milliseconds
     */
    public void overrideColor(Color color, double time){
        override = true;
        overrideColor = color;
        overrideStartTime = (Timer.getFPGATimestamp() * 1000);
        overrideTime = time;
    }

    /** 
     * Blinks the entire Led strip a solid color
     * @param color Color
     * @param time time between Blinks in milliseconds
     */
    public void blinkSolidColor(Color color, double time){
        if (((Timer.getFPGATimestamp() * 1000) % (time*2)) > time){
            setSolidColor(color);
        }
        else{
            setSolidColor(Color.kBlack);
        }
    }

    public void waveSolidColor(Color color, double time){
        for (var i = 0; i < ledBuffer.getLength(); i++) {
            final var brightness = (i * 180 / ledBuffer.getLength()) % 180;
            ledBuffer.setRGB(i, brightness, 255, brightness);
        }
    }

    public void rainbow() {
        for (var i = 0; i < ledBuffer.getLength(); i++) {
            final var hue = (m_rainbowFirstPixelHue + (i * 180 / ledBuffer.getLength())) % 180;
            ledBuffer.setHSV(i, hue, 255, 128);
        }
        m_rainbowFirstPixelHue += 3;
        m_rainbowFirstPixelHue %= 180;

        ledStrip.setData(ledBuffer);
    }

    public Color toRBG(Color color){
        return new Color(color.red, color.blue, color.green);
    }

    @Override
    public void periodic() {
        
        /* Not enabled code */
        if(DriverStation.isDisabled() && DriverStation.isDSAttached()){
          blinkSolidColor(Color.kGreen, 500); //If robot is disabled & Driver Station is connected
        
        }
        else if(DriverStation.isDisabled() && !DriverStation.isDSAttached()){
            blinkSolidColor(Color.kRed, 500); //If robot is disabled & Driver Station is not connected
        }
        else if(DriverStation.isEnabled() && DriverStation.isDSAttached()){
          rainbow(); //If robot is disabled & Driver Station is disconnected
      }
        /* Override */
        else if(override){ 
            if((Timer.getFPGATimestamp() * 1000) > (overrideStartTime + overrideTime)){
                override = false;
            }
            else{
                blinkSolidColor(overrideColor, 100);
            }
        }
        /* Enabled Code */
        
      }
}
