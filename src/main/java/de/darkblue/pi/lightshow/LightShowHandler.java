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
import de.darkblue.pi.lightshow.action.SetColorAction;
import de.darkblue.pi.lightshow.action.SleepAction;
import de.darkblue.pi.lightshow.effect.ActiveEffect;
import de.darkblue.pi.lightshow.effect.Effect;
import de.darkblue.pi.lightshow.utils.Utils;
import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.xml.parsers.ParserConfigurationException;
import org.reflections.Reflections;

/**
 * Creates a wonderful light show :)
 * 
 * @author Florian Frankenberger
 */
public class LightShowHandler extends Thread {
    
    private final static Reflections REFLECTIONS = new Reflections("de.darkblue.pi.lightshow.effect");    
    
    private static final int MIN_ADJACENT_COLORS = 1;
    private static final int MAX_ADJACENT_COLORS = 5;
    
    private static final int MIN_EFFECTS_PER_SEQUENCE = 4;
    private static final int MAX_EFFECTS_PER_SEQUENCE = 10;
    
    private static final int MIN_SEQUENCE_REPETITIONS = 2;
    private static final int MAX_SEQUENCE_REPETITIONS = 6;

    private TimeRange offDuringTime = null;
    private final Random random = new Random();
    private boolean shutdown = false;
    
    private final List<Class<? extends Effect>> activeEffects = new ArrayList<Class<? extends Effect>>();
    private final Queue<Action> actions = new ConcurrentLinkedQueue<Action>();
    private final ColorDisplay colorDisplay;
    
    public LightShowHandler(ColorDisplay colorDisplay) throws ParserConfigurationException {
        this.colorDisplay = colorDisplay;
        initEffects();
    }

    /**
     * sets the time range during the light should be switched off
     * @param offDuringTime 
     */
    public void setOffDuringTime(TimeRange offDuringTime) {
        this.offDuringTime = offDuringTime;
    }
    
    private void initEffects() {
        for (final Class<?> clazz : REFLECTIONS.getSubTypesOf(Effect.class)) {
            if (clazz.getAnnotation(ActiveEffect.class) != null) {
                activeEffects.add((Class<? extends Effect>) clazz);
            }
        }
    }

    @Override
    public void run() {
        
        try {
            while (!shutdown) {

                try {
                    if (actions.isEmpty()) {
                        boolean createNew = true;
                        if (offDuringTime != null) {
                            final float currentHour = Utils.getCurrentHour();
                            if (currentHour >= offDuringTime.getFromHour()
                                    && currentHour <= offDuringTime.getToHour()) {
                                createNew = false;
                                actions.add(new SetColorAction(Color.BLACK));
                                actions.add(new SleepAction(1000));
                            }
                        }
                        
                        if (createNew) {
                            final LightShowSequence currentSequence = createNewSequence();
                            actions.addAll(currentSequence.getActions());
                        }
                    }

                    final Action action = actions.poll();
                    if (action != null) {
                        action.execute(colorDisplay);
                    }
                } catch (RuntimeException e) {
                    throw e;
                } catch (Exception e) {
                    System.out.println("Problem: " + e);
                }
            }
        } catch (Exception e) {
            System.out.println("Severe problem: " + e);
            e.printStackTrace(System.out);
        }
    }
    
    private LightShowSequence createNewSequence() throws NoSuchMethodException, InstantiationException, 
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        //1. create new colors
        final List<Color> primaryColors = chooseColors();
        
        //2. choose effects
        final int effectAmount = random.nextInt(MAX_EFFECTS_PER_SEQUENCE - MIN_EFFECTS_PER_SEQUENCE) + MIN_EFFECTS_PER_SEQUENCE;
        final int repetitions = random.nextInt(MAX_SEQUENCE_REPETITIONS - MIN_SEQUENCE_REPETITIONS) + MIN_SEQUENCE_REPETITIONS;
        final LightShowSequence sequence = new LightShowSequence(random, Mood.getRandom(random), primaryColors, repetitions);
        
        for (int i = 0; i < effectAmount; ++i) {
            final Class<? extends Effect> effect = activeEffects.get(random.nextInt(activeEffects.size()));
            sequence.addEffect(effect);
        }
        
        return sequence;
    }
    
    private List<Color> chooseColors() {
        final List<Color> colors = new ArrayList<Color>();
        
        //choose first color
        final Color firstColor = Color.getHSBColor(random.nextFloat(), 1.0f, 1.0f);
        colors.add(firstColor);
        
        final int adjacentColorsAmount = random.nextInt(MAX_ADJACENT_COLORS - MIN_ADJACENT_COLORS) + MIN_ADJACENT_COLORS;
        colors.addAll(getAdjecentColors(firstColor, adjacentColorsAmount));
        
        return colors;
    }
    
    private List<Color> getAdjecentColors(Color color, int amount) {
        List<Color> colors = new ArrayList<Color>();
        
        final float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        float h = hsb[0];
        float s = hsb[1];
        float b = hsb[2];
        final float distance = 1.0f / (float) (amount + 1);
        
        for (int i = 0; i < amount; ++i) {
            h = (h + distance) % 1.0f;
            colors.add(Color.getHSBColor(h, s, b));
        }
        
        return colors;
    }
    
}
