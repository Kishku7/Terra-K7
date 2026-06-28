/*
 * This file is part of Terra.
 *
 * Terra is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Terra is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Terra.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.dfsek.terra.registry.master;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serial;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

import com.dfsek.terra.api.Platform;
import com.dfsek.terra.api.config.ConfigPack;
import com.dfsek.terra.api.util.reflection.TypeKey;
import com.dfsek.terra.config.pack.ConfigPackImpl;
import com.dfsek.terra.registry.OpenRegistryImpl;


/**
 * Class to hold config packs
 */
public class ConfigRegistry extends OpenRegistryImpl<ConfigPack> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigRegistry.class);

    public ConfigRegistry() {
        super(TypeKey.of(ConfigPack.class));
    }

    public synchronized void loadAll(Platform platform) throws IOException, PackLoadFailuresException {
        Path packsDirectory = platform.getDataFolder().toPath().resolve("packs");
        Files.createDirectories(packsDirectory);
        List<String> ignored = new CopyOnWriteArrayList<>();
        try(Stream<Path> packs = Files.list(packsDirectory)) {
            packs.parallel().forEach(path -> {
                try {
                    ConfigPack pack = new ConfigPackImpl(path, platform);
                    registerChecked(pack.getRegistryKey(), pack);
                } catch(IOException | RuntimeException e) {
                    // A pack that fails to load is treated as incompatible with this Terra /
                    // Minecraft version. Ignore it so a single bad pack cannot break startup, but
                    // record that we did so: a concise reason at WARN, the full stack trace at DEBUG.
                    String name = path.getFileName().toString();
                    ignored.add(name);
                    LOGGER.warn("Ignoring incompatible config pack \"{}\": {}", name, rootCauseMessage(e));
                    LOGGER.debug("Full error for ignored config pack \"{}\":", name, e);
                }
            });
        }
        if(!ignored.isEmpty()) {
            LOGGER.warn("Ignored {} incompatible config pack(s): {}. They were skipped; startup continues normally.",
                ignored.size(), String.join(", ", ignored));
        }
    }

    /**
     * Unwrap to the deepest cause and render a one-line "SimpleName: message" string, so an ignored
     * pack is logged with a useful reason (e.g. the offending block) instead of a wall of stack trace.
     */
    private static String rootCauseMessage(Throwable t) {
        Throwable root = t;
        while(root.getCause() != null && root.getCause() != root) {
            root = root.getCause();
        }
        String message = root.getMessage();
        return root.getClass().getSimpleName() + (message != null ? ": " + message : "");
    }

    public static class PackLoadFailuresException extends Exception {
        @Serial
        private static final long serialVersionUID = 538998844645186306L;

        @SuppressWarnings("serial") // runtime value is an immutable List copy; not serialized across JVMs
        private final List<Throwable> exceptions;

        public PackLoadFailuresException(List<? extends Throwable> exceptions) {
            this.exceptions = List.copyOf(exceptions);
        }

        public List<Throwable> getExceptions() {
            return exceptions;
        }
    }
}
