package info.loenwind.middletorch;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IProxy {

  public abstract void preinit(FMLPreInitializationEvent event);

  public abstract void init(FMLInitializationEvent event);

}
