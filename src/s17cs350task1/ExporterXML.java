package s17cs350task1;

/**
 * Created by bcxtr on 5/30/2017.
 */
public class ExporterXML extends A_Exporter{

    public ExporterXML(){



    }

    @Override
    void addPoint(String id, Point3D point) {

        String added = "<point id=\"" + id + "\" x=\"" + point.getX() + "\" y=\"" + point.getY() + "\" z=\"" + point.getZ()
                + "\"/>";


    }

    @Override
    void closeComponentNode(String id) {
        String close = "</component>";
    }

    @Override
    void openComponentNode(String id) {


    }

    @Override
    void openComponentNode(String id, String idParent) {

    }

    @Override
    public String export(){
        return "";
    }
}
