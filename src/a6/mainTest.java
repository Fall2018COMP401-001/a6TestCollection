package a6;


public class mainTest {

	public static void main(String[] args) {
		
		Region a = new RegionImpl(0, 0, 5, 5);
		Region b = new RegionImpl(4, 4, 8, 8);
		
		Pixel red = new ColorPixel(1, 0, 0);
		Pixel green = new ColorPixel(0, 1, 0);
		Pixel blue = new ColorPixel(0, 0, 1);
		Pixel[][] redPArray = {{red, red, red, red, red, red, red, red, red},
				{red, red, red, red, red, red, red, red, red},
				{red, red, red, red, red, red, red, red, red},
				{red, red, red, red, red, red, red, red, red},
				{red, red, red, red, red, red, red, red, red},
				{red, red, red, red, red, red, red, red, red},
				{red, red, red, red, red, red, red, red, red},
				{red, red, red, red, red, red, red, red, red},
				{red, red, red, red, red, red, red, red, red},
				};

		Pixel[][] greenPArray = {{green, green, green}, 
				{green, green, green},
				{green, green, green}};
		Picture p1 = new ImmutablePixelArrayPicture(redPArray, "test");
		Picture p2 = new ImmutablePixelArrayPicture(greenPArray, "test2");
		ROIObserver o1 = new ROIObserverImpl();
		ROIObserver o2 = new ROIObserverImpl();
		
		ObservablePicture op1 = new ObservablePictureImpl(p1);
		op1.registerROIObserver(o1, a);
		op1.registerROIObserver(o1, b);
		op1.registerROIObserver(o2, a);
		
		op1.paint(5, 5, blue);
		
		System.out.println();
		
		op1.unregisterROIObservers(a);
		
		op1.paint(5, 5, green);
		
		op1.registerROIObserver(o1, a);
		op1.registerROIObserver(o2, a);
		op1.registerROIObserver(o1, b);
		
		System.out.println();
		
		op1.paint(5, 5, blue);
		
		System.out.println();
		
		op1.suspendObservable();
		
		op1.paint(2, 2, green);
		op1.paint(6, 6, green);
		op1.paint(8, 8, green);
		
		op1.resumeObservable();
		
		System.out.println();
		
		op1.suspendObservable();
		op1.paint(0, 0, p2);
		op1.paint(4, 4, p2);
		op1.resumeObservable();
	}

}
