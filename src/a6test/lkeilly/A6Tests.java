package a6test.lkeilly;

import static org.junit.Assert.*;

import org.junit.Test;
import a6.*;


public class A6Tests {
	// Initialize different pixel amounts.
	Pixel red = new ColorPixel(1, 0, 0);
	Pixel green = new ColorPixel(0, 1, 0);
	Pixel blue = new ColorPixel(0, 0, 1);
	Pixel randomColor = new ColorPixel(0.85, 0.65, 0.214);

	// Valid Pixel 2d Arrays
	Pixel[][] randomPicture = { { blue, randomColor, randomColor, green }, { green, red, green, randomColor } };

	// Initializing captions
	String caption1 = "Hello world!";

	// Valid Immutable Pixel Array Picture initializations.
	Picture randomImmutablePicture = new ImmutablePixelArrayPicture(randomPicture, caption1);

	// Valid Mutable Pixel Array Picture initializations.
	Picture randomMutablePicture = new MutablePixelArrayPicture(randomPicture, caption1);

	@Test
	public void RegionImplConstructorTest() {
		try {
			Region leftGreaterThanRight = new RegionImpl(5, 2, 1, 5);
			fail("Left side value is greater than the right side value");
		} catch (IllegalArgumentException e) {
		}

		try {
			Region topGreaterThanBottom = new RegionImpl(3, 7, 6, 5);
			fail("Top side value is greater than the Bottom side value");
		} catch (IllegalArgumentException e) {
		}

		try {
			Region negativeValues = new RegionImpl(-5, -2, 1, 5);
		} catch (IllegalArgumentException e) {
			fail("Negatives values are accepted");
		}
	}

	@Test
	public void regionIntersectionTest() {
		Region a = new RegionImpl(-2, -2, 3, 3);
		Region b = new RegionImpl(0, 0, 5, 5);
		Region c = null;
		Region d = new RegionImpl(4, 4, 5, 5);
		
		try {
			a.intersect(c);
			fail("There is no intersection");
		} catch (NoIntersectionException) {
		}
		
		Region intersection = a.intersect(b);
		
		assertEquals(0, intersection.getLeft());
		assertEquals(0, intersection.getTop());
		assertEquals(3, intersection.getRight());
		assertEquals(3, intersection.getBottom());
		
		intersection = b.intersects(a);
		
		assertEquals(0, intersection.getLeft());
		assertEquals(0, intersection.getTop());
		assertEquals(3, intersection.getRight());
		assertEquals(3, intersection.getBottom());
		
		try {
			a.intersect(d);
			fail("There is no intersection");
		} catch (NoIntersectionException) {
		}		
	}

	@Test
	public void ObservablePictureImplConstructorTest() {
		try {
			ObservablePicture invalidNullPicture = new ObservablePictureImpl(null);
			fail("Picture cannot be null");
		} catch (IllegalArgumentException e) {
		}

		try {
			ObservablePicture validPicture = new ObservablePictureImpl(randomImmutablePicture);
		} catch (IllegalArgumentException e) {
			fail("Picture cannot be null");
		}
	}
	
	@Test
	public void ObservablePictureImplRegisterUnregisterTests() {
		ObservablePicture validPicture = new ObservablePictureImpl(randomImmutablePicture);
		
		
	}

}
