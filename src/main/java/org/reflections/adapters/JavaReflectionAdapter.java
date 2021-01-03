package org.reflections.adapters;

import org.reflections.util.Utils;
import org.reflections.vfs.Vfs;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.reflections.ReflectionUtils.forName;
import static org.reflections.util.Utils.join;

/** */
public class JavaReflectionAdapter implements MetadataAdapter<Class, Field, Member> {

    public List<Field> getFields(Class cls) {
        return Arrays.asList(cls.getDeclaredFields());
    }

    public List<Member> getMethods(Class cls) {
        List<Member> methods = new ArrayList<>();
        methods.addAll(Arrays.asList(cls.getDeclaredMethods()));
        methods.addAll(Arrays.asList(cls.getDeclaredConstructors()));
        return methods;
    }

    public String getMethodName(Member method) {
        return method instanceof Method ? method.getName() :
                method instanceof Constructor ? "<init>" : null;
    }

    public List<String> getParameterNames(final Member member) {
        Class<?>[] parameterTypes = member instanceof Method ? ((Method) member).getParameterTypes() :
                member instanceof Constructor ? ((Constructor) member).getParameterTypes() : null;

        return parameterTypes != null ? Arrays.stream(parameterTypes).map(JavaReflectionAdapter::getName).collect(Collectors.toList()) : Collections.emptyList();
    }

    public List<String> getClassAnnotationNames(Class aClass) {
        return getAnnotationNames(aClass.getDeclaredAnnotations());
    }

    public List<String> getFieldAnnotationNames(Field field) {
        return getAnnotationNames(field.getDeclaredAnnotations());
    }

    public List<String> getMethodAnnotationNames(Member method) {
        Annotation[] annotations =
                method instanceof Method ? ((Method) method).getDeclaredAnnotations() :
                method instanceof Constructor ? ((Constructor) method).getDeclaredAnnotations() : null;
        return getAnnotationNames(annotations);
    }

    public List<String> getParameterAnnotationNames(Member method, int parameterIndex) {
        Annotation[][] annotations =
                method instanceof Method ? ((Method) method).getParameterAnnotations() :
                method instanceof Constructor ? ((Constructor) method).getParameterAnnotations() : null;

        return getAnnotationNames(annotations != null ? annotations[parameterIndex] : null);
    }

    public String getReturnTypeName(Member method) {
        return ((Method) method).getReturnType().getName();
    }

    public String getFieldName(Field field) {
        return field.getName();
    }

    public Class getOrCreateClassObject(Vfs.File file) throws Exception {
        return getOrCreateClassObject(file, null);
    }

    public Class getOrCreateClassObject(Vfs.File file, ClassLoader... loaders) throws Exception {
        String name = file.getRelativePath().replace("/", ".").replace(".class", "");
        return forName(name, loaders);
    }

    public String getMethodModifier(Member method) {
        return Modifier.toString(method.getModifiers());
    }

    public String getMethodKey(Class cls, Member method) {
        return getMethodName(method) + "(" + join(getParameterNames(method), ", ") + ")";
    }

    public String getMethodFullKey(Class cls, Member method) {
        return getClassName(cls) + "." + getMethodKey(cls, method);
    }

    public boolean isPublic(Object o) {
        Integer mod =
                o instanceof Class ? ((Class) o).getModifiers() :
                o instanceof Member ? ((Member) o).getModifiers() : null;

        return mod != null && Modifier.isPublic(mod);
    }

    public String getClassName(Class cls) {
        return cls.getName();
    }

    public String getSuperclassName(Class cls) {
        Class superclass = cls.getSuperclass();
        return superclass != null ? superclass.getName() : "";
    }

    public List<String> getInterfacesNames(Class cls) {
        Class[] classes = cls.getInterfaces();
        return classes != null ? Arrays.stream(classes).map(Class::getName).collect(Collectors.toList()) : Collections.emptyList();
    }

    public boolean acceptsInput(String file) {
        return file.endsWith(".class");
    }
    
    //
    private List<String> getAnnotationNames(Annotation[] annotations) {
        return Arrays.stream(annotations).map(annotation -> annotation.annotationType().getName()).collect(Collectors.toList());
    }

    public static String getName(Class type) {
        if (type.isArray()) {
            try {
                Class cl = type;
                int dim = 0; while (cl.isArray()) { dim++; cl = cl.getComponentType(); }
                return cl.getName() + Utils.repeat("[]", dim);
            } catch (Throwable e) {
                //
            }
        }
        return type.getName();
    }
}
