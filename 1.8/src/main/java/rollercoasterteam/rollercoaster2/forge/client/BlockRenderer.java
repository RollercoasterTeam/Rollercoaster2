package rollercoasterteam.rollercoaster2.forge.client;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
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
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rollercoasterteam.rollercoaster2.core.ModInfo;
import rollercoasterteam.rollercoaster2.core.api.block.RCBlock;
import rollercoasterteam.rollercoaster2.core.api.textures.model.IModeledBlock;
import rollercoasterteam.rollercoaster2.core.api.textures.model.ModelPart;
import rollercoasterteam.rollercoaster2.forge.Rollercoaster2Forge;

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
        TextureAtlasSprite up = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(rcBlock.getTexturesWithFaces().getUp().textureName().replace(":", ":blocks/"));
        TextureAtlasSprite down = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(rcBlock.getTexturesWithFaces().getDown().textureName().replace(":", ":blocks/"));
        TextureAtlasSprite north = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(rcBlock.getTexturesWithFaces().getNorth().textureName().replace(":", ":blocks/"));
        TextureAtlasSprite south = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(rcBlock.getTexturesWithFaces().getSouth().textureName().replace(":", ":blocks/"));
        TextureAtlasSprite east = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(rcBlock.getTexturesWithFaces().getEast().textureName().replace(":", ":blocks/"));
        TextureAtlasSprite west = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(rcBlock.getTexturesWithFaces().getWest().textureName().replace(":", ":blocks/"));

        IBakedModel customModel = new CustomModel(up, down, north, south, east, west, rcBlock);
        event.modelRegistry.putObject(blockLocation, customModel);

        ItemModelMesher itemModelMesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
        event.modelRegistry.putObject(itemLocation, new CustomModel(up, down, north, south, east, west, rcBlock));
        itemModelMesher.register(Item.getItemFromBlock(Rollercoaster2Forge.handler.blockHashMap.get(rcBlock)), 0, itemLocation);
    }


    public static class CustomModel implements IFlexibleBakedModel, ISmartBlockModel, ISmartItemModel {
        private final TextureAtlasSprite up;
        private final TextureAtlasSprite down;
        private final TextureAtlasSprite north;
        private final TextureAtlasSprite south;
        private final TextureAtlasSprite east;
        private final TextureAtlasSprite west;

        protected static FaceBakery faceBakery = new FaceBakery();

        RCBlock rcBlock;

        public CustomModel(TextureAtlasSprite up, TextureAtlasSprite down, TextureAtlasSprite north, TextureAtlasSprite south, TextureAtlasSprite east, TextureAtlasSprite west, RCBlock block) {
            this.up = up;
            this.down = down;
            this.north = north;
            this.south = south;
            this.east = east;
            this.west = west;
            this.rcBlock = block;
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
            if(rcBlock instanceof IModeledBlock){
                for(ModelPart part : ((IModeledBlock) rcBlock).getModel().cubes){
                    addCube(part, list, face, modelRot);
                }
            } else {

                list.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(16.0F, 0.0F, 16.0F), face, down, EnumFacing.DOWN, modelRot, null, scale, true));//down
                list.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 16.0F, 0.0F), new Vector3f(16.0F, 16.0F, 16.0F), face, up, EnumFacing.UP, modelRot, null, scale, true));//up
                list.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(16.0F, 16.0F, 0.0F), face, north, EnumFacing.NORTH, modelRot, null, scale, true));//north
                list.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 16.0F), new Vector3f(16.0F, 16.0F, 16.0F), face, south, EnumFacing.SOUTH, modelRot, null, scale, true));//south
                list.add(faceBakery.makeBakedQuad(new Vector3f(16.0F, 0.0F, 0.0F), new Vector3f(16.0F, 16.0F, 16.0F), face, east, EnumFacing.EAST, modelRot, null, scale, true));//east
                list.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(0.0F, 16.0F, 16.0F), face, west, EnumFacing.WEST, modelRot, null, scale, true));//west

            }
            return list;
        }

        //TODO draw block from the cube
        public void addCube(ModelPart part, ArrayList<BakedQuad> list, BlockPartFace face, ModelRotation modelRotation){
            list.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(16.0F, 0.0F, 16.0F), face, down, EnumFacing.DOWN, modelRotation, null, true, true));//down
            list.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 16.0F, 0.0F), new Vector3f(16.0F, 16.0F, 16.0F), face, up, EnumFacing.UP, modelRotation, null, true, true));//up
            list.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(16.0F, 16.0F, 0.0F), face, north, EnumFacing.NORTH, modelRotation, null, true, true));//north
            list.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 16.0F), new Vector3f(16.0F, 16.0F, 16.0F), face, south, EnumFacing.SOUTH, modelRotation, null, true, true));//south
            list.add(faceBakery.makeBakedQuad(new Vector3f(16.0F, 0.0F, 0.0F), new Vector3f(16.0F, 16.0F, 16.0F), face, east, EnumFacing.EAST, modelRotation, null, true, true));//east
            list.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(0.0F, 16.0F, 16.0F), face, west, EnumFacing.WEST, modelRotation, null, true, true));//west
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
            return this.north;
        }

        public static ItemTransformVec3f MovedUp = new ItemTransformVec3f(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(-0.05F, 0.05F, -0.15F), new Vector3f(-0.5F, -0.5F, -0.5F));
        @Override
        public ItemCameraTransforms getItemCameraTransforms() {
            return new ItemCameraTransforms(MovedUp, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT);
        }

        @Override
        public IBakedModel handleBlockState(IBlockState state) {
            return new CustomModel(up, down, north, south, east, west, rcBlock);
        }

        @Override
        public IBakedModel handleItemState(ItemStack stack) {
            return new CustomModel(up, down, north, south, east, west, rcBlock);
        }
    }
}