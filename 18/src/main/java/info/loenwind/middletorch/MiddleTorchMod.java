package info.loenwind.middletorch;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = MiddleTorchMod.MODID, version = MiddleTorchMod.VERSION)
public class MiddleTorchMod
{
    public static final String MODID = "middletorch";
    public static final String VERSION = "2.0.3";
    
    @SidedProxy(clientSide="info.loenwind.middletorch.ClientProxy")
    public static IProxy proxy;
    
    @EventHandler
    public void init(FMLPreInitializationEvent event)
    {
		proxy.preinit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		proxy.init(event);
    }
}
