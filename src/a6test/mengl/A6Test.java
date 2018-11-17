package a6test.mengl;

import a6.*;

import static org.junit.Assert.*;

import org.junit.Test;

public class A6Test {
	Pixel red = new ColorPixel(1, 0, 0);

	Picture RedMutablePicture = new MutablePixelArrayPicture(
			new Pixel[][] { { red, red, red }, { red, red, red }, { red, red, red } }, "Red");
	ObservablePicture observableRedMutable = new ObservablePictureImpl(RedMutablePicture);

	private Region region0022 = new RegionImpl(0, 0, 2, 2);
	private Region region1122 = new RegionImpl(1, 1, 2, 2);
	private Region region3344 = new RegionImpl(3, 3, 4, 4);
	private Region region2244 = new RegionImpl(2, 2, 4, 4);

	ROIObserver observer1 = new ROIObserverImpl();
	ROIObserver observer2 = new ROIObserverImpl();

	@Test
	public void testRegionImplConstructor() {

		try {
			Region region = new RegionImpl(2, 2, 1, 3);
			fail("Left cannot be greater than right");
		} catch (IllegalArgumentException e) {
		}

		try {
			Region region = new RegionImpl(2, 2, 3, 1);
			fail("Bottom cannot be greater than top");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testRegionImplIntersection() throws NoIntersectionException {

		assertEquals(1, region0022.intersect(region1122).getLeft());
		assertEquals(1, region0022.intersect(region1122).getTop());
		assertEquals(2, region0022.intersect(region1122).getRight());
		assertEquals(2, region0022.intersect(region1122).getBottom());

		try {
			region0022.intersect(region3344);
			fail("No intersection");
		} catch (NoIntersectionException e) {
		}
	}

	@Test
	public void testRegionImplUnion() {

		assertEquals(0, region0022.union(region3344).getLeft());
		assertEquals(0, region0022.union(region3344).getTop());
		assertEquals(4, region0022.union(region3344).getRight());
		assertEquals(4, region0022.union(region3344).getBottom());

		assertEquals(region0022, region0022.union(null));
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
	public void testRegisterAndFindObserver() {
		observableRedMutable.registerROIObserver(observer1, region0022);
		observableRedMutable.registerROIObserver(observer1, region2244);
		observableRedMutable.registerROIObserver(observer2, region3344);

		assertEquals(2, observableRedMutable.findROIObservers(region1122).length);
		assertEquals(observer1, observableRedMutable.findROIObservers(region1122)[0]);
		assertEquals(observer1, observableRedMutable.findROIObservers(region1122)[1]);

		assertEquals(3, observableRedMutable.findROIObservers(region2244).length);
		assertEquals(observer1, observableRedMutable.findROIObservers(region2244)[0]);
		assertEquals(observer1, observableRedMutable.findROIObservers(region2244)[1]);
		assertEquals(observer2, observableRedMutable.findROIObservers(region2244)[2]);
	}

	@Test
	public void testUnregister() {
		observableRedMutable.registerROIObserver(observer1, region0022);
		observableRedMutable.registerROIObserver(observer1, region2244);
		observableRedMutable.registerROIObserver(observer2, region3344);
		observableRedMutable.registerROIObserver(observer2, region1122);

		assertEquals(3, observableRedMutable.findROIObservers(region1122).length);
		observableRedMutable.unregisterROIObserver(observer1);
		assertEquals(1, observableRedMutable.findROIObservers(region1122).length);

		assertEquals(1, observableRedMutable.findROIObservers(region0022).length);
		observableRedMutable.unregisterROIObservers(region0022);
		assertEquals(0, observableRedMutable.findROIObservers(region0022).length);
	}

}
