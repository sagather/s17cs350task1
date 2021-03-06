package s17cs350task1;

import s17cs350project.command.*;
import s17cs350project.command.CommandController;
import s17cs350project.component.thruster.A_Thruster;
import s17cs350project.datatype.*;
import s17cs350project.datatype.Dimension3D;
import s17cs350project.datatype.Point3D;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bcxtr on 6/9/2017.
 */
public class CommandParser {

    private ArrayList<String> command = new ArrayList<>();
    private ArrayList<A_Command> comObjects = new ArrayList<>();
    //private s17cs350project.command.CommandController comController = new s17cs350project.command.CommandController(null);
    private s17cs350task1.CommandController comController = new s17cs350task1.CommandController();

    public CommandParser(s17cs350task1.CommandController controller, String commands){

        this.comController = controller;

        for(String cmd : commands.split("\\n")){
            command.add(cmd);
        }

    }

    public A_Command parse(){

        for(String c : command){

            String lowerCase = c.toLowerCase();

            if(lowerCase.contains("create") || lowerCase.contains("create main") || lowerCase.contains("create vernier")){
                comObjects.add( creationalCommandHelper(c));
            }
            else if(lowerCase.matches("create static") || lowerCase.contains("create dynamic") || lowerCase.contains("build") || lowerCase.contains("add")){
                comObjects.add( structuralCommandHelper(c));
            }
            else if(lowerCase.contains("fire") || lowerCase.contains("extend") || lowerCase.contains("retract") || lowerCase.contains("disconnect") || lowerCase.contains("reconnect")
                    || lowerCase.contains("generate flight path")){
                comObjects.add( behavioralCommandHelper(c));
            }
            else if(lowerCase.contains("@")){
                comObjects.add( metaCommandHelper(c));
            }

        }

        for(A_Command aCommand : comObjects){
            comController.schedule(aCommand);
        }

        return null;
    }

    private A_Command creationalCommandHelper(String cCommand){

          boolean root = false;
          String id = "", sur = "", or = "";
          double w = 0,h = 0,d = 0;
          Thrust thrust = new Thrust(0), max = new Thrust(0);
          Percent percent = new Percent(0);

          A_Command c = null;
        String[] parts = cCommand.split(" ");

          if(cCommand.contains("[")){

              for(int i = 0; i < parts.length; i++){

                  String s = parts[i];

                  if(s.compareToIgnoreCase("true") == 0 || s.compareToIgnoreCase("false") == 0){
                      root = Boolean.parseBoolean(s);
                  }
                  else if(s.compareToIgnoreCase("component") == 0){
                      id = parts[i+1];
                  }

                  else if(s.compareToIgnoreCase("width") == 0){
                      w = Double.parseDouble(parts[i + 1]);
                  }

                  else if(s.compareToIgnoreCase("height") == 0){
                      h = Double.parseDouble(parts[i + 1]);
                  }

                  else if(s.compareToIgnoreCase("depth") == 0){
                      d = Double.parseDouble(parts[i + 1]);
                  }
              }
              c = new CommandCreateComponent(id, new Dimension3D(w, h, d), root);
              System.out.println(c.toString());
              return c;
          }

          else if(cCommand.toLowerCase().contains("main thruster") || cCommand.toLowerCase().contains("vernier")){

              for(int i = 0; i < parts.length; i++){

                  String compare = parts[i];
                  if(compare.compareToIgnoreCase("Thruster") == 0){
                      id = parts[i + 1];
                  }

                  else if(compare.compareToIgnoreCase("offset") == 0){
                      w = Double.parseDouble(parts[i + 2]);
                      h = Double.parseDouble(parts[i + 3]);
                      d = Double.parseDouble(parts[i + 4]);
                  }

                  else if(compare.compareToIgnoreCase("rate") == 0){
                      percent = new Percent(Double.parseDouble(parts[i + 1]));
                  }

                  else if(compare.compareToIgnoreCase("surface") == 0){
                      sur = parts[i+2];
                  }
                  else if(compare.compareToIgnoreCase("orientation") == 0){
                      or = parts[i + 2];
                  }

                  else if(compare.compareToIgnoreCase("thrust") == 0){
                      if(parts[i +1].compareToIgnoreCase("min") == 0) {
                          thrust = new Thrust(Double.parseDouble(parts[i + 2]));
                      }
                      else{
                          thrust = new Thrust(Double.parseDouble(parts[i+1]));
                      }

                  }

                  else if(compare.compareToIgnoreCase("max") == 0){
                      max = new Thrust(Double.parseDouble(parts [i + 1]));
                  }

              }

              if(cCommand.toLowerCase().contains("main")) {

                  c = new CommandCreateMainThruster(id, new Point3D(w, h, d), A_Thruster.E_Surface.valueOf(sur), A_Thruster.E_Orientation.valueOf(or), percent, thrust);
              }
              else if(cCommand.toLowerCase().contains("vernier")){
                  c = new CommandCreateVernierThruster(id, new Point3D(w, h, d), A_Thruster.E_Surface.valueOf(sur), A_Thruster.E_Orientation.valueOf(or), percent, thrust);
              }

              if(cCommand.toLowerCase().contains("variable")){

                  c = new CommandCreateMainThruster(id, new Point3D(w, h, d), A_Thruster.E_Surface.valueOf(sur), A_Thruster.E_Orientation.valueOf(or), percent, thrust, max);

              }


              System.out.println(c.toString());
          }


        return c;
    }

    private A_Command structuralCommandHelper(String cCommand){

        A_Command com = null;
        String[] parts = cCommand.split(" ");
        String compare = "", id = "", childid = "", parentid = "";
        Point3D offset = null, beta = null;
        boolean isDisconnectable = false, isReconnectable = false;
        Percent initial = new Percent(0), speed = new Percent(0);

        //takes care of the two create commands
        if(cCommand.toLowerCase().contains("create")){

            if(cCommand.toLowerCase().contains("static")) {

                for (int i = 0; i < parts.length; i++) {
                    compare = parts[i].toLowerCase();

                    if (compare.compareToIgnoreCase("connector") == 0) {
                        id = parts[i + 1];
                    } else if (compare.compareToIgnoreCase("from") == 0) {
                        childid = parts[i + 1];
                    } else if (compare.compareToIgnoreCase("to") == 0) {
                        parentid = parts[i + 1];
                    } else if (compare.compareToIgnoreCase("offset") == 0) {
                        offset = new Point3D(Double.parseDouble(parts[i + 2]), Double.parseDouble(parts[i + 3]), Double.parseDouble(parts[i + 4]));
                    } else if (compare.compareToIgnoreCase("allow") == 0) {

                        int j = i++;

                        if (parts[j].compareToIgnoreCase("disconnection") == 0) {
                            isDisconnectable = true;
                        } else if (parts[j].compareToIgnoreCase("reconnection") == 0) {
                            isReconnectable = true;
                        }

                        if (j+1 < parts.length) {
                            if (parts[j + 1].compareToIgnoreCase("disconnection") == 0) {
                                isDisconnectable = true;
                            } else if (parts[j + 1].compareToIgnoreCase("reconnection") == 0) {
                                isReconnectable = true;
                            }
                        }

                    }


                }

                com = new CommandCreateStaticConnector(id, childid, parentid, offset, isDisconnectable, isReconnectable);
                System.out.println(com.toString());
            }

            else if(cCommand.toLowerCase().contains("dynamic")){

                for (int i = 0; i < parts.length; i++) {
                    compare = parts[i].toLowerCase();

                    if (compare.compareToIgnoreCase("connector") == 0) {
                        id = parts[i + 1];
                    } else if (compare.compareToIgnoreCase("from") == 0) {
                        childid = parts[i + 1];
                    } else if (compare.compareToIgnoreCase("to") == 0) {
                        parentid = parts[i + 1];
                    } else if (compare.compareToIgnoreCase("alpha") == 0) {
                        offset = new Point3D(Double.parseDouble(parts[i + 2]), Double.parseDouble(parts[i + 3]), Double.parseDouble(parts[i + 4]));
                    }
                    else if(compare.compareToIgnoreCase("beta") == 0){
                        beta = new Point3D(Double.parseDouble(parts[i + 2]), Double.parseDouble(parts[i + 3]), Double.parseDouble(parts[i + 4]));
                    }
                    else if(compare.compareToIgnoreCase("initial") == 0){
                        initial = new Percent(Double.parseDouble(parts[i + 1]));
                    }
                    else if(compare.compareToIgnoreCase("speed") == 0){
                        speed = new Percent(Double.parseDouble(parts[i + 1]));
                    }
                    else if (compare.compareToIgnoreCase("allow") == 0) {

                        int j = i + 1;

                        if (parts[j].compareToIgnoreCase("disconnection") == 0) {
                            isDisconnectable = true;
                        } else if (parts[j].compareToIgnoreCase("reconnection") == 0) {
                            isReconnectable = true;
                        }

                        if (j + 1 < parts.length) {
                            if (parts[j + 1].compareToIgnoreCase("disconnection") == 0) {
                                isDisconnectable = true;
                            } else if (parts[j + 1].compareToIgnoreCase("reconnection") == 0) {
                                isReconnectable = true;
                            }
                        }

                    }

                }

                com = new CommandCreateDynamicConnector(id, childid, parentid, offset, beta, initial, speed, isDisconnectable, isReconnectable);
                System.out.println(com.toString());
            }

        }

        //takes care of the two build commands
        else if(cCommand.toLowerCase().contains("build")){

            List<String> groupID = new ArrayList<>();

                for(int i = 0; i < parts.length; i++){
                    compare = parts[i].toLowerCase();
                    if(compare.contains("group")){
                        i++;
                        id = parts[i];
                    }
                    else if(compare.contains("with")){

                        i++;
                        while(i < parts.length){
                            groupID.add(parts[i]);
                            i++;
                        }
                        break;
                    }

                }

                if(cCommand.toLowerCase().contains("main")) {
                    com = new CommandBuildMainThrusterGroup(id, groupID);
                }
                else if(cCommand.toLowerCase().contains("vernier")){
                    com = new CommandBuildVernierThrusterGroup(id, groupID);
                }
                System.out.println(com.toString());

        }

        //takes care of the add command
        else if(cCommand.toLowerCase().contains("add")){

            List<String> groupIDs = new ArrayList<>();
            String componentID = "";

            for(int i = 0; i < parts.length; i++){

                compare = parts[i].toLowerCase();

                if(compare.equalsIgnoreCase("to")){
                    componentID = parts[++i];
                    break;
                }

                else if(compare.equalsIgnoreCase("thruster")){

                    i++;
                    groupIDs.add(parts[i]);

                }

            }

            com = new CommandAddThrusterGroups(groupIDs, componentID);
            System.out.println(com.toString());

        }

        return com;
    }

    private A_Command behavioralCommandHelper(String cCommand){

        A_Command com = null;
        String[] parts = cCommand.split(" ");
        String id = "", lowerCase = cCommand.toLowerCase(), reconnectId = "", compare = "";
        Time time = new Time(0);
        Thrust thrust = null;

        if(lowerCase.contains("fire")){

            for (int i = 0; i < parts.length; i++) {

                compare = parts[i].toLowerCase();
                if(compare.equalsIgnoreCase("group")){
                    id = parts[++i];
                }
                else if(compare.equalsIgnoreCase("for")){
                    time = new Time(Double.parseDouble(parts[++i]));
                }
                else if(compare.equalsIgnoreCase("thrust")){

                    thrust = new Thrust(Double.parseDouble(parts[++i]));

                }

            }
            if(thrust != null){
                com = new CommandFireThruster(id, time, thrust);
            }
            else {
                com = new CommandFireThruster(id, time);
            }
            //System.out.println(com.toString());
        }
        else if(lowerCase.contains("extend")){

            for (int i = 0; i < parts.length; i++) {
                compare = parts[i].toLowerCase();
                if(compare.equalsIgnoreCase("strut"))
                    id = parts[++i];
            }

            com = new CommandExtendStrut(id);
        }
        else if(lowerCase.contains("retract")){

            for (int i = 0; i < parts.length; i++) {
                compare = parts[i].toLowerCase();
                if(compare.equalsIgnoreCase("strut"))
                    id = parts[++i];
            }

            com = new CommandRetractStrut(id);
        }
        else if(lowerCase.contains("disconnect")){

            for (int i = 0; i < parts.length; i++) {
                compare = parts[i].toLowerCase();
                if(compare.equalsIgnoreCase("strut"))
                    id = parts[++i];
            }

            com = new CommandDisconnectStrut(id);

        }
        else if(lowerCase.contains("reconnect")){

            for (int i = 0; i < parts.length; i++) {
                compare = parts[i].toLowerCase();
                if(compare.equalsIgnoreCase("strut"))
                    id = parts[++i];

                else if(compare.equalsIgnoreCase("to"))
                    reconnectId = parts[++i];
            }

            com = new CommandReconnectStrut(id, reconnectId);

        }

        //TODO
        else if(lowerCase.contains("generate")){

            com = generatePathHelper();
        }

        System.out.println(com.toString());

        return com;
    }

    private A_Command metaCommandHelper(String cCommand){

        A_Command command = null;

        String[] parts = cCommand.split(" ");
        String compare = "", id = "", lowerCase = cCommand.toLowerCase();
        double yaw=0, pitch=0, roll=0;
        String x = "", y = "", z = "";
        double dx=0, dy=0, dz = 0;

        for (int i = 0; i < parts.length; i++) {
            compare = parts[i].toLowerCase();

            if(compare.contains("on") || compare.contains("component")){

                id = parts[++i];

            }

        }
        if(lowerCase.contains("attitude")){

            for (int i = 0; i < parts.length; i++) {

                compare = parts[i].toLowerCase();

                if(compare.contains("to")){

                    i++;

                    if(parts[i].equalsIgnoreCase("yaw")){
                        yaw = Double.parseDouble(parts[i+1]);

                        for (int j = i; j < parts.length; j++) {
                            if(parts[j].equalsIgnoreCase("pitch")) {
                                pitch = Double.parseDouble(parts[j + 1]);
                            }
                            else if(parts[j].equalsIgnoreCase("roll")){
                                roll = Double.parseDouble(parts[j+1]);
                            }
                        }
                    }
                    else if(parts[i].equalsIgnoreCase("pitch")){
                        pitch = Double.parseDouble(parts[i+1]);
                        for(int j = i; j < parts.length; j++){
                            if(parts[j].equalsIgnoreCase("roll")){
                                roll = Double.parseDouble(parts[j+1]);
                            }
                        }
                    }
                    else if(parts[i].equalsIgnoreCase("roll")){
                        roll = Double.parseDouble(parts[i+1]);
                    }

                }

            }
            if(lowerCase.contains("rate")){
                command = new CommandMetaForceAttitudeRate(id, new Attitude(yaw, pitch, roll));
            }
            else{
                command = new CommandMetaForceAttitude(id, new Attitude(yaw, pitch, roll));
            }

        }

        else if(lowerCase.contains("position")){

            for (int i = 0; i < parts.length; i++) {

                compare = parts[i];

                if(compare.equalsIgnoreCase("(")){

                    x = parts[++i];
                    y = parts[++i];
                    z = parts[++i];

                }

            }

            if(!x.contains("_") && x.matches("[0-9]+")){
                dx = Double.parseDouble(x);
            }
            if(!y.contains("_") && y.matches("[0-9]+")){
                dy = Double.parseDouble(y);
            }
            if(!z.contains("_") && z.matches("[0-9]+")){
                dz = Double.parseDouble(z);
            }

            command = new CommandMetaForcePosition(id, new Point3D(dx, dy, dz));

        }

        else if(lowerCase.contains("clock")){

            Time rate = new Time(Double.parseDouble(parts[2])), step = new Time(Double.parseDouble(parts[3]));

            command = new CommandMetaConfigClock(rate, step);
        }

        else if(lowerCase.contains("wait")){
            Time delta = new Time(Double.parseDouble(parts[1]));
            command = new CommandMetaWait(delta);
        }

        else if(lowerCase.contains("commit")){
            command = new CommandMetaCommit();
        }

        else if(lowerCase.contains("exit")){
            command = new CommandMetaExit();
        }

        else if(lowerCase.contains("pause")){
            command = new CommandMetaPause();
        }

        else if(lowerCase.contains("resume")){
            command = new CommandMetaResume();
        }

        else if(lowerCase.contains("schedule")){

            String filename = "";

            File file = new File(filename);

            //command = new CommandMetaLoad(comController, file);
        }

        System.out.println(command.toString());

        return command;
    }

    private A_Command generatePathHelper(){

        A_Command command = null;


        return command;
    }

}
