## 数据结构



## 算法汇总

一些算法的Java 实现

### 排序算法

* [冒泡排序](../src/main/java/info/victorchu/algorithms/sort/bubblesort/BubbleSort.java)
* [插入排序](../src/main/java/info/victorchu/algorithms/sort/insertionsort/InsertionSort.java)
* [二分插入排序](../src/main/java/info/victorchu/algorithms/sort/insertionsort/BinaryInsertSort.java)
* [选择排序](../src/main/java/info/victorchu/algorithms/sort/selectionsort/SelectionSort.java)

### 搜索算法

* [字典树](../src/main/java/info/victorchu/algorithms/search/trietree/TrieTree.java)
    * [压缩字典树](../src/main/java/info/victorchu/algorithms/search/trietree/CompressedTrieTree.java)

### 緩存算法

* [LFU缓存](../src/main/java/info/victorchu/algorithms/cache/LFUCache.java)

### 数学

* [简单素数生成](../src/main/java/info/victorchu/algorithms/primes/PrimesGenerator.java)

### 图算法

* [拓扑排序](../src/main/java/info/victorchu/algorithms/graph/TopologicalSortingGraph.java)

### Hash
* [一致性hash-hash环](../src/main/java/info/victorchu/algorithms/hash/TreeMapConsistentHash.java)
* [一致性hash-跳转hash](../src/main/java/info/victorchu/algorithms/hash/JumpConsistentHash.java)
* [MurmurHash](../src/main/java/info/victorchu/algorithms/hash/MurmurHash.java)
* [md5](../src/main/java/info/victorchu/algorithms/hash/MD5.java)


### 字符串相关

* [Manacher-最长回文子串](../src/main/java/info/victorchu/algorithms/str/palindrome/Manacher.java)
* [KMP-字符串模式匹配](../src/main/java/info/victorchu/algorithms/str/match/KMPMatcher.java)

### 文本Diff算法

* [Myers 算法]()

### 布尔表达式化简

将布尔化简用于简化复杂filter条件，用作rete算法或是查询优化

* [boolean simplification 代码](../src/main/java/info/victorchu/algorithms/booleansimplification/package-info.java)
* 文档    
    * [矩阵化方法化简布尔表达式](./booleanSimplification.md)
    * [矩阵化方法化简布尔表达式V2](./booleanSimplificationV2.md)