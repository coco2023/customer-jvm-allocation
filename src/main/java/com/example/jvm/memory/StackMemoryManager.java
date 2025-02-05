package com.example.jvm.memory;

import java.util.Stack;

public class StackMemoryManager {
          private Stack<String> callStack = new Stack<>();

          public void pushMethod(String methodName) {
                    callStack.push(methodName);
                    System.out.println("Method entered: " + methodName);
          }

          public void popMethod() {
                    if (!callStack.isEmpty()) {
                              System.out.println("Method exited: " + callStack.pop());
                    }
          }
}
