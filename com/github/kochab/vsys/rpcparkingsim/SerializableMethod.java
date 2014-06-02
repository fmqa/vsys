package com.github.kochab.vsys.rpcparkingsim;

import java.lang.reflect.Method;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.IOException;

/**
 * Serializable wrapper for methods.
 *
 * @author Matthias Siegmund
 * @author Fadi Moukayed
 * @author Eugen Kinder
 */

public final class SerializableMethod implements Serializable {
    private static final long serialVersionUID = 0L;
    
    /**
     * Wrap a method object in a serializable wrapper.
     *
     * @param method Method to be wrapped
     * @return A serializable method wrapper object
     */
    public static SerializableMethod serialize(Method method) {
        return new SerializableMethod(method);
    }
    
    /**
     * Wrap the method described by the given signature in a serializable wrapper.
     *
     * @param declaringClass The class the method is declared in
     * @param methodName The method name
     * @param parameterTypes The method's argument types
     * @return A serializable wrapper for the given method
     */
    public static SerializableMethod serialize(Class<?> declaringClass, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        return new SerializableMethod(declaringClass.getMethod(methodName, parameterTypes));
    }
    
    /**
     * Returns the method wrapped by this object.
     *
     * @return The method wrapped by this object.
     * @throws NoSuchMethodException If the method could not be resolved
     */
    public Method method() throws NoSuchMethodException {
        if (method == null) {
            method = declaringClass.getMethod(methodName, parameterTypes);
            declaringClass = null;
            methodName = null;
            parameterTypes = null;
        }
        return method;
    }
    
    private SerializableMethod(Method method) {
        this.method = method;
    }
    
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        declaringClass = (Class<?>)in.readObject();
        methodName = in.readUTF();
        parameterTypes = (Class<?>[])in.readObject();
    }
    
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(method.getDeclaringClass());
        out.writeUTF(method.getName());
        out.writeObject(method.getParameterTypes());
    }
    
    private Method method;
    private Class<?> declaringClass;
    private String methodName;
    private Class<?>[] parameterTypes;
}
