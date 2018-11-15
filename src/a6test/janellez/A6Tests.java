package a6test.janellez;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import a6.*;

class A6Tests {
	
	// Colored pixels
	Pixel red = new ColorPixel(1, 0, 0);
	Pixel green = new ColorPixel(0, 1, 0);
	Pixel blue = new ColorPixel(0, 0, 1);
	
	//Lists used in tests
	Region[] regionList = {new RegionImpl(2,2,2,2), new RegionImpl(0,0,1,1), 
			new RegionImpl(0,0,2,2), new RegionImpl(1,1,3,3),
			new RegionImpl(3,3,5,5), new RegionImpl(4,4,5,5)};
	
	/*
	 * This list contains the union between 
	 * the region and the one before it in regionList
	 * and the first regionList element is compared to null first
	*/
	Region[] unionList = {new RegionImpl(2,2,2,2), new RegionImpl(0,0,2,2), 
			new RegionImpl(0,0,2,2), new RegionImpl(0,0,3,3),
			new RegionImpl(1,1,5,5), new RegionImpl(3,3,5,5)};
	
	ROIObserver[] observerList = {new ROIObserverImpl(), new ROIObserverImpl(), 
								new ROIObserverImpl(), new ROIObserverImpl(),
								new ROIObserverImpl(), new ROIObserverImpl()};
	@Test
	public void testRegionConstructor() {
		//test constructors
		Region r;

		//valid ones
		try {
			r = new RegionImpl(0,0,1,1);
		} catch (IllegalArgumentException e) {
			fail("No exception should be thrown");
		} 
		try {
			r = new RegionImpl(2,2,2,2);
		} catch (IllegalArgumentException e) { 
			fail("No exception should be thrown"); 
		} 
		try {
			r = new RegionImpl(0,0,2,2);
		} catch (IllegalArgumentException e) { 
			fail("No exception should be thrown"); 
		} 
		try {
			r = new RegionImpl(1,1,3,3);
		} catch (IllegalArgumentException e) { 
			fail("No exception should be thrown"); 
		} 
		try {
			r = new RegionImpl(3,3,5,5);
		} catch (IllegalArgumentException e) {
			fail("No exception should be thrown"); 
		} 
		try {
			r = new RegionImpl(4,4,5,5);
		} catch (IllegalArgumentException e) {
			fail("No exception should be thrown"); 
		}
		
		//invalid ones
		try {
			r = new RegionImpl(1,1,0,0);
		}catch (IllegalArgumentException e) {}
		try {
			r = new RegionImpl(2,1,2,2); 
		} catch (IllegalArgumentException e) {}
		try {
			r = new RegionImpl(0,1,0,0); 
		} catch (IllegalArgumentException e) {}
		try {
			r = new RegionImpl(4,4,3,3); 
		} catch (IllegalArgumentException e) {}
		
		//Negative coordinates
		r = new RegionImpl(-3,0,-1,5);
		r = new RegionImpl(-2,0,4,4);
		try {
			r = new RegionImpl(0,1,-1,0); 
		} catch (IllegalArgumentException e) {}
		try {
			r = new RegionImpl(2,2,-3,-3); 
		} catch (IllegalArgumentException e) {}
	}
	
	@Test
	public void testRegionGetters() {
		Region r1 = regionList[0];
		assertEquals(2, r1.getLeft());
		assertEquals(2, r1.getTop());
		assertEquals(2, r1.getRight());
		assertEquals(2, r1.getBottom());
		
		r1 = regionList[1];
		assertEquals(0, r1.getLeft());
		assertEquals(0, r1.getTop());
		assertEquals(1, r1.getRight());
		assertEquals(1, r1.getBottom());
		
		r1 = regionList[2];
		assertEquals(0, r1.getLeft());
		assertEquals(0, r1.getTop());
		assertEquals(2, r1.getRight());
		assertEquals(2, r1.getBottom());
		
		r1 = regionList[3];
		assertEquals(1, r1.getLeft());
		assertEquals(1, r1.getTop());
		assertEquals(3, r1.getRight());
		assertEquals(3, r1.getBottom());
		
		r1 = regionList[4];
		assertEquals(3, r1.getLeft());
		assertEquals(3, r1.getTop());
		assertEquals(5, r1.getRight());
		assertEquals(5, r1.getBottom());
		
		r1 = regionList[5];
		assertEquals(4, r1.getLeft());
		assertEquals(4, r1.getTop());
		assertEquals(5, r1.getRight());
		assertEquals(5, r1.getBottom());
	}
	
	@Test
	public void testRegionUnion() {
		assertTrue(equalRegions(unionList[0], regionList[0].union(null)));
		assertTrue(equalRegions(unionList[1], regionList[1].union(regionList[0])));
		assertTrue(equalRegions(unionList[2], regionList[2].union(regionList[1])));
		assertTrue(equalRegions(unionList[3], regionList[3].union(regionList[2])));
		assertTrue(equalRegions(unionList[4], regionList[4].union(regionList[3])));
		assertTrue(equalRegions(unionList[5], regionList[4].union(regionList[5])));
	}
	
	@Test
	public void testRegionIntersect() {

		// Regions that should intersect
		Region intersect = new RegionImpl(0,0,1,1);
		try {
			assertTrue(equalRegions(intersect, regionList[1].intersect(regionList[2])));
		} catch (NoIntersectionException e) {
			fail("No exception should be thrown");
		}
		
		intersect = new RegionImpl(1,1,2,2);
		try {
			assertTrue(equalRegions(intersect, regionList[2].intersect(regionList[3])));
		} catch (NoIntersectionException e) {
			fail("No exception should be thrown");
		}
		
		intersect = new RegionImpl(3,3,3,3);
		try {
			assertTrue(equalRegions(intersect, regionList[3].intersect(regionList[4])));
		} catch (NoIntersectionException e) {
			fail("No exception should be thrown");
		}
		
		intersect = new RegionImpl(4,4,5,5);
		try {
			assertTrue(equalRegions(intersect, regionList[4].intersect(regionList[5])));
		} catch (NoIntersectionException e) {
			fail("No exception should be thrown");
		}
		
		intersect = new RegionImpl(2,2,2,2);
		try {
			assertTrue(equalRegions(intersect, regionList[0].intersect(regionList[3])));
		} catch (NoIntersectionException e) {
			fail("No exception should be thrown");
		}
		
		// Regions that should not intersect
		try {
			regionList[0].intersect(regionList[1]);
			fail("Exception should be thrown");
		} catch (NoIntersectionException e) {}
		
		try {
			regionList[0].intersect(regionList[5]);
			fail("Exception should be thrown");
		} catch (NoIntersectionException e) {}
		
		try {
			regionList[4].intersect(regionList[1]);
			fail("Exception should be thrown");
		} catch (NoIntersectionException e) {}
		
		try {
			regionList[0].intersect(regionList[4]);
			fail("Exception should be thrown");
		} catch (NoIntersectionException e) {}
		
	}
	
	private static boolean equalRegions(Region r1, Region r2) {
		return (r1.getLeft() == r2.getLeft() && r1.getTop() == r2.getTop() && r1.getBottom() == r2.getBottom()
				&& r1.getRight() == r2.getRight());
	}
	
}
