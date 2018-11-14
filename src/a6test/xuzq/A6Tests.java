package a6test.xuzq;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import a6.*;


class A6Tests {
	
	//legal regions
	Region square_region=new RegionImpl(3,3,6,6);
	Region dot_region=new RegionImpl (4,4,4,4);
	Region rec_region=new RegionImpl (2,3,5,10);
	Region out_region=new RegionImpl (11,11,13,12);
	Region null_region=null;
	
	//Observable pictures
	Pixel red =new ColorPixel(1.0,0.0,0.0);
	Pixel blue=new ColorPixel (0.0,0.0,1.0);
	Pixel green=new ColorPixel(0.0,1.0,0.0);
	
	
	
	//Observers
	ROIObserver observer_1=new ROIObserverImpl();
	
		
	//test RegionImpl throw correct exceptions
	@Test
	void testIllegalRegions() {
		//Illegal regions
		try {
			Region illegal_region_1 =new RegionImpl (4,1,1,4);
			Region illegal_region_2=new RegionImpl (1,4,4,1);
			fail ("illegal region starting points");
		} catch (IllegalArgumentException e) {
			
		}
	}
	
	//test RegionImpl's intersect methods
	@Test
	void testRegionImplIntersectMethod() {
		//intersect
		Region intersect_1=new RegionImpl(4,4,4,4);
		Region intersect_2=new RegionImpl(3,3,5,6);
		try {
			Region intersect_sd=square_region.intersect(dot_region);
			Region intersect_sr=square_region.intersect(rec_region);

			assertEquals (true, regionsEqual(intersect_1, intersect_sd));
			assertEquals (true, regionsEqual(intersect_2, intersect_sr));
		} catch (NoIntersectionException e) {
			
		}
		

		//no intersect		
		try {
			Region no_intersect_1=square_region.intersect(null_region);
			Region no_intersect_2=square_region.intersect(out_region);
			
			fail("should throw exception when no intersect");
		} catch (NoIntersectionException e) {
		}
		
	}
	
	//test RegionImpl's union methods
		@Test
		void testRegionImplUniontMethod() {
			//union
			Region union_1=new RegionImpl(3,3,6,6);
			Region union_2=new RegionImpl(2,3,6,10);
			Region union_3=new RegionImpl(3,3,13,12);
			
			Region union_sd=square_region.union(dot_region);
			Region union_sr=square_region.union(rec_region);
			Region union_so=square_region.union(out_region);
			Region union_sn=square_region.union(null_region);
						
			assertEquals (true, regionsEqual(union_2, union_sr));
			assertEquals (true, regionsEqual(union_3, union_so));
			assertEquals (true, regionsEqual(union_1, union_sn));
			assertEquals (true, regionsEqual(union_1, union_sd));
			
		}
	
	//test Observable picture register
	@Test
	void testRegisterObservers(){
		Pixel[][] red_array=new Pixel [12][12];
		for (int i=0;i<red_array.length;i++) {
			for (int j=0;j<red_array[i].length;j++) {
				red_array[i][j]=red;
			}
		}
		Picture mutable_red=new MutablePixelArrayPicture(red_array, "mutable red picture");
		ObservablePicture observable=new ObservablePictureImpl(mutable_red);
		
		observable.registerROIObserver(observer_1, square_region);
		observable.registerROIObserver(observer_1, out_region);
		observable.registerROIObserver(observer_1, square_region);
		
		ROIObserver[] observer_array_square=observable.findROIObservers(square_region);
		assertEquals(2,observer_array_square.length);
		assertEquals(observer_1, observer_array_square[0]);
		assertEquals(observer_1, observer_array_square[1]);
		
		ROIObserver[] observer_array_out=observable.findROIObservers(out_region);
		assertEquals(1,observer_array_out.length);
		assertEquals(observer_1, observer_array_out[0]);
		
		
		
	}
	
	//test unregister methods in observable picture
	@Test
	void testUnregisterObservers() {
		Pixel[][] red_array=new Pixel [12][12];
		for (int i=0;i<red_array.length;i++) {
			for (int j=0;j<red_array[i].length;j++) {
				red_array[i][j]=red;
			}
		}
		Picture mutable_red=new MutablePixelArrayPicture(red_array, "mutable red picture");
		ObservablePicture observable=new ObservablePictureImpl(mutable_red);
		
		observable.registerROIObserver(observer_1, square_region);
		observable.registerROIObserver(observer_1, out_region);
		observable.registerROIObserver(observer_1, square_region);
		
		//test unregister all registrations with one observer
		observable.unregisterROIObserver(observer_1);
		assertEquals(0,observable.findROIObservers(out_region).length);
		assertEquals(0, observable.findROIObservers(square_region).length);
		
		observable.registerROIObserver(observer_1, square_region);
		observable.registerROIObserver(observer_1, out_region);
		
		//test unregister a region
		observable.unregisterROIObservers(square_region);
		assertEquals(1,observable.findROIObservers(out_region).length);
		assertEquals(observer_1,observable.findROIObservers(out_region)[0]);
		assertEquals(0,observable.findROIObservers(square_region).length);
		
	}
	
	//paint
	@Test
	void testPaint() {
		
		//no matter whether the source is mutable or immutable, observables are mutable
		Pixel[][] red_array=new Pixel [12][12];
		for (int i=0;i<red_array.length;i++) {
			for (int j=0;j<red_array[i].length;j++) {
				red_array[i][j]=red;
			}
		}
		Picture mutable_red=new MutablePixelArrayPicture(red_array, "mutable red picture");
		ObservablePicture mutable_observable=new ObservablePictureImpl(mutable_red);
		Picture painted_mutable_observable=mutable_observable.paint(3, 5, green);
		assertEquals(painted_mutable_observable, mutable_observable);

		Picture immutable_red=new ImmutablePixelArrayPicture(red_array, "immutable red picture");
		ObservablePicture immutable_observable=new ObservablePictureImpl(mutable_red);
		Picture painted_immutable_observable=immutable_observable.paint(3, 5, green);
		assertEquals(painted_mutable_observable, mutable_observable);
		
	}
	
	@Test
	void testCaption() {
		Pixel[][] red_array=new Pixel [12][12];
		for (int i=0;i<red_array.length;i++) {
			for (int j=0;j<red_array[i].length;j++) {
				red_array[i][j]=red;
			}
		}
		Picture mutable_red=new MutablePixelArrayPicture(red_array, "mutable red picture");
		ObservablePicture mutable_observable=new ObservablePictureImpl(mutable_red);
		//get caption
		assertEquals("mutable red picture", mutable_observable.getCaption());
		//set caption
		mutable_observable.setCaption("new caption");
		assertEquals("new caption", mutable_observable.getCaption());
		
		
	}
	
	
	private boolean regionsEqual(Region r1, Region r2) {
		if (r1.getBottom()==r2.getBottom()&&r1.getLeft()==r2.getLeft()&& r1.getRight()==r2.getRight()&&r1.getTop()==r2.getTop()) {
			return true;
		} else {
			return false;
		}
	}

}
