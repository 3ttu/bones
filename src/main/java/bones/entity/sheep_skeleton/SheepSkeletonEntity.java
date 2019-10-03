package bones.entity.sheep_skeleton;

import bones.Bones;
import bones.setup.SoundEvents;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SheepSkeletonEntity extends AnimalEntity implements net.minecraftforge.common.IShearable {

    private static final DataParameter<Boolean> IS_SHEARED = EntityDataManager.createKey(SheepSkeletonEntity.class, DataSerializers.BOOLEAN);


    public SheepSkeletonEntity(EntityType<? extends SheepSkeletonEntity> type, World worldIn) {
        super(type, worldIn);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.1, Ingredient.fromItems(Items.ROTTEN_FLESH), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.23F);
    }

    protected void registerData() {
        super.registerData();
        dataManager.register(IS_SHEARED, false);
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 0.95F * sizeIn.height;
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        return Bones.SHEEP_SKELETON_ENTITY_TYPE.create(ageable.world);
    }

    @Override
    public java.util.List<ItemStack> onSheared(ItemStack item, net.minecraft.world.IWorld world, BlockPos pos, int fortune) {
        java.util.List<ItemStack> result = new java.util.ArrayList<>();
        if (!this.world.isRemote) {
            dataManager.set(IS_SHEARED, true);
            int i = 1 + this.rand.nextInt(3);

            for(int j = 0; j < i; ++j) {
                result.add(new ItemStack(Items.MUTTON));
            }
        }
        return result;
    }

    public boolean isSheared() {
        return dataManager.get(IS_SHEARED);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SHEEP_SKELETON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.SHEEP_SKELETON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SHEEP_SKELETON_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(net.minecraft.util.SoundEvents.ENTITY_SKELETON_STEP, 0.15F, 1);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Items.ROTTEN_FLESH;
    }
}
