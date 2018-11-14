package a6test.qishun96;

import java.util.List;

import a6.ROIObserver;
import a6.Region;

public interface ObserverHelper extends ROIObserver {

	public int getCount();
	
	public List<Region> getNotifiedRegion();
	
}
