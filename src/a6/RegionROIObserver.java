package a6;

public interface RegionROIObserver extends ROIObserver {
	
	public Region getRegion(); 
	public ROIObserver getWrappedROIObserver();

}
