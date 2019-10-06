package bones.item;

import bones.entity.skeleton_pig.SkeletonPigEntity;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PorkchopOnAStickItem extends Item {

    public PorkchopOnAStickItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (worldIn.isRemote) {
            return new ActionResult<>(ActionResultType.PASS, stack);
        } else {
            if (player.isPassenger() && player.getRidingEntity() instanceof SkeletonPigEntity) {
                SkeletonPigEntity entity = (SkeletonPigEntity) player.getRidingEntity();
                if (stack.getMaxDamage() - stack.getDamage() >= 1 && entity.boost()) {
                    stack.damageItem(1, player, (p) -> p.sendBreakAnimation(hand));
                    if (stack.isEmpty()) {
                        ItemStack fishingRodStack = new ItemStack(Items.FISHING_ROD);
                        fishingRodStack.setTag(stack.getTag());
                        return new ActionResult<>(ActionResultType.SUCCESS, fishingRodStack);
                    }

                    return new ActionResult<>(ActionResultType.SUCCESS, stack);
                }
            }

            player.addStat(Stats.ITEM_USED.get(this));
            return new ActionResult<>(ActionResultType.PASS, stack);
        }
    }
}
