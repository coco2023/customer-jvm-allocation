# OUTPUT
```
customer-jvm-allocation> java -cp target/customer-jvm-allocation-1.0-SNAPSHOT.jar com.example.jvm.runtime.JVMRuntime
Method entered: main
obj1 allocated 524288 bytes.
Method exited: main
JVM execution completed.
```

# customer-jvm-allocation
---

### **1. Project Structure**
```
customer-jvm-allocation
│── src/main/java/com/example/jvm
│   ├── memory/
│   │   ├── HeapMemoryManager.java
│   │   ├── StackMemoryManager.java
│   │   ├── MethodArea.java
│   │   ├── DirectMemory.java
│   │   ├── SimpleJVM.java  <-- New (Eden, Survivor, Minor GC)
│   ├── gc/
│   │   ├── GarbageCollector.java
│   │   ├── YoungGenGC.java
│   │   ├── OldGenGC.java
│   ├── runtime/
│   │   ├── JVMRuntime.java
│   ├── App.java
│── pom.xml
```

---

### **2. Code Files**
I'll generate each Java file along with the `pom.xml` to ensure you have a complete working project.

#### **2.1 `HeapMemoryManager.java`**
Handles heap memory allocation and deallocation.
```java
package com.example.jvm.memory;

import java.util.HashMap;
import java.util.Map;

public class HeapMemoryManager {
    private static final int MAX_HEAP_SIZE = 1024 * 1024; // 1MB
    private int usedMemory = 0;
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

    public int getUsedMemory() {
        return usedMemory;
    }
}
```

---

#### **2.2 `StackMemoryManager.java`**
Handles stack memory management for method calls.
```java
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
```

---

#### **2.3 `MethodArea.java`**
Represents JVM Method Area, storing class metadata.
```java
package com.example.jvm.memory;

import java.util.HashMap;
import java.util.Map;

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
```

---

#### **2.4 `DirectMemory.java`**
Represents direct memory allocation outside JVM heap.
```java
package com.example.jvm.memory;

public class DirectMemory {
    private int allocatedMemory = 0;
    private static final int MAX_DIRECT_MEMORY = 512 * 1024; // 512 KB

    public boolean allocate(int size) {
        if (allocatedMemory + size > MAX_DIRECT_MEMORY) {
            System.out.println("Direct memory allocation failed!");
            return false;
        }
        allocatedMemory += size;
        System.out.println("Allocated " + size + " bytes in direct memory.");
        return true;
    }

    public void free(int size) {
        allocatedMemory -= size;
        System.out.println("Freed " + size + " bytes from direct memory.");
    }
}
```

---

#### **2.5 `GarbageCollector.java`**
Handles garbage collection for heap memory.
```java
package com.example.jvm.gc;

import com.example.jvm.memory.HeapMemoryManager;

public class GarbageCollector {
    private HeapMemoryManager heap;

    public GarbageCollector(HeapMemoryManager heap) {
        this.heap = heap;
    }

    public void runGC() {
        System.out.println("Running Garbage Collection...");
        heap.deallocate("tempObject");
    }
}
```

---

#### **2.6 `YoungGenGC.java`**
Simulates Young Generation Garbage Collection.
```java
package com.example.jvm.gc;

public class YoungGenGC extends GarbageCollector {
    public YoungGenGC(com.example.jvm.memory.HeapMemoryManager heap) {
        super(heap);
    }

    @Override
    public void runGC() {
        System.out.println("Young Generation Garbage Collection running...");
        super.runGC();
    }
}
```

---

#### **2.7 `OldGenGC.java`**
Simulates Old Generation Garbage Collection.
```java
package com.example.jvm.gc;

public class OldGenGC extends GarbageCollector {
    public OldGenGC(com.example.jvm.memory.HeapMemoryManager heap) {
        super(heap);
    }

    @Override
    public void runGC() {
        System.out.println("Old Generation Garbage Collection running...");
        super.runGC();
    }
}
```

---

#### **2.8 `JVMRuntime.java`**
Main entry point simulating JVM execution.
```java
package com.example.jvm.runtime;

import com.example.jvm.memory.HeapMemoryManager;
import com.example.jvm.memory.StackMemoryManager;
import com.example.jvm.gc.GarbageCollector;
import com.example.jvm.gc.YoungGenGC;

public class JVMRuntime {
    public static void main(String[] args) {
        HeapMemoryManager heapManager = new HeapMemoryManager();
        StackMemoryManager stackManager = new StackMemoryManager();
        GarbageCollector gc = new YoungGenGC(heapManager);

        stackManager.pushMethod("main");

        boolean success = heapManager.allocate("obj1", 512 * 1024);
        if (!success) {
            gc.runGC();
            heapManager.allocate("obj1", 512 * 1024);
        }

        stackManager.popMethod();
        System.out.println("JVM execution completed.");
    }
}
```

---

#### **2.9 `App.java`**
Main application entry point.
```java
package com.example.jvm;

public class App {
    public static void main(String[] args) {
        System.out.println("JVM Simulator Running...");
    }
}
```

---

### **3. `pom.xml` (Maven Dependencies)**
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>customer-jvm-allocation</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
    </properties>

    <dependencies>
        <!-- JUnit for testing -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>5.8.1</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
            </plugin>
        </plugins>
    </build>
</project>
```

---

### **4. Running the Project**
#### **Compile and Package**
```sh
mvn clean package
```

#### **Run JVM Simulation**
```sh
java -cp target/customer-jvm-allocation-1.0-SNAPSHOT.jar com.example.jvm.runtime.JVMRuntime
```

