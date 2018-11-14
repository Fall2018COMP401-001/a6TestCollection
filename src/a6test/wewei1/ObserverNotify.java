package a6test.wewei1;

import java.util.List;
import a6.ROIObserver;
import a6.Region;

public interface ObserverNotify extends ROIObserver {

		public int getCount();
		
		public List<Region> NotifiedRegion();
		
}