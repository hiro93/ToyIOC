package net.sk32.toy.demo;

import net.sk32.toy.boot.annotation.TyComponent;

import java.util.Arrays;

@TyComponent
public class Service {
    public int sum(int[] array) {
        return Arrays.stream(array).reduce(Integer::sum).orElse(0);
    }
}
