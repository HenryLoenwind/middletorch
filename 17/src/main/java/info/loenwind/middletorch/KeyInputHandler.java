package info.loenwind.middletorch;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class KeyInputHandler {

  private static KeyInputHandler me;
  private static KeyBinding keyBinding;

  public static void init() {
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
          mc.thePlayer.inventory.currentItem = slot;
          mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.mainInventory[slot], mc.objectMouseOver.blockX,
              mc.objectMouseOver.blockY, mc.objectMouseOver.blockZ, mc.objectMouseOver.sideHit, mc.objectMouseOver.hitVec);
          mc.thePlayer.inventory.currentItem = currentItem;
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
      Block block = ((ItemBlock) candidate.getItem()).field_150939_a;
      return block instanceof BlockTorch || "tile.decoration.stonetorch".equals(block.getUnlocalizedName());
    }
    return "item.silentgems:TorchBandolier".equals(candidate.getItem().getUnlocalizedName(candidate));
  }

}
