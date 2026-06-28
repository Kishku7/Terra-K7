# Modrinth listing -- Terra-K7

## Summary (short, <= 256 chars)
A self-contained Minecraft 1.21.11 build of the Terra world generator for Paper/Spigot/Folia. Runs your classic Terra packs unchanged, bundles the official default packs, and phones home to nobody.

## Title
Terra-K7

## Categories / tags
worldgen, library (utility), management -- loaders: paper, spigot, folia. MC: 1.21.11. License: GPL-3.0.

## Body (description page)

# Terra-K7

**Terra-K7** is an unofficial, **self-contained** build of the [Terra](https://modrinth.com/plugin/terra)
voxel world-generation engine, targeting **Minecraft 1.21.11** on **Paper / Spigot / Folia**. One jar:
the full engine, all 38 Terra addons, and the official default packs are bundled inside it.

## Why Terra-K7

### Runs your classic Terra packs -- unchanged
This is the headline. Minecraft 1.21.11 tightened its block parser, which breaks the
`block{NBT}` convention that virtually every existing Terra pack uses (loot chests, suspicious
sand/gravel, end gateways, end crystals, ...). Terra-K7 **reads the standard Terra pack format as-is** --
drop in any classic Terra config pack and it just loads. No re-authoring, no conversions, no edits.

### It phones home to nobody
No bStats, no metrics, no telemetry, no update checks, **no runtime downloads of any kind**. Every library
is shaded into the jar and the default packs are extracted locally on first start. Drop it on a bare
server with no other plugins and it runs -- fully offline, nothing leaves your box.

### A real 1.21.11 port, not a half one
Biome appearance (fog, water, sky colours, ambient particles, ambient sounds, music) is properly migrated
to Minecraft 1.21.11's new **EnvironmentAttributes** system -- so Terra biomes look the way they should,
not washed-out defaults.

### Won't let one bad pack take your server down
If a pack genuinely can't be read, Terra-K7 **skips it with a single clear line** in the log (and a summary
of what was skipped) instead of dumping a wall of stack traces -- and it **never rejects a pack it can
read**. Your server keeps starting.

### Paper, Spigot, and Folia
`folia-supported: true`. Works across the Bukkit family on 1.21.11.

## Included default packs
Bundled and validated to actually generate on 1.21.11:
- **Overworld** -- full 3D-noise overworld generator.
- **Tartarus** -- a richer Nether with many new biomes.
- **ReimagEND** -- the End reimagined with new biomes and structures.

## Install
1. Drop `terra-k7-1.0-plugin.jar` in `plugins/`.
2. Start once; it creates `plugins/Terra-K7/` and extracts the default packs to
   `plugins/Terra-K7/packs/`.
3. Point a world at a generator in `bukkit.yml`, then restart:
```yaml
worlds:
  world:
    generator: Terra-K7:OVERWORLD
  world_nether:
    generator: Terra-K7:TARTARUS
  world_the_end:
    generator: Terra-K7:REIMAGEND
```
Add your own or community packs by dropping a folder or `.zip` into `plugins/Terra-K7/packs/`.

## Credits & license
Terra is created by [PolyhedralDev](https://github.com/PolyhedralDev/Terra) and contributors. Terra-K7 is
an **unofficial** 1.21.11 build, distributed under **GPL-3.0** like upstream. All credit for the Terra
engine and the official packs goes to their original authors.
