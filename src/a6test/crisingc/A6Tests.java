package a6test.crisingc;

import static org.junit.Assert.*;

import org.junit.Test;



public class A6Tests {

	
	@Test
	public void testRegisterROIObserver() {
		
		ColorPixel red = new ColorPixel(1, 0, 0);
		
		Pixel[][] pixArray = new Pixel[5][5];
			for (int i = 0; i < pixArray.length; i++) {
				for (int j = 0; j < pixArray[0].length; j++) {
					pixArray[i][j] = red;
				}
			}
			
		Picture picMute = new MutablePixelArrayPicture(pixArray, "picMute");
		
		ObservablePictureImpl obPic1 = new ObservablePictureImpl(picMute);

		
		ROIObserver o = new ROIObserverImpl("o");
		Region r = new RegionImpl(1, 1, 3, 3);
		
		obPic1.registerROIObserver(o, r);
		assertTrue(obPic1.getObservers().contains(o));

	}
	
	public void testUnregisterObserver() {
		ColorPixel red = new ColorPixel(1, 0, 0);
		
		Pixel[][] pixArray = new Pixel[5][5];
			for (int i = 0; i < pixArray.length; i++) {
				for (int j = 0; j < pixArray[0].length; j++) {
					pixArray[i][j] = red;
				}
			}
			
		Picture picMute = new MutablePixelArrayPicture(pixArray, "picMute");
		
		ObservablePictureImpl obPic1 = new ObservablePictureImpl(picMute);
		
		ROIObserver o = new ROIObserverImpl("o");

		
		obPic1.unregisterROIObserver(o);
		assertFalse(obPic1.getObservers().contains(o));
	}

}
