package enzohuang;

import static org.junit.Assert.*;

import org.junit.Test;

import a6.*;

public class A6Test{

	static public String[] getTestNames() {
		String[] test_names = new String[5];
		test_names[0] = "nullRegionUnionTest";
		test_names[1] = "basicRegionUnionTest";
		test_names[2] = "nullRegionIntersectTest";
		test_names[3] = "basicRegionIntersectionTest";
		test_names[4] = "observerNotifyTest";
		return test_names;
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

	@Test
	public void nullRegionIntersectTest() {
		try {
			Region a = new RegionImpl(1,1,6,5);
			Region intersection = a.intersect(null);
			fail("cannot be null region");
		} catch (Exception e){
		}
	}
	
	@Test
	public void basicRegionIntersectionTest() {
		Region a = new RegionImpl(0, 0, 5, 5);
		Region b = new RegionImpl(4, 4, 10, 10);
		
		try {
			Region intersection = a.intersect(b);
			assertEquals(4, intersection.getLeft());
			assertEquals(4, intersection.getTop());
			assertEquals(5, intersection.getRight());
			assertEquals(5, intersection.getBottom());
			
			// Try the other way also.
			
			intersection = b.intersect(a);
			
			assertEquals(4, intersection.getLeft());
			assertEquals(4, intersection.getTop());
			assertEquals(5, intersection.getRight());
			assertEquals(5, intersection.getBottom());
		} catch (Exception e) {
			fail("this is not a null region");
		}
	}
	
	@Test
	public void observerNotifyTest() {
		Region a = new RegionImpl(0, 0, 5, 5);
		Region b = new RegionImpl(4, 4, 8, 8);
		
		Pixel red = new ColorPixel(1, 0, 0);
		Pixel green = new ColorPixel(0, 1, 0);
		Pixel blue = new ColorPixel(0, 0, 1);
		Pixel[][] redPArray = {{red, red, red, red, red, red, red, red, red},
				{red, red, red, red, red, red, red, red, red},
				{red, red, red, red, red, red, red, red, red},
				{red, red, red, red, red, red, red, red, red},
				{red, red, red, red, red, red, red, red, red},
				{red, red, red, red, red, red, red, red, red},
				{red, red, red, red, red, red, red, red, red},
				{red, red, red, red, red, red, red, red, red},
				{red, red, red, red, red, red, red, red, red},
				};

		Pixel[][] greenPArray = {{green, green, green}, 
				{green, green, green},
				{green, green, green}};
		Picture p1 = new ImmutablePixelArrayPicture(redPArray, "test");
		Picture p2 = new ImmutablePixelArrayPicture(greenPArray, "test2");
		ROIObserver o1 = new ROIObserverImpl();
		ROIObserver o2 = new ROIObserverImpl();
		
		ObservablePicture op1 = new ObservablePictureImpl(p1);
		op1.registerROIObserver(o1, a);
		op1.registerROIObserver(o1, b);
		op1.registerROIObserver(o2, a);
		
		op1.paint(5, 5, blue);
		
		assertEquals(3, ((ObservablePictureImpl) op1).getNotifyCount());
		op1.clearNotifications;
		
		op1.unregisterROIObservers(a);
		
		op1.paint(5, 5, green);
		
		op1.registerROIObserver(o1, a);
		op1.registerROIObserver(o2, a);
		op1.registerROIObserver(o1, b);
		
		assertEquals(1, ((ObservablePictureImpl) op1).getNotifyCount());
		op1.clearNotifications();
		
		op1.paint(5, 5, blue);
		
		assertEquals(4, ((ObservablePictureImpl) op1).getNotifyCount());
		op1.clearNotifications();
		
		op1.suspendObservable();
		
		op1.paint(2, 2, green);
		op1.paint(6, 6, green);
		op1.paint(8, 8, green);
		
		op1.resumeObservable();
		
		assertEquals(4, ((ObservablePictureImpl) op1).getNotifyCount());
		op1.clearNotifications();
		
		op1.suspendObservable();
		op1.paint(0, 0, p2);
		op1.paint(4, 4, p2);
		op1.resumeObservable();
		
		assertEquals(4, ((ObservablePictureImpl) op1).getNotifyCount());
	}
	
}
