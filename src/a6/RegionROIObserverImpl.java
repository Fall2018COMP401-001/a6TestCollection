package a6;

public class RegionROIObserverImpl implements RegionROIObserver {
    
	private Region region;
	private ROIObserver observer; 
	
	public RegionROIObserverImpl(ROIObserver observer, Region region){ 
		if (region == null || observer == null) {
			throw new IllegalArgumentException("null parameter");
		}
		this.observer = observer;
		this.region = region;
	}
	@Override
	public void notify(ObservablePicture picture, Region changed) {
		observer.notify(picture, changed);

	}

	@Override
	public Region getRegion() {
		return this.region;
	}

	@Override
	public ROIObserver getWrappedROIObserver() {
		return this.observer;
	}


}
