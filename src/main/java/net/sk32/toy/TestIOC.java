package net.sk32.toy;

import net.sk32.toy.boot.annotation.TyBootApplication;
import net.sk32.toy.boot.autoconfigure.TyApplication;
import net.sk32.toy.boot.autoconfigure.TyApplicationContext;
import net.sk32.toy.demo.Controller;

@TyBootApplication
public class TestIOC {
    public static void main(String[] args) {
        TyApplication.run(TestIOC.class, args);
        TyApplicationContext context = TyApplicationContext.getInstance();
        Controller bean = context.getBean(Controller.class);
        bean.say();
    }
}
