package a6test.qishun96;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import a6.*;

class A6Tests {

	Pixel red = new ColorPixel(1, 0, 0);
	Pixel blue = new ColorPixel(0, 0, 1);

	Picture blueMutable4by4 = new MutablePixelArrayPicture(new Pixel[][] { { blue, blue, blue, blue },
			{ blue, blue, blue, blue }, { blue, blue, blue, blue }, { blue, blue, blue, blue } }, "Blue");
	Picture blueImmutable4by4 = new ImmutablePixelArrayPicture(new Pixel[][] { { blue, blue, blue, blue },
			{ blue, blue, blue, blue }, { blue, blue, blue, blue }, { blue, blue, blue, blue } }, "Blue");

	Region region0000 = new RegionImpl(0, 0, 0, 0);
	Region region2133 = new RegionImpl(2, 1, 3, 3);
	Region region1122 = new RegionImpl(1, 1, 2, 2);
	Region region2122 = new RegionImpl(2, 1, 2, 2);
	Region region2222 = new RegionImpl(2, 2, 2, 2);
	Region region2121 = new RegionImpl(2, 1, 2, 1);
	Region region1133 = new RegionImpl(1, 1, 3, 3);
	Region region3031 = new RegionImpl(3, 0, 3, 1);
	Region region3131 = new RegionImpl(3, 1, 3, 1);
	Region region1111 = new RegionImpl(1, 1, 1, 1);
	Region region3333 = new RegionImpl(3, 3, 3, 3);
	Region region0033 = new RegionImpl(0, 0, 3, 3);

	ObservablePicture observableBlueImmutable3by3 = new ObservablePictureImpl(blueImmutable4by4);

	ROIObserver observer1 = new ROIObserverImpl();
	ROIObserver observer2 = new ROIObserverImpl();
	ROIObserver observer3 = new ROIObserverImpl();

	@Test
	void testRegionImplConstructor() {
		try {
			new RegionImpl(2, 1, 1, 2);
			fail("Left cannot be greater than right");
		} catch (IllegalArgumentException e) {
		}
		try {
			new RegionImpl(1, 2, 2, 1);
			fail("Top cannot be greater than bottom");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	void testRegionIntersection() throws NoIntersectionException {
		assert (equalRegions(region2122.intersect(region2133), region2122));
		try {
			region1122.intersect(null);
			fail("cannot intersect null");
		} catch (NoIntersectionException e) {
		}
	}

	@Test
	void testRegionUnion() {
		assert (equalRegions(region3333.union(region1111), region1133));
		assert (equalRegions(region1133.union(null), region1133));
	}

	@Test
	void testObservablePictureImplConstructor() {
		try {
			new ObservablePictureImpl(null);
			fail("Picture cannot be null.");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	void testObservablePictureImplGetters() {
		ObservablePicture p = new ObservablePictureImpl(blueMutable4by4);
		assertEquals(p.getCaption(), blueMutable4by4.getCaption());
		assertEquals(p.getHeight(), blueMutable4by4.getHeight());
		assertEquals(p.getWidth(), blueMutable4by4.getWidth());
		for (int i = 0; i < p.getWidth(); i++) {
			for (int j = 0; j < p.getHeight(); j++) {
				assertEquals(p.getPixel(i, j), blueMutable4by4.getPixel(i, j));
			}
		}
	}

	@Test
	void testSimpleROIObservableMethods() {
		ObservablePicture observableBlueMutable4by4 = new ObservablePictureImpl(blueMutable4by4);
		ROIObserver[] observers;

		try {
			observableBlueMutable4by4.registerROIObserver(null, region0000);
			fail("Cannot register null observer");
		} catch (Exception e) {
		}

		try {
			observableBlueMutable4by4.registerROIObserver(observer1, null);
			fail("Cannot register null region");
		} catch (Exception e) {
		}
		
		// Assignment doesn't define what should happen with null 
		// arguments for these methods, but kmp solution throws
		// IllegalArgumentException and would expect student solutions
		// to do the same. Not going to be tested by autograder, so 
		// just commenting these tests out.
		
		// observableBlueMutable4by4.unregisterROIObserver(null);
		// observers = observableBlueMutable4by4.findROIObservers(null);
		// observableBlueMutable4by4.unregisterROIObservers(null);

		observableBlueMutable4by4.registerROIObserver(observer1, region0000);
		observableBlueMutable4by4.registerROIObserver(observer1, region3333);
		observableBlueMutable4by4.registerROIObserver(observer2, region2122);
		observableBlueMutable4by4.registerROIObserver(observer3, region3031);

		observers = observableBlueMutable4by4.findROIObservers(region0033);

		assertEquals(4, observers.length);
		assert (checkObservers(observers, observer1));
		assert (checkObservers(observers, observer1));
		assert (checkObservers(observers, observer2));
		assert (checkObservers(observers, observer3));

		observableBlueMutable4by4.registerROIObserver(observer1, region0000);

		observers = observableBlueMutable4by4.findROIObservers(region0033);

		assertEquals(5, observers.length);
		assert (checkObservers(observers, observer1));
		assert (checkObservers(observers, observer1));
		assert (checkObservers(observers, observer1));
		assert (checkObservers(observers, observer2));
		assert (checkObservers(observers, observer3));

		observableBlueMutable4by4.unregisterROIObserver(observer1);

		observers = observableBlueMutable4by4.findROIObservers(region0033);

		assertEquals(2, observers.length);
		assert (checkObservers(observers, observer2));
		assert (checkObservers(observers, observer3));

		observableBlueMutable4by4.registerROIObserver(observer1, region0000);
		observableBlueMutable4by4.registerROIObserver(observer1, region3333);

		observers = observableBlueMutable4by4.findROIObservers(region0033);

		assertEquals(4, observers.length);
		assert (checkObservers(observers, observer1));
		assert (checkObservers(observers, observer1));
		assert (checkObservers(observers, observer2));
		assert (checkObservers(observers, observer3));

		observableBlueMutable4by4.unregisterROIObservers(region3333);

		observers = observableBlueMutable4by4.findROIObservers(region0033);

		assertEquals(3, observers.length);
		assert (checkObservers(observers, observer1));
		assert (checkObservers(observers, observer2));
		assert (checkObservers(observers, observer3));

		observableBlueMutable4by4.unregisterROIObservers(region2121);

		observers = observableBlueMutable4by4.findROIObservers(region0033);

		assertEquals(2, observers.length);
		assert (checkObservers(observers, observer1));
		assert (checkObservers(observers, observer3));
	}

	@Test
	void testDifferentPaintNotifyObservers() {
		ObservablePicture observableBlueMutable4by4 = new ObservablePictureImpl(blueMutable4by4);

		ObserverHelper ob1 = new ObserverHelperImpl(observer1);
		ObserverHelper ob2 = new ObserverHelperImpl(observer2);
		ObserverHelper ob3 = new ObserverHelperImpl(observer3);

		observableBlueMutable4by4.registerROIObserver(ob1, region0000);
		observableBlueMutable4by4.registerROIObserver(ob1, region3333);
		observableBlueMutable4by4.registerROIObserver(ob2, region2122);
		observableBlueMutable4by4.registerROIObserver(ob3, region3031);

		observableBlueMutable4by4.paint(0, 0, red);
		assertEquals(1, ob1.getCount());
		assertEquals(0, ob2.getCount());
		assertEquals(0, ob3.getCount());
		assert (equalRegions(ob1.getNotifiedRegion().get(0), region0000));

		observableBlueMutable4by4.paint(0, 0, observableBlueImmutable3by3);
		assertEquals(3, ob1.getCount());
		assertEquals(1, ob2.getCount());
		assertEquals(1, ob3.getCount());
		assert (equalRegions(ob1.getNotifiedRegion().get(0), region0000));
		assert (equalRegions(ob1.getNotifiedRegion().get(1), region0000));
		assert (equalRegions(ob1.getNotifiedRegion().get(2), region3333));
		assert (equalRegions(ob2.getNotifiedRegion().get(0), region2122));
		assert (equalRegions(ob3.getNotifiedRegion().get(0), region3031));

		observableBlueMutable4by4.paint(1, 1, red);
		assertEquals(3, ob1.getCount());
		assertEquals(1, ob2.getCount());
		assertEquals(1, ob3.getCount());
		assert (equalRegions(ob1.getNotifiedRegion().get(0), region0000));
		assert (equalRegions(ob1.getNotifiedRegion().get(1), region0000));
		assert (equalRegions(ob1.getNotifiedRegion().get(2), region3333));
		assert (equalRegions(ob2.getNotifiedRegion().get(0), region2122));
		assert (equalRegions(ob3.getNotifiedRegion().get(0), region3031));

		observableBlueMutable4by4.unregisterROIObservers(region3333);
		observableBlueMutable4by4.paint(3, 3, 2, red);
		assertEquals(3, ob1.getCount());
		assertEquals(2, ob2.getCount());
		assertEquals(2, ob3.getCount());
		assert (equalRegions(ob1.getNotifiedRegion().get(0), region0000));
		assert (equalRegions(ob1.getNotifiedRegion().get(1), region0000));
		assert (equalRegions(ob1.getNotifiedRegion().get(2), region3333));
		assert (equalRegions(ob2.getNotifiedRegion().get(0), region2122));
		assert (equalRegions(ob2.getNotifiedRegion().get(1), region2122));
		assert (equalRegions(ob3.getNotifiedRegion().get(0), region3031));
		assert (equalRegions(ob3.getNotifiedRegion().get(1), region3131));
	}

	@Test
	void testSuspendAndResume() {
		ObservablePicture observableBlueMutable4by4 = new ObservablePictureImpl(blueMutable4by4);

		ObserverHelper ob1 = new ObserverHelperImpl(observer1);
		ObserverHelper ob2 = new ObserverHelperImpl(observer2);
		ObserverHelper ob3 = new ObserverHelperImpl(observer3);

		observableBlueMutable4by4.registerROIObserver(ob1, region0000);
		observableBlueMutable4by4.registerROIObserver(ob1, region3333);
		observableBlueMutable4by4.registerROIObserver(ob2, region2122);
		observableBlueMutable4by4.registerROIObserver(ob3, region3031);

		observableBlueMutable4by4.suspendObservable();

		observableBlueMutable4by4.paint(0, 0, red);
		observableBlueMutable4by4.paint(1, 1, red);
		assertEquals(0, ob1.getCount());
		assertEquals(0, ob2.getCount());
		assertEquals(0, ob3.getCount());

		observableBlueMutable4by4.resumeObservable();
		assertEquals(1, ob1.getCount());
		assertEquals(0, ob2.getCount());
		assertEquals(0, ob3.getCount());
		assert (equalRegions(ob1.getNotifiedRegion().get(0), region0000));

		observableBlueMutable4by4.suspendObservable();
		observableBlueMutable4by4.suspendObservable();

		observableBlueMutable4by4.paint(0, 0, red);
		observableBlueMutable4by4.paint(2, 0, red);
		observableBlueMutable4by4.paint(0, 3, red);

		observableBlueMutable4by4.resumeObservable();
		assertEquals(2, ob1.getCount());
		assertEquals(1, ob2.getCount());
		assertEquals(0, ob3.getCount());
		assert (equalRegions(ob1.getNotifiedRegion().get(0), region0000));
		assert (equalRegions(ob1.getNotifiedRegion().get(1), region0000));
		assert (equalRegions(ob2.getNotifiedRegion().get(0), region2122));

		observableBlueMutable4by4.resumeObservable();
		assertEquals(2, ob1.getCount());
		assertEquals(1, ob2.getCount());
		assertEquals(0, ob3.getCount());
		assert (equalRegions(ob1.getNotifiedRegion().get(0), region0000));
		assert (equalRegions(ob1.getNotifiedRegion().get(1), region0000));
		assert (equalRegions(ob2.getNotifiedRegion().get(0), region2122));
	}

	private static boolean equalRegions(Region r1, Region r2) {
		return (r1.getLeft() == r2.getLeft() && r1.getTop() == r2.getTop() && r1.getBottom() == r2.getBottom()
				&& r1.getRight() == r2.getRight());
	}

	private static boolean checkObservers(ROIObserver[] observers, ROIObserver observer) {
		for (int i = 0; i < observers.length; i++) {
			if (observers[i] == observer) {
				observers[i] = null;
				return true;
			}
		}
		return false;
	}
}
