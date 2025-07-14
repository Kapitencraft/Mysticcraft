package net.kapitencraft.mysticcraft.rpg.perks;

import java.util.function.ToIntFunction;

public class PerkVisibilityEvaluator {

   public static void evaluateVisibilityNew(Perk perk, ToIntFunction<Perk> levelGetter, PerkVisibilityEvaluator.Output output) {
      if (perk.getParent() == null) {
         output.accept(perk, true);
      }
      if (levelGetter.applyAsInt(perk) >= perk.getChildrenVisibleMin()) {
         for (Perk child : perk.getChildren()) {
            output.accept(child, true);
            evaluateVisibilityNew(child, levelGetter, output);
         }
      }
   }

   @FunctionalInterface
   public interface Output {
      void accept(Perk pPerk, boolean pVisible);
   }
}
