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

/**
 * 
 * 
 * @author Florian Frankenberger
 */
public class FixedTimeRange implements TimeRange {

    private final float fromHour;
    private final float toHour;

    public FixedTimeRange(float fromHour, float toHour) {
        if (fromHour > toHour) {
            throw new IllegalArgumentException("fromHour needs to be smaller or equal to toHour");
        }
        this.fromHour = fromHour;
        this.toHour = toHour;
    }
    
    @Override
    public float getFromHour() {
        return fromHour;
    }

    @Override
    public float getToHour() {
        return toHour;
    }

}
