package a6test.kmp;

import a6.NoIntersectionException;
import a6.ObservablePicture;
import a6.Region;

public class RegionHitImpl implements RegionHit {

	private Region region;
	private ObservablePicture picture;
	
	public RegionHitImpl(Region r, ObservablePicture p) {
		region = r;
		picture = p;
	}
	
	@Override
	public int getTop() {
		return region.getTop();
	}

	@Override
	public int getBottom() {
		return region.getBottom();
	}

	@Override
	public int getLeft() {
		return region.getLeft();
	}

	@Override
	public int getRight() {
		return region.getRight();
	}

	@Override
	public Region intersect(Region other) throws NoIntersectionException {
		return region.intersect(other);
	}

	@Override
	public Region union(Region other) {
		return region.union(other);
	}

	@Override
	public ObservablePicture getHitPicture() {
		return picture;
	}

}
