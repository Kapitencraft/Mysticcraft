package net.kapitencraft.mysticcraft.entity.skeleton_master;


import net.kapitencraft.kap_lib.helpers.MiscHelper;

public class KeeperHolder {

    private final ArrowKeeper[] arrowKeepers = new ArrowKeeper[4];
    private final SkeletonMaster master;

    public KeeperHolder(SkeletonMaster master) {
        this.master = master;
        MiscHelper.repeat(4, integer -> arrowKeepers[integer] = new ArrowKeeper(integer * 90));
        chargeAll();
    }

    void tick(boolean shouldShoot) {
        ArrowKeeper arrowKeeper = arrowKeepers[0];
        arrowKeeper.update(master);
        if (arrowKeeper.empty()) {
            arrowKeeper.nextTargetRotation();
        }
    }

    void chargeAll() {
        for (ArrowKeeper keeper : this.arrowKeepers) {
            keeper.charge();
        }
    }
}
