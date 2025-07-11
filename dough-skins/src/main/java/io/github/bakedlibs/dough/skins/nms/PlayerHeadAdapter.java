package io.github.bakedlibs.dough.skins.nms;

import com.mojang.authlib.GameProfile;
import io.github.bakedlibs.dough.common.DoughLogger;
import io.github.bakedlibs.dough.versions.MinecraftVersion;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import org.bukkit.block.Block;

public interface PlayerHeadAdapter {

    @ParametersAreNonnullByDefault
    void setGameProfile(Block block, GameProfile profile, boolean sendBlockUpdate) throws IllegalAccessException, InvocationTargetException, InstantiationException;

    static @Nullable PlayerHeadAdapter get() {
        try {
            MinecraftVersion version = MinecraftVersion.get();

            if (version.isAtLeast(1, 21, 6)) {
                // 1.21.6 mappings
                return new PlayerHeadAdapter21v6();
            } else if (version.isAtLeast(1, 20, 5)) {
                // 1.20.5 mappings
                return new PlayerHeadAdapter20v5();
            } else if (version.isAtLeast(1, 18)) {
                // 1.18 mappings
                return new PlayerHeadAdapter18();
            } else if (version.isAtLeast(1, 17)) {
                // 1.17 mappings
                return new PlayerHeadAdapter17();
            } else {
                // Old mappings
                return new PlayerHeadAdapterBefore17();
            }
        } catch (Exception x) {
            DoughLogger logger = new DoughLogger("skins");
            logger.log(Level.SEVERE, "Failed to detect skull nbt methods", x);
            return null;
        }

    }
}
