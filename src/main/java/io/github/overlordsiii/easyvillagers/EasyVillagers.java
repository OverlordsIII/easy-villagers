package io.github.overlordsiii.easyvillagers;

import io.github.overlordsiii.easyvillagers.config.EasyVillagerConfig;
import io.github.overlordsiii.easyvillagers.registry.ModItems;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.ConfigHolder;
import me.sargunvohra.mcmods.autoconfig1u.event.ConfigSerializeEvent;
import me.sargunvohra.mcmods.autoconfig1u.serializer.DummyConfigSerializer;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.ActionResult;

public class EasyVillagers implements ModInitializer {
    public static EasyVillagerConfig CONFIG;
    static {
        AutoConfig.register(EasyVillagerConfig.class, JanksonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(EasyVillagerConfig.class).getConfig();
    }
    @Override
    public void onInitialize() {
        ModItems.init();
        AutoConfig.getConfigHolder(EasyVillagerConfig.class).registerLoadListener((manager, newData) -> {
            System.out.println("Loading...");
            return ActionResult.SUCCESS;
        });
        AutoConfig.getConfigHolder(EasyVillagerConfig.class).registerSaveListener((manager, data) -> {
            System.out.println("Saving...");
            return ActionResult.SUCCESS;
        });
    }
}
