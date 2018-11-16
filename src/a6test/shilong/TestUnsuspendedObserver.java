package a6test.shilong;

import org.junit.Test;

public class TestUnsuspendedObserver {

	@Test
	public void testSinglePixelSinglePaintObserver() {
		ObservablePicture pic = TestUtils.getObservablePicture(500, 500);
		Region matchRegion = new RegionImpl(15, 15, 15, 15);
		Region unmatchRegion = new RegionImpl(1, 1, 1, 1);
		ROIObserver notifiedObserver = new MockObserver(matchRegion, true);
		ROIObserver negativeObserver = new MockObserver(unmatchRegion, false);
		pic.registerROIObserver(notifiedObserver, matchRegion);
		pic.registerROIObserver(negativeObserver, unmatchRegion);
		pic.paint(15, 15, TestUtils.randColorPixel());
	}

	@Test
	public void testMultiPixelSinglePaintObserver() {
		ObservablePicture pic = TestUtils.getObservablePicture(500, 500);
		Region registeredRegion = new RegionImpl(10, 30, 40, 5);
		Region registeredNonMatchRegion = new RegionImpl(0, 5, 5, 0);
		Region matchRegion = new RegionImpl(15, 15, 15, 15);
		Region unmatchRegion = new RegionImpl(1, 1, 1, 1);
		ROIObserver notifiedObserver = new MockObserver(matchRegion, true);
		ROIObserver negativeObserver = new MockObserver(unmatchRegion, false);
		pic.registerROIObserver(notifiedObserver, registeredRegion);
		pic.registerROIObserver(negativeObserver, registeredNonMatchRegion);
		pic.paint(15, 15, TestUtils.randColorPixel());
	}

	@Test
	public void testRectangularWholeIntersectObserver() {
		ObservablePicture pic = TestUtils.getObservablePicture(500, 500);
		Region matchRegion = new RegionImpl(10, 30, 40, 5);
		Region registeredRegion = new RegionImpl(0, 100, 100, 0);
		ROIObserver notifiedObserver = new MockObserver(matchRegion, true);
		pic.registerROIObserver(notifiedObserver, registeredRegion);
		pic.paint(10, 30, 40, 5, TestUtils.randColorPixel());
	}

	@Test
	public void testRectangularEqualRegionObserver() {
		ObservablePicture pic = TestUtils.getObservablePicture(500, 500);
		Region registeredRegion = new RegionImpl(0, 100, 100, 0);
		ROIObserver notifiedObserver = new MockObserver(registeredRegion, true);
		pic.registerROIObserver(notifiedObserver, registeredRegion);
		pic.paint(0, 0, 100, 100, TestUtils.randColorPixel());
	}

	@Test
	public void testRectangularPartialIntersectObserver() {
		ObservablePicture pic = TestUtils.getObservablePicture(500, 500);
		Region registeredRegion = new RegionImpl(0, 100, 100, 0);
		Region matchingRegion = new RegionImpl(50, 100, 100, 50);
		ROIObserver notified = new MockObserver(matchingRegion, true);
		pic.registerROIObserver(notified, registeredRegion);
		pic.paint(50, 50, 400, 400, TestUtils.randColorPixel());
	}

	@Test
	public void testCircularRegionWholeIntersectObserver() {
		ObservablePicture pic = TestUtils.getObservablePicture(500, 500);
		Region registeredRegion = new RegionImpl(50, 200, 200, 50);
		Region matchingRegion = new RegionImpl( 60, 140, 140, 60);
		ROIObserver notified = new MockObserver(matchingRegion, true);
		pic.registerROIObserver(notified, registeredRegion);
		pic.paint(100, 100, 40, TestUtils.randColorPixel());
	}

	@Test
	public void testCircularRegionPartialIntersect() {
		ObservablePicture pic = TestUtils.getObservablePicture(500, 500);
		Region registeredRegion = new RegionImpl(50, 200, 200, 50);
		Region matchingRegion = new RegionImpl(140, 200, 200, 140);
		ROIObserver notified = new MockObserver(matchingRegion, true);
		pic.registerROIObserver(notified, registeredRegion);
		pic.paint(180, 180, 40, TestUtils.randColorPixel());
	}

	@Test
	public void testPictureRegionWholeIntersect() {
		ObservablePicture pic = TestUtils.getObservablePicture(500, 500);
		Region matchRegion = new RegionImpl(10, 30, 40, 5);
		Region registeredRegion = new RegionImpl(0, 100, 100, 0);
		Picture pictureRegion = new MutablePixelArrayPicture(TestUtils.randColorRectangle(30, 25), "Cyka Blyat");
		ROIObserver notified = new MockObserver(matchRegion, true);
		pic.registerROIObserver(notified, registeredRegion);
		pic.paint(10, 5, pictureRegion);
	}

	@Test
	public void testPictureRegionPartialIntersect() {
		ObservablePicture pic = TestUtils.getObservablePicture(500, 500);
		Region registeredRegion = new RegionImpl(0, 100, 100, 0);
		Region matchingRegion = new RegionImpl(50, 100, 100, 50);
		Picture paintRegion = new MutablePixelArrayPicture(TestUtils.randColorRectangle(200, 200), "Billy Herrington");
		ROIObserver notified = new MockObserver(matchingRegion, true);
		pic.registerROIObserver(notified, registeredRegion);
		pic.paint(50, 50, paintRegion);
	}

}
