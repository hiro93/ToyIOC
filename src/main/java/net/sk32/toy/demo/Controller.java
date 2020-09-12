package net.sk32.toy.demo;

import net.sk32.toy.boot.annotation.TyResource;
import net.sk32.toy.boot.annotation.TyComponent;

import java.util.Arrays;

@TyComponent
public class Controller {
    @TyResource
    private Service service;

    public void say() {
        int[] ints = {1, 2, 3, 4, 5};
        System.out.printf("%s = %d\n", Arrays.toString(ints), service.sum(ints));
    }
}
