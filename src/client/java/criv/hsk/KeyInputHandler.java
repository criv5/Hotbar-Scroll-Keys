package criv.hsk;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_HSK = "key.category.hsk";
    public static final String KEY_LEFT = "key.hsk.left";
    public static final String KEY_RIGHT = "key.hsk.right";

    public static KeyBinding leftKey;
    public static KeyBinding rightKey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (leftKey.wasPressed()) {
                assert client.player != null;
                int oldSlot = client.player.getInventory().selectedSlot;
                int newSlot = oldSlot-1;
                if(newSlot < 0)
                    newSlot = 8;
                client.player.getInventory().selectedSlot = newSlot;
                client.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(newSlot));
            }
            if (rightKey.wasPressed()) {
                assert client.player != null;
                int oldSlot = client.player.getInventory().selectedSlot;
                int newSlot = oldSlot+1;
                if(newSlot > 8)
                    newSlot = 0;
                client.player.getInventory().selectedSlot = newSlot;
                client.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(newSlot));
            }
        });
    }

    public static void register() {
        leftKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_LEFT,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_BRACKET,
                KEY_CATEGORY_HSK
        ));
        rightKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_RIGHT,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_BRACKET,
                KEY_CATEGORY_HSK
        ));
        registerKeyInputs();
    }
}
