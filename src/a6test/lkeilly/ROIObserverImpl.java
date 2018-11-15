package a6test.lkeilly;

import a6.*;
import a6.ObservablePicture;
import a6.ROIObserver;
import a6.Region;

public class ROIObserverImpl implements ROIObserver {
	private int counter;

	public ROIObserverImpl() {
		counter = 0;
	}

	public int getCount() {
		return counter;
	}

	public void notify(ObservablePicture picture, Region changed_region) {
		counter++;
	}

	public void clearCounter() {
		counter = 0;
	}
}
