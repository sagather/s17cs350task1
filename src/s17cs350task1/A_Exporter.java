package s17cs350task1;

/**
 * Created by bcxtr on 5/25/2017.
 */
public abstract class A_Exporter {

    private boolean closed = false;

    public A_Exporter(){

    }

    abstract void addPoint(String id, Point3D point);

    void append(String data, boolean... withNewLine){



    }

    abstract void closeComponentNode(String id);

    public void closeExport(){

        export();
        closed = true;

    }

    public String export(){

        return "</components>";
    }

    public boolean isClosed(){

        return closed;
    }

    abstract void openComponentNode(String id);

    abstract void openComponentNode(String id, String idParent);

    void validateOpen() throws TaskException{


    }
    

}
