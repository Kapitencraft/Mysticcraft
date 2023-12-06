package net.kapitencraft.mysticcraft.item.creative;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.api.QuadConsumer;
import net.kapitencraft.mysticcraft.config.CommonModConfig;
import net.kapitencraft.mysticcraft.helpers.AttributeHelper;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.helpers.TextHelper;
import net.kapitencraft.mysticcraft.item.misc.IModItem;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BuildersWand extends Item implements IModItem {
    private BuildType type = BuildType.LINE;
    private Block requiredBlock = Blocks.AIR;

    private final List<BlockPos> posList = new ArrayList<>();

    private final List<Block> useAbles = new ArrayList<>();

    private MutableComponent extraMessage = Component.literal("");
    private int msgTime = 0;
    int test = 0;

    public BuildersWand() {
        super(MiscHelper.rarity(FormattingCodes.LEGENDARY).stacksTo(1));
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int p_41407_, boolean flag) {
        if (entity instanceof Player living && living.getMainHandItem() == stack) {
            living.displayClientMessage(Component.translatable("builders_wand.type").append(": ").append(Component.translatable(this.type.translateKey)).append(" ").append(extraMessage).withStyle(ChatFormatting.GREEN), true);
        }
        if (this.msgTime <= 0) {
            extraMessage = Component.literal("");
        } else {
            this.msgTime--;
        }
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
        String s = TextHelper.makeList(posList, TextHelper::fromBlockPos);
        Component component = TextHelper.makeList(useAbles, Component::empty, Block::getName, MutableComponent::append);
        list.add(Component.literal("saved positions: " + s).withStyle(ChatFormatting.GREEN));
        list.add(Component.literal("active blocks: ").append(component).withStyle(ChatFormatting.GREEN));
        if (Screen.hasControlDown()) {
            list.add(Component.literal("click on block + CTRL: add useAble"));
            list.add(Component.literal("click on block + SHIFT: add pos"));
            list.add(Component.literal("click on block + CTRL + SHIFT: reset pos"));
            list.add(Component.literal("click on block + ALT: reset blocks"));
        } else {
            list.add(Component.literal("press [CTRL] for controls"));
        }
        super.appendHoverText(p_41421_, p_41422_, list, p_41424_);
    }

    private void setMsg(MutableComponent component) {
        this.msgTime = 100;
        this.extraMessage = component;
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        if (test == 0) {
            Block block = context.getLevel().getBlockState(context.getClickedPos()).getBlock();
            if (Screen.hasControlDown()) {
                if (Screen.hasShiftDown()) {
                    posList.clear();
                    setMsg(Component.literal("cleared positions list"));
                } else if (!useAbles.contains(block)) {
                    useAbles.add(block);
                    setMsg(Component.literal("added block ").append(block.getName()));
                }
            } else if (Screen.hasShiftDown()) {
                if (posList.size() < type.getPosAmount()) {
                    posList.add(context.getClickedPos());
                    setMsg(Component.literal("added " + TextHelper.fromBlockPos(context.getClickedPos()) + " to the list"));
                    if (posList.size() == type.getPosAmount() && CommonModConfig.useOnComplete) {
                        use(context.getLevel());
                    }
                }
            } else if (Screen.hasAltDown()) {
                useAbles.clear();
                setMsg(Component.literal("cleared possible blocks"));
            } else {
                if (type.hasRequiredBlock()) {
                    requiredBlock = block;
                    setMsg(Component.literal("set required block to: ").append(requiredBlock.getName()));
                    return InteractionResult.SUCCESS;
                } else {
                    return InteractionResult.FAIL;
                }
            }
            test++;
        } else {
            test = 0;
            return InteractionResult.FAIL;
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (test == 0) {
            if (Screen.hasShiftDown() && !level.isClientSide()) {
                use(level);
            } else if (Screen.hasControlDown()) {
                this.type = type.next();
            } else {
                return super.use(level, player, hand);
            }
            test++;
        } else {
            test = 0;
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        HashMultimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
        multimap.put(ForgeMod.REACH_DISTANCE.get(), AttributeHelper.createModifier("Builder's Wand Modifier", AttributeModifier.Operation.ADDITION, 100));
        return slot == EquipmentSlot.MAINHAND ? multimap : super.getAttributeModifiers(slot, stack);
    }

    private void use(Level level) {
        if (posList.size() == type.getPosAmount() && !level.isClientSide()) {
            try {
                type.consumer.accept(requiredBlock, useAbles, posList, level);
                setMsg(Component.literal("used!"));
                if (CommonModConfig.resetBuildersWand) posList.clear();
            } catch (Exception e) {
                setMsg(Component.literal("Unable to use Builder's wand: " + e.getMessage()));
            }
        }
    }

    @Override
    public TabGroup getGroup() {
        return null;
    }

    public enum BuildType {
        LINE(1, false, 2, (block, blocks, blockPos, level) -> {
            BlockPos pos1 = blockPos.get(0);
            BlockPos pos2 = blockPos.get(1);
            List<BlockPos> line = MathHelper.makeLine(pos1, pos2, MathHelper.LineSize.THIN);
            line.forEach(pos3 -> checkAndPlaceBlock(level, block, pos3, blocks));
        }, "builders_wand.line"),
        CUBOID(2, false, 2, (block, blocks, blockPos, level) -> {
            BlockPos pos1 = blockPos.get(0);
            BlockPos pos2 = blockPos.get(1);
            BlockPos diff = pos2.subtract(pos1);
            MathHelper.forCube(diff, (pos) -> {
                BlockPos pos3 = diff.offset(pos1.getX() + pos.getX(), pos1.getY() + pos.getY(), pos1.getZ() + pos.getZ());
                level.setBlock(pos3, MathHelper.pickRandom(blocks).defaultBlockState(), 3);
            });
        }, "builders_wand.cuboid"),
        SPHERE(3, false, 2, (block, blocks, blockPos, level) -> {}, "builders_wand.sphere"),
        CONE(4, false, 3, (block, blocks, blockPos, level) -> {}, "builders_wand.cone"),
        CYLINDER(5,false, 3, (block, blocks, blockPos, level) -> {}, "builders_wand.cylinder"),
        REPLACE(6, true, 2, (block, blocks, blockPos, level) -> {
            BlockPos start = blockPos.get(0);
            BlockPos stop = blockPos.get(1);
            for (int x = start.getX(); x < stop.getX(); x++) {
                for (int y = start.getY(); y < stop.getY(); y++) {
                    for (int z = start.getZ(); z < stop.getZ(); z++) {
                        BlockPos pos = new BlockPos(x, y, z);
                        checkAndPlaceBlock(level, block, pos, blocks);
                    }
                }
            }
        }, "builders_wand.replace");

        private static void checkAndPlaceBlock(Level level, Block block, BlockPos pos, List<Block> blocks) {
            if ((block == null || level.getBlockState(pos).getBlock() == block)) {
                level.setBlock(pos, MathHelper.pickRandom(blocks).defaultBlockState(), 3);
            }
        }

        private final boolean hasRequiredBlock;
        private final int posAmount;
        private final int pos;
        private final String translateKey;
        private final QuadConsumer<Block, List<Block>, List<BlockPos>, Level> consumer;
        BuildType(int pos, boolean hasRequiredBlock, int posAmount, QuadConsumer<Block, List<Block>, List<BlockPos>, Level> consumer, String s) {
            this.pos = pos;
            this.hasRequiredBlock = hasRequiredBlock;
            this.posAmount = posAmount;
            this.consumer = consumer;
            this.translateKey = s;
        }

        public BuildType next() {
            return getById(this.pos + 1);
        }

        public BuildType getById(int id) {
            return MiscHelper.getValue(BuildType::getPos, LINE, id, values());
        }


        public boolean hasRequiredBlock() {
            return hasRequiredBlock;
        }

        public int getPosAmount() {
            return posAmount;
        }

        public int getPos() {
            return pos;
        }
    }
}
