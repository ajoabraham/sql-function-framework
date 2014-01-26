sql-function-framework
======================

Framework to parser user input into an abstract function library and translate that input to the correct sql statement depending on database dialect.

How to use
----------

1. Instantiate object of `Parsing` class as a singleton in your application (just as `public static final` variable or through DI containers such as Spring or Guice).
2. Use `Parsing.parse(String)` method to instantiate `Formula` object.
3. Use `Formula` object to perform main operations (such as generating sql clauses).
4. Use `Hints` object to generate hints for user input.

For more details please see javadoc and test cases for methods of `Formula` and `Hints` classes.

How to extend
-------------

If you need to add new dialect:

1. Create new class that implemented `Dialect` interface or extended existed implementation such as `GenericSQL` or `TeradataSQL`.
2. Implement functions that supported by your own dialect.
3. Methods have to throw `UnsupportedOperationException` if dialect doesn't support appropriate operation.

Please take into account that you should instantiate/use appropriate dialect depending on your database.
For example, you should manually check that your Teradata database has version >= 14 and instantiate `Teradata14SQL` dialect,
but for the other versions, you should instantiate `TeradataSQL` dialect.

If you need to add new function:

1. Create a class implemented `FormulaElement` interface or extended one of the classes such as `Scalar2` or `Aggregation`.
2. Set class name that is the name of the function.
3. Registry function in one of the following class - `Aggregations`, `Scalars`, `Olap`. See current implementations and javadoc in `FunctionBuilder` class.

If you need to traverse the formula tree and do some action with its elements,
please see implementation of `Formula.entityNames` or `Formula.windowParameters` methods.
Just implement what you need in the same way.