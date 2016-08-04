package info.loenwind.middletorch;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class KeyInputHandler {

  private static KeyInputHandler me;
  private static KeyBinding keyBinding;
  private static Method rightClickMouse;

  public static void init() {
    rightClickMouse = ReflectionHelper.findMethod(Minecraft.class, Minecraft.getMinecraft(), new String[] { "func_147121_ag", "rightClickMouse" });
    keyBinding = new KeyBinding("key.placetorch", Keyboard.CHAR_NONE, "key.categories.gameplay");
    me = new KeyInputHandler();
    ClientRegistry.registerKeyBinding(keyBinding);
    MinecraftForge.EVENT_BUS.register(me);
  }

  @SubscribeEvent
  public void onKeyInput(InputEvent.MouseInputEvent event) {
    if (keyBinding.isPressed()) {
      Minecraft mc = Minecraft.getMinecraft();
      int currentItem = mc.thePlayer.inventory.currentItem;
      for (int slot = 0; slot <= 8; slot++) {
        if (isTorchItem(mc.thePlayer.inventory.mainInventory[slot])) {
          try {
            mc.thePlayer.inventory.currentItem = slot;
            rightClickMouse.invoke(mc);
          } catch (IllegalAccessException e) {
            e.printStackTrace();
          } catch (IllegalArgumentException e) {
            e.printStackTrace();
          } catch (InvocationTargetException e) {
            e.printStackTrace();
          } finally {
            mc.thePlayer.inventory.currentItem = currentItem;
          }
          return;
        }
      }
    }
  }

  private boolean isTorchItem(ItemStack candidate) {
    if (candidate == null || candidate.getItem() == null || candidate.stackSize <= 0) {
      return false;
    }
    if (candidate.getItem() instanceof ItemBlock) {
      return isTorchItem(((ItemBlock) candidate.getItem()).block.getUnlocalizedName());
    }
    return isTorchItem(candidate.getItem().getUnlocalizedName(candidate));
  }

  private boolean isTorchItem(String unlocalizedName) {
    return "tile.torch".equals(unlocalizedName) || "tile.decoration.stonetorch".equals(unlocalizedName)
        || "item.silentgems:TorchBandolier".equals(unlocalizedName) || "tile.blockCarpentersTorch".equals(unlocalizedName);
  }
}
