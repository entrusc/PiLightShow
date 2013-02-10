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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * An object that has an automatic toString() implementation
 * 
 * @author Florian Frankenberger
 */
public abstract class DescribableObject {

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName()).append('{');
        try {
            boolean first = true;
            for (final Field field : this.getClass().getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    if (first) {
                        first = false;
                    } else {
                        sb.append(", ");
                    }
                    field.setAccessible(true);
                    sb.append(field.getName()).append("=").append(field.get(this));
                }
            }
        } catch (Exception e) {
            //ignore
        }
        sb.append('}');
        return sb.toString();
    }    
    
}
