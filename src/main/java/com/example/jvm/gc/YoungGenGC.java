package com.example.jvm.gc;

import com.example.jvm.memory.HeapMemoryManager;

public class YoungGenGC extends GarbageCollector {
          public YoungGenGC(HeapMemoryManager heapMemoryManager) {
                    super(heapMemoryManager);
          }

          @Override
          public void runGC() {
                    System.out.println("Young Generation Garbage Collection running...");
                    super.runGC();
          }
}
