package net.kapitencraft.mysticcraft.networking;

import net.kapitencraft.mysticcraft.networking.packets.RequestDataPacket;
import net.kapitencraft.mysticcraft.networking.packets.RequestPacket;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class RequestHandler {
    private final Map<Short, Consumer<?>> requests = new HashMap<>();
    private short nextEmpty = 0;

    private final Consumer<RequestPacket<?, ?>> packetDistributor;

    public RequestHandler(Consumer<RequestPacket<?, ?>> packetDistributor) {
        this.packetDistributor = packetDistributor;
    }

    public synchronized <T, K> void createRequest(IRequestable<T, K> requestable, K value, Consumer<T> consumer) {
        RequestPacket<T, K> packet = new RequestPacket<T, K>(nextEmpty, requestable, value);
        packetDistributor.accept(packet);
        requests.put(nextEmpty, consumer);
        nextEmpty = getNextEmpty(nextEmpty);
    }

    private short getNextEmpty(short in) {
        while (!requests.containsKey(in)) {
            in++;
        }
        return in;
    }

    public synchronized <T, K> void accordPackageReceive(RequestDataPacket<T, K> packet) {
        short key = packet.getId();
        Consumer<T> consumer = (Consumer<T>) requests.get(key);
        consumer.accept(packet.getData());
        requests.remove(key);
        nextEmpty = key;
    }
}