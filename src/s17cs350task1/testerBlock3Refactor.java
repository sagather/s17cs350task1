package s17cs350task1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mgilge on 5/21/17.
 */
public class testerBlock3Refactor
{

    private String _report;
    private int _scoreYour;


    public testerBlock3Refactor()
    {
        this._report = "";
        this._scoreYour = 0;
    }

    public void runTests()
    {
        /*
        this.checkBoundingBox();
        this.frameGenerationTest();
        System.out.println();
        System.out.println("**** The following tests what was broken in the first submission! ****\n");
        this.testBrokenStuffFromBlock2();

        this.checkExceptionDimension3D();
        this.checkConnector();
        this.checkBox();
        this.checkBoxIDs();
        this.checkBoxCycles();
        this.messWithBoxReturnsA();
        this.messWithBoxReturnsB();
        this.messWithBoxReturnsC();
        this.printTappanResults();
        */
        this.xmlExportText();
    }

    private void xmlExportText()
    {
        ComponentBox boxA = new ComponentBox("A", new Dimension3D (1 , 2,  3), true);
        ComponentBox boxB = new ComponentBox("B", new Dimension3D( 4,  6 , 8), false);
        ComponentBox boxC = new ComponentBox("C", new Dimension3D(10, 20, 30), false);
        ComponentBox boxD = new ComponentBox("D", new Dimension3D( 5, 15, 25), false);
        ComponentBox boxE = new ComponentBox("E", new Dimension3D( 7, 14, 21), false);
        boxA.connectChild(new Connector(boxE, new Point3D( 4,  5,  6)));
        boxA.connectChild(new Connector(boxB, new Point3D( 3,  2,  1)));
        boxB.connectChild(new Connector(boxC, new Point3D( 9,  8,  7)));
        boxB.connectChild(new Connector(boxD, new Point3D(10, 12, 14)));
        ExporterXML exporter = new ExporterXML();
        System.out.println(boxA.export(exporter));
    }

    private void printTappanResults()
    {
        System.out.println();
        System.out.println();
        System.out.println("The following is Tappans own test from block 1\n");

        System.out.println("EXECUTE");
        System.out.println();
        System.out.println(this._report);
        System.out.println("Score: " + this._scoreYour + " (FROM 27) = " + (this._scoreYour/27.0) * 100.0);
    }

    private void testBrokenStuffFromBlock2()
    {
        A_Component boxA = new ComponentBox("boxA", new Dimension3D(1,2,3), true);
        A_Component boxB = new ComponentBox("boxB", new Dimension3D(10,16.5,20));
        A_Component boxC = new ComponentBox("boxC", new Dimension3D(11,13,13.25));

        boxA.connectChild(new Connector(boxB, new Point3D(6,13.25,3)));
        boxB.connectChild(new Connector(boxC, new Point3D(9.5,-7.75, 11.25)));

        BoundingBox boundingBox = boxB.generateBoundingBoxAll();

        ArrayList<Point3D> anotherList = (ArrayList<Point3D>) boundingBox.generateCorners();
        System.out.println();
        System.out.println("TEST VOLUME, should be 12543.75");
        System.out.println("Actual: " + boundingBox.calculateVolume());


        System.out.println("TEST AREA XY, should be 900.0");
        System.out.println("Actual: " + boundingBox.calculateArea(BoundingBox.E_Plane.XY));


        System.out.println("TEST AREA YZ, should be 1254.375");
        System.out.println("Actual: " + boundingBox.calculateArea(BoundingBox.E_Plane.YZ));


        System.out.println("TEST AREA XZ, should be 1115.0");
        System.out.println("Actual: " + boundingBox.calculateArea(BoundingBox.E_Plane.XZ));



        System.out.println("Generating Corners should be: ");
        System.out.println("[1, -1, -7], [21, -1.0, -7], [21, -1.0, 20.875], [1, -1, 20.875], [1, 21.5, -7], [21, 21.5, 7], [21, 21.5, 20.875], [1, 21.5, 20.875]");
        System.out.print("Actual: ");

        for(Point3D point3D : anotherList)
        {
            System.out.print(point3D + ", ");
        }

        System.out.println();
        System.out.println("Testing center of mas all, should be: Point3D [x = 11.0, y = 10.25, z = 6.9375]");
        System.out.println(boundingBox.getCenter());
        System.out.println();
        System.out.println();
    }
    private void frameGenerationTest()
    {
        A_Component boxA = new ComponentBox("boxA", new Dimension3D(2,2,2), true);
        A_Component boxB = new ComponentBox("boxB", new Dimension3D(4,4,4));
        boxA.connectChild(new Connector(boxB, new Point3D(2,2,0)));

        ArrayList<Point3D> pointList = (ArrayList<Point3D>) boxA.generateFrameSelf();

        System.out.println();
        System.out.println();
        System.out.println("Testing Frame Generation");

        for(Point3D point3D : pointList)
        {
            System.out.print(point3D + ", ");
        }

        ArrayList<List<Point3D>> anotherList = (ArrayList<List<Point3D>>) boxA.generateFramesAll();
        System.out.println();
        System.out.println();


        for(List list : anotherList)
        {
            for(int x = 0; x < list.size(); x++)
            {
                Point3D point3D = (Point3D) list.get(x);
                System.out.print(point3D + ", ");
            }
            System.out.println();
        }
    }
    private void checkBoundingBox()
    {
        ArrayList<Point3D> cornerList;
        //build boxes from spreadsheet
        ComponentBox boxA = new ComponentBox("boxA", new Dimension3D(1,2,3), true);
        ComponentBox boxB = new ComponentBox("boxB", new Dimension3D(4,6,8));
        ComponentBox boxC = new ComponentBox("boxC", new Dimension3D(10,20,30));
        ComponentBox boxD = new ComponentBox("boxD", new Dimension3D(5,15,25));
        ComponentBox boxE = new ComponentBox("boxE", new Dimension3D(7,14,21));

        //connect boxes as described
        boxA.connectChild(new Connector(boxE, new Point3D(4,5,6)));
        boxA.connectChild(new Connector(boxB, new Point3D(3,2,1)));
        boxB.connectChild(new Connector(boxC, new Point3D(9,8,7)));
        boxB.connectChild(new Connector(boxD, new Point3D(10,12,14)));

        //construct bounding boxes
        BoundingBox boundingA = new BoundingBox(boxA.getAbsoluteCenterPosition(), boxA.getSize());
        BoundingBox boundingB = new BoundingBox(boxB.getAbsoluteCenterPosition(), boxB.getSize());
        BoundingBox boundingC = new BoundingBox(boxC.getAbsoluteCenterPosition(), boxC.getSize());
        BoundingBox boundingD = new BoundingBox(boxD.getAbsoluteCenterPosition(), boxD.getSize());
        BoundingBox boundingE = new BoundingBox(boxE.getAbsoluteCenterPosition(), boxE.getSize());


        System.out.println();
        System.out.println();

        System.out.println("boxA results");
        System.out.println("The center of mass is :" + boxA.calculateCenterOfMassSelf());
        System.out.println("Volume should be 6, volume is : " + boundingA.calculateVolume());
        System.out.println("Area.xy should be 4 is: " + boundingA.calculateArea(BoundingBox.E_Plane.XY));
        System.out.println("Area.yz should be 12 is: " + boundingA.calculateArea(BoundingBox.E_Plane.YZ));
        System.out.println("Area.xz should be 6 is: " + boundingA.calculateArea(BoundingBox.E_Plane.XZ));
        cornerList = (ArrayList<Point3D>) boundingA.generateCorners();
        System.out.println("Generating Corners should be: ");
        System.out.println("[-0.5, -1.0, -1.5], [0.5, -1.0, -1.5], [0.5, -1.0, 1.5], [-0.5, -1.0, 1.5], [-0.5, 1.0, -1.5], [0.5, 1.0, -1.5], [0.5, 1.0, 1.5], [-0.5, 1.0, 1.5]");
        System.out.println("Actual:");
        String str = "";
        for(Point3D point3D : cornerList)
        {
            double x = point3D.getX();
            double y = point3D.getY();
            double z = point3D.getZ();
            str += "[" + x + ", " + y + ", " + z + "], ";
        }
        System.out.println(str);


        System.out.println();
        System.out.println("boxB results");
        System.out.println("The center of mass is :" + boxB.calculateCenterOfMassSelf().toString());
        System.out.println("Volume should be 192, volume is : " + boundingB.calculateVolume());
        System.out.println("Area.xy should be 48 is: " + boundingB.calculateArea(BoundingBox.E_Plane.XY));
        System.out.println("Area.yz should be 96 is: " + boundingB.calculateArea(BoundingBox.E_Plane.YZ));
        System.out.println("Area.xz should be 64 is: " + boundingB.calculateArea(BoundingBox.E_Plane.XZ));
        cornerList = (ArrayList<Point3D>) boundingB.generateCorners();
        System.out.println("Generating Corners should be: ");
        System.out.println("[1.0, -1.0, -3.0], [5.0, -1.0, -3.0], [5.0, -1.0, 5.0], [1.0, -1.0, 5.0], [1.0, 5.0, -3.0], [5.0, 5.0, -3.0], [5.0, 5.0, 5.0], [1.0, 5.0, 5.0]");
        System.out.println("Actual:");
        str = "";
        for(Point3D point3D : cornerList)
        {
            double x = point3D.getX();
            double y = point3D.getY();
            double z = point3D.getZ();
            str += "[" + x + ", " + y + ", " + z + "], ";
        }
        System.out.println(str);

        System.out.println();
        System.out.println("boxC results");
        System.out.println("The center of mass is :" + boxC.calculateCenterOfMassSelf().toString());
        System.out.println("Volume should be 6000, volume is : " + boundingC.calculateVolume());
        System.out.println("Area.xy should be 400 is: " + boundingC.calculateArea(BoundingBox.E_Plane.XY));
        System.out.println("Area.yz should be 1200 is: " + boundingC.calculateArea(BoundingBox.E_Plane.YZ));
        System.out.println("Area.xz should be 600 is: " + boundingC.calculateArea(BoundingBox.E_Plane.XZ));
        cornerList = (ArrayList<Point3D>) boundingC.generateCorners();
        System.out.println("Generating Corners should be: ");
        System.out.println("[7.0, 0.0, -7.0], [17.0, 0.0, -7.0], [17.0, 0.0, 23.0], [7.0, 0.0, 23.0], [7.0, 20.0, -7.0], [17.0, 20.0, -7.0], [17.0, 20.0, 23.0], [7.0, 20.0, 23.0]");
        System.out.println("Actual:");
        str = "";
        for(Point3D point3D : cornerList)
        {
            double x = point3D.getX();
            double y = point3D.getY();
            double z = point3D.getZ();
            str += "[" + x + ", " + y + ", " + z + "], ";
        }
        System.out.println(str);

        System.out.println();
        System.out.println("boxD results");
        System.out.println("The center of mass is :" + boxD.calculateCenterOfMassSelf().toString());
        System.out.println("Volume should be 1875, volume is : " + boundingD.calculateVolume());
        System.out.println("Area.xy should be 150 is: " + boundingD.calculateArea(BoundingBox.E_Plane.XY));
        System.out.println("Area.yz should be 750 is: " + boundingD.calculateArea(BoundingBox.E_Plane.YZ));
        System.out.println("Area.xz should be 250 is: " + boundingD.calculateArea(BoundingBox.E_Plane.XZ));
        cornerList = (ArrayList<Point3D>) boundingD.generateCorners();
        System.out.println("Generating Corners should be: ");
        System.out.println("[10.5, 6.5, 2.5], [15.5, 6.5, 2.5], [15.5, 6.5, 27.5], [10.5, 6.5, 27.5], [10.5, 21.5, 2.5], [15.5, 21.5, 2.5], [15.5, 21.5, 27.5], [10.5, 21.5, 27.5]");
        System.out.println("Actual:");
        str = "";
        for(Point3D point3D : cornerList)
        {
            double x = point3D.getX();
            double y = point3D.getY();
            double z = point3D.getZ();
            str += "[" + x + ", " + y + ", " + z + "], ";
        }
        System.out.println(str);

        System.out.println();
        System.out.println("boxE results");
        System.out.println("The center of mass is :" + boxE.calculateCenterOfMassSelf().toString());
        System.out.println("Volume should be 2085, volume is : " + boundingE.calculateVolume());
        System.out.println("Area.xy should be 196 is: " + boundingE.calculateArea(BoundingBox.E_Plane.XY));
        System.out.println("Area.yz should be 588 is: " + boundingE.calculateArea(BoundingBox.E_Plane.YZ));
        System.out.println("Area.xz should be 294 is: " + boundingE.calculateArea(BoundingBox.E_Plane.XZ));
        cornerList = (ArrayList<Point3D>) boundingE.generateCorners();
        System.out.println("Generating Corners should be: ");
        System.out.println("[0.5, -2.0, -4.5], [7.5, -2.0, -4.5], [7.5, -2.0, 16.5], [0.5, -2.0, 16.5], [0.5, 12.0, -4.5], [7.5, 12.0, -4.5], [7.5, 12.0, 16.5], [0.5, 12.0, 16.5]");
        System.out.println("Actual:");
        str = "";
        for(Point3D point3D : cornerList)
        {
            double x = point3D.getX();
            double y = point3D.getY();
            double z = point3D.getZ();
            str += "[" + x + ", " + y + ", " + z + "], ";
        }
        System.out.println(str);

        System.out.println("The center of mass of all of the boxes is: " + boxA.calculateCenterOfMassAll());
        System.out.println("Volume of all of the boxes is: " + boxA.calculateVolumeAll());
        System.out.println("Area.xy of all of the boxes is: " + boxA.calculateAreaAll(BoundingBox.E_Plane.XY));
        System.out.println("Area.xz of all of the boxes is: " + boxA.calculateAreaAll(BoundingBox.E_Plane.XZ));
        System.out.println("Area.yz of all of the boxes is: " + boxA.calculateAreaAll(BoundingBox.E_Plane.YZ));


        System.out.println();
        System.out.println();
        BoundingBox genBox = boxA.generateBoundingBoxAll();
        cornerList = (ArrayList<Point3D>) genBox.generateCorners();
        System.out.println("Testing BoundingBox Extension");
        System.out.println("Generating Corners should be: ");
        System.out.println("[-0.5, -2.0, -7.0], [17.0, -2.0, -7.0], [17.0, -2.0, 27.5], [-0.5, -2.0, 27.5], [-0.5, 21.5, -7.0], [17.0, 21.5, -7.0], [17.0, 21.5, 27.5], [-0.5, 21.5, 27.5]");
        System.out.println("Actual:");
        str = "";
        for(Point3D point3D : cornerList)
        {
            double x = point3D.getX();
            double y = point3D.getY();
            double z = point3D.getZ();
            str += "[" + x + ", " + y + ", " + z + "], ";
        }
        System.out.println(str);

        System.out.println();
        System.out.println();
        System.out.println("Testing Enum Shizzle");
        for(BoundingBox.E_Plane c : BoundingBox.E_Plane.values())
        {
            System.out.println(c);
        }
        System.out.println(BoundingBox.E_Plane.valueOf("XY"));
        System.out.println(BoundingBox.E_Plane.valueOf("XZ"));
        System.out.println(BoundingBox.E_Plane.valueOf("YZ"));
    }

    private void checkExceptionDimension3D()
    {
        // ----  ----  ----  ----  ----  ----
        try
        {
            _report += (" Dimension3D(1,2,3): ");

            new Dimension3D(1,2,3);

            _scoreYour++;

            _report += ("#OK#\n");
        }
        catch(TaskException exception)
        {
            _report += ("fail ("+ exception.getMessage() + ")\n");
        }

        // ----  ----  ----  ----  ----  ----
        try
        {
            _report += (" Dimension3D(-1,2,3): ");

            new Dimension3D(-1,2,3);

            _report += ("fail\n");
        }
        catch(TaskException exception)
        {
            _scoreYour++;

            _report += ("#OK# ("+ exception.getMessage() + ")\n");
        }


        // ----  ----  ----  ----  ----  ----
        try
        {
            _report += (" Dimension3D(1,-2,3): ");

            new Dimension3D(1,-2,3);

            _report += ("fail\n");
        }
        catch(TaskException exception)
        {
            _scoreYour++;

            _report += ("#OK# ("+ exception.getMessage() + ")\n");
        }


        // ----  ----  ----  ----  ----  ----
        try
        {
            _report += (" Dimension3D(1,2,-3): ");

            new Dimension3D(1,2,-3);

            _report += ("fail\n");
        }
        catch(TaskException exception)
        {
            _scoreYour++;

            _report += ("#OK# ("+ exception.getMessage() + ")\n");
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    private void checkConnector()
    {
        // ----  ----  ----  ----  ----  ----
        try
        {
            _report += (" Connector(null, valid): ");

            new Connector(null, new Point3D(1,2,3));

            _report += ("fail\n");
        }
        catch(TaskException exception)
        {
            _scoreYour++;

            _report += ("#OK# ("+ exception.getMessage() + ")\n");
        }

        // ----  ----  ----  ----  ----  ----
        try
        {
            _report += (" Connector(valid, null): ");

            new Connector(new ComponentBox("box", new Dimension3D(1, 2, 3)), null);

            _report += ("fail\n");
        }
        catch(TaskException exception)
        {
            _scoreYour++;

            _report += ("#OK# ("+ exception.getMessage() + ")\n");
        }


        // ----  ----  ----  ----  ----  ----
        try
        {
            _report += (" Connector(valid, valid) get parent box, parent not set: ");

            Connector connector = new Connector(new ComponentBox("box", new Dimension3D(1, 2, 3)), new Point3D(1,2,3));

            connector.getComponentParent();

            _report += ("fail\n");
        }
        catch(TaskException exception)
        {
            _scoreYour++;

            _report += ("#OK# ("+ exception.getMessage() + ")\n");
        }

        // ----  ----  ----  ----  ----  ----
        try
        {
            _report += (" Connector(valid, valid) setParentBox null: ");

            Connector connector = new Connector(new ComponentBox("box", new Dimension3D(1, 2, 3)), new Point3D(1,2,3));

            connector.setComponentParent(null);

            _report += ("fail\n");
        }
        catch(TaskException exception)
        {
            _scoreYour++;

            _report += ("#OK# ("+ exception.getMessage() + ")\n");
        }

        // ----  ----  ----  ----  ----  ----
        try
        {
            _report += (" Connector(valid, valid) setParentBox already set: ");

            Connector connector = new Connector(new ComponentBox("box", new Dimension3D(1, 2, 3)), new Point3D(1,2,3));

            A_Component box1 = new ComponentBox("box1", new Dimension3D(1, 2, 3));
            A_Component box2 = new ComponentBox("box2", new Dimension3D(1, 2, 3));

            connector.setComponentParent(box1);
            connector.setComponentParent(box2);

            _report += ("fail\n");
        }
        catch(TaskException exception)
        {
            _scoreYour++;

            _report += ("#OK# ("+ exception.getMessage() + ")\n");
        }

        // ----  ----  ----  ----  ----  ----
        try
        {
            _report += (" Connector(valid, valid) child box root: ");

            new Connector(new ComponentBox("box", new Dimension3D(1, 2, 3), true), new Point3D(1,2,3));

            _report += ("fail\n");
        }
        catch(TaskException exception)
        {
            _scoreYour++;

            _report += ("#OK# ("+ exception.getMessage() + ")\n");
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    private void checkBox()
    {
        // ----  ----  ----  ----  ----  ----
        try
        {
            _report += (" Box(null, valid): ");

            new ComponentBox(null, new Dimension3D(1,2,3));

            _report += ("fail\n");
        }
        catch(TaskException exception)
        {
            _scoreYour++;

            _report += ("#OK# ("+ exception.getMessage() + ")\n");
        }

        // ----  ----  ----  ----  ----  ----
        try
        {
            _report += (" Box(empty, valid): ");

            new ComponentBox("", new Dimension3D(1,2,3));

            _report += ("fail\n");
        }
        catch(TaskException exception)
        {
            _scoreYour++;

            _report += ("#OK# ("+ exception.getMessage() + ")\n");
        }

        // ----  ----  ----  ----  ----  ----
        try
        {
            _report += (" Box(valid, null): ");

            new ComponentBox("id", null);

            _report += ("fail\n");
        }
        catch(TaskException exception)
        {
            _scoreYour++;

            _report += ("#OK# ("+ exception.getMessage() + ")\n");
        }

        // ----  ----  ----  ----  ----  ----
        try
        {
            _report += (" Box connectChild(null): ");

            A_Component box = new ComponentBox("id", new Dimension3D(1,2,3));

            box.connectChild(null);

            _report += ("fail\n");
        }
        catch(TaskException exception)
        {
            _scoreYour++;

            _report += ("#OK# ("+ exception.getMessage() + ")\n");
        }

        // ----  ----  ----  ----  ----  ----
        try
        {
            _report += (" Box getAbsoluteCenterPosition() no root: ");

            A_Component box = new ComponentBox("id", new Dimension3D(1,2,3));

            box.getAbsoluteCenterPosition();

            _report += ("fail\n");
        }
        catch(TaskException exception)
        {
            _scoreYour++;

            _report += ("#OK# ("+ exception.getMessage() + ")\n");
        }

        // ----  ----  ----  ----  ----  ----
        try
        {
            _report += (" Box getConnectorToParent() no parent: ");

            A_Component box = new ComponentBox("id", new Dimension3D(1,2,3));

            box.getConnectorToParent();

            _report += ("fail\n");
        }
        catch(TaskException exception)
        {
            _scoreYour++;

            _report += ("#OK# ("+ exception.getMessage() + ")\n");
        }

        // ----  ----  ----  ----  ----  ----
        try
        {
            _report += (" Box setConnectorToParent(null): ");

            A_Component box = new ComponentBox("id", new Dimension3D(1,2,3));

            box.setConnectorToParent(null);

            _report += ("fail\n");
        }
        catch(TaskException exception)
        {
            _scoreYour++;

            _report += ("#OK# ("+ exception.getMessage() + ")\n");
        }

        // ----  ----  ----  ----  ----  ----
        try
        {
            _report += (" Box setConnectorToParent() already set: ");

            A_Component boxParent  = new ComponentBox("boxParent", new Dimension3D(1, 2, 3));
            A_Component boxChild1  = new ComponentBox("boxChild",  new Dimension3D(1, 2, 3));
            A_Component boxChild2  = new ComponentBox("boxChild",  new Dimension3D(1, 2, 3));

            Connector connector1 = new Connector(boxChild1, new Point3D(1,2,3));
            Connector connector2 = new Connector(boxChild2, new Point3D(1,2,3));

            boxParent.setConnectorToParent(connector1);
            boxParent.setConnectorToParent(connector2);

            _report += ("fail\n");
        }
        catch(TaskException exception)
        {
            _scoreYour++;

            _report += ("#OK# ("+ exception + ")\n");
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    private void checkBoxIDs()
    {
        // ----  ----  ----  ----  ----  ----
        try
        {
            _report += (" Box duplicate IDs 1: ");

//           System.out.println("$ checkBoxIDs:1");

            A_Component box1A = new ComponentBox("boxA", new Dimension3D(1,1,1));
            A_Component box1B = new ComponentBox("boxB", new Dimension3D(1,1,1));
            A_Component box1C = new ComponentBox("boxC", new Dimension3D(1,1,1));
            A_Component box1D = new ComponentBox("boxD", new Dimension3D(1,1,1));
            A_Component box1E = new ComponentBox("boxa", new Dimension3D(1,1,1));

//               System.out.println("$ checkBoxIDs:2");

            box1A.connectChild(new Connector(box1B, new Point3D(0,0,0)));
            box1A.connectChild(new Connector(box1C, new Point3D(0,0,0)));
            box1C.connectChild(new Connector(box1D, new Point3D(0,0,0)));
            box1C.connectChild(new Connector(box1E, new Point3D(0,0,0)));

//               System.out.println("$ checkBoxIDs:3");

            _report += ("fail\n");
        }
        catch(TaskException exception)
        {
            _scoreYour++;

            _report += ("#OK# ("+ exception.getMessage() + ")\n");
        }

        // ----  ----  ----  ----  ----  ----
        try
        {
            _report += (" Box duplicate IDs 2: ");

//           System.out.println("$ checkBoxIDs:4");

            A_Component box1A = new ComponentBox("boxA", new Dimension3D(1,1,1));
            A_Component box1B = new ComponentBox("boxB", new Dimension3D(1,1,1));
            A_Component box1C = new ComponentBox("boxC", new Dimension3D(1,1,1));
            A_Component box1D = new ComponentBox("boxD", new Dimension3D(1,1,1));
            A_Component box1E = new ComponentBox("boxE", new Dimension3D(1,1,1));

            box1A.connectChild(new Connector(box1B, new Point3D(0,0,0)));
            box1A.connectChild(new Connector(box1C, new Point3D(0,0,0)));
            box1C.connectChild(new Connector(box1D, new Point3D(0,0,0)));
            box1C.connectChild(new Connector(box1E, new Point3D(0,0,0)));

//               System.out.println("$ checkBoxIDs:5");

            //
            A_Component box2A = new ComponentBox("boxA", new Dimension3D(1,1,1));
            A_Component box2B = new ComponentBox("boxB", new Dimension3D(1,1,1));
            A_Component box2C = new ComponentBox("boxC", new Dimension3D(1,1,1));
            A_Component box2D = new ComponentBox("boxD", new Dimension3D(1,1,1));
            A_Component box2E = new ComponentBox("boxE", new Dimension3D(1,1,1));

            box2A.connectChild(new Connector(box2B, new Point3D(0,0,0)));
            box2A.connectChild(new Connector(box2C, new Point3D(0,0,0)));
            box2C.connectChild(new Connector(box2D, new Point3D(0,0,0)));
            box2C.connectChild(new Connector(box2E, new Point3D(0,0,0)));

//               System.out.println("$ checkBoxIDs:6");

            _scoreYour++;

            _report += ("#OK#\n");
        }
        catch(TaskException exception)
        {
            _report += ("fail ("+ exception.getMessage() + ")\n");
        }

        // ----  ----  ----  ----  ----  ----
        try
        {
            _report += (" Box duplicate IDs 3: ");

//           System.out.println("$ checkBoxIDs:7");

            A_Component box1A = new ComponentBox("box1A", new Dimension3D(1,1,1));
            A_Component box1B = new ComponentBox("box1B", new Dimension3D(1,1,1));
            A_Component box1C = new ComponentBox("box1C", new Dimension3D(1,1,1));
            A_Component box1D = new ComponentBox("box1D", new Dimension3D(1,1,1));
            A_Component box1E = new ComponentBox("boxE", new Dimension3D(1,1,1));

            box1A.connectChild(new Connector(box1B, new Point3D(0,0,0)));
            box1A.connectChild(new Connector(box1C, new Point3D(0,0,0)));
            box1C.connectChild(new Connector(box1D, new Point3D(0,0,0)));
            box1C.connectChild(new Connector(box1E, new Point3D(0,0,0)));

//               System.out.println("$ checkBoxIDs:8");

            //
            A_Component box2A = new ComponentBox("box2A", new Dimension3D(1,1,1));
            A_Component box2B = new ComponentBox("box2B", new Dimension3D(1,1,1));
            A_Component box2C = new ComponentBox("box2C", new Dimension3D(1,1,1));
            A_Component box2D = new ComponentBox("box2D", new Dimension3D(1,1,1));
            A_Component box2E = new ComponentBox("boxE", new Dimension3D(1,1,1));

            box2A.connectChild(new Connector(box2B, new Point3D(0,0,0)));
            box2A.connectChild(new Connector(box2C, new Point3D(0,0,0)));
            box2C.connectChild(new Connector(box2D, new Point3D(0,0,0)));
            box2C.connectChild(new Connector(box2E, new Point3D(0,0,0)));

//               System.out.println("$ checkBoxIDs:9");

            box1E.connectChild(new Connector(box2A, new Point3D(0,0,0)));

//               System.out.println("$ checkBoxIDs:10");

            _report += ("fail\n");
        }
        catch(TaskException exception)
        {
            _scoreYour++;

            _report += ("#OK# ("+ exception.getMessage() + ")\n");
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    private void checkBoxCycles()
    {
        // ----  ----  ----  ----  ----  ----
        try
        {
            _report += (" Box cycle 1: ");

            A_Component box1A = new ComponentBox("boxA", new Dimension3D(1,1,1));
            A_Component box1B = new ComponentBox("boxB", new Dimension3D(1,1,1));
            A_Component box1C = new ComponentBox("boxC", new Dimension3D(1,1,1));
            A_Component box1D = new ComponentBox("boxD", new Dimension3D(1,1,1));
            A_Component box1E = new ComponentBox("boxE", new Dimension3D(1,1,1));

            box1A.connectChild(new Connector(box1E, new Point3D(0,0,0)));
            box1A.connectChild(new Connector(box1B, new Point3D(0,0,0)));
            box1A.connectChild(new Connector(box1C, new Point3D(0,0,0)));
            box1C.connectChild(new Connector(box1D, new Point3D(0,0,0)));
            box1C.connectChild(new Connector(box1E, new Point3D(0,0,0)));

            _report += ("fail\n");
        }
        catch(TaskException exception)
        {
            _scoreYour++;

            _report += ("#OK# ("+ exception.getMessage() + ")\n");
        }

        // ----  ----  ----  ----  ----  ----
        try
        {
            _report += (" Box cycle 2: ");

            A_Component box1A = new ComponentBox("boxA", new Dimension3D(1,1,1));
            A_Component box1B = new ComponentBox("boxB", new Dimension3D(1,1,1));

            box1A.connectChild(new Connector(box1B, new Point3D(0,0,0)));
            box1B.connectChild(new Connector(box1A, new Point3D(0,0,0)));

            _report += ("fail\n");
        }
        catch(TaskException exception)
        {
            _scoreYour++;

            _report += ("#OK# ("+ exception.getMessage() + ")\n");
        }

        // ----  ----  ----  ----  ----  ----
        try
        {
            _report += (" Box cycle 3: ");

            A_Component box1A = new ComponentBox("boxA", new Dimension3D(1,1,1));

            box1A.connectChild(new Connector(box1A, new Point3D(0,0,0)));

            _report += ("fail\n");
        }
        catch(TaskException exception)
        {
            _scoreYour++;

            _report += ("#OK# ("+ exception.getMessage() + ")\n");
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    private void messWithBoxReturnsA()
    {
        _report += " Box check getChildBoxes(): ";

        A_Component box1A = new ComponentBox("boxA", new Dimension3D(1,1,1));
        A_Component box1B = new ComponentBox("boxB", new Dimension3D(1,1,1));
        A_Component box1C = new ComponentBox("boxC", new Dimension3D(1,1,1));
        A_Component box1D = new ComponentBox("boxD", new Dimension3D(1,1,1));
        A_Component box1E = new ComponentBox("boxE", new Dimension3D(1,1,1));

        box1A.connectChild(new Connector(box1B, new Point3D(0,0,0)));
        box1A.connectChild(new Connector(box1C, new Point3D(0,0,0)));
        box1A.connectChild(new Connector(box1D, new Point3D(0,0,0)));
        box1A.connectChild(new Connector(box1E, new Point3D(0,0,0)));

        List<A_Component> boxesChildPre = box1A.getChildren();

        try
        {
            boxesChildPre.clear();

            List<A_Component> boxesChildPost = box1A.getChildren();

            if (4 != boxesChildPost.size())
            {
                _report += ("messed with internals; expected 4 ; got " + boxesChildPost.size() + "\n");
            }
            else
            {
                _scoreYour++;

                _report += ("#OK# [1]\n");
            }
        }
        catch(UnsupportedOperationException exception)
        {
            _scoreYour++;

            _report += ("#OK# [2]\n");
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    private void messWithBoxReturnsB()
    {
        _report += " Box check getDescendantBoxes(): ";

        A_Component box1A = new ComponentBox("boxA", new Dimension3D(1,1,1));
        A_Component box1B = new ComponentBox("boxB", new Dimension3D(1,1,1));
        A_Component box1C = new ComponentBox("boxC", new Dimension3D(1,1,1));
        A_Component box1D = new ComponentBox("boxD", new Dimension3D(1,1,1));
        A_Component box1E = new ComponentBox("boxE", new Dimension3D(1,1,1));

        box1A.connectChild(new Connector(box1B, new Point3D(0,0,0)));
        box1A.connectChild(new Connector(box1C, new Point3D(0,0,0)));
        box1A.connectChild(new Connector(box1D, new Point3D(0,0,0)));
        box1A.connectChild(new Connector(box1E, new Point3D(0,0,0)));

        List<A_Component> boxesChildPre = box1A.getDescendants();

        try
        {
            boxesChildPre.clear();

            List<A_Component> boxesChildPost = box1A.getDescendants();

            if (4 != boxesChildPost.size())
            {
                _report += ("messed with internals; expected 4; got " + boxesChildPost.size() + "\n");
            }
            else
            {
                _scoreYour++;

                _report += ("#OK# [1]\n");
            }
        }
        catch(UnsupportedOperationException exception)
        {
            _scoreYour++;

            _report += ("#OK# [2]\n");
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    private void messWithBoxReturnsC()
    {
        _report += " Box check getConnectorsToChildren(): ";

        A_Component box1A = new ComponentBox("boxA", new Dimension3D(1,1,1));
        A_Component box1B = new ComponentBox("boxB", new Dimension3D(1,1,1));
        A_Component box1C = new ComponentBox("boxC", new Dimension3D(1,1,1));
        A_Component box1D = new ComponentBox("boxD", new Dimension3D(1,1,1));
        A_Component box1E = new ComponentBox("boxE", new Dimension3D(1,1,1));

        box1A.connectChild(new Connector(box1B, new Point3D(0,0,0)));
        box1A.connectChild(new Connector(box1C, new Point3D(0,0,0)));
        box1A.connectChild(new Connector(box1D, new Point3D(0,0,0)));
        box1A.connectChild(new Connector(box1E, new Point3D(0,0,0)));

        List<Connector> connectorsPre = box1A.getConnectorsToChildren();

        try
        {
            connectorsPre.clear();

            List<Connector> connectorsPost = box1A.getConnectorsToChildren();

            if (4 != connectorsPost.size())
            {
                _report += ("messed with internals; expected 4; got " + connectorsPost.size() + "\n");
            }
            else
            {
                _scoreYour++;

                _report += ("#OK# [1]\n");
            }
        }
        catch(UnsupportedOperationException exception)
        {
            _scoreYour++;

            _report += ("#OK# [2]\n");
        }
    }
}
