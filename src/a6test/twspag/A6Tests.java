package a6test.twspag;

import static org.junit.Assert.*;

import org.junit.Test;

import a6.*;



public class A6Tests {
	
	
	@Test
	public void regionConstructorTest() {
		
		try {
			Region region_1 = new RegionImpl(0, 0, 1, 1);
			Region region_2 = new RegionImpl(1, 1, 1 ,1);
		} catch (IllegalArgumentException e){
			fail("Does not accept legal arguments");
		}
		
		try {
			Region region_3 = new RegionImpl(0, 1, 1, 0);
		} catch (IllegalArgumentException e) {
			try {
			Region region_4 = new RegionImpl(1, 0, 0, 1);
			} catch (IllegalArgumentException f) {
				return;
			}
		}
		fail("Accepts illegal arguments");
	}
	
}
