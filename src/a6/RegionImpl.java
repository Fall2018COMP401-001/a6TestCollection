package a6;

public class RegionImpl implements Region {
	
	private int left, top, right, bottom;
	
	public RegionImpl(int left, int top, int right, int bottom) {
		if (left > right || top > bottom) {
			throw new IllegalArgumentException("illegal geometry");
		}
		this.left = left;
		this.right = right;
		this.top = top;
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
		if (other == null) {
			throw new NoIntersectionException();
		}
		else if (other.getRight() < left || other.getLeft() > right 
				|| other.getTop() > bottom || other.getBottom() < top) {
			throw new NoIntersectionException();
		}
		int newLeft = (other.getLeft() < left) ? left : other.getLeft();
		int newTop = (other.getTop() < top) ? top : other.getTop();
		int newRight = (other.getRight() > right) ? right : other.getRight();
		int newBottom = (other.getBottom() > bottom) ? bottom : other.getBottom();
		return new RegionImpl(newLeft, newTop, newRight, newBottom);
	}

	@Override
	public Region union(Region other) {
		if (other == null) {
			return this;
		}
		int newLeft = (other.getLeft() > left) ? left : other.getLeft();
		int newTop = (other.getTop() > top) ? top : other.getTop();
		int newRight = (other.getRight() < right) ? right : other.getRight();
		int newBottom = (other.getBottom() < bottom) ? bottom : other.getBottom();
		return new RegionImpl(newLeft, newTop, newRight, newBottom);
	}

	
}
