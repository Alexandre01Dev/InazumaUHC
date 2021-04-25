package be.alexandre01.inazuma.uhc.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class ObjectUtil {
    public static Object cloneObject(Object obj){
        try{
            Object clone = obj.getClass().newInstance();
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if(field.get(obj) == null || Modifier.isFinal(field.getModifiers())){
                    continue;
                }
                if(field.getType().isPrimitive() || field.getType().equals(String.class)
                        || field.getType().getSuperclass().equals(Number.class)
                        || field.getType().equals(Boolean.class)){
                    field.set(clone, field.get(obj));
                }else{
                    Object childObj = field.get(obj);
                    if(childObj == obj){
                        field.set(clone, clone);
                    }else{
                        field.set(clone, cloneObject(field.get(obj)));
                    }
                }
            }
            return clone;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static Object copyAndPaste(Object obj, Object clone,String... exclude){
        System.out.println("COPY >> "+clone + " TO >>" + clone);
        try{
            for (Field field : obj.getClass().getDeclaredFields()) {
                System.out.println(field);
                field.setAccessible(true);

                if(Arrays.asList(exclude).contains(field.getName()))
                    continue;

                if(field.get(obj) == null || Modifier.isFinal(field.getModifiers())){
                    continue;
                }
                if(field.getType().isPrimitive() || field.getType().equals(String.class)
                        || field.getType().getSuperclass().equals(Number.class)
                        || field.getType().equals(Boolean.class)){
                    field.set(clone, field.get(obj));
                    System.out.println("1NEW >> "+clone + " TO >>" + field.get(obj));
                }else{
                    Object childObj = field.get(obj);
                    if(childObj == obj){
                        field.set(clone, clone);
                        System.out.println("2NEW >> "+clone + " TO >>" + clone);
                    }else{
                        System.out.println("3NEW >> "+clone + " TO >>" + field.get(obj));
                        try{
                            field.set(clone, field.get(obj));
                        }catch(Exception ignored){

                        }


                    }
                }
            }
            for (Field field : obj.getClass().getFields()) {
                System.out.println(field);
                field.setAccessible(true);
                if(field.get(obj) == null || Modifier.isFinal(field.getModifiers())){
                    continue;
                }
                if(field.getType().isPrimitive() || field.getType().equals(String.class)
                        || field.getType().getSuperclass().equals(Number.class)
                        || field.getType().equals(Boolean.class)){
                    field.set(clone, field.get(obj));
                    System.out.println("1NEW >> "+clone + " TO >>" + field.get(obj));
                }else{
                    Object childObj = field.get(obj);
                    if(childObj == obj){
                        field.set(clone, clone);
                        System.out.println("2NEW >> "+clone + " TO >>" + clone);
                    }else{
                        field.set(clone, cloneObject(field.get(obj)));
                        System.out.println("3NEW >> "+clone + " TO >>" + cloneObject(field.get(obj)));
                    }
                }
            }
            return clone;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
