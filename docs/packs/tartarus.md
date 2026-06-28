# Tartarus (Terra default pack)

Terra's official **Tartarus** config pack (by Jaddot), id `TARTARUS`. Bundled with and supported as-is by
the `Terra-K7` plugin on MC 1.21.11 -- **no fork required**; the plugin reads the standard pack format
unchanged.

## What it does
A Terra **Nether** generation pack: replaces the vanilla Nether with 3D-noise generation and a large set
of new Nether biomes and terrain.

## Where it does it
The **Nether** dimension. Assign on Paper via `bukkit.yml`:
```
worlds:
  world_nether:
    generator: Terra-K7:TARTARUS
```

## Highlights / specials
- Many new Nether biomes layered over Terra's 3D-noise terrain (a far richer Nether than vanilla).
- Full Terra pipeline (biome distribution, terrain, ores, features) tuned for the Nether.
- The lightest of the three default packs in custom NBT structures, so it loads cleanly with no special
  handling.

## Plugin support note (1.21.11)
Loads and generates as-is on MC 1.21.11 with `Terra-K7`. No pack edits needed.

