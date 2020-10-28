package io.github.overlordsiii.easyvillagers.item;

import io.github.overlordsiii.easyvillagers.EasyVillagers;
import io.github.overlordsiii.easyvillagers.config.EasyVillagerConfig;
import io.github.overlordsiii.easyvillagers.corelib.CachedMap;
import io.github.overlordsiii.easyvillagers.registry.ModItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VillagerItem extends Item {

    private CachedMap<ItemStack, VillagerEntity> cachedVillagers;

    public VillagerItem() {
        super(new Item.Settings().maxCount(1).group(ItemGroup.MISC));
        cachedVillagers = new CachedMap<>(10_000);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else {
            ItemStack itemstack = context.getStack();
            BlockPos blockpos = context.getBlockPos();
            Direction direction = context.getPlayerFacing();
            BlockState blockstate = world.getBlockState(blockpos);

            if (!blockstate.getCollisionShape(world, blockpos).isEmpty()) {
                blockpos = blockpos.offset(direction);
            }

            VillagerEntity villager = getVillager(world, itemstack);

            villager.setPos(blockpos.getX() + 0.5D, blockpos.getY(), blockpos.getZ() + 0.5);

            if (world.spawnEntity(villager)) {
                itemstack.decrement(1);
            }

            return ActionResult.CONSUME;
        }
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
    }
    @Environment(EnvType.CLIENT)
    @Override
    public Text getName(ItemStack stack) {
        World world = MinecraftClient.getInstance().world;
        if (world == null) {
            return super.getName(stack);
        } else {
            VillagerEntity villager = getVillagerFast(world, stack);
            if (!villager.hasCustomName() && villager.isBaby()) {
                return new TranslatableText("item.easy_villagers.baby_villager");
            }
            return villager.getDisplayName();
        }
    }


    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, world, entity, itemSlot, isSelected);
        if (!(entity instanceof PlayerEntity) || world.isClient) {
            return;
        }
        if (!EasyVillagers.CONFIG.villagerMakeSounds) {
            return;
        }
        if (world.getTime() % 20 != 0) {
            return;
        }
        if (world.random.nextInt(20) == 0) {
            PlayerEntity playerEntity = (PlayerEntity) entity;
            playerEntity.playSound(SoundEvents.ENTITY_VILLAGER_AMBIENT, SoundCategory.NEUTRAL, 1F, 1F);
        }
    }

    public void setVillager(ItemStack stack, VillagerEntity villager) {
        CompoundTag compound = stack.getOrCreateSubTag("villager");
        villager.writeCustomDataToTag(compound);
    }

    public VillagerEntity getVillager(World world, ItemStack stack) {
        CompoundTag compound = stack.getOrCreateSubTag("villager");
        if (compound == null) {
            compound = new CompoundTag();
        }

        VillagerEntity villager = new VillagerEntity(EntityType.VILLAGER, world);
        villager.readCustomDataFromTag(compound);
        return villager;
    }

    public VillagerEntity getVillagerFast(World world, ItemStack stack) {
        return cachedVillagers.get(stack, () -> getVillager(world, stack));
    }
    @Environment(EnvType.CLIENT)
    public static ItemStack getBabyVillager() {
        ItemStack babyVillager = new ItemStack(ModItems.VILLAGER);
        assert MinecraftClient.getInstance().world != null;

        VillagerEntity villager = new VillagerEntity(EntityType.VILLAGER, MinecraftClient.getInstance().world) {
            @Override
            public int getBreedingAge() {
                return -24000;
            }
        };
        ModItems.VILLAGER.setVillager(babyVillager, villager);
        return babyVillager;
    }

}