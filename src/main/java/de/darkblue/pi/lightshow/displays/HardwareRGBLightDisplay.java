/*
 * Copyright (C) 2012-2013 Florian Frankenberger
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.darkblue.pi.lightshow.displays;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.SoftPwm;
import de.darkblue.pi.lightshow.ColorDisplay;
import java.awt.Color;

/**
 * The hardware display of the Raspberry PI. The RGB-(HighPower)-LED needs
 * to be connected to the IO PINs denoted by the RED_PIN, GREEN_PIN and BLUE_PIN
 * constant.
 * 
 * The wiring infos can be found here http://pi4j.com/example/control.html#Wiring_Diagram
 * 
 * @author Florian Frankenberger
 */
public class HardwareRGBLightDisplay implements ColorDisplay {

    private static final Pin RED_PIN = RaspiPin.GPIO_00;
    private static final Pin GREEN_PIN = RaspiPin.GPIO_01;
    private static final Pin BLUE_PIN = RaspiPin.GPIO_02;
    
    private Color color = Color.BLACK;

    public HardwareRGBLightDisplay() {
        
        final GpioController gpio = GpioFactory.getInstance();

        final GpioPinDigitalOutput ledRed = gpio.provisionDigitalOutputPin(RED_PIN);
        final GpioPinDigitalOutput ledGreen = gpio.provisionDigitalOutputPin(GREEN_PIN);
        final GpioPinDigitalOutput ledBlue = gpio.provisionDigitalOutputPin(BLUE_PIN);
        
        ledRed.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
        ledGreen.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
        ledBlue.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
        
        SoftPwm.softPwmCreate(RED_PIN.getAddress(), 0, 50);
        SoftPwm.softPwmCreate(GREEN_PIN.getAddress(), 0, 50);
        SoftPwm.softPwmCreate(BLUE_PIN.getAddress(), 0, 50);
        displayColor(color);
    }
    
    @Override
    public final void displayColor(Color color) {
        final float[] colors = color.getRGBColorComponents(null);
        SoftPwm.softPwmWrite(RED_PIN.getAddress(), (int) (colors[0] * 50f));
        SoftPwm.softPwmWrite(GREEN_PIN.getAddress(), (int) (colors[1] * 50f));
        SoftPwm.softPwmWrite(BLUE_PIN.getAddress(), (int) (colors[2] * 50f));
        this.color = color;
    }

    @Override
    public Color getCurrentColor() {
        return color;
    }
    
}
