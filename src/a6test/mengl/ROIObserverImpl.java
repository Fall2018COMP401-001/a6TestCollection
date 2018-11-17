package a6test.mengl;

import a6.ObservablePicture;
import a6.ROIObserver;
import a6.Region;

public class ROIObserverImpl implements ROIObserver {

	@Override
	public void notify(ObservablePicture picture, Region changed_region) {
		System.out.println("Region Change");
	}
}
