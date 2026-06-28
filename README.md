# Terra-K7

A self-contained, maintained build of the [Terra](https://github.com/PolyhedralDev/Terra) voxel
world-generation engine. Terra-K7 packages the full Terra engine, its addons, and the official default
packs into a single drop-in artifact, focused on **running classic Terra packs unchanged**, **zero
telemetry**, and **resilient loading**.

Terra-K7 is an unofficial, GPL-3.0 build derived from Terra. It is built to grow across loaders and
Minecraft versions over time (see Roadmap) -- the current release is the starting point, not the scope.

## Highlights
- **Runs classic Terra packs unchanged.** Existing Terra config packs load as-is, including the
  long-standing `id{NBT}` block convention (loot chests, suspicious sand/gravel, end gateways, etc.) that
  stricter modern Minecraft parsers otherwise reject. Drop a pack in; no re-authoring.
- **Phones home to nobody.** No bStats, metrics, telemetry, update checks, or runtime downloads. Every
  dependency is shaded in and the default packs are bundled and extracted locally, so it runs fully
  offline on a bare server.
- **Faithful engine.** Biome appearance (fog/water/sky colours, ambient particles, sounds, music) is kept
  correct against the targeted Minecraft version rather than left stubbed.
- **One bad pack won't take your server down.** An unreadable pack is skipped with a single clear log line
  (plus a summary); a pack it *can* read is never rejected. The server keeps starting.

## Current release
- **Platform:** PaperMC / Spigot / Folia (server-side).
- **Minecraft:** 1.21.11.
- **Bundled default packs:** Overworld, Tartarus (Nether), ReimagEND (End).
- **Install:** drop the jar in `plugins/`, start once (it creates `plugins/Terra-K7/` and extracts the
  default packs), then assign a generator in `bukkit.yml` and restart:
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

## Roadmap
Terra-K7 is expanding beyond the current release: planned support for the **Fabric and NeoForge mod
loaders** and **Minecraft 26**, alongside the Paper-family plugin.

## Credits & license
Terra is created by [PolyhedralDev](https://github.com/PolyhedralDev/Terra) and contributors. Terra-K7 is
an unofficial build distributed under **GPL-3.0** (see `LICENSE`). All credit for the Terra engine and the
official packs goes to their original authors.
