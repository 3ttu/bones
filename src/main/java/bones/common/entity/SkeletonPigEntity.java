package bones.common.entity;

import bones.common.init.ModEntities;
import bones.common.init.ModItems;
import bones.common.init.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class SkeletonPigEntity extends UndeadAnimalEntity {

    private static final DataParameter<Boolean> SADDLED = EntityDataManager.createKey(SkeletonPigEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> BOOST_TIME = EntityDataManager.createKey(SkeletonPigEntity.class, DataSerializers.VARINT);
    // TODO use BoostHelper
    private boolean boosting;
    private int boostTime;
    private int totalBoostTime;

    public SkeletonPigEntity(EntityType<? extends SkeletonPigEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new SwimGoal(this));
        goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        goalSelector.addGoal(3, new BreedGoal(this, 1));
        goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.fromItems(ModItems.PORKCHOP_ON_A_STICK), false));
        goalSelector.addGoal(4, new TemptGoal(this, 1.2D, false, Ingredient.fromItems(Items.PORKCHOP)));
        goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1));
        goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6));
        goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 12)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25);
    }

    @Override
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
            return player.getHeldItemMainhand().getItem() == ModItems.PORKCHOP_ON_A_STICK || player.getHeldItemOffhand().getItem() == ModItems.PORKCHOP_ON_A_STICK;
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
        compound.putBoolean("Saddle", isSaddled());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        setSaddled(compound.getBoolean("Saddle"));
    }

    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData spawnData, @Nullable CompoundNBT dataTag) {
        spawnData = super.onInitialSpawn(world, difficulty, reason, spawnData, dataTag);
        if (world.getRandom().nextInt(50) == 0 && reason == SpawnReason.NATURAL) {
            PiglinEntity piglin = EntityType.PIGLIN.create(this.world);
            if (piglin != null) {
                setSaddled(true);
                piglin.setLocationAndAngles(getPosX(), getPosY(), getPosZ(), rotationYaw, 0);
                piglin.onInitialSpawn(world, difficulty, reason, null, null);
                world.addEntity(piglin);
                piglin.setItemStackToSlot(EquipmentSlotType.OFFHAND, new ItemStack(ModItems.PORKCHOP_ON_A_STICK));
                piglin.startRiding(this);
            }
        }
        return spawnData;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.SKELETON_PIG_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSoundEvents.SKELETON_PIG_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.SKELETON_PIG_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState block) {
        this.playSound(net.minecraft.util.SoundEvents.ENTITY_SKELETON_STEP, 0.15F, 1);
    }

    @Override
    public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!isBreedingItem(stack) && !player.isSecondaryUseActive() && isSaddled() && !isBeingRidden()) {
            if (!world.isRemote()) {
                player.startRiding(this);
            }
            return ActionResultType.func_233537_a_(world.isRemote());
        } else {
            ActionResultType actionResultType = super.func_230254_b_(player, hand);
            if (!actionResultType.isSuccessOrConsume()) {
                if (!stack.isEmpty() && stack.getItem() == Items.SADDLE && !isSaddled() && isAlive() && !isChild()) {
                    setSaddled(true);
                    world.playSound(player, getPosX(), getPosY(), getPosZ(), net.minecraft.util.SoundEvents.ENTITY_PIG_SADDLE, SoundCategory.NEUTRAL, 0.5F, 1);
                    consumeItemFromStack(player, stack);
                    return ActionResultType.func_233537_a_(world.isRemote());
                }
                return ActionResultType.PASS;
            } else {
                return actionResultType;
            }
        }
    }

    @Override
    protected void dropInventory() {
        super.dropInventory();
        if (isSaddled()) {
            entityDropItem(Items.SADDLE);
        }
    }

    public boolean isSaddled() {
        return dataManager.get(SADDLED);
    }

    public void setSaddled(boolean saddled) {
        dataManager.set(SADDLED, saddled);
    }

    @Override
    public void travel(Vector3d motion) {
        if (this.isAlive()) {
            Entity entity = getPassengers().isEmpty() ? null : getPassengers().get(0);
            if (entity != null && isBeingRidden() && canBeSteered()) {
                rotationYaw = entity.rotationYaw;
                prevRotationYaw = rotationYaw;
                rotationPitch = entity.rotationPitch * 0.5F;
                setRotation(rotationYaw, rotationPitch);
                renderYawOffset = rotationYaw;
                rotationYawHead = rotationYaw;
                stepHeight = 1;
                jumpMovementFactor = getAIMoveSpeed() * 0.1F;
                if (boosting && boostTime++ > totalBoostTime) {
                    boosting = false;
                }

                if (canPassengerSteer()) {
                    float f = (float) getAttribute(Attributes.MOVEMENT_SPEED).getValue() * 0.5F;
                    if (boosting) {
                        f += f * 1.15F * MathHelper.sin(boostTime / (float) totalBoostTime * (float) Math.PI);
                    }

                    setAIMoveSpeed(f);
                    super.travel(new Vector3d(0, 0, 1));
                } else {
                    setMotion(Vector3d.ZERO);
                }

                prevLimbSwingAmount = limbSwingAmount;
                double motionX = getPosX() - prevPosX;
                double motionZ = getPosZ() - prevPosZ;
                float f1 = MathHelper.sqrt(motionX * motionX + motionZ * motionZ) * 4;
                if (f1 > 1) {
                    f1 = 1;
                }

                limbSwingAmount += (f1 - limbSwingAmount) * 0.4F;
                limbSwing += limbSwingAmount;
            } else {
                stepHeight = 0.5F;
                jumpMovementFactor = 0.02F;
                super.travel(motion);
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
    public AgeableEntity func_241840_a(ServerWorld world, AgeableEntity parent) {
        return ModEntities.SKELETON_PIG.create(world);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Items.PORKCHOP;
    }
}
