package com.powernode.bank.utils;

import org.apache.ibatis.javassist.CannotCompileException;
import org.apache.ibatis.javassist.ClassPool;
import org.apache.ibatis.javassist.CtClass;
import org.apache.ibatis.javassist.CtMethod;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 工具类：可以动态的生成dao的实现类
 */
public class GenerateDaoProxy {
    public static Object generate(SqlSession sqlSession, Class daoInterface){
        //类池
        ClassPool pool = ClassPool.getDefault();
        //制造类
        CtClass ctClass = pool.makeClass(daoInterface.getName() + "Proxy");
        //制造接口
        CtClass ctInterface = pool.makeInterface(daoInterface.getName());
        //实现接口
        ctClass.addInterface(ctInterface);
        //实现接口中所有的方法、
        Method[] methods = daoInterface.getDeclaredMethods();
        Arrays.stream(methods).forEach(method -> {
        try {
            StringBuilder methodCode = new StringBuilder();
            methodCode.append("public ");
            methodCode.append(method.getReturnType().getName());
            methodCode.append(" ");
            methodCode.append(method.getName());
            methodCode.append("(");
            //需要方法的形参
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (int i=0; i < parameterTypes.length; i++){
                Class<?> parameterType = parameterTypes[i];
                methodCode.append(parameterType.getName());
                methodCode.append(" ");
                methodCode.append("arg" + i);
                if (i != parameterTypes.length - 1){
                    methodCode.append(",");
                }
            }
            methodCode.append(")");
            methodCode.append("{");
            //方法体里面的代码
            methodCode.append("org.apache.ibatis.session.SqlSession sqlSession = com.powernode.bank.utils.SqlSessionUtil.openSession();");
            String sqlId = daoInterface.getName()+ "." + method.getName();
            SqlCommandType sqlCommandType = sqlSession.getConfiguration().getMappedStatement(sqlId).getSqlCommandType();
            if (sqlCommandType == sqlCommandType.INSERT){

            }
            if (sqlCommandType == sqlCommandType.DELETE){

            }
            if (sqlCommandType == sqlCommandType.UPDATE){
                methodCode.append("return sqlSession.update(\""+sqlId+"\",arg0);");
            }
            if (sqlCommandType == sqlCommandType.SELECT){
                String returnType = method.getReturnType().getName();
                methodCode.append("return ("+returnType+")sqlSession.selectOne(\""+sqlId+"\", arg0);");
            }
            methodCode.append("}");
            CtMethod ctMethod = CtMethod.make(methodCode.toString(), ctClass);
            ctClass.addMethod(ctMethod);
            } catch (CannotCompileException e) {
                e.printStackTrace();
            }
        });
        //创建对象
        Object obj = null;
        try {
            Class<?> clazz = ctClass.toClass();
            obj = clazz.newInstance();
        }catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
