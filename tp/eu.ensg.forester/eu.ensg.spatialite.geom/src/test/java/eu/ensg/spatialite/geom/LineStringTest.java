package eu.ensg.spatialite.geom;

import static org.junit.Assert.assertEquals;

/**
 * Created by cyann on 18/12/15.
 */
public class LineStringTest {

    @org.junit.Test
    public void testMarshall() throws Exception {

        LineString lineString = new LineString();
        lineString.addCoordinate(new XY(10, 10));
        lineString.addCoordinate(new XY(20, 10));
        lineString.addCoordinate(new XY(20, 20));
        lineString.addCoordinate(new XY(10, 20));

        System.out.println(lineString);
        assertEquals("LINESTRING (10 10, 20 10, 20 20, 10 20)", lineString.toString());
    }

    @org.junit.Test
    public void testUnMarshall() throws Exception {

        String lineStringString = "LINESTRING (10 10, 20 10, 20 20, 10 20)";

        LineString lineString = LineString.unMarshall(new StringBuilder(lineStringString));
        System.out.println(lineString);

        assertEquals(lineStringString, lineString.toString());
    }

}