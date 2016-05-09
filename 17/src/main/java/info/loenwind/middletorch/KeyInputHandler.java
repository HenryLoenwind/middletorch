package info.loenwind.middletorch;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
    FMLCommonHandler.instance().bus().register(me);
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
      return isTorchItem(((ItemBlock) candidate.getItem()).field_150939_a.getUnlocalizedName());
    }
    return isTorchItem(candidate.getItem().getUnlocalizedName(candidate));
  }

  private boolean isTorchItem(String unlocalizedName) {
    return "tile.torch".equals(unlocalizedName) || "tile.decoration.stonetorch".equals(unlocalizedName)
        || "item.silentgems:TorchBandolier".equals(unlocalizedName) || "tile.blockCarpentersTorch".equals(unlocalizedName);
  }

}
