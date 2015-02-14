package info.loenwind.middletorch;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public interface IProxy {

	public abstract void preinit(FMLPreInitializationEvent event);
	
	public abstract void init(FMLInitializationEvent event);

}
