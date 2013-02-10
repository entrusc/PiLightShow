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
package de.darkblue.pi.lightshow.effect;

import de.darkblue.pi.lightshow.Mood;
import de.darkblue.pi.lightshow.action.Action;
import de.darkblue.pi.lightshow.action.SetColorAction;
import de.darkblue.pi.lightshow.action.SleepAction;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Florian Frankenberger
 */
@ActiveEffect
public class BlinkEffect extends Effect {

    private final long blinkTime;
    
    public BlinkEffect(Random random, Mood mood, List<Color> primaryColors) {
        super(random, mood, primaryColors);
        blinkTime = mood.getNewRandomSpeed(random);
    }

    @Override
    public List<Action> getActions() {
        final List<Action> actions = new ArrayList<Action>();
        for (final Color primaryColor : getPrimaryColors()) {
            actions.add(new SetColorAction(primaryColor));
        }
        
        return implode(new SleepAction(blinkTime), actions);
    }

}
