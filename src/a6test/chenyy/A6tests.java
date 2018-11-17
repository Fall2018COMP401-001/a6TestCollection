package a6test.chenyy;

import static org.junit.Assert.*;

import org.junit.Test;

import a6.*;

class A6testpaint {
	
	Region region_a = new RegionImpl(1, 1, 5, 5);
	Region region_b = new RegionImpl(2, 2, 7, 2);
	Region region_c = new RegionImpl(0,0,0,0);
	
	// Picture
	Pixel red = new ColorPixel(1, 0, 0);
	Pixel green = new ColorPixel(0, 1, 0);
	Pixel blue = new ColorPixel(0, 0, 1);
	Pixel orange = new ColorPixel(0.9, 0.6, 0.1);
	Pixel yellow = new ColorPixel(0.9, 1, 0.1);
	Pixel randomColor = new ColorPixel(0.545, 0.65, 0.332);
	
	// ROIObserver (programming to implementation since needed methods aren't in interface)
	ROIObserverImpl ROIa = new ROIObserverImpl();
	ROIObserverImpl ROIb = new ROIObserverImpl();
	Region region1 = new RegionImpl(1,1,5,5);
	Region region2 = new RegionImpl(0,0,3,3);
	Region region3 = new RegionImpl(2,2,7,7);
	
	// I added only one thing based on others work, first suspend and resume immediately

	@Test
	public void testObserverKMP() {
		Pixel[][] pixels = new Pixel[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				pixels[i][j] = red;
			}
		}
		
		Picture core = new ImmutablePixelArrayPicture(pixels, "caption");
		ObservablePicture obs = new ObservablePictureImpl(core);
		
		obs.registerROIObserver(ROIa, region1);
		obs.registerROIObserver(ROIb, region2);
		obs.registerROIObserver(ROIb, region3);
		
		//Prints 1,1,1,1, 1,1,1,1
		System.out.println("Expected output: 1,1,1,1");
		obs.paint(1, 1, green);
		System.out.println("");
		
		//Tests that obs pic changes but source pic does not
		assertFalse(core.getPixel(1, 1).equals(green));
		assertTrue(obs.getPixel(1, 1).equals(green));
		
		//Prints 1,1,4,4, 1,1,3,3, 2,2,4,4
		System.out.println("Expected output: 1,1,4,4,  1,1,3,3,  2,2,4,4");
		obs.paint(1,1,4,4, blue);
		System.out.println("");
		
		//Returns nothing
		obs.paint(8, 8, blue);
		System.out.println("Expected output: nothing");
		System.out.println("");
		
		// Returns 1,1,5,5, then 1,1,3,3, then 2,2,5,5
		System.out.println("Expected output: 1,1,5,5,  1,1,3,3,  2,2,5,5");
		obs.paint(3, 3, 2.0, red);
		System.out.println("");
		
		obs.suspendObservable();
		obs.paint(2,1, Pixel.WHITE);
		obs.paint(3, 2, Pixel.WHITE);
		obs.paint(4, 4, Pixel.WHITE);
		System.out.println("Expected output: 2,1,4,4,  2,1,3,3,  2,2,4,4");
		obs.resumeObservable();
		System.out.println("");
		
		obs.suspendObservable();
		obs.paint(2,1, Pixel.WHITE);
		obs.paint(3, 2, Pixel.WHITE);
		obs.paint(4, 4, Pixel.WHITE);
		System.out.println("Expected output: 2,1,4,4,  2,1,3,3,  2,2,4,4");
		obs.resumeObservable();
		System.out.println("");
		
		obs.suspendObservable();
		obs.resumeObservable();
	}

}
