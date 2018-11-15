package a6test.gruehle;

import a6.*;

public class ROIObserverImpl implements ROIObserver {

	private int timesNotified;
	
	public ROIObserverImpl() {
		timesNotified = 0;
	}
	
	public void notify(ObservablePicture picture, Region changed_region) {
		timesNotified++;
	}
	
	public int getTimesNotified() {
		return timesNotified;
	}
}

