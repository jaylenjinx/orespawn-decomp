package danger.orespawn.world.gen;

import java.util.Random;

import net.minecraft.world.gen.structure.MapGenVillage;

public class MapGenMoreVillages extends MapGenVillage {

    private int field_82665_g = 9;
    private int field_82666_h = 7;

    protected boolean canSpawnStructureAtCoords(int n, int n2) {
        int n3 = n;
        int n4 = n2;
        if (n < 0) {
            n -= this.field_82665_g - 1;
        }

        if (n2 < 0) {
            n2 -= this.field_82665_g - 1;
        }

        int n5 = n / this.field_82665_g;
        int n6 = n2 / this.field_82665_g;
        Random rand = this.worldObj.setRandomSeed(n5, n6, 10387312);
        n5 *= this.field_82665_g;
        n6 *= this.field_82665_g;
        n5 += rand.nextInt(this.field_82665_g - this.field_82666_h);
        n6 += rand.nextInt(this.field_82665_g - this.field_82666_h);
        if (n3 == n5 && n4 == n6) {
            boolean flag = this.worldObj.getWorldChunkManager()
                .areBiomesViable(n3 * 16 + 8, n4 * 16 + 8, 0, villageSpawnBiomes);
            return true;
        } else {
            return false;
        }
    }
}
