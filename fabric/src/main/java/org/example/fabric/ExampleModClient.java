package org.example.fabric;

import net.fabricmc.api.ClientModInitializer;
import org.example.UniminedMultiplatformExample;

public class ExampleModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        System.out.println("Hello from Fabric client!");
        UniminedMultiplatformExample.printHello();
    }

}
