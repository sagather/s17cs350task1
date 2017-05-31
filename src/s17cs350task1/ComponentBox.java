package s17cs350task1;

import javafx.concurrent.Task;
//import javafx.geometry.Point3D;

import java.util.*;

/**
 * Created by Samantha Agather on 4/3/2017.
 */
public class ComponentBox extends A_Component implements Cloneable {

    private Dimension3D size= new Dimension3D(1,1,1);

    ComponentBox(String iName, Dimension3D iSize) {

        super(iName, false);
        this.size = iSize;
        if(this.size == null || this.size.getDepth() < 0 || this.size.getHeight() < 0 || this.size.getWidth() < 0)
            throw new TaskException("Cannot have negative dimensions to a 3D ComponentBox.");

    }

    ComponentBox(String iName, Dimension3D iSize, boolean isRoot){

        super(iName, isRoot);
        this.size = iSize;
        if(this.size == null || this.size.getDepth() < 0 || this.size.getHeight() < 0 || this.size.getWidth() < 0)
            throw new TaskException("Cannot have negative dimensions to a 3D ComponentBox.");

    }

    public ComponentBox clone()throws CloneNotSupportedException{

        ComponentBox cloneBox = (ComponentBox)super.clone();
        cloneBox.size = this.size.clone();

        return cloneBox;
    }

    @Override
    public String export(A_Exporter exporter) {

        List<A_Component> all = new ArrayList<>();
        String ret = "";
        A_Component temp = this;
        while(temp.hasConnectorToParent()){
            temp = temp.getConnectorToParent().getComponentParent();
        }
        all.add(temp);
        all.addAll(temp.getDescendants());

        exporter.openComponentNode(this.getID());
        exporter.addPoint("center", this.getAbsoluteCenterPosition());

        List<Point3D> corners = this.generateFrameSelf();
        exporter.addPoint("left-bottom-far", corners.get(0));
        exporter.addPoint("right-bottom-far", corners.get(1));
        exporter.addPoint("right-bottom-near", corners.get(2));
        exporter.addPoint("left-bottom-near", corners.get(3));
        exporter.addPoint("left-top-far", corners.get(4));
        exporter.addPoint("right-top-far", corners.get(5));
        exporter.addPoint("right-top-near", corners.get(6));
        exporter.addPoint("left-top-near", corners.get(7));
        exporter.closeComponentNode(this.getID());

        ret += exporter.export();

        for(A_Component a : this.getChildren()){
            ret += a.export(exporter);
        }

        exporter.closeExport();
        return ret;
    }

    public Dimension3D getSize(){   return this.size;   }

    @Override
    public double calculateAreaAll(BoundingBox.E_Plane plane){

        if(plane == null)
            throw new TaskException("Null plane in NEW_calculateAreaAll");

        List<A_Component> descendants = this.getDescendants();

        double sum = this.calculateAreaSelf(plane);

        if(descendants == null || descendants.size() == 0)
            return sum;

        for(A_Component b : descendants)
            sum += b.calculateAreaSelf(plane);

        return sum;
    }

    @Override
    public double calculateAreaSelf(BoundingBox.E_Plane plane){

        if(plane  == null)
            throw new TaskException("Null plane in NEW_calculateAreaSelf");

        BoundingBox b = this.generateBoundingBoxSelf();
        return b.calculateArea(plane);
    }

    @Override
    public Point3D calculateCenterOfMassAll(){

        BoundingBox temp = this.generateBoundingBoxAll();

        Point3D centerOfMass = temp.getCenter();

        return centerOfMass;
    }

    @Override
    public Point3D calculateCenterOfMassSelf(){

        return this.getAbsoluteCenterPosition();
    }

    @Override
    public double calculateVolumeAll(){

        List<A_Component> descendants = super.getDescendants();
        double sum = this.calculateVolumeSelf();

        if(descendants == null || descendants.size() == 0)
            return sum;

        for(A_Component b : descendants)
            sum += b.calculateVolumeSelf();

        return sum;
    }

    @Override
    public double calculateVolumeSelf(){

        BoundingBox b = generateBoundingBoxSelf();

        return b.calculateVolume();
    }

    @Override
    public BoundingBox generateBoundingBoxAll(){

        BoundingBox b = generateBoundingBoxSelf();
        List<A_Component> descendants = this.getDescendants();

        for(A_Component componentBox : descendants)
            b.extend(componentBox.generateBoundingBoxSelf());

        return b;
    }

    @Override
    public BoundingBox generateBoundingBoxSelf(){

        return new BoundingBox(this.getAbsoluteCenterPosition(), this.size);
    }

    @Override
    public List<List<Point3D>>  generateFramesAll(){

        List<List<Point3D>> allFrames = new LinkedList<>();

        allFrames.add(this.generateFrameSelf());

        List<A_Component> ComponentBoxes = super.getDescendants();

        for(A_Component b : ComponentBoxes)
            allFrames.add(b.generateFrameSelf());

        return allFrames;
    }

    @Override
    public List<Point3D> generateFrameSelf(){

        List<Point3D> corners = new LinkedList<>();

        BoundingBox ComponentBox = this.generateBoundingBoxSelf();
        corners.add(this.getAbsoluteCenterPosition());
        corners.addAll(ComponentBox.generateCorners());

        return corners;
    }

    public String toString(){   return super.getID();   }

}
