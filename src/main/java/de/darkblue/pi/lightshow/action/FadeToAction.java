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

package de.darkblue.pi.lightshow.action;

import de.darkblue.pi.lightshow.ColorDisplay;
import java.awt.Color;

/**
 *
 * @author Florian Frankenberger
 */
public class FadeToAction extends Action {

    private final long duration;
    private final Color toColor;
    
    public FadeToAction(long duration, Color toColor) {
        this.duration = duration;
        this.toColor = toColor;
    }
    
    @Override
    public void execute(ColorDisplay colorDisplay) throws Exception {
        final float[] steps = new float[3];
        final long startTime = System.currentTimeMillis();
        final float[] colorCurrentComponents = colorDisplay.getCurrentColor().getColorComponents(null);
        final float[] toColorComponent = toColor.getColorComponents(null);

        for (int i = 0; i < 3; ++i) {
            steps[i] = (toColorComponent[i] - colorCurrentComponents[i]) / duration;
        }
        
        long passedTime;
        do {
            passedTime = System.currentTimeMillis() - startTime;
            final Color color = new Color(
                        (float) Math.max(0, Math.min(1.0, colorCurrentComponents[0] + passedTime * steps[0])),
                        (float) Math.max(0, Math.min(1.0, colorCurrentComponents[1] + passedTime * steps[1])),
                        (float) Math.max(0, Math.min(1.0, colorCurrentComponents[2] + passedTime * steps[2]))
                    );
            colorDisplay.displayColor(color);
            Thread.sleep(1);
        } while (passedTime <= duration);
        
    }
    
}
