package mars.tools;

import java.util.Observable;
import java.util.Observer;

import mars.Globals;
import mars.mips.hardware.AccessNotice;
import mars.mips.hardware.AddressErrorException;
import mars.mips.hardware.MemoryAccessNotice;
import mars.tools.marsscanner.ScannerRunnable;

public class MARSScanner implements MarsTool, Observer
{
	private static final int ADDR_STARTRANGE = 0xffff9000;
	private static final int ADDR_ENDRANGE   = 0xffff9080;
	
	public static final int ADDR_JUMPVALUE   = 0xffff9000;
	
	public static final int ADDR_STARTX      = 0xffff9020;
	public static final int ADDR_STARTY      = 0xffff9040;
	
	public static final int ADDR_NEXTX       = 0xffff9060;
	public static final int ADDR_NEXTY       = 0xffff9080;
	
	public static ScannerRunnable sInstance;
	
	@Override
	public String getName() 
    {
		return "MARS Scanner";
	}
	
	public String getVersion()
	{
		return "Rev 1.6";
	}

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
		catch (Exception e) { e.printStackTrace(); }
	}

	public void update(Observable o, Object arg) 
	{
		if(arg instanceof MemoryAccessNotice)
		{
			MemoryAccessNotice notice = (MemoryAccessNotice) arg;
			int address = notice.getAddress();
			
			if(address < 0 && notice.getAccessType() == AccessNotice.WRITE)
            {
				if(address == ADDR_JUMPVALUE)
				{
					if(notice.getValue() == 1)
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
