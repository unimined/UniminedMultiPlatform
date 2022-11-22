package org.example;

import net.minecraft.client.Minecraft;

public class UniminedMultiplatformExample {

    public static void printHello() {
        System.out.println("Hello from Java!");
        System.out.println(Minecraft.getMinecraft());
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Minecraft.getMinecraft());
            }
        }).start();
    }

}
