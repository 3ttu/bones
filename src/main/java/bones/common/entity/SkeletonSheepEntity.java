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
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SkeletonSheepEntity extends UndeadAnimalEntity implements net.minecraftforge.common.IForgeShearable {

    private static final DataParameter<Boolean> IS_SHEARED = EntityDataManager.createKey(SkeletonSheepEntity.class, DataSerializers.BOOLEAN);

    private static final ResourceLocation LOOT_TABLE_UNSHEARED = new ResourceLocation(Bones.MODID, "entities/skeleton_sheep_unsheared");

    public SkeletonSheepEntity(EntityType<? extends SkeletonSheepEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.1, Ingredient.fromItems(Items.ROTTEN_FLESH, Items.MUTTON), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 8)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25);
    }

    @Override
    protected void registerData() {
        super.registerData();
        dataManager.register(IS_SHEARED, true);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        dataManager.set(IS_SHEARED, !compound.getBoolean("IsShearable"));
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("IsShearable", !isSheared());
    }

    @Override
    protected ResourceLocation getLootTable() {
        if (!isSheared()) {
            return LOOT_TABLE_UNSHEARED;
        }
        return super.getLootTable();
    }

    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData spawnData, @Nullable CompoundNBT dataTag) {
        if (rand.nextInt(4) == 0) {
            dataManager.set(IS_SHEARED, false);
        }
        return super.onInitialSpawn(world, difficulty, reason, spawnData, dataTag);
    }

    protected float getStandingEyeHeight(Pose pose, EntitySize size) {
        return 0.95F * size.height;
    }

    @Override
    public AgeableEntity func_241840_a(ServerWorld world, AgeableEntity parent) {
        return ModEntities.SKELETON_SHEEP.create(world);
    }

    @Override
    public List<ItemStack> onSheared(PlayerEntity player, ItemStack stack, World world, BlockPos pos, int fortune) {
        List<ItemStack> result = new ArrayList<>();
        if (!world.isRemote()) {
            dataManager.set(IS_SHEARED, true);
            int amount = 1 + rand.nextInt(3);

            for (int i = 0; i < amount; ++i) {
                result.add(new ItemStack(Items.MUTTON));
            }
        }
        playSound(ModSoundEvents.SKELETON_SHEEP_SHEAR, 1, 1);
        return result;
    }

    @Override
    public boolean isShearable(ItemStack item, World world, BlockPos pos) {
        return !isSheared();
    }

    public boolean isSheared() {
        return dataManager.get(IS_SHEARED);
    }

    @Override
    public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
        ActionResultType actionResultType = super.func_230254_b_(player, hand);
        if (!actionResultType.isSuccessOrConsume()) {
            ItemStack stack = player.getHeldItem(hand);
            if (stack.getItem() == Items.ROTTEN_FLESH && isSheared()) {
                consumeItemFromStack(player, stack);
                if (!world.isRemote) {
                    if (rand.nextInt(3) == 0) {
                        dataManager.set(IS_SHEARED, false);
                    }
                } else {
                    for (int i = 4 + rand.nextInt(4); i > 0; i--) {
                        world.addParticle(ParticleTypes.HAPPY_VILLAGER, getPosX() + rand.nextFloat() * getWidth() * 2 - getWidth(), getPosY() + 0.5 + rand.nextFloat() * getHeight(), getPosZ() + rand.nextFloat() * getWidth() * 2 - getWidth(), 0, 0, 0);
                    }
                }
                return ActionResultType.func_233537_a_(world.isRemote());
            }
        }
        return actionResultType;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.SKELETON_SHEEP_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSoundEvents.SKELETON_SHEEP_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.SKELETON_SHEEP_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(net.minecraft.util.SoundEvents.ENTITY_SKELETON_STEP, 0.15F, 1);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Items.MUTTON;
    }
}
