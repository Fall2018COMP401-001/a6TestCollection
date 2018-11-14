package a6test.tylery;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import a6.*;

class A6Tests {
	
	Pixel red = new ColorPixel(1, 0, 0);
	Pixel blue = new ColorPixel(0, 0, 1);
	
	Picture blueMutable3by3 = new MutablePixelArrayPicture(new Pixel[][] {{blue,blue,blue},{blue,blue,blue},{blue,blue,blue}}, "Blue");
	Picture blueImmutable3by3 = new ImmutablePixelArrayPicture(new Pixel[][] {{blue,blue,blue},{blue,blue,blue},{blue,blue,blue}}, "Blue");
	
	Region region2133 = new RegionImpl(2,1,3,3);
	Region region1122 = new RegionImpl(1,1,2,2);
	Region region2122 = new RegionImpl(2,1,2,2);
	Region region1133 = new RegionImpl(1,1,3,3);
	Region region3031 = new RegionImpl(3,0,3,1);
	
	ObservablePicture observableBlueMutable3by3 = new ObservablePictureImpl(blueMutable3by3);
	ObservablePicture observableBlueImmutable3by3 = new ObservablePictureImpl(blueImmutable3by3);
	
	ROIObserver observer1 = new ROIObserverImpl();
	ROIObserver observer2 = new ROIObserverImpl();
	ROIObserver observer3 = new ROIObserverImpl();
	
	@Test
	void testRegionImplConstructor() {
		try {
			Region invalid = new RegionImpl(2,1,1,2);
			fail("Left cannot be greater than right");
		} catch (IllegalArgumentException e) {
		}
		try {
			Region invalid = new RegionImpl(1,2,2,1);
			fail("Top cannot be greater than bottom");
		} catch (IllegalArgumentException e) {
		}
	}
	
	@Test
	void testRegionImplMethods() throws NoIntersectionException {
		assertEquals(2, region2133.getLeft());
		assertEquals(1, region2133.getTop());
		assertEquals(3, region2133.getRight());
		assertEquals(3, region2133.getBottom());
		assertEquals(true, equalRegions(region1122.intersect(region2133), region2122));
		assertEquals(true, equalRegions(region1122.union(region2133), region1133));
	}
	
	@Test
	void testObservablePictureImplConstructor() {
		try {
			Picture p = new ObservablePictureImpl(null);
			fail("Picture cannot be null.");
		} catch (IllegalArgumentException e) {
		}
	}
	
	@Test
	void testObservablePictureImplPaintMethods() {
		assertEquals(blue, observableBlueImmutable3by3.getPixel(1, 2));
		Picture p = observableBlueImmutable3by3.paint(1, 2, red);
		assertEquals(true, equalPixels(red, p.getPixel(1, 2)));
		assertEquals(true, equalPixels(red, observableBlueImmutable3by3.getPixel(1, 2)));
		assertEquals(true, equalPixels(blue, blueImmutable3by3.getPixel(1, 2)));
		
		p = observableBlueImmutable3by3.paint(1, 0, 2, 2, red);
		for (int y = 0; y <= 2; y++) {
			for (int x = 1; x <= 2; x++) {
				assertEquals(true, equalPixels(red, p.getPixel(x, y)));
			}
			assertEquals(true, equalPixels(blue, p.getPixel(0, y)));
		}
	}
	
	@Test
	void testROIObservableRegisterAndFindObserverMethods() {
		observableBlueMutable3by3.registerROIObserver(observer1, region1133);
		observableBlueMutable3by3.registerROIObserver(observer1, region3031);
		observableBlueMutable3by3.registerROIObserver(observer2, region2122);
		observableBlueMutable3by3.registerROIObserver(observer3, region3031);
		
		assertEquals(2, observableBlueMutable3by3.findROIObservers(region1122).length);
		assertEquals(observer1, observableBlueMutable3by3.findROIObservers(region1122)[0]);
		assertEquals(observer2, observableBlueMutable3by3.findROIObservers(region1122)[1]);
	}
	
	static boolean equalRegions(Region r1, Region r2) {
		return (r1.getLeft() == r2.getLeft() && r1.getTop() == r2.getTop() && r1.getBottom() == r2.getBottom() && r1.getRight() == r2.getRight());
	}
	
	static boolean equalPixels(Pixel p1, Pixel p2) {
		return (p1.getRed() == p2.getRed() && p2.getGreen() == p2.getGreen() && p1.getBlue() == p2.getBlue());
	}

}
