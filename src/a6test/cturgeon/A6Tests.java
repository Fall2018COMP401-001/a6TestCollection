package a6.cturgeon;

import static org.junit.Assert.*;

import org.junit.Test;

import a6.*;

public class A6Tests {

	@Test
	public void observerTest() {
		Pixel[][] pArray = { { Pixel.BLACK, Pixel.BLACK, Pixel.BLACK } };
		Picture paintedPic = new MutablePixelArrayPicture(pArray, "caption");
		Region r = new RegionImpl(0, 0, 0, 0);
		ROIObserver observer = new ROIObserverImpl();

		ObservablePictureImpl pic1 = new ObservablePictureImpl(paintedPic);
		pic1.registerROIObserver(observer, r);
		assertNotEquals(observer, pic1.findROIObservers(r));
	}
}