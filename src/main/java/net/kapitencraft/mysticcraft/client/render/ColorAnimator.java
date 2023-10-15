package net.kapitencraft.mysticcraft.client.render;

import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.helpers.NetworkingHelper;
import net.minecraft.core.particles.DustParticleOptionsBase;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextColor;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class ColorAnimator {
    private final List<Vector3f> colors = new ArrayList<>();
    private final int nextColorTime;

    public ColorAnimator(int nextColorTime) {
        this.nextColorTime = nextColorTime;
    }

    public static ColorAnimator createRainbow(int cooldown) {
        return create(cooldown).addFrame(new Vector3f(1, 0, 0)).addFrame(new Vector3f(0, 1, 0)).addFrame(new Vector3f(0, 0, 1));
    }

    public static ColorAnimator simple(Vector3f color) {
        return create(0).addFrame(color);
    }


    public static ColorAnimator create(int nextColorTime) {
        return new ColorAnimator(nextColorTime);
    }

    public ColorAnimator addFrame(Vector3f color) {
        colors.add(color);
        return this;
    }

    @SuppressWarnings("ALL")
    public Vector3f getColor(int curTime) {
        Vector3f oldColor = null;
        curTime %= getLength();
        if (colors.size() == 0) throw new IllegalArgumentException("can not make color animation with 0 colors");
        if (colors.size() == 1) return colors.get(0);
        for (Vector3f entry : colors) {
            if (nextColorTime > curTime) {
                if (oldColor == null) {
                    return getColor(curTime, colors.get(colors.size() - 1), entry);
                } else {
                    return getColor(curTime, oldColor, entry);
                }
            }
            oldColor = entry;
            curTime -= nextColorTime;
        }
        return getColor(curTime, oldColor, colors.get(0));
    }

    public TextColor makeTextColor(int curTime)  {
        return TextColor.fromRgb(MathHelper.RGBtoInt(getColor(curTime)));
    }

    private Vector3f getColor(int curTime, Vector3f first, Vector3f second) {
        float percentage = MathHelper.makePercentage(curTime, nextColorTime);
        Vector3f a = new Vector3f(first);
        Vector3f b = new Vector3f(second);
        Vector3f change = new Vector3f(a.add(b.mul(-1)));
        a.add(change.mul(-percentage));
        return a;
    }

    public int getLength() {
        return nextColorTime * colors.size();
    }

    public void writeToBytes(FriendlyByteBuf buf) {
        buf.writeInt(getLength());
        buf.writeInt(nextColorTime);
        colors.forEach((vector3f) -> NetworkingHelper.writeVector3f(buf, vector3f));
    }

    public static ColorAnimator fromBuf(FriendlyByteBuf buf) {
        int length = buf.readInt();
        int nextColorTime = buf.readInt();
        ColorAnimator animator = create(nextColorTime);
        MiscHelper.repeatXTimes(length, integer -> {
            Vector3f color = DustParticleOptionsBase.readVector3f(buf);
            animator.addFrame(color);
        });
        return animator;
    }
}