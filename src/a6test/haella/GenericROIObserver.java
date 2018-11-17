package a6test.haella;

import a6.*;

public class GenericROIObserver implements ROIObserver {


	
	public GenericROIObserver(){  
	}
	
	@Override
	public void notify(ObservablePicture picture, Region changed_region) {
		System.out.println("I observed it!");

	}

}
