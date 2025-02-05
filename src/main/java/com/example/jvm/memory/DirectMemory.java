package com.example.jvm.memory;

import java.util.Map;

/**
 * Represents direct memory allocation outside JVM heap.
 */
public class DirectMemory {
          private int allocateMemory = 0;
          private static final int MAX_DIRECT_MEMORY = 512 * 1024;

          public boolean allocate(int size) {
                    if (allocateMemory + size > MAX_DIRECT_MEMORY) {
                              System.out.println("Direct memory allocation failed!");
                              return false;
                    }
                    allocateMemory += size;
                    System.out.println("Allocated " + size + " bytes in direct memory.");
                    return true;
          }

          public void free(int size) {
                    allocateMemory -= size;
                    System.out.println("Freed " + size + " bytes from direct memory.");
          }
}
