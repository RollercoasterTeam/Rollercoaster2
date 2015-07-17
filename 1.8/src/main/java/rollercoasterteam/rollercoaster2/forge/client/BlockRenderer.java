package rollercoasterteam.rollercoaster2.forge.client;

import com.google.common.primitives.Ints;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rollercoasterteam.rollercoaster2.core.ModInfo;
import rollercoasterteam.rollercoaster2.core.api.block.RCBlock;
import rollercoasterteam.rollercoaster2.forge.Rollercoaster2Forge;
import sun.reflect.generics.scope.DummyScope;

import javax.vecmath.Vector3f;
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
        TextureAtlasSprite base = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(rcBlock.getTexture().replace(":", ":blocks/"));
        IBakedModel customModel = new CustomModel(base);
        event.modelRegistry.putObject(blockLocation, customModel);
    }


    public static class CustomModel implements IFlexibleBakedModel, ISmartBlockModel, ISmartItemModel {
        private final TextureAtlasSprite base;
        private RCBlock rcBlock;
        protected static FaceBakery faceBakery = new FaceBakery();

        public CustomModel(TextureAtlasSprite base) {
            this(base, null);
        }

        public CustomModel(TextureAtlasSprite base, IBlockState state) {
            this.base = base;
        }

        @Override
        public List<BakedQuad> getFaceQuads(EnumFacing side) {
            return Collections.emptyList();
        }

        @Override
        public List<BakedQuad> getGeneralQuads() {
            ArrayList<BakedQuad> list = new ArrayList<BakedQuad>();
            BlockFaceUV uv = new BlockFaceUV(new float[]{0.0F, 0.0F, 16.0F, 16.0F}, 0);
            BlockPartFace face = new BlockPartFace(null, 0, "", uv);

            ModelRotation modelRot = ModelRotation.X0_Y0;
            boolean scale = true;

            list.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(16.0F, 0.0F, 16.0F), face, base, EnumFacing.DOWN, modelRot, null, scale, true));
            list.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 16.0F, 0.0F), new Vector3f(16.0F, 16.0F, 16.0F), face, base, EnumFacing.UP, modelRot, null, scale, true));
            list.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(16.0F, 16.0F, 0.0F), face, base, EnumFacing.NORTH, modelRot, null, scale, true));
            list.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 16.0F), new Vector3f(16.0F, 16.0F, 16.0F), face, base, EnumFacing.SOUTH, modelRot, null, scale, true));
            list.add(faceBakery.makeBakedQuad(new Vector3f(16.0F, 0.0F, 0.0F), new Vector3f(16.0F, 16.0F, 16.0F), face, base, EnumFacing.EAST, modelRot, null, scale, true));
            list.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(0.0F, 16.0F, 16.0F), face, base, EnumFacing.WEST, modelRot, null, scale, true));

            return list;
        }

        @Override
        public VertexFormat getFormat() {
            return Attributes.DEFAULT_BAKED_FORMAT;
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

        public static ItemTransformVec3f MovedUp = new ItemTransformVec3f(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(-0.05F, 0.05F, -0.15F), new Vector3f(-0.5F, -0.5F, -0.5F));
        @Override
        public ItemCameraTransforms getItemCameraTransforms() {
            return new ItemCameraTransforms(MovedUp, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT);
        }

        @Override
        public IBakedModel handleBlockState(IBlockState state) {
            return new CustomModel(base, state);
        }

        @Override
        public IBakedModel handleItemState(ItemStack stack) {
            IExtendedBlockState itemState = ((IExtendedBlockState) Rollercoaster2Forge.handler.blockHashMap.get(rcBlock).getDefaultState());
            return new CustomModel(base, itemState);
        }
    }
}
