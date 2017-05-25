package s17cs350task1;

//import javafx.geometry.Point3D;
import s17cs350task1.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mgilge on 5/2/17.
 */
public class Tester21
{
    private String _report;
    private int _scoreYour;


    public Tester21()
    {
        this._report = "";
        this._scoreYour = 0;
    }

    public void printResults()
    {
        System.out.println("EXECUTE");
        System.out.println();
        System.out.println(this._report);
        System.out.println("Score: " + this._scoreYour + " (FROM 27) = " + (this._scoreYour/27.0) * 100.0);
    }

    public void runTests()
    {
        this.checkExceptionDimension3D();
        this.checkConnector();
        this.checkBox();
        this.checkBoxIDs();
        this.checkBoxCycles();
        this.messWithBoxReturnsA();
        this.messWithBoxReturnsB();
        this.messWithBoxReturnsC();
        this.checkCloning();

        //below was this submission minus the middle one it was already commented

        this.checkBoundingBox();
        this.checkBoundingBoxExtend();
        this.frameGenerationTest();

        this.goFuckingDockWithYourselfTappan();
    }

    private void goFuckingDockWithYourselfTappan()
    {
        Box boxA = new Box ("boxA", new Dimension3D(1,2,3), true);
        Box boxB = new Box("boxB", new Dimension3D(4,6,8));
        Box boxC = new Box("boxC", new Dimension3D(10,20,30));

        boxA.connectChild(new Connector(boxB, new Point3D(3,2,1)));
        boxB.connectChild(new Connector(boxC, new Point3D(9,8,7)));

        List<List<Point3D>> anotherList = (List<List<Point3D>>) boxC.NEW_generateFramesAll();
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

    private void frameGenerationTest()
    {
        Box boxA = new Box ("boxA", new Dimension3D(2,2,2), true);
        Box boxB = new Box("boxB", new Dimension3D(4,4,4));
        boxA.connectChild(new Connector(boxB, new Point3D(2,2,0)));

        List<Point3D> pointList = (List<Point3D>) boxA.NEW_generateFrameSelf();

        System.out.println();
        System.out.println();
        System.out.println("Testing Frame Generation");

        for(Point3D point3D : pointList)
        {
            System.out.print(point3D + ", ");
        }

        List<List<Point3D>> anotherList = (List<List<Point3D>>) boxA.NEW_generateFramesAll();
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

    private void checkBoundingBoxExtend()
    {
        Box boxA = new Box ("boxA", new Dimension3D(2,2,2), true);
        Box boxB = new Box("boxB", new Dimension3D(4,4,4));
        boxA.connectChild(new Connector(boxB, new Point3D(2,2,0)));


        BoundingBox boundA = boxA.NEW_generateBoundingBoxSelf();
        BoundingBox boundB = boxB.NEW_generateBoundingBoxSelf();
        BoundingBox extBox = boundA.extend(boundB);

        List<Point3D> cornerList;
        cornerList = (List<Point3D>) extBox.generateCorners();
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
    }

    private void checkBoundingBox()
    {
        List<Point3D> cornerList;
        //build boxes from spreadsheet
        Box boxA = new Box ("boxA", new Dimension3D(1,2,3), true);
        Box boxB = new Box("boxB", new Dimension3D(4,6,8));
        Box boxC = new Box("boxC", new Dimension3D(10,20,30));
        Box boxD = new Box("boxD", new Dimension3D(5,15,25));
        Box boxE = new Box("boxE", new Dimension3D(7,14,21));

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
        System.out.println("The center of mass is :" + boxA.NEW_calculateVolumeSelf());
        System.out.println("Volume should be 6, volume is : " + boundingA.calculateVolume());
        System.out.println("Area.xy should be 4 is: " + boundingA.calculateArea(BoundingBox.E_Plane.XY));
        System.out.println("Area.yz should be 12 is: " + boundingA.calculateArea(BoundingBox.E_Plane.YZ));
        System.out.println("Area.xz should be 6 is: " + boundingA.calculateArea(BoundingBox.E_Plane.XZ));
        cornerList = (List<Point3D>) boundingA.generateCorners();
        System.out.println("Generating Corners should be: ");
        System.out.println("[-0.5, -1.0, -1.5], [0.5, -1.0, -1.5], [0.5, -1.0, 1.5], [-0.5, 1.0, 1.5], [-0.5, 1.0, -1.5], [0.5, 1.0, -1.5], [0.5, 1.0, 1.5], [-0.5, -1.0, -1.5]");
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
        System.out.println("The center of mass is :" + boxB.NEW_calculateVolumeSelf());
        System.out.println("Volume should be 6, volume is : " + boundingB.calculateVolume());
        System.out.println("Area.xy should be 48 is: " + boundingB.calculateArea(BoundingBox.E_Plane.XY));
        System.out.println("Area.yz should be 96 is: " + boundingB.calculateArea(BoundingBox.E_Plane.YZ));
        System.out.println("Area.xz should be 64 is: " + boundingB.calculateArea(BoundingBox.E_Plane.XZ));
        cornerList = (List<Point3D>) boundingB.generateCorners();
        System.out.println("Generating Corners should be: ");
        System.out.println("[1.0, -1.0, -3.0], [5.0, -1.0, -3.0], [5.0, -1.0, 5.0], [1.0, -1.0, 5.0], [1.0, 5.0, -3.0], [5.0, 5.0, -3.0], [5.0, 5.0, 5.0], [-0.5, -1.0, -3.0]");
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
        System.out.println("The center of mass is :" + boxC.NEW_calculateVolumeSelf());
        System.out.println("Volume should be 6, volume is : " + boundingC.calculateVolume());
        System.out.println("Area.xy should be 400 is: " + boundingC.calculateArea(BoundingBox.E_Plane.XY));
        System.out.println("Area.yz should be 1200 is: " + boundingC.calculateArea(BoundingBox.E_Plane.YZ));
        System.out.println("Area.xz should be 600 is: " + boundingC.calculateArea(BoundingBox.E_Plane.XZ));
        cornerList = (List<Point3D>) boundingC.generateCorners();
        System.out.println("Generating Corners should be: ");
        System.out.println("[7.0, 0.0, -7.0], [17.0, 0.0, -7.0], [17.0, 0.0, 23.0], [7.0, 0.0, 23.0], [7.0, 20.0, -7.0], [17.0, 20.0, -7.0], [17.0, 20.0, 23.0], [1.0, -1.0, -7.0]");
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
        System.out.println("The center of mass is :" + boxD.NEW_calculateVolumeSelf());
        System.out.println("Volume should be 6, volume is : " + boundingD.calculateVolume());
        System.out.println("Area.xy should be 150 is: " + boundingD.calculateArea(BoundingBox.E_Plane.XY));
        System.out.println("Area.yz should be 750 is: " + boundingD.calculateArea(BoundingBox.E_Plane.YZ));
        System.out.println("Area.xz should be 250 is: " + boundingD.calculateArea(BoundingBox.E_Plane.XZ));
        cornerList = boundingD.generateCorners();
        System.out.println("Generating Corners should be: ");
        System.out.println("[10.5, 6.5, 2.5], [15.5, 6.5, 2.5], [15.5, 6.5, 27.5], [10.5, 6.5, 27.5], [10.5, 21.5, 2.5], [15.5, 21.5, 2.5], [15.5, 21.5, 27.5], [7.0, 0.0, -7.0]");
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
        System.out.println("The center of mass is :" + boxE.NEW_calculateVolumeSelf());
        System.out.println("Volume should be 6, volume is : " + boundingE.calculateVolume());
        System.out.println("Area.xy should be 196 is: " + boundingE.calculateArea(BoundingBox.E_Plane.XY));
        System.out.println("Area.yz should be 588 is: " + boundingE.calculateArea(BoundingBox.E_Plane.YZ));
        System.out.println("Area.xz should be 294 is: " + boundingE.calculateArea(BoundingBox.E_Plane.XZ));
        cornerList = boundingE.generateCorners();
        System.out.println("Generating Corners should be: ");
        System.out.println("[0.5, -2.0, -4.5], [7.5, -2.0, -4.5], [7.5, -2.0, 16.5], [0.5, -2.0, 16.5], [0.5, 12.0, -4.5], [7.5, 12.0, -4.5], [7.5, 12.0, 16.5], [-0.5, -2.0, -4.5]");
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

        System.out.println("The center of mass of all of the boxes is: " + boxA.NEW_calculateCenterOfMassAll());
        System.out.println("Volume of all of the boxes is: " + boxA.NEW_calculateVolumeAll());
        System.out.println("Area.xy of all of the boxes is: " + boxA.NEW_calculateAreaAll(BoundingBox.E_Plane.XY));
        System.out.println("Area.xz of all of the boxes is: " + boxA.NEW_calculateAreaAll(BoundingBox.E_Plane.XZ));
        System.out.println("Area.yz of all of the boxes is: " + boxA.NEW_calculateAreaAll(BoundingBox.E_Plane.YZ));


        System.out.println();
        System.out.println();
        BoundingBox genBox = boxA.NEW_generateBoundingBoxAll();
        cornerList = genBox.generateCorners();
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

    private void checkCloning()
    {
        try
        {
            System.out.println("Checking cloning, numbers should match");
            Box box1 = new Box("box1", new Dimension3D(3, 5, 1), true);
            Box box2 = new Box("box2", new Dimension3D(4, 6, 1));
            Box box3 = new Box("box3", new Dimension3D(8, 7, 1));
            Box box4 = new Box("box4", new Dimension3D(2, 5, 1));
            Box box5 = new Box("box5", new Dimension3D(4, 6, 1));
            Box box6 = new Box("box6", new Dimension3D(8, 7, 1));
            Box box7 = new Box("box7", new Dimension3D(2, 5, 1));

            box1.connectChild(new Connector(box2, new Point3D(10, 12, 1)));


            box1.connectChild(new Connector(box3, new Point3D(-2, -15, 1)));
            box1.connectChild(new Connector(box4, new Point3D(7, -8, 1)));
            box2.connectChild(new Connector(box5, new Point3D(-2, -15, 1)));
            box5.connectChild(new Connector(box6, new Point3D(-2, -15, 1)));
            box5.connectChild(new Connector(box7, new Point3D(-2, -15, 1)));

            int boxCount = box1.getDescendantBoxCount();
            System.out.println(boxCount);

            Box cloneBox = box1.clone();
            int cloneCount = cloneBox.getDescendantBoxCount();
            System.out.println(cloneCount);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

        try
        {
            System.out.println("Testing other clonage");
            Box box1 = new Box("box1", new Dimension3D(3, 5, 1), true);
            Box box2 = new Box("box2", new Dimension3D(4, 6, 1));
            Box box3 = new Box("box3", new Dimension3D(8, 7, 1));
            Box box4 = new Box("box4", new Dimension3D(2, 5, 1));


            box1.connectChild(new Connector(box2, new Point3D(10, 12, 1)));


            box2.connectChild(new Connector(box3, new Point3D(-2, -15, 1)));
            box1.connectChild(new Connector(box4, new Point3D(7, -8, 1)));


            //the following has to do with printing the tree will need to actually make this work, but these are the methods his printout is going to access
            List<Box> boxList;

            boxList = box1.getChildBoxes();
            printFuckingShit(boxList);

            boxList = box1.getDescendantBoxes();
            printFuckingShit(boxList);

            boxList = box2.getChildBoxes();
            printFuckingShit(boxList);

            boxList = box2.getDescendantBoxes();
            printFuckingShit(boxList);

            boxList = box3.getChildBoxes();
            printFuckingShit(boxList);

            boxList = box3.getDescendantBoxes();
            printFuckingShit(boxList);

            boxList = box4.getChildBoxes();
            printFuckingShit(boxList);

            boxList = box4.getDescendantBoxes();
            printFuckingShit(boxList);


            //CLONEINGTESTSPELLEDWRIJNG

            Box boxClone = box1.clone();
            System.out.println(boxClone);
            //box1.setId("StuFuck");
            //box2.setId("The");
            //box3.setId("Taphole!");
            System.out.println(box3);
            System.out.println(boxClone);
            System.out.println(box1);
            boxList = boxClone.getChildBoxes();
            printFuckingShit(boxList);

            boxList = boxClone.getDescendantBoxes();
            printFuckingShit(boxList);


            System.out.println(box1.getAbsoluteCenterPosition());
            System.out.println(box2.getAbsoluteCenterPosition());
            System.out.println(box3.getAbsoluteCenterPosition());
            System.out.println(box4.getAbsoluteCenterPosition());


            //should also test aditional box class functionality
            System.out.println(box1.getChildBoxCount());
            System.out.println(box1.getDescendantBoxCount());
            System.out.println(box1.getID()); //these are just some I thought of quickly!
            System.out.println(box2.getID());
            System.out.println(box3.getID());
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public static void printFuckingShit(List<Box> boxList)
    {
        for(Box box: boxList)
        {
            System.out.print(box.getID() + ",");
        }
        System.out.println();
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

            new Connector(new Box("box", new Dimension3D(1, 2, 3)), null);

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

            Connector connector = new Connector(new Box("box", new Dimension3D(1, 2, 3)), new Point3D(1,2,3));

            connector.getParentBox();

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

            Connector connector = new Connector(new Box("box", new Dimension3D(1, 2, 3)), new Point3D(1,2,3));

            connector.setParentBox(null);

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

            Connector connector = new Connector(new Box("box", new Dimension3D(1, 2, 3)), new Point3D(1,2,3));

            Box box1 = new Box("box1", new Dimension3D(1, 2, 3));
            Box box2 = new Box("box2", new Dimension3D(1, 2, 3));

            connector.setParentBox(box1);
            connector.setParentBox(box2);

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

            new Connector(new Box("box", new Dimension3D(1, 2, 3), true), new Point3D(1,2,3));

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

            new Box(null, new Dimension3D(1,2,3));

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

            new Box("", new Dimension3D(1,2,3));

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

            new Box("id", null);

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

            Box box = new Box("id", new Dimension3D(1,2,3));

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

            Box box = new Box("id", new Dimension3D(1,2,3));

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

            Box box = new Box("id", new Dimension3D(1,2,3));

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

            Box box = new Box("id", new Dimension3D(1,2,3));

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

            Box boxParent  = new Box("boxParent", new Dimension3D(1, 2, 3));
            Box boxChild1  = new Box("boxChild",  new Dimension3D(1, 2, 3));
            Box boxChild2  = new Box("boxChild",  new Dimension3D(1, 2, 3));

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

            Box box1A = new Box("boxA", new Dimension3D(1,1,1));
            Box box1B = new Box("boxB", new Dimension3D(1,1,1));
            Box box1C = new Box("boxC", new Dimension3D(1,1,1));
            Box box1D = new Box("boxD", new Dimension3D(1,1,1));
            Box box1E = new Box("boxa", new Dimension3D(1,1,1));

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

            Box box1A = new Box("boxA", new Dimension3D(1,1,1));
            Box box1B = new Box("boxB", new Dimension3D(1,1,1));
            Box box1C = new Box("boxC", new Dimension3D(1,1,1));
            Box box1D = new Box("boxD", new Dimension3D(1,1,1));
            Box box1E = new Box("boxE", new Dimension3D(1,1,1));

            box1A.connectChild(new Connector(box1B, new Point3D(0,0,0)));
            box1A.connectChild(new Connector(box1C, new Point3D(0,0,0)));
            box1C.connectChild(new Connector(box1D, new Point3D(0,0,0)));
            box1C.connectChild(new Connector(box1E, new Point3D(0,0,0)));

//               System.out.println("$ checkBoxIDs:5");

            //
            Box box2A = new Box("boxA", new Dimension3D(1,1,1));
            Box box2B = new Box("boxB", new Dimension3D(1,1,1));
            Box box2C = new Box("boxC", new Dimension3D(1,1,1));
            Box box2D = new Box("boxD", new Dimension3D(1,1,1));
            Box box2E = new Box("boxE", new Dimension3D(1,1,1));

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

            Box box1A = new Box("box1A", new Dimension3D(1,1,1));
            Box box1B = new Box("box1B", new Dimension3D(1,1,1));
            Box box1C = new Box("box1C", new Dimension3D(1,1,1));
            Box box1D = new Box("box1D", new Dimension3D(1,1,1));
            Box box1E = new Box("boxE", new Dimension3D(1,1,1));

            box1A.connectChild(new Connector(box1B, new Point3D(0,0,0)));
            box1A.connectChild(new Connector(box1C, new Point3D(0,0,0)));
            box1C.connectChild(new Connector(box1D, new Point3D(0,0,0)));
            box1C.connectChild(new Connector(box1E, new Point3D(0,0,0)));

//               System.out.println("$ checkBoxIDs:8");

            //
            Box box2A = new Box("box2A", new Dimension3D(1,1,1));
            Box box2B = new Box("box2B", new Dimension3D(1,1,1));
            Box box2C = new Box("box2C", new Dimension3D(1,1,1));
            Box box2D = new Box("box2D", new Dimension3D(1,1,1));
            Box box2E = new Box("boxE", new Dimension3D(1,1,1));

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

            Box box1A = new Box("boxA", new Dimension3D(1,1,1));
            Box box1B = new Box("boxB", new Dimension3D(1,1,1));
            Box box1C = new Box("boxC", new Dimension3D(1,1,1));
            Box box1D = new Box("boxD", new Dimension3D(1,1,1));
            Box box1E = new Box("boxE", new Dimension3D(1,1,1));

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

            Box box1A = new Box("boxA", new Dimension3D(1,1,1));
            Box box1B = new Box("boxB", new Dimension3D(1,1,1));

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

            Box box1A = new Box("boxA", new Dimension3D(1,1,1));

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

        Box box1A = new Box("boxA", new Dimension3D(1,1,1));
        Box box1B = new Box("boxB", new Dimension3D(1,1,1));
        Box box1C = new Box("boxC", new Dimension3D(1,1,1));
        Box box1D = new Box("boxD", new Dimension3D(1,1,1));
        Box box1E = new Box("boxE", new Dimension3D(1,1,1));

        box1A.connectChild(new Connector(box1B, new Point3D(0,0,0)));
        box1A.connectChild(new Connector(box1C, new Point3D(0,0,0)));
        box1A.connectChild(new Connector(box1D, new Point3D(0,0,0)));
        box1A.connectChild(new Connector(box1E, new Point3D(0,0,0)));

        List<Box> boxesChildPre = box1A.getChildBoxes();

        try
        {
            boxesChildPre.clear();

            List<Box> boxesChildPost = box1A.getChildBoxes();

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

        Box box1A = new Box("boxA", new Dimension3D(1,1,1));
        Box box1B = new Box("boxB", new Dimension3D(1,1,1));
        Box box1C = new Box("boxC", new Dimension3D(1,1,1));
        Box box1D = new Box("boxD", new Dimension3D(1,1,1));
        Box box1E = new Box("boxE", new Dimension3D(1,1,1));

        box1A.connectChild(new Connector(box1B, new Point3D(0,0,0)));
        box1A.connectChild(new Connector(box1C, new Point3D(0,0,0)));
        box1A.connectChild(new Connector(box1D, new Point3D(0,0,0)));
        box1A.connectChild(new Connector(box1E, new Point3D(0,0,0)));

        List<Box> boxesChildPre = box1A.getDescendantBoxes();

        try
        {
            boxesChildPre.clear();

            List<Box> boxesChildPost = box1A.getDescendantBoxes();

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

        Box box1A = new Box("boxA", new Dimension3D(1,1,1));
        Box box1B = new Box("boxB", new Dimension3D(1,1,1));
        Box box1C = new Box("boxC", new Dimension3D(1,1,1));
        Box box1D = new Box("boxD", new Dimension3D(1,1,1));
        Box box1E = new Box("boxE", new Dimension3D(1,1,1));

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
