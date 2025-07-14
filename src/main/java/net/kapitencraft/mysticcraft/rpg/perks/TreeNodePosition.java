package net.kapitencraft.mysticcraft.rpg.perks;

import com.google.common.collect.Lists;

import javax.annotation.Nullable;
import java.util.List;

public class TreeNodePosition {
   private final Perk perk;
   @Nullable
   private final TreeNodePosition parent;
   @Nullable
   private final TreeNodePosition previousSibling;
   private final int childIndex;
   private final List<TreeNodePosition> children = Lists.newArrayList();
   private TreeNodePosition ancestor;
   @Nullable
   private TreeNodePosition thread;
   private int y;
   private float x;
   private float mod;
   private float change;
   private float shift;

   public TreeNodePosition(Perk pPerk, @Nullable TreeNodePosition pParent, @Nullable TreeNodePosition pPreviousSibling, int pChildIndex, int pY) {
       this.perk = pPerk;
       this.parent = pParent;
       this.previousSibling = pPreviousSibling;
       this.childIndex = pChildIndex;
       this.ancestor = this;
       this.y = pY;
       this.x = -1.0F;
       TreeNodePosition treenodeposition = null;

       for(Perk advancement : pPerk.getChildren()) {
          treenodeposition = this.addChild(advancement, treenodeposition);
       }

   }

   private TreeNodePosition addChild(Perk perk, @Nullable TreeNodePosition pPrevious) {
       pPrevious = new TreeNodePosition(perk, this, pPrevious, this.children.size() + 1, this.y + 1);
       this.children.add(pPrevious);

       return pPrevious;
   }

   private void firstWalk() {
      if (this.children.isEmpty()) {
         if (this.previousSibling != null) {
            this.x = this.previousSibling.x + 1.0F;
         } else {
            this.x = 0.0F;
         }

      } else {
         TreeNodePosition treenodeposition = null;

         for(TreeNodePosition position : this.children) {
            position.firstWalk();
            treenodeposition = position.apportion(treenodeposition == null ? position : treenodeposition);
         }

         this.executeShifts();
         float f = ((this.children.get(0)).x + (this.children.get(this.children.size() - 1)).x) / 2.0F;
         if (this.previousSibling != null) {
            this.x = this.previousSibling.x + 1.0F;
            this.mod = this.x - f;
         } else {
            this.x = f;
         }

      }
   }

   private float secondWalk(float pOffsetX, int pColumnY, float pSubtreeTopX) {
      this.x += pOffsetX;
      this.y = pColumnY;
      if (this.x < pSubtreeTopX) {
         pSubtreeTopX = this.x;
      }

      for(TreeNodePosition treenodeposition : this.children) {
         pSubtreeTopX = treenodeposition.secondWalk(pOffsetX + this.mod, pColumnY + 1, pSubtreeTopX);
      }

      return pSubtreeTopX;
   }

   private void thirdWalk(float pX) {
      this.x += pX;

      for(TreeNodePosition treenodeposition : this.children) {
         treenodeposition.thirdWalk(pX);
      }

   }

   private void executeShifts() {
      float f = 0.0F;
      float f1 = 0.0F;

      for(int i = this.children.size() - 1; i >= 0; --i) {
         TreeNodePosition treenodeposition = this.children.get(i);
         treenodeposition.x += f;
         treenodeposition.mod += f;
         f1 += treenodeposition.change;
         f += treenodeposition.shift + f1;
      }

   }

   @Nullable
   private TreeNodePosition previousOrThread() {
      if (this.thread != null) {
         return this.thread;
      } else {
         return !this.children.isEmpty() ? this.children.get(0) : null;
      }
   }

   @Nullable
   private TreeNodePosition nextOrThread() {
      if (this.thread != null) {
         return this.thread;
      } else {
         return !this.children.isEmpty() ? this.children.get(this.children.size() - 1) : null;
      }
   }

   private TreeNodePosition apportion(TreeNodePosition pNode) {
       if (this.previousSibling != null) {
           TreeNodePosition treenodeposition = this;
           TreeNodePosition treenodeposition1 = this;
           TreeNodePosition treenodeposition2 = this.previousSibling;
           TreeNodePosition treenodeposition3 = this.parent.children.get(0);
           float f = this.mod;
           float f1 = this.mod;
           float f2 = treenodeposition2.mod;

           float f3;
           for (f3 = treenodeposition3.mod; treenodeposition2.nextOrThread() != null && treenodeposition.previousOrThread() != null; f1 += treenodeposition1.mod) {
               treenodeposition2 = treenodeposition2.nextOrThread();
               treenodeposition = treenodeposition.previousOrThread();
               treenodeposition3 = treenodeposition3.previousOrThread();
               treenodeposition1 = treenodeposition1.nextOrThread();
               treenodeposition1.ancestor = this;
               float f4 = treenodeposition2.x + f2 - (treenodeposition.x + f) + 1.0F;
               if (f4 > 0.0F) {
                   treenodeposition2.getAncestor(this, pNode).moveSubtree(this, f4);
                   f += f4;
                   f1 += f4;
               }

               f2 += treenodeposition2.mod;
               f += treenodeposition.mod;
               f3 += treenodeposition3.mod;
           }

           if (treenodeposition2.nextOrThread() != null && treenodeposition1.nextOrThread() == null) {
               treenodeposition1.thread = treenodeposition2.nextOrThread();
               treenodeposition1.mod += f2 - f1;
           } else {
               if (treenodeposition.previousOrThread() != null && treenodeposition3.previousOrThread() == null) {
                   treenodeposition3.thread = treenodeposition.previousOrThread();
                   treenodeposition3.mod += f - f3;
               }

               pNode = this;
           }

       }
       return pNode;
   }

   private void moveSubtree(TreeNodePosition pNode, float pShift) {
      float f = (float)(pNode.childIndex - this.childIndex);
      if (f != 0.0F) {
         pNode.change -= pShift / f;
         this.change += pShift / f;
      }

      pNode.shift += pShift;
      pNode.x += pShift;
      pNode.mod += pShift;
   }

   private TreeNodePosition getAncestor(TreeNodePosition pSelf, TreeNodePosition pOther) {
      return this.ancestor != null && pSelf.parent.children.contains(this.ancestor) ? this.ancestor : pOther;
   }

   private void finalizePosition() {
       this.perk.getDisplay().setLocation(this.x, this.y);

       if (!this.children.isEmpty()) {
         for(TreeNodePosition treenodeposition : this.children) {
            treenodeposition.finalizePosition();
         }
      }

   }

   public static void run(Perk pRoot) {
       TreeNodePosition treenodeposition = new TreeNodePosition(pRoot, null, null, 1, 0);
       treenodeposition.firstWalk();
       float f = treenodeposition.secondWalk(0.0F, 0, treenodeposition.x);
       if (f < 0.0F) {
          treenodeposition.thirdWalk(-f);
       }

       treenodeposition.finalizePosition();
   }
}