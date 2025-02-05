package com.example.jvm.runtime;

import com.example.jvm.gc.GarbageCollector;
import com.example.jvm.gc.YoungGenGC;
import com.example.jvm.memory.HeapMemoryManager;
import com.example.jvm.memory.SimpleJVM;
import com.example.jvm.memory.StackMemoryManager;

/**
 * Main entry point simulating JVM execution.
 */
public class JVMRuntime {
          public static void main(String[] args) {
                    HeapMemoryManager heapMemoryManager = new HeapMemoryManager();
                    StackMemoryManager stackMemoryManager = new StackMemoryManager();
                    GarbageCollector gc = new YoungGenGC(heapMemoryManager);

                    stackMemoryManager.pushMethod("main");

                    boolean success = heapMemoryManager.allocate("obj1", 512 * 1024);
                    if (!success) {
                              gc.runGC();
                              heapMemoryManager.allocate("obj1", 512 * 1024);
                    }

                    stackMemoryManager.popMethod();
                    System.out.println("JVM execution completed.");

                    System.out.println("=== ::Simple JVM Running Begin:: ===");
                    SimpleJVM jvm = new SimpleJVM();

                    // Allocate 3 objects (each 20KB)
                    jvm.allocate(20 * 1024); // Object 1
                    jvm.allocate(20 * 1024); // Object 2
                    jvm.allocate(20 * 1024); // Object 3 (triggers GC)

                    System.out.println("Eden Usage: " + jvm.getEdenUsage() + " bytes");
                    System.out.println("Survivor Usage: " + jvm.getSurvivorUsage() + " bytes");
                    System.out.println("Old Gen Usage: " + jvm.getOldGenUsage() + " bytes");

          }
}
