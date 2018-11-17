package a6test.pavcha;

import static org.junit.Assert.*;

import org.junit.Test;

import a6.*;

public class A6Tests {
	
	private static Pixel RED = new ColorPixel(1.0, 0, 0);
	private static Pixel GREEN = new ColorPixel(0, 1.0, 0);
	private static Pixel BLUE = new ColorPixel(0, 0, 1.0);
	
	Pixel[][] mutable_parray = { {RED, RED, RED, RED},
								 {GREEN, GREEN, GREEN, GREEN},
								 {BLUE, BLUE, BLUE, BLUE},
								 {RED, RED, RED, RED} };
	
	Region small_square = new RegionImpl(0, 0, 2, 2);
	Region intersecting = new RegionImpl(-1, -1, 1, 1);
	Region nonintersecting = new RegionImpl(-3, -3, -1, -1);
	
	Picture mutable = new MutablePixelArrayPicture(mutable_parray, "caption");
	
	@Test
	public void initializeRegionTest() {
		try {
			Region illegal = new RegionImpl(0, 0, -1, 0);
			fail("right cannot be less than left");
		} catch (IllegalArgumentException e) {
		}
		try {
			Region illegal = new RegionImpl(0, 0, 0, -1);
			fail("bottom cannot be less than top");
		} catch (IllegalArgumentException e) {
		}
		
		assertEquals(0, small_square.getLeft());
		assertEquals(0, small_square.getTop());
		assertEquals(2, small_square.getRight());
		assertEquals(2, small_square.getBottom());
	}
	
	@Test
	public void unionAndIntersectTest() throws NoIntersectionException {
		Region union = small_square.union(intersecting);
		Region intersection = small_square.intersect(intersecting);
		
		assertEquals(-1, union.getLeft());
		// assertEquals(2, union.getRight());
		assertEquals(-1, union.getTop());
		assertEquals(2, union.getBottom());
		
		assertEquals(0, intersection.getLeft());
		assertEquals(1, intersection.getRight());
		assertEquals(0, intersection.getTop());
		assertEquals(1, intersection.getBottom());
		
		try {
			small_square.intersect(nonintersecting);
			fail("no intersection between two regions");
		} catch (NoIntersectionException e) {
		}
		
		try {
			small_square.intersect(null);
			fail("no intersection with null");
		} catch (NoIntersectionException e) {
		}
	}
	
	@Test
	public void intitializeObservablePictureImpl() {
		try {
			Picture illegal = new ObservablePictureImpl(null);
			fail("null cannot be observerd");
		} catch (IllegalArgumentException e) {
		}
		
		Picture observable = new ObservablePictureImpl(mutable);
		
		assertTrue(observable.getCaption().equals("caption"));
		assertEquals(observable.getHeight(), mutable.getHeight());
		assertEquals(observable.getWidth(), mutable.getWidth());
		for (int i = 0; i<observable.getWidth(); i++) {
			for (int j = 0; j<observable.getHeight(); j++) {
				assertEquals(observable.getPixel(i, j), mutable.getPixel(i, j));
			}
		}
		
		// Check that paint returns this
		
		Picture changed_observable = observable.paint(0, 0, GREEN);
		
		assertEquals(changed_observable, observable);
		
		for (int i = 0; i<observable.getWidth(); i++) {
			for (int j = 0; j<observable.getHeight(); j++) {
				check_for_component_equality(observable.getPixel(i, j), changed_observable.getPixel(i, j));
			}
		}
	}
	
	private static void check_for_component_equality(Pixel a, Pixel b) {
		assertTrue(a.getRed() - b.getRed() < 0.01);
		assertTrue(a.getGreen() - b.getGreen() < 0.01);
		assertTrue(a.getBlue() - b.getBlue() < 0.01);
	}
	
}

