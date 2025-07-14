package net.kapitencraft.mysticcraft.tech.block;

import net.kapitencraft.mysticcraft.tech.ManaDistributionNetwork;

public class ManaRelayBlock extends DistributionNetworkBlock {
    public ManaRelayBlock() {
        super(ManaDistributionNetwork.Node.Type.RELAY);
    }
}
