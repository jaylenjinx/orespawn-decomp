# OreSpawn (1.7.10) — decompiled dev workspace

A buildable source tree for **OreSpawn 1.7.10-20.3** by TheyCallMeDanger, recovered from the
released jar and set up on the [GTNH ExampleMod buildscript](https://github.com/GTNewHorizons/ExampleMod1.7.10)
(RetroFuturaGradle, Forge 10.13.4.1614, MCP `stable_12` mappings).

OreSpawn is not open-source; all rights to the original code and assets stay with its author.
This tree is for personal study and modding.

## How the sources were produced

1. The release jar (SRG names, e.g. `func_149663_c`) was remapped to MCP names
   (`setBlockName`) at bytecode level with RetroFuturaGradle's `rfg.deobf(...)`.
2. The remapped jar was decompiled with Vineflower 1.11.1, with the patched Minecraft
   jar and Forge libraries on its classpath so generics and overrides resolve.
3. Decompiler artefacts were fixed by hand (29 javac errors: `int` locals that should be
   `boolean`, raw-`List` for-each loops) and three copied vanilla `__OBFID` constants were removed.
4. Sources were formatted with the project's Spotless rules.

The rebuilt release jar contains exactly the same set of classes and resources as the original.
Decompiler-generated local and parameter names (`var12`, `par1`, `p_70097_1_`) were replaced with
descriptive ones, inferred from overridden Minecraft/Forge signatures, initialisers and how each variable is
used. The compiled bytecode is instruction-for-instruction identical to the unrenamed tree. Some names are
still generic (`n`, `f`, `i2`) where nothing better could be inferred; rename them as you go.

## Layout

Code is in `src/main/java/danger/orespawn` (`OreSpawnMain` is the `@Mod` class); textures, models and
sounds are in `src/main/resources/assets/orespawn`.

| Package | Contents |
| --- | --- |
| `danger.orespawn` | `@Mod` class, common proxy, config/stat tables, sounds, GUI handler, utilities |
| `block` | blocks and tile entities |
| `item` | items, tools, armour, food |
| `entity` | mobs and other entities; `entity.ai` AI tasks and sorters; `entity.projectile` thrown and shot entities |
| `client` | client proxy and key handler; `client.render` entity/tile renderers (`client.render.item` item renderers); `client.model` models; `client.gui` screens |
| `inventory` | containers |
| `network` | packets (rider controls) |
| `dispenser` | dispenser behaviours |
| `world.dimension` | dimension providers, biomes, chunk providers, teleporters |
| `world.gen` | ore and structure generation, trees, dungeons, mazes |

## Requirements

- **JDK 25** to run Gradle (the mod still compiles to Java 8 bytecode via Jabel; Gradle
  downloads the toolchain it needs).
- IntelliJ IDEA (Community is fine).

## IntelliJ IDEA setup

1. **File → Open…** and pick this folder (open the folder, not `build.gradle.kts`). Trust the project.
2. **Settings → Build, Execution, Deployment → Build Tools → Gradle**: set **Gradle JVM** to a JDK 25.
3. **File → Project Structure → Project**: SDK = the same JDK 25, language level 8.
4. Let the Gradle sync finish (first time takes several minutes: it downloads and decompiles
   Minecraft). The sync generates run configurations: `1.7.10 Client`, `1.7.10 Server`, and the
   modern-Java variants.
5. On Apple Silicon use the **Java 21 client** (`runClient21`, which uses lwjgl3ify);
   the plain `runClient` needs x86 LWJGL 2 natives.

To browse Minecraft/Forge sources, the sync attaches them automatically
(`build/rfg/mcp_patched_minecraft-sources.jar`).

## Command line

```bash
./gradlew build          # compile, test, and produce build/libs/orespawn-<version>.jar (release, reobfuscated)
./gradlew runClient21    # play in a dev client (modern Java + lwjgl3ify)
./gradlew runServer      # dev dedicated server
./gradlew test           # JUnit tests in src/test/java
./gradlew spotlessApply  # format sources (the build fails on unformatted code)
```

The jar without a suffix in `build/libs` is the one to drop into a normal 1.7.10 Forge `mods` folder;
`-dev` is for other dev workspaces.

## Versioning and releases

The version comes from the latest git tag. Pushing a tag runs the `release-tags` workflow,
which builds the jar and attaches it to a GitHub release.
