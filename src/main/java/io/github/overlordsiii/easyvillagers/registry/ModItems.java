package io.github.overlordsiii.easyvillagers.registry;

import io.github.overlordsiii.easyvillagers.item.VillagerItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {
    public static VillagerItem VILLAGER = new VillagerItem();
    public static void init(){
        Registry.register(Registry.ITEM, new Identifier("easy_villagers", "villager_item"), VILLAGER);
    }
}
