# Terra-K7 1.0 (for Minecraft 1.21.11)

A plugin-only, **self-contained** build of the [Terra](https://github.com/PolyhedralDev/Terra) voxel
world-generation engine for **PaperMC / Spigot / Folia, Minecraft 1.21.11**. One jar -- the engine, all
38 Terra addons, and the default config packs are bundled inside it.

Terra-K7 is an unofficial 1.21.11 build (the "K7" line) derived from Terra's in-development 7.0 branch. It
runs as its own plugin (`Terra-K7`) so it never collides with official Terra.

## Self-contained -- no outside help at runtime
This plugin does **not** contact anything when it runs on your server:
- No bStats / metrics, no update checks, no "phone home" of any kind (verified: zero such code in the
  source and zero metrics classes in the jar).
- No runtime downloads -- the default config packs are baked into the jar and extracted locally on first
  start. (Packs are fetched only at *build* time, never at runtime.)
- All library dependencies (tectonic, strata, paralithic, seismic, caffeine, cloud, guava, ...) are shaded
  into the jar. Drop it on a vanilla Paper server with no other plugins and it runs.

## Install
1. Put `terra-k7-1.0-plugin.jar` in your server's `plugins/` folder.
2. Start the server once. It creates `plugins/Terra-K7/` and extracts the bundled packs into
   `plugins/Terra-K7/packs/` (Overworld, ReimagEND, Tartarus) and `plugins/Terra-K7/metapacks/` (default).
3. Tell a world to use a generator via `bukkit.yml` (see below), then restart.

## Assigning generators (bukkit.yml)
The generator namespace is the plugin name, **Terra-K7**:
```
worlds:
  world:
    generator: Terra-K7:OVERWORLD
  world_nether:
    generator: Terra-K7:TARTARUS
  world_the_end:
    generator: Terra-K7:REIMAGEND
```
The id after `Terra-K7:` is a loaded pack id. Run with the bundled defaults, or drop your own / community
packs (folders or .zip) into `plugins/Terra-K7/packs/`.

## Bundled default packs
See `docs/packs/`:
- **Overworld** -- full 3D-noise overworld generator. (`docs/packs/overworld.md`)
- **ReimagEND** -- the End reimagined with new biomes/structures. (`docs/packs/reimagend.md`)
- **Tartarus** -- a richer Nether with many new biomes. (`docs/packs/tartarus.md`)

## What's in this 1.21.11 build (vs upstream)
- **Reads the standard Terra pack format on 1.21.11.** MC 1.21.11's strict block parser rejects the
  `id{NBT}` convention Terra packs use (e.g. `minecraft:chest{LootTable:'...'}`); this build tolerates it
  (the block/entity places; the NBT, which Bukkit world-gen cannot apply, is ignored -- the long-standing
  Terra-on-Bukkit behaviour). Packs load unchanged.
- **Biome appearance migrated to 1.21.11's EnvironmentAttributes** (fog / water / sky colours, ambient
  particles, ambient sounds, music) -- features upstream had left stubbed for the 1.21.11 port.
- **Graceful handling of an unreadable pack:** it is skipped with a one-line warning and a summary (no
  stack-trace dumps), and a readable pack is never rejected. The server keeps starting.

## Validation
Built on JDK 21; boot-tested on Paper 1.21.11. All three default packs generate as real Terra terrain via
Chunksmith: Overworld (overworld), Tartarus (Nether), ReimagEND (End) -- no crashes, no vanilla fallback.

## License
GPL-3.0 (see `LICENSE`). Terra (c) PolyhedralDev and contributors. Terra-K7 is an unofficial 1.21.11 build.
