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
package de.darkblue.pi.lightshow;

import de.darkblue.pi.lightshow.displays.DebugFormDisplay;
import de.darkblue.pi.lightshow.displays.HardwareRGBLightDisplay;
import de.darkblue.pi.lightshow.utils.Utils;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Entry Point
 */
public class App {

    public static void main(String[] args) throws Exception {
        ColorDisplay display;
        Set<String> arguments = new LinkedHashSet<String>(Arrays.asList(args));
        
        if (arguments.contains("debug")) {
            DebugFormDisplay debugDisplay = new DebugFormDisplay();
            debugDisplay.setVisible(true);

            display = debugDisplay;
        } else {
            display = new HardwareRGBLightDisplay();
        }
        
        TimeRange offTimeRange = null;
        for (int i = 0; i < args.length - 1; ++i) {
            if (Utils.isNumeric(args[i]) && i + 1 < args.length && Utils.isNumeric(args[i + 1])) {
                final Float fromTime = Float.valueOf(args[i]);
                final Float toTime = Float.valueOf(args[i + 1]);
                offTimeRange = new FixedTimeRange(fromTime, toTime);
                System.out.println("Off from " + fromTime + " - " + toTime);
            }
        }
        
        final LightShowHandler lightHandler = new LightShowHandler(display);
        lightHandler.setOffDuringTime(offTimeRange);
        lightHandler.start();
    }

}
