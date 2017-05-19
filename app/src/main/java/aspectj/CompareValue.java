package aspectj;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Sophia on 2017/5/6.
 */
public class CompareValue {

    public static  ArrayList<String> getFields(Object obj) throws IllegalAccessException {
        if(obj==null)
        {
            return null;
        }
        String className[] = obj.toString().split("@");

        Class<?> cla = null;
        try {
            cla = Class.forName(className[0]);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(cla==null)
        {
            return null;
        }
        Field[] fields = cla.getDeclaredFields();
        ArrayList<String> strlist=new ArrayList<>();
        for(int i=0;i<fields.length;i++){
            fields[i].setAccessible(true);
            if(fields[i].get(obj)!=null&&!fields[i].get(obj).toString().contains("execution"))
            {
                strlist.add(fields[i].get(obj).toString());
            }
        }

//        return   str;
          return strlist;
    //    return null;
    }

    public static boolean compare(ArrayList<String> fields1,ArrayList<String> fields2) throws IllegalAccessException {

        for(int i=0;i<fields1.size()&&i<fields2.size();i++)
        {
            if(!fields1.get(i).equals(fields2.get(i)))
            {
                return false;
            }
        }


        return true;
    }
}
