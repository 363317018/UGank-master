package aspectj;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

/**
 * Created by Sophia on 2017/5/6.
 */
public class PrintValue {
    Object object;
    public PrintValue(Object object) {
        this.object = object;
    }

    public void run() {
        getValue(object, null,0);
    }

    private void printTab(int depth){
        for(int i=0;i<depth;i++)
        {
            System.out.print("\t");
        }
    }

    public String getValue(Object obj,Field field,int depth) {
        try{
            if(field == null)
            {
                String className = obj.toString().split("@")[0];
                //System.out.println(a);
                //printTab(depth);
                //System.out.println("{");
                Class<?> cla = Class.forName(className);
                System.out.println(cla.getSimpleName());

                Field[] fields = cla.getDeclaredFields();
                for(Field f:fields){
                    f.setAccessible(true);
                    getValue(obj,f,depth+1);
                }
                //printTab(depth);
                //System.out.println("}");
            }

            else {
                String name = field.toString().substring(field.toString().lastIndexOf(".") + 1);

                Object o = field.get(obj);
                if(o.getClass().isArray())
                {
                    Class elementType = o.getClass().getComponentType();
                    for(int i=0;i<Array.getLength(o);i++)
                    {
                        Object element = Array.get(o,i);
                        getValue(element,null,depth);
                    }
                    //return "";
                }

                if(field.getType().isPrimitive())
                {
                    printTab(depth);
                    System.out.println(name + " : " + field.get(obj));
                    return "";
                }
                String type = field.toString().split(" ")[0];
                String value = field.get(obj).toString();

                boolean flag = false;

                if(value.indexOf('@') >= 0)
                {
                    String subValue = value.split("@")[0];

                    if(type.equals(subValue))
                    {
                        flag = true;
                    }
                }

                if(flag) {
                    Class<?> c = Class.forName(value.split("@")[0]);
                    //Object o = field.get(obj);

                    Field[] fields = c.getDeclaredFields();
                    printTab(depth);
                    System.out.println(name);
                    printTab(depth);System.out.println("{");
                    for (Field f: fields) {

                        getValue(o,f,depth+1);
                    }
                    printTab(depth);System.out.println("}");
                }
                if(!flag) {
                    printTab(depth);
                    System.out.println(name + " : " + value);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        return "Test";
    }
}
