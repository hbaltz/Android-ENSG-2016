package eu.ensg.forester;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import java.io.IOException;

import eu.ensg.spatialite.geom.XY;
import jsqlite.Database;

/**
 * Created by cyann on 20/12/15.
 */
public class MySpatialiteHelper extends SpatialiteOpenHelper {

    public static final String DATABASE_NAME = "Spatial.sqlite";
    public static final int DATABASE_VERSION = 2;

    public static final int GPS_SRID = 4326;

    public static final String TABLE_INTEREST = "PointOfInterest";
    public static final String TABLE_SECTOR = "Sector";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_COMMENT = "comment";
    public static final String COLUMN_COORDINATE = "coordinate";

    public static final String CREATE_INTEREST =
            "create table " + TABLE_INTEREST +
                    "(" + COLUMN_ID + " integer PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " string NOT NULL, " +
                    COLUMN_COMMENT + " string" + ");";

    public static final String CREATE_INTEREST_COORDINATE =
            "SELECT AddGeometryColumn('" + TABLE_INTEREST + "', '" + COLUMN_COORDINATE + "', " + GPS_SRID + ", 'POINT', 'XY', 0);";

    public static final String CREATE_SECTOR =
            "create table " + TABLE_SECTOR +
                    "(" + COLUMN_ID + " integer PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " string NOT NULL, " +
                    COLUMN_COMMENT + " string" + ");";

    public static final String CREATE_SECTOR_COORDINATE =
            "SELECT AddGeometryColumn('" + TABLE_SECTOR + "', '" + COLUMN_COORDINATE + "', " + GPS_SRID + ", 'POLYGON', 'XY', 0);";

    public MySpatialiteHelper(Context context) throws jsqlite.Exception, IOException {
        super(context, DATABASE_NAME, DATABASE_VERSION);
    }

    public static XY coordFactory(Location location) {
        return new XY(location.getLongitude(), location.getLatitude());
    }

    @Override
    public void onCreate(Database db) throws jsqlite.Exception {
        super.exec(CREATE_INTEREST);
        super.exec(CREATE_INTEREST_COORDINATE);
        super.exec(CREATE_SECTOR);
        super.exec(CREATE_SECTOR_COORDINATE);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) throws jsqlite.Exception {
        Log.w(MySpatialiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");

        int delta = newVersion - oldVersion;

        if (newVersion == 2) {
            if (delta <= 1) {
                super.exec(CREATE_SECTOR);
                super.exec(CREATE_SECTOR_COORDINATE);

            }
        }
    }
}