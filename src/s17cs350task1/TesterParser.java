package s17cs350task1;

/**
 * Created by bcxtr on 6/10/2017.
 */
public class TesterParser {

    private String report;
    private double score;

    public TesterParser(){

        this.report = "";
        this.score = 0;
    }

    public void testCreational(){

        CommandController cc = new CommandController();
        String command = "CREATE MAIN THRUSTER A AT OFFSET ( 2 2 2 ) ON SURFACE ( TOP ) WITH ORIENTATION ( UPWARD ) USING VARIABLE THRUST MIN 23 MAX 90 RATE 20";
        CommandParser parser;

        parser = new CommandParser(cc, "CREATE [ TRUE ] 123B SIZE WIDTH 9 HEIGHT 3 DEPTH 83\nCREATE VERNIER THRUSTER Z AT OFFSET ( 9 9 9 ) ON SURFACE ( LEFT ) WITH ORIENTATION ( RIGHTWARD ) USING THRUST 7 RATE 23\n" + command + "\n CREATE [ FALSE] COMPONENT B SIZE WIDTH 2 HEIGHT 1 DEPTH 3");
        parser.parse();

        score += 1;
        report += "Test of multiple create component done";

        System.out.println(score);
        System.out.println(report);



    }

    public void testStructural(){

        String command1 = "CREATE STATIC CONNECTOR 3 FROM B TO J WITH OFFSET ( 1 1 1 ) [ ALLOW ( DISCONNECTION ) ]\n";
        String command2 = "CREATE DYNAMIC CONNECTOR J FROM 3 TO A WITH OFFSET ALPHA ( 3 3 2 ) BETA ( 2 4 1 ) EXTENT INITIAL 3 SPEED 9 [ ALLOW ( RECONNECTION ) ]\n";
        String command3 = "BUILD MAIN THRUSTER GROUP F4 WITH 8\n";
        String command4 = "BUILD VERNIER THRUSTER GROUP G6 WITH 9 G7\n";
        String command5 = "ADD THRUSTER F4 TO J\n";

        CommandController cc = new CommandController();
        CommandParser parser = new CommandParser(cc, command1 + command2 + command3 + command4 + command5);

        parser.parse();

        System.out.println("Beta test of behavioral commands done");
    }

    public void testBehavioral(){

        String command1 = "FIRE THRUSTER GROUP G7 FOR 90\n";
        String command2 = "FIRE THRUSTER GROUP G7 FOR 90 AT THRUST 78\n";
        String command3 = "EXTEND STRUT 8\n";
        String command4 = "RETRACT STRUT 8\n";
        String command5 = "DISCONNECT STRUT 8\n";
        String command6 = "RECONNECT STRUT 8 TO 3\n";
        //String command7 = "GENERATE FLIGHT PATH FROM "

        CommandController cc = new CommandController();
        CommandParser parser = new CommandParser(cc, command1 + command2 + command3 + command4 + command5 + command6);
        parser.parse();

    }

}
