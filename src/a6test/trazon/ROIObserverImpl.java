package a6test.trazon;

import a6.*;

public class ROIObserverImpl implements ROIObserver {

	private int timesNotified;
	
	
	public void notify(ObservablePicture p, Region changed_region) {
		++timesNotified;
	}
	
	public ROIObserverImpl() {
		this.timesNotified = timesNotified;
	}
	
	public int TimesNotified() {
		return timesNotified;
	}
}

