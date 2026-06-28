package com.dfsek.terra.addons.palette.shortcut.block;

import com.kishku7.TerraK7.seismic.type.Rotation;
import com.kishku7.TerraK7.seismic.type.vector.Vector3Int;

import java.util.random.RandomGenerator;

import com.dfsek.terra.api.block.state.BlockState;
import com.dfsek.terra.api.structure.Structure;
import com.dfsek.terra.api.world.WritableWorld;


public class SingletonStructure implements Structure {
    private final BlockState blockState;

    public SingletonStructure(BlockState blockState) {
        this.blockState = blockState;
    }

    @Override
    public boolean generate(Vector3Int location, WritableWorld world, RandomGenerator random, Rotation rotation) {
        world.setBlockState(location, blockState);
        return true;
    }
}
