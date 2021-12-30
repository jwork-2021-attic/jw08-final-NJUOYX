package nju.java.logic.element.utils;

import nju.java.logic.system.GSystem;
import org.junit.Test;

import java.io.IOException;

public class BarrelTest {
    GSystem system;
    public BarrelTest(){
        this.system = new GSystem();
    }

    @Test
    public void barrelTest(){
        Barrel barrel = new Barrel(10,10,"left",system);

    }
}
