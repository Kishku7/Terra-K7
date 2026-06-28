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

package com.dfsek.terra.bukkit.handles;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dfsek.terra.api.block.state.BlockState;
import com.dfsek.terra.api.entity.EntityType;
import com.dfsek.terra.api.handle.WorldHandle;
import com.dfsek.terra.bukkit.util.BukkitUtils;
import com.dfsek.terra.bukkit.world.block.data.BukkitBlockState;


public class BukkitWorldHandle implements WorldHandle {
    private static final Logger logger = LoggerFactory.getLogger(BukkitWorldHandle.class);
    private final BlockState air;

    public BukkitWorldHandle() {
        this.air = BukkitBlockState.newInstance(Material.AIR.createBlockData());
    }

    @Override
    public synchronized @NotNull BlockState createBlockState(@NotNull String data) {
        // Terra's pack convention permits a trailing "{...}" block-entity NBT tag on a block string
        // (e.g. minecraft:chest{LootTable:'chests/simple_dungeon'} or
        // minecraft:end_gateway{ExactTeleport:1,...}). The Bukkit platform has never applied that NBT
        // during world generation -- pre-1.21.11 Bukkit.createBlockData() simply tolerated/ignored it.
        // On MC 1.21.11 createBlockData() became strict and rejects the trailing data ("Spurious
        // trailing data"), which would otherwise fail the entire pack. Strip the NBT so the block still
        // places, restoring the long-standing Bukkit behaviour. Honouring the NBT (e.g. actually setting
        // a chest loot table) would require a block-entity pipeline that Bukkit world-gen does not expose.
        org.bukkit.block.data.BlockData bukkitData = Bukkit.createBlockData(
            stripTrailingNbt(data)); // somehow bukkit managed to make this not thread safe! :)
        return BukkitBlockState.newInstance(bukkitData);
    }

    @Override
    public @NotNull BlockState air() {
        return air;
    }

    @Override
    public @NotNull EntityType getEntity(@NotNull String id) {
        // Same convention as blocks: an entity id may carry a trailing "{...}" NBT tag
        // (e.g. minecraft:end_crystal{ShowBottom:0}). Bukkit gen cannot apply it; strip before parsing.
        return BukkitUtils.getEntityType(stripTrailingNbt(id));
    }

    /**
     * Remove a trailing "{...}" NBT tag from a block/entity id string. Block states ("[...]") are
     * preserved (NBT is always the last component in Terra's convention); only the unsupported NBT is
     * dropped. Restores the pre-1.21.11 lenient parse instead of letting the now-strict parser reject
     * the whole pack.
     */
    private static String stripTrailingNbt(String data) {
        int brace = data.indexOf('{');
        if(brace < 0) {
            return data;
        }
        String stripped = data.substring(0, brace);
        logger.debug("Stripped unsupported NBT from \"{}\" -> \"{}\" (Bukkit gen cannot apply block/entity NBT)",
            data, stripped);
        return stripped;
    }
}
