package com.dfsek.terra.mod.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.attribute.EnvironmentAttributeMap;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.DimensionType.MonsterSettings;
import org.jetbrains.annotations.NotNull;

import com.dfsek.terra.mod.ModPlatform;
import com.dfsek.terra.mod.config.MonsterSettingsConfig;
import com.dfsek.terra.mod.config.VanillaWorldProperties;
import com.dfsek.terra.mod.implmentation.TerraIntProvider;


public class DimensionUtil {
    public static DimensionType createDimension(VanillaWorldProperties vanillaWorldProperties, DimensionType defaultDimension,
                                                ModPlatform platform) {

        MonsterSettingsConfig monsterSettingsConfig;
        if(vanillaWorldProperties.getMonsterSettings() != null) {
            monsterSettingsConfig = vanillaWorldProperties.getMonsterSettings();
        } else {
            monsterSettingsConfig = new MonsterSettingsConfig();
        }

        MonsterSettings monsterSettings = getMonsterSettings(defaultDimension, monsterSettingsConfig);

        // 1.21.11 restructured DimensionType: fixed-time value, ultrawarm, natural, bed-works, the dimension
        // "effects" id and cloud height are no longer direct record fields. Dimension-level behaviour and
        // visuals now live in the EnvironmentAttributeMap (plus skybox / cardinalLightType / timelines).
        // We inherit the template dimension's attributes and override only the pack-specified knobs that
        // map cleanly to a single attribute key.
        EnvironmentAttributeMap.Builder attributes = EnvironmentAttributeMap.builder();
        attributes.putAll(defaultDimension.attributes());

        if(vanillaWorldProperties.getRespawnAnchorWorks() != null) {
            attributes.set(EnvironmentAttributes.RESPAWN_ANCHOR_WORKS, vanillaWorldProperties.getRespawnAnchorWorks());
        }
        if(vanillaWorldProperties.getCloudHeight() != null) {
            attributes.set(EnvironmentAttributes.CLOUD_HEIGHT, vanillaWorldProperties.getCloudHeight().floatValue());
        }
        if(monsterSettingsConfig.getHasRaids() != null) {
            attributes.set(EnvironmentAttributes.CAN_START_RAID, monsterSettingsConfig.getHasRaids());
        }

        // TODO (1.21.11 dimension-attribute migration, deeper plumbing required - flagged, not dropped):
        //  - fixed-time VALUE: now driven by a Timeline (RegistryEntryList<Timeline>), not a long; only the
        //    hasFixedTime flag remains a direct field. Inheriting the template's timeline for now.
        //  - ultraWarm: splits into WATER_EVAPORATES_GAMEPLAY / FAST_LAVA_GAMEPLAY / INCREASED_FIRE_BURNOUT_GAMEPLAY.
        //  - natural: split across several gameplay attributes; no single-key equivalent.
        //  - bedWorks: now BED_RULE_GAMEPLAY (a BedRule, not a boolean) - needs an enum mapping.
        //  - piglinSafe: inverse of PIGLINS_ZOMBIFY_GAMEPLAY - needs careful inversion.
        //  - effects (DimensionEffects id): now expressed via skybox + cardinalLightType + timelines.
        //  These inherit from the chosen template dimension (overworld/nether/end), preserving vanilla behaviour.

        boolean hasFixedTime = vanillaWorldProperties.getFixedTime() == null
                               ? defaultDimension.hasFixedTime()
                               : true;

        return new DimensionType(
            hasFixedTime,
            vanillaWorldProperties.getHasSkyLight() == null ? defaultDimension.hasSkyLight() : vanillaWorldProperties.getHasSkyLight(),
            vanillaWorldProperties.getHasCeiling() == null ? defaultDimension.hasCeiling() : vanillaWorldProperties.getHasCeiling(),
            vanillaWorldProperties.getCoordinateScale() == null
            ? defaultDimension.coordinateScale()
            : vanillaWorldProperties.getCoordinateScale(),
            vanillaWorldProperties.getHeight() == null ? defaultDimension.minY() : vanillaWorldProperties.getHeight().getMin(),
            vanillaWorldProperties.getHeight() == null ? defaultDimension.height() : vanillaWorldProperties.getHeight().getRange(),
            vanillaWorldProperties.getLogicalHeight() == null
            ? defaultDimension.logicalHeight()
            : vanillaWorldProperties.getLogicalHeight(),
            vanillaWorldProperties.getInfiniburn() == null
            ? defaultDimension.infiniburn()
            : TagKey.create(Registries.BLOCK, vanillaWorldProperties.getInfiniburn()),
            vanillaWorldProperties.getAmbientLight() == null ? defaultDimension.ambientLight() : vanillaWorldProperties.getAmbientLight(),
            monsterSettings,
            defaultDimension.skybox(),
            defaultDimension.cardinalLightType(),
            attributes.build(),
            defaultDimension.timelines()
        );
    }

    @NotNull
    private static MonsterSettings getMonsterSettings(DimensionType defaultDimension, MonsterSettingsConfig monsterSettingsConfig) {
        MonsterSettings defaultMonsterSettings = defaultDimension.monsterSettings();

        // 1.21.11: MonsterSettings is now a 2-field record (spawn light test + block light limit). piglinSafe
        // and hasRaids moved to dimension EnvironmentAttributes (handled in createDimension).
        return new MonsterSettings(
            monsterSettingsConfig.getMonsterSpawnLight() == null ? defaultMonsterSettings.monsterSpawnLightTest() : new TerraIntProvider(
                monsterSettingsConfig.getMonsterSpawnLight()),
            monsterSettingsConfig.getMonsterSpawnBlockLightLimit() == null
            ? defaultMonsterSettings.monsterSpawnBlockLightLimit()
            : monsterSettingsConfig.getMonsterSpawnBlockLightLimit()
        );
    }
}
