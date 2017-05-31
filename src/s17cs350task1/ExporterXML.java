package s17cs350task1;

/**
 * Created by bcxtr on 5/30/2017.
 */
public class ExporterXML extends A_Exporter{

    private String export = "";

    public ExporterXML(){

        if(!isClosed()) {

            export += "<components>\n";
        }

    }

    @Override
    void addPoint(String id, Point3D point) {

        export += "<point id=\"" + id + "\" x=\"" + point.getX() + "\" y=\"" + point.getY() + "\" z=\"" + point.getZ()
                + "\"/>\n";


    }

    @Override
    void closeComponentNode(String id) {
        export += "</component>\n";
    }

    @Override
    void openComponentNode(String id) {

        export += "<component id=\"" + id + "\" isRoot=\"false\">\n";

    }

    @Override
    void openComponentNode(String id, String idParent) {

        export += "<component id=\"" + id + "\" isRoot=\"" +  "\"" + "parent-id=\""  + idParent + "\">\n";

    }

    @Override
    public String export(){

        return export;
    }
}
