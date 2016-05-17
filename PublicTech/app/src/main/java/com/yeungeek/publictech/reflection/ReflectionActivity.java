package com.yeungeek.publictech.reflection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.yeungeek.publictech.BaseActivity;
import com.yeungeek.publictech.R;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import butterknife.OnClick;

/**
 * Created by yeungeek on 2016/5/13.
 */
public class ReflectionActivity extends BaseActivity {
    private final static String TAG = "test";
    private final String PERSON_CLASS_NAME = "com.yeungeek.publictech.reflection.Person";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "### onCreate");
    }

    @Override
    protected int getResId() {
        return R.layout.activity_reflection;
    }

    @OnClick(R.id.id_basic)
    public void basic() {
        //1. getConstructor
        try {
            inspectClasses();
            inspectClassesFromConstructor();

            inspectGetDeclaredMethods();
            inspectGetMethods();
            //filed
            inspectGetFiled();
            inspectSuperClass();
            //
            inspectAnnotations();
            inspectClassLoader();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void inspectClasses() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        Class<?> clazz = Class.forName(PERSON_CLASS_NAME);
        Person person = (Person) clazz.newInstance();
        person.setName("hello");
        person.setAge(18);

        Log.d(TAG, "### 1.inspectClasses: " + person.toString());
    }

    private void inspectClassesFromConstructor() throws Exception {
        Class<?> clazz = Class.forName(PERSON_CLASS_NAME);
        Constructor<?>[] constructors = clazz.getConstructors();

        Person person = (Person) constructors[0].newInstance();
        Log.d(TAG, "### 2.constructor[0]: " + person.toString());

        person = (Person) constructors[1].newInstance("test", 102);
        Log.d(TAG, "### 3.constructor[1]: " + person.toString());
    }

    private void inspectGetDeclaredMethods() throws Exception {
        Father father = new Father();
        Method[] methods = father.getClass().getDeclaredMethods();//get public default protected private method
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            Log.d(TAG, "### method[" + i + "]: " + method.getName());
        }

        Method nameMethod = father.getClass().getDeclaredMethod("setMan", boolean.class);
        //get parameter
        Class<?>[] paramClasses = nameMethod.getParameterTypes();
        for (Class<?> paramClass : paramClasses) {
            Log.d(TAG, "### paramClass: " + paramClass.getName());
        }

        Log.d(TAG, "### " + nameMethod.getName() + " is private: " +
                Modifier.isPrivate(nameMethod.getModifiers()));

        nameMethod.invoke(father, true);
        Log.d(TAG, "### 4. invoke: " + father.toString());
    }

    private void inspectGetMethods() throws Exception {
        Log.e(TAG, "### inspectGetMethods");
        Father father = new Father();
        Method[] methods = father.getClass().getMethods();

        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            Log.d(TAG, "### method[" + i + "]: " + method.getName());
        }

        Method nameMethod = father.getClass().getMethod("setName", String.class);

        nameMethod.setAccessible(true);
        nameMethod.invoke(father, "InvokeGetMethods");
        Log.d(TAG, "### 5. invoke: " + father.toString());
    }

    private void inspectGetFiled() throws Exception {
        Father father = new Father("Father", 28);
        Field[] fields = father.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            Log.d(TAG, "### field[" + i + "]: " + field.getName());
        }

        Field manField = father.getClass().getDeclaredField("isMan");
        manField.setAccessible(true);
        manField.setBoolean(father, true);
        Log.d(TAG, "### 6. getDeclaredFields: " + father.toString());

        Log.e(TAG, "### inspectGetFiled");
        fields = father.getClass().getFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            Log.d(TAG, "### field[" + i + "]: " + field.getName());
        }

        Field ageField = father.getClass().getField("age");
        ageField.setAccessible(true);
        ageField.set(father, 35);
        Log.d(TAG, "### 7. getFiled: " + father.toString());
    }

    private void inspectSuperClass() {
        Log.e(TAG, "### inspectSuperClass");
        Father father = new Father();
        Class<?> fs = father.getClass().getSuperclass();
        Log.d(TAG, "### superClass: " + fs.getName());

        //super or not
        Class<?>[] interfaces = father.getClass().getInterfaces();
        for (Class<?> clazz : interfaces) {
            Log.d(TAG, "### interfaces: " + clazz);
        }
    }

    private void inspectAnnotations() throws Exception {
        Log.e(TAG, "### inspectAnnotations");
        Father father = new Father();
        Annotation[] annotations = father.getClass().getAnnotations();
        for (Annotation annotation : annotations) {
            Log.d(TAG, "### annotation: " + annotation);
        }

        //special
        Brand brand = father.getClass().getAnnotation(Brand.class);
        Log.d(TAG, "### annotation brand: " + brand.name());

        Field manField = father.getClass().getDeclaredField("isMan");
        brand = manField.getAnnotation(Brand.class);
        Log.d(TAG, "### annotation field: " + brand.name());
    }

    private void inspectClassLoader() throws Exception {
        Class<?> clazz = Class.forName(PERSON_CLASS_NAME);
        String name = clazz.getClassLoader().getClass().getSimpleName();
        Log.e(TAG, "### inspectClassLoader: " + name);
    }
}