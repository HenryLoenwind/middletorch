package info.loenwind.middletorch;

import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod(modid = MiddleTorchMod.MODID, version = MiddleTorchMod.VERSION)
public class MiddleTorchMod
{
    public static final String MODID = "middletorch";
    public static final String VERSION = "1.0";
    
    @SidedProxy(clientSide="info.loenwind.middletorch.ClientProxy")
    public static IProxy proxy;
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		if (proxy != null) {
			proxy.init();
		}
    }
}
