package com.example.jvm.gc;

import com.example.jvm.memory.HeapMemoryManager;

public class GarbageCollector {
          private HeapMemoryManager heapMemoryManager;

          public GarbageCollector(HeapMemoryManager heapMemoryManager) {
                    this.heapMemoryManager = heapMemoryManager;
          }

          public void runGC() {
                    System.out.println("Running Garbage Collection...");
                    heapMemoryManager.deallocate("tempObject");
          }
}
