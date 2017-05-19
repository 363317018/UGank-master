package aspectj.getclass;

/**
 * Created by Sophia on 2017/5/15.
 */
public class Data {
    private String type;  //变量类型
    private String name;  //变量名
    private String value; //变量值

    public Data()
    {

    }

    public Data(String type,String name,String value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String print()
    {
        return (type + " " + name + " : " + value);
    }
}
