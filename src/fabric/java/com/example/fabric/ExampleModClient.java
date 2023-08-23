package com.example.fabric;

import com.example.UniminedMultiplatformExample;
import net.fabricmc.api.ClientModInitializer;

public class ExampleModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        System.out.println("Hello from Fabric client!");
        UniminedMultiplatformExample.printHello();
    }

}
