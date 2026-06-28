/*
 * Copyright (c) 2020-2025 Polyhedral Development
 *
 * The Terra API is licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in the common/api directory.
 */

package com.dfsek.terra.api.structure;

import com.kishku7.TerraK7.seismic.type.Rotation;
import com.kishku7.TerraK7.seismic.type.vector.Vector3Int;

import java.util.random.RandomGenerator;

import com.dfsek.terra.api.world.WritableWorld;


public interface Structure {
    boolean generate(Vector3Int location, WritableWorld world, RandomGenerator random, Rotation rotation);
}
