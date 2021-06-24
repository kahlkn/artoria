package artoria.data;

import java.math.BigDecimal;

/**
 * Geographic coordinate object.
 * @see <a href="https://en.wikipedia.org/wiki/Geographic_coordinate_system">Geographic coordinate system</a>
 * @author Kahle
 */
public interface GeoCoordinate extends Coordinate {

    /**
     * The longitude of longitude and latitude.
     * @return The longitude
     */
    BigDecimal getLongitude();

    /**
     * The longitude of longitude and latitude.
     * @param longitude The longitude
     */
    void setLongitude(BigDecimal longitude);

    /**
     * The latitude of longitude and latitude.
     * @return The latitude
     */
    BigDecimal getLatitude();

    /**
     * The latitude of longitude and latitude.
     * @param latitude The latitude
     */
    void setLatitude(BigDecimal latitude);

    /**
     * The altitude value.
     * @return The altitude
     */
    BigDecimal getAltitude();

    /**
     * The altitude value.
     * @param altitude The altitude
     */
    void setAltitude(BigDecimal altitude);

}
