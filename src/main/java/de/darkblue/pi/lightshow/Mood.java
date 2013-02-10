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

import java.util.Random;

/**
 *
 * @author Florian Frankenberger
 */
public enum Mood {
    
    SLOW(2000, 3800),
    
    MEDIUM(1000, 2000),
    
    FAST(200, 1000);

    private final int speedMin;
    private final int speedMax;
    
    private Mood(int speedMin, int speedMax) {
        this.speedMin = speedMin;
        this.speedMax = speedMax;
    }
    
    public int getNewRandomSpeed(Random random) {
        return random.nextInt(speedMax - speedMin) + speedMin;
    }
    
    public static Mood getRandom(Random random) {
        return values()[random.nextInt(values().length)];
    }
    
}
