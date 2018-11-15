package a6test.crisingc;

public class Main {

	public static void main(String[] args) {
		
		ColorPixel red = new ColorPixel(1, 0, 0);
		
		Pixel[][] pixArray = new Pixel[5][5];
			for (int i = 0; i < pixArray.length; i++) {
				for (int j = 0; j < pixArray[0].length; j++) {
					pixArray[i][j] = red;
				}
			}
			
		Picture picMute = new MutablePixelArrayPicture(pixArray, "picMute");
		Picture picImmute = new ImmutablePixelArrayPicture(pixArray, "picImmute");
		
		ObservablePicture obPic1 = new ObservablePictureImpl(picMute);
		ObservablePicture obPic2 = new ObservablePictureImpl(picImmute);
		
		ROIObserver o = new ROIObserverImpl("o");
		Region r = new RegionImpl(1, 1, 3, 3);
		
		obPic1.registerROIObserver(o, r);
		obPic1.paint(1,1,red);
		
		obPic2.registerROIObserver(o, r);
		obPic2.paint(1, 1, red);
		obPic2.unregisterROIObservers(r);
		obPic2.paint(1, 1, red, .05);
		obPic2.registerROIObserver(o, r);
		obPic2.unregisterROIObserver(o);
		obPic2.paint(1, 1, red);
		
	}

}
