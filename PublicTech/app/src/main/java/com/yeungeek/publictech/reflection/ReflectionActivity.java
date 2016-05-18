package com.yeungeek.publictech.reflection;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.github.pedrovgs.lynx.LynxView;
import com.yeungeek.publictech.BaseActivity;
import com.yeungeek.publictech.R;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by yeungeek on 2016/5/13.
 */
public class ReflectionActivity extends BaseActivity {
    private final static String TAG = "test";
    private final String PERSON_CLASS_NAME = "com.yeungeek.publictech.reflection.Person";

    @BindView(R.id.id_console)
    LynxView lynxView;

    Object mObj;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "### onCreate");
        Toast toast = Toast.makeText(this, "Toast Reflection", Toast.LENGTH_LONG);
        try {
            Field field = toast.getClass().getDeclaredField("mTN");
            field.setAccessible(true);
            mObj = field.get(toast);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getResId() {
        return R.layout.activity_reflection;
    }

    @OnClick(R.id.id_toast_show)
    public void toastShow() {
        try {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                Field field = mObj.getClass().getDeclaredField("mNextView");
                field.setAccessible(true);
                LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = inflate.inflate(R.layout.layout_toast, null);

                field.set(mObj, v);
            }
            
            Method method = mObj.getClass().getDeclaredMethod("show");
            method.invoke(mObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.id_toast_hide)
    public void toastHide() {
        try {
            Method method = mObj.getClass().getDeclaredMethod("hide");
            method.invoke(mObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.id_benchmarks)
    public void benchmarks() {
        lynxView.clear();
        try {
            Log.e(TAG, "### benchmarks");
            long start = 0;
            long end = 0;
            start = System.currentTimeMillis();
            //1. getFields
            Class<?> clazz1 = android.app.Activity.class;
            for (int i = 0; i < 10000; i++) {
                clazz1.getFields();
            }
            end = System.currentTimeMillis();
            Log.d(TAG, "### benchmarks getFields: " + (end - start));

            //2. getDeclaredFields
            start = System.currentTimeMillis();
            Class<?> clazz2 = android.app.Activity.class;
            for (int i = 0; i < 10000; i++) {
                clazz2.getDeclaredFields();
            }
            end = System.currentTimeMillis();
            Log.d(TAG, "### benchmarks getDeclaredFields: " + (end - start));

            //3. getGenericInterfaces
            start = System.currentTimeMillis();
            Class<?> clazz3 = android.app.Activity.class;
            for (int i = 0; i < 10000; i++) {
                clazz3.getGenericInterfaces();
            }
            end = System.currentTimeMillis();
            Log.d(TAG, "### benchmarks getGenericInterfaces: " + (end - start));

            //4. setObject
            start = System.currentTimeMillis();
            Class<?> clazz4 = android.app.Activity.class;
            for (int i = 0; i < 10000; i++) {
                Field field = clazz4.getDeclaredField("mTitle");
                field.setAccessible(true);
                field.set(this, "Title");
            }
            end = System.currentTimeMillis();
            Log.d(TAG, "### benchmarks setObject: " + (end - start));

            //5. newInstance
            start = System.currentTimeMillis();
            for (int i = 0; i < 100000; i++) {
                new Person();
            }
            end = System.currentTimeMillis();
            Log.d(TAG, "### benchmarks newInstance: " + (end - start));

            //5. reflection newInstance
            start = System.currentTimeMillis();
            for (int i = 0; i < 100000; i++) {
                Person.class.newInstance();
            }
            end = System.currentTimeMillis();
            Log.d(TAG, "### benchmarks reflection newInstance: " + (end - start));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.id_basic)
    public void basic() {
        lynxView.clear();
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

            inspectGeneric();
            inspectArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        toastHide();
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

        Constructor<?> constructor = clazz.getConstructor(String.class, int.class);
        person = (Person) constructor.newInstance("test", 102);
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
        nameMethod.setAccessible(true);
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

    private void inspectGeneric() throws Exception {
        Log.e(TAG, "### inspectGeneric");
        Method method = Father.class.getMethod("getStringList");
        Type type = method.getGenericReturnType();

        if (type instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) type;
            Type[] types = paramType.getActualTypeArguments();
            for (Type typeArg : types) {
                Class typeArgClass = (Class) typeArg;
                Log.d(TAG, "### typeArgClass: " + typeArgClass);
            }
        }

        Log.e(TAG, "### method parameter");
        method = Father.class.getMethod("setStringList", List.class);

        Type[] types = method.getGenericParameterTypes();
        for (Type genericType : types) {
            if (genericType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericType;
                Type[] args = parameterizedType.getActualTypeArguments();
                for (Type arg : args) {
                    Class argClass = (Class) arg;
                    Log.d(TAG, "### typeArgClass: " + argClass);
                }
            }
        }

        Log.e(TAG, "### inspect field parameter");

        Field field = Father.class.getDeclaredField("stringList");
        type = field.getGenericType();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] args = parameterizedType.getActualTypeArguments();
            for (Type arg : args) {
                Class argClass = (Class) arg;
                Log.d(TAG, "### typeArgClass: " + argClass);
            }
        }
    }

    private void inspectArray() throws Exception {
        Log.e(TAG, "### inspectArray");
        int[] intArray = (int[]) Array.newInstance(int.class, 3);

        Array.setInt(intArray, 0, 10);
        Array.setInt(intArray, 1, 20);
        Array.setInt(intArray, 2, 30);

        Log.d(TAG, "### get Array: " + Array.get(intArray, 0));

        Class stringClass = String[].class;
        Class intArray1 = Class.forName("[I");
        Class stringArray = Class.forName("[Ljava.lang.String;");

        Class stringArray1 = Array.newInstance(String[].class, 3).getClass();
        Log.d(TAG, "### stringarray?: " + stringArray.isArray());
        Class componentType = stringArray1.getComponentType();
        Log.d(TAG, "### componentType: " + componentType);

    }
}