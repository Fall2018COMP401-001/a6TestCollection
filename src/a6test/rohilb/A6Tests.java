package a6test.rohilb;

import static org.junit.Assert.*;
import org.junit.Test;
import a6.*;

public class A6Tests {

	@Test
	public void testRegionImplConstructor() {

		// Left is greater than right.
		try {
			Region tested = new RegionImpl(9, 2, 8, 6);
		} catch (IllegalArgumentException e) {
		}

		// Top is greater than bottom.
		try {
			Region tested = new RegionImpl(3, 7, 8, 6);
		} catch (IllegalArgumentException e) {
		}

	}

	@Test
	public void testRegionImplGetters() {
		Region tested = new RegionImpl(2, 3, 6, 8);
		assertEquals(tested.getLeft(), 2);
		assertEquals(tested.getTop(), 3);
		assertEquals(tested.getRight(), 6);
		assertEquals(tested.getBottom(), 8);
	}

	@Test
	public void testRegionUnion() {
		Region first_region = new RegionImpl(1, 1, 5, 5);
		Region second_region = new RegionImpl(4, 4, 8, 8);
		Region third_region = first_region.union(second_region);
		assertEquals(third_region.getLeft(), 1);
		assertEquals(third_region.getTop(), 1);
		assertEquals(third_region.getRight(), 8);
		assertEquals(third_region.getBottom(), 8);
	}

	@Test
	public void testObservablePictureDelegation() {
		Pixel[][] mutable = new Pixel[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				mutable[i][j] = new ColorPixel(0.0, 1.0, 0.0);
			}
		}
		Picture mutable_picture = new MutablePixelArrayPicture(mutable, "A boring caption.");
		Picture observed_picture = new ObservablePictureImpl(mutable_picture);
		
		assertEquals(observed_picture.getWidth(), mutable_picture.getWidth());
		assertEquals(observed_picture.getHeight(), mutable_picture.getHeight());
		assertEquals(observed_picture.getCaption(), mutable_picture.getCaption());
		observed_picture.setCaption("A changed caption.");
		assertEquals(observed_picture.getCaption(), mutable_picture.getCaption());
	}
}
