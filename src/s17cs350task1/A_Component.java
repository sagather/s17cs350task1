package s17cs350task1;

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

    public abstract String export(A_Exporter.export);
}
