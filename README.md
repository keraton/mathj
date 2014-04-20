MathJ
=====

Math using object perspective. Instead of created a specific method to define a mathematical function, MathJ offers a dynamic approach. This is a example how to use MathJ : 


    @Test public void
    should_run_integration_test() {
        Context context = new MapContext();
        MathJ mathJ = new MathJ(context);

        Equation eq_2x_add_2y = asEq(asConst(2)).multiply(asVar("x"))
                                                                .add(
                                asEq(asConst(2)).multiply(asVar("y"))
                                );
        // By default x=0, y=0
        mathJ.addContext("x")
             .addContext("y");

        // 2(0) + 2(0) = 0
        assertEquals(0, mathJ.apply(eq_2x_add_2y).value());

        // 2(3) + 2(4) = 14
        mathJ.setContext("x", asConst(3))
                .setContext("y", asConst(4));
        assertEquals(14, mathJ.apply(eq_2x_add_2y).value());

    }


MathJ is limited to integer type operation, it can handles : add, minus, multiply and divide operation.

MathJ provide a simple String parser to interpret a mathematical expression to a java object

    @Test public void // 2 / ((1 + 1) + 2
    should_2_divide_$$1_plus_1££_plus_2() throws SyntaxException, ValidationReaderException {
        Function func =reader.read("2 / ((1 + 1)) + 2");

        assertEquals(3, value(func));
    }
    
    ...
    
    private int value(Function func) {
        return mathJ.apply(func).value();
    }
    


