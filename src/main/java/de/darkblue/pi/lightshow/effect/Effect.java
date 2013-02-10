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

import de.darkblue.pi.lightshow.DescribableObject;
import de.darkblue.pi.lightshow.Mood;
import de.darkblue.pi.lightshow.action.Action;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Florian Frankenberger
 */
public abstract class Effect extends DescribableObject {

    private final Random random;
    private final Mood mood;
    private final List<Color> primaryColors;
    
    private long effectRunTime = 0L;
    
    public Effect(Random random, Mood mood, List<Color> primaryColors) {
        this.random = random;
        this.mood = mood;
        this.primaryColors = primaryColors;
    }

    protected List<Color> getPrimaryColors() {
        return primaryColors;
    }

    protected Random getRandom() {
        return random;
    }

    public Mood getMood() {
        return mood;
    }

    public long getEffectRunTime() {
        return effectRunTime;
    }

    public abstract List<Action> getActions();

    protected static List<Action> implode(Action separator, Collection<Action> actions) {
        return implode(separator, actions.toArray(new Action[0]));
    }
    
    protected static List<Action> implode(Action separator, Action... actions) {
        final List<Action> result = new ArrayList<Action>();
        boolean first = false;
        for (final Action action : actions) {
            if (first) {
                first = false;
            } else {
                result.add(separator);
            }
            result.add(action);
        }
        return result;
    }
    
}
