package a6test.mattyg;

import static org.junit.Assert.*;

import org.junit.Test;

import a6.*;

public class A6Tests {

	Pixel red = new ColorPixel(1.0, 0.0, 0.0);
	Pixel green = new ColorPixel(0.0, 1.0, 0.0);
	Pixel blue = new ColorPixel(0.0, 0.0, 1.0);
	
	Pixel[][] redArray = {{red, red, red},
						  {red, red, red},
						  {red, red, red}}; 
	
	
	Picture redPicture = new MutablePixelArrayPicture(redArray, "red picture");
	
	@Test
	public void someRegionTest() {
		try {
			Region r1 = new RegionImpl(2, 2, 3, 3);
			Region r2 = new RegionImpl(4, 4, 5, 5);
			Region r3 = r2.intersect(r1);
			fail("Illegal intersection");
			
		} catch (NoIntersectionException e) {
			
		}
	}
	
	@Test
	public void observerListTest() {
		ObservablePicture testPic = new ObservablePictureImpl(redPicture);
		
		ROIObserver a = new ROIObserverImpl();
		ROIObserver b = new ROIObserverImpl();
		
		testPic.registerROIObserver(a, new RegionImpl(0, 0, 1, 1));
		testPic.registerROIObserver(b, new RegionImpl(1, 1, 2, 2));
		
		ROIObserver[] observers = testPic.findROIObservers(new RegionImpl(0, 0, 2, 2));
		
		assertEquals(a, observers[0]);
		assertEquals(b, observers[1]);
	}
	
	@Test
	public void unregisterObserversTest() {
		ObservablePicture testPic = new ObservablePictureImpl(redPicture);
		
		ROIObserver a = new ROIObserverImpl();
		ROIObserver b = new ROIObserverImpl();
		
		testPic.registerROIObserver(a, new RegionImpl(0, 0, 1, 1));
		testPic.registerROIObserver(b, new RegionImpl(1, 1, 2, 2));
		
		ROIObserver[] observers = testPic.findROIObservers(new RegionImpl(0, 0, 2, 2));
		
		assertEquals(a, observers[0]);
		assertEquals(b, observers[1]);
		
		testPic.unregisterROIObserver(b);
		
		ROIObserver[] newObservers = testPic.findROIObservers(new RegionImpl(0, 0, 2, 2));
		
		assertEquals(a, observers[0]);
		
		if (newObservers.length != 1) {
			fail("Incorrect ROIObservers array");
		}
	}
}