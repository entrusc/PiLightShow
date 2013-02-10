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
public class SetColorAction extends Action {

    private final Color color;

    public SetColorAction(Color color) {
        this.color = color;
    }

    @Override
    public void execute(ColorDisplay colorDisplay) throws Exception {
        colorDisplay.displayColor(color);
    }
    
}
