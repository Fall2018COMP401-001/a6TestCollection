package a6;

public class ObserverAndRegion {

	private ROIObserver o; 
	private Region r;
	
	public ObserverAndRegion(ROIObserver o, Region r) {
		this.o = o;
		this.r = r;
	}
	
	public ROIObserver getObserver() {
		return o;
	}
	
	public Region getRegion() {
		return r;
	}
}
