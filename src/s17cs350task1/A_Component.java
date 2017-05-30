package s17cs350task1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by bcxtr on 5/25/2017.
 */
public abstract class A_Component {

    private String componentID = "";
    private boolean root = false;
    private Connector parentConnector = null;
    private List<Connector> childConnectors = new ArrayList<>();

    public A_Component(String id, boolean isRoot) {

        this.componentID = id;
        this.root = isRoot;

        constructorChecker();

    }

    public abstract double calculateAreaAll(BoundingBox.E_Plane plane);

    public abstract double calculateAreaSelf(BoundingBox.E_Plane plane);

    public abstract Point3D calculateCenterOfMassAll();

    public abstract Point3D calculateCenterOfMassSelf();

    public abstract double calculateVolumeSelf();

    public abstract double calculateVolumeAll();

    public A_Component clone() throws CloneNotSupportedException {

        A_Component cloneComponentBox = (A_Component) super.clone();  //clone the object itself
        cloneComponentBox.componentID = new String(this.componentID);
        cloneComponentBox.root = this.root;  //clone root
        cloneComponentBox.childConnectors = new ArrayList<>();  //initialize a new list for childConnectors
        if (this.childConnectors != null) {
            for (Connector c : this.childConnectors) {  //for each existing connector, clone and connect
                cloneComponentBox.connectChild(c.clone());
            }
        }

        return cloneComponentBox;

    }

    public void connectChild(Connector connector) {

        if (connector == null)
            throw new TaskException("Bad connector, null");

        boolean ok = nameChecker(connector.getChildBox());

        if (!ok)
            throw new TaskException("ComponentBoxes must have unique id");

        this.childConnectors.add(connector);
        this.childConnectors.get(this.childConnectors.indexOf(connector)).setParentBox((ComponentBox)this);

    }

    public boolean equals(Object obj) {

        if(this.componentID.compareTo(obj.toString()) == 0)
            return true;

        return false;
    }

    public abstract String export(A_Exporter exporter);

    abstract BoundingBox generateBoundingBoxAll();

    abstract BoundingBox generateBoundingBoxSelf();

    abstract List<List<Point3D>> generateFramesAll();

    abstract List<Point3D> generateFrameSelf();

    public Point3D getAbsoluteCenterPosition() {

        //base case--> root
        if (this.root) {
            return new Point3D(0, 0, 0);
        }

        //every other case, use recursive call to getAbsoluteCenterPosition()
        //use getParentComponentBox and getOffsetFromParentComponentBox
        Connector cParent = this.getConnectorToParent();
        A_Component parent = cParent.getParentBox();
        double x = parent.getAbsoluteCenterPosition().getX();
        double y = parent.getAbsoluteCenterPosition().getY();
        double z = parent.getAbsoluteCenterPosition().getZ();

        x += cParent.getOffsetFromParentBox().getX();
        y += cParent.getOffsetFromParentBox().getY();
        z += cParent.getOffsetFromParentBox().getZ();

        Point3D absCenter = new Point3D(x, y, z);

        return absCenter;

    }

    public int getChildCount() {

        return getChildren().size();
    }

    public List<A_Component> getChildren() {

        List<A_Component> list = new ArrayList<>();
        List<Connector> children = this.getConnectorsToChildren();
        for(int i = 0; i < children.size(); i++)
        {
            list.add(children.get(i).getChildBox());
        }
        Comparator<A_Component> comp = (A_Component a, A_Component b) -> {return a.toString().compareTo(b.toString());};
        list.sort(comp);
        return list;
    }


    public List<Connector> getConnectorsToChildren()
    {
        if(this.childConnectors == null)
            throw new TaskException("No child connectors");

        List<Connector> returnConnectors = new ArrayList<>();

        for(Connector c : this.childConnectors)
            returnConnectors.add(c);

        return returnConnectors;

    }

    public Connector getConnectorToParent() {

        if(this.parentConnector == null)
            throw new TaskException("Parent Connector Null");

        return this.parentConnector;
    }

    public int getDescendantCount() {   return getDescendants().size();   }

    public List<A_Component> getDescendants() {

        List<A_Component> list = (ArrayList<A_Component>)this.getChildren();
        List<A_Component> retList = new ArrayList<>();
        retList.addAll(this.getDescendantRecursion(list));
        Comparator<A_Component> comp = (A_Component a, A_Component b) -> {return a.toString().compareTo(b.toString());};
        retList.sort(comp);
        return retList;
    }

    public String getID() {

        return this.componentID;
    }

    //TODO
    List<A_Component> getSubtree() {

        return new ArrayList<>();
    }

    //TODO
    List<A_Component> getTree() {

        return new ArrayList<>();
    }

    public boolean hasConnectorToParent() {

        return !(parentConnector == null);
    }

    //TODO
    public int hashCode() {

        return 0;
    }

    public boolean isRoot() {

        return this.root;
    }

    void setConnectorToParent(Connector connector) {
        if(connector == null ||isRoot())
            throw new TaskException("Must have a valid connector input");

        if(this.parentConnector != null)
            throw new TaskException("Cannot set parent, already has a parent");

        this.parentConnector = connector;
    }

    public String toString() {

        return this.componentID;
    }


    private boolean nameChecker(ComponentBox iChildComponentBox) throws TaskException {

        //A_Component temp = this;

        List<A_Component> list = getTopLevel(this).getDescendants();
        list.add(getTopLevel(this));

        List<A_Component> inList = iChildComponentBox.getDescendants();
        inList.add(iChildComponentBox);

        for (A_Component a : list) {

            for (A_Component b : inList) {
                if (a.getID().compareToIgnoreCase(b.getID()) == 0)
                    return false;
            }
        }

        return true;


    }

    private A_Component getTopLevel(A_Component iComponentBox) {

        if (iComponentBox.hasConnectorToParent()) {
            iComponentBox = getTopLevel(iComponentBox.getConnectorToParent().getParentBox());
        }

        return iComponentBox;

    }


    private void constructorChecker() throws TaskException{

        if(this.componentID == null || this.componentID.isEmpty())
            throw new TaskException("Must have a unique ID");

    }


    private List<A_Component> getDescendantRecursion(List<A_Component> list)
    {
        List<A_Component> retList = new ArrayList<>();
        for(A_Component comp: list)
        {
            retList.add(comp);
            retList.addAll(getDescendantRecursion(comp.getChildren()));
        }
        return retList;
    }
}
