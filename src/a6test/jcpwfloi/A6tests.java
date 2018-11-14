package a6test.jcpwfloi;

import a6.*;

import static org.junit.Assert.*;

import org.junit.Test;

public class A6tests {

	private static Pixel generateRandomPixel() {
		return new ColorPixel(Math.random(), Math.random(), Math.random());
	}
	
	private static Pixel[][] generateRandomMutablePictureArray(int x, int y) {
		Pixel[][] pixelArray = new Pixel[x][y];
		for (int i = 0; i < x; ++ i)
			for (int j = 0; j < y; ++ j)
				pixelArray[i][j] = generateRandomPixel();
		return pixelArray;
	}
	
	private static Picture generateRandomMutablePicture(int x, int y) {
		return new MutablePixelArrayPicture(generateRandomMutablePictureArray(x, y), "");
	}
	
	private static Picture generateRandomMutablePictureWithCaption(int x, int y, String s) {
		return new MutablePixelArrayPicture(generateRandomMutablePictureArray(x, y), s);
	}
	
	@Test
	public void regionConstructorTest() {
		// test instantiation
		try {
			Region a = new RegionImpl(1, 1, 1, 1);
			a = new RegionImpl(0, 0, 100, 100);
			a = new RegionImpl(0, 1, 100, 101); 
		} catch (RuntimeException e) {
			fail("Fail to instantiate Region instance");
		}
				
		try {
			Region a = new RegionImpl(4, 3, 3, 3);
			fail("left should not be greater than right");
		} catch (RuntimeException e) {
		}
	}
	
	@Test
	public void regionGetterTest() {
		try {
			Region a = new RegionImpl(0, 100, 2, 101);
			assertEquals(a.getLeft(), 0);
			assertEquals(a.getTop(), 100);
			assertEquals(a.getRight(), 2);
			assertEquals(a.getBottom(), 101);
		} catch (RuntimeException e) {
			fail("getter fails");
		}
	}
	
	@Test
	public void regionIntersectionTest() {
		try {
			Region a = new RegionImpl(0, 100, 2, 101);
			Region b = new RegionImpl(2, 100, 3, 101);
			
			Region ans = a.intersect(b);
			
			assertEquals(ans.getLeft(), 2);
			assertEquals(ans.getTop(), 100);
			assertEquals(ans.getRight(), 2);
			assertEquals(ans.getBottom(), 101);
		} catch (NoIntersectionException e) {
			fail("intersection fails");
		}
		
		try {
			Region a = new RegionImpl(0, 100, 2, 101);
			Region b = new RegionImpl(100, 100, 101, 101);
			
			Region ans = a.intersect(b);
			
			fail("intersection fails: should throw exception");
		} catch (NoIntersectionException e) {
		}
	}
	
	@Test
	public void nullRegionUnionTest() {
		Region a = new RegionImpl(1, 1, 6, 5);
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
		
		union = b.union(a);
		
		assertEquals(0, union.getLeft());
		assertEquals(0, union.getTop());
		assertEquals(10, union.getRight());
		assertEquals(10, union.getBottom());
		
	}
	
	@Test
	public void basicObserverTest() {
		Picture picture = generateRandomMutablePictureWithCaption(100, 100, "KMP");
		
		ObservablePicture observable = new ObservablePictureImpl(picture);
		
		SuperObserverHandlerMachine machine = new SuperObserverHandlerMachine(
				observable, 100, true);
		
		machine.test();
	}

}
