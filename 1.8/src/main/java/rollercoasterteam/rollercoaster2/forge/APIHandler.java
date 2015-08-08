package rollercoasterteam.rollercoaster2.forge;

import com.google.common.primitives.Ints;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ISmartBlockModel;
import net.minecraftforge.client.model.ISmartItemModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import rollercoasterteam.rollercoaster2.core.ModInfo;
import rollercoasterteam.rollercoaster2.core.api.IApiHandler;
import rollercoasterteam.rollercoaster2.core.api.IXMod;
import rollercoasterteam.rollercoaster2.core.api.block.RCBlock;
import rollercoasterteam.rollercoaster2.core.api.block.RCMeta;
import rollercoasterteam.rollercoaster2.core.api.item.RCItem;
import rollercoasterteam.rollercoaster2.core.api.world.RCWorld;
import rollercoasterteam.rollercoaster2.forge.client.BlockRenderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class APIHandler implements IApiHandler {
    
    public HashMap<RCBlock, Block> blockHashMap = new HashMap<RCBlock, Block>();
	public HashMap<World, RCWorld> worldHashMap = new HashMap<World, RCWorld>();

    @Override
    public void registerBlock(RCBlock block) {
		BlockConverter mcBlock = new BlockConverter(block);
		GameRegistry.registerBlock(mcBlock, new ItemBlockConverter(mcBlock).getClass(), block.getName());


        if(!blockHashMap.containsKey(block)){
            blockHashMap.put(block, mcBlock);
        }
        new BlockRenderer(block);
    }

    @Override
    public void registerItem(RCItem rcItem) {
        GameRegistry.registerItem(new ItemConverter(rcItem), rcItem.getName());
    }

    @Override
    public void modContrcution(IXMod mod) {

    }

    @Override
    public RCWorld getWorld(int dimID) {
        return getWorld(DimensionManager.getWorld(dimID));
    }

	public RCWorld getWorld(World world) {
		if(worldHashMap.containsKey(world)){
			return worldHashMap.get(world);
		}
		worldHashMap.put(world,new WorldConverter(world));
		return worldHashMap.get(world);
	}
}
