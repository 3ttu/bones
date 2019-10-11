package bones.entity.skeleton_pig;

import bones.entity.UndeadAnimalEntity;
import bones.setup.Entities;
import bones.setup.SoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.ZombiePigmanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class SkeletonPigEntity extends UndeadAnimalEntity {
    private static final DataParameter<Boolean> SADDLED = EntityDataManager.createKey(SkeletonPigEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> BOOST_TIME = EntityDataManager.createKey(SkeletonPigEntity.class, DataSerializers.VARINT);
    private boolean boosting;
    private int boostTime;
    private int totalBoostTime;

    public SkeletonPigEntity(EntityType<? extends SkeletonPigEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new SwimGoal(this));
        goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        goalSelector.addGoal(3, new BreedGoal(this, 1));
        goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.fromItems(bones.setup.Items.PORKCHOP_ON_A_STICK), false));
        goalSelector.addGoal(4, new TemptGoal(this, 1.2D, false, Ingredient.fromItems(Items.PORKCHOP)));
        goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1));
        goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6));
        goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12);
        getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    @Override
    @Nullable
    public Entity getControllingPassenger() {
        return getPassengers().isEmpty() ? null : getPassengers().get(0);
    }

    @Override
    public boolean canBeSteered() {
        Entity entity = getControllingPassenger();
        if (!(entity instanceof PlayerEntity)) {
            return false;
        } else {
            PlayerEntity player = (PlayerEntity) entity;
            return player.getHeldItemMainhand().getItem() == bones.setup.Items.PORKCHOP_ON_A_STICK || player.getHeldItemOffhand().getItem() == bones.setup.Items.PORKCHOP_ON_A_STICK;
        }
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key) {
        if (BOOST_TIME.equals(key) && world.isRemote) {
            boosting = true;
            boostTime = 0;
            totalBoostTime = dataManager.get(BOOST_TIME);
        }

        super.notifyDataManagerChange(key);
    }

    @Override
    protected void registerData() {
        super.registerData();
        dataManager.register(SADDLED, false);
        dataManager.register(BOOST_TIME, 0);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("Saddle", getSaddled());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        setSaddled(compound.getBoolean("Saddle"));
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IWorld world, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData spawnData, @Nullable CompoundNBT dataTag) {
        spawnData = super.onInitialSpawn(world, difficulty, reason, spawnData, dataTag);
        if (world.getRandom().nextInt(50) == 0 && world.getDimension().isNether()) {
            ZombiePigmanEntity pigman = EntityType.ZOMBIE_PIGMAN.create(this.world);
            if (pigman != null) {
                setSaddled(true);
                pigman.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0);
                pigman.onInitialSpawn(world, difficulty, reason, null, null);
                world.addEntity(pigman);
                pigman.setItemStackToSlot(EquipmentSlotType.OFFHAND, new ItemStack(bones.setup.Items.PORKCHOP_ON_A_STICK));
                pigman.startRiding(this);
            }
        }
        return spawnData;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SKELETON_PIG_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.SKELETON_PIG_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SKELETON_PIG_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(net.minecraft.util.SoundEvents.ENTITY_SKELETON_STEP, 0.15F, 1);
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        if (!super.processInteract(player, hand)) {
            ItemStack itemstack = player.getHeldItem(hand);
            if (itemstack.getItem() == Items.NAME_TAG) {
                itemstack.interactWithEntity(player, this, hand);
                return true;
            } else if (getSaddled() && !isBeingRidden()) {
                if (!this.world.isRemote) {
                    player.startRiding(this);
                }

                return true;
            } else if (itemstack.getItem() == Items.SADDLE) {
                itemstack.interactWithEntity(player, this, hand);
                if (isAlive() && !getSaddled() && !isChild()) {
                    setSaddled(true);
                    world.playSound(player, posX, posY, posZ, net.minecraft.util.SoundEvents.ENTITY_PIG_SADDLE, SoundCategory.NEUTRAL, 0.5F, 1);
                    itemstack.shrink(1);
                }
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void dropInventory() {
        super.dropInventory();
        if (getSaddled()) {
            entityDropItem(Items.SADDLE);
        }
    }

    public boolean getSaddled() {
        return dataManager.get(SADDLED);
    }

    public void setSaddled(boolean saddled) {
        if (saddled) {
            dataManager.set(SADDLED, true);
        } else {
            dataManager.set(SADDLED, false);
        }

    }

    @Override
    public void travel(Vec3d p_213352_1_) {
        if (this.isAlive()) {
            Entity entity = getPassengers().isEmpty() ? null : getPassengers().get(0);
            if (entity != null && isBeingRidden() && canBeSteered()) {
                rotationYaw = entity.rotationYaw;
                prevRotationYaw = rotationYaw;
                rotationPitch = entity.rotationPitch * 0.5F;
                setRotation(rotationYaw, rotationPitch);
                renderYawOffset = rotationYaw;
                rotationYawHead = rotationYaw;
                stepHeight = 1.0F;
                jumpMovementFactor = getAIMoveSpeed() * 0.1F;
                if (boosting && boostTime++ > totalBoostTime) {
                    boosting = false;
                }

                if (canPassengerSteer()) {
                    float f = (float) getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue() * 0.5F;
                    if (boosting) {
                        f += f * 1.15F * MathHelper.sin(boostTime / (float) totalBoostTime * (float) Math.PI);
                    }

                    setAIMoveSpeed(f);
                    super.travel(new Vec3d(0, 0, 1));
                } else {
                    setMotion(Vec3d.ZERO);
                }

                prevLimbSwingAmount = limbSwingAmount;
                double motionX = posX - prevPosX;
                double motionZ = posZ - prevPosZ;
                float f1 = MathHelper.sqrt(motionX * motionX + motionZ * motionZ) * 4;
                if (f1 > 1) {
                    f1 = 1;
                }

                limbSwingAmount += (f1 - limbSwingAmount) * 0.4F;
                limbSwing += limbSwingAmount;
            } else {
                stepHeight = 0.5F;
                jumpMovementFactor = 0.02F;
                super.travel(p_213352_1_);
            }
        }
    }

    public boolean boost() {
        if (boosting) {
            return false;
        } else {
            boosting = true;
            boostTime = 0;
            totalBoostTime = getRNG().nextInt(841) + 140;
            getDataManager().set(BOOST_TIME, totalBoostTime);
            return true;
        }
    }

    @Override
    public SkeletonPigEntity createChild(AgeableEntity ageable) {
        return Entities.SKELETON_PIG.create(world);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Items.PORKCHOP;
    }
}
