package bones.common.entity;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public abstract class UndeadAnimalEntity extends AnimalEntity {

    protected UndeadAnimalEntity(EntityType<? extends UndeadAnimalEntity> type, World world) {
        super(type, world);
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.UNDEAD;
    }

    public boolean canSpawn(IWorld world, SpawnReason spawnReason) {
        return true;
    }
}
