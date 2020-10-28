package io.github.overlordsiii.easyvillagers.mixin.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity implements VillagerDataContainer {
    public VillagerEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }
    @Redirect(method = "interactMob",at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/VillagerEntity;hasCustomer()Z"))
    private boolean redirectCanAccess(VillagerEntity entity, PlayerEntity player, Hand hand){
        System.out.println("does the entity have a customer? = " + entity.hasCustomer());
        System.out.println("is the player sneaking? = " + player.isSneaking());
        boolean bl = player.isSneaking() && !entity.hasCustomer();
        System.out.println("completed bool = " + bl);
        return !entity.hasCustomer() && player.isSneaking();
    }
}
