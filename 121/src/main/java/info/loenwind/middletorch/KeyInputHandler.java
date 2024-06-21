package info.loenwind.middletorch;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BaseTorchBlock;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;

@EventBusSubscriber(modid = MiddleTorchMod.MODID, value = Dist.CLIENT, bus = Bus.GAME)
public class KeyInputHandler {

	public static final TagKey<Item> TORCH = ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "torches"));
	public static final TagKey<Item> TORCH_PLACER = ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "torchplacers"));

	public static KeyMapping keyBinding;
	private static int middleClickDelay = 0;

	@EventBusSubscriber(modid = MiddleTorchMod.MODID, value = Dist.CLIENT, bus = Bus.MOD)
	private static final class Dummy {
	
		@SubscribeEvent
		public static void registerBindings(RegisterKeyMappingsEvent event) {
			keyBinding = new KeyMapping("key.placetorch", KeyConflictContext.IN_GAME, InputConstants.UNKNOWN, "key.categories.gameplay");
			event.register(keyBinding);
		}

	}

	@SubscribeEvent
	public static void onKeyInput(ClientTickEvent.Post event) {
		if (middleClickDelay > 0) {
			middleClickDelay--;
		}
		Minecraft mc = Minecraft.getInstance();
		if (keyBinding.isDown() && middleClickDelay == 0 && !mc.player.isUsingItem()) {
			middleClickDelay = 4;
			int currentItem = mc.player.getInventory().selected;
			for (int slot = 0; slot <= 8; slot++) {
				ItemStack stack = mc.player.getInventory().items.get(slot);
				final Item item = stack.getItem();
				if (Block.byItem(item) instanceof BaseTorchBlock || stack.is(TORCH) || stack.is(TORCH_PLACER)) {
					mc.player.getInventory().selected = slot;
					mc.startUseItem();
					mc.player.getInventory().selected = currentItem;
					return;
				}
			}
		}
	}

}
