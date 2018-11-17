package a6test.bentontc;

import static org.junit.Assert.*;

import org.junit.Test;

import a6.*;

public class A6tests {
	
	Pixel red = new ColorPixel(1, 0, 0);
	Pixel green = new ColorPixel(0, 1, 0);
	Pixel blue = new ColorPixel(0, 0, 1);
	Pixel white = new ColorPixel(1, 1, 1);
	Pixel black = new ColorPixel(0, 0, 0);
	
	Pixel red1 = new ColorPixel(1, 0, 0);
	Pixel green1 = new ColorPixel(0, 1, 0);
	Pixel[][] redPicture = { { red, red, red, red }, { red, red, red, red }, 
			                 { red, red, red, red }, { red, red, red, red } };
	Picture redMutablePicture = new MutablePixelArrayPicture(redPicture, "red");
	Picture redImmutablePicture = new ImmutablePixelArrayPicture(redPicture, "red");

	Region region0000 = new RegionImpl(0, 0, 0, 0);
	Region region0011 = new RegionImpl(0, 0, 1, 1);
	Region region0022 = new RegionImpl(0, 0, 2, 2);
	Region region1111 = new RegionImpl(1, 1, 1, 1);
	Region region1122 = new RegionImpl(1, 1, 2, 2);
	Region region1133 = new RegionImpl(1, 1, 3, 3);
	Region region2121 = new RegionImpl(2, 1, 2, 1);
	Region region2122 = new RegionImpl(2, 1, 2, 2);
	Region region2133 = new RegionImpl(2, 1, 3, 3);
	Region region2222 = new RegionImpl(2, 2, 2, 2);
	Region region3031 = new RegionImpl(3, 0, 3, 1);
	Region region3131 = new RegionImpl(3, 1, 3, 1);
	Region region3333 = new RegionImpl(3, 3, 3, 3);
	
	ObservablePicture observableRedImmutablePicture = new ObservablePictureImpl(redImmutablePicture);
	ObservablePicture observableRedMutablePicture = new ObservablePictureImpl(redMutablePicture);
	
	ROIObserver observer1 = new ROIObserverImpl();
	ROIObserver observer2 = new ROIObserverImpl();
	ROIObserver observer3 = new ROIObserverImpl();
	
	Pixel red2 = new ColorPixel(1, 0, 0);
	Pixel blue2 = new ColorPixel(0, 0, 1);
	
	Picture blueMutable3by3 = new MutablePixelArrayPicture(new Pixel[][] {{blue,blue,blue},{blue,blue,blue},{blue,blue,blue}}, "Blue");
	Picture blueImmutable3by3 = new ImmutablePixelArrayPicture(new Pixel[][] {{blue,blue,blue},{blue,blue,blue},{blue,blue,blue}}, "Blue");
	
	Region region21331 = new RegionImpl(2,1,3,3);
	Region region11221 = new RegionImpl(1,1,2,2);
	Region region21221 = new RegionImpl(2,1,2,2);
	Region region11331 = new RegionImpl(1,1,3,3);
	Region region30311 = new RegionImpl(3,0,3,1);
	
	ObservablePicture observableBlueMutable3by3 = new ObservablePictureImpl(blueMutable3by3);
	ObservablePicture observableBlueImmutable3by3 = new ObservablePictureImpl(blueImmutable3by3);
	
	ROIObserver observer11 = new ROIObserverImpl();
	ROIObserver observer21 = new ROIObserverImpl();
	ROIObserver observer31 = new ROIObserverImpl();

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
	public void basicRegionIntersectiontest() {
		Region a = new RegionImpl(0, 0, 4, 4);
		Region b = new RegionImpl(3, 3, 6, 6);
		Region c = new RegionImpl(7,7,10,10);

		try {
			Region null_intersection = a.intersect(null);
			fail("Region cannot be null");
		} catch (NoIntersectionException e) {
		}

		try {
			Region null_intersection = a.intersect(c);
			fail("Regions don't intersect");
		} catch (NoIntersectionException e) {
		}

		try {
			Region intersection = a.intersect(b);

			assertEquals(3, intersection.getLeft());
			assertEquals(3, intersection.getTop());
			assertEquals(4, intersection.getRight());
			assertEquals(4, intersection.getBottom());

			intersection = b.intersect(a);

			assertEquals(3, intersection.getLeft());
			assertEquals(3, intersection.getTop());
			assertEquals(4, intersection.getRight());
			assertEquals(4, intersection.getBottom());
		} catch (NoIntersectionException e) {
			fail("Should not have thrown NoIntersectionException");
		}
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
	@Test
	public void testObservablePicturePaint() {
		//Mutable underlying picture
		Picture mut = new MutablePixelArrayPicture(makePixelArray(5, 5), "5x5 half black half white");
		Picture o = new ObservablePictureImpl(mut);
		
		mut.paint(0, 0, red);
		assertTrue(colorCompare(o.getPixel(0, 0), red));
		o.paint(1, 0, blue);
		assertTrue(colorCompare(mut.getPixel(1, 0), blue));
		assertTrue(colorCompare(o.getPixel(1, 0), blue));	
		
		Picture p = o.paint(2, 0, green);
		assertEquals(p, o);
		assertNotEquals(o, mut);
		
		//Immutable underlying picture
		Picture imm = new ImmutablePixelArrayPicture(makePixelArray(5, 5), "5x5 half black half white");
		Picture o2 = new ObservablePictureImpl(imm);
		
		imm.paint(0, 0, red);
		assertFalse(colorCompare(o2.getPixel(0, 0), red));
		Picture i = imm.paint(0, 0, red);
		assertFalse(colorCompare(o2.getPixel(0, 0), red));
		
		o2.paint(1, 0, blue);
		assertTrue(colorCompare(o2.getPixel(1, 0), blue));
		Picture po = o2.paint(2, 0, green);
		assertTrue(colorCompare(o2.getPixel(2, 0), green));
		
		Picture p2 = o.paint(3, 0, red);
		assertEquals(p2, o);
		assertNotEquals(p2, imm);
	}
	
	@Test
	public void testObserverRegistrationAndFinding() {
		Picture p = new MutablePixelArrayPicture(makePixelArray(5, 5), "5x5 half black half white");
		ObservablePictureImpl o = new ObservablePictureImpl(p);
		
		Region region1 = new RegionImpl(2, 2, 3, 3);
		Region region2 = new RegionImpl(4, 4, 5, 5);
		Region region3 = new RegionImpl(0, 0, 1, 5);
		Region region4 = new RegionImpl(3, 3, 4, 4);
		
		ROIObserver o1 = new ROIObserverImpl();
		ROIObserver o2 = new ROIObserverImpl();
		ROIObserver o3 = new ROIObserverImpl();
		
		o.registerROIObserver(o1, region1);
		o.registerROIObserver(o2, region2);
		o.registerROIObserver(o3, region3);
		
		assertEquals(o1, o.findROIObservers(region1)[0]);
		assertEquals(o2, o.findROIObservers(region2)[0]);
		assertEquals(o3, o.findROIObservers(region3)[0]);
		assertNotEquals(o1, o.findROIObservers(region2)[0]);
		assertNotEquals(o1, o.findROIObservers(region3)[0]);
		assertEquals(o1, o.findROIObservers(region4)[0]);
		assertEquals(o2, o.findROIObservers(region4)[1]);
		
		o.unregisterROIObserver(o1);
		o.unregisterROIObservers(region3);
		
		try {
			ROIObserver test = o.findROIObservers(region1)[0];
			fail("No observers here, array should be out of bounds!");
		} catch (ArrayIndexOutOfBoundsException e) {
			
		}
		
		try {
			ROIObserver test = o.findROIObservers(region3)[0];
			fail("No observers here, array should be out of bounds!");
		} catch (ArrayIndexOutOfBoundsException e) {
			
		}
		
		o.registerROIObserver(o2, region3);
		assertEquals(o2, o.findROIObservers(region2)[0]);
		assertEquals(o2, o.findROIObservers(region4)[0]);
		assertEquals(o2, o.findROIObservers(region3)[0]);
	}
	
	@Test
	public void testNotify() {
		//No suspend or resume
		Picture p = new MutablePixelArrayPicture(makePixelArray(10, 10), "10x10 half black half white");
		ObservablePicture o = new ObservablePictureImpl(p);
		
		Region r1 = new RegionImpl(1, 1, 5, 5);
		Region r2 = new RegionImpl(0, 0, 3, 3);
		Region r3 = new RegionImpl(2, 2, 7, 7);
		
		ROIObserverImpl o1 = new ROIObserverImpl();
		ROIObserverImpl o2 = new ROIObserverImpl();
		
		o.registerROIObserver(o1, r1);
		o.registerROIObserver(o2, r2);
		o.registerROIObserver(o2, r3);
		
		o.paint(1, 1, red);
		assertEquals(1, o1.getNotifyCount());
		assertEquals(1, o2.getNotifyCount());
		o1.clearNotifications();
		o2.clearNotifications();
		
		o.paint(1, 1, 4, 4, red);
		assertEquals(1, o1.getNotifyCount());
		assertEquals(2, o2.getNotifyCount());
		o1.clearNotifications();
		o2.clearNotifications();
		
		o.paint(8, 8, red);
		assertEquals(0, o1.getNotifyCount());
		assertEquals(0, o2.getNotifyCount());
		
		//Suspend and resume
		o.suspendObservable();
		o.paint(2, 1, red);
		o.paint(3, 2, red);
		o.paint(4, 4, red);
		o.resumeObservable();
		assertEquals(1, o1.getNotifyCount());
		assertEquals(2, o2.getNotifyCount());
		o1.clearNotifications();
		o2.clearNotifications();
		
	}
	
	private boolean regionCompare(Region r1, Region r2) {
		if (r1.getLeft() == r2.getLeft() && r1.getRight() == r2.getRight()
				&& r1.getTop() == r2.getTop() && r1.getBottom() == r2.getBottom()){
					return true;
		}
		return false;
	}
	
	private boolean colorCompare(Pixel c1, Pixel c2) {
		if (c1.getRed() == c2.getRed() && c1.getGreen() == c2.getGreen()
				&& c1.getBlue() == c2.getBlue()) {
			return true;
		}
		return false;
	}
	
	private Pixel[][] makePixelArray(int x, int y) {
		Pixel[][] pixels = new Pixel[x][y];
		for (int r = 0; r < pixels.length; r++) {
			for (int h = 0; h < pixels[0].length; h++) {
				if (h < y/2) {
					pixels[r][h] = Pixel.BLACK;
				}else {
					pixels[r][h] = Pixel.WHITE;
				}
			}
		}
		return pixels;
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


	@Test
	public void testObservablePictureImplConstructor() {
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
		
		assertEquals(1, observableRedMutablePicture.findROIObservers(region0022).length);
		assertEquals(observer1, observableRedMutablePicture.findROIObservers(region0022)[0]);
	}
		
	private static boolean equalRegions(Region r1, Region r2) {
		return (r1.getLeft() == r2.getLeft() && r1.getTop() == r2.getTop() && r1.getBottom() == r2.getBottom()
				&& r1.getRight() == r2.getRight());
	}
	@Test
	public void testRegionImplConstructor1() {
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
	public void testObservablePictureImplConstructor1() {
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
		observableBlueMutable3by3.registerROIObserver(observer11, region11331);
		observableBlueMutable3by3.registerROIObserver(observer11, region30311);
		observableBlueMutable3by3.registerROIObserver(observer21, region21221);
		observableBlueMutable3by3.registerROIObserver(observer31, region30311);
		
		assertEquals(2, observableBlueMutable3by3.findROIObservers(region11221).length);
		assertEquals(observer11, observableBlueMutable3by3.findROIObservers(region11221)[0]);
		assertEquals(observer21, observableBlueMutable3by3.findROIObservers(region11221)[1]);
	}
	
	
	static boolean equalPixels(Pixel p1, Pixel p2) {
		return (p1.getRed() == p2.getRed() && p2.getGreen() == p2.getGreen() && p1.getBlue() == p2.getBlue());
	}
}
