# Modrinth listing -- Terra-K7

## Summary (short -- keep concise; current live subtitle was hand-edited by Dave, do not overwrite)
Self-contained Terra world generator. Runs classic Terra packs unchanged, no telemetry.

## Title
Terra-K7

## Categories / tags
worldgen, utility. License: GPL-3.0. Loaders/MC come from each version (current: paper/spigot/folia, 1.21.11).

## Body (description page)

# Terra-K7

A self-contained, maintained build of the [Terra](https://modrinth.com/plugin/terra) voxel
world-generation engine. Terra-K7 packages the full Terra engine, its addons, and the official default
packs into one drop-in artifact, focused on running **classic Terra packs unchanged**, **zero telemetry**,
and **resilient loading**. It is built to grow across loaders and Minecraft versions over time -- the
current release is the starting point, not the scope.

## Highlights

### Runs your classic Terra packs -- unchanged
Existing Terra config packs load as-is, including the long-standing `id{NBT}` block convention (loot
chests, suspicious sand/gravel, end gateways, end crystals) that stricter modern Minecraft block parsers
otherwise reject. Drop a pack in -- no re-authoring, no conversions.

### Phones home to nobody
No bStats, metrics, telemetry, update checks, or runtime downloads. Every dependency is shaded in and the
default packs are bundled and extracted locally, so it runs fully offline on a bare server.

### Faithful engine
Biome appearance (fog/water/sky colours, ambient particles, sounds, music) is kept correct against the
targeted Minecraft version rather than left stubbed.

### One bad pack won't take your server down
An unreadable pack is skipped with a single clear log line (plus a summary); a pack it can read is never
rejected. The server keeps starting.

## Included default packs
- **Overworld** -- full 3D-noise overworld generator.
- **Tartarus** -- a richer Nether with many new biomes.
- **ReimagEND** -- the End reimagined with new biomes and structures.

## Current release
Available now for **PaperMC / Spigot / Folia** on **Minecraft 1.21.11**. Drop the jar in `plugins/`, start
once, then point a world at a generator in `bukkit.yml` (e.g. `generator: Terra-K7:OVERWORLD`). Add your
own or community packs by dropping a folder or `.zip` into `plugins/Terra-K7/packs/`.

## Roadmap
Terra-K7 is expanding: planned support for the **Fabric and NeoForge mod loaders** and **Minecraft 26**,
alongside the current Paper-family plugin.

## Credits & license
Terra is created by [PolyhedralDev](https://github.com/PolyhedralDev/Terra) and contributors. Terra-K7 is
an **unofficial** build distributed under **GPL-3.0**. All credit for the Terra engine and the official
packs goes to their original authors.
