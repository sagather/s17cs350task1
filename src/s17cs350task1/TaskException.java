package s17cs350task1;

/**
 * Created by bcxtr on 4/17/2017.
 */
public class TaskException extends RuntimeException {

    TaskException(){

        super("Aw, snap!  Something went wrong.");

    }

    TaskException(String iMessage){

        super(iMessage);

    }

}
