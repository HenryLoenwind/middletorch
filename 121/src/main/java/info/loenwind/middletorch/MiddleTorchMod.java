package info.loenwind.middletorch;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = MiddleTorchMod.MODID, dist = Dist.CLIENT)
public class MiddleTorchMod {
  public static final String MODID = "middletorch";

  public MiddleTorchMod(ModContainer container) {
	  container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
  }
  
}
