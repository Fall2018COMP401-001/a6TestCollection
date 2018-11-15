package a6test.tucker17;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import a6.ColorPixel;
import a6.MutablePixelArrayPicture;
import a6.ObservablePicture;
import a6.ObservablePictureImpl;
import a6.Picture;
import a6.Pixel;
import a6.ROIObserver;
import a6.ROIObserverImpl;
import a6.RegionImpl;

public class A6Tests {
	Pixel blue = new ColorPixel(0, 0, 1);

	Picture blue3by3 = new MutablePixelArrayPicture(
			new Pixel[][] { { blue, blue, blue }, { blue, blue, blue }, { blue, blue, blue } }, "Blue");
	ObservablePicture observableBlue3by3 = new ObservablePictureImpl(blue3by3);

	ROIObserver observer1 = new ROIObserverImpl();
	ROIObserver observer2 = new ROIObserverImpl();
	ROIObserver observer3 = new ROIObserverImpl();

	Region region2133 = new RegionImpl(2, 1, 3, 3);
	Region region1122 = new RegionImpl(1, 1, 2, 2);
	Region region2122 = new RegionImpl(2, 1, 2, 2);
	Region region1133 = new RegionImpl(1, 1, 3, 3);
	Region region3031 = new RegionImpl(3, 0, 3, 1);
	Region region0000 = new RegionImpl(0, 0, 0, 0);
	Region region0021 = new RegionImpl(0, 0, 2, 1);

	@Test
	public void registerObserverTest() {
		observableBlue3by3.registerROIObserver(observer1, region0000);
		observableBlue3by3.registerROIObserver(observer1, region0000);
		observableBlue3by3.registerROIObserver(observer2, region0000);
		observableBlue3by3.registerROIObserver(observer3, region0000);

		assertEquals(observableBlue3by3.findROIObservers(region0000).length, 4);
	}

	@Test
	public void findROIObserversTest() {

		observableBlue3by3.registerROIObserver(observer1, region1133);
		observableBlue3by3.registerROIObserver(observer1, region3031);
		observableBlue3by3.registerROIObserver(observer2, region2122);
		observableBlue3by3.registerROIObserver(observer3, region3031);

		assertEquals(observableBlue3by3.findROIObservers(region1133).length, 4);
		assertEquals(observableBlue3by3.findROIObservers(region3031).length, 3);
		assertEquals(observableBlue3by3.findROIObservers(region2122).length, 2);

	}

	@Test
	public void unregisterROIObserverByRegionTest() {

		observableBlue3by3.registerROIObserver(observer1, region0000);
		observableBlue3by3.registerROIObserver(observer1, region1133);
		observableBlue3by3.registerROIObserver(observer2, region0000);
		observableBlue3by3.registerROIObserver(observer3, region0000);
		observableBlue3by3.registerROIObserver(observer3, region0021);

		observableBlue3by3.unregisterROIObservers(region0000);

		assertEquals(observableBlue3by3.findROIObservers(region0000).length, 0);
		assertEquals(observableBlue3by3.findROIObservers(region1133).length, 1);

	}

	@Test
	public void unregisterROIObserverByObserverTest() {

		observableBlue3by3.registerROIObserver(observer1, region0000);
		observableBlue3by3.registerROIObserver(observer1, region0000);
		observableBlue3by3.registerROIObserver(observer2, region0000);
		observableBlue3by3.registerROIObserver(observer3, region0000);
		observableBlue3by3.registerROIObserver(observer3, region0000);

		observableBlue3by3.unregisterROIObserver(observer3);

		assertEquals(observableBlue3by3.findROIObservers(region0000).length, 3);

	}

}