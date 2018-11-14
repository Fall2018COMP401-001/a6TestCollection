package a6test.gruehle;


import org.junit.*;

import static org.junit.Assert.*;

import a6.*;

import a6test.gruehle.ROIObserverImpl;


public class A6Tests {

		Pixel red = new ColorPixel(1.0, 0.0, 0.0);
		Pixel green = new ColorPixel(0.0, 1.0, 0.0);
		Pixel blue = new ColorPixel(0.0, 0.0, 1.0);
		Pixel white = a6.Pixel.WHITE;
		Pixel black = a6.Pixel.BLACK;
		Pixel randomColor = new ColorPixel(.123, .456, .789);
		
		Region region1133 = new RegionImpl(1, 1, 3, 3);
		Region region2244 = new RegionImpl(2, 2, 4, 4);
		Region region2233 = new RegionImpl(2, 2, 3, 3); // intersection of 1133 and 2244
		Region region1144 = new RegionImpl(1, 1, 4, 4); // union of 1133 and 2244
		Region region3153 = new RegionImpl(3, 1, 5, 3); // only crosses 1133 on right column
		Region region3133 = new RegionImpl(3, 1, 3, 3); // intersection of 1133 and 3153
		Region region1153 = new RegionImpl(1, 1, 5, 3); // union of 1133 and 3153
		Region region4153 = new RegionImpl(4, 1, 5, 3); // doesn't intersect 1133
		Region region1335 = new RegionImpl(1, 3, 3, 5); // only crosses on bottom row
		Region region1333 = new RegionImpl(1, 3, 3, 3); // intersection of 1133 and 1335
		Region region1435 = new RegionImpl(1, 4, 3, 5); // doesn't intersect 1133
		
		Picture nullPicture = null;
		
		Picture red3x3MutablePicture = new MutablePixelArrayPicture(newPixelArray(3, 3, red), "red picture");
		Picture white3x3MutablePicture = new MutablePixelArrayPicture(newPixelArray(3, 3, white), "white picture");
		Picture blue5x5MutablePicture = new MutablePixelArrayPicture(newPixelArray(5, 5, blue), "blue picture");
		Picture random3x3MutablePicture = new MutablePixelArrayPicture(newRandomPixelArray(3, 3), "random 3x3 picture");
		
		@Test
		public void testRegionImplConstructor() {
			try {
				Region invalidRegion = new RegionImpl(2, 1, 1, 3);
				fail("left can't be greater than right");
			} catch (IllegalArgumentException e) {
			}
			try {
				Region invalidRegion = new RegionImpl(2, 3, 3, 1);
				fail("top can't be greater than bottom");
			} catch (IllegalArgumentException e) {
			}
		}
		
		@Test
		public void testRegionImplGetters() {
		 
			Region testRegion = new RegionImpl(1, 2, 3, 4);
			
			assertEquals(testRegion.getLeft(), 1);
			assertEquals(testRegion.getTop(), 2);
			assertEquals(testRegion.getRight(), 3);
			assertEquals(testRegion.getBottom(), 4);
		}
		
		@Test
		public void testRegionIntersectMethod() throws NoIntersectionException {
			assertTrue(equalRegions(region1133.intersect(region2244), region2233));
			assertTrue(equalRegions(region1133.intersect(region3153), region3133));
			assertTrue(equalRegions(region1133.intersect(region1335), region1333));
			assertTrue(equalRegions(region2244.intersect(region1133), region2233));
			
			try {
				region1133.intersect(region4153);
				fail("doesn't intersect");
			} catch (NoIntersectionException e) {
			}
			try {
				region1133.intersect(region1435);
				fail("doesn't intersect");
			} catch (NoIntersectionException e) {
			}
			try {
				region1133.intersect(null);
				fail("doesn't intersect");
			} catch (NoIntersectionException e) {
			}
			
		}
		
		@Test
		public void testRegionUnionMethod() throws NoIntersectionException {
			Region region1234 = new RegionImpl(1, 2, 3, 4);
			Region region5678 = new RegionImpl(5, 6, 7, 8);
			Region region1278 = new RegionImpl(1, 2, 7, 8);
			
			assertTrue(equalRegions(region1234.union(region5678), region1278));
			
			assertTrue(equalRegions(region1133.union(region2244), region1144));
			assertTrue(equalRegions(region1133.union(null), region1133));
			assertTrue(equalRegions(region1133.union(region3153), region1153));
		}
		
		@Test
		public void testObservablePaintMethod() {
			
			Picture random3x3ImmutablePic = new ImmutablePixelArrayPicture(newRandomPixelArray(3, 3), "random 3x3 picture");
			
			ObservablePicture observableRandomPic = new ObservablePictureImpl(random3x3ImmutablePic);
			
			Picture paintedObservablePic = observableRandomPic.paint(1, 1, white);
			
			for (int i=0; i<paintedObservablePic.getWidth(); i++) {
				for (int j=0; j<paintedObservablePic.getHeight(); j++) {
					if (i == 1 && j == 1) {
						assertTrue(check_for_pixel_equality(paintedObservablePic.getPixel(1, 1), white));
					} else {
						assertTrue(check_for_pixel_equality(paintedObservablePic.getPixel(i, j), observableRandomPic.getPixel(i, j)));
					}
				}
			}
			
		}
		
		@Test
		public void testObservableRectanglePaintMethod() {
			Picture random3x3ImmutablePic = new ImmutablePixelArrayPicture(newRandomPixelArray(3, 3), "random 3x3 picture");
			
			ObservablePicture observableRandomPic = new ObservablePictureImpl(random3x3ImmutablePic);
			
			Picture paintedObservablePic = observableRandomPic.paint(0, 1, 1, 0, white);
			
			for (int i=0; i<paintedObservablePic.getWidth(); i++) {
				for (int j=0; j<paintedObservablePic.getHeight(); j++) {
					if (i <= 1 && j <= 1) {
						assertTrue(check_for_pixel_equality(paintedObservablePic.getPixel(1, 1), white));
					} else {
						assertTrue(check_for_pixel_equality(paintedObservablePic.getPixel(i, j), observableRandomPic.getPixel(i, j)));
					}
				}
			}
		}
		
		@Test
		public void testNotifyObservers() {
			Picture red4x4MutablePic = new MutablePixelArrayPicture(newPixelArray(4, 4, red), "red 4x4 picture");
			
			ObservablePicture observablePic = new ObservablePictureImpl(red4x4MutablePic);
			
			ROIObserverImpl a = new ROIObserverImpl();
			ROIObserverImpl b = new ROIObserverImpl();
			ROIObserverImpl c = new ROIObserverImpl();
			ROIObserverImpl d = new ROIObserverImpl();
			
			observablePic.registerROIObserver(a, new RegionImpl(0, 0, 1, 1));  // top-left corner
			observablePic.registerROIObserver(b, new RegionImpl(2, 0, 3, 1));  // top-right corner 
			observablePic.registerROIObserver(c, new RegionImpl(0, 2, 1, 3));  // bottom-left corner
			observablePic.registerROIObserver(d, new RegionImpl(2, 2, 3, 3));  // bottom-right corner
			
			assertEquals(a.getTimesNotified(), 0);
			assertEquals(b.getTimesNotified(), 0);
			assertEquals(c.getTimesNotified(), 0);
			assertEquals(d.getTimesNotified(), 0);
			
			observablePic.paint(0, 0, blue);
			
			assertEquals(a.getTimesNotified(), 1);
			assertEquals(b.getTimesNotified(), 0);
			assertEquals(c.getTimesNotified(), 0);
			assertEquals(d.getTimesNotified(), 0);		
		}
		
		@Test
		public void suspendedObservableTest() {
			Picture red4x4MutablePic = new MutablePixelArrayPicture(newPixelArray(4, 4, red), "red 4x4 picture");
			
			ObservablePicture observablePic = new ObservablePictureImpl(red4x4MutablePic);
			
			ROIObserverImpl a = new ROIObserverImpl();
			ROIObserverImpl b = new ROIObserverImpl();
			ROIObserverImpl c = new ROIObserverImpl();
			ROIObserverImpl d = new ROIObserverImpl();
			
			observablePic.registerROIObserver(a, new RegionImpl(0, 0, 1, 1));  // top-left corner
			observablePic.registerROIObserver(b, new RegionImpl(2, 0, 3, 1));  // top-right corner 
			observablePic.registerROIObserver(c, new RegionImpl(0, 2, 1, 3));  // bottom-left corner
			observablePic.registerROIObserver(d, new RegionImpl(2, 2, 3, 3));  // bottom-right corner
			
			assertEquals(a.getTimesNotified(), 0);
			assertEquals(b.getTimesNotified(), 0);
			assertEquals(c.getTimesNotified(), 0);
			assertEquals(d.getTimesNotified(), 0);
			
			observablePic.suspendObservable();
			observablePic.paint(0, 0, blue);
			
			assertEquals(a.getTimesNotified(), 0);
			assertEquals(b.getTimesNotified(), 0);
			assertEquals(c.getTimesNotified(), 0);
			assertEquals(d.getTimesNotified(), 0);
			
			observablePic.resumeObservable();
			
			assertEquals(a.getTimesNotified(), 1);
			assertEquals(b.getTimesNotified(), 0);
			assertEquals(c.getTimesNotified(), 0);
			assertEquals(d.getTimesNotified(), 0);			
		}
		
		@Test
		public void testNofityRectangle() {
			Picture red4x4MutablePic = new MutablePixelArrayPicture(newPixelArray(4, 4, red), "red 4x4 picture");
			
			ObservablePicture observablePic = new ObservablePictureImpl(red4x4MutablePic);
			
			ROIObserverImpl a = new ROIObserverImpl();
			ROIObserverImpl b = new ROIObserverImpl();
			ROIObserverImpl c = new ROIObserverImpl();
			ROIObserverImpl d = new ROIObserverImpl();
			
			observablePic.registerROIObserver(a, new RegionImpl(0, 0, 1, 1));  // top-left corner
			observablePic.registerROIObserver(b, new RegionImpl(2, 0, 3, 1));  // top-right corner 
			observablePic.registerROIObserver(c, new RegionImpl(0, 2, 1, 3));  // bottom-left corner
			observablePic.registerROIObserver(d, new RegionImpl(2, 2, 3, 3));  // bottom-right corner
			
			assertEquals(a.getTimesNotified(), 0);
			assertEquals(b.getTimesNotified(), 0);
			assertEquals(c.getTimesNotified(), 0);
			assertEquals(d.getTimesNotified(), 0);
			
			observablePic.paint(1, 1, 2, 2, blue);
			
			assertEquals(a.getTimesNotified(), 1);
			assertEquals(b.getTimesNotified(), 1);
			assertEquals(c.getTimesNotified(), 1);
			assertEquals(d.getTimesNotified(), 1);
			
			observablePic.paint(0, 0, blue);
			
			assertEquals(a.getTimesNotified(), 2);		
		}
		
		@Test
		public void testFindObservers() {
			Picture red4x4MutablePic = new MutablePixelArrayPicture(newPixelArray(4, 4, red), "red 4x4 picture");
			
			ObservablePicture observablePic = new ObservablePictureImpl(red4x4MutablePic);
			
			ROIObserverImpl a = new ROIObserverImpl();
			ROIObserverImpl b = new ROIObserverImpl();
			ROIObserverImpl c = new ROIObserverImpl();
			ROIObserverImpl d = new ROIObserverImpl();
			
			observablePic.registerROIObserver(a, new RegionImpl(0, 0, 1, 1));  // top-left corner
			observablePic.registerROIObserver(b, new RegionImpl(2, 0, 3, 1));  // top-right corner 
			observablePic.registerROIObserver(c, new RegionImpl(0, 2, 1, 3));  // bottom-left corner
			observablePic.registerROIObserver(d, new RegionImpl(2, 2, 3, 3));  // bottom-right corner
			
			ROIObserver[] foundObservers = observablePic.findROIObservers(new RegionImpl(0, 0, 0, 0));
			
			assertEquals(foundObservers.length, 1);
			assertEquals(foundObservers[0], a);
			
			ROIObserver[] foundObservers2 = observablePic.findROIObservers(new RegionImpl(0, 0, 3, 0));
			
			assertEquals(foundObservers2.length, 2);
			assertEquals(foundObservers2[0], a);
			assertEquals(foundObservers2[1], b);
		}
		
		@Test
		public void testUnregisterObservers() {
			Picture red4x4ImmutablePic = new ImmutablePixelArrayPicture(newPixelArray(4, 4, red), "red 4x4 picture");
			
			ObservablePicture observablePic = new ObservablePictureImpl(red4x4ImmutablePic);
			
			ROIObserverImpl a = new ROIObserverImpl();
			ROIObserverImpl b = new ROIObserverImpl();
			ROIObserverImpl c = new ROIObserverImpl();
			ROIObserverImpl d = new ROIObserverImpl();
			
			observablePic.registerROIObserver(a, new RegionImpl(0, 0, 1, 1));  // top-left corner
			observablePic.registerROIObserver(b, new RegionImpl(2, 0, 3, 1));  // top-right corner 
			observablePic.registerROIObserver(c, new RegionImpl(0, 2, 1, 3));  // bottom-left corner
			observablePic.registerROIObserver(d, new RegionImpl(2, 2, 3, 3));  // bottom-right corner
			
			ROIObserver[] foundObservers = observablePic.findROIObservers(new RegionImpl(0, 0, 3, 3));
			
			assertEquals(foundObservers[0], a);
			assertEquals(foundObservers[1], b);
			assertEquals(foundObservers[2], c);
			assertEquals(foundObservers[3], d);
			
			observablePic.unregisterROIObservers(new RegionImpl(0, 0, 0, 0));
			
			ROIObserver[] foundObservers3 = observablePic.findROIObservers(new RegionImpl(0, 0, 3, 3));
			System.out.println(foundObservers3.length);
			
			assertEquals(foundObservers3[0], b);
			assertEquals(foundObservers3[1], c);
			assertEquals(foundObservers3[2], d);
			
			observablePic.unregisterROIObserver(c);
			
			ROIObserver[] foundObservers4 = observablePic.findROIObservers(new RegionImpl(0, 0, 3, 3));
			
			assertEquals(foundObservers4[0], b);
			assertEquals(foundObservers4[1], d);		
		}
		
		

		
		
		
		
		static boolean equalRegions(Region r1, Region r2) {
			return (r1.getLeft() == r2.getLeft() && 
					r1.getTop() == r2.getTop() && 
					r1.getBottom() == r2.getBottom() && 
					r1.getRight() == r2.getRight());
		}
		
		private Pixel[][] newPixelArray(int x, int y, Pixel p) {
			Pixel[][] parray = new Pixel[x][y];
			for (int i=0; i<x; i++) {
				for (int j=0; j<y; j++) {
					parray[i][j] = p;
				}
			}
			return parray;
		}
		
		private Pixel[][] newRandomPixelArray(int x, int y) {
			Pixel[][] parray = new Pixel[x][y];
			for (int i=0; i<x; i++) {
				for (int j=0; j<y; j++) {
					parray[i][j] = new ColorPixel(Math.random(), Math.random(), Math.random());
				}
			}
			return parray;
		}
		
		private static boolean check_for_pixel_equality(Pixel a, Pixel b) {
			assertEquals(a.getRed(), b.getRed(), 0.001);
			assertEquals(a.getGreen(), b.getGreen(), 0.001);
			assertEquals(a.getBlue(), b.getBlue(), 0.001);

			return true;
		}
	
}
