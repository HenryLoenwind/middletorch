package info.loenwind.middletorch;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.lwjgl.input.Keyboard;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@EventBusSubscriber(modid = MiddleTorchMod.MODID)
public class KeyInputHandler {

  private static KeyBinding keyBinding;
  private static Method rightClickMouse;

  public static void init() {
    rightClickMouse = ReflectionHelper.findMethod(Minecraft.class, "rightClickMouse", "func_147121_ag");
    keyBinding = new KeyBinding("key.placetorch", Keyboard.CHAR_NONE, "key.categories.gameplay");
    ClientRegistry.registerKeyBinding(keyBinding);
  }

  @SubscribeEvent
  public static void onKeyInput(InputEvent.MouseInputEvent event) {
    if (keyBinding.isPressed()) {
      Minecraft mc = Minecraft.getMinecraft();
      int currentItem = mc.player.inventory.currentItem;
      for (int slot = 0; slot <= 8; slot++) {
        if (isTorchItem(mc.player.inventory.mainInventory.get(slot))) {
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

  private static boolean isTorchItem(ItemStack candidate) {
    if (candidate.isEmpty()) {
      return false;
    }
    if (candidate.getItem() instanceof ItemBlock) {
      final Block block = ((ItemBlock) candidate.getItem()).getBlock();
      return block instanceof BlockTorch || isTorchItem(block.getUnlocalizedName());
    }
    return isTorchItem(candidate.getItem().getUnlocalizedName(candidate));
  }

  private static boolean isTorchItem(String unlocalizedName) {
    return "tile.torch".equals(unlocalizedName) || "tile.decoration.stonetorch".equals(unlocalizedName)
        || "item.silentgems:torchbandolier".equals(unlocalizedName) || "item.silentgems:torch_bandolier".equals(unlocalizedName)
        || "item.silentgems:torchBandolier".equals(unlocalizedName) || "tile.blockcarpenterstorch".equals(unlocalizedName)
        || "tile.block_carpenters_torch".equals(unlocalizedName) || "tile.blockCarpentersTorch".equals(unlocalizedName)
        || "item.illuminationwand".equals(unlocalizedName) || "item.illumination_wand".equals(unlocalizedName)
        || "item.IlluminationWand".equals(unlocalizedName);
  }

}
