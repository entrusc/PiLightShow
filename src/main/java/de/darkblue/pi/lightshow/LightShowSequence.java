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

import de.darkblue.pi.lightshow.action.Action;
import de.darkblue.pi.lightshow.effect.Effect;
import java.awt.Color;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Florian Frankenberger
 */
public class LightShowSequence {

    private final Mood mood;
    
    private final List<Effect> effects = new ArrayList<Effect>();
    private final int repetitions;
    
    private final Random random;
    private final List<Color> primaryColors;
    

    public LightShowSequence(Random random, Mood mood, List<Color> primaryColors, int repetitions) {
        this.random = random;
        this.mood = mood;
        this.primaryColors = primaryColors;
        this.repetitions = repetitions;
    }
    
    public void addEffect(Class<? extends Effect> effectType) throws NoSuchMethodException, InstantiationException, 
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final Constructor<? extends Effect> constructor = effectType.getConstructor(Random.class, Mood.class, List.class);
        final Effect effect = constructor.newInstance(random, mood, primaryColors);
        
        this.effects.add(effect);
    }
    
    public void addEffect(Effect effect) {
        this.effects.add(effect);
    }

    /**
     * unwraps all effects actions to a list of action repeated
     * by the given repetitions
     * @return 
     */
    public List<Action> getActions() {
        List<Action> actions = new ArrayList<Action>();
        for (int i = 0; i < repetitions; ++i) {
            for (final Effect effect : this.effects) {
                actions.addAll(effect.getActions());
            }
        }    
        
        return actions;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("LighShowSequence {\n");
        sb.append("\t").append("repetitions: ").append(repetitions).append(",\n");
        sb.append("\t").append("colors: ").append(primaryColors).append(",\n");
        sb.append("\t").append("effects: ").append("\n");
        for (Effect effect : effects) {
            sb.append("\t\t").append(effect.toString()).append('\n');
        }
        sb.append("}\n");
        
        return sb.toString();
    }
    
}
