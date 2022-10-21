package net.kapitencraft.mysticcraft.init;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class ModCreativeModeTabs {
    public static final CreativeModeTab SPELL_AND_GEMSTONE = new CreativeModeTab("spell_and_gemstone") {
        @Override
        public @Nonnull ItemStack makeIcon() {
            return new ItemStack(ModItems.STAFF_OF_THE_WILD.get());
        }
    };
}
