package a6test.hannahru;

import static org.junit.Assert.assertEquals;

public class A6Tests {

	Pixel blue = new ColorPixel(0, 0, 1);
	Pixel[][] blueArr = { { blue, blue, blue }, { blue, blue, blue }, { blue, blue, blue } };
	Picture bluePic = new MutablePixelArrayPicture(blueArr, "This is my caption");
	Region reg1 = new RegionImpl(0,0,2,2);
	Region reg2 = new RegionImpl(0,0,1,1);
	ObservablePicture blueObser = new ObservablePictureImpl(bluePic);
	ROIObserver observer = new ROIObserverImpl();
	@Test
	public void registerTest() {
		blueObser.registerROIObserver(observer, reg1);
		blueObser.registerROIObserver(observer, reg2);
		blueObser.registerROIObserver(observer, reg2);
		assertEquals(blueObser.findROIObservers(reg1).length, 3);
		assertEquals(blueObser.findROIObservers(reg2).length, 3);
	}
	
	Pixel green = new ColorPixel(0.0,1.0,0.0);
	Pixel red = new ColorPixel(1.0,0.0,0.0);
	@Test
	public void rectanglePaintTest() {
		Pixel[][] greenarr = new Pixel [6][6];
		for (int i=0;i < greenarr.length;i++) {
			for (int j=0;j < greenarr[i].length;j++) {
				greenarr[i][j] = green;
			}
		}
		Picture greenPic = new MutablePixelArrayPicture(greenarr, "this is my caption");
		ObservablePicture obserGreen =new ObservablePictureImpl(greenPic);
		Picture greenRec = obserGreen.paint(0, 0, 3, 3, red);
		assertEquals(greenRec, obserGreen);
		
	}
}
