package net.kapitencraft.mysticcraft.tech.item;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.registry.ModBlocks;
import net.kapitencraft.mysticcraft.tech.DistributionNetworkManager;
import net.kapitencraft.mysticcraft.tech.ManaDistributionNetwork;
import net.kapitencraft.mysticcraft.tech.block.DistributionNetworkBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TechWandItem extends Item {
    public TechWandItem() {
        super(MiscHelper.rarity(Rarity.RARE).stacksTo(1));
    }

    //TODO ensure cache is cleared after game is closed
    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos clicked = pContext.getClickedPos();
        BlockState clickedState = level.getBlockState(clicked);
        ItemStack item = pContext.getItemInHand();
        if (clickedState.is(ModBlocks.MANA_RELAY.get()) || clickedState.is(ModBlocks.MANA_PORT.get())) {
            CompoundTag tag = item.getOrCreateTag();
            Player player = pContext.getPlayer();
            if (tag.contains("origin", Tag.TAG_LONG)) {
                BlockPos original = BlockPos.of(tag.getLong("origin"));
                if (!original.equals(clicked)) {
                    DistributionNetworkManager manager = DistributionNetworkManager.get(level);
                    if (manager != null) {
                        //HitResult result = level.clip(new ClipContext(original.getCenter(), clicked.getCenter(), ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, null));
                        //if (result.getType() != HitResult.Type.MISS) {
                        //    if (player != null) player.sendSystemMessage(Component.translatable("tech_wand.connect.failed").withStyle(ChatFormatting.RED));
                        //}
                        ManaDistributionNetwork firstNetwork = manager.getOrCreateNetwork(original, getNodeType(level, original));
                        ManaDistributionNetwork secondNetwork = manager.getOrCreateNetwork(clicked, getNodeType(level, clicked));
                        if (firstNetwork == secondNetwork && firstNetwork.getNode(original).getConnected().contains(firstNetwork.getNode(clicked))) {
                            sendMessage(player, Component.translatable("tech_wand.connect.already", convertBlockPos(original), convertBlockPos(clicked)).withStyle(ChatFormatting.RED));
                        } else {
                            manager.connect(firstNetwork, secondNetwork, original, clicked, level);
                            sendMessage(player, Component.translatable("tech_wand.connect.success", convertBlockPos(original), convertBlockPos(clicked)).withStyle(ChatFormatting.GREEN));
                        }
                        return InteractionResult.sidedSuccess(level.isClientSide());
                    }
                } else {
                    sendMessage(player, Component.translatable("tech_wand.connect.self").withStyle(ChatFormatting.RED));
                }
            } else {
                tag.putLong("origin", clicked.asLong());
                sendMessage(player, Component.translatable("tech_wand.connect.stored", convertBlockPos(clicked)).withStyle(ChatFormatting.GREEN));
                return InteractionResult.sidedSuccess(level.isClientSide());
            }
        }
        return super.useOn(pContext);
    }

    private static String convertBlockPos(BlockPos pos) {
        return String.format("[%s,%s,%s]", pos.getX(), pos.getY(), pos.getZ());
    }

    private static void sendMessage(Player player, Component msg) {
        if (player != null && player.level().isClientSide())
            player.sendSystemMessage(msg);

    }

    private static ManaDistributionNetwork.Node.Type getNodeType(Level level, BlockPos pos) {
        if (level.getBlockState(pos).getBlock() instanceof DistributionNetworkBlock DNB) {
            return DNB.getType();
        }
        return ManaDistributionNetwork.Node.Type.PORT;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        stack.removeTagKey("origin");
        return InteractionResultHolder.sidedSuccess(stack, pLevel.isClientSide);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (pStack.hasTag()) {
            BlockPos pos = BlockPos.of(pStack.getTag().getLong("origin"));
            pTooltipComponents.add(Component.translatable("tech_wand.selected", convertBlockPos(pos)).withStyle(ChatFormatting.GREEN));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
