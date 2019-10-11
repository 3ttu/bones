package bones.entity;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
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
}
