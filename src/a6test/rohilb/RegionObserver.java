package a6test.rohilb;

public class RegionObserver implements ROIObserver {
	private ROIObserver observer;
	private Region r;

	public RegionObserver(ROIObserver observer, Region r) {
		this.observer = observer;
		this.r = r;
	}
	
	public void notify(ObservablePicture picture, Region changed_region) {
		observer.notify(picture, changed_region);
	}
	
	public ROIObserver getObserver() {
		return observer;
	}
	
	public Region getObservedRegion() {
		return r;
	}
	
}
