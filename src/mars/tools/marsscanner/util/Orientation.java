/**
 * MARS Scanner
 * Copyright (C) 2018 Luiz H. Susin [https://github.com/luizsusin/MARS-Scanner]
 *
 * Developed to be used with MARS (MIPS Assembler and Runtime Simulator)
 * Copyright (c) 2003-2013, Pete Sanderson and Kenneth Vollmar
 *
 * This program is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 3.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package mars.tools.marsscanner.util;

/**
 * @author Luiz H. Susin
 * @date 25/09/2018
 * @time 11:55 PM
 * */
public class Orientation 
{
	/**
	 * Used to relate the item with its name on dropdown menus
	 * */
	public class HorizontalOrientation 
	{
		public static final String LEFT = "Left";
		public static final String CENTER = "Center";
		public static final String RIGHT = "Right";
	}
	
	/**
	 * Used to relate the item with its name on dropdown menus
	 * */
	public class VerticalOrientation 
	{
		public static final String UP = "Up";
		public static final String CENTER = "Center";
		public static final String DOWN = "Down";
	}
}
