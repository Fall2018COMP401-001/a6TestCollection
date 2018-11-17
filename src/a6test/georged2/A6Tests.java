package a6test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import a6.*;

public class A6Testss {
	
	Pixel blue = new ColorPixel(0.0, 0.0, 1.0);
	Pixel red = new ColorPixel(1.0, 0.0, 0.0);
	
	Picture blue3by3ImmutablePic = new ImmutablePixelArrayPicture(new Pixel[][] {{blue, blue, blue},{blue,blue,blue},{blue,blue,blue}}, "Blue");
	ObservablePicture immutableBlue3by3= new ObservablePictureImpl(blue3by3ImmutablePic);
	
	ROIObserver observerA = new ROIObserverImpl();
	ROIObserver observerB = new ROIObserverImpl();
	ROIObserver observerC = new ROIObserverImpl();
	
	Region a = new RegionImpl(0, 0, 0, 0);
	Region b = new RegionImpl(1, 2, 2, 2);
	Region c = new RegionImpl(2, 2, 3, 3);

	@Test
	public void testIllegalRegionConstructor() {
		try {
			Region invalidRegion = new RegionImpl(10, 5, 2, 10);
			fail("Left cannot be greater than right.");
		} catch (IllegalArgumentException e) {
		}
		try {
			Region invalidRegion = new RegionImpl(0, 10, 2, 5);
			fail("Top cannot be greater than bottom.");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testNoIntersection() throws NoIntersectionException{
		try {
			Region f= new RegionImpl(12, 12, 16, 16);
			a.intersect(f);
			fail("there should have been an exception thrown");
		}catch (NoIntersectionException e) {
		}
	}
	
	@Test
	public void testRegionImplMethods() throws NoIntersectionException {
		Region d= b.intersect(c);
		try {
			Region e = b.intersect(c);
		}catch (NoIntersectionException e){
		}
		assertEquals(d.getTop(), b.getTop());
		assertEquals(d.getLeft(), c.getLeft());
		assertEquals(d.getRight(), b.getRight());
		assertEquals(d.getBottom(), b.getBottom());
	}
	
	@Test
	public void nullUnionTest() {
		Region a = new RegionImpl(3, 3, 6, 6);
		Region union = a.union(null);

		assertEquals(a.getLeft(), union.getLeft());
		assertEquals(a.getTop(), union.getTop());
		assertEquals(a.getRight(), union.getRight());
		assertEquals(a.getBottom(), union.getBottom());
	}
	
	@Test
	public void testRegisteringObservers() {
		immutableBlue3by3.registerROIObserver(observerA, a);
		immutableBlue3by3.registerROIObserver(observerB, b);
		immutableBlue3by3.registerROIObserver(observerC, c);
		
		assertEquals(1,  immutableBlue3by3.findROIObservers(a).length);
		assertEquals(1,  immutableBlue3by3.findROIObservers(new RegionImpl(3,3,3,3)).length);
	}
	
	@Test
	public void testUnregisteringObserversRegions() {
		immutableBlue3by3.unregisterROIObservers(new RegionImpl(3,3,3,3));
		ROIObserver[] remainingObservers = immutableBlue3by3.findROIObservers(new RegionImpl(0,0,3,3));
		System.out.println(remainingObservers.length +"  ");
		
	}
	
	@Test
	public void paintMethodsTests() {
		Picture p = immutableBlue3by3.paint(1, 0, 2, 2, red);
		for (int y = 0; y <= 2; y++) {
			for (int x = 1; x <= 2; x++) {
				assertEquals(true, checkPixels(red, p.getPixel(x, y)));
			}
			assertEquals(true, checkPixels(blue, p.getPixel(0, y)));
		}
	}
	
	static boolean checkPixels(Pixel p1, Pixel p2) {
		return (p1.getRed() == p2.getRed() && p2.getGreen() == p2.getGreen() && p1.getBlue() == p2.getBlue());
	}
}
