package a6test.jingyz;

import a6.ObservablePicture;
import a6.ROIObserver;
import a6.Region;

/*
 * Only for this test, not real implementation
 */
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
		return this.count;
	}
}
