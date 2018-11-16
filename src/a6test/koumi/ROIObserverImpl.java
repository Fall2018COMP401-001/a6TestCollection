package a6test.koumi;

import a6.*;

public class ROIObserverImpl implements ROIObserver {

	private int _count = 0;
	
	
	@Override
	public void notify(ObservablePicture picture, Region changed_region) {
		_count ++;
	}

	public int getCount() {
		return _count;
	}
 }
