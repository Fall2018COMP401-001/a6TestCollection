package a6test.jennzli;

import a6.*;

public class ROIObserverImpl implements ROIObserver {
	private int count;
	
	public ROIObserverImpl() {
		count = 0;
	}

	@Override
	public void notify(ObservablePicture picture, Region changed_region) {
		count++;
	}
	
	public int getCount() { 
		return count;
	}
	
	public void clearCount() {
		count = 0;
	}

}
