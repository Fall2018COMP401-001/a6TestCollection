package a6;

import java.util.List;
import java.util.ArrayList;

public class RegionObserverImpl implements RegionObserver {
	
	private List<Region> _regions;
	private ROIObserver _observer;

	public RegionObserverImpl(ROIObserver o) {
		_regions = new ArrayList<Region>();
		_observer = o;
	}
	
	public ROIObserver getObserver() {
		return _observer;
	}
	
	public Region[] getRegions() {
		return _regions.toArray(new Region[_regions.size()]);
	}
	
	public void addRegion(Region r) {
		_regions.add(r);
	}

	public void notify(ObservablePicture picture, Region changed_region) {
		_observer.notify(picture, changed_region);		
	}

}
