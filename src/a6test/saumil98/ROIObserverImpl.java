package a6test.saumil98;

import a6.ObservablePicture;
import a6.ROIObserver;
import a6.Region;

public class ROIObserverImpl implements ROIObserver{
	
	private int notifiedCounter = 0;

	public void notify(ObservablePicture picture, Region changedRegion) {
		
		notifiedCounter++;
		
	}
	
	public int getNotifiedCounter() {
		return notifiedCounter;
	}
	
	public void resetCounter() {
		notifiedCounter = 0;
	}

}
