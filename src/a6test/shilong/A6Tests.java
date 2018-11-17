package a6test.shilong;

import org.junit.Assert;
import org.junit.Test;

public class A6Tests {

	@Test
	public void testPointIntersection() {
		Region region = new RegionImpl(0, 0, 3, 3);
		Region point = new RegionImpl(1, 1, 1, 1);
		try {
			Region intersection = region.intersect(point);
			Region equivIntersection = point.intersect(region);
			Assert.assertTrue("Intersection should be commutative",
				TestUtils.regionEquals(intersection, equivIntersection));
			Assert.assertEquals(1, intersection.getLeft());
			Assert.assertEquals(1, intersection.getRight());
			Assert.assertEquals(1, intersection.getBottom());
			Assert.assertEquals(1, intersection.getTop());
		} catch (NoIntersectionException e) {
			Assert.fail("The regions do intersect");
		}
	}

	@Test
	public void testRegionFullIntersection() {
		Region wholeRegion = new RegionImpl(0, 0, 100, 100);
		Region subRegion = new RegionImpl(10, 5, 40, 50);
		try {
			Region intersection = wholeRegion.intersect(subRegion);
			Region equivIntersection = subRegion.intersect(wholeRegion);
			Assert.assertTrue("Intersection should be commutative",
				TestUtils.regionEquals(intersection, equivIntersection));
			Assert.assertEquals(10, intersection.getLeft());
			Assert.assertEquals(5, intersection.getTop());
			Assert.assertEquals(40, intersection.getRight());
			Assert.assertEquals(50, intersection.getBottom());
		} catch (NoIntersectionException e) {
			Assert.fail("The regions do intersect");
		}
	}

	@Test
	public void testPartialIntersection() {
		Region region1 = new RegionImpl(0, 0, 100, 100);
		Region region2 = new RegionImpl(50, 50, 110, 120);
		try {
			Region intersection = region1.intersect(region2);
			Region equivIntersection = region2.intersect(region1);
			Assert.assertTrue("Intersection should be commutative",
				TestUtils.regionEquals(intersection, equivIntersection));
			Assert.assertEquals(50, intersection.getLeft());
			Assert.assertEquals(50, intersection.getTop());
			Assert.assertEquals(100, intersection.getRight());
			Assert.assertEquals(100, intersection.getBottom());
		} catch (NoIntersectionException e) {
			Assert.fail("The regions do intersect");
		}
	}

	@Test(expected = NoIntersectionException.class)
	public void testNoPointIntersect() throws NoIntersectionException {
		Region region = new RegionImpl(100, 0, 200, 200);
		Region point = new RegionImpl(1000, 1000, 1000, 1000);
		region.intersect(point);
	}

	@Test(expected = NoIntersectionException.class)
	public void testNoRegionIntersect() throws NoIntersectionException {
		Region region = new RegionImpl(0, 0, 100, 100);
		Region noIntersect = new RegionImpl(200, 200, 400, 400);
		region.intersect(noIntersect);
	}

	@Test(expected = NoIntersectionException.class)
	public void testNullIntersect() throws NoIntersectionException {
		Region region = new RegionImpl(0, 0, 100, 100);
		region.intersect(null);
	}

	@Test
	public void testInclusiveUnion() {
		Region wholeRegion = new RegionImpl(0, 0, 100, 100);
		Region subRegion = new RegionImpl(10, 5, 40, 50);
		Region union = wholeRegion.union(subRegion);
		Region unionEquiv = subRegion.union(wholeRegion);
		Assert.assertTrue("Union should be commutative",
			TestUtils.regionEquals(union, unionEquiv));
		Assert.assertEquals(union, wholeRegion);
	}

	@Test
	public void testPartialUnion() {
		Region region1 = new RegionImpl(0, 0, 100, 100);
		Region region2 = new RegionImpl(50, 50, 110, 120);
		Region union = region1.union(region2);
		Region unionEquiv = region2.union(region1);
		Assert.assertTrue("Union should be commutative",
			TestUtils.regionEquals(union, unionEquiv));
		Assert.assertEquals(0, union.getLeft());
		Assert.assertEquals(0, union.getTop());
		Assert.assertEquals(110, union.getRight());
		Assert.assertEquals(120, union.getBottom());
	}

	@Test
	public void testSeperatedUnion() {
		Region region = new RegionImpl(0, 0, 100, 100);
		Region noIntersect = new RegionImpl(200, 200, 400, 400);
		Region union = region.union(noIntersect);
		Region unionEquiv = noIntersect.union(region);
		Assert.assertTrue("Union should be commutative",
			TestUtils.regionEquals(union, unionEquiv));
		Assert.assertEquals(0, union.getLeft());
		Assert.assertEquals(0, union.getTop());
		Assert.assertEquals(400, union.getRight());
		Assert.assertEquals(400, union.getBottom());
	}

	@Test
	public void testNullUnion() {
		Region region = new RegionImpl(0, 0, 100, 100);
		Region self = region.union(null);
		Assert.assertTrue("region.union(null) should have the same region as itself",
			TestUtils.regionEquals(region, self));
		Assert.assertSame(region, self);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testValidateBoundaries() {
		Region illegal = new RegionImpl(200, 0, 0, 100);
	}

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
		Region registeredRegion = new RegionImpl(10, 5, 40, 30);
		Region registeredNonMatchRegion = new RegionImpl(0, 0, 5, 5);
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
		Region matchRegion = new RegionImpl(10, 5, 40, 30);
		Region registeredRegion = new RegionImpl(0, 0, 100, 100);
		ROIObserver notifiedObserver = new MockObserver(matchRegion, true);
		pic.registerROIObserver(notifiedObserver, registeredRegion);
		pic.paint(10, 30, 40, 5, TestUtils.randColorPixel());
	}

	@Test
	public void testRectangularEqualRegionObserver() {
		ObservablePicture pic = TestUtils.getObservablePicture(500, 500);
		Region registeredRegion = new RegionImpl(0, 0, 100, 100);
		ROIObserver notifiedObserver = new MockObserver(registeredRegion, true);
		pic.registerROIObserver(notifiedObserver, registeredRegion);
		pic.paint(0, 0, 100, 100, TestUtils.randColorPixel());
	}

	@Test
	public void testRectangularPartialIntersectObserver() {
		ObservablePicture pic = TestUtils.getObservablePicture(500, 500);
		Region registeredRegion = new RegionImpl(0, 0, 100, 100);
		Region matchingRegion = new RegionImpl(50, 50, 100, 100);
		ROIObserver notified = new MockObserver(matchingRegion, true);
		pic.registerROIObserver(notified, registeredRegion);
		pic.paint(50, 50, 400, 400, TestUtils.randColorPixel());
	}

	@Test
	public void testCircularRegionWholeIntersectObserver() {
		ObservablePicture pic = TestUtils.getObservablePicture(500, 500);
		Region registeredRegion = new RegionImpl(50, 50, 200, 200);
		Region matchingRegion = new RegionImpl(60, 60, 140, 140);
		ROIObserver notified = new MockObserver(matchingRegion, true);
		pic.registerROIObserver(notified, registeredRegion);
		pic.paint(100, 100, 40, TestUtils.randColorPixel());
	}

	@Test
	public void testCircularRegionPartialIntersect() {
		ObservablePicture pic = TestUtils.getObservablePicture(500, 500);
		Region registeredRegion = new RegionImpl(50, 50, 200, 200);
		Region matchingRegion = new RegionImpl(140, 140, 200, 200);
		ROIObserver notified = new MockObserver(matchingRegion, true);
		pic.registerROIObserver(notified, registeredRegion);
		pic.paint(180, 180, 40, TestUtils.randColorPixel());
	}

	@Test
	public void testPictureRegionWholeIntersect() {
		ObservablePicture pic = TestUtils.getObservablePicture(500, 500);
		Region matchRegion = new RegionImpl(10, 5, 39, 29);
		Region registeredRegion = new RegionImpl(0, 0, 100, 100);
		Picture pictureRegion = new MutablePixelArrayPicture(TestUtils.randColorRectangle(30, 25),
			"Cyka Blyat");
		ROIObserver notified = new MockObserver(matchRegion, true);
		pic.registerROIObserver(notified, registeredRegion);
		pic.paint(10, 5, pictureRegion);
	}

	@Test
	public void testPictureRegionPartialIntersect() {
		ObservablePicture pic = TestUtils.getObservablePicture(500, 500);
		Region registeredRegion = new RegionImpl(0, 0, 100, 100);
		Region matchingRegion = new RegionImpl(50, 50, 100, 100);
		Picture paintRegion = new MutablePixelArrayPicture(TestUtils.randColorRectangle(200, 200),
			"Billy Herrington");
		ROIObserver notified = new MockObserver(matchingRegion, true);
		pic.registerROIObserver(notified, registeredRegion);
		pic.paint(50, 50, paintRegion);
	}

	@Test
	public void testSuspension() {
		ObservablePicture pic = TestUtils.getObservablePicture(500, 500);
		Region registeredRegion = new RegionImpl(0, 0, 100, 100);
		Region matchingRegion = new RegionImpl(25, 25, 55, 55);
		ROIObserver notified = new MockObserver(matchingRegion, true);
		pic.registerROIObserver(notified, registeredRegion);
		pic.suspendObservable();
		pic.paint(25, 25, 45, 40, TestUtils.randColorPixel());
		pic.paint(45, 40, 10, TestUtils.randColorPixel());
		pic.paint(25, 55, 30, 50, TestUtils.randColorPixel());
		pic.resumeObservable();
	}
}
