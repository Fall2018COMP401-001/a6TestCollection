package a6test.kerandby;

import static org.junit.Assert.*;


import org.junit.Test;

import a6.ColorPixel;
import a6.ImmutablePixelArrayPicture;
import a6.MutablePixelArrayPicture;
import a6.ObservablePictureImpl;
import a6.Picture;
import a6.Pixel;
import a6.Region;
import a6.RegionImpl;

public class A6Tests {

	Pixel red = new ColorPixel(1, 0, 0);
	Pixel blue = new ColorPixel(0, 0, 1);
	
	Picture blueMutable3by3 = new MutablePixelArrayPicture(new Pixel[][] {{blue,blue,blue},{blue,blue,blue},{blue,blue,blue}}, "Blue");
	Picture blueImmutable3by3 = new ImmutablePixelArrayPicture(new Pixel[][] {{blue,blue,blue},{blue,blue,blue},{blue,blue,blue}}, "Blue");
	
	Region region2133 = new RegionImpl(2,1,3,3);
	Region region1122 = new RegionImpl(1,1,2,2);
	Region region2122 = new RegionImpl(2,1,2,2);
	Region region1133 = new RegionImpl(1,1,3,3);
	Region region3031 = new RegionImpl(3,0,3,1);
	
	

	@Test
	public void testRegionImplConstructor() {
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
	public void testObservablePictureImplConstructor() {
		try {
			Picture p = new ObservablePictureImpl(null);
			fail("Picture cannot be null.");
		} catch (IllegalArgumentException e) {
		}
	}
	
	@Test
	public void nullRegionUnionTest() {
		Region a = new RegionImpl(1,1,6,5);
		Region union = a.union(null);
		
		assertEquals(a.getLeft(), union.getLeft());
		assertEquals(a.getTop(), union.getTop());
		assertEquals(a.getRight(), union.getRight());
		assertEquals(a.getBottom(), union.getBottom());
		assertEquals(a, union);
	}
	@Test
	public void basicRegionIntersectTest() {
		Region a = new RegionImpl(0,0,5,5);
		Region b = new RegionImpl(2,2,4,4);
		Region c = new RegionImpl(0,0,8,2);
		Region d = new RegionImpl(0,0,5,2);
		Region f = new RegionImpl(10,10,10,10);
		
		try {
		Region intersect = a.intersect(b);
		equalRegions(b, intersect);
		intersect = a.intersect(c);
		equalRegions(d, intersect);
		} catch (Exception e) {	}
		
		try {
		Region intersect = a.intersect(f);
		fail("no intersection");
		} catch ( Exception e ) {
			
		}
	}
	
	@Test
	public void basicRegionUnionTest() {
		Region a = new RegionImpl(0, 0, 5, 5);
		Region b = new RegionImpl(6, 6, 10, 10);
		
		Region union = a.union(b);
		
		assertEquals(0, union.getLeft());
		assertEquals(0, union.getTop());
		assertEquals(10, union.getRight());
		assertEquals(10, union.getBottom());
		
		// Try the other way also.
		
		union = b.union(a);
		
		assertEquals(0, union.getLeft());
		assertEquals(0, union.getTop());
		assertEquals(10, union.getRight());
		assertEquals(10, union.getBottom());
		
	}
	
	static void equalRegions(Region r1, Region r2) {
		assertTrue(r1.getLeft() == r2.getLeft());
		assertTrue(r1.getTop() == r2.getTop());
		assertTrue( r1.getRight() == r2.getRight());
		assertTrue( r1.getBottom() == r2.getBottom() );
	}
	
	static boolean equalPixels(Pixel p1, Pixel p2) {
		return (p1.getRed() == p2.getRed() && p2.getGreen() == p2.getGreen() && p1.getBlue() == p2.getBlue());
	}


}
