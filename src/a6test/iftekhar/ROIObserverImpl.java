package a6test.iftekhar;

import a6.ObservablePicture;
import a6.ROIObserver;
import a6.Region;

public class ROIObserverImpl implements ROIObserver {

	@Override
	public void notify(ObservablePicture picture, Region changed_region) {
		String thisRegion = "Region Code (LTRB): " + 
							changed_region.getLeft() + 
							changed_region.getTop() + 
							changed_region.getRight() + 
							changed_region.getBottom() + 
							"\n New Pixel: " +
							picture.getPixel(changed_region.getLeft(), changed_region.getRight()); 
		System.out.println(thisRegion);
	}

}
