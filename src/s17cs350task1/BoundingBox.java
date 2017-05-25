package s17cs350task1;

//import javafx.geometry.Point3D;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by bcxtr on 5/11/2017.
 */
public class BoundingBox {

    private Point3D center = null;
    private Dimension3D size = null;

    public enum E_Plane{
        XY, YZ, XZ
    }

    public BoundingBox(Point3D iCenter, Dimension3D iSize){

        if(iCenter == null || iSize == null)
            throw new TaskException("Must use valid input for box construction");

        this.center = iCenter;
        this.size = iSize;
        checkConstructor();

    }

    public double calculateArea(BoundingBox.E_Plane plane){

        if(plane == null)
            throw new TaskException("Can't have a nonexistent plane");

        switch (plane){
            case XY:  return 2 * (this.size.getWidth() * this.size.getHeight());
            case YZ:  return 2 * (this.size.getHeight() * this.size.getDepth());
            case XZ:  return 2 * (this.size.getDepth() * this.size.getWidth());
            default:  return 0f;
        }

    }

    public double calculateVolume(){

        if(this.size == null)
            throw new TaskException("How did this get created with a null size??");

        return this.size.getWidth() * this.size.getHeight() * this.size.getDepth();
    }

    public BoundingBox clone() throws CloneNotSupportedException{

        BoundingBox clone = (BoundingBox) super.clone();
        clone.size = this.size.clone();
        clone.center.add(this.center);

        return clone;
    }

    public BoundingBox extend(BoundingBox iBox){

        Dimension3D nSize;
        Point3D nCenter;

        double x,y,z;

        List<Point3D> new_corners = new LinkedList<>();

        List<Point3D> this_corners = generateCorners();
        List<Point3D> other_corners = iBox.generateCorners();

        for(int i = 0; i < 8; i++){
            if(Math.abs(this_corners.get(i).getX()) < Math.abs(other_corners.get(i).getX()))
                x = other_corners.get(i).getX();

            else
                x = this_corners.get(i).getX();

            if(Math.abs(this_corners.get(i).getY()) < Math.abs(other_corners.get(i).getY()))
                y = other_corners.get(i).getY();

            else
                y = this_corners.get(i).getY();

            if(Math.abs(this_corners.get(i).getZ()) < Math.abs(other_corners.get(i).getZ()))
                z = other_corners.get(i).getZ();

            else
                z = this_corners.get(i).getZ();

            new_corners.add(new Point3D(x, y, z));
        }

        double[] newCoords = extendHelper(new_corners);

        double xLeft = newCoords[0]; double xRight = newCoords[1]; double yUp = newCoords[2]; double yDown = newCoords[3];
        double zClose = newCoords[4]; double zFar = newCoords[5];

        //calculate center
        x = (xLeft + xRight) * 0.5f;
        y = (yUp + yDown) * 0.5f;
        z = (zClose + zFar) * 0.5f;

        nSize = new Dimension3D(x,y,z);

        //calculate size
        x = (x / 2.0) + xLeft;
        y = (y / 2.0) + yDown;
        z = (z / 2.0) + zClose;

        nCenter = new Point3D(x, y, z);

        return new BoundingBox(nCenter, nSize);
    }

    private double[] extendHelper(List<Point3D> iPoints){

        double[] returnArray = new double[]{0,0,0,0,0,0};

        for(Point3D point : iPoints){

            if(point.getZ() < returnArray[4])
                returnArray[4] = point.getZ();

            if(point.getZ() > returnArray[5])
                returnArray[5] = point.getZ();

            if(point.getY() < returnArray[3])
                returnArray[3] = point.getY();

            if(point.getY() > returnArray[2])
                returnArray[2] = point.getY();

            if(point.getX() < returnArray[1])
                returnArray[1] = point.getX();

            if(point.getX() > returnArray[0])
                returnArray[0] = point.getX();

        }

        return returnArray;

    }

    public List<Point3D> generateCorners(){

        double x,y,z;
        List<Point3D> hold = new LinkedList<>();
        x = this.size.getWidth() / 2;
        y = this.size.getHeight() / 2;
        z = this.size.getDepth() / 2;

        hold.add(new Point3D(this.center.getX() - x, this.center.getY() - y, this.center.getZ() - z));
        hold.add(new Point3D(this.center.getX() + x, this.center.getY() - y, this.center.getZ() - z));
        hold.add(new Point3D(this.center.getX() + x, this.center.getY() - y, this.center.getZ() + z));
        hold.add(new Point3D(this.center.getX() - x, this.center.getY() - y, this.center.getZ() + z));
        hold.add(new Point3D(this.center.getX() - x, this.center.getY() + y, this.center.getZ() - z));
        hold.add(new Point3D(this.center.getX() + x, this.center.getY() + y, this.center.getZ() - z));
        hold.add(new Point3D(this.center.getX() + x, this.center.getY() + y, this.center.getZ() + z));
        hold.add(new Point3D(this.center.getX() - x, this.center.getY() + y, this.center.getZ() + z));

        return hold;
    }

    public s17cs350task1.Point3D getCenter(){   return this.center;   }

    public Dimension3D getSize(){   return this.size;   }

    public String toString(){

        return "";
    }

    private void checkConstructor(){
        if(this.size.getDepth() <= 0 || this.size.getHeight() <= 0 || this.size.getWidth() <= 0)
            throw new TaskException("Not sure how you did it, but you managed to give an invalid size");
    }

}