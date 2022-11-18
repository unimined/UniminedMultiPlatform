package org.example.forge;

import net.minecraftforge.fml.common.Mod;
import org.example.UniminedMultiplatformExample;

@Mod("examplemod")
public class ExampleMod {

    public ExampleMod() {
        System.out.println("Hello from Forge!");
        UniminedMultiplatformExample.printHello();
    }

}
