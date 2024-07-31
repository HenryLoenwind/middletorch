package info.loenwind.middletorch;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.gui.screens.options.controls.KeyBindsList.KeyEntry;
import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.neoforged.fml.ModContainer;

public class ConfigurationScreen extends OptionsSubScreen {

	@SuppressWarnings("resource")
	public ConfigurationScreen(final ModContainer container, final Screen parent) {
		super(parent, Minecraft.getInstance().options, Component.translatable(MiddleTorchMod.MODID + ".configuration.title"));
	}

	@Override
	protected void addOptions() {
		list.addSmall(Button.builder(Component.translatable("controls.keybinds"), p_346358_ -> minecraft.setScreen(new InjectKeybindScreen(this, options))).build(), null);
	}

	private static class InjectKeybindScreen extends KeyBindsScreen {

		public InjectKeybindScreen(final Screen parent, final Options options) {
			super(parent, options);
		}

		@Override
		protected void addContents() {
			super.addContents();
			keyBindsList.children().removeAll(keyBindsList.children().stream().filter( entry -> !(entry instanceof KeyEntry) || (((KeyEntry) entry).key != KeyInputHandler.keyBinding)).toList());
		}

		@Override
		protected void addFooter() {
			resetButton = Button.builder(Component.translatable("controls.resetAll"), button -> {}).build();
			layout.addToFooter(Button.builder(CommonComponents.GUI_DONE, button -> onClose()).width(200).build());
		}

	}

}
