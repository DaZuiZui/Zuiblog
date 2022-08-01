package com.dazuizui.openfeign;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OpenfeignApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(Math.floor(2.5));
        System.out.println(Math.rint(2.7));
        System.out.println(Math.round(2.5));
    }

}
