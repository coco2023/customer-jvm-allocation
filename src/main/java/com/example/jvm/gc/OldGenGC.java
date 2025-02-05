package com.example.jvm.gc;

import com.example.jvm.memory.HeapMemoryManager;

public class OldGenGC extends GarbageCollector {
          public OldGenGC(HeapMemoryManager heapMemoryManager) {
                    super(heapMemoryManager);
          }

          @Override
          public void runGC() {
                    System.out.println("Old Generation Garbage Collection running...");
                    super.runGC();
          }

}
