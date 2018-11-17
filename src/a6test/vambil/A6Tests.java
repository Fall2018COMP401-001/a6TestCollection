/*
 * AMBIL VERSION
 * 
 * MOST OF THE TESTS HERE ARE FROM OTHER PEOPLE... 
 * I HAVE ADDED MY OWN IN THE BEGINNING
 * THOUGHT IT MIGHT BE EASIER FOR OTHERS TO TEST WITH ALL IN ONE FILE
*/

package a6Tests;

import static org.junit.Assert.*;
import org.junit.Test;
import a6.*;
import java.util.*;

public class A6Tests {

	Pixel red = new ColorPixel(1.0, 0.0, 0.0);
	Pixel green = new ColorPixel(0.0, 1.0, 0.0);
	Pixel blue = new ColorPixel(0.0, 0.0, 1.0);
	Pixel white = a6.Pixel.WHITE;
	Pixel black = a6.Pixel.BLACK;
	Pixel randomColor = new ColorPixel(.123, .456, .789);
	
	Region region11331 = new RegionImpl(1, 1, 3, 3);
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

	Picture random50x50MutablePicture = new MutablePixelArrayPicture(newRandomPixelArray(50, 50), "random 50x50 picture");

	@Test
	public void paintTests() {
		ObservablePicture ob = new ObservablePictureImpl(random50x50MutablePicture);
		
		//Observers
		ROIObserverImpl o1 = new ROIObserverImpl();
		ROIObserverImpl o2 = new ROIObserverImpl();
		ROIObserverImpl o3 = new ROIObserverImpl();
		ROIObserverImpl o4 = new ROIObserverImpl();
		
		//Register dem boiz
		ob.registerROIObserver(o1, new RegionImpl(0,0,5,5));
		ob.registerROIObserver(o2, new RegionImpl(2,2,7,7));
		ob.registerROIObserver(o3, new RegionImpl(7,7,9,9));
		ob.registerROIObserver(o4, new RegionImpl(9,9,9,9));
		
		//paint circle
		ob.paint(2, 2, 4.0, black);
		o1.assertNotified();
		o2.assertNotified();
		o3.assertNotNotified();
		o4.assertNotNotified();
		
		//paint pixel
		ob.paint(7, 7, green);
		o1.assertNotNotified();
		o2.assertNotified();
		o3.assertNotified();
		o4.assertNotNotified();
		
		//paint rectangle
		ob.paint(1, 1, 9, 9, blue);
		o1.assertNotified();
		o2.assertNotified();
		o3.assertNotified();
		o4.assertNotified();		
	}
	
	@Test
	public void constructorRegionTest() {
		try {
			Region a = new RegionImpl(3, 0, 2, 5);
			Region b = new RegionImpl(1, 5, 2, 4);
			fail("IllegalArgumentException should have been thrown ... illegeal geometry D:");
		}
		catch(Exception e) {
		}
	}
	
	@Test
	public void intersectionTests() {
		Region a = new RegionImpl(0, 0, 5, 5);
		Region b = new RegionImpl(5, 5, 10, 10);
		
		try {
			a.intersect(b);
			b.intersect(a);
		}
		catch(Exception e) {
			fail("These two regions should intersect!! Check for <=/>=");
		}
		
		Region c = new RegionImpl(0, 0, 5, 5);
		Region d = new RegionImpl(6, 7, 10, 10);
		
		try {
			c.intersect(d);
			d.intersect(c);
			fail("These two regions should not intersect!!");
		}
		catch(Exception e) { }
	}
	
	@Test
	public void unionTest() {
		Region a = new RegionImpl(0, 0, 5, 5);
		Region b = null;
		
		assertTrue(a.union(b) == a);
		Region c = new RegionImpl(3,3,10,10);
		
		assertTrue(compareRegions(a.union(c),(new RegionImpl(0, 0, 10, 10))));
				
	}
	
	/*
	 * ALL OF THE TESTS BELOW ARE COPIED FROM OTHER PEOPLE!!! 
	 * I PUT IT HERE SO IT MAKES IT EASIER TO TEST ALL AT ONCE
	*/
	
	private boolean compareRegions(Region a, Region b) {
		assertEquals(a.getTop(), b.getTop());
		assertEquals(a.getLeft(), b.getLeft());
		assertEquals(a.getBottom(), b.getBottom());
		assertEquals(a.getRight(), b.getRight());
		
		return true;
	}
	
	@Test
	public void testRegionIntersectAndUnion() {
		//Regional Intersect
		Region r1 = new RegionImpl(0, 0, 5, 5);
		Region r2 = new RegionImpl(2, 2, 6, 6);
		Region r3 = new RegionImpl(1, 2, 3, 4);
		Region r4 = new RegionImpl(3, 3, 5, 4);
		Region r5 = new RegionImpl(0, 0, 10, 10);
		Region r6 = new RegionImpl(3, 3, 4, 7);
		Region r8 = new RegionImpl(6, 6, 8, 8);
		
		try {
			compareRegions(r1.intersect(r2), new RegionImpl(2, 2, 5 ,5));
			compareRegions(r1.intersect(r3), new RegionImpl(1, 2, 3 ,4));
			compareRegions(r5.intersect(r6), r6);
			compareRegions(r6.intersect(r4), new RegionImpl(3, 3, 4 ,4));
		} catch (NoIntersectionException e) {
			fail("");
		}
		
		try {
			r8.intersect(r1);
			fail();
		}catch (NoIntersectionException e) {
			
		}
		//Union
		compareRegions(r1.union(r2), new RegionImpl(0, 0, 6, 6));
		compareRegions(r5.union(r2), r5);
		compareRegions(r8.union(r6), new RegionImpl(3, 3, 8, 8));
	}
	
	@Test
	public void testROIObserver() {
		Random rGen = new Random();
		ColorPixel[][] pixels = new ColorPixel[10][10];
		ColorPixel paintbrush = new ColorPixel(0.5, 0, 0);
		for(int x = 0; x < pixels.length; x++) {
			for(int y = 0; y < pixels[x].length; y++) {
				pixels[x][y] = new ColorPixel(rGen.nextDouble(), rGen.nextDouble(), rGen.nextDouble());
			}
		}
		
		Picture testPic = new MutablePixelArrayPicture(pixels, "test");
		ObservablePicture observablePic = new ObservablePictureImpl(testPic);
		ROIObserverImpl obs1 = new ROIObserverImpl();
		
		observablePic.registerROIObserver(obs1, new RegionImpl(0, 0, 3, 3));
		observablePic.registerROIObserver(obs1, new RegionImpl(7, 7, 9, 9));
		
		//paint outside region, make sure not notified
		observablePic.paint(4, 5, paintbrush);
		obs1.assertNotNotified();
		//paint inside region, make sure notified
		observablePic.paint(6, 6, 7, 7, paintbrush);
		obs1.assertNotified();
		//suspend observable, make sure not notified
		observablePic.suspendObservable();
		observablePic.paint(6, 6, 7, 7, paintbrush);
		obs1.assertNotNotified();
		observablePic.paint(0, 0, 7, 7, paintbrush);
		obs1.assertNotNotified();
		//resume observable, make sure notified
		observablePic.resumeObservable();
		obs1.assertNotified();
		//make sure suspending and resuming does not automatically notify
		observablePic.suspendObservable();
		observablePic.paint(4, 4, paintbrush);
		observablePic.resumeObservable();
		obs1.assertNotNotified();
		//make sure it finds correct observers
		assertEquals(observablePic.findROIObservers(new RegionImpl(0, 0, 1, 1))[0], obs1);
		assertEquals(observablePic.findROIObservers(new RegionImpl(4, 4, 6, 6)).length, 0);
		//make sure it correctly removes observer in area, but keeps other one
		observablePic.unregisterROIObservers(new RegionImpl(3, 3, 5, 6));
		assertEquals(observablePic.findROIObservers(new RegionImpl(0, 0, 1, 1)).length, 0);
		assertEquals(observablePic.findROIObservers(new RegionImpl(4, 4, 8, 8))[0], obs1);

		
	}
	
	public class ROIObserverImpl implements ROIObserver{
		private boolean hasBeenNotified;
		
		public ROIObserverImpl() {
			this.hasBeenNotified = false;
		}
		@Override
		public void notify(ObservablePicture picture, Region changed_region) {
			this.hasBeenNotified = true;
		}
		
		public void assertNotified() {
			assertTrue(hasBeenNotified);
			hasBeenNotified = false;
		}
		
		public void assertNotNotified() {
			assertFalse(hasBeenNotified);
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
		
		// a quick change.
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
	public void noIntersectionRegionTest() {
		Region a = new RegionImpl(0, 0, 1, 1);
		Region b = new RegionImpl(2, 2, 3, 3);
		Region c = null;
		
		try {
			a.intersect(b);
			fail("expected NoIntersectionException to be thrown when regions do not intersect");
		} catch(NoIntersectionException e) {}
		
		try {
			a.intersect(c);
			fail("expected NoIntersectionException to be thrown when called region is null");
		} catch(NoIntersectionException e ) {}
	}
	
	@Test
	public void basicRegionIntersectTest() {
		Region wide_region = new RegionImpl(0, 0, 10, 0);
		Region skinny_region = new RegionImpl(5, 0, 6, 10);
		Region large_square = new RegionImpl(0, 0, 9, 9);
		Region small_square = new RegionImpl(4, 4, 6, 6);
		
		try {
			Region r1 = wide_region.intersect(skinny_region);
			Region r2 = large_square.intersect(small_square);
			
			assertEquals(5, r1.getLeft());
			assertEquals(0, r1.getTop());
			assertEquals(6, r1.getRight());
			assertEquals(0, r1.getBottom());

			assertEquals(4, r2.getLeft());
			assertEquals(4, r2.getTop());
			assertEquals(6, r2.getRight());
			assertEquals(6, r2.getBottom());
		} catch(NoIntersectionException e) {
			fail("expected intersections to be found, but none were");
		}
	}
	
	@Test
	public void regionConstructorTest() {
		try {
			new RegionImpl(1, 0, 0, 0);
			fail("left of region is greater than right, IllegalArgumentException should have been thrown");
		} catch (IllegalArgumentException e) {}
		try {
			new RegionImpl(0, 1, 0, 0);
			fail("top of region is greater than bottom, IllegalArgumentException should have been thrown");
		} catch (IllegalArgumentException e) {}
	}
	

	
	private class ROIObserverImpl2 implements ROIObserver {

		private int timesNotified;
		
		public ROIObserverImpl2() {
			timesNotified = 0;
		}
		
		public void notify(ObservablePicture picture, Region changed_region) {
			timesNotified++;
		}
		
		public int getTimesNotified() {
			return timesNotified;
		}
	}
	
	@Test
	public void testRegionImplConstructor1() {
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
		assertTrue(equalRegions1(region11331.intersect(region2244), region2233));
		assertTrue(equalRegions1(region11331.intersect(region3153), region3133));
		assertTrue(equalRegions1(region11331.intersect(region1335), region1333));
		assertTrue(equalRegions1(region2244.intersect(region11331), region2233));
		
		try {
			region11331.intersect(region4153);
			fail("doesn't intersect");
		} catch (NoIntersectionException e) {
		}
		try {
			region11331.intersect(region1435);
			fail("doesn't intersect");
		} catch (NoIntersectionException e) {
		}
		try {
			region11331.intersect(null);
			fail("doesn't intersect");
		} catch (NoIntersectionException e) {
		}
		
	}
	
	@Test
	public void testRegionUnionMethod() throws NoIntersectionException {
		Region region1234 = new RegionImpl(1, 2, 3, 4);
		Region region5678 = new RegionImpl(5, 6, 7, 8);
		Region region1278 = new RegionImpl(1, 2, 7, 8);
		
		assertTrue(equalRegions1(region1234.union(region5678), region1278));
		
		assertTrue(equalRegions1(region11331.union(region2244), region1144));
		assertTrue(equalRegions1(region11331.union(null), region11331));
		assertTrue(equalRegions1(region11331.union(region3153), region1153));
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
		
		ROIObserverImpl2 a = new ROIObserverImpl2();
		ROIObserverImpl2 b = new ROIObserverImpl2();
		ROIObserverImpl2 c = new ROIObserverImpl2();
		ROIObserverImpl2 d = new ROIObserverImpl2();
		
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
		
		ROIObserverImpl2 a = new ROIObserverImpl2();
		ROIObserverImpl2 b = new ROIObserverImpl2();
		ROIObserverImpl2 c = new ROIObserverImpl2();
		ROIObserverImpl2 d = new ROIObserverImpl2();
		
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
		
		ROIObserverImpl2 a = new ROIObserverImpl2();
		ROIObserverImpl2 b = new ROIObserverImpl2();
		ROIObserverImpl2 c = new ROIObserverImpl2();
		ROIObserverImpl2 d = new ROIObserverImpl2();
		
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
		
		ROIObserverImpl2 a = new ROIObserverImpl2();
		ROIObserverImpl2 b = new ROIObserverImpl2();
		ROIObserverImpl2 c = new ROIObserverImpl2();
		ROIObserverImpl2 d = new ROIObserverImpl2();
		
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
		
		ROIObserverImpl2 a = new ROIObserverImpl2();
		ROIObserverImpl2 b = new ROIObserverImpl2();
		ROIObserverImpl2 c = new ROIObserverImpl2();
		ROIObserverImpl2 d = new ROIObserverImpl2();
		
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
		//System.out.println(foundObservers3.length);
		
		assertEquals(foundObservers3[0], b);
		assertEquals(foundObservers3[1], c);
		assertEquals(foundObservers3[2], d);
		
		observablePic.unregisterROIObserver(c);
		
		ROIObserver[] foundObservers4 = observablePic.findROIObservers(new RegionImpl(0, 0, 3, 3));
		
		assertEquals(foundObservers4[0], b);
		assertEquals(foundObservers4[1], d);		
	}
	
	

	
	
	
	
	public static boolean equalRegions1(Region r1, Region r2) {
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
	
	private boolean compareRegions2(Region a, Region b) {
		assertEquals(a.getTop(), b.getTop());
		assertEquals(a.getLeft(), b.getLeft());
		assertEquals(a.getBottom(), b.getBottom());
		assertEquals(a.getRight(), b.getRight());
		
		return true;
	}
	
	@Test
	public void testRegionIntersectAndUnion2() {
		//Regional Intersect
		Region r1 = new RegionImpl(0, 0, 5, 5);
		Region r2 = new RegionImpl(2, 2, 6, 6);
		Region r3 = new RegionImpl(1, 2, 3, 4);
		Region r4 = new RegionImpl(3, 3, 5, 4);
		Region r5 = new RegionImpl(0, 0, 10, 10);
		Region r6 = new RegionImpl(3, 3, 4, 7);
		Region r8 = new RegionImpl(6, 6, 8, 8);
		
		try {
			compareRegions2(r1.intersect(r2), new RegionImpl(2, 2, 5 ,5));
			compareRegions2(r1.intersect(r3), new RegionImpl(1, 2, 3 ,4));
			compareRegions2(r5.intersect(r6), r6);
			compareRegions2(r6.intersect(r4), new RegionImpl(3, 3, 4 ,4));
		} catch (NoIntersectionException e) {
			fail("");
		}
		
		try {
			r8.intersect(r1);
			fail();
		}catch (NoIntersectionException e) {
			
		}
		//Union
		compareRegions2(r1.union(r2), new RegionImpl(0, 0, 6, 6));
		compareRegions2(r5.union(r2), r5);
		compareRegions2(r8.union(r6), new RegionImpl(3, 3, 8, 8));
	}
	@Test
	public void testROIObserver2() {
		Random rGen = new Random();
		ColorPixel[][] pixels = new ColorPixel[10][10];
		ColorPixel paintbrush = new ColorPixel(0.5, 0, 0);
		for(int x = 0; x < pixels.length; x++) {
			for(int y = 0; y < pixels[x].length; y++) {
				pixels[x][y] = new ColorPixel(rGen.nextDouble(), rGen.nextDouble(), rGen.nextDouble());
			}
		}
		
		Picture testPic = new MutablePixelArrayPicture(pixels, "test");
		ObservablePicture observablePic = new ObservablePictureImpl(testPic);
		ROIObserverImpl obs1 = new ROIObserverImpl();
		
		observablePic.registerROIObserver(obs1, new RegionImpl(0, 0, 3, 3));
		observablePic.registerROIObserver(obs1, new RegionImpl(7, 7, 9, 9));
		
		//paint outside region, make sure not notified
		observablePic.paint(4, 5, paintbrush);
		obs1.assertNotNotified();
		//paint inside region, make sure notified
		observablePic.paint(6, 6, 7, 7, paintbrush);
		obs1.assertNotified();
		//suspend observable, make sure not notified
		observablePic.suspendObservable();
		observablePic.paint(6, 6, 7, 7, paintbrush);
		obs1.assertNotNotified();
		observablePic.paint(0, 0, 7, 7, paintbrush);
		obs1.assertNotNotified();
		//resume observable, make sure notified
		observablePic.resumeObservable();
		obs1.assertNotified();
		//make sure suspending and resuming does not automatically notify
		observablePic.suspendObservable();
		observablePic.paint(4, 4, paintbrush);
		observablePic.resumeObservable();
		obs1.assertNotNotified();
		//make sure it finds correct observers
		assertEquals(observablePic.findROIObservers(new RegionImpl(0, 0, 1, 1))[0], obs1);
		assertEquals(observablePic.findROIObservers(new RegionImpl(4, 4, 6, 6)).length, 0);
		//make sure it correctly removes observer in area, but keeps other one
		observablePic.unregisterROIObservers(new RegionImpl(3, 3, 5, 6));
		assertEquals(observablePic.findROIObservers(new RegionImpl(0, 0, 1, 1)).length, 0);
		assertEquals(observablePic.findROIObservers(new RegionImpl(4, 4, 8, 8))[0], obs1);	
	}
	@Test
	public void nullRegionIntersectTest() {
		Region first = new RegionImpl(1,1,7,7);
		Region second = null;

		try {
			first.intersect(second);
			fail("Intersect parameter cannot be null");
		} catch (NoIntersectionException e) {
		}

	}

	@Test
	public void invalidIntersectTest() {
		Region first;
		Region second;

		try {
			first = new RegionImpl(1,1,3,7);
			second = new RegionImpl(5,1,7,7);
			first.intersect(second);
			fail("No intersection of these two regions");
		} catch (NoIntersectionException e) {
		}

		try {	
			first = new RegionImpl(5,1,5,7);
			second = new RegionImpl(1,1,3,7);
			first.intersect(second);
			fail("No intersection of these two regions");
		} catch (NoIntersectionException e) {
		}
		try {	
			first = new RegionImpl(5,1,5,1);
			second = new RegionImpl(1,7,3,7);
			first.intersect(second);
			fail("No intersection of these two regions");
		} catch (NoIntersectionException e) {
		}
		try {	
			first = new RegionImpl(5,7,5,7);
			second = new RegionImpl(1,1,3,4);
			first.intersect(second);
			fail("No intersection of these two regions");
		} catch (NoIntersectionException e) {
		}

	}

	@Test
	public void gettersTest() {
		Region test = new RegionImpl(3,5,3,5);

		assertEquals(test.getBottom(), 5);
		assertEquals(test.getTop(), 5);
		assertEquals(test.getRight(), 3);
		assertEquals(test.getLeft(), 3);

	}

	@Test
	public void regionImplConstructorTest() {

		try {		
			Region test = new RegionImpl(5,7,3,7);
		} catch (IllegalArgumentException e) {
		}
		try {		
			Region test = new RegionImpl(5,6,5,3);
		} catch (IllegalArgumentException e) {
		}


	}


	// Initialize Pixel 2d Arrays
	Pixel[][] rgbPicture = { { red, red, red }, { green, green, green }, { blue, blue, blue } };
	Pixel[][] greenPicture = { { green, green, green }, { green, green, green }, { green, green, green } };
	Pixel[][] bluePicture = { { blue, blue, blue }, { blue, blue, blue }, { blue, blue, blue } };

	// Initialize Mutable Pixel Array Picture
	Picture rgbMuPic = new MutablePixelArrayPicture(rgbPicture, "Mutable RGB");
	Picture greenMuPic = new MutablePixelArrayPicture(greenPicture, "Mutable green");
	Picture blueMuPic = new MutablePixelArrayPicture(bluePicture, "Mutable blue");
	
	// Initialize Immutable Pixel Array Picture
	Picture rgbImPic = new ImmutablePixelArrayPicture(rgbPicture, "Immutable RGB");
	Picture greenImPic = new ImmutablePixelArrayPicture(greenPicture, "Immutable green");
	Picture blueImPic = new ImmutablePixelArrayPicture(bluePicture, "Immutable blue");
	
	// Initialize region
	Region a = new RegionImpl(1,1,1,1);
	Region b = new RegionImpl(-5,-5,-1,-1);
	Region c = new RegionImpl(0,0,2,2);
	Region d = new RegionImpl(0,0,1,1);
	Region e = new RegionImpl(2,2,2,2);

	// Initialize observable picture
	ObservablePicture rgbMuOb = new ObservablePictureImpl(rgbMuPic);
	ObservablePicture greenMuOb = new ObservablePictureImpl(greenMuPic);
	ObservablePicture blueMuOb = new ObservablePictureImpl(blueMuPic);

	ObservablePicture rgbImOb = new ObservablePictureImpl(rgbImPic);
	ObservablePicture greenImOb = new ObservablePictureImpl(greenImPic);
	ObservablePicture blueImOb = new ObservablePictureImpl(blueImPic);
	
	// Initialize observer
	ROIObserver o1 = new ROIObserverImpl3();
	ROIObserver o2 = new ROIObserverImpl3();
	ROIObserver o3 = new ROIObserverImpl3();
	ROIObserver o4 = new ROIObserverImpl3();
	
	@Test
	public void regionConstructorTest2() {

		// test when left equals right, top equals bottom
		assertEquals(a.getLeft(), 1);
		assertEquals(a.getTop(), 1);
		assertEquals(a.getRight(), 1);
		assertEquals(a.getBottom(), 1);
		
		// test negative values of four sides
		assertEquals(b.getLeft(), -5);
		assertEquals(b.getTop(), -5);
		assertEquals(b.getRight(), -1);
		assertEquals(b.getBottom(), -1);
		
	}
	
	@Test
	public void noIntersectionTest() {
		try {
			a.intersect(b);
		} catch (NoIntersectionException e) {
		}
	}
	
	@Test
	public void intersectionTest() throws NoIntersectionException {
		Region temp = a.intersect(c);
		assertEquals(temp.getLeft(), 1);
		assertEquals(temp.getTop(), 1);
		assertEquals(temp.getRight(), 1);
		assertEquals(temp.getBottom(), 1);
	}
	
	@Test
	public void mutableObservablePicturePaint() {
		Picture greenMuOb1 = new ObservablePictureImpl(greenMuPic);
		greenMuOb1.paint(0, 0, red);
		check_for_component_equality(greenMuOb1.getPixel(0, 0), red);
		greenMuOb1.paint(0, 0, 2, 2, blue);
		check_for_picture_equality(greenMuOb1,blueMuOb);
	}
	
	@Test
	public void immutableObservablePicturePaint() {
		Picture greenImOb2 = new ObservablePictureImpl(greenImPic);
		greenImOb2.paint(0, 0, red);
		check_for_component_equality(greenImOb2.getPixel(0, 0), red);
		greenImOb2.paint(0, 0, 2, 2, blue);
		check_for_picture_equality(greenImOb2,blueMuOb);
	}
	
	public class ROIObserverImpl3 implements ROIObserver {

		private int count;
		
		public ROIObserverImpl3() {
			count = 0;
		}
		
		@Override
		public void notify(ObservablePicture picture, Region changed_region) {
			count++;
		}
		
		public int getCount() {
			return this.count;
		}
	}
	
	@Test
	public void notifyTest() {
		rgbMuOb.registerROIObserver(o1, d);
		assertEquals(((ROIObserverImpl3) o1).getCount(), 0);
		rgbMuOb.paint(0, 0, randomColor);
		assertEquals(((ROIObserverImpl3) o1).getCount(), 1);
		rgbMuOb.paint(1, 1, randomColor);
		assertEquals(((ROIObserverImpl3) o1).getCount(), 2);
		rgbMuOb.paint(2, 2, randomColor);
		assertEquals(((ROIObserverImpl3) o1).getCount(), 2);
	}
	
	@Test
	public void suspendResumeTest() {
		blueMuOb.registerROIObserver(o2, d);
		blueMuOb.suspendObservable();
		assertEquals(((ROIObserverImpl3) o2).getCount(), 0);
		blueMuOb.paint(0, 0, randomColor);
		assertEquals(((ROIObserverImpl3) o2).getCount(), 0);
		blueMuOb.paint(1, 1, randomColor);
		assertEquals(((ROIObserverImpl3) o2).getCount(), 0);
		blueMuOb.resumeObservable();
		assertEquals(((ROIObserverImpl3) o2).getCount(), 1);
	}
	
	@Test
	public void registerAndUnregisterTest() {
		greenMuOb.registerROIObserver(o3, a);
		greenMuOb.registerROIObserver(o3, c);
		greenMuOb.registerROIObserver(o3, d);
		greenMuOb.registerROIObserver(o4, a);
		greenMuOb.registerROIObserver(o4, c);
		greenMuOb.registerROIObserver(o4, d);
		assertEquals(greenMuOb.findROIObservers(a).length, 6);
		assertEquals(greenMuOb.findROIObservers(e).length, 2);
		greenMuOb.unregisterROIObservers(e);
		assertEquals(greenMuOb.findROIObservers(e).length, 0);
		greenMuOb.unregisterROIObserver(o3);
		assertEquals(greenMuOb.findROIObservers(a).length, 2);
	}
	
	
	private static boolean check_for_component_equality(Pixel a, Pixel b) {
		assertEquals(a.getRed(), b.getRed(), 0.001);
		assertEquals(a.getGreen(), b.getGreen(), 0.001);
		assertEquals(a.getBlue(), b.getBlue(), 0.001);

		return true;
	}
	
	private static boolean check_for_picture_equality(Picture a, Picture b) {
		if(a.getWidth() != b.getWidth() || a.getHeight() != b.getHeight()) {
			throw new IllegalArgumentException("cannot compare");
		}
		for(int i=0;i<a.getWidth();i++) {
			for(int j=0; j<a.getHeight(); j++) {
				Pixel a1 = a.getPixel(i, j);
				Pixel b1 = b.getPixel(i, j);
				check_for_component_equality(a1, b1);
			}
		}
		return true;
	}

	Pixel[][] redArray = { { red, red, red }, { red, red, red }, { red, red, red } };

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
	
	@Test
	public void nullRegionIntersectionTest() throws NoIntersectionException {
		Region a = new RegionImpl(0, 0, 2, 2);
		try {
			Region intersect = a.intersect(null);
			
			throw new NoIntersectionException();
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void basicRegionIntersectionTest() throws NoIntersectionException {
		Region a = new RegionImpl(0, 0, 5, 5);
		Region b = new RegionImpl(2, 2, 8, 8);
		
		Region intersect = a.intersect(b);
		
		assertEquals(2, intersect.getLeft());
		assertEquals(2, intersect.getTop());
		assertEquals(5, intersect.getRight());
		assertEquals(5, intersect.getBottom());
		
		// Switched
		
		intersect = b.intersect(a);
		
		assertEquals(2, intersect.getLeft());
		assertEquals(2, intersect.getTop());
		assertEquals(5, intersect.getRight());
		assertEquals(5, intersect.getBottom());
	}

	@Test
	public void basicIntersectionTest() throws NoIntersectionException {
		Region a = new RegionImpl(0, 0, 5, 5);
		Region b = new RegionImpl(4, 4, 8, 8);
		
		Region intersect = a.intersect(b);
		
		assertEquals(4, intersect.getLeft());
		assertEquals(4, intersect.getTop());
		assertEquals(5, intersect.getRight());
		assertEquals(5, intersect.getBottom());
		
		intersect = b.intersect(a);
		
		assertEquals(4, intersect.getLeft());
		assertEquals(4, intersect.getTop());
		assertEquals(5, intersect.getRight());
		assertEquals(5, intersect.getBottom());
		
	}
	
	@Test
	public void testRegionConstructor() {
		Region reg;
		Region other;
		Region another;
		
		try {
			reg = new RegionImpl(5, 2, 3, 3);
			other = new RegionImpl(1, 5, 3, 3);
		}
		catch (IllegalArgumentException e) {
			
		}
		
	}
	
	Picture blueMutable4by4 = new MutablePixelArrayPicture(new Pixel[][] { { blue, blue, blue, blue },
			{ blue, blue, blue, blue }, { blue, blue, blue, blue }, { blue, blue, blue, blue } }, "Blue");
	Picture blueImmutable4by4 = new ImmutablePixelArrayPicture(new Pixel[][] { { blue, blue, blue, blue },
			{ blue, blue, blue, blue }, { blue, blue, blue, blue }, { blue, blue, blue, blue } }, "Blue");

	Region region0000 = new RegionImpl(0, 0, 0, 0);
	Region region2133 = new RegionImpl(2, 1, 3, 3);
	Region region1122 = new RegionImpl(1, 1, 2, 2);
	Region region2122 = new RegionImpl(2, 1, 2, 2);
	Region region2222 = new RegionImpl(2, 2, 2, 2);
	Region region2121 = new RegionImpl(2, 1, 2, 1);
	Region region1133 = new RegionImpl(1, 1, 3, 3);
	Region region3031 = new RegionImpl(3, 0, 3, 1);
	Region region3131 = new RegionImpl(3, 1, 3, 1);
	Region region1111 = new RegionImpl(1, 1, 1, 1);
	Region region3333 = new RegionImpl(3, 3, 3, 3);
	Region region0033 = new RegionImpl(0, 0, 3, 3);

	ObservablePicture observableBlueImmutable3by3 = new ObservablePictureImpl(blueImmutable4by4);

	ROIObserver observer1 = new ROIObserverImpl();
	ROIObserver observer2 = new ROIObserverImpl();
	ROIObserver observer3 = new ROIObserverImpl();

	@Test
	public void testRegionImplConstructor() {
		try {
			new RegionImpl(2, 1, 1, 2);
			fail("Left cannot be greater than right");
		} catch (IllegalArgumentException e) {
		}
		try {
			new RegionImpl(1, 2, 2, 1);
			fail("Top cannot be greater than bottom");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testRegionIntersection() throws NoIntersectionException {
		assert (equalRegions1(region2122.intersect(region2133), region2122));
		try {
			region1122.intersect(null);
			fail("cannot intersect null");
		} catch (NoIntersectionException e) {
		}
	}

	@Test
	public void testRegionUnion() {
		assert (equalRegions1(region3333.union(region1111), region11331));
		assert (equalRegions1(region11331.union(null), region11331));
	}

	@Test
	public void testObservablePictureImplConstructor() {
		try {
			new ObservablePictureImpl(null);
			fail("Picture cannot be null.");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testObservablePictureImplGetters() {
		ObservablePicture p = new ObservablePictureImpl(blueMutable4by4);
		assertEquals(p.getCaption(), blueMutable4by4.getCaption());
		assertEquals(p.getHeight(), blueMutable4by4.getHeight());
		assertEquals(p.getWidth(), blueMutable4by4.getWidth());
		for (int i = 0; i < p.getWidth(); i++) {
			for (int j = 0; j < p.getHeight(); j++) {
				assertEquals(p.getPixel(i, j), blueMutable4by4.getPixel(i, j));
			}
		}
	}

	@Test
	public void testSimpleROIObservableMethods() {
		ObservablePicture observableBlueMutable4by4 = new ObservablePictureImpl(blueMutable4by4);
		ROIObserver[] observers;

		try {
			observableBlueMutable4by4.registerROIObserver(null, region0000);
			fail("Cannot register null observer");
		} catch (Exception e) {
		}

		try {
			observableBlueMutable4by4.registerROIObserver(observer1, null);
			fail("Cannot register null region");
		} catch (Exception e) {
		}

		// Assignment doesn't define what should happen with null
		// arguments for these methods, but kmp solution throws
		// IllegalArgumentException and would expect student solutions
		// to do the same. Not going to be tested by autograder, so
		// just commenting these tests out.

		// observableBlueMutable4by4.unregisterROIObserver(null);
		// observers = observableBlueMutable4by4.findROIObservers(null);
		// observableBlueMutable4by4.unregisterROIObservers(null);

		observableBlueMutable4by4.registerROIObserver(observer1, region0000);
		observableBlueMutable4by4.registerROIObserver(observer1, region3333);
		observableBlueMutable4by4.registerROIObserver(observer2, region2122);
		observableBlueMutable4by4.registerROIObserver(observer3, region3031);

		observers = observableBlueMutable4by4.findROIObservers(region0033);

		assertEquals(4, observers.length);
		assert (checkObservers(observers, observer1));
		assert (checkObservers(observers, observer1));
		assert (checkObservers(observers, observer2));
		assert (checkObservers(observers, observer3));

		observableBlueMutable4by4.registerROIObserver(observer1, region0000);

		observers = observableBlueMutable4by4.findROIObservers(region0033);

		assertEquals(5, observers.length);
		assert (checkObservers(observers, observer1));
		assert (checkObservers(observers, observer1));
		assert (checkObservers(observers, observer1));
		assert (checkObservers(observers, observer2));
		assert (checkObservers(observers, observer3));

		observableBlueMutable4by4.unregisterROIObserver(observer1);

		observers = observableBlueMutable4by4.findROIObservers(region0033);

		assertEquals(2, observers.length);
		assert (checkObservers(observers, observer2));
		assert (checkObservers(observers, observer3));

		observableBlueMutable4by4.registerROIObserver(observer1, region0000);
		observableBlueMutable4by4.registerROIObserver(observer1, region3333);

		observers = observableBlueMutable4by4.findROIObservers(region0033);

		assertEquals(4, observers.length);
		assert (checkObservers(observers, observer1));
		assert (checkObservers(observers, observer1));
		assert (checkObservers(observers, observer2));
		assert (checkObservers(observers, observer3));

		observableBlueMutable4by4.unregisterROIObservers(region3333);

		observers = observableBlueMutable4by4.findROIObservers(region0033);

		assertEquals(3, observers.length);
		assert (checkObservers(observers, observer1));
		assert (checkObservers(observers, observer2));
		assert (checkObservers(observers, observer3));

		observableBlueMutable4by4.unregisterROIObservers(region2121);

		observers = observableBlueMutable4by4.findROIObservers(region0033);

		assertEquals(2, observers.length);
		assert (checkObservers(observers, observer1));
		assert (checkObservers(observers, observer3));
	}

	@Test
	public void testDifferentPaintNotifyObservers() {
		ObservablePicture observableBlueMutable4by4 = new ObservablePictureImpl(blueMutable4by4);

		ObserverHelper ob1 = new ObserverHelperImpl(observer1);
		ObserverHelper ob2 = new ObserverHelperImpl(observer2);
		ObserverHelper ob3 = new ObserverHelperImpl(observer3);

		observableBlueMutable4by4.registerROIObserver(ob1, region0000);
		observableBlueMutable4by4.registerROIObserver(ob1, region3333);
		observableBlueMutable4by4.registerROIObserver(ob2, region2122);
		observableBlueMutable4by4.registerROIObserver(ob3, region3031);

		observableBlueMutable4by4.paint(0, 0, red);
		assertEquals(1, ob1.getCount());
		assertEquals(0, ob2.getCount());
		assertEquals(0, ob3.getCount());
		assert (equalRegions1(ob1.getNotifiedRegion().get(0), region0000));

		observableBlueMutable4by4.paint(0, 0, observableBlueImmutable3by3);
		assertEquals(3, ob1.getCount());
		assertEquals(1, ob2.getCount());
		assertEquals(1, ob3.getCount());
		assert (equalRegions1(ob1.getNotifiedRegion().get(0), region0000));
		assert (equalRegions1(ob1.getNotifiedRegion().get(1), region0000));
		assert (equalRegions1(ob1.getNotifiedRegion().get(2), region3333));
		assert (equalRegions1(ob2.getNotifiedRegion().get(0), region2122));
		assert (equalRegions1(ob3.getNotifiedRegion().get(0), region3031));

		observableBlueMutable4by4.paint(1, 1, red);
		assertEquals(3, ob1.getCount());
		assertEquals(1, ob2.getCount());
		assertEquals(1, ob3.getCount());
		assert (equalRegions1(ob1.getNotifiedRegion().get(0), region0000));
		assert (equalRegions1(ob1.getNotifiedRegion().get(1), region0000));
		assert (equalRegions1(ob1.getNotifiedRegion().get(2), region3333));
		assert (equalRegions1(ob2.getNotifiedRegion().get(0), region2122));
		assert (equalRegions1(ob3.getNotifiedRegion().get(0), region3031));

		observableBlueMutable4by4.unregisterROIObservers(region3333);
		observableBlueMutable4by4.paint(3, 3, 2, red);
		assertEquals(3, ob1.getCount());
		assertEquals(2, ob2.getCount());
		assertEquals(2, ob3.getCount());
		assert (equalRegions1(ob1.getNotifiedRegion().get(0), region0000));
		assert (equalRegions1(ob1.getNotifiedRegion().get(1), region0000));
		assert (equalRegions1(ob1.getNotifiedRegion().get(2), region3333));
		assert (equalRegions1(ob2.getNotifiedRegion().get(0), region2122));
		assert (equalRegions1(ob2.getNotifiedRegion().get(1), region2122));
		assert (equalRegions1(ob3.getNotifiedRegion().get(0), region3031));
		assert (equalRegions1(ob3.getNotifiedRegion().get(1), region3131));
	}

	@Test
	public void testSuspendAndResume() {
		ObservablePicture observableBlueMutable4by4 = new ObservablePictureImpl(blueMutable4by4);

		ObserverHelper ob1 = new ObserverHelperImpl(observer1);
		ObserverHelper ob2 = new ObserverHelperImpl(observer2);
		ObserverHelper ob3 = new ObserverHelperImpl(observer3);

		observableBlueMutable4by4.registerROIObserver(ob1, region0000);
		observableBlueMutable4by4.registerROIObserver(ob1, region3333);
		observableBlueMutable4by4.registerROIObserver(ob2, region2122);
		observableBlueMutable4by4.registerROIObserver(ob3, region3031);

		observableBlueMutable4by4.suspendObservable();

		observableBlueMutable4by4.paint(0, 0, red);
		observableBlueMutable4by4.paint(1, 1, red);
		assertEquals(0, ob1.getCount());
		assertEquals(0, ob2.getCount());
		assertEquals(0, ob3.getCount());

		observableBlueMutable4by4.resumeObservable();
		assertEquals(1, ob1.getCount());
		assertEquals(0, ob2.getCount());
		assertEquals(0, ob3.getCount());
		assert (equalRegions1(ob1.getNotifiedRegion().get(0), region0000));

		observableBlueMutable4by4.suspendObservable();
		observableBlueMutable4by4.suspendObservable();

		observableBlueMutable4by4.paint(0, 0, red);
		observableBlueMutable4by4.paint(2, 0, red);
		observableBlueMutable4by4.paint(0, 3, red);

		observableBlueMutable4by4.resumeObservable();
		assertEquals(2, ob1.getCount());
		assertEquals(1, ob2.getCount());
		assertEquals(0, ob3.getCount());
		assert (equalRegions1(ob1.getNotifiedRegion().get(0), region0000));
		assert (equalRegions1(ob1.getNotifiedRegion().get(1), region0000));
		assert (equalRegions1(ob2.getNotifiedRegion().get(0), region2122));

		observableBlueMutable4by4.resumeObservable();
		assertEquals(2, ob1.getCount());
		assertEquals(1, ob2.getCount());
		assertEquals(0, ob3.getCount());
		assert (equalRegions1(ob1.getNotifiedRegion().get(0), region0000));
		assert (equalRegions1(ob1.getNotifiedRegion().get(1), region0000));
		assert (equalRegions1(ob2.getNotifiedRegion().get(0), region2122));
	}

	private static boolean equalRegions(Region r1, Region r2) {
		return (r1.getLeft() == r2.getLeft() && r1.getTop() == r2.getTop() && r1.getBottom() == r2.getBottom()
				&& r1.getRight() == r2.getRight());
	}

	private static boolean checkObservers(ROIObserver[] observers, ROIObserver observer) {
		for (int i = 0; i < observers.length; i++) {
			if (observers[i] == observer) {
				observers[i] = null;
				return true;
			}
		}
		return false;
	}
	
	public interface ObserverHelper extends ROIObserver {

		public int getCount();
		
		public List<Region> getNotifiedRegion();
		
	}
	public class ObserverHelperImpl implements ObserverHelper {
		
		private ROIObserver _o;
		private List<Region> NotifiedRegions;
		
		public ObserverHelperImpl(ROIObserver o) {
			_o = o;
			NotifiedRegions = new ArrayList<Region>();
		}
		
		@Override
		public void notify(ObservablePicture picture, Region changed_region) {
			_o.notify(picture, changed_region);
			NotifiedRegions.add(changed_region);
		}

		@Override
		public int getCount() {
			return NotifiedRegions.size();
		}

		@Override
		public List<Region> getNotifiedRegion() {
			return NotifiedRegions;
		}

	}
	@Test
	public void testRegionImplConstructor2() {
		try {
			Region a = new RegionImpl(5,1,1,2);
			fail("Left should not be greater than right");
		} catch (IllegalArgumentException e) {
		}
		try {
			Region b = new RegionImpl(1,5,2,1);
			fail("Top should not be greater than bottom");
		} catch (IllegalArgumentException e) {
		}
	}
	
	@Test
	public void testUnion() {
		Region a = new RegionImpl(1,2,3,5);
		Region b = new RegionImpl(2,4,7,6);
		Region union = a.union(b);
		assertEquals(1, union.getLeft());
		assertEquals(2, union.getTop());
		assertEquals(7, union.getRight());
		assertEquals(6, union.getBottom());
	}
	
	@Test
	public void testIntersection() throws NoIntersectionException {
		Region a = new RegionImpl(1,2,3,5);
		Region b = new RegionImpl(2,4,7,6);
		Region intersec = a.intersect(b);
		assertEquals(2,intersec.getLeft());
		assertEquals(4,intersec.getTop());
		assertEquals(3,intersec.getRight());
		assertEquals(5,intersec.getBottom());
	}
	
	@Test
	public void testNullIntersection() {
		Region a = new RegionImpl(1,2,3,5);
		Region b = null;
		try {
			Region intersec = a.intersect(b);
			fail("illegal intersection");
		} catch (NoIntersectionException e) {
		}
	}
	
	@Test
	public void testNoIntersection() {
		Region a = new RegionImpl(1,2,3,5);
		Region b = new RegionImpl(4,2,5,7);
		try {
			Region intersec = a.intersect(b);
			fail("illegal intersection");
		} catch (NoIntersectionException e) {
		}
	}
	
	public class ROIObserverImplT implements ROIObserver {

		private int timesNotified;
		
		
		public void notify(ObservablePicture p, Region changed_region) {
			++timesNotified;
		}
		
		public ROIObserverImplT() {
			this.timesNotified = timesNotified;
		}
		
		public int TimesNotified() {
			return timesNotified;
		}
	}
	
	@Test
	public void testRegionImplConstructor7() {
		try {
			new RegionImpl(3, 2, 2, 3);
			fail("Left cannot be greater than right");
		} catch (IllegalArgumentException e) {
		}
		try {
			new RegionImpl(5, 6, 6, 5);
			fail("Top cannot be greater than bottom");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testRegisterROIObserver() {
		ROIObserver o1 = new ROIObserverImplT(), o2 = new ROIObserverImplT(), o3 = new ROIObserverImplT(),
				o4 = new ROIObserverImplT();
		Region reg1 = new RegionImpl(0, 0, 2, 3), reg2 = new RegionImpl(2, 2, 5, 5), reg3 = new RegionImpl(1, 2, 2, 3);
		ObservablePicture pic = new ObservablePictureImpl(NewMutablePictureWithCaption(767, 362, "test"));

		pic.registerROIObserver(o1, reg1);
		pic.registerROIObserver(o2, reg1);
		pic.registerROIObserver(o1, reg1);
		pic.registerROIObserver(o2, reg2);
		pic.registerROIObserver(o1, reg3);
		pic.registerROIObserver(o3, reg1);

		ROIObserver[] ROIOarray = pic.findROIObservers(reg1);

/*		assertEquals(ROIOarray[0], o1);
		assertEquals(ROIOarray[1], o2);
		assertEquals(ROIOarray[2], o1);
		assertEquals(ROIOarray[3], o2);
		assertEquals(ROIOarray[4], o1);
		assertEquals(ROIOarray[5], o3);*/
/*
		pic.registerROIObserver(o4, reg1);
		pic.registerROIObserver(o3, reg2);

		ROIObserver[] rarrayAdded = pic.findROIObservers(reg1);

		assertEquals(rarrayAdded[6], o4);
		assertEquals(rarrayAdded[7], o3);*/

	}

	@Test
	public void testNullRegionIntersection() {
		Region reg1 = new RegionImpl(10, 10, 20, 20), reg2 = null;
		try {
			reg1.intersect(reg2);
			fail("Intersection cannot be null");
		} catch (NoIntersectionException e) {
		}

	}

	@Test
	public void testNotify() {
		Picture pic = new MutablePixelArrayPicture(MutablePictureArray(10, 10), "test");

		ObservablePicture obspic = new ObservablePictureImpl(pic);

		ROIObserverImplT a = new ROIObserverImplT(), b = new ROIObserverImplT(), c = new ROIObserverImplT(),
				d = new ROIObserverImplT();
		obspic.registerROIObserver(a, new RegionImpl(0, 0, 1, 1));
		obspic.registerROIObserver(b, new RegionImpl(2, 0, 3, 1));
		obspic.registerROIObserver(c, new RegionImpl(0, 2, 1, 3));
		obspic.registerROIObserver(d, new RegionImpl(2, 2, 3, 3));

		assertEquals(a.TimesNotified(), 0);
		assertEquals(b.TimesNotified(), 0);
		assertEquals(c.TimesNotified(), 0);
		assertEquals(d.TimesNotified(), 0);

		obspic.paint(0, 0, NewPixel());

		assertEquals(a.TimesNotified(), 1);
		assertEquals(b.TimesNotified(), 0);
		assertEquals(c.TimesNotified(), 0);
		assertEquals(d.TimesNotified(), 0);
	}

	@Test
	public void testRegionUnionMethod3() {
		Region reg1 = new RegionImpl(0, 0, 10, 10), reg2 = new RegionImpl(12, 12, 18, 18), union = reg1.union(reg2);
		assertEquals(0, union.getLeft());
		assertEquals(0, union.getTop());
		assertEquals(18, union.getRight());
		assertEquals(18, union.getBottom());

		union = reg2.union(reg1);

		assertEquals(0, union.getLeft());
		assertEquals(0, union.getTop());
		assertEquals(18, union.getRight());
		assertEquals(18, union.getBottom());

	}
	

	public static Pixel NewPixel() {
		return new ColorPixel(Math.random(), Math.random(), Math.random());
	}

	public static Pixel[][] MutablePictureArray(int x, int y) {
		Pixel[][] pixel_array = new Pixel[x][y];
		for (int i = 0; i < x; ++i)
			for (int j = 0; j < y; ++j)
				pixel_array[i][j] = NewPixel();
		return pixel_array;
	}

	public static Picture NewMutablePictureWithCaption(int x, int y, String caption) {
		return new MutablePixelArrayPicture(MutablePictureArray(x, y), caption);
	}
	
	Picture blueMutable3by3 = new MutablePixelArrayPicture(new Pixel[][] {{blue,blue,blue},{blue,blue,blue},{blue,blue,blue}}, "Blue");
	Picture blueImmutable3by3 = new ImmutablePixelArrayPicture(new Pixel[][] {{blue,blue,blue},{blue,blue,blue},{blue,blue,blue}}, "Blue");
	
	Region region21331 = new RegionImpl(2,1,3,3);
	Region region11221 = new RegionImpl(1,1,2,2);
	Region region21221 = new RegionImpl(2,1,2,2);
	Region region113311 = new RegionImpl(1,1,3,3);
	Region region30311 = new RegionImpl(3,0,3,1);
	
	ObservablePicture observableBlueMutable3by3 = new ObservablePictureImpl(blueMutable3by3);
	ObservablePicture observableBlueImmutable3by31 = new ObservablePictureImpl(blueImmutable3by3);
	
	ROIObserver observer11 = new ROIObserverImpl();
	ROIObserver observer21 = new ROIObserverImpl();
	ROIObserver observer31 = new ROIObserverImpl();
	
	@Test
	public void testRegionImplConstructor3() {
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
	public void testRegionImplMethods() throws NoIntersectionException {
		assertEquals(2, region2133.getLeft());
		assertEquals(1, region2133.getTop());
		assertEquals(3, region2133.getRight());
		assertEquals(3, region2133.getBottom());
		assertEquals(true, equalRegions(region1122.intersect(region2133), region2122));
		assertEquals(true, equalRegions(region1122.union(region2133), region1133));
	}
	
	@Test
	public void testObservablePictureImplConstructor4() {
		try {
			Picture p = new ObservablePictureImpl(null);
			fail("Picture cannot be null.");
		} catch (IllegalArgumentException e) {
		}
	}
	
	@Test
	public void testObservablePictureImplPaintMethods() {
		assertEquals(blue, observableBlueImmutable3by3.getPixel(1, 2));
		Picture p = observableBlueImmutable3by3.paint(1, 2, red);
		assertEquals(true, equalPixels(red, p.getPixel(1, 2)));
		assertEquals(true, equalPixels(red, observableBlueImmutable3by3.getPixel(1, 2)));
		assertEquals(true, equalPixels(blue, blueImmutable3by3.getPixel(1, 2)));
		
		p = observableBlueImmutable3by3.paint(1, 0, 2, 2, red);
		for (int y = 0; y <= 2; y++) {
			for (int x = 1; x <= 2; x++) {
				assertEquals(true, equalPixels(red, p.getPixel(x, y)));
			}
			assertEquals(true, equalPixels(blue, p.getPixel(0, y)));
		}
	}
	
	@Test
	public void testROIObservableRegisterAndFindObserverMethods() {
		observableBlueMutable3by3.registerROIObserver(observer1, region1133);
		observableBlueMutable3by3.registerROIObserver(observer1, region3031);
		observableBlueMutable3by3.registerROIObserver(observer2, region2122);
		observableBlueMutable3by3.registerROIObserver(observer3, region3031);
		
		assertEquals(2, observableBlueMutable3by3.findROIObservers(region1122).length);
		assertEquals(observer1, observableBlueMutable3by3.findROIObservers(region1122)[0]);
		assertEquals(observer2, observableBlueMutable3by3.findROIObservers(region1122)[1]);
	}
	
	public static boolean equalRegions3(Region r1, Region r2) {
		return (r1.getLeft() == r2.getLeft() && r1.getTop() == r2.getTop() && r1.getBottom() == r2.getBottom() && r1.getRight() == r2.getRight());
	}

	public static boolean equalPixels(Pixel p1, Pixel p2) {
		return (p1.getRed() == p2.getRed() && p2.getGreen() == p2.getGreen() && p1.getBlue() == p2.getBlue());
	}

	public interface ObserverNotify extends ROIObserver {

		public int getCount();

		public List<Region> NotifiedRegion();

	}

	public class ObserverNotifyImpl implements ObserverNotify {

		private ROIObserver o;
		private List<Region> NotifiedRegion;

		public ObserverNotifyImpl(ROIObserver o) {
			this.o = o;
			this.NotifiedRegion = new ArrayList<Region>();
		}

		@Override
		public void notify(ObservablePicture picture, Region changed_region) {
			o.notify(picture, changed_region);
			NotifiedRegion.add(changed_region);
		}

		@Override
		public int getCount() {
			return NotifiedRegion.size();
		}

		@Override
		public List<Region> NotifiedRegion() {
			return NotifiedRegion;
		}

	}

	Pixel[][] redPicture1 = { { red, red, red, red }, { red, red, red, red }, { red, red, red, red },
			{ red, red, red, red } };
	Picture redMutablePicture = new MutablePixelArrayPicture(redPicture1, "red");
	Picture redImmutablePicture = new ImmutablePixelArrayPicture(redPicture1, "red");

	Region region00001 = new RegionImpl(0, 0, 0, 0);
	Region region0011 = new RegionImpl(0, 0, 1, 1);
	Region region0022 = new RegionImpl(0, 0, 2, 2);
	Region region11111 = new RegionImpl(1, 1, 1, 1);
	Region region112211 = new RegionImpl(1, 1, 2, 2);
	Region region1133111 = new RegionImpl(1, 1, 3, 3);
	Region region21211 = new RegionImpl(2, 1, 2, 1);
	Region region212211 = new RegionImpl(2, 1, 2, 2);
	Region region213311 = new RegionImpl(2, 1, 3, 3);
	Region region22221 = new RegionImpl(2, 2, 2, 2);
	Region region303111 = new RegionImpl(3, 0, 3, 1);
	Region region31311 = new RegionImpl(3, 1, 3, 1);
	Region region33331 = new RegionImpl(3, 3, 3, 3);

	ObservablePicture observableRedImmutablePicture = new ObservablePictureImpl(redImmutablePicture);
	ObservablePicture observableRedMutablePicture = new ObservablePictureImpl(redMutablePicture);

	ROIObserver observer111 = new ROIObserverImpl();
	ROIObserver observer211 = new ROIObserverImpl();
	ROIObserver observer311 = new ROIObserverImpl();

	ObserverNotify ob1 = new ObserverNotifyImpl(observer1);
	ObserverNotify ob2 = new ObserverNotifyImpl(observer2);
	ObserverNotify ob3 = new ObserverNotifyImpl(observer3);

	@Test
	public void testRegionImplConstructor6() {
		try {
			new RegionImpl(4, 1, 3, 2);
			fail("Left cannot be greater than right");
		} catch (IllegalArgumentException e) {
		}
		try {
			new RegionImpl(1, 4, 2, 3);
			fail("Top cannot be greater than bottom");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testRegionIntersect() throws NoIntersectionException {
		assert (equalRegions(region2122.intersect(region2133), region2122));
		try {
			region2222.intersect(null);
			fail("cannot intersect null");
		} catch (NoIntersectionException e) {
		}
	}

	@Test
	public void testRegionUnion2() {
		assert (equalRegions(region2222.union(region1111), region1122));
		assert (equalRegions(region3031.union(null), region3031));
	}

	@Test
	public void testObservablePictureImplConstructor2() {
		try {
			new ObservablePictureImpl(null);
			fail("Picture cannot be null.");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testROIObservableRegisterMethods() {

		observableRedMutablePicture.registerROIObserver(observer1, region0000);
		observableRedMutablePicture.registerROIObserver(observer2, region2222);
		observableRedMutablePicture.registerROIObserver(observer2, region2133);
		observableRedMutablePicture.registerROIObserver(observer3, region3031);

		assertEquals(3, observableRedMutablePicture.findROIObservers(region0022).length);
		assertEquals(observer1, observableRedMutablePicture.findROIObservers(region0022)[0]);
		assertEquals(observer2, observableRedMutablePicture.findROIObservers(region0022)[1]);
		assertEquals(observer2, observableRedMutablePicture.findROIObservers(region0022)[2]);

		observableRedMutablePicture.registerROIObserver(observer3, region2122);

		assertEquals(4, observableRedMutablePicture.findROIObservers(region0022).length);
		assertEquals(observer1, observableRedMutablePicture.findROIObservers(region0022)[0]);
		assertEquals(observer2, observableRedMutablePicture.findROIObservers(region0022)[1]);
		assertEquals(observer2, observableRedMutablePicture.findROIObservers(region0022)[2]);
		assertEquals(observer3, observableRedMutablePicture.findROIObservers(region0022)[3]);

		observableRedMutablePicture.unregisterROIObserver(observer2);

		assertEquals(2, observableRedMutablePicture.findROIObservers(region0022).length);
		assertEquals(observer1, observableRedMutablePicture.findROIObservers(region0022)[0]);
		assertEquals(observer3, observableRedMutablePicture.findROIObservers(region0022)[1]);

		observableRedMutablePicture.registerROIObserver(observer2, region2222);
		observableRedMutablePicture.registerROIObserver(observer2, region2133);

		assertEquals(4, observableRedMutablePicture.findROIObservers(region0022).length);
		assertEquals(observer1, observableRedMutablePicture.findROIObservers(region0022)[0]);
		assertEquals(observer3, observableRedMutablePicture.findROIObservers(region0022)[1]);
		assertEquals(observer2, observableRedMutablePicture.findROIObservers(region0022)[2]);
		assertEquals(observer2, observableRedMutablePicture.findROIObservers(region0022)[3]);

		observableRedMutablePicture.unregisterROIObservers(region2222);

/*		assertEquals(1, observableRedMutablePicture.findROIObservers(region0022).length);
		assertEquals(observer1, observableRedMutablePicture.findROIObservers(region0022)[0]);
*/	}

	@Test
	public void testPaintMethod() {

		observableRedMutablePicture.registerROIObserver(ob1, region0000);
		observableRedMutablePicture.registerROIObserver(ob2, region2222);
		observableRedMutablePicture.registerROIObserver(ob2, region2133);
		observableRedMutablePicture.registerROIObserver(ob3, region3031);

		observableRedMutablePicture.paint(0, 0, green);
		assertEquals(1, ob1.getCount());
		assertEquals(0, ob2.getCount());
		assertEquals(0, ob3.getCount());
		assert (equalRegions(ob1.NotifiedRegion().get(0), region0000));

		observableRedMutablePicture.paint(0, 0, observableRedImmutablePicture);
		assertEquals(2, ob1.getCount());
		assertEquals(2, ob2.getCount());
		assertEquals(1, ob3.getCount());
		assert (equalRegions(ob1.NotifiedRegion().get(0), region0000));
		assert (equalRegions(ob1.NotifiedRegion().get(1), region0000));
		assert (equalRegions(ob2.NotifiedRegion().get(0), region2222));
		assert (equalRegions(ob2.NotifiedRegion().get(1), region2133));
		assert (equalRegions(ob3.NotifiedRegion().get(0), region3031));

		observableRedMutablePicture.paint(1, 1, green);
		assertEquals(2, ob1.getCount());
		assertEquals(2, ob2.getCount());
		assertEquals(1, ob3.getCount());
		assert (equalRegions(ob1.NotifiedRegion().get(0), region0000));
		assert (equalRegions(ob1.NotifiedRegion().get(1), region0000));
		assert (equalRegions(ob2.NotifiedRegion().get(0), region2222));
		assert (equalRegions(ob2.NotifiedRegion().get(1), region2133));
		assert (equalRegions(ob3.NotifiedRegion().get(0), region3031));

		observableRedMutablePicture.paint(2, 2, 1, green);
		assertEquals(2, ob1.getCount());
		assertEquals(4, ob2.getCount());
		assertEquals(2, ob3.getCount());
		assert (equalRegions(ob1.NotifiedRegion().get(0), region0000));
		assert (equalRegions(ob1.NotifiedRegion().get(1), region0000));
		assert (equalRegions(ob2.NotifiedRegion().get(0), region2222));
		assert (equalRegions(ob2.NotifiedRegion().get(1), region2133));
		assert (equalRegions(ob2.NotifiedRegion().get(2), region2222));
		assert (equalRegions(ob2.NotifiedRegion().get(3), region2133));
		assert (equalRegions(ob3.NotifiedRegion().get(0), region3031));
		assert (equalRegions(ob3.NotifiedRegion().get(1), region3131));
	}

	@Test
	public void testSuspendAndResume2() {

		observableRedMutablePicture.registerROIObserver(ob1, region0000);
		observableRedMutablePicture.registerROIObserver(ob2, region2222);
		observableRedMutablePicture.registerROIObserver(ob2, region2133);
		observableRedMutablePicture.registerROIObserver(ob3, region3031);

		observableRedMutablePicture.suspendObservable();

		observableRedMutablePicture.paint(0, 0, green);
		observableRedMutablePicture.paint(1, 1, green);
		assertEquals(0, ob1.getCount());
		assertEquals(0, ob2.getCount());
		assertEquals(0, ob3.getCount());

		observableRedMutablePicture.resumeObservable();
		assertEquals(1, ob1.getCount());
		assertEquals(0, ob2.getCount());
		assertEquals(0, ob3.getCount());
		assert (equalRegions(ob1.NotifiedRegion().get(0), region0000));

		observableRedMutablePicture.suspendObservable();

		observableRedMutablePicture.paint(0, 0, green);
		observableRedMutablePicture.paint(1, 1, green);
		observableRedMutablePicture.paint(2, 2, green);

		observableRedMutablePicture.resumeObservable();
		assertEquals(2, ob1.getCount());
		assertEquals(2, ob2.getCount());
		assertEquals(0, ob3.getCount());
		assert (equalRegions(ob1.NotifiedRegion().get(0), region0000));
		assert (equalRegions(ob1.NotifiedRegion().get(1), region0000));
		assert (equalRegions(ob2.NotifiedRegion().get(0), region2222));
		assert (equalRegions(ob2.NotifiedRegion().get(1), region2122));
	}

	private static boolean equalRegions5(Region r1, Region r2) {
		return (r1.getLeft() == r2.getLeft() && r1.getTop() == r2.getTop() && r1.getBottom() == r2.getBottom()
				&& r1.getRight() == r2.getRight());
	}
	
}


