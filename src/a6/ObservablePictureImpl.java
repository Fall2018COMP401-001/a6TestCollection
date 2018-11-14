package a6;

import java.util.List;
import java.util.ArrayList;

public class ObservablePictureImpl implements ObservablePicture {
	
	private Picture _wrapped_picture;
	private List<RegionObserverImpl> _observers;
	private boolean _observable;
	private List<Region> _updated_regions;

	public ObservablePictureImpl(Picture p) {
		if (p == null) {
			throw new IllegalArgumentException("Picture cannot be null.");
		}
		_wrapped_picture = p;
		_observable = true;
		_observers = new ArrayList<RegionObserverImpl>();
		_updated_regions = new ArrayList<Region>();
	}

	@Override
	public int getWidth() {
		return _wrapped_picture.getWidth();
	}

	@Override
	public int getHeight() {
		return _wrapped_picture.getHeight();
	}

	@Override
	public Pixel getPixel(int x, int y) {
		return _wrapped_picture.getPixel(x, y);
	}

	@Override
	public Picture paint(int x, int y, Pixel p) {
		Region r = new RegionImpl(x,y,x,y);
		notifyObservers(r);
		_wrapped_picture = _wrapped_picture.paint(x, y, p);
		return _wrapped_picture;
	}

	@Override
	public Picture paint(int x, int y, Pixel p, double factor) {
		Region r = new RegionImpl(x,y,x,y);
		notifyObservers(r);
		_wrapped_picture = _wrapped_picture.paint(x, y, p, factor);
		return _wrapped_picture;
	}

	@Override
	public Picture paint(int ax, int ay, int bx, int by, Pixel p) {
		Region r = new RegionImpl(ax,ay,bx,by);
		notifyObservers(r);
		_wrapped_picture = _wrapped_picture.paint(ax, ay, bx, by, p);
		return _wrapped_picture;
	}

	@Override
	public Picture paint(int ax, int ay, int bx, int by, Pixel p, double factor) {
		Region r = new RegionImpl(ax,ay,bx,by);
		notifyObservers(r);
		_wrapped_picture = _wrapped_picture.paint(ax, ay, bx, by, p, factor);
		return _wrapped_picture;
	}

	@Override
	public Picture paint(int cx, int cy, double radius, Pixel p) {
		Region r = new RegionImpl((int)-radius+cx-1,(int)-radius+cy-1,(int)radius+cx+1,(int)radius+cy+1);
		notifyObservers(r);
		_wrapped_picture = _wrapped_picture.paint(cx, cy, radius, p);
		return _wrapped_picture;
	}

	@Override
	public Picture paint(int cx, int cy, double radius, Pixel p, double factor) {
		Region r = new RegionImpl((int)-radius+cx-1,(int)-radius+cy-1,(int)radius+cx+1,(int)radius+cy+1);
		notifyObservers(r);
		_wrapped_picture = _wrapped_picture.paint(cx, cy, radius, p, factor);
		return _wrapped_picture;
	}

	@Override
	public Picture paint(int x, int y, Picture p) {
		Region r = new RegionImpl(x,y,x+p.getWidth(),y+p.getHeight());
		notifyObservers(r);
		_wrapped_picture = _wrapped_picture.paint(x, y, p);
		return _wrapped_picture;
	}

	@Override
	public Picture paint(int x, int y, Picture p, double factor) {
		Region r = new RegionImpl(x,y,x+p.getWidth(),y+p.getHeight());
		notifyObservers(r);
		_wrapped_picture = _wrapped_picture.paint(x, y, p, factor);
		return _wrapped_picture;
	}

	@Override
	public String getCaption() {
		return _wrapped_picture.getCaption();
	}

	@Override
	public void setCaption(String caption) {
		_wrapped_picture.setCaption(caption);
	}

	@Override
	public SubPicture extract(int x, int y, int width, int height) {
		return _wrapped_picture.extract(x, y, width, height);
	}

	@Override
	public void registerROIObserver(ROIObserver observer, Region r) {
		RegionObserverImpl ro = new RegionObserverImpl(observer);
		_observers.add(ro);
		ro.addRegion(r);
	}

	@Override
	public void unregisterROIObservers(Region r) {
		ROIObserver[] unregister = findROIObservers(r);
		for (ROIObserver observer: unregister) {
			unregisterROIObserver(observer);
		}
	}

	@Override
	public void unregisterROIObserver(ROIObserver observer) {
		for (RegionObserverImpl ro: _observers) {
			if (ro.getObserver() == observer) {
				_observers.remove(ro);
			}
		}
	}

	@Override
	public ROIObserver[] findROIObservers(Region r) {
		List<ROIObserver> rois = new ArrayList<ROIObserver>();
		
		for (RegionObserverImpl ro: _observers) {
			for (Region region: ro.getRegions()) {
				try {
					region.intersect(r);
					if (!rois.contains(ro.getObserver())) {
						rois.add(ro.getObserver());
					}
				} catch (NoIntersectionException e) {
				}
			}
		}
		return rois.toArray(new ROIObserver[rois.size()]);
	}

	@Override
	public void suspendObservable() {
		_observable = false;
	}

	@Override
	public void resumeObservable() {
		_observable = true;
		for(Region r: _updated_regions.toArray(new Region[_updated_regions.size()])) {
			notifyObservers(r);
		}
	}
	
	private void notifyObservers(Region r) {
		if (_observable) {
			for (RegionObserverImpl ro: _observers) {
				for (Region region: ro.getRegions()) {
					try {
						ro.notify(this, region.intersect(r));
					} catch (NoIntersectionException e) {
					}
				}
			}
		} else {
			_updated_regions.add(r);
		}
	}

}
