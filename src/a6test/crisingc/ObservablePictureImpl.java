package a6test.crisingc;
import java.util.ArrayList;
import java.util.List;

public class ObservablePictureImpl implements ObservablePicture {
	
	 Picture wrappedPic;
	 List<Region> regions;
	 List<ROIObserver> observers;

	 boolean suspended;
	Region changedRegion;
	
	public ObservablePictureImpl(Picture p) {
		//make this a decorator class by wrapping p
		wrappedPic = p;
		//make an array list of observers
		observers = new ArrayList<ROIObserver>();
		//make an array list of regions
		regions = new ArrayList<Region>();

		//setting suspended to false
		suspended = false;
		//setting changedRegion to null
		changedRegion = null;
	}

	public List<ROIObserver> getObservers() {
		return observers;
	}
	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return wrappedPic.getWidth();
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return wrappedPic.getHeight();
	}

	@Override
	public Pixel getPixel(int x, int y) {
		// TODO Auto-generated method stub
		return wrappedPic.getPixel(x, y);
	}

	@Override
	public Picture paint(int x, int y, Pixel p) {

		return paint(x, y, p, 1);
	}

	@Override
	public Picture paint(int x, int y, Pixel p, double factor) {
		//sending the wrapped picture through the paint method
		this.wrappedPic = wrappedPic.paint(x, y, p, factor);
		//setting up local variables for use in for loop
		Region r = new RegionImpl(x, y, x, y);
		//set changedRegion to r
		if (suspended) {
			changedRegion = r.union(changedRegion);
		} else {
			changedRegion = r;
			return notifyObservers(r);
		}
		
		return this;
	}

	@Override
	public Picture paint(int ax, int ay, int bx, int by, Pixel p) {
		return paint(ax, ay, bx, by, p, 1);
	}

	@Override
	public Picture paint(int ax, int ay, int bx, int by, Pixel p, double factor) {
		//sending the paint method through the wrapped pic
		this.wrappedPic = wrappedPic.paint(ax, ay, bx, by, p);
		//declaring the changed region
		int left = (ax <= bx) ? ax : bx;
		int top = (ax <= bx) ? ay : by;
		int right = (ax <= bx) ? bx : ax;
		int bottom = (ax <= bx) ? by : ay;
		
		Region r = new RegionImpl(left, top, right, bottom);
		
		if (suspended) {
			changedRegion = r.union(changedRegion);
		} else {
			changedRegion = r;
			return notifyObservers(r);
		}
		
		return this;
	}

	@Override
	public Picture paint(int cx, int cy, double radius, Pixel p) {
		// TODO Auto-generated method stub
		return paint(cx, cy, radius, p, 1);
	}

	@Override
	public Picture paint(int cx, int cy, double radius, Pixel p, double factor) {
		
		this.wrappedPic = wrappedPic.paint(cx, cy, radius, p, factor);
		
		Region r = new RegionImpl((int)(cx - radius), (int)(cy - radius), (int)(cx + radius), (int)(cy + radius));
		
		if (suspended) {
			changedRegion = r.union(changedRegion);
		} else {
			changedRegion = r;
			return notifyObservers(r);
		}
		
		return this;
	}

	@Override
	public Picture paint(int x, int y, Picture p) {
		return paint(x, y, p, 1);
	}

	@Override
	public Picture paint(int x, int y, Picture p, double factor) {
		// TODO Auto-generated method stub
		wrappedPic = wrappedPic.paint(x, y, p);
		
		Region r = new RegionImpl(x, y, (x + wrappedPic.getWidth() -1), (y + getHeight() - 1));
		
		if (suspended) {
			changedRegion = r.union(changedRegion);
		} else {
			changedRegion = r;
			return notifyObservers(r);
		}
		
		return this;
	}

	@Override
	public String getCaption() {
		// TODO Auto-generated method stub
		return wrappedPic.getCaption();
	}

	@Override
	public void setCaption(String caption) {
		// TODO Auto-generated method stub
		/*
		 * should this be delegated to the wrapped picture?
		 */
		wrappedPic.setCaption(caption);
	}

	@Override
	public SubPicture extract(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		return wrappedPic.extract(x, y, width, height);
	}

	@Override
	public void registerROIObserver(ROIObserver observer, Region r) {
		regions.add(r);
		observers.add(observer);

	}

	@Override
	public void unregisterROIObservers(Region r) {
		List<Region> regionsTemp = new ArrayList<Region>();
		List<ROIObserver> observersTemp = new ArrayList<ROIObserver>();
		int i = 0;
		//creating an iterator for both lists
//		Iterator<Region> rIter = regions.iterator();
//		Iterator<ROIObserver> oIter = observers.iterator();
		/*
		 * This for loop cycles through the regions list. If r region is encountered, the iterator removes it
		 * and along with the corresponding observer. Whether or not the region matches, both iterators advanced
		 * by one, keeping them together. The iterators stop advancing at the end of the observer list.
		 */
		for (Region otherRegion : regions) {
			//advance each iterator
//			oIter.next();
//			rIter.next();
			
			if (!otherRegion.equals(r)) {
			//remove the observer and corresponding region from the lists
//				rIter.remove();
//				oIter.remove();
				regionsTemp.add(otherRegion);
				observersTemp.add(observers.get(i));
			}
			i++;
		}
		regions = regionsTemp;
		observers = observersTemp;
	}

	@Override
	public void unregisterROIObserver(ROIObserver observer) {
		List<Region> regionsTemp = new ArrayList<Region>();
		List<ROIObserver> observersTemp = new ArrayList<ROIObserver>();
		int i = 0;
		//creating an iterator for both lists
//		Iterator<Region> rIter = regions.iterator();
//		Iterator<ROIObserver> oIter = observers.iterator();
		/*
		 * This for loop cycles through the regions list. If r region is encountered, the iterator removes it
		 * and along with the corresponding observer. Whether or not the region matches, both iterators advanced
		 * by one, keeping them together. The iterators stop advancing at the end of the observer list.
		 */
		for (ROIObserver o : observers) {
			//advance each iterator
//			oIter.next();
//			rIter.next();
			
			if (!o.equals(observer)) {
			//remove the observer and corresponding region from the lists
//				rIter.remove();
//				oIter.remove();
				regionsTemp.add(regions.get(i));
				observersTemp.add(o);
			}
			i++;
		}
		regions = regionsTemp;
		observers = observersTemp;
	}

	@Override
	public ROIObserver[] findROIObservers(Region r) {
		List<ROIObserver> list = new ArrayList<ROIObserver>();
	     	
		int i = 0;
		for (Region otherRegion : regions) {
			if (otherRegion.equals(r)) {
				list.add(observers.get(i));
			}
			i++;
		}
		ROIObserver[] array = new ROIObserver[list.size()];
		array = list.toArray(array);
		return array;
	}

	@Override
	public void suspendObservable() {
		suspended = true;

	}

	@Override
	public void resumeObservable() {
		notifyObservers(changedRegion);
		suspended = false;
		changedRegion = null;
	}
	
	private Picture notifyObservers(Region r1) {
		//set up a counter
		int i = 0;
		
		for (Region r2 : regions) {
		/*
		 * Determining if an intersection exists for any region in the regions list.
		 * If no intersection exists with a particular region, then adding 1 to the current i counter
		 * and moving to the next region.
		 * If an intersection does exist, then retrieving the observer that corresponds with the same i counter 
		 * on the observers list and notifying with this picture and the region of intersection.
		 */
			if (checkIntersection(r1, r2)) {
				observers.get(i).notify(this, r1);
				i++;
			} else {
				i++;
			}
		}
		
		return this;
	}
	
	//private method to see if there is an intersection between two resions
	private Boolean checkIntersection(Region r1, Region r2) {
		try {
			r1.intersect(r2);
		} catch (NoIntersectionException e) {
			return false;
		} return true;
	}

}
