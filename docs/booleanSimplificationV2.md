# boolean Simplification V2

在上一篇[矩阵化方法化简布尔表达式](./booleanSimplification.md)中，描述了如何将布尔表达式化简成最小项。不足之处是没有对“非“操作的布尔表达式做处理。本文将优化这一点。

## 提取矩阵

设表达式出现的所有布尔变量为xi(i=1,2...n),n为布尔变量的个数,m为表达式的最小项个数。

例如 x1 x3 x5 + x2 x4 + x1 x2 x4 x5 可以写成:

```
[
 1 0 1 0 1
 0 1 0 1 0
 1 1 0 1 1 
]
```

例如 x1 !x3 x5 + x2 !x4 + x1 x2 x4 x5 可以写成:

```
[
 1 0 -1 0 1
 0 1 0 -1 0
 1 1 0 1 1 
]
```

> 对于不可拆分的原子布尔变量,如果表达式存在该表达式填写1，不存在该表达式填写0，存在该表达式的非填写-1.

## 矩阵计算

### 或运算


表达式 A 与表达式 B 的或运算，只需要将A和B的矩阵各行合并在一起，就可以得到结果

```
a11 a12 a13 ... a1n      b11 b12 b13 ... b1n   a11 a12 a13 ... a1n
a21 a22 a23 ... a2n   +  b21 b22 b23 ... b2n = ... ... ... ... ...
... ... ... ... ...      ... ... ... ... ...   am1 am2 am3 ... amn
am1 am2 am3 ... amn      bg1 bg2 bg3 ... bgn   b11 b12 b13 ... b1n
                                               ... ... ... ... ...
                                               bg1 bg2 bg3 ... bgn
```

其中 n 为A和B 使用的布尔变量个数，m 为A 中最小项个数，g 为B 中最小项个数。很显然或运算中不涉及值的计算。

### 与操作

计算真值表:

|A\B|-1|0|1|Ø|
|---:|---:|---:|---:|---:|
|-1|-1|-1|Ø|Ø|
|0|-1|0|1|Ø|
|1|Ø|1|1|Ø|
|Ø|Ø|Ø|Ø|Ø|

> Ø 表示 A 与 A' , 即 永远是false.

### 单行析范矩阵的与操作

对于两个单行析范矩阵，他们的与操作也是一个单行析范矩阵，该矩阵的元素分别是两个矩阵对应元素的计算结果：
    
```
[a1 a2 ... an] * [b1 b2 ... bn] = [a1b1 a2b2 ... anbn]
```

### 析范矩阵的与操作

布尔表达式A 与 布尔表达式B 进行与操作，只要将A与B的矩阵的各行两两进行单行析范矩阵的与操作。

```
1 1 0 0   1 0 1 0   1 1 1 0
0 0 1 0 * 1 1 0 1 = 1 1 0 1
                    1 0 1 0
                    1 1 1 1
```

### 析范矩阵的简化操作

在矩阵中，如果某行含有Ø元素,那么这行要被删除.

```
-1 Ø 0 0
0 1 0 0  =  0 1 0 0
```


在矩阵中，如果某行含有另一行的所有非0元素,那么这行要被删除.

```
1 1 0 0
0 1 0 0  =  0 1 0 0
```

在矩阵中，如果某行与另一行，除了一个互斥，其他所有非0元素相同,那么这两行要被合并.

```
1 1 0 0
-1 1 0 0  =  0 1 0 0
```

### 如何从语法树生成析范矩阵

1. 如果当前语法节点是叶子节点（`atom` 或者 not`atom`), 生成当前节点的一阶析范矩阵
2. 如果当前不是叶子节点：
    1. 获取第一个子节点和第二个子节点
    2. 根据与，或规则进行计算
    3. 使用吸收，化简矩阵
3. 根节点的矩阵就是化简结果
