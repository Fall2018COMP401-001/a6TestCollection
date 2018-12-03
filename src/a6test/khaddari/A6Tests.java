package a6test.khaddari;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import a6.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;




class A6Tests {
	

	@Test
	public void theUnionTests() {
		Region top = new RegionImpl(0, 0, 8, 8);
		Region bottom = new RegionImpl(4, 4, 8, 8);
		Region far = new RegionImpl(8, 8, 20, 20);
		
		Region union_top_bottom = new RegionImpl(0, 0, 6, 6);
		Region test_top_bottom = top.union(bottom);
		assertEquals(union_top_bottom.getLeft(), test_top_bottom.getLeft());
		assertEquals(union_top_bottom.getTop(), test_top_bottom.getTop());
		assertEquals(union_top_bottom.getRight(), 6);
		assertEquals(union_top_bottom.getBottom(), 6);
		
		Region union_far_top = new RegionImpl(0, 0, 12, 12);
		Region test_far_top = top.union(far);
		assertEquals(union_far_top.getLeft(), test_far_top.getLeft());
		assertEquals(union_far_top.getTop(), test_far_top.getTop());
		assertEquals(union_far_top.getRight(), 12);
		assertEquals(union_far_top.getBottom(), 12);
		
		assertTrue(union_far_top.union(null) == union_far_top);
	}
	
	@Test 
	public void testObservablePictureImpl() {
		
		Pixel[][] black_array=new Pixel [12][12];
		for (int i=0;i<black_array.length;i++) {
			for (int j=0;j<black_array[i].length;j++) {
				black_array[i][j]=new GrayPixel(1.0);
			}
		}
		Picture mutable_black=new MutablePixelArrayPicture(black_array, "mutable black picture");
		ObservablePicture observable_black=new ObservablePictureImpl(mutable_black);
		
		Pixel[][] white_array=new Pixel [12][12];
		for (int i=0;i<white_array.length;i++) {
			for (int j=0;j<white_array[i].length;j++) {
				white_array[i][j]=new GrayPixel(0.0);
			}
		}
		Picture mutable_white=new MutablePixelArrayPicture(white_array, "mutable white picture");
		ObservablePicture observable_white=new ObservablePictureImpl(mutable_white);
		
		ObservablePicture test = new ObservablePictureImpl(observable_black);
		try {
			ObservablePicture failed = new ObservablePictureImpl(null);
			fail("input cannot be null");
		}catch(IllegalArgumentException e) {
		}
		
		// Test Observable methods
		Region r = new RegionImpl(8, 8, 20, 20);
		Region r2 = new RegionImpl(0, 0, 12, 12);
		Region r3 = new RegionImpl(12, 12, 25, 25);
		
		ROIObserver[] observers = observable_white.findROIObservers(r);
		assertEquals(observers.length, 0);
		ROIObserverImpl roiObserver1 = new ROIObserverImpl(r);
		ROIObserverImpl roiObserver2 = new ROIObserverImpl(r2);
		observable_black.registerROIObserver(roiObserver1,r);
		observable_black.registerROIObserver(roiObserver2,r2);
		
		observers = observable_black.findROIObservers(r);
		assertEquals(observers.length, 2);
		observers = observable_black.findROIObservers(r2);
		assertEquals(observers.length, 2);
		observers = observable_black.findROIObservers(r3);
		assertEquals(observers.length, 0);

		observable_black.unregisterROIObserver(roiObserver1) ;
		observable_black.unregisterROIObservers(r2);
		
		observers = observable_black.findROIObservers(r);
		assertEquals(observers.length, 0);
		observers = observable_black.findROIObservers(r2);
		assertEquals(observers.length, 0);


	}
	
	
	//my test
	@Test
	public void IntersectTest() {
		Region one = new RegionImpl(2, 2, 5, 5);
		Region two = new RegionImpl(4, 4, 7, 7);
		Region three = new RegionImpl(6, 6, 10, 10);


		try {
			Region intersect = one.intersect(two);
			assertEquals(intersect.getLeft(), 4);
			assertEquals(intersect.getTop(), 4);
			assertEquals(intersect.getRight(), 5);
			assertEquals(intersect.getBottom(), 5);
		}
		catch(Exception e) { 
			fail("Error");
		}

		try {
			Region intersectB = one.intersect(three);
			fail("No Intersection Exception");
		}
		catch(NoIntersectionException e) {
		}
	}


}
