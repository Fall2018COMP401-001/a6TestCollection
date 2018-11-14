package a6test.wewei1;

import java.util.ArrayList;
import java.util.List;

import a6.ObservablePicture;
import a6.ROIObserver;
import a6.Region;

public class ObserverNotifyImpl implements ObserverNotify {
	
	private ROIObserver o;
	private List<Region> NotifiedRegion;
	
	public ObserverNotifyImpl(ROIObserver o) {
		this.o = o;
		this.NotifiedRegion = new ArrayList<Region>();
	}
	
	@Override
	public void notify(ObservablePicture picture, Region changed_region) {
		o.notify(picture, changed_region);
		NotifiedRegion.add(changed_region);
	}

	@Override
	public int getCount() {
		return NotifiedRegion.size();
	}

	@Override
	public List<Region> NotifiedRegion() {
		return NotifiedRegion;
	}

}