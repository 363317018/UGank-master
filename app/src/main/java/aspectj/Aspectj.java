package aspectj;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.ArrayList;

import aspectj.getclass.GetClass;
import aspectj.getclass.TreeNode;

/**
 * Created by jason on 2017/5/6.
 */
@Aspect
public class Aspectj {
   static ArrayList<String> strlist=new ArrayList<>();

    private static final String TAG = "aop";

    //com.example.jason.myapplication包下所有类中的所有方法都会被拦截
    @Around("execution(* me.bakumon.ugank.module.*.*.*(..))")

    public Object onActivityMethodBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName=joinPoint.getSignature().getName();

        Object []obj=joinPoint.getArgs();
        ArrayList<ArrayList<String>> strlist=new ArrayList<>();

        for(int i=0;i<obj.length;i++)
        {
           strlist.add(CompareValue.getFields(obj[i]));
        }

        Object result=joinPoint.proceed();
        Object []obja=joinPoint.getArgs();
        ArrayList<ArrayList<String>> strlist2=new ArrayList<>();

        for(int i=0;i<obja.length;i++)
        {
            strlist2.add(CompareValue.getFields(obja[i]));
        }
        boolean isChanged=false;
        for(int i=0;i<strlist.size()&&i<strlist2.size();i++)
        {
            if(strlist.get(i)==null&&strlist.get(i)==null)
            {
                continue;
            }
            if(strlist.get(i)==null||strlist.get(i)==null)
            {

                isChanged=true;
                break;
            }
            if(!CompareValue.compare(strlist.get(i),strlist2.get(i)))
            {

                isChanged=true;
                break;
            }
        }
        if(isChanged)
        {
            Log.w("outprint",  methodName+"\n"+strlist+" \n*** \n"+ strlist2+methodName+"\n");
        }
        //printTree(obj);
        return result;
    }
    private void printTree(Object[] objs){
     for(Object obj:objs){
         GetClass getClass = new GetClass(obj.toString());
         if(obj==null){
             continue;
         }
         getClass.run(obj);
         TreeNode tree = getClass.getTree();

         tree.traverse(0);

         Log.w("tree",tree.getSbufAndClear());
     }
    }

    //execution(* com.longzhun.UserManager+.*(..)) com.longzhun包下的UserManager类或接口中的所有方法以及子类，实现类的所有方法都会被拦截
}
