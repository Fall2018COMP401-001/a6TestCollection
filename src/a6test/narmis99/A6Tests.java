import static org.junit.Assert.*;

import org.junit.Test;

import a6.NoIntersectionException;
import a6.ObservablePicture;
import a6.ObservablePictureImpl;
import a6.Picture;
import a6.PictureImpl;
import a6.ROIObserver;
import a6.Region;
import a6.RegionImpl;

public class A6Tests {
	
	@Test
	public void testRegionConstructor() {
		Region reg;
		Region other;
		Region another;
		
		try {
			reg = new RegionImpl(5, 2, 3, 3);
			other = new RegionImpl(1, 5, 3, 3);
		}
		catch (IllegalArgumentException e) {
			
		}
		
		another = new RegionImpl(1, 3, 5, -7);
		assertEquals(-7, another.getBottom());
	}
	
	/*@Test
	public void testObservableNull() {
		ROIObserver obs = null;
		Region reg = new RegionImpl(1, 3, 5, 7);		
		
		try {
			registerROIObserver(obs, reg);
		}
		catch (IllegalArgumentException e) {
			
		}
	}*/

}
