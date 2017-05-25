package s17cs350task1;

/**
 * Created by bcxtr on 5/25/2017.
 */
public class Point3D {

    private double x = 0;
    private double y = 0;
    private double z = 0;

    public Point3D(double iX, double iY, double iZ){

        this.x = iX;
        this.y = iY;
        this.z = iZ;

    }

    public Point3D add(Point3D iPoint){

        double nX = this.x + iPoint.x;
        double nY = this.y + iPoint.y;
        double nZ = this.z + iPoint.z;

        return new Point3D(nX, nY, nZ);

    }

    public Point3D clone() throws CloneNotSupportedException{

        Point3D clone = (Point3D)super.clone();
        clone.x = this.x;
        clone.y = this.y;
        clone.z = this.z;

        return clone;

    }

    public double getX(){   return this.x;   }

    public double getY(){   return this.y;   }

    public double getZ(){   return this.z;   }

    public Point3D subtract(Point3D iPoint){

        double nX = this.x - iPoint.x;
        double nY = this.y - iPoint.y;
        double nZ = this.z - iPoint.z;

        return new Point3D(nX, nY, nZ);

    }

}
