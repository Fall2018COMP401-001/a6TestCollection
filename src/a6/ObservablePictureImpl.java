package a6;

import java.util.*;

public class ObservablePictureImpl implements ObservablePicture {

	private Picture p;
	private List<ObserverAndRegion> observers;
	private List<Region> region;
	private boolean suspended = false;

	public ObservablePictureImpl(Picture p) {
		if (p == null) {
			throw new IllegalArgumentException("picture cannot be null");
		}
		this.p = p;
		observers = new ArrayList<>();
		region = new ArrayList<>();
	}

	@Override
	public int getWidth() {
		return p.getWidth();
	}

	@Override
	public int getHeight() {
		return p.getHeight();
	}

	@Override
	public Pixel getPixel(int x, int y) {
		return p.getPixel(x, y);
	}

	@Override
	public Picture paint(int x, int y, Pixel p) {
		this.p = this.p.paint(x, y, p);
		Region r = new RegionImpl(x, y, x, y);
		if (suspended == true) {
			region.add(r);
		} else {
			notify(r);
		}
		return this;
	}

	@Override
	public Picture paint(int x, int y, Pixel p, double factor) {
		this.p = this.p.paint(x, y, p, factor);
		Region r = new RegionImpl(x, y, x, y);
		if (suspended == true) {
			region.add(r);
		} else {
			notify(r);
		}
		return this;
	}

	@Override
	public Picture paint(int ax, int ay, int bx, int by, Pixel p) {
		this.p = this.p.paint(ax, ay, bx, by, p);
		Region r = new RegionImpl(ax, ay, bx, by);
		if (suspended == true) {
			region.add(r);
		} else {
			notify(r);
		}
		return this;
	}

	@Override
	public Picture paint(int ax, int ay, int bx, int by, Pixel p, double factor) {
		this.p = this.p.paint(ax, ay, bx, by, p, factor);
		Region r = new RegionImpl(ax, ay, bx, by);
		if (suspended == true) {
			region.add(r);
		} else {
			notify(r);
		}
		return this;
	}

	@Override
	public Picture paint(int cx, int cy, double radius, Pixel p) {
		this.p = this.p.paint(cx, cy, radius, p);
		int rad = (int)radius;
		Region r = new RegionImpl(cx - rad, cy - rad, cx + rad, cy + rad);
		if (suspended == true) {
			region.add(r);
		} else {
			notify(r);
		}
		return this;
	}

	@Override
	public Picture paint(int cx, int cy, double radius, Pixel p, double factor) {
		this.p = this.p.paint(cx, cy, radius, p, factor);
		int rad = (int)radius;
		Region r = new RegionImpl(cx - rad, cy - rad, cx + rad, cy + rad);
		if (suspended == true) {
			region.add(r);
		} else {
			notify(r);
		}
		return this;
	}

	@Override
	public Picture paint(int x, int y, Picture p) {
		this.p = this.p.paint(x, y, p);
		Region r = new RegionImpl(x, y, p.getWidth(), p.getHeight());
		if (suspended == true) {
			region.add(r);
		} else {
			notify(r);
		}
		return this;
	}

	@Override
	public Picture paint(int x, int y, Picture p, double factor) {
		this.p = this.p.paint(x, y, p, factor);
		Region r = new RegionImpl(x, y, p.getWidth(), p.getHeight());
		if (suspended == true) {
			region.add(r);
		} else {
			notify(r);
		}
		return this;
	}

	@Override
	public String getCaption() {
		return p.getCaption();
	}

	@Override
	public void setCaption(String caption) {
		p.setCaption(caption);

	}

	@Override
	public SubPicture extract(int x, int y, int width, int height) {
		return p.extract(x, y, width, height);
	}

	@Override
	public void registerROIObserver(ROIObserver observer, Region r) {
		observers.add(new ObserverAndRegion(observer, r));
	}

	@Override
	public void unregisterROIObservers(Region r) {
		for (ObserverAndRegion o : observers) {
			if (o.getRegion() == r) {
				observers.remove(o);
			}
		}
	}

	@Override
	public void unregisterROIObserver(ROIObserver observer) {
		for (ObserverAndRegion o : observers) {
			if (o.getObserver() == observer) {
				observers.remove(o);
			}
		}
	}

	@Override
	public ROIObserver[] findROIObservers(Region r) {
		List<ROIObserver> obs = new ArrayList<>();
		for (ObserverAndRegion o : observers) {
			if (intersect(o.getRegion(), r)) {
				
				obs.add(o.getObserver());
			}
		}
		return obs.toArray(new ROIObserver[obs.size()]);
	}

	private boolean intersect(Region region, Region r) {
		try {
			region.intersect(r);
		} catch (NoIntersectionException e){
			return false;
		}
		return true;
	}

	@Override
	public void suspendObservable() {
		suspended = true;
	}

	@Override
	public void resumeObservable() {
		suspended = false;
		if (region != null) {
			for (Region r : region) {
				notify(r);
			}
		}
		region = new ArrayList<>();
	}

	public void notify(Region changed_region) {
		if (changed_region == null) {
			throw new IllegalArgumentException("region cannot be null");
		}
		ROIObserver[] OList = findROIObservers(changed_region);
		for (ROIObserver o : OList) {
			o.notify(this, changed_region);
		}
	}
}
