package a6test.lkeilly;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.Test;
import a6.*;

public class A6Tests {

	// Initialize different pixel amounts.
	Pixel red = new ColorPixel(1, 0, 0);
	Pixel green = new ColorPixel(0, 1, 0);
	Pixel blue = new ColorPixel(0, 0, 1);
	Pixel randomColor = new ColorPixel(0.85, 0.65, 0.214);

	// Valid Pixel 2d Arrays
	Pixel[][] randomPicture = { { blue, randomColor, randomColor, green }, { green, red, green, randomColor },
			{ blue, blue, red, red }, { green, blue, green, blue } };

	// Initializing captions
	String caption1 = "Hello world!";

	// Valid Immutable Pixel Array Picture initializations.
	Picture randomImmutablePicture = new ImmutablePixelArrayPicture(randomPicture, caption1);

	// Valid Mutable Pixel Array Picture initializations.
	Picture randomMutablePicture = new MutablePixelArrayPicture(randomPicture, caption1);

	// Regions
	Region region1 = new RegionImpl(0, 0, 2, 2);
	Region region2 = new RegionImpl(1, 0, 2, 2);
	Region region3 = new RegionImpl(3, 3, 3, 3);

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
		// regions
		Region a = new RegionImpl(-2, -2, 3, 3);
		Region b = new RegionImpl(0, 0, 5, 5);
		Region c = null;
		Region d = new RegionImpl(4, 4, 5, 5);

		try {
			a.intersect(c);
			fail("There is no intersection");
		} catch (NoIntersectionException e) {
		}

		try {
			Region intersection = a.intersect(b);

			assertEquals(0, intersection.getLeft());
			assertEquals(0, intersection.getTop());
			assertEquals(3, intersection.getRight());
			assertEquals(3, intersection.getBottom());

			intersection = b.intersect(a);

			assertEquals(0, intersection.getLeft());
			assertEquals(0, intersection.getTop());
			assertEquals(3, intersection.getRight());
			assertEquals(3, intersection.getBottom());
		} catch (NoIntersectionException e) {
			fail("There is an intersection");
		}

		try {
			a.intersect(d);
			fail("There is no intersection");
		} catch (NoIntersectionException e) {
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
			fail("Picture is not null");
		}
	}

	@Test
	public void ObservablePictureImplRegisterUnregisterTests() {

		// ObservableImmutablePicture
		ObservablePicture validImmutablePicture = new ObservablePictureImpl(randomImmutablePicture);

		// Observers
		ROIObserverImpl observer1 = new ROIObserverImpl();
		ROIObserverImpl observer2 = new ROIObserverImpl();

		// Registering observers with regions
		validImmutablePicture.registerROIObserver(observer1, region1);
		validImmutablePicture.registerROIObserver(observer1, region2);
		validImmutablePicture.registerROIObserver(observer2, region1);
		validImmutablePicture.registerROIObserver(observer2, region3);

		// Finding observers according to a region
		assertEquals(3, validImmutablePicture.findROIObservers(region1).length);
		assertEquals(observer1, validImmutablePicture.findROIObservers(region1)[0]);
		assertEquals(observer1, validImmutablePicture.findROIObservers(region1)[1]);
		assertEquals(observer2, validImmutablePicture.findROIObservers(region1)[2]);
		assertEquals(3, validImmutablePicture.findROIObservers(region2).length);
		assertEquals(observer1, validImmutablePicture.findROIObservers(region2)[0]);
		assertEquals(observer1, validImmutablePicture.findROIObservers(region2)[1]);
		assertEquals(observer2, validImmutablePicture.findROIObservers(region2)[2]);
		assertEquals(1, validImmutablePicture.findROIObservers(region3).length);
		assertEquals(observer2, validImmutablePicture.findROIObservers(region3)[0]);

		// Unregistering a observer2 from all its regions
		validImmutablePicture.unregisterROIObserver(observer2);
		assertEquals(2, validImmutablePicture.findROIObservers(region1).length);
		assertEquals(observer1, validImmutablePicture.findROIObservers(region1)[0]);
		assertEquals(observer1, validImmutablePicture.findROIObservers(region1)[1]);
		assertEquals(0, validImmutablePicture.findROIObservers(region3).length);

		// Registering again observer2
		validImmutablePicture.registerROIObserver(observer2, region1);
		validImmutablePicture.registerROIObserver(observer2, region3);

		// Unregistering observers from region1
		validImmutablePicture.unregisterROIObservers(region1);
		assertEquals(0, validImmutablePicture.findROIObservers(region1).length);
		assertEquals(1, validImmutablePicture.findROIObservers(region3).length);
		assertEquals(observer2, validImmutablePicture.findROIObservers(region3)[0]);
	}

	@Test
	public void paintMethodsTests() {

		// ObservableImmutablePicture
		ObservablePicture validImmutablePicture = new ObservablePictureImpl(randomImmutablePicture);

		// Painting a single pixel
		Picture newPic = validImmutablePicture.paint(0, 0, red);
		check_for_component_equality(red, newPic.getPixel(0, 0));

		// Painting a region
		validImmutablePicture.paint(1, 1, 2, 2, green);
		for (int i = 1; i <= 2; i++) {
			for (int j = 1; j <= 2; j++) {
				check_for_component_equality(green, validImmutablePicture.getPixel(i, j));
			}
		}

		// Painting with a radius
		Picture newPic2 = validImmutablePicture.paint(2, 2, 10, blue);
		for (int i = 0; i < newPic2.getWidth(); i++) {
			for (int j = 0; j < newPic2.getHeight(); j++) {
				check_for_component_equality(blue, newPic2.getPixel(i, j));
			}
		}

	}

	private static boolean check_for_component_equality(Pixel a, Pixel b) {
		assertEquals(a.getRed(), b.getRed(), 0.001);
		assertEquals(a.getGreen(), b.getGreen(), 0.001);
		assertEquals(a.getBlue(), b.getBlue(), 0.001);

		return true;
	}

}
