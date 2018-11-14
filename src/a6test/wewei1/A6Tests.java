package a6test.wewei1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import a6.ColorPixel;
import a6.ImmutablePixelArrayPicture;
import a6.MutablePixelArrayPicture;
import a6.Picture;
import a6.Pixel;
import a6.*;

class A6Tests {

	Pixel red = new ColorPixel(1, 0, 0);
	Pixel green = new ColorPixel(0, 1, 0);
	Pixel[][] redPicture = { { red, red, red, red }, { red, red, red, red }, 
			                 { red, red, red, red }, { red, red, red, red } };
	Picture redMutablePicture = new MutablePixelArrayPicture(redPicture, "red");
	Picture redImmutablePicture = new ImmutablePixelArrayPicture(redPicture, "red");

	Region region0000 = new RegionImpl(0, 0, 0, 0);
	Region region0011 = new RegionImpl(0, 0, 1, 1);
	Region region0022 = new RegionImpl(0, 0, 2, 2);
	Region region1111 = new RegionImpl(1, 1, 1, 1);
	Region region1122 = new RegionImpl(1, 1, 2, 2);
	Region region1133 = new RegionImpl(1, 1, 3, 3);
	Region region2121 = new RegionImpl(2, 1, 2, 1);
	Region region2122 = new RegionImpl(2, 1, 2, 2);
	Region region2133 = new RegionImpl(2, 1, 3, 3);
	Region region2222 = new RegionImpl(2, 2, 2, 2);
	Region region3031 = new RegionImpl(3, 0, 3, 1);
	Region region3131 = new RegionImpl(3, 1, 3, 1);
	Region region3333 = new RegionImpl(3, 3, 3, 3);
	
	ObservablePicture observableRedImmutablePicture = new ObservablePictureImpl(redImmutablePicture);
	ObservablePicture observableRedMutablePicture = new ObservablePictureImpl(redMutablePicture);
	
	ROIObserver observer1 = new ROIObserverImpl();
	ROIObserver observer2 = new ROIObserverImpl();
	ROIObserver observer3 = new ROIObserverImpl();
	
	ObserverNotify ob1 = new ObserverNotifyImpl(observer1);
	ObserverNotify ob2 = new ObserverNotifyImpl(observer2);
	ObserverNotify ob3 = new ObserverNotifyImpl(observer3);

	@Test
	void testRegionImplConstructor() {
		try {
			new RegionImpl(4, 1, 3, 2);
			fail("Left cannot be greater than right");
		} catch (IllegalArgumentException e) {
		}
		try {
			new RegionImpl(1, 4, 2, 3);
			fail("Top cannot be greater than bottom");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	void testRegionIntersect() throws NoIntersectionException {
		assert (equalRegions(region2122.intersect(region2133), region2122));
		try {
			region2222.intersect(null);
			fail("cannot intersect null");
		} catch (NoIntersectionException e) {
		}
	}

	@Test
	void testRegionUnion() {
		assert (equalRegions(region2222.union(region1111), region1122));
		assert (equalRegions(region3031.union(null), region3031));
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
	void testROIObservableRegisterMethods() {

		observableRedMutablePicture.registerROIObserver(observer1, region0000);
		observableRedMutablePicture.registerROIObserver(observer2, region2222);
		observableRedMutablePicture.registerROIObserver(observer2, region2133);
		observableRedMutablePicture.registerROIObserver(observer3, region3031);

		assertEquals(3, observableRedMutablePicture.findROIObservers(region0022).length);
		assertEquals(observer1, observableRedMutablePicture.findROIObservers(region0022)[0]);
		assertEquals(observer2, observableRedMutablePicture.findROIObservers(region0022)[1]);
		assertEquals(observer2, observableRedMutablePicture.findROIObservers(region0022)[2]);
		

		observableRedMutablePicture.registerROIObserver(observer3, region2122);

		assertEquals(4, observableRedMutablePicture.findROIObservers(region0022).length);
		assertEquals(observer1, observableRedMutablePicture.findROIObservers(region0022)[0]);
		assertEquals(observer2, observableRedMutablePicture.findROIObservers(region0022)[1]);
		assertEquals(observer2, observableRedMutablePicture.findROIObservers(region0022)[2]);
		assertEquals(observer3, observableRedMutablePicture.findROIObservers(region0022)[3]);

		observableRedMutablePicture.unregisterROIObserver(observer2);

		assertEquals(2, observableRedMutablePicture.findROIObservers(region0022).length);
		assertEquals(observer1, observableRedMutablePicture.findROIObservers(region0022)[0]);
		assertEquals(observer3, observableRedMutablePicture.findROIObservers(region0022)[1]);
		
		observableRedMutablePicture.registerROIObserver(observer2, region2222);
		observableRedMutablePicture.registerROIObserver(observer2, region2133);
		
		assertEquals(4, observableRedMutablePicture.findROIObservers(region0022).length);
		assertEquals(observer1, observableRedMutablePicture.findROIObservers(region0022)[0]);
		assertEquals(observer3, observableRedMutablePicture.findROIObservers(region0022)[1]);
		assertEquals(observer2, observableRedMutablePicture.findROIObservers(region0022)[2]);
		assertEquals(observer2, observableRedMutablePicture.findROIObservers(region0022)[3]);
		
		observableRedMutablePicture.unregisterROIObservers(region2222);
		
		assertEquals(1, observableRedMutablePicture.findROIObservers(region0022).length);
		assertEquals(observer1, observableRedMutablePicture.findROIObservers(region0022)[0]);
	}
	
	@Test
	void testPaintMethod() {
		
		observableRedMutablePicture.registerROIObserver(ob1, region0000);
		observableRedMutablePicture.registerROIObserver(ob2, region2222);
		observableRedMutablePicture.registerROIObserver(ob2, region2133);
		observableRedMutablePicture.registerROIObserver(ob3, region3031);

		observableRedMutablePicture.paint(0, 0, green);
		assertEquals(1, ob1.getCount());
		assertEquals(0, ob2.getCount());
		assertEquals(0, ob3.getCount());
		assert (equalRegions(ob1.NotifiedRegion().get(0), region0000));
		
		observableRedMutablePicture.paint(0, 0, observableRedImmutablePicture);
		assertEquals(2, ob1.getCount());
		assertEquals(2, ob2.getCount());
		assertEquals(1, ob3.getCount());
		assert (equalRegions(ob1.NotifiedRegion().get(0), region0000));
		assert (equalRegions(ob1.NotifiedRegion().get(1), region0000));
		assert (equalRegions(ob2.NotifiedRegion().get(0), region2222));
		assert (equalRegions(ob2.NotifiedRegion().get(1), region2133));
		assert (equalRegions(ob3.NotifiedRegion().get(0), region3031));
		
		observableRedMutablePicture.paint(1, 1, green);
		assertEquals(2, ob1.getCount());
		assertEquals(2, ob2.getCount());
		assertEquals(1, ob3.getCount());
		assert (equalRegions(ob1.NotifiedRegion().get(0), region0000));
		assert (equalRegions(ob1.NotifiedRegion().get(1), region0000));
		assert (equalRegions(ob2.NotifiedRegion().get(0), region2222));
		assert (equalRegions(ob2.NotifiedRegion().get(1), region2133));
		assert (equalRegions(ob3.NotifiedRegion().get(0), region3031));
		
		observableRedMutablePicture.paint(2, 2, 1, green);
		assertEquals(2, ob1.getCount());
		assertEquals(4, ob2.getCount());
		assertEquals(2, ob3.getCount());
		assert (equalRegions(ob1.NotifiedRegion().get(0), region0000));
		assert (equalRegions(ob1.NotifiedRegion().get(1), region0000));
		assert (equalRegions(ob2.NotifiedRegion().get(0), region2222));
		assert (equalRegions(ob2.NotifiedRegion().get(1), region2133));
		assert (equalRegions(ob2.NotifiedRegion().get(2), region2222));
		assert (equalRegions(ob2.NotifiedRegion().get(3), region2133));
		assert (equalRegions(ob3.NotifiedRegion().get(0), region3031));
		assert (equalRegions(ob3.NotifiedRegion().get(1), region3131));
	}

	@Test
	void testSuspendAndResume() {

		observableRedMutablePicture.registerROIObserver(ob1, region0000);
		observableRedMutablePicture.registerROIObserver(ob2, region2222);
		observableRedMutablePicture.registerROIObserver(ob2, region2133);
		observableRedMutablePicture.registerROIObserver(ob3, region3031);
		
		observableRedMutablePicture.suspendObservable();
		
		observableRedMutablePicture.paint(0, 0, green);		
		observableRedMutablePicture.paint(1, 1, green);
		assertEquals(0, ob1.getCount());
		assertEquals(0, ob2.getCount());
		assertEquals(0, ob3.getCount());
		
		observableRedMutablePicture.resumeObservable();
		assertEquals(1, ob1.getCount());
		assertEquals(0, ob2.getCount());
		assertEquals(0, ob3.getCount());
		assert (equalRegions(ob1.NotifiedRegion().get(0), region0000));
		
		observableRedMutablePicture.suspendObservable();
		
		observableRedMutablePicture.paint(0, 0, green);
		observableRedMutablePicture.paint(1, 1, green);
		observableRedMutablePicture.paint(2, 2, green);
		
		observableRedMutablePicture.resumeObservable();
		assertEquals(2, ob1.getCount());
		assertEquals(2, ob2.getCount());
		assertEquals(0, ob3.getCount());
		assert (equalRegions(ob1.NotifiedRegion().get(0), region0000));
		assert (equalRegions(ob1.NotifiedRegion().get(1), region0000));
		assert (equalRegions(ob2.NotifiedRegion().get(0), region2222));
		assert (equalRegions(ob2.NotifiedRegion().get(1), region2122));
	}
		
	private static boolean equalRegions(Region r1, Region r2) {
		return (r1.getLeft() == r2.getLeft() && r1.getTop() == r2.getTop() && r1.getBottom() == r2.getBottom()
				&& r1.getRight() == r2.getRight());
	}

}