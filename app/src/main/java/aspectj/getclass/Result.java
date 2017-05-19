package aspectj.getclass;

/**
 * Created by jason on 2017/5/18.
 */
public class Result {
    private Result(){}
    private static Result result=new Result();
    public static Result getInstance(){
        return result;
    }
   public StringBuffer sbuf=new StringBuffer();

}
