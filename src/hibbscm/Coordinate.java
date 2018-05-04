package hibbscm;

public class Coordinate implements Comparable<Coordinate>{
    int x, y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /** Sorts by x, then by y */
    @Override
    public int compareTo(Coordinate o) {
        int xDiff = x - o.x;

        if(xDiff != 0) {
            return xDiff;
        } else {
            return y - o.y;
        }
    }

    /** Check on the values, not on the hash of the object */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Coordinate) && (((Coordinate)obj).x == x) && (((Coordinate)obj).y == y);
    }

    /** Required for the hash map */
    @Override
    public int hashCode() {
        return ( y << 16 ) ^ x;
    }
}
