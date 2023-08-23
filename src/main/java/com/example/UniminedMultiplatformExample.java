package com.example;

import net.minecraft.client.MinecraftClient;

public class UniminedMultiplatformExample {

    public static void printHello() {
        System.out.println("Hello from Java!");
        System.out.println(MinecraftClient.getInstance());
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(MinecraftClient.getInstance());
            }
        }).start();
    }

}
