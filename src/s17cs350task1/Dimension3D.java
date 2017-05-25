package s17cs350task1;

/**
 * Created by bcxtr on 4/17/2017.
 */
public class Dimension3D implements Cloneable{

    private double width;
    private double height;
    private double depth;

    public Dimension3D(){

        this.width = 1;
        this.height = 1;
        this.depth = 1;

    }

    public Dimension3D(double inX, double inY){

        this.width = inX;
        this.height = inY;
        this.depth = 1;
        constructionChecker();

    }

    public Dimension3D(double inX, double inY, double inZ){

        this.width = inX;
        this.height = inY;
        this.depth = inZ;
        constructionChecker();

    }

    public Dimension3D clone() throws CloneNotSupportedException{

        Dimension3D cloneDimension = (Dimension3D) super.clone();
        cloneDimension.setWidth(this.width);
        cloneDimension.setHeight(this.height);
        cloneDimension.setDepth(this.depth);
        return cloneDimension;
    }

    public void setWidth(double inX){   this.width = inX;   }

    public void setHeight(double inY){   this.height = inY;   }

    public void setDepth(double inZ){   this.depth = inZ;   }

    public double getWidth(){   return this.width;   }

    public double getHeight(){   return this.height;   }

    public double getDepth(){   return this.depth;   }

    public String toString(){

        return "Dimension3D [x = " + this.width + ", y = " + this.height + ", z = " + this.depth + "]";

    }

    private void constructionChecker(){

        if(this.width < 1 || this.height < 1 || this.depth <1)
            throw new TaskException("No negative or zero dimensions, we're working in 3D space");

    }

}
