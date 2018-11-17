package a6test.rohaidb;

import static org.junit.Assert.*;

import org.junit.Test;

public class A6Tests {
	Region test = new RegionImpl (1,2,3,4);
	Pixel red = new ColorPixel(1, 0, 0);
	Pixel green = new ColorPixel(0, 1, 0);
	Pixel blue = new ColorPixel(0, 0, 1);
	Pixel[][] testArray = new Pixel[][] {new Pixel[] {red,green,blue}, new Pixel[] {red,green,blue}};
	Picture test2 = new MutablePixelArrayPicture(testArray, "why");
	
	@Test
	public void constructor() {
		try {
			Region test1 = new RegionImpl(3, 2, 1, 5); 
			assertFalse(false);
			} catch (IllegalArgumentException c) {
				assertTrue(true);
	}
		assertTrue(test.getLeft() == 1);
		assertTrue(test.getTop() == 2);
		assertTrue(test.getRight() == 3);
		assertTrue(test.getBottom() == 4);
		
	}
	
	@Test
	public void RegionImplIntersect() {
		try {
			test.intersect(null);
		} catch (NoIntersectionException c) {
			assertTrue(true);
		}
		
		try {
			test.intersect(new RegionImpl(0, 1, 1, 1));
		} catch (NoIntersectionException c) {
			assertTrue (true);
		}
		
		try {
			test.intersect(new RegionImpl (0, 0,1,1 ));
		} catch (NoIntersectionException c) {
			assertTrue(true);
		}
		
		try {
			test.intersect(new RegionImpl(5, 2, 7, 8));
		} catch (NoIntersectionException c) {
			assertTrue(true);
		}
		
		try {
			test.intersect(new RegionImpl (0, 0, 7, 5));
		} catch (NoIntersectionException c) {
			assertTrue(true);
		}
		
	}
	
	@Test
	public void RegionImplUnionIntersect() {
		assertEquals(test.union(null), test);
	}
	
	@Test
	public void ObservablePictureConstructor() {
		try {
		 ObservablePicture test1 = new ObservablePictureImpl(null);
		 } catch (IllegalArgumentException e) {
			 assertTrue(true);
		 }
		ObservablePicture test3 = new ObservablePictureImpl(test2);
		assertEquals(test3.getWidth(), 2 );
		assertEquals(test3.getHeight(), 3);
		assertTrue(test3.getCaption().equals("why"));
		test3.setCaption("hello");
		assertTrue(test3.getCaption().equals("hello"));
		
	}

}
