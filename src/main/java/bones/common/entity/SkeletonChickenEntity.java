package bones.common.entity;

import bones.Bones;
import bones.common.init.ModEntities;
import bones.common.init.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTable;

public class SkeletonChickenEntity extends UndeadAnimalEntity {

    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.CHICKEN);
    public float wingRotation;
    public float destinationPos;
    public float flapSpeed;
    public float flap;
    public float wingRotationDelta = 1;
    public int timeUntilNextEgg = rand.nextInt(6000) + 6000;

    public SkeletonChickenEntity(EntityType<? extends SkeletonChickenEntity> type, World world) {
        super(type, world);
        setPathPriority(PathNodeType.WATER, 0);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new SwimGoal(this));
        goalSelector.addGoal(1, new PanicGoal(this, 1.4));
        goalSelector.addGoal(2, new BreedGoal(this, 1));
        goalSelector.addGoal(3, new TemptGoal(this, 1, false, TEMPTATION_ITEMS));
        goalSelector.addGoal(4, new FollowParentGoal(this, 1.1));
        goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1));
        goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6));
        goalSelector.addGoal(7, new LookRandomlyGoal(this));
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize size) {
        return isChild() ? size.height * 0.85F : size.height * 0.92F;
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 4)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25);
    }

    @Override
    public void livingTick() {
        super.livingTick();
        flap = wingRotation;
        flapSpeed = destinationPos;
        destinationPos = destinationPos + (onGround ? -1 : 4) * 0.3F;
        destinationPos = MathHelper.clamp(destinationPos, 0, 1);
        if (!onGround && wingRotationDelta < 1) {
            wingRotationDelta = 1;
        }

        wingRotationDelta = wingRotationDelta * 0.9F;
        Vector3d vec3d = getMotion();
        if (!onGround && vec3d.y < 0) {
            setMotion(vec3d.mul(1, 0.8, 1));
        }

        wingRotation += wingRotationDelta * 2;
        if (!world.isRemote && isAlive() && !isChild() && --timeUntilNextEgg <= 0) {
            playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1);
            // noinspection ConstantConditions
            LootTable table = world.getServer().getLootTableManager().getLootTableFromLocation(new ResourceLocation(Bones.MODID, "entities/skeleton_chicken_eggs"));
            LootContext context = new LootContext.Builder((ServerWorld) world).build(LootParameterSets.EMPTY);
            for (ItemStack stack : table.generate(context)) {
                entityDropItem(stack);
            }
            timeUntilNextEgg = rand.nextInt(6000) + 6000;
        }

    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.SKELETON_CHICKEN_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSoundEvents.SKELETON_CHICKEN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.SKELETON_CHICKEN_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockState) {
        this.playSound(SoundEvents.ENTITY_SKELETON_STEP, 0.08F, 1);
    }

    @Override
    public AgeableEntity func_241840_a(ServerWorld world, AgeableEntity parent) {
        return ModEntities.SKELETON_CHICKEN.create(world);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return TEMPTATION_ITEMS.test(stack);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("EggLayTime")) {
            timeUntilNextEgg = compound.getInt("EggLayTime");
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("EggLayTime", timeUntilNextEgg);
    }

    @Override
    public void updatePassenger(Entity passenger) {
        super.updatePassenger(passenger);
        float f = MathHelper.sin(renderYawOffset * ((float) Math.PI / 180));
        float f1 = MathHelper.cos(renderYawOffset * ((float) Math.PI / 180));
        passenger.setPosition(getPosX() + 0.1 * f, getPosY() + getHeight() * 0.5 + passenger.getYOffset(), getPosZ() - 0.1F * f1);
        if (passenger instanceof LivingEntity) {
            ((LivingEntity) passenger).renderYawOffset = renderYawOffset;
        }
    }
}
