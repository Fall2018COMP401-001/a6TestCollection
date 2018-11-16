package a6test.shilong;

import org.junit.Test;

public class TestSuspendedObserver {

	@Test
	public void testSuspension() {
		ObservablePicture pic = TestUtils.getObservablePicture(500, 500);
		Region registeredRegion = new RegionImpl(0, 100, 100, 0);
		Region matchingRegion = new RegionImpl(25, 55, 55, 25);
		ROIObserver notified = new MockObserver(matchingRegion, true);
		pic.registerROIObserver(notified, registeredRegion);
		pic.suspendObservable();
		pic.paint(25, 25, 45, 40, TestUtils.randColorPixel());
		pic.paint(45, 40, 10, TestUtils.randColorPixel());
		pic.paint(25, 55, 30, 50, TestUtils.randColorPixel());
		pic.resumeObservable();
	}

}
