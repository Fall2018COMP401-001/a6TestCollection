package a6test.koumi;

import a6.*;
public interface ROIObserver {
	
	public void notify(ObservablePicture picture, Region changed_region) ;
	
	public int getCount();
}

