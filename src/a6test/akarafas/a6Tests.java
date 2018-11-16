package a6test.akarafas;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import a6.*;
import a6.RegionImpl;

public class a6Tests {

	@Test
	public void negativeUnionTest() {
		Region negativeA = new RegionImpl(-10,-10,-5,-5);
		Region negativeB = new RegionImpl(-20,-10,-10,-5);
		System.out.println(negativeB.union(negativeA).getLeft());
		Region aUnionB = new RegionImpl(-20,-10,-10,-5);
		assertEquals(aUnionB.getLeft(), negativeB.union(negativeA).getLeft());
	}
	
	@Test
	public void RegionImplGetters() {
		Region checkThisRegion = new RegionImpl(1546,2343,3454,4564);
		assertEquals(1546, checkThisRegion.getLeft());
		assertEquals(2343, checkThisRegion.getTop());
		assertEquals(3454, checkThisRegion.getRight());
		assertEquals(4564, checkThisRegion.getBottom());
	}
	
	@Test
	public void ROIObserverImplGetter() {
		Region r = new RegionImpl(1,2,3,4);
		ROIObserver observingR = new ROIObserverImpl(r);
		assertEquals(r, observingR.getRegion());
		
	}
	
}
