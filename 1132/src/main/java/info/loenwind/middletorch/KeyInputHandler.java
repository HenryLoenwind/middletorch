package info.loenwind.middletorch;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Nonnull;

import org.lwjgl.glfw.GLFW;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

@EventBusSubscriber(modid = MiddleTorchMod.MODID, value = Dist.CLIENT)
public class KeyInputHandler {

  private static final @Nonnull Tag<Item> TORCH = new ItemTags.Wrapper(new ResourceLocation("forge", "torches"));
  private static final @Nonnull Tag<Item> TORCH_PLACER = new ItemTags.Wrapper(new ResourceLocation("forge", "torchplacers"));

  private static KeyBinding keyBinding;
  private static Method rightClickMouse;

  public static void init() {
    rightClickMouse = ObfuscationReflectionHelper.findMethod(Minecraft.class, "func_147121_ag"); // rightClickMouse()
    keyBinding = new KeyBinding("key.placetorch", GLFW.GLFW_KEY_UNKNOWN, "key.categories.gameplay");
    ClientRegistry.registerKeyBinding(keyBinding);
  }

  @SubscribeEvent
  public static void onKeyInput(TickEvent.ClientTickEvent event) {
    if (event.phase == Phase.END) {
      Minecraft mc = Minecraft.getInstance();
      final GuiScreen currentScreen = mc.currentScreen;
      if (currentScreen == null || currentScreen.allowUserInput) {
        if (keyBinding.isPressed()) {
          int currentItem = mc.player.inventory.currentItem;
          for (int slot = 0; slot <= 8; slot++) {
            final Item item = mc.player.inventory.mainInventory.get(slot).getItem();
            if (Block.getBlockFromItem(item) instanceof BlockTorch || TORCH.contains(item) || TORCH_PLACER.contains(item)) {
              try {
                mc.player.inventory.currentItem = slot;
                rightClickMouse.invoke(mc);
              } catch (IllegalAccessException e) {
                e.printStackTrace();
              } catch (IllegalArgumentException e) {
                e.printStackTrace();
              } catch (InvocationTargetException e) {
                e.printStackTrace();
              } finally {
                mc.player.inventory.currentItem = currentItem;
              }
              return;
            }
          }
        }
      }
    }
  }

}
