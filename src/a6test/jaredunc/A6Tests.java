package a6test.jaredunc;



public class A6Tests {
	
	public void Intersectiontestbasic() throws NoIntersectionException {
		Region first = new RegionImpl(1, 1, 5, 6);
		Region second = new RegionImpl(0, 0, 10, 10);

		Region third = first.intersect(second);

		assertEquals(1, third.getLeft());
		assertEquals(1, third.getTop());
		assertEquals(5, third.getRight());
		assertEquals(6, third.getBottom());


	}
	
	public void uniontestbasic() {
		
		Region first = new RegionImpl(1, 1, 5, 6);
		Region second = new RegionImpl(0, 0, 2, 2);
		
		Region third = first.union(second);
		
		assertEquals(0, third.getLeft());
		assertEquals(0, third.getTop());
		assertEquals(5, third.getRight());
		assertEquals(6, third.getBottom());
		
	}
	
	public void throwerrorintersection() throws NoIntersectionException {
		Region first = new RegionImpl(1, 1, 5, 6);
		Region second = new RegionImpl(0, 0, 0, 0);
		
		try {
			Region third = first.union(second);
			
		} catch (NoIntersectionException badness) {
			
		}
	}
	
public void registerobservers() throws NoIntersectionException {
		
		Region a = new RegionImpl(0,1,0,1);
		Region s = a.union(a);
		Region b = new RegionImpl(0,0,4,4);
		Region c = new RegionImpl(4,4,5,6);
		Region d = new RegionImpl(0,0,3,3);
		Region e = new RegionImpl(8,8,8,9);
		Region f = new RegionImpl(0,0,3,3);
		ROIObserver w = new ROIObserverI();
		ROIObserver x = new ROIObserverI();
		ROIObserver y = new ROIObserverI();
		ROIObserver z = new ROIObserverI();

		
		
		Pixel[][] pixels = new Pixel[10][10];
		
		for (int i = 0; i < pixels.length; i++) {
			for (int j = 0; j < pixels.length; j++) {
				pixels[i][j] = new ColorPixel(Math.random(), Math.random(), Math.random());
			}
		}
		
		Picture picture = new ImmutablePixelArrayPicture(pixels, "Array");
		
		Pixel[][] pixels2 = new Pixel[10][10];
		
		for (int i = 0; i < pixels2.length; i++) {
			for (int j = 0; j < pixels2.length; j++) {
				pixels[i][j] = new ColorPixel(Math.random(), Math.random(), Math.random());
			}
		}

	
		
		ObservablePicture O = new ObservablePictureImpl(picture);
		
		O.registerROIObserver(w,a);
		O.registerROIObserver(x,a);
		O.registerROIObserver(y,c);
		O.registerROIObserver(z,e);
		O.registerROIObserver(w,d);
		O.registerROIObserver(x,f);
		O.registerROIObserver(y,e);
		O.registerROIObserver(z,a);
		
		
		
		ROIObserver[] listy = O.findROIObservers(new RegionImpl(0,0,10,10));
		assertEquals(8, listy.length);
		
		O.unregisterROIObserver(w);
		ROIObserver[] listy2 = O.findROIObservers(new RegionImpl(0,0,10,10));
		assertEquals(6, listy.length);
		
		
		O.unregisterROIObservers(new RegionImpl(0,0,10,10));
		ROIObserver[] listy3 = O.findROIObservers(new RegionImpl(0,0,10,10));
		assertEquals(0, listy.length);
		
	}
	
	
}
