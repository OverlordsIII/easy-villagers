package io.github.overlordsiii.easyvillagers.client.renderer;

import io.github.overlordsiii.easyvillagers.item.VillagerItem;
import io.github.overlordsiii.easyvillagers.registry.ModItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.VillagerEntityRenderer;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ReloadableResourceManager;

public class VillagerItemRenderer extends BuiltinModelItemRenderer {
    public static VillagerItemRenderer INSTANCE = new VillagerItemRenderer();
    private VillagerEntityRenderer renderer;

    @Override
    public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        VillagerItem item = (VillagerItem) stack.getItem();
        MinecraftClient minecraft = MinecraftClient.getInstance();
        if (renderer == null) {
            renderer = new VillagerEntityRenderer(minecraft.getEntityRenderDispatcher(), (ReloadableResourceManager) minecraft.getResourceManager());
        }
        matrices.translate(0.5, 0, 0.5);
        renderer.render(item.getVillager(minecraft.world, stack), 0F, 1F, matrices, vertexConsumers, light);
    }
}
