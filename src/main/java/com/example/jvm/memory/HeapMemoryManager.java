package com.example.jvm.memory;

import java.util.HashMap;
import java.util.Map;

public class HeapMemoryManager {
          private static final int MAX_HEAP_SIZE = 1024 * 1024;
          private int usedMemory;
          private Map<String, byte[]> heap = new HashMap<>();

          public boolean allocate(String objectName, int size) {
                    if (usedMemory + size > MAX_HEAP_SIZE) {
                              System.out.println("Heap out of memory! Running GC...");
                              return false;
                    }
                    heap.put(objectName, new byte[size]);
                    usedMemory += size;
                    System.out.println(objectName + " allocated " + size + " bytes.");
                    return true;
          }

          public void deallocate(String objectName) {
                    if (heap.containsKey(objectName)) {
                              usedMemory -= heap.get(objectName).length;
                              heap.remove(objectName);
                              System.out.println(objectName + " is removed from heap.");
                    }
          }

          private int getUsedMemory() {
                    return usedMemory;
          }
}
