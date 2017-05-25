package s17cs350task1;

import javafx.concurrent.Task;
//import javafx.geometry.Point3D;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Samantha Agather on 4/3/2017.
 */
public class Box implements Cloneable{

    private String name = "";
    private Dimension3D size = null;
    private boolean root = false;
    private Connector parentConnector = null;
    private List<Connector> childConnectors = new LinkedList<>();

    Box(String iName, Dimension3D iSize) {

        this.name = iName;
        this.size = iSize;
        constructorChecker();

    }

    Box(String iName, Dimension3D iSize, boolean isRoot){

        this.name = iName;
        this.size = iSize;
        this.root = isRoot;
        constructorChecker();

    }

    @Override
    public Box clone() throws CloneNotSupportedException{  //8 lines expected

        Box cloneBox = (Box)super.clone();  //clone the object itself
        cloneBox.name = new String(this.name);
        cloneBox.root = this.root;  //clone root
        cloneBox.size = this.size.clone();  //clone size
        cloneBox.childConnectors = new LinkedList<>();  //initialize a new list for childConnectors
        if(this.childConnectors != null) {
            for (Connector c : this.childConnectors) {  //for each existing connector, clone and connect
                cloneBox.connectChild(c.clone());
            }
        }

        return cloneBox;

    }

    public void connectChild(Connector iConnector){

        if(iConnector == null)
            throw new TaskException("Bad connector, null");

        boolean ok = nameChecker(iConnector.getChildBox());

        if(!ok)
            throw new TaskException("Boxes must have unique id");

        this.childConnectors.add(iConnector);
        this.childConnectors.get(this.childConnectors.indexOf(iConnector)).setParentBox(this);

    }

    public Point3D getAbsoluteCenterPosition(){   //10 lines of code expected

        //base case--> root
        if(this.root){
            return new Point3D(0,0, 0);
        }

        //every other case, use recursive call to getAbsoluteCenterPosition()
        //use getParentBox and getOffsetFromParentBox
        Connector cParent = this.getConnectorToParent();
        Box parent = cParent.getParentBox();
        double x = parent.getAbsoluteCenterPosition().getX();
        double y = parent.getAbsoluteCenterPosition().getY();
        double z = parent.getAbsoluteCenterPosition().getZ();

        x += cParent.getOffsetFromParentBox().getX();
        y += cParent.getOffsetFromParentBox().getY();
        z += cParent.getOffsetFromParentBox().getZ();

        Point3D absCenter = new Point3D(x, y, z);

        return absCenter;
    }

    public int getChildBoxCount(){

        return getChildBoxes().size();
    }

    public List<Box> getChildBoxes(){

        LinkedList<Box> list = new LinkedList<Box>();
        List<Connector> children = this.getConnectorsToChildren();
            for(int i = 0; i < children.size(); i++)
            {
                list.add(children.get(i).getChildBox());
            }
            Comparator<Box> comp = (Box a, Box b) -> {return a.toString().compareTo(b.toString());};
            //list.sort(comp);
            return list;

    }

    public List<Connector> getConnectorsToChildren()
    {
        if(this.childConnectors == null)
            throw new TaskException("No child connectors");

        List<Connector> returnConnectors = new LinkedList<Connector>();

        for(Connector c : this.childConnectors)
            returnConnectors.add(c);

        return returnConnectors;

    }

    Connector getConnectorToParent()
    {
        if(this.parentConnector == null)
            throw new TaskException("No parent connector");
        return this.parentConnector;
    }

    public int getDescendantBoxCount(){   return getDescendantBoxes().size();   }

    public List<Box> getDescendantBoxes(){

        LinkedList<Box> list = (LinkedList<Box>)this.getChildBoxes();
        LinkedList<Box> retList = new LinkedList<Box>();
        retList.addAll(this.getDescendantRecursion(list));
        Comparator<Box> comp = (Box a, Box b) -> {return a.toString().compareTo(b.toString());};
        retList.sort(comp);
        return retList;
    }

    private List<Box> getDescendantRecursion(LinkedList<Box> list)
    {
        List<Box> retList = new LinkedList<>();
        for(Box box: list)
        {
            retList.add(box);
            retList.addAll(getDescendantRecursion((LinkedList<Box>)box.getChildBoxes()));
        }
        return retList;
    }

    public String getID(){   return this.name;   }

    public Dimension3D getSize(){   return this.size;   }

    public boolean hasConnectorToParent(){   return !(parentConnector == null);   }

    public boolean isRoot(){   return this.root;   }

    void setConnectorToParent(Connector iConnector)
    {
        if(iConnector == null ||isRoot())
            throw new TaskException("Must have a valid connector input");

        if(this.parentConnector != null)
            throw new TaskException("Cannot set parent, already has a parent");

        this.parentConnector = iConnector;

    }

    public double NEW_calculateAreaAll(BoundingBox.E_Plane plane){

        if(plane == null)
            throw new TaskException("Null plane in NEW_calculateAreaAll");

        List<Box> descendants = this.getDescendantBoxes();

        double sum = this.NEW_calculateAreaSelf(plane);

        if(descendants == null || descendants.size() == 0)
            return sum;

        for(Box b : descendants)
            sum += b.NEW_calculateAreaSelf(plane);

        return sum;
    }

    public double NEW_calculateAreaSelf(BoundingBox.E_Plane plane){

        if(plane  == null)
            throw new TaskException("Null plane in NEW_calculateAreaSelf");

        BoundingBox b = this.NEW_generateBoundingBoxSelf();
        return b.calculateArea(plane);
    }

    public Point3D NEW_calculateCenterOfMassAll(){

        BoundingBox temp = this.NEW_generateBoundingBoxAll();

        Point3D centerOfMass = temp.getCenter();

        return centerOfMass;
    }

    public Point3D NEW_calculateCenterOfMassSelf(){

        return this.getAbsoluteCenterPosition();
    }

    public double NEW_calculateVolumeAll(){

        List<Box> descendants = this.getDescendantBoxes();
        double sum = this.NEW_calculateVolumeSelf();

        if(descendants == null || descendants.size() == 0)
            return sum;

        for(Box b : descendants)
            sum += b.NEW_calculateVolumeSelf();

        return sum;
    }

    public double NEW_calculateVolumeSelf(){

        BoundingBox b = NEW_generateBoundingBoxSelf();

        return b.calculateVolume();
    }

    public BoundingBox NEW_generateBoundingBoxAll(){

        BoundingBox b = NEW_generateBoundingBoxSelf();
        List<Box> descendants = this.getDescendantBoxes();

        for(Box box : descendants)
            b.extend(box.NEW_generateBoundingBoxSelf());

        return b;
    }

    public BoundingBox NEW_generateBoundingBoxSelf(){

        return new BoundingBox(this.getAbsoluteCenterPosition(), this.size);
    }

    public List<List<Point3D>>  NEW_generateFramesAll(){

        List<List<Point3D>> allFrames = new LinkedList<>();

        allFrames.add(this.NEW_generateFrameSelf());

        List<Box> boxes = this.getDescendantBoxes();

        for(Box b : boxes)
            allFrames.add(b.NEW_generateFrameSelf());

        return allFrames;
    }

    public List<Point3D> NEW_generateFrameSelf(){

        List<Point3D> corners = new LinkedList<>();

        BoundingBox box = this.NEW_generateBoundingBoxSelf();
        corners.add(this.getAbsoluteCenterPosition());
        corners.addAll(box.generateCorners());

        return corners;
    }

    public String toString(){   return this.name;   }

    private void constructorChecker() throws TaskException{

        if(this.name == null || this.name.isEmpty())
            throw new TaskException("Must have a unique ID");

        if(this.size == null || this.size.getDepth() < 0 || this.size.getHeight() < 0 || this.size.getWidth() < 0)
            throw new TaskException("Cannot have negative dimensions to a 3D box.");

    }


    private boolean nameChecker(Box iChildBox) throws TaskException{

        Box temp = this;

        List<Box> list = getTopLevel(this).getDescendantBoxes();
        list.add(getTopLevel(this));

        List<Box> inList = iChildBox.getDescendantBoxes();
        inList.add(iChildBox);

        for(Box a : list){

            for(Box b : inList){
                if(a.getID().compareToIgnoreCase(b.getID()) == 0)
                    return false;
            }
        }

        return true;


    }

    private Box getTopLevel(Box iBox){

        if(iBox.hasConnectorToParent()){
            iBox = getTopLevel(iBox.getConnectorToParent().getParentBox());
        }

        return iBox;

    }

}
