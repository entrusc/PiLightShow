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
package de.darkblue.pi.lightshow.utils;

import java.util.Calendar;

/**
 *
 * @author Florian Frankenberger
 */
public class Utils {

    private Utils() {
    }

    public static int getGCD(int a, int b) {
        while (a != b) {
            if (a > b) {
                a = a - b;
            } else {
                b = b - a;
            }
        }
        return a;
    }
    
    public static float getCurrentHour() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.HOUR_OF_DAY) + cal.get(Calendar.MINUTE) / 60f;
    }
    
    /**
     * checks if the given string is numeric.
     * 
     * @param str
     * @return 
     */
    public static boolean isNumeric(String str) {
        //I know control flow through exception handling is
        //bad - but this is a quick to implement and safe way to check it.
        try {
            Float.valueOf(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
}
