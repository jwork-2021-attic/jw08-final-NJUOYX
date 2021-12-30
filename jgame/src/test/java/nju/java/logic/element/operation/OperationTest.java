package nju.java.logic.element.operation;

import nju.java.logic.element.opration.Attack;
import nju.java.logic.element.opration.ToBrotherAttack;
import org.junit.Test;
import static org.junit.Assert.*;

public class OperationTest {
    @Test
    public void createTest(){
        assertNotNull(new Attack());
        assertNotNull(new ToBrotherAttack());
    }
}
