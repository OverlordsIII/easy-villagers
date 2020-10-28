package io.github.overlordsiii.easyvillagers.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.Comment;

@Config(name = "easyvillagers")
public class EasyVillagerConfig implements ConfigData {
    @Comment("Decides if villagers should make sounds while in the players inventory")
    public boolean villagerMakeSounds = true;
}
