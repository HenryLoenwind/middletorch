package info.loenwind.middletorch;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MiddleTorchMod.MODID)
public class MiddleTorchMod {
  public static final String MODID = "middletorch";

  public MiddleTorchMod() {
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
  }

  private void doClientStuff(final FMLClientSetupEvent event) {
    KeyInputHandler.init();
  }

}
