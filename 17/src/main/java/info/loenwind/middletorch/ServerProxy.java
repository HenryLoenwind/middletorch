package info.loenwind.middletorch;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ServerProxy implements IProxy {

	@Override
	public void init(FMLInitializationEvent event) {
	}

	@Override
	public void preinit(FMLPreInitializationEvent event) {
		event.getModLog().warn("Middletorch is a client-side mod. You do not need it on the server. However, it will do nothing here...");
	}

}
