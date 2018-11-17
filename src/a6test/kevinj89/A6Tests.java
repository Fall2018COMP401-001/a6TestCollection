package a6test.kevinj89;

import static org.junit.Assert.*;
import org.junit.Test;
import a6.*;

public class A6Tests {

	Pixel red = new ColorPixel(1, 0, 0);
	Pixel green = new ColorPixel(0, 1, 0);
	Pixel[][] parray = new Pixel[5][5];
	
	@Test
	public void testPaintRectangle() {
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				parray[i][j] = red;
			}
		}
		
		Picture pic = new MutablePixelArrayPicture(parray,"red pic");
		Picture observablepic = new ObservablePictureImpl(pic);
		
		observablepic.paint(1, 1, 3, 3, green);
		
		for (int i = 1; i < 4; i++) {
			for( int j = 1; j < 4; j++) {
				assertTrue(checkPixels(observablepic.getPixel(i, j), green));
			}
		}
		assertFalse(checkPixels(observablepic.getPixel(0,0), green));
		assertFalse(checkPixels(observablepic.getPixel(4,4), green));
	}
	
	@Test
	public void testPaintCircle() {
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				parray[i][j] = red;
			}
		}
		
		Picture pic = new MutablePixelArrayPicture(parray,"red pic");
		Picture observablepic = new ObservablePictureImpl(pic);
		
		observablepic.paint(2, 2, 1, green);
		
		assertTrue(checkPixels(observablepic.getPixel(2, 2), green));
		assertTrue(checkPixels(observablepic.getPixel(2, 3), green));
		assertTrue(checkPixels(observablepic.getPixel(3, 2), green));
		assertTrue(checkPixels(observablepic.getPixel(1, 2), green));
		assertTrue(checkPixels(observablepic.getPixel(2, 1), green));
		
		assertFalse(checkPixels(observablepic.getPixel(1,1), green));
		assertFalse(checkPixels(observablepic.getPixel(3,3), green));
	}
	
	@Test
	public void testRegionIntersect() {
		
		Region r1 = new RegionImpl(0,0,3,3);
		Region r2 = new RegionImpl(2,2,4,4);
		
		Region r1r2intersection = new RegionImpl(2,2,3,3);
		Region nullRegion = null;
		
		try {
			r1.intersect(r2);
			assertTrue(checkRegions(r1r2intersection, r1.intersect(r2)));
		} catch (NoIntersectionException e){
			fail("intersection test should have passed");
		}
		
		try {
			r2.intersect(r1);
			assertTrue(checkRegions(r1r2intersection, r2.intersect(r1)));
		} catch (NoIntersectionException e){
			fail("intersection test should have passed");
		}
		
		try {
			r1.intersect(nullRegion);
			fail("region was null: should throw exception");
		} catch (NoIntersectionException e) {
			
		}
		
	}
	
	private boolean checkPixels(Pixel p1, Pixel p2) {
		if (p1.getRed() == p2.getRed() && p1.getGreen() == p2.getGreen()
				&& p1.getBlue() == p2.getBlue()) {
			return true;
		}
		return false;
	}
	
	private boolean checkRegions(Region r1, Region r2) {
		if (r1.getLeft() == r2.getLeft() && r1.getTop() == r2.getTop() &&
				r1.getRight() == r2.getRight() && r1.getBottom() == r2.getBottom()) {
			return true;
		}
		return false;
	}
}
