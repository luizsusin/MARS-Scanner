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
package mars.tools;

import java.util.Observable;
import java.util.Observer;

import mars.Globals;
import mars.mips.hardware.AccessNotice;
import mars.mips.hardware.AddressErrorException;
import mars.mips.hardware.MemoryAccessNotice;
import mars.tools.marsscanner.ScannerRunnable;

/**
 * @author Luiz H. Susin
 * @date 14/09/2018
 * @time 12:46 AM
 */
public class MARSScanner implements MarsTool, Observer 
{
	/** Start Address used on MMIO */
	private static final int ADDR_STARTRANGE = 0xffff9000;
	/** Final Address used on MMIO */
	private static final int ADDR_ENDRANGE = 0xffff9020;

	/** Address used to pop a point from the list */
	public static final int ADDR_JUMPVALUE = 0xffff9000;

	/** Address used to get the point to mark on X axis */
	public static final int ADDR_NEXTX = 0xffff9010;
	/** Address used to get the point to mark on Y axis */
	public static final int ADDR_NEXTY = 0xffff9020;

	public static ScannerRunnable sInstance;

	/**
	 * @return The name of the tool
	 */
	@Override
	public String getName() 
	{
		return "MARS Scanner";
	}

	/**
	 * Exhibits the tool's interface and start monitoring the memory addresses
	 */
	@Override
	public void action() 
	{
		try 
		{
			ScannerRunnable sRun = new ScannerRunnable();
			sInstance = sRun;

			Thread t1 = new Thread(sRun);
			t1.start();

			Globals.memory.addObserver(this, ADDR_STARTRANGE, ADDR_ENDRANGE);
		} 
		catch (Exception e) { e.printStackTrace();}
	}

	/**
	 * Monitor the memory addresses and processes its requests on demand
	 */
	public void update(Observable o, Object arg) 
	{
		if (arg instanceof MemoryAccessNotice) 
		{
			MemoryAccessNotice notice = (MemoryAccessNotice) arg;
			int address = notice.getAddress();

			if (address < 0 && notice.getAccessType() == AccessNotice.WRITE) 
			{
				if (address == ADDR_JUMPVALUE) 
				{
					if (notice.getValue() == 1) 
					{
						try 
						{
							Globals.memory.setWord(ADDR_NEXTX, sInstance.getTracePoints().get(0).x);
							Globals.memory.setWord(ADDR_NEXTY, sInstance.getTracePoints().get(0).y);

							sInstance.getTracePoints().remove(0);
						} 
						catch (AddressErrorException e1) { e1.printStackTrace(); }
					}
				}
			}
		}
	}
}
