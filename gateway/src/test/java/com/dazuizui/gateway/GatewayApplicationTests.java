package com.dazuizui.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Scanner;

@SpringBootTest
class GatewayApplicationTests {

    @Test
    void contextLoads() {
        Scanner s = new Scanner(System.in);
        int num = s.nextInt();
        char c= (char) num;
        System.out.println(c);
    }

}
