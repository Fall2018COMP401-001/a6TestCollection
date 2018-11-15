package a6test.zricky;

import static org.junit.jupiter.api.Assertions.*;
import a6.*;

import org.junit.jupiter.api.Test;


class A6Tests {
	
	ROIObserver observer_A = new ROIObserverImpl();
	ROIObserver observer_B = new ROIObserverImpl();
	ROIObserver observer_C = new ROIObserverImpl();
	
	
	
	Region region_A = new RegionImpl(2,2,5,5);
	Region region_B = new RegionImpl(1,1,3,3);
	Region region_C = new RegionImpl(2,2,5,5);
	Region region_D = new RegionImpl(7,7,9,9);
	
	
	@Test
	public void testregionImplContructor() {
		boolean thrown_A = false;
		boolean thrown_B = false;
		try {
			Region region_E = new RegionImpl(7,7,3,9);
		}
		catch (IllegalArgumentException e) {
			thrown_A = true;
		}
		assertTrue(thrown_A);
		try {
			Region region_F = new RegionImpl(7,7,9,3);
		}
		catch (IllegalArgumentException e) {
			thrown_B = true;
		}
		assertTrue(thrown_B);
	}
	
			
	@Test
	public void testIntersectMethod() {
		boolean thrown = false;
		Region region_X = new RegionImpl(2,2,3,3);
		Region region_Y = new RegionImpl(7,7,5,5);
		try {
			region_A.intersect(region_B);
			assertTrue((region_A.intersect(region_B)).equals(region_X));
		} catch (NoIntersectionException e){
			throw new IllegalArgumentException("Intersection is expected");
		}
		try {
			region_A.intersect(region_C);
			assertTrue((region_A.intersect(region_C)).equals(region_A));
		} catch (NoIntersectionException e){
			throw new IllegalArgumentException("Intersection is expected");
		}
		try {
			region_A.intersect(region_D);
		} catch (NoIntersectionException e){
			thrown = true;
		}
		assertTrue(thrown);
		
	}
	
	@Test
	public void testUnionMethod() {
		Region region_X = new RegionImpl(1,1,5,5);
		Region region_Y = new RegionImpl(2,2,9,9);
		
		assertTrue((region_A.union(region_B)).equals(region_X));
		assertTrue((region_A.union(region_C)).equals(region_A));
		assertTrue((region_A.union(region_D)).equals(region_Y));
	}
	
	@Test 
	public void testRegisterObservers() {
		Pixel[][] parray = new Pixel[20][20];
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				parray[i][j] = new ColorPixel(0, 0, 0);
			}
		}
		Picture picture = new MutablePixelArrayPicture(parray, "My caption ");
		ObservablePicture picture_A = new ObservablePictureImpl(picture);
		picture_A.registerROIObserver(observer_A, region_A);
		picture_A.registerROIObserver(observer_B, region_A);
		ROIObserver[] listA = {observer_A, observer_B};
		assertEquals(picture_A.findROIObservers(region_A), listA);
		
	}
	
	// helper method
	public boolean equals(Region region_A, Region region_B) {
		if (region_A.getLeft() == region_B.getLeft() && 
				region_A.getTop() == region_B.getTop() && 
				region_A.getRight() == region_B.getRight() &&
				region_A.getBottom() == region_B.getBottom()) {
			return true;
		}
		return false;
	}
}
