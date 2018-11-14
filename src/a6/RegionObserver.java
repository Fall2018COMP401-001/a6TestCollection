package a6;

public interface RegionObserver {
	
	ROIObserver getObserver();
	
	Region[] getRegions();
	
	void addRegion(Region r);

	void notify(ObservablePicture picture, Region changed_region);
	
}
