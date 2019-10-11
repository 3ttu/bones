package bones.entity.skeleton_chicken;

import bones.Bones;
import bones.entity.UndeadAnimalEntity;
import bones.setup.Entities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameterSets;
import net.minecraft.world.storage.loot.LootTable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class SkeletonChickenEntity extends UndeadAnimalEntity {

    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.CHICKEN);
    public float wingRotation;
    public float destPos;
    public float oFlapSpeed;
    public float oFlap;
    public float wingRotDelta = 1;
    public int timeUntilNextEgg = rand.nextInt(6000) + 6000;

    public SkeletonChickenEntity(EntityType<? extends SkeletonChickenEntity> type, World worldIn) {
        super(type, worldIn);
        setPathPriority(PathNodeType.WATER, 0);
    }

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

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return isChild() ? sizeIn.height * 0.85F : sizeIn.height * 0.92F;
    }

    protected void registerAttributes() {
        super.registerAttributes();
        getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0D);
        getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    public void livingTick() {
        super.livingTick();
        oFlap = wingRotation;
        oFlapSpeed = destPos;
        destPos = destPos + (onGround ? -1 : 4) * 0.3F;
        destPos = MathHelper.clamp(destPos, 0, 1);
        if (!onGround && wingRotDelta < 1) {
            wingRotDelta = 1;
        }

        wingRotDelta = wingRotDelta * 0.9F;
        Vec3d vec3d = getMotion();
        if (!onGround && vec3d.y < 0) {
            setMotion(vec3d.mul(1, 0.8, 1));
        }

        wingRotation += wingRotDelta * 2;
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

    public void fall(float distance, float damageMultiplier) {
    }

    protected SoundEvent getAmbientSound() {
        return bones.setup.SoundEvents.SKELETON_CHICKEN_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return bones.setup.SoundEvents.SKELETON_CHICKEN_HURT;
    }

    protected SoundEvent getDeathSound() {
        return bones.setup.SoundEvents.SKELETON_CHICKEN_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(net.minecraft.util.SoundEvents.ENTITY_SKELETON_STEP, 0.08F, 1);
    }


    public SkeletonChickenEntity createChild(AgeableEntity ageable) {
        return Entities.SKELETON_CHICKEN.create(world);
    }

    public boolean isBreedingItem(ItemStack stack) {
        return TEMPTATION_ITEMS.test(stack);
    }

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("EggLayTime")) {
            timeUntilNextEgg = compound.getInt("EggLayTime");
        }
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("EggLayTime", timeUntilNextEgg);
    }

    public void updatePassenger(Entity passenger) {
        super.updatePassenger(passenger);
        float f = MathHelper.sin(renderYawOffset * ((float) Math.PI / 180));
        float f1 = MathHelper.cos(renderYawOffset * ((float) Math.PI / 180));
        passenger.setPosition(posX + 0.1 * f, posY + getHeight() * 0.5 + passenger.getYOffset(), posZ - 0.1F * f1);
        if (passenger instanceof LivingEntity) {
            ((LivingEntity) passenger).renderYawOffset = renderYawOffset;
        }
    }
}
