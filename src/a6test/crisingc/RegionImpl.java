package a6test.crisingc;

public class RegionImpl implements Region {
	
	int left;
	int right;
	int top;
	int bottom;
	
	public RegionImpl(int left, int top, int right, int bottom) {
		if (left > right || top > bottom) {
			throw new IllegalArgumentException("illegal parameters");
		}
		
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
	}
	
	@Override
	public int getTop() {
		// TODO Auto-generated method stub
		return top;
	}

	@Override
	public int getBottom() {
		// TODO Auto-generated method stub
		return bottom;
	}

	@Override
	public int getLeft() {
		// TODO Auto-generated method stub
		return left;
	}

	@Override
	public int getRight() {
		// TODO Auto-generated method stub
		return right;
	}

	@Override
	public Region intersect(Region other) throws NoIntersectionException {
		// the following if statement should catch any argument that does not intersect with this one.
		if (other == null ||
				other.getTop() > getBottom() ||
				other.getLeft() > getRight() ||
				other.getRight() < getLeft() ||
				other.getBottom() < getTop())
			throw new NoIntersectionException();
		/*
		 * The following set of integers are set to equal the parameters of the intersecting region.
		 * newLeft will equal the rightmost of the two regions' left parameter,
		 * newRight will equal the leftmost, newTop the lowest, and newBottom the highest.
		 */
		int newLeft = (getLeft() >= other.getLeft()) ? getLeft() : other.getLeft();
		int newRight = (getRight() <= other.getRight()) ? getRight() : other.getRight();
		int newTop = (getTop() >= other.getTop()) ? getTop() : other.getTop();
		int newBottom = (getBottom() <= other.getBottom()) ? getBottom() : other.getBottom();
		
		// Creating a new Region r with the four previous integers as arguments.
		Region r = new RegionImpl(newLeft, newTop, newRight, newBottom);
		
		// Returning Region r
		return r;
	}

	@Override
	public Region union(Region other) {
		
		// Returning this Region if the parameter is null
		if (other == null) {
			return this;
		}

			
		/*
		* The following set of integers are set to equal the parameters of the union region.
		* newLeft will equal the leftmost of the two regions' left parameter,
		* newRight will equal the rightmost, newTop the highest, and newBottom the lowest.
		*/
		int newLeft = (getLeft() <= other.getLeft()) ? getLeft() : other.getLeft();
		int newRight = (getRight() >= other.getRight()) ? getRight() : other.getRight();
		int newTop = (getTop() <= other.getTop()) ? getTop() : other.getTop();
		int newBottom = (getBottom() >= other.getBottom()) ? getBottom() : other.getBottom();
			
		// Creating a new Region r with the four previous integers as arguments.
		Region r = new RegionImpl(newLeft, newTop, newRight, newBottom);
		return r;
	}

}
