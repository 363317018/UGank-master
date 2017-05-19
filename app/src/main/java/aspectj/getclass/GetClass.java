package aspectj.getclass;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

/**
 * Created by Sophia on 2017/5/15.
 */
public class GetClass {
    private TreeNode root;
    private String objName;

    public GetClass(String name) {
        root = null;
        objName = name;
    }

    public void run(Object object) {
        root = new TreeNode();

        String name = objName;
        String classType = object.toString().split("@")[0];
        String value = object.toString();
        root.setData(new Data(classType, name, value));

        if(object!=null)
        {
            Field[] fields = getFields(object);

            generateTree(object, fields, root);
        }

    }

    public TreeNode getTree() {
        return root;
    }

    public Field[] getFields(Object obj) {
        Field[] fields = null;
        try {
            String classType = obj.toString().split("@")[0];
            if(classType.equals("ACTIVITY")){
                return null;
            }
            if(Class.forName(classType)==null)
            {
                return null;
            }
            Class<?> cla = Class.forName(classType);
            if(cla==null)
            {
                return null;
            }
            fields = cla.getDeclaredFields();
            if(fields == null) {
                return null;
            }
            for (Field f : fields) {
                f.setAccessible(true);
            }
        } catch (Exception e) {
            return null;
        } finally {
            return fields;
        }
    }

    public Data generateData(Object object, Field field) {
        String name = field.toString().substring(field.toString().lastIndexOf(".") + 1);
        String type = field.toString().split(" ")[0];
        String value = "null";

        if(object == null) {
            return new Data(type, name, value);
        }

        try{
            value = field.get(object).toString();
        }catch(Exception e) {
            value = object.toString();
        }

        return new Data(type, name, value);
    }

    public void generateTree(Object object, Field[] fields, TreeNode parent) {
        Data temp;
        if(fields==null) {
            return;
        }
        try {
            for (Field f : fields) {
                if(f==null)
                {
                    continue;
                }
                Object o = f.get(object);

                temp = generateData(o,f);
                TreeNode current = new TreeNode();
                current.setData(temp);
                current.setParentNode(parent);
                parent.addChildNode(current);

                //空类
                if (o == null) {
                    continue;
                }

                //成员变量是基本类型
                if (f.getType().isPrimitive()) {
                    continue;
                }

                //成员变量是数组
                if (o.getClass().isArray()) {
                    for (int i = 0; i < Array.getLength(o); i++) {
                        String elementType = o.getClass().getComponentType().getSimpleName();
                        String elementName = temp.getName()+"["+i+"]";
                        String elementValue = "null";

                        TreeNode elementNode = new TreeNode();
                        elementNode.setData(new Data(elementType,elementName,elementValue));
                        elementNode.setParentNode(current);
                        current.addChildNode(elementNode);

                        Object element = Array.get(o, i);
                        if (element == null) {
                            continue;
                        }else{
                            generateTree(element, getFields(element), elementNode);
                        }
                    }
                    continue;
                }

                //成员变量是自定义类
                Class<?> c = Class.forName(f.getType().getName());
                Field[] cDeclaredFields = c.getDeclaredFields();
                for (Field df : cDeclaredFields) {
                    df.setAccessible(true);
                }

                generateTree(o, cDeclaredFields, current);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
