package a6test.jcpwfloi;

import a6.ObservablePicture;
import a6.ROIObserver;
import a6.Region;

public class ChensObserver implements ROIObserver {

	private SuperObserverHandlerMachine machine;
	
	public ChensObserver(SuperObserverHandlerMachine machine) {
		this.machine = machine;
	}
	
	@Override
	public void notify(ObservablePicture picture, Region changed_region) {
		machine.handler(this, picture, changed_region);
	}

}
