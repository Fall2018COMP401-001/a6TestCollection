package a6test.qishun96;

import java.util.ArrayList;
import java.util.List;

import a6.ObservablePicture;
import a6.ROIObserver;
import a6.Region;

public class ObserverHelperImpl implements ObserverHelper {
	
	private ROIObserver _o;
	private List<Region> NotifiedRegions;
	
	public ObserverHelperImpl(ROIObserver o) {
		_o = o;
		NotifiedRegions = new ArrayList<Region>();
	}
	
	@Override
	public void notify(ObservablePicture picture, Region changed_region) {
		_o.notify(picture, changed_region);
		NotifiedRegions.add(changed_region);
	}

	@Override
	public int getCount() {
		return NotifiedRegions.size();
	}

	@Override
	public List<Region> getNotifiedRegion() {
		return NotifiedRegions;
	}

}
