package a6test.shilong;

import org.junit.Assert;

public class MockObserver implements ROIObserver {

	private Region supposedRegion;
	private boolean shouldNotify;

	public MockObserver(Region supposedRegion, boolean shouldNotify) {
		this.supposedRegion = supposedRegion;
		this.shouldNotify = shouldNotify;
	}

	@Override
	public void notify(ObservablePicture picture, Region changed_region) {
		if(shouldNotify) {
			Assert.assertTrue("Observable did not observe expected region",
				TestUtils.regionEquals(supposedRegion, changed_region));
		} else {
			Assert.fail("Observer notified even when it shouldn't be");
		}
	}
}
