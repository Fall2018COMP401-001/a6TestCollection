package a6test.sbahali;

import static org.junit.Assert.*;
import a6.*;

import org.junit.Test;

public class A6Tests {

	@Test
	public void regionTests() {
		Region r0 = new RegionImpl(0,0,0,0);
		Region r1 = new RegionImpl(1,1,1,1);
		try
		{
			r0.intersect(r1);
			fail();
		}
		catch(NoIntersectionException e)
		{
			Region r01 = new RegionImpl(0,0,1,1);
			try
			{
				assertTrue(regionEquality(r0.intersect(r01),r0));
				Region rN = null;
				try
				{
					r0.intersect(rN);
					fail();
				}
				catch(NoIntersectionException f)
				{
					
				}
			}
			catch (NoIntersectionException g)
			{
				fail();
			}
		}
	}
	private boolean regionEquality(Region r1, Region r2)
	{
		return r1.getTop()==r2.getTop() && r1.getBottom()==r2.getBottom() 
				&& r1.getLeft()==r2.getLeft() && r1.getRight() == r2.getRight();
	}
	@Test
	public void suspend()
	{
		ObservablePicture pic = new ObservablePictureImpl(new MutablePixelArrayPicture(
				pixArrayMaker(10,10),"Empty"));
		ROIObserverImpl r1 = new ROIObserverImpl();
		ROIObserverImpl r2 = new ROIObserverImpl();
		pic.registerROIObserver(r1, new RegionImpl(0,0,4,4));
		pic.registerROIObserver(r2, new RegionImpl(5,5,9,9));
		pic.suspendObservable();
		pic.paint(5, 5, 3, new ColorPixel(0.3,0.2,0.5), 1.0);
		pic.paint(7, 7, new ColorPixel(0.3,0.6,0.4));
		assertEquals(0,r1.getTimesNotified());
		assertEquals(0,r2.getTimesNotified());
		pic.resumeObservable();
		assertEquals(1,r1.getTimesNotified());
		assertEquals(1,r2.getTimesNotified());
	}
	private Pixel[][] pixArrayMaker(int x, int  y)
	{
		Pixel[][] pix = new ColorPixel[x][y];
		for(int i = 0; i<x ;i++)
		{
			for (int j = 0; j<y; j++)
			{
				pix[i][j] = new ColorPixel(Math.random(),Math.random(),Math.random());
			}
		}
		return pix;
	}
	
	@Test
	public void unregister()
	{
		ObservablePicture pic = new ObservablePictureImpl(new MutablePixelArrayPicture(
				pixArrayMaker(10,10),"Empty"));
		ROIObserverImpl r1 = new ROIObserverImpl();
		ROIObserverImpl r2 = new ROIObserverImpl();
		pic.registerROIObserver(r1, new RegionImpl(0,0,4,4));
		pic.registerROIObserver(r2, new RegionImpl(5,5,9,9));
		assertEquals(1,pic.findROIObservers(new RegionImpl(0,2,2,3)).length);
		assertEquals(2,pic.findROIObservers(new RegionImpl(0,2,8,7)).length);
		pic.unregisterROIObserver(r1);
		assertEquals(0,pic.findROIObservers(new RegionImpl(0,2,2,3)).length);
	}
}
