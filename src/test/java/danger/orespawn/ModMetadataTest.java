package danger.orespawn;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

class ModMetadataTest {

    private static String resource(String path) {
        InputStream in = ModMetadataTest.class.getResourceAsStream(path);
        assertNotNull(in, "missing resource " + path);
        try (Scanner s = new Scanner(in, StandardCharsets.UTF_8.name())) {
            return s.useDelimiter("\\A")
                .next();
        }
    }

    @Test
    void mcmodInfoDeclaresOreSpawn() {
        assertTrue(
            resource("/mcmod.info").contains("\"modid\": \"orespawn\""),
            "mcmod.info should declare the orespawn mod id");
    }

    @Test
    void coreTexturesArePackaged() {
        assertNotNull(ModMetadataTest.class.getResourceAsStream("/assets/orespawn/textures/items/zookeeper.png"));
    }
}
