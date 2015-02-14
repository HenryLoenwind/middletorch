package info.loenwind.middletorch;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy implements IProxy {
	
	@Override
	public void init(FMLInitializationEvent event) {
		KeyInputHandler.init();
	}

	@Override
	public void preinit(FMLPreInitializationEvent event) {
	}

}
