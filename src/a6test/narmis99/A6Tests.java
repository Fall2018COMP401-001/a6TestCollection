package a6test.narmis99;

import static org.junit.Assert.*;

import org.junit.Test;

import a6.Region;
import a6.RegionImpl;

public class A6Tests {

	@Test
	public void testRegionConstructor() {
		Region reg;
		Region other;
		Region another;
		
		try {
			another = new RegionImpl(1, 3, 5, -7);
		}
		catch (IllegalArgumentException e) {
			
		}
		
		try {
			reg = new RegionImpl(5, 2, 3, 3);
			other = new RegionImpl(1, 5, 3, 3);
		}
		catch (IllegalArgumentException e) {
			
		}
		
	}

}
