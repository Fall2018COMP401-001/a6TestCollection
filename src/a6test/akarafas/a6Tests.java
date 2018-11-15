package a6test.akarafas;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import a6.Region;
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
	
}
