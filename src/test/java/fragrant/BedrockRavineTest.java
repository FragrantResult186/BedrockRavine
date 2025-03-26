package fragrant;

import fragrant.utils.BedrockVersion;
import fragrant.ravine.BedrockRavine;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BedrockRavineTest {

    @Test
    void PRE_21_60() {
        BedrockRavine.Info ravine = BedrockRavine.canGenerateRavine(12345, BedrockVersion.V1_21_60, 25, 39);
        assertEquals(403, ravine.blockX());
        assertEquals(35, ravine.blockY());
        assertEquals(627, ravine.blockZ());
    }

    @Test
    void POST1_21_60() {
        BedrockRavine.Info ravine = BedrockRavine.canGenerateRavine(-9876543210L, BedrockVersion.V1_17, -19, 116);
        assertEquals(6.70, ravine.width(), 0.01);
        assertEquals(1.31, ravine.yaw(), 0.01);
        assertEquals(-0.09, ravine.pitch(), 0.01);
        assertTrue(true, String.valueOf(ravine.isGiant()));
    }
}