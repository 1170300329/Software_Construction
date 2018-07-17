这个工程的四个APP都是命令行模式的，命令详解如下：

1.首先会需要用户输入测试文件的路径，比如：

>src/test.txt

工程中带有的四个测试文件：

> test.txt:MovieGraph
>
> test1.txt:NetworkTopology
>
> test2.txt:SocialNetwork
>
> test3.txt:GraphPoet

2.指令说明

```
vertex --add label type                                      
增加结点：label是要增加的结点的标签，type是要增加结点的类型

vertex --delete regex　
删除结点：删除满足条件的点(regex=点label时可以删除相应点)

vertex --change label Attributes(e.g. age/sex/year)　to attrs 
修改属性：修改标签为label的顶点的属性值，Attributes是要修改的属性，比如age，sex，year等等，attrs是要修改后的属性的值。

edge --add label type [weighted=Y|N] [weight] [directed=Y|N] v1 v2
增边：label是增加的边的label，type是增加的边的类型，weight是权重，directed是是否有向，v1，v2是端点。

edge --delete regex                                                  
删边：删除满足条件的边(regex=边label时可以删除相应边)

edge --reweight label to x 
修改标签为label的边的权值为x

edge --redirect label
修改标签为label的边的方向

edge --remove GraphPoet weight below n
删除GraphPoet中权值小于ｎ的边

hyperedge --add label type vertex1 
加入超边，type是加入的边的类型，vertex1是要加入的超边里包含的一个顶点（之前存在）

hyperedge --remove edgelabel  vertexlabel 
从标签为edgelabel的超边中删除vertexlabel

hyperedge --add edgelabel  vertexlabel  
从标签为edgelabel的超边中加入vertexlabel(之前已经存在的顶点)

compute v1 v2/null    
计算图的各种度的信息，第二个顶点参数可选，如果不需要，则填null
这两个可选的顶点用来计算相应的各种参数，第一个参数必须要填，
第二个参数如果不需要求v1和v2的距离的话就可以填null

graph --toString 
打印出图的信息，以文本形式呈现

graph --show   
打印出图，并在图上做出一些改变（这些改变在关闭窗口之后并不对原有的边构成影响。）

```

3.指令实例

见工程中的文件:examplecmds.txt

4.关于中心度

①部分中心度调用了第三方库的代码，原来利用Floyd的代码在**GraphMetrics2.java** 有实现

②这里的中心度将所有的边的权值都赋值为1，如果需要改变在minPath()方法中将构造邻接矩阵的时候赋值改为图中边的weight即可。