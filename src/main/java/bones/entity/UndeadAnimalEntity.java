package bones.entity;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

@MethodsReturnNonnullByDefault
public abstract class UndeadAnimalEntity extends AnimalEntity {

    protected UndeadAnimalEntity(EntityType<? extends UndeadAnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.UNDEAD;
    }

    public boolean canSpawn(IWorld worldIn, SpawnReason spawnReasonIn) {
        return true;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 8;
    }
}
