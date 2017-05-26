package s17cs350task1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bcxtr on 5/25/2017.
 */
public abstract class A_Component {

    private String componentID = "";
    private boolean root = false;

    public A_Component(String id, boolean isRoot){

        this.componentID = id;
        this.root = isRoot;

    }

    public abstract double calculateAreaAll();

    public abstract double calculateAreaSelf();

    public abstract Point3D calculateCenterOfMassAll();

    public abstract Point3D calculateCenterOfMassSelf();

    public abstract double calculateVolumeSelf();

    public abstract double calculateVolumeAll();

    public A_Component clone() throws CloneNotSupportedException{

        return (A_Component)super.clone();

    }

    public void connectChild(Connector connector){



    }

    public boolean equals(Object obj){



        return false;
    }

    public abstract String export(A_Exporter exporter);

    abstract BoundingBox generateBoundingBoxAll();

    abstract BoundingBox generateBoundingBoxSelf();

    abstract List<List<Point3D>> generateFramesAll();

    abstract List<Point3D> generateFrameSelf();

    public Point3D getAbsoluteCenterPosition(){

        return new Point3D(0,0,0);

    }

    public int getChildCount(){

        return 0;
    }

    public List<A_Component> getChildren(){

        return new ArrayList<A_Component>();
    }

    public List<Connector> getConnectorsToChildren(){

        return new ArrayList<>();
    }

    public Connector getConnectorToParent(){

        return new Connector(new Box("", new Dimension3D(0,0,0)), new Point3D(0,0,0));
    }

    public int getDescendantCount(){

        return 0;
    }

    public List<A_Component> getDescendants(){

        return new ArrayList<>();
    }

    public String getID(){

        return "";
    }

    List<A_Component> getSubtree(){

        return new ArrayList<>();
    }

    List<A_Component> getTree(){

        return new ArrayList<>();
    }

    public boolean hasConnectorToParent(){

        return false;
    }

    public int hashCode(){

        return 0;
    }

    public boolean isRoot(){

        return this.root;
    }

    void setConnectorToParent(Connector connector){

    }

    public String toString(){

        return "";
    }
}
