package net.kapitencraft.mysticcraft.item;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.item.gemstone.Gemstone;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.HashMap;

@Mod.EventBusSubscriber(modid = MysticcraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TabRegister {

    @SubscribeEvent
    public static void registerTabs(CreativeModeTabEvent.Register event) {
        event.registerCreativeModeTab(new ResourceLocation(MysticcraftMod.MOD_ID, "spell_and_gemstone"), builder ->
                builder.m_257941_(Component.translatable("itemGroup.spell_and_gemstone"))
                        .m_257737_(() -> new ItemStack(ModItems.STAFF_OF_THE_WILD.get()))
                        .m_257501_(((featureFlagSet, output, flag) -> {
                            output.m_246326_(ModItems.STAFF_OF_THE_WILD.get());
                            output.m_246326_(ModItems.HEATED_SCYTHE.get());
                            output.m_246326_(ModItems.FIERY_SCYTHE.get());
                            output.m_246326_(ModItems.BURNING_SCYTHE.get());
                            output.m_246326_(ModItems.INFERNAL_SCYTHE.get());
                            output.m_246326_(ModItems.WIZARD_HAT.get());
                            output.m_246326_(ModItems.SLIVYRA.get());
                            output.m_246326_(ModItems.THE_STAFF_DESTRUCTION.get());
                            addGemstones(ModItems.ALMANDINE_GEMSTONES, output);
                            addGemstones(ModItems.JASPER_GEMSTONES, output);
                            addGemstones(ModItems.RUBY_GEMSTONES, output);
                            addGemstones(ModItems.SAPPHIRE_GEMSTONES, output);
                        }))
        );
    }

    private static void addGemstones(HashMap<Gemstone.Rarity, RegistryObject<Item>> gemstone, CreativeModeTab.Output output) {
        Collection<RegistryObject<Item>> gemstones = gemstone.values();
        for (RegistryObject<Item> registryObject : gemstones) {
            output.m_246326_(registryObject.get());
        }
    }
}
