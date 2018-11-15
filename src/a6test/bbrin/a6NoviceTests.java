package a6.a6Tests;

import a6.NoIntersectionException;
import a6.Region.Region;
import a6.Region.RegionImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class a6NoviceTests {

    @Test
    public void tryIntersection() throws NoIntersectionException {
        Region _region_1 = new RegionImpl(1,3,1,3);
        Region _region_2 = new RegionImpl(4,6, 1,4);

        try {
            _region_1.intersect(_region_2);
            fail("Supposed to throw No Intersection");
        } catch (NoIntersectionException e) {
        }

        try {
            _region_2 = new RegionImpl(1,3,-5,0);
            _region_1.intersect(_region_2);
            fail("Supposed to throw No Intersection");
        } catch (NoIntersectionException e) {
        }

        try {
            _region_2 = new RegionImpl(-4,0,1,3);
            _region_1.intersect(_region_2);
            fail("Supposed to throw No Intersection");
        } catch (NoIntersectionException e) {
        }

        try {
            _region_2 = new RegionImpl(-4,0,4,6);
            _region_1.intersect(_region_2);
            fail("Supposed to throw No Intersection");
        } catch (NoIntersectionException e) {
        }

//        BR

        _region_2 = new RegionImpl(2,4, 2,4);
        Region _intersection = _region_1.intersect(_region_2);

        assertEquals(_intersection.getTop(), 2);
        assertEquals(_intersection.getBottom(), 3);
        assertEquals(_intersection.getLeft(), 2);
        assertEquals(_intersection.getRight(), 3);

//        TL

        _region_2 = new RegionImpl(0,2, 0,2);
        _intersection = _region_1.intersect(_region_2);

        assertEquals(_intersection.getTop(), 1);
        assertEquals(_intersection.getBottom(), 2);
        assertEquals(_intersection.getLeft(), 1);
        assertEquals(_intersection.getRight(), 2);

//        TR

        _region_2 = new RegionImpl(0,2, 2,4);
        _intersection = _region_1.intersect(_region_2);

        assertEquals(_intersection.getTop(), 1);
        assertEquals(_intersection.getBottom(), 2);
        assertEquals(_intersection.getLeft(), 2);
        assertEquals(_intersection.getRight(), 3);

//        BL

        _region_2 = new RegionImpl(2,4, 0,2);
        _intersection = _region_1.intersect(_region_2);

        assertEquals(_intersection.getTop(), 2);
        assertEquals(_intersection.getBottom(), 3);
        assertEquals(_intersection.getLeft(), 1);
        assertEquals(_intersection.getRight(), 2);

//        centered

        _region_1 = new RegionImpl(1,4, 1,4);
        _region_2 = new RegionImpl(2,3, 2,3);
        _intersection = _region_1.intersect(_region_2);

        assertEquals(_intersection.getTop(), 2);
        assertEquals(_intersection.getBottom(), 3);
        assertEquals(_intersection.getLeft(), 2);
        assertEquals(_intersection.getRight(), 3);

//        region two bigger centered horizontal

        _region_2 = new RegionImpl(2,3, 0,4);
        _intersection = _region_1.intersect(_region_2);

        assertEquals(_intersection.getTop(), 2);
        assertEquals(_intersection.getBottom(), 3);
        assertEquals(_intersection.getLeft(), 1);
        assertEquals(_intersection.getRight(), 4);

//        region 2 bigger bottom

        _region_2 = new RegionImpl(2,5, 0,4);
        _intersection = _region_1.intersect(_region_2);

        assertEquals(_intersection.getTop(), 2);
        assertEquals(_intersection.getBottom(), 4);
        assertEquals(_intersection.getLeft(),  1);
        assertEquals(_intersection.getRight(), 4);

//        region 2 bigger top

        _region_2 = new RegionImpl(0,2, 0,4);
        _intersection = _region_1.intersect(_region_2);

        assertEquals(_intersection.getTop(), 1);
        assertEquals(_intersection.getBottom(), 2);
        assertEquals(_intersection.getLeft(),  1);
        assertEquals(_intersection.getRight(), 4);

//        region 2 bigger left

        _region_2 = new RegionImpl(0,5, 0,2);
        _intersection = _region_1.intersect(_region_2);

        assertEquals(_intersection.getTop(), 1);
        assertEquals(_intersection.getBottom(), 4);
        assertEquals(_intersection.getLeft(),  1);
        assertEquals(_intersection.getRight(), 2);

//        region 2 bigger right

        _region_2 = new RegionImpl(0,5, 3,5);
        _intersection = _region_1.intersect(_region_2);

        assertEquals(_intersection.getTop(), 1);
        assertEquals(_intersection.getBottom(), 4);
        assertEquals(_intersection.getLeft(),  3);
        assertEquals(_intersection.getRight(), 4);

//        region 2 bigger centered vertical

        _region_2 = new RegionImpl(0,5, 2,3);
        _intersection = _region_1.intersect(_region_2);

        assertEquals(_intersection.getTop(), 1);
        assertEquals(_intersection.getBottom(), 4);
        assertEquals(_intersection.getLeft(),  2);
        assertEquals(_intersection.getRight(), 3);

//        region 2 is along top edge, but does not touch corners

        _region_2 = new RegionImpl(0,3, 2,3);
        _intersection = _region_1.intersect(_region_2);

        assertEquals(_intersection.getTop(), 1);
        assertEquals(_intersection.getBottom(), 3);
        assertEquals(_intersection.getLeft(),  2);
        assertEquals(_intersection.getRight(), 3);

//        region 2 is along left edge, but does not touch corners

        _region_2 = new RegionImpl(2,3, 0,3);
        _intersection = _region_1.intersect(_region_2);

        assertEquals(_intersection.getTop(), 2);
        assertEquals(_intersection.getBottom(), 3);
        assertEquals(_intersection.getLeft(),  1);
        assertEquals(_intersection.getRight(), 3);

//        region 2 is along bottom edge, but does not touch corners

        _region_2 = new RegionImpl(3,5, 2,3);
        _intersection = _region_1.intersect(_region_2);

        assertEquals(_intersection.getTop(), 3);
        assertEquals(_intersection.getBottom(), 4);
        assertEquals(_intersection.getLeft(),  2);
        assertEquals(_intersection.getRight(), 3);

//        region 2 is along right edge, but does not touch corners

        _region_2 = new RegionImpl(2,3, 2,5);
        _intersection = _region_1.intersect(_region_2);

        assertEquals(_intersection.getTop(), 2);
        assertEquals(_intersection.getBottom(), 3);
        assertEquals(_intersection.getLeft(),  2);
        assertEquals(_intersection.getRight(), 4);
    }

    @Test
    public void unionTest() throws NoIntersectionException {
        Region _region_1 = new RegionImpl(1,3,1,3);
        Region _region_2 = new RegionImpl(4,6, 1,4);

        Region _should_be_null = _region_1.union(_region_2);
        assertEquals(null, _should_be_null);

        _region_2 = new RegionImpl(2,3, 2,5);
        Region _union = _region_1.union(_region_2);

        assertEquals(_union.getTop(), 1);
        assertEquals(_union.getBottom(), 3);
        assertEquals(_union.getLeft(),  1);
        assertEquals(_union.getRight(), 5);

        _region_1 = new RegionImpl(0,10,0,10);
        _region_2 = new RegionImpl(5,15, 5,15);
        _union = _region_1.union(_region_2);

        assertEquals(_union.getTop(), 0);
        assertEquals(_union.getBottom(), 15);
        assertEquals(_union.getLeft(),  0);
        assertEquals(_union.getRight(), 15);
    }
}