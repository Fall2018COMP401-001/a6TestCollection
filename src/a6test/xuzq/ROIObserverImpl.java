package a6test.xuzq;

import a6.ObservablePicture;
import a6.ROIObserver;
import a6.Region;

public class ROIObserverImpl implements ROIObserver {

	@Override
	public void notify(ObservablePicture picture, Region changed_region) {
		// TODO Auto-generated method stub
		System.out.printf("region change for region (%d, %d, %d, %d)", changed_region.getLeft(),
				changed_region.getRight(), changed_region.getRight(), changed_region.getBottom());

	}

}
