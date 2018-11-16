package a6test.zricky;

import a6.ObservablePicture;


public class ROIObserverImpl implements ROIObserver {

	@Override
	public void notify(ObservablePicture picture, A6Tests changed_region) {
		System.out.println("Region of interest has changed");
	}

}