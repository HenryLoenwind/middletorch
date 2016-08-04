package info.loenwind.middletorch;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
