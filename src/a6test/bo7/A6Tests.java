package a6test.bo7;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import a6.*;

class A6Tests {
	Pixel Grey = new GrayPixel(0.5);
	Pixel Green = new ColorPixel(0,1,0);
	Pixel Blue = new ColorPixel(0,0,1);
	
	Picture GreenMutable4by4 = new MutablePixelArrayPicture(new Pixel[][] {{Green,Green,Green,Green},{Green,Green,Green,Green},{Green,Green,Green,Green},{Green,Green,Green,Green}},"Green");
	Picture GreenImutable4by4 = new ImmutablePixelArrayPicture(new Pixel[][] {{Green,Green,Green,Green},{Green,Green,Green,Green},{Green,Green,Green,Green},{Green,Green,Green,Green}},"Green");

	Region region2135 = new RegionImpl(2,1,3,5);
	Region region2244 = new RegionImpl(2,2,4,4);
	Region region1133 = new RegionImpl(1,1,3,3);
	Region region2021 = new RegionImpl(2,0,2,1);
	Region region1122 = new RegionImpl(1,1,2,2);
	Region region0011 = new RegionImpl(0,0,1,1);
	
	ObservablePicture observableGreenMutable4by4 = new ObservablePictureImpl(GreenMutable4by4);
	ObservablePicture observableGreenImutable4by4 = new ObservablePictureImpl(GreenImutable4by4);
	
	ROIObserver observer1 = new ROIObserverImpl();
	ROIObserver observer2 = new ROIObserverImpl();
	ROIObserver observer3 = new ROIObserverImpl();
	
	@Test
	public void testPictureImplGetters() {
		assertEquals(4,observableGreenImutable4by4.getWidth());
		assertEquals(4,observableGreenMutable4by4.getHeight());
		assertEquals("Green",observableGreenMutable4by4.getCaption());
	}
	
	@Test
	public void testRegisterandFindObservers() {
		observableGreenMutable4by4.registerROIObserver(observer1, region1133);
		observableGreenMutable4by4.registerROIObserver(observer1, region2021);
		observableGreenMutable4by4.registerROIObserver(observer2, region2021);
		observableGreenMutable4by4.registerROIObserver(observer2, region2244);
		
		assertEquals(4,observableGreenMutable4by4.findROIObservers(region1122).length);
		assertEquals(observer1, observableGreenMutable4by4.findROIObservers(region1122)[0]);
		assertEquals(observer1, observableGreenMutable4by4.findROIObservers(region1122)[1]);
		
		assertEquals(1,observableGreenMutable4by4.findROIObservers(region0011).length);
		assertEquals(observer1,observableGreenMutable4by4.findROIObservers(region0011)[0]);
	}
	
	@Test
	public void testUnregister() {
		observableGreenMutable4by4.registerROIObserver(observer1, region1133);
		observableGreenMutable4by4.registerROIObserver(observer1, region2021);
		observableGreenMutable4by4.registerROIObserver(observer2, region2021);
		
		assertEquals(3,observableGreenMutable4by4.findROIObservers(region1122).length);
		observableGreenMutable4by4.unregisterROIObserver(observer2);
		assertEquals(2,observableGreenMutable4by4.findROIObservers(region1122).length);
		
		observableGreenMutable4by4.unregisterROIObserver(observer1);
		assertEquals(0,observableGreenMutable4by4.findROIObservers(region1122).length);
	}
	
	@Test
	public void testPaintMethod() {
		testPixelEquality(Green,observableGreenMutable4by4.getPixel(0, 0));
		
		observableGreenMutable4by4.paint(0, 0, Blue);
		testPixelEquality(Blue,observableGreenMutable4by4.getPixel(0, 0));
		
		observableGreenMutable4by4.paint(0, 0, 2, 2, Grey);
		for (int i=0;i<=1;i++)
			for (int j=0;j<=1;j++) {
				testPixelEquality(Grey,observableGreenMutable4by4.getPixel(i, j));
			}
	}
	
	@Test
	public void testMutableImmutable() {
		Picture testImmutable = observableGreenImutable4by4.paint(0, 0,Blue);
		assertEquals(testImmutable,observableGreenImutable4by4);
	}
	static boolean testPixelEquality(Pixel a, Pixel b) {
		if (a.getBlue()==b.getBlue() && a.getGreen()==b.getGreen() && a.getRed() ==b.getRed()) return true;
		else return false;
	}
}