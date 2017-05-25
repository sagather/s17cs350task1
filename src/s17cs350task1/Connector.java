package s17cs350task1;

//import javafx.geometry.Point3D;

/**
 * Created by Samantha Agather on 4/3/2017.
 */
public class Connector implements Cloneable{

    private Box childBox = null, parentBox = null;
    private Point3D offset = null;

    Connector(Box iChildBox, Point3D iOffset) {

        this.childBox = iChildBox;
        this.offset = iOffset;
        if(hasParentBox())
            throw new TaskException("Cannot have more than one parent");
        constructionChecker();
        connectionChecker();

    }

    @Override
    public Connector clone() throws CloneNotSupportedException{  //5 lines of code expected

        Connector connectorClone = (Connector) super.clone();  //clone original object
        connectorClone.childBox = this.childBox.clone();  //clone the child box
        connectorClone.childBox.setConnectorToParent(connectorClone);  //set parent connector
        connectorClone.offset = new Point3D(this.offset.getX(), this.offset.getY(), 0);  //clone offset
        return connectorClone;  //return

    }

    public Box getChildBox()
    {
        if(this.childBox == null)
            throw new TaskException("Child box does not exist");

        return this.childBox;
    }

    public Point3D getOffsetFromParentBox()
    {
        if(this.offset == null)
            throw new TaskException("Offset is invalid");
        return this.offset;
    }

    public Box getParentBox()
    {
        if(this.parentBox == null)
            throw new TaskException("No parent box connected");
        return this.parentBox;
    }

    public boolean hasParentBox(){   return !(this.parentBox == null);   }

    public void setParentBox(Box iParentBox)
    {
        if(iParentBox == null)
            throw new TaskException("Must be a valid box");
        if(this.parentBox != null && this.childBox != null) {
            if (this.parentBox.equals(this.childBox))
                throw new TaskException("Cannot have a parent box that is the child box");
        }
        this.childBox.setConnectorToParent(this);
        this.parentBox = iParentBox;
    }

    public String toString(){   return " ";   }

    private void connectionChecker() throws TaskException{

        if(this.childBox.isRoot())
            throw new TaskException("Cannot connect a root as a child.");

        if(this.childBox == this.parentBox){
            throw new TaskException("You can't connect a child box as a parent, or vice versa");
        }

    }

    private void constructionChecker() throws TaskException{

        if(this.childBox == null)
            throw new TaskException("No null children");

        if(this.offset == null)
            throw new TaskException("Must have a valid offset");

    }


}
