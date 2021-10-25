# Command Line Table Filter
Command line utitlity to filter results from a table. 


# Getting started
Clone the repository
```
git clone https://github.com/IdrissRio/Filter.git
```

Move into the repository folder

```
cd Filter
```

Run `./gradlew build`. This command will generate a jar file located in `app/filter.jar`.

## Example

Supposing that we get this table in std input:
+---------------------+------------+----------+--------------+------------+--------------+--------+----------+
         NAME         | LINE_START | LINE_END | COLUMN_START | COLUMN_END |   REL_PATH   | VALUE  |   TYPE   |
+---------------------+------------+----------+--------------+------------+--------------+--------+----------+
      MethodDecl      |     2      |    5     |      10      |     13     | Example.java |  bar   |   void   |
  VariableDeclarator  |     3      |    3     |      13      |     16     | Example.java |  BAR   |   int    |
         Block        |     2      |    5     |      15      |     5      | Example.java |   -    |    -     |
       ClassDecl      |     1      |    6     |      1       |     1      | Example.java |   -    | Example  |
  PrimitiveTypeAccess |     3      |    3     |      9       |     11     | Example.java |  int   |   int    |
  PrimitiveTypeAccess |     0      |    0     |      0       |     0      | Example.java |  void  |   void   |
      VarDeclStmt     |     3      |    3     |      9       |     20     | Example.java |   -    |    -     |
       Modifier       |     1      |    1     |      1       |     6      | Example.java | public | Modifier |
    IntegerLiteral    |     3      |    3     |      19      |     19     | Example.java |   2    |   int    |
+---------------------+------------+----------+--------------+------------+--------------+--------+----------+

We will filter it with the following commands:
```java
java -jar filter.jar -filterby=type{int}
```
Producing the following result

+------------------------+--------------+------------+----------------+--------------+----------------+----------+------------+
|          NAME          |  LINE_START  |  LINE_END  |  COLUMN_START  |  COLUMN_END  |    REL_PATH    |  VALUE   |    TYPE    |
+------------------------+--------------+------------+----------------+--------------+----------------+----------+------------+
|   VariableDeclarator   |      3       |     3      |       13       |      16      |  Example.java  |   BAR    |    int     |
|   PrimitiveTypeAccess  |      3       |     3      |       9        |      11      |  Example.java  |   int    |    int     |
|     IntegerLiteral     |      3       |     3      |       19       |      19      |  Example.java  |    2     |    int     |
+------------------------+--------------+------------+----------------+--------------+----------------+----------+------------+
