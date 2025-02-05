package com.example.jvm.memory;

public class SimpleJVM {
          private static final int EDEN_SIZE = 64 * 1024; // 64KB
          private static final int SURVIVOR_SIZE = 32 * 1024; // 32KB
          private static final int OLD_GEN_SIZE = 128 * 1024; // 128KB

          private byte[] eden = new byte[EDEN_SIZE];
          private int edenPointer = 0;

          private byte[] survivor = new byte[SURVIVOR_SIZE];
          private int survivorPointer = 0;

          private byte[] oldGen = new byte[OLD_GEN_SIZE];
          private int oldGenPointer = 0;

          // Allocate memory in Eden space
          public int allocate(int size) {
                    if (edenPointer + size > EDEN_SIZE) {
                              minorGC(); // Trigger Minor GC
                              if (edenPointer + size > EDEN_SIZE) {
                                        System.out.println("Allocation failed: Not enough space after GC!");
                                        return -1;
                              }
                    }
                    int address = edenPointer;
                    edenPointer += size;
                    System.out.println("Allocated " + size + " bytes at Eden[" + address + "]");
                    return address;
          }

          // Simulate Minor GC: Move objects to Survivor
          private void minorGC() {
                    System.out.println("=== Minor GC Triggered ===");
                    survivorPointer = 0;

                    // Copy all objects to Survivor
                    if (edenPointer <= SURVIVOR_SIZE) {
                              System.arraycopy(eden, 0, survivor, 0, edenPointer);
                              survivorPointer = edenPointer;
                              System.out.println("Survivor now holds " + survivorPointer + " bytes.");
                    } else {
                              promoteToOldGen();
                    }

                    edenPointer = 0;
          }

          // Promote objects to Old Gen when Survivor is full
          private void promoteToOldGen() {
                    if (survivorPointer + edenPointer > SURVIVOR_SIZE) {
                              System.out.println("Survivor full! Promoting to Old Gen...");
                              if (oldGenPointer + edenPointer <= OLD_GEN_SIZE) {
                                        System.arraycopy(eden, 0, oldGen, oldGenPointer, edenPointer);
                                        oldGenPointer += edenPointer;
                                        System.out.println("Old Gen now holds " + oldGenPointer + " bytes.");
                              } else {
                                        System.out.println("Out of Memory! Old Gen full!");
                              }
                    }
          }

          public int getEdenUsage() {
                    return edenPointer;
          }

          public int getSurvivorUsage() {
                    return survivorPointer;
          }

          public int getOldGenUsage() {
                    return oldGenPointer;
          }
}
