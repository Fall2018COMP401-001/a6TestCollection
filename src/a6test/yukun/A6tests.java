package a6test.yukun;

import static org.junit.Assert.*;
import a6.*;

import org.junit.Test;

public class A6tests {

    @Test
    public void testInvalidRegionImplConstructure() {
	try {
	    Region r1 = new RegionImpl(1, 0, 0, 0);
	    Region r2 = new RegionImpl(0, 1, 1, 0);
	    Region r3 = new RegionImpl(2, 1, 1, 0);
	    fail("Top is larger than Bottom or Left is larger than Right");
	} catch (IllegalArgumentException e) {

	}
	
	Region r4 = new RegionImpl(0, 0, 6, 6);
	Region r5 = new RegionImpl(1, 2, 3, 4);
	Region r6 =new RegionImpl(0, 8, 9, 10);
	Region r7= new RegionImpl(8, 7, 10, 9); 
	
    }

    @Test
    public void testRegionIntersection() {
	 Region r1 = new RegionImpl(0, 0, 1, 1);
	 Region r2 = new RegionImpl(2, 2, 3, 3);
	 Region r3 = null;
	try {
	    r1.intersect(r2);
	    r1.intersect(r3);
	    r2.intersect(r3);
	    fail("No Intersection, you should throw the exception");
	} catch (NoIntersectionException e) {

	}
	
	Region r4= new RegionImpl(0, 0, 8, 8);
	Region r5= new RegionImpl(0,0,2,2);
	Region r6= new RegionImpl(2, 2, 4, 4);
	Region r7= new RegionImpl(8, 8, 10, 10);
	Region r8=new RegionImpl(1, 1, 6, 7);
	
	try {
	    assertTrue(testEqualReion(r4.intersect(r5), r5));
	    assertTrue(testEqualReion(r4.intersect(r6),r6));
	    assertTrue(testEqualReion(r4.intersect(r8),r8));
	    assertTrue(testEqualReion(r4.intersect(r7),new RegionImpl(8,8,8,8)));
	    assertTrue(testEqualReion(r5.intersect(r6),new RegionImpl(2,2,2,2)));
	    assertTrue(testEqualReion(r5.intersect(r8),new RegionImpl(1,1,2,2)));
	    assertTrue(testEqualReion(r6.intersect(r8),new RegionImpl(2,2,4,4)));
	    assertTrue(testEqualReion(r7.intersect(r8),new RegionImpl(8,8,6,7)));
	    
	    
	} catch (NoIntersectionException e) {
	    
	}
	
	
	
	
	
	
    }
    @Test
    public void testUnion() {
	Region r1= new RegionImpl(0, 0, 8, 8);
	Region r2= new RegionImpl(0,0,2,2);
	Region r3= new RegionImpl(2, 2, 4, 4); 
	
	assertTrue(testEqualReion(r1.union(r2),r1));
	assertTrue(testEqualReion(r1.union(r3),r1));
	assertTrue(testEqualReion(r2.union(r3),new RegionImpl(0, 0, 4, 4)));
	assertTrue(testEqualReion(r3.union(null), r3));
    }
    
    @Test
    public void testRegister() {
	ROIObserver o1 = new ROIObserverImpl();
	ROIObserver o2 = new ROIObserverImpl();
	ROIObserver o3 = new ROIObserverImpl();
	
	Region r1= new RegionImpl(0, 0, 2, 2);
	Region r2= new RegionImpl(1, 1, 3, 3);
	Region r3= new RegionImpl(4, 4, 4, 4);
	
	Pixel[][] pixels= new Pixel[5][5];
	for(int i=0; i<5;i++) {
	    for(int j=0; j<5;j++) {
		pixels[i][j]=Pixel.BLACK;
	    }
	}
	Picture pic = new MutablePixelArrayPicture(pixels, "Black is color of my true love's hair");
	
	ObservablePicture observablePicture= new ObservablePictureImpl(pic);
	
	observablePicture.registerROIObserver(o1, r1);
	observablePicture.registerROIObserver(o2, r2);
	observablePicture.registerROIObserver(o3, r3);
	
	assertEquals(observablePicture.findROIObservers(r1).length,2);
	assertTrue(observablePicture.findROIObservers(r2).length==2);
	assertTrue(observablePicture.findROIObservers(r3).length==1);
	
	
    }
    
    @Test
    public void testUnRegister() {
	ROIObserver o1 = new ROIObserverImpl();
	ROIObserver o2 = new ROIObserverImpl();
	ROIObserver o3 = new ROIObserverImpl();
	
	Region r1= new RegionImpl(0, 0, 2, 2);
	Region r2= new RegionImpl(1, 1, 3, 3);
	Region r3= new RegionImpl(4, 4, 4, 4);
	
	Pixel[][] pixels= new Pixel[5][5];
	for(int i=0; i<5;i++) {
	    for(int j=0; j<5;j++) {
		pixels[i][j]=Pixel.WHITE;
	    }
	}
	Picture pic = new MutablePixelArrayPicture(pixels, "White Xmas is coming");
	
	ObservablePicture observablePicture= new ObservablePictureImpl(pic);
	
	observablePicture.registerROIObserver(o1, r1);
	observablePicture.registerROIObserver(o1, r2);
	observablePicture.registerROIObserver(o1, r3);
	observablePicture.registerROIObserver(o2, r1);
	observablePicture.registerROIObserver(o2, r2);
	observablePicture.registerROIObserver(o3, r3);
	
	assertEquals(observablePicture.findROIObservers(r1).length, 4);
	assertEquals(observablePicture.findROIObservers(new RegionImpl(0, 0, 5, 5)).length, 6);
	
	observablePicture.unregisterROIObserver(o1);
	assertEquals(observablePicture.findROIObservers(r1).length, 2);
	
	
	observablePicture.unregisterROIObservers(r2);
	assertEquals(observablePicture.findROIObservers(r1).length, 0);
	assertEquals(observablePicture.findROIObservers(r2).length, 0);
	assertEquals(observablePicture.findROIObservers(r3).length, 1);
	
    }
    
    @Test
    public void testFindRegister() {
	ROIObserver o1 = new ROIObserverImpl();
	ROIObserver o2 = new ROIObserverImpl();
	ROIObserver o3 = new ROIObserverImpl();
	
	Region r1= new RegionImpl(0, 0, 2, 2);
	Region r2= new RegionImpl(1, 1, 3, 3);
	Region r3= new RegionImpl(4, 4, 4, 4);
	
	Pixel[][] pixels= new Pixel[5][5];
	for(int i=0; i<5;i++) {
	    for(int j=0; j<5;j++) {
		pixels[i][j]=new ColorPixel(1, 0, 0);
	    }
	}
	Picture pic = new MutablePixelArrayPicture(pixels, "Wrapped in Red");
	
	ObservablePicture observablePicture= new ObservablePictureImpl(pic);
	
	observablePicture.registerROIObserver(o1, r1);
	observablePicture.registerROIObserver(o1, r2);
	observablePicture.registerROIObserver(o1, r3);
	observablePicture.registerROIObserver(o3, r3);
	
	assertEquals(observablePicture.findROIObservers(r1)[0],o1);
	assertEquals(observablePicture.findROIObservers(r1)[1],o1);
	assertEquals(observablePicture.findROIObservers(r2)[1],o1);
	assertEquals(observablePicture.findROIObservers(r3)[0],o1);
	assertEquals(observablePicture.findROIObservers(r3)[1],o3);
	
	try {
	    observablePicture.findROIObservers(new RegionImpl(100, 100, 100, 1000));
	    observablePicture.findROIObservers(new RegionImpl(-1000, -100000, -100, -1000));
	} catch (ArrayIndexOutOfBoundsException e) {
	    // TODO: handle exception
	}
    }
    
    
	
	
    
    public static boolean testEqualReion(Region a, Region b) {
	if (a.getBottom()==b.getBottom()&&a.getLeft()==b.getLeft()&&
		a.getRight()==b.getRight()&&a.getTop()==b.getTop()) {
	    return true;
	} else {
	
	
	
	return false;
	
    }
    
    }
}
