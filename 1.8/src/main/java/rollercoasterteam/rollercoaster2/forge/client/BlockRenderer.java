package rollercoasterteam.rollercoaster2.forge.client;

import com.google.common.primitives.Ints;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ISmartBlockModel;
import net.minecraftforge.client.model.ISmartItemModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rollercoasterteam.rollercoaster2.core.ModInfo;
import rollercoasterteam.rollercoaster2.core.api.block.RCBlock;
import rollercoasterteam.rollercoaster2.forge.Rollercoaster2Forge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlockRenderer {

    ModelResourceLocation blockLocation;
    ModelResourceLocation itemLocation;

    RCBlock rcBlock;

    public BlockRenderer(RCBlock rcBlock) {
        this.rcBlock = rcBlock;

        blockLocation = new ModelResourceLocation(ModInfo.MODID + ":" + rcBlock.getName(), "normal");
        itemLocation = new ModelResourceLocation(ModInfo.MODID + ":" + rcBlock.getName(), "inventory");
        Item item = Item.getItemFromBlock(Rollercoaster2Forge.handler.blockHashMap.get(rcBlock));
        ModelLoader.setCustomModelResourceLocation(item, 0, itemLocation);
        ModelLoader.setCustomStateMapper(Rollercoaster2Forge.handler.blockHashMap.get(rcBlock), new StateMapperBase() {
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return blockLocation;
            }
        });
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onModelBakeEvent(ModelBakeEvent event) {
        TextureAtlasSprite base = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(rcBlock.getTexture());
        IBakedModel customModel = new CustomModel(base);
        event.modelRegistry.putObject(blockLocation, customModel);
    }


    public static class CustomModel implements IBakedModel, ISmartBlockModel, ISmartItemModel {
        private final TextureAtlasSprite base;
        private RCBlock rcBlock;

        public CustomModel(TextureAtlasSprite base) {
            this(base, null);
        }

        public CustomModel(TextureAtlasSprite base, IExtendedBlockState state) {
            this.base = base;
        }

        @Override
        public List<BakedQuad> getFaceQuads(EnumFacing side) {
            return Collections.emptyList();
        }

        private int[] vertexToInts(float x, float y, float z, int color, TextureAtlasSprite texture, float u, float v) {
            return new int[]{
                    Float.floatToRawIntBits(x),
                    Float.floatToRawIntBits(y),
                    Float.floatToRawIntBits(z),
                    color,
                    Float.floatToRawIntBits(texture.getInterpolatedU(u)),
                    Float.floatToRawIntBits(texture.getInterpolatedV(v)),
                    0
            };
        }

        private BakedQuad createSidedBakedQuad(float x1, float x2, float z1, float z2, float y, TextureAtlasSprite texture, EnumFacing side) {
            Vec3 v1 = rotate(new Vec3(x1 - .5, y - .5, z1 - .5), side).addVector(.5, .5, .5);
            Vec3 v2 = rotate(new Vec3(x1 - .5, y - .5, z2 - .5), side).addVector(.5, .5, .5);
            Vec3 v3 = rotate(new Vec3(x2 - .5, y - .5, z2 - .5), side).addVector(.5, .5, .5);
            Vec3 v4 = rotate(new Vec3(x2 - .5, y - .5, z1 - .5), side).addVector(.5, .5, .5);
            return new BakedQuad(Ints.concat(
                    vertexToInts((float) v1.xCoord, (float) v1.yCoord, (float) v1.zCoord, -1, texture, 0, 0),
                    vertexToInts((float) v2.xCoord, (float) v2.yCoord, (float) v2.zCoord, -1, texture, 0, 16),
                    vertexToInts((float) v3.xCoord, (float) v3.yCoord, (float) v3.zCoord, -1, texture, 16, 16),
                    vertexToInts((float) v4.xCoord, (float) v4.yCoord, (float) v4.zCoord, -1, texture, 16, 0)
            ), -1, side);
        }

        private static Vec3 rotate(Vec3 vec, EnumFacing side) {
            switch (side) {
                case DOWN:
                    return new Vec3(vec.xCoord, -vec.yCoord, -vec.zCoord);
                case UP:
                    return new Vec3(vec.xCoord, vec.yCoord, vec.zCoord);
                case NORTH:
                    return new Vec3(vec.xCoord, vec.zCoord, -vec.yCoord);
                case SOUTH:
                    return new Vec3(vec.xCoord, -vec.zCoord, vec.yCoord);
                case WEST:
                    return new Vec3(-vec.yCoord, vec.xCoord, vec.zCoord);
                case EAST:
                    return new Vec3(vec.yCoord, -vec.xCoord, vec.zCoord);
            }
            return null;
        }

        @Override
        public List<BakedQuad> getGeneralQuads() {
            List<BakedQuad> ret = new ArrayList<BakedQuad>();
            for (EnumFacing f : EnumFacing.values()) {
                ret.add(createSidedBakedQuad(0, 1, 0, 1, 1, base, f));
            }
            return ret;
        }

        @Override
        public boolean isGui3d() {
            return true;
        }

        @Override
        public boolean isAmbientOcclusion() {
            return true;
        }

        @Override
        public boolean isBuiltInRenderer() {
            return false;
        }

        @Override
        public TextureAtlasSprite getTexture() {
            return this.base;
        }

        @Override
        public ItemCameraTransforms getItemCameraTransforms() {
            return ItemCameraTransforms.DEFAULT;
        }

        @Override
        public IBakedModel handleBlockState(IBlockState state) {
            return new CustomModel(base, (IExtendedBlockState) state);
        }

        @Override
        public IBakedModel handleItemState(ItemStack stack) {
            IExtendedBlockState itemState = ((IExtendedBlockState) Rollercoaster2Forge.handler.blockHashMap.get(rcBlock).getDefaultState());
            return new CustomModel(base, itemState);
        }
    }
}
