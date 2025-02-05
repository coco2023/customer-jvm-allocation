package com.example.jvm.memory;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents JVM Method Area, storing class metadata
 */
public class MethodArea {
          private Map<String, Class<?>> loadedClasses = new HashMap<>();

          public void loadClass(String className, Class<?> clazz) {
                    loadedClasses.put(className, clazz);
                    System.out.println("Loaded class: " + className);
          }

          public boolean isClassLoaded(String className) {
                    return loadedClasses.containsKey(className);
          }
}
