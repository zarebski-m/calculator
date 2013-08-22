calculator
==========

Calculator computing arithmetic expressions.

Features
--------

* Calculator uses [*Reverse Polish Notation*](http://en.wikipedia.org/wiki/Reverse_Polish_notation) for evaluation of math expressions.
* Defines some builtin functions:
  * trigonometric (`sin`, `cos`, `tan`),
  * inverse trigonometric (`asin`, `acos`, `atan`, `atan2`),
  * hyperbolic (`sinh`, `cosh`, `tanh`),
  * `log` and `exp`,
  * `abs`, `sgn` and `sqrt`,
  * conversions: degrees to radians (`d2r`), radians to degrees (`r2d`),
  * `min` and `max`,
  * `neg` (unary minus).
* Predefined constants: `PI` and `E`.
* Calculator allows user to define own custom functions and constants.

Expression notation
-------------------

As mentioned before, calculator evaluates expressions using RPN and [shunting-yard algorithm](http://en.wikipedia.org/wiki/Shunting-yard_algorithm). No additional syntax check is performed, which makes expression syntax quite liberal. Here are some examples of valid expressions:

```
> 2+2*2
6.0

> 2^2^3
256.0

> sin(PI)
1.2246467991473532E-16

> cos 0*2
2.0

> atan2(2+2, 3+3)
0.5880026035475675

> log exp -1
-1

> 7-(2*PI + cos 12.3456)
-0.25891429587906956
```

As can be seen, brackets are not needed for unary functions. All operators except power (`^`) are evaluated left to right. Power operator as well as functions are evaluated right to left. This allows function composition without explicit using brackets.

Moreover, functions have higher priority than operators. Following table shows operator precedence and associativity.

<table>
<tr>
  <th>Function or operator</th><th>Priority</th><th>Associativity</th><th>Comment</th>
</tr>
<tr>
  <td><pre>( ) ,</pre></td><td>-1</td><td>Right</td><td>No actions are executed; these operators are only used as “terminals” in the expression.</td>
</tr>
<tr>
  <td>Additive: +, -</td><td>1</td><td>Left</td><td></td>
</tr>
<tr>
  <td>Multiplicative: *, /, %</td><td>2</td><td>Left</td><td></td>
</tr>
<tr>
  <td>Power: ^</td><td>3</td><td>Right</td><td>Evaluated right to left for expressions like 2^3^4 (== 2^(3^4))</td>
</tr>
<tr>
  <td>Function</td><td>10</td><td>Right</td><td></td>
</tr>
<tr>
  <td>Constant</td><td>100</td><td>Left</td><td>Constants are a special kind of functions that take no parameters. “Ultra high” priority causes them to be always evaluated first, and left associativity assures they are left-to-right evaluated.</td>
</tr>
</table>

Special commands
----------------

Special commands start with colon (`:`). Here is the list of all defined commands:
* `:c`, `:del` and `:ce` - clear and clear all,
* `:func`, `:const` and `:s` - user defined functions and constants,
* `:p` and `:pd` - print list of functions and constants.

**Clearing results**

* `:c` - clear. Result of every expression evaluation is stored in variable `ans`. Command `:c` erases last result and sets `ans` to 0.
* `:del {name}` - deletes constant or function with name `{name}`. Default builtin functions and constants (e.e. `sin` or `PI`) cannot be removed.
* `:ce` - clear all. Clears last result (see `:c`) and deletes all user defined functions and constants.

**Defining functions and constants**

Custom functions and constants are nothing more than expressions. Arguments for function are denoted in its body as `{0}`, `{1}`..., `{N}`. Constants are functions without arguments, which makes them constant expressions.

Function or constant name cannot be equal to any of the default function or constant. If custom (non-default) function or constant with the same name already exists, new function or constant overwrites it.

* `:func {name} {expression}` - define a function with name `{name}`. Examples of function definitions:
  * `:func square {0}*{0}` - defines an unary function named `square` that multiplies the argument by itself,
  * `:func dist max({0},{1}) - min({0},{1})` - defines binary function calculating absolute distance between two numbers.
* `:const {name} {expression}` - define a constant with name `{name}`. Examples of constant definitions:
  * `:const ONE 1` - constant with value 1,
  * `:const PI_2 PI/2` - constant with value PI / 2.
* `:s {name}` - save last result (i.e. `ans`) to a new constant named `{name}`.

**Printing functions and constants**

* `:p` - print all custom functions and constants.
* `:pd` - print all default, predefined functions and constants (e.g. `sin`, `abs`, `PI`).