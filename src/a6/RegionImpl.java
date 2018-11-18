package a6;

public class RegionImpl implements Region {
 
	private int left;
	private int top;
	private int right;
	private int bottom;

	public RegionImpl(int left, int top, int right, int bottom) {
		if (left > right || top > bottom) {
			throw new IllegalArgumentException("invalid input");
		}
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;

	}

	@Override
	public int getTop() {
		return top;
	}

	@Override
	public int getBottom() {
		return bottom;
	}

	@Override
	public int getLeft() {
		return left;
	}

	@Override
	public int getRight() {
		return right;
	}

	@Override
	public Region intersect(Region other) throws NoIntersectionException {
		
        if(other == null) {
        	throw new NoIntersectionException();
        }

		int t, b, l, r;
		t = this.top > other.getTop() ? this.top : other.getTop();
	    b = this.bottom < other.getBottom() ? this.bottom : other.getBottom();
		l = this.left > other.getLeft() ? this.left : other.getLeft();
		r = this.right < other.getRight() ? this.right : other.getRight();

		try {
			return new RegionImpl(l, t, r,b);
		} catch (IllegalArgumentException e) {
			throw new NoIntersectionException();
		}
	}

	@Override
	public Region union(Region other) {
		int t, b, l, r;
		if (other == null) {
			return this;
		}
	    t = this.top < other.getTop() ? this.top : other.getTop();
		b = this.bottom > other.getBottom() ? this.bottom : other.getBottom();
		l = this.left < other.getLeft() ? this.left : other.getLeft();
	    r = this.right > other.getRight() ? this.right : other.getRight();
		return new RegionImpl(l, t, r, b);
	}
}

