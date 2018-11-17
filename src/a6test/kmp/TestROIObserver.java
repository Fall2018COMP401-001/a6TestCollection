package a6test.kmp;

import java.util.List;

import a6.ObservablePicture;
import a6.ROIObserver;
import a6.Region;

import java.util.List;
import java.util.ArrayList;

public class TestROIObserver implements ROIObserver {

	List<RegionHit> hits;
	
	public TestROIObserver() {
		hits = new ArrayList<RegionHit>();
	}
	
	public int getHitCount() {
		return hits.size();
	}
	
	public RegionHit[] getHits() {
		return hits.toArray(new RegionHit[hits.size()]);
	}
	
	public void reset() {
		hits = new ArrayList<RegionHit>();
	}
	
	@Override
	public void notify(ObservablePicture picture, Region changed_region) {
		hits.add(new RegionHitImpl(changed_region, picture));
	}

}
