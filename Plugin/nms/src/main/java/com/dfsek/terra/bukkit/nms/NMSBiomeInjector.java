package com.dfsek.terra.bukkit.nms;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.attribute.AmbientAdditionsSettings;
import net.minecraft.world.attribute.AmbientMoodSettings;
import net.minecraft.world.attribute.AmbientSounds;
import net.minecraft.world.attribute.BackgroundMusic;
import net.minecraft.world.attribute.EnvironmentAttributeMap;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import com.dfsek.terra.api.config.ConfigPack;
import com.dfsek.terra.bukkit.nms.config.VanillaBiomeProperties;


public class NMSBiomeInjector {

    public static <T> Optional<Holder<T>> getEntry(Registry<T> registry, Identifier identifier) {
        return registry.getOptional(identifier)
            .flatMap(registry::getResourceKey)
            .flatMap(registry::get);
    }

    public static Biome createBiome(Biome vanilla, VanillaBiomeProperties vanillaBiomeProperties)
    throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Biome.BiomeBuilder builder = new Biome.BiomeBuilder();

        // BiomeSpecialEffects is a slim record in 1.21.11: only water color, grass/foliage overrides,
        // and the grass color modifier still live here.
        BiomeSpecialEffects.Builder effects = new BiomeSpecialEffects.Builder();

        effects.waterColor(Objects.requireNonNullElse(vanillaBiomeProperties.getWaterColor(), vanilla.getWaterColor()))
            .grassColorModifier(Objects.requireNonNullElse(vanillaBiomeProperties.getGrassColorModifier(),
                vanilla.getSpecialEffects().grassColorModifier()));

        if(vanillaBiomeProperties.getGrassColor() == null) {
            vanilla.getSpecialEffects().grassColorOverride().ifPresent(effects::grassColorOverride);
        } else {
            effects.grassColorOverride(vanillaBiomeProperties.getGrassColor());
        }

        if(vanillaBiomeProperties.getFoliageColor() == null) {
            vanilla.getSpecialEffects().foliageColorOverride().ifPresent(effects::foliageColorOverride);
        } else {
            effects.foliageColorOverride(vanillaBiomeProperties.getFoliageColor());
        }

        if(vanillaBiomeProperties.getDryFoliageColor() == null) {
            vanilla.getSpecialEffects().dryFoliageColorOverride().ifPresent(effects::dryFoliageColorOverride);
        } else {
            effects.dryFoliageColorOverride(vanillaBiomeProperties.getDryFoliageColor());
        }

        // 1.21.11: fog/water-fog/sky colors, ambient particles, ambient sounds, music, and music
        // volume moved out of BiomeSpecialEffects into the registry-based EnvironmentAttributeMap.
        // Inherit the vanilla template biome's attributes, then override only what the pack specifies
        // (this preserves the original per-field "Terra value else vanilla value" semantics).
        EnvironmentAttributeMap.Builder attributes = EnvironmentAttributeMap.builder();
        attributes.putAll(vanilla.getAttributes());

        if(vanillaBiomeProperties.getFogColor() != null) {
            attributes.set(EnvironmentAttributes.FOG_COLOR, vanillaBiomeProperties.getFogColor());
        }
        if(vanillaBiomeProperties.getWaterFogColor() != null) {
            attributes.set(EnvironmentAttributes.WATER_FOG_COLOR, vanillaBiomeProperties.getWaterFogColor());
        }
        if(vanillaBiomeProperties.getSkyColor() != null) {
            attributes.set(EnvironmentAttributes.SKY_COLOR, vanillaBiomeProperties.getSkyColor());
        }
        if(vanillaBiomeProperties.getMusicVolume() != null) {
            attributes.set(EnvironmentAttributes.MUSIC_VOLUME, vanillaBiomeProperties.getMusicVolume());
        }
        if(vanillaBiomeProperties.getParticleConfig() != null) {
            attributes.set(EnvironmentAttributes.AMBIENT_PARTICLES, List.of(vanillaBiomeProperties.getParticleConfig()));
        }

        // Ambient sounds: loop + mood + additions are consolidated into one AmbientSounds record.
        // Only rebuild it if the pack overrides at least one part; unspecified parts fall back to the
        // vanilla biome's resolved values.
        if(vanillaBiomeProperties.getLoopSound() != null
            || vanillaBiomeProperties.getMoodSound() != null
            || vanillaBiomeProperties.getAdditionsSound() != null) {
            AmbientSounds vanillaSounds = vanilla.getAttributes().applyModifier(EnvironmentAttributes.AMBIENT_SOUNDS, AmbientSounds.EMPTY);
            Optional<Holder<SoundEvent>> loop = vanillaBiomeProperties.getLoopSound() != null
                ? Optional.of(RegistryFetcher.soundEventRegistry().wrapAsHolder(vanillaBiomeProperties.getLoopSound()))
                : vanillaSounds.loop();
            Optional<AmbientMoodSettings> mood = vanillaBiomeProperties.getMoodSound() != null
                ? Optional.of(vanillaBiomeProperties.getMoodSound())
                : vanillaSounds.mood();
            List<AmbientAdditionsSettings> additions = vanillaBiomeProperties.getAdditionsSound() != null
                ? List.of(vanillaBiomeProperties.getAdditionsSound())
                : vanillaSounds.additions();
            attributes.set(EnvironmentAttributes.AMBIENT_SOUNDS, new AmbientSounds(loop, mood, additions));
        }

        if(vanillaBiomeProperties.getMusic() != null) {
            attributes.set(EnvironmentAttributes.BACKGROUND_MUSIC, new BackgroundMusic(vanillaBiomeProperties.getMusic()));
        }

        builder.hasPrecipitation(Objects.requireNonNullElse(vanillaBiomeProperties.getPrecipitation(), vanilla.hasPrecipitation()));

        builder.temperature(Objects.requireNonNullElse(vanillaBiomeProperties.getTemperature(), vanilla.getBaseTemperature()));

        builder.downfall(Objects.requireNonNullElse(vanillaBiomeProperties.getDownfall(), vanilla.climateSettings.downfall()));

        builder.temperatureAdjustment(
            Objects.requireNonNullElse(vanillaBiomeProperties.getTemperatureModifier(), vanilla.climateSettings.temperatureModifier()));

        builder.mobSpawnSettings(Objects.requireNonNullElse(vanillaBiomeProperties.getSpawnSettings(), vanilla.getMobSettings()));

        return builder
            .specialEffects(effects.build())
            .putAttributes(attributes)
            .generationSettings(new BiomeGenerationSettings.PlainBuilder().build())
            .build();
    }

    public static String createBiomeID(ConfigPack pack, com.dfsek.terra.api.registry.key.RegistryKey biomeID) {
        return pack.getID()
                   .toLowerCase() + "/" + biomeID.getNamespace().toLowerCase(Locale.ROOT) + "/" + biomeID.getID().toLowerCase(Locale.ROOT);
    }
}
