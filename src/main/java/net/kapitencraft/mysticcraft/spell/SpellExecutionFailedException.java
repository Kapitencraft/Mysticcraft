package net.kapitencraft.mysticcraft.spell;

public class SpellExecutionFailedException extends Exception {
    private final String msg;

    public SpellExecutionFailedException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
