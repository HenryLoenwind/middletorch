package info.loenwind.middletorch;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class KeyInputHandler {

	private static KeyInputHandler me;
	private static KeyBinding keyBinding;
	
	public static void init() {
		keyBinding = new KeyBinding("key.placetorch", Keyboard.CHAR_NONE, "key.categories.gameplay");
		me = new KeyInputHandler();
        ClientRegistry.registerKeyBinding(keyBinding);
        MinecraftForge.EVENT_BUS.register(me);
	}

	@SubscribeEvent
    public void onKeyInput(InputEvent.MouseInputEvent event) {
        if(keyBinding.isPressed()) {
    		Minecraft mc = Minecraft.getMinecraft();
    		int currentItem = mc.thePlayer.inventory.currentItem;
    		for (int slot = 0; slot <= 8; slot++) {
    			if (mc.thePlayer.inventory.mainInventory[slot] != null && mc.thePlayer.inventory.mainInventory[slot].getItem() instanceof ItemBlock) {
    				Block block = ((ItemBlock) mc.thePlayer.inventory.mainInventory[slot].getItem()).block;
					if (block instanceof BlockTorch || block.getUnlocalizedName().equals("tile.decoration.stonetorch")) {
	    				mc.thePlayer.inventory.currentItem = slot;
	    				mc.playerController.onPlayerRightClick(
	    						mc.thePlayer, mc.theWorld,
	    						mc.thePlayer.inventory.mainInventory[slot],
	    						mc.objectMouseOver.getBlockPos(), mc.objectMouseOver.sideHit,
	    						mc.objectMouseOver.hitVec);
	    				mc.thePlayer.inventory.currentItem = currentItem;
	    				return;
        			}
    			}
    		}
        }
    }

}
