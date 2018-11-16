package a6test.shua;

import static org.junit.Assert.*;

import org.junit.Test;

import a6.*;

public class A6Tests {
	
	@Test 
	public void BasicIntersectLogic() {
		Region rOne = new RegionImpl(2,2,5,5);
		Region rTwo = new RegionImpl(3,3,4,4);
		
		try {
		Region r3 = rOne.intersect(rTwo);
		regionToString(r3);
		} catch (NoIntersectionException e) {
		}
		
	}
	
	private void regionToString(Region r) {
		System.out.println(r.getLeft() + " " + 
						   r.getTop() + " " + 
				           r.getRight() + " " + 
						   r.getBottom());
	}
}
