# Overworld (Terra default pack)

Terra's official **Overworld** config pack (by Astrash, Sancires, Aureus, RogueShade), id `OVERWORLD`.
Bundled with and supported as-is by the `Terra-K7` plugin on MC 1.21.11 -- **no fork required**; the
plugin reads the standard pack format unchanged.

## What it does
Replaces the **overworld** with Terra's 3D-noise world generator (`generator: NOISE_3D`): custom biome
distribution, terrain shape, caves, ores, vegetation and small structures -- a complete drop-in
replacement for vanilla overworld generation.

## Where it does it
The **overworld** (default world). Assign on Paper via `bukkit.yml`:
```
worlds:
  world:
    generator: Terra-K7:OVERWORLD
```

## Highlights / specials
- 3D-noise terrain with continent, river, ocean-trench, temperature and precipitation samplers, plus a
  slant/slab pass that smooths slopes with slabs.
- Full custom biome set (pipeline biome provider, default preset; single / single-debug presets available).
- Generation stages: global-preprocessors, preprocessors, structures, landforms, slabs, ores, deposits,
  river-decoration, trees (Gaussian-blended), processors, underwater-flora, flora, postprocessors.
- Small structures: mob rooms, desert wells, fossils, and archaeology blocks (suspicious sand/gravel).

## Plugin support note (1.21.11)
The pack uses Terra's `id{NBT}` block convention (e.g. `minecraft:suspicious_sand{LootTable:'...'}`).
`Terra-K7` reads this on 1.21.11: the block places normally and the block-entity NBT is parsed and
ignored on the Bukkit platform (Bukkit world-gen cannot apply it -- the long-standing Terra-on-Bukkit
behaviour). No pack edits needed.

