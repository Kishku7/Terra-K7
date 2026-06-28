# ReimagEND (Terra default pack)

Terra's official **ReimagEND** config pack (by Aureus, RogueShade), id `REIMAGEND`. Bundled with and
supported as-is by the `Terra-K7` plugin on MC 1.21.11 -- **no fork required**; the plugin reads the
standard pack format unchanged.

## What it does
Reimagines the **End**: replaces vanilla End generation with Terra 3D-noise (`generator: NOISE_3D`),
adding new End biomes, terrain and structures beyond the central island.

## Where it does it
The **End** dimension (bound to `vanilla: minecraft:the_end` / `vanilla-generation: minecraft:end`).
Assign on Paper via `bukkit.yml`:
```
worlds:
  world_the_end:
    generator: Terra-K7:REIMAGEND
```

## Highlights / specials
- New End biome distribution (default preset; END_HIGHLANDS single preset, plus aether / vanilla-ish /
  BetterEnd alternative presets selectable in pack.yml).
- 3D-noise terrain with simplex + spots samplers and a white-noise palette blend.
- Stages: global-preprocessors, preprocessors, buildings, landforms, slabs, ores, deposits, trees, flora,
  postprocessors.
- Structures: abandoned houses, mob rooms, purpur end-towers, and a crashed End ship -- with loot chests
  (end-city treasure, village and dungeon loot).

## Plugin support note (1.21.11)
Uses Terra's `id{NBT}` convention for blocks/entities (`minecraft:chest{LootTable:'...'}`,
`minecraft:end_gateway{ExactTeleport:...}`, `minecraft:end_crystal{ShowBottom:0}`). `Terra-K7` reads
these on 1.21.11: the block/entity is placed and the unsupported NBT is parsed and ignored on Bukkit gen.
No pack edits needed.

