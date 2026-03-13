package net.kapitencraft.mysticcraft.rpg.skill.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.kap_lib.component.ExtraComponents;
import net.kapitencraft.kap_lib.core.helpers.MathHelper;
import net.kapitencraft.kap_lib.core.helpers.TextHelper;
import net.kapitencraft.kap_lib.inventory_page.page_renderer.InventoryPageRenderer;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.config.ClientModConfig;
import net.kapitencraft.mysticcraft.registry.ModAttachmentTypes;
import net.kapitencraft.mysticcraft.rpg.classes.RPGCharacter;
import net.kapitencraft.mysticcraft.rpg.skill.PlayerSkills;
import net.kapitencraft.mysticcraft.rpg.skill.Skill;
import net.kapitencraft.mysticcraft.rpg.skill.SkillsInventoryPage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CharacterInventoryPageRenderer implements InventoryPageRenderer {
    private static final Component TITLE = Component.translatable("inventory.page.character");

    private static final ResourceLocation GOAL = ResourceLocation.withDefaultNamespace("advancements/goal_frame_unobtained");
    private static final ResourceLocation CHALLENGE = ResourceLocation.withDefaultNamespace("advancements/challenge_frame_unobtained");
    private static final ResourceLocation TASK = ResourceLocation.withDefaultNamespace("advancements/task_frame_unobtained");
    private static final ResourceLocation BACKGROUND = MysticcraftMod.res("textures/gui/blank_inventory_page_background.png");

    private final Player player;
    private final Component head;

    public CharacterInventoryPageRenderer(SkillsInventoryPage page) {
        this.player = page.getPlayer();
        this.head = ExtraComponents.playerHead(player.getUUID());
    }

    //dims: 176 x 166
    @Override
    public void render(GuiGraphics graphics, Minecraft minecraft, int mouseX, int mouseY, float mouseXOld, float mouseYOld, int leftPos, int topPos) {
        graphics.drawString(minecraft.font, TITLE, leftPos + 5, topPos + 5, 0x00208F, false);
        Skill[] skills = Skill.values();
        final int elementsPerRow = 5;
        PoseStack pose = graphics.pose();
        PlayerSkills playerSkills = player.getData(ModAttachmentTypes.SKILLS);
        RPGCharacter character = player.getData(ModAttachmentTypes.CHARACTER);
        renderLeveledBackground(graphics, leftPos + 73, topPos + 9, character.getLevel());
        pose.pushPose();
        pose.translate(leftPos + 78, topPos + 15, 0);
        pose.scale(2, 2, 1);
        graphics.drawString(minecraft.font, this.head, 0, 0, -1, false);
        boolean renderCharacterTooltip = MathHelper.is2dBetween(mouseX, mouseY, leftPos + 78, topPos + 15, leftPos + 94, topPos + 31);
        pose.popPose();
        if (renderCharacterTooltip) {
            renderCharacterTooltip(graphics, mouseX, mouseY, minecraft, character);
        }
        for (int i = 0; i < skills.length; i++) {
            int x = leftPos + 88 - Math.min(elementsPerRow, skills.length - i / elementsPerRow * elementsPerRow) * 30 / 2;
            int y = topPos + 40 + i / elementsPerRow * 30;
            x += i % elementsPerRow * 30;
            Skill skill  = skills[i];
            PlayerSkills.Progression progression = playerSkills.get(skill);
            renderSkill(graphics, skill, x, y, progression.getLevel());
            if (!renderCharacterTooltip && MathHelper.is2dBetween(mouseX, mouseY, x, y, x + 26, y + 26)) {
                renderSkillTooltip(graphics, mouseX, mouseY, minecraft, skill, progression);
            }
        }
    }

    private void renderCharacterTooltip(GuiGraphics graphics, int mouseX, int mouseY, Minecraft minecraft, RPGCharacter character) {
        PoseStack pose = graphics.pose();
        pose.pushPose();
        pose.translate(mouseX + 12, mouseY - 12, 400);
        int width = 100;
        boolean showAsRoman = ClientModConfig.showSkillLevelsAsRoman();
        boolean advancedItemTooltips = minecraft.options.advancedItemTooltips;
        TooltipRenderUtil.renderTooltipBackground(graphics, 0, 0, width,  advancedItemTooltips ? 34 : 27, 0);
        graphics.drawString(minecraft.font, this.player.getDisplayName(), 0, 0, -1);
        graphics.drawString(minecraft.font, showAsRoman ? TextHelper.convertToLatin(character.getLevel()) : String.valueOf(character.getLevel()), 0, 10, -1);
        String nextLevel = showAsRoman ? TextHelper.convertToLatin(character.getLevel() + 1) : String.valueOf(character.getLevel() + 1);
        int textWidth = minecraft.font.width(nextLevel);
        graphics.drawString(minecraft.font, nextLevel, width - textWidth - 1, 10, -1);
        graphics.fill(0, 20, width, 24, 0xFF7f7f7f);
        graphics.fill(0, 20, (int) (width * character.getXp() / 100), 24, 0xFF00FF1B);
        if (advancedItemTooltips) {
            graphics.drawString(minecraft.font, character.getXp() + " / 100", 0, 26, -1);
        }
        pose.popPose();
    }

    private void renderSkillTooltip(GuiGraphics graphics, int mouseX, int mouseY, Minecraft minecraft, Skill skill, PlayerSkills.Progression progression) {
        PoseStack pose = graphics.pose();
        pose.pushPose();
        pose.translate(mouseX + 12, mouseY - 12, 400);
        int width = 100;
        boolean showAsRoman = ClientModConfig.showSkillLevelsAsRoman();
        boolean advancedItemTooltips = minecraft.options.advancedItemTooltips;
        TooltipRenderUtil.renderTooltipBackground(graphics, 0, 0, width,  advancedItemTooltips ? 34 : 27, 0);
        graphics.drawString(minecraft.font, Component.translatable("skill." + skill.getSerializedName()), 0, 0, -1);
        graphics.drawString(minecraft.font, showAsRoman ? TextHelper.convertToLatin(progression.getLevel()) : String.valueOf(progression.getLevel()), 0, 10, -1);
        String nextLevel = showAsRoman ? TextHelper.convertToLatin(progression.getLevel() + 1) : String.valueOf(progression.getLevel() + 1);
        int textWidth = minecraft.font.width(nextLevel);
        graphics.drawString(minecraft.font, nextLevel, width - textWidth - 1, 10, -1);
        graphics.fill(0, 20, width, 24, 0xFF7f7f7f);
        graphics.fill(0, 20, (int) (width * progression.getXp() / progression.getRequiredXp()), 24, 0xFF00FF1B);
        if (advancedItemTooltips) {
            graphics.drawString(minecraft.font, progression.getXp() + " / " + progression.getRequiredXp(), 0, 26, -1);
        }
        pose.popPose();
    }

    @Override
    public void init(int leftPos, int topPos) {
    }

    @Override
    public @NotNull ResourceLocation pageBackgroundLocation() {
        return BACKGROUND;
    }

    @Override
    public Component getTitle() {
        return TITLE;
    }

    private static void renderSkill(GuiGraphics graphics, Skill skill, int x, int y, int level) {
        renderLeveledBackground(graphics, x, y, level);
        graphics.renderFakeItem(new ItemStack(skill.getItem()), x + 5, y + 5);
    }

    private static void renderLeveledBackground(GuiGraphics graphics, int x, int y, int level) {
        graphics.blitSprite(level < 25 ? TASK : level < 100 ? GOAL : CHALLENGE, x, y, 26, 26);
    }
}