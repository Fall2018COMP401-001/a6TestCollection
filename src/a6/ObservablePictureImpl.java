package a6;
 
import java.util.ArrayList;
import java.util.List;

public class ObservablePictureImpl implements ObservablePicture {

	private Picture picture;
	private List<RegionROIObserver> regObserver;
	private boolean suspend;
	private Region changed;

	public ObservablePictureImpl(Picture p) {
		this.picture = p;
		this.regObserver = new ArrayList<RegionROIObserver>();
		this.suspend = false;
		this.changed = null;
	}

	
	@Override
	public int getWidth() {
		return this.picture.getWidth();
	}

	@Override
	public int getHeight() {
		return this.picture.getHeight();
	}

	@Override
	public Pixel getPixel(int x, int y) {
		return this.picture.getPixel(x, y);
	}

	@Override
	public Picture paint(int x, int y, Pixel p) {
		return this.paint(x, y, p, 1.0);
	}

	@Override
	public Picture paint(int x, int y, Pixel p, double factor) {

		Picture result = this.picture.paint(x, y, p, factor);
		this.picture = result;

		if (changed != null) {
			changed = changed.union(new RegionImpl(x, y, x, y));
		} else {
			changed = new RegionImpl(x, y, x, y);
		}

		notifyObservers();
		return this;
	}

	@Override
	public Picture paint(int ax, int ay, int bx, int by, Pixel p) {
		return paint(ax, ay, bx, by, p, 1.0);
	}

	@Override
	public Picture paint(int ax, int ay, int bx, int by, Pixel p, double factor) {

		Picture result = this.picture.paint(ax, ay, bx, by, p, factor);
		this.picture = result;

		if (changed != null) {
			changed = changed.union(new RegionImpl(ax, ay, bx, by));
		} else {
			changed = new RegionImpl(ax, ay, bx, by);
		}

		notifyObservers();
		return this;
	}

	@Override
	public Picture paint(int cx, int cy, double radius, Pixel p) {
		return paint(cx, cy, radius, p,1.0);
	}

	@Override
	public Picture paint(int cx, int cy, double radius, Pixel p, double factor) {

		picture = picture.paint(cx, cy, radius, p, factor);
		if(changed!=null) {
			changed = changed.union(new RegionImpl((int)(cx - radius), 
					(int)(cy - radius), 
					(int)(cx + radius), 
					(int)(cy +  radius)));
		} else {
			changed = new RegionImpl((int)(cx - radius), 
					(int)(cy - radius), 
					(int)(cx + radius), 
					(int)(cy +  radius));
		}
		notifyObservers();
		return this;
	}

	@Override
	public Picture paint(int x, int y, Picture p) {
		return paint(x, y, p, 1.0);
	}

	@Override
	public Picture paint(int x, int y, Picture p, double factor) {

		this.picture = this.picture.paint(x, y, p, factor);
 
		if (changed != null) {
			changed = changed.union(new RegionImpl(x, y, x + p.getWidth() - 1, y + p.getHeight() - 1));
		} else {
			changed = new RegionImpl(x, y, x + p.getWidth() - 1, y + p.getHeight() - 1);
		}

		notifyObservers();
		return this;
	}

	@Override
	public String getCaption() {
		return this.picture.getCaption();
	}

	@Override
	public void setCaption(String caption) {
		this.picture.setCaption(caption);
	}

	@Override
	public SubPicture extract(int x, int y, int width, int height) {
		return this.picture.extract(x, y, width, height);
	}
	
	@Override
	public void registerROIObserver(ROIObserver observer, Region r) {
		RegionROIObserver register= new RegionROIObserverImpl(observer, r);
		regObserver.add(register);
	}

	@Override
	public void unregisterROIObservers(Region r) {
		List<RegionROIObserver> unregister = new ArrayList<RegionROIObserver>();
		for (RegionROIObserver rr : this.regObserver) {
			try {
				rr.getRegion().intersect(r);
			} 
			catch (NoIntersectionException e) {
				continue;
			}
			unregister.add(rr);
		}
		
		regObserver.removeAll(unregister);
	}

	@Override
	public void unregisterROIObserver(ROIObserver observer) {
		List<RegionROIObserver> remain = new ArrayList<RegionROIObserver>();
		for (RegionROIObserver o : regObserver) {
			if (o.getWrappedROIObserver() != observer) {
				remain.add(o);
			}
		}
		this.regObserver = remain;

	}

	@Override
	public ROIObserver[] findROIObservers(Region r) {
		
		List<ROIObserver> l = new ArrayList<ROIObserver>();
		for (RegionROIObserver o : this.regObserver) {
			try {
				o.getRegion().intersect(r);
			} catch (NoIntersectionException e) {
				continue;
			}
			l.add(o.getWrappedROIObserver());
		}
		return l.toArray(new ROIObserver[l.size()]);
	}

	
	
	
	
	@Override
	public void suspendObservable() {
		this.suspend = true;
	}

	@Override
	public void resumeObservable() {
		this.suspend = false;
		notifyObservers();
	}
	
	private void notifyObservers() {
		if (!suspend && changed != null) {
			for (RegionROIObserver ro : regObserver) {
				try {
					Region intersect = ro.getRegion().intersect(changed);
					ro.notify(this, intersect);
				}

				catch (NoIntersectionException e) {

				}
			}

			changed = null;

		}
	}


}