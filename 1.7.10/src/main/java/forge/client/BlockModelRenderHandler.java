package forge.client;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import forge.BlockConverter;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;
import rollercoasterteam.rollercoaster2.core.api.textures.model.IModeledBlock;
import rollercoasterteam.rollercoaster2.core.api.textures.model.ModelPart;
import rollercoasterteam.rollercoaster2.core.api.textures.model.vecMath.Vecs3dCube;

public class BlockModelRenderHandler implements ISimpleBlockRenderingHandler {

    BlockConverter blockConverter;

    public BlockModelRenderHandler(BlockConverter blockConverter) {
        this.blockConverter = blockConverter;
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        Tessellator tessellator = Tessellator.instance;

        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

        tessellator.startDrawingQuads();
        if(blockConverter.rcBlock instanceof IModeledBlock){
            IModeledBlock modeledBlock = (IModeledBlock) blockConverter.rcBlock;
            for(ModelPart cube : modeledBlock.getModel().cubes){
                renderBox(cube.getCube(), block, Tessellator.instance, renderer, Blocks.cobblestone.getIcon(0, 0), 0, 0, 0);
            }
        }
        tessellator.draw();
        block.setBlockBoundsForItemRender();
        renderer.setRenderBoundsFromBlock(block);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        if(blockConverter.rcBlock instanceof IModeledBlock){
            IModeledBlock modeledBlock = (IModeledBlock) blockConverter.rcBlock;
            for(ModelPart cube : modeledBlock.getModel().cubes){
                renderBox(cube.getCube(), block, Tessellator.instance, renderer, Blocks.cobblestone.getIcon(0, 0), x, y, z);
            }
        }
        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        renderer.setRenderBoundsFromBlock(block);
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return blockConverter.getRenderType();
    }

    public static void renderBox(Vecs3dCube cube, Block block, Tessellator tessellator, RenderBlocks renderblocks, IIcon texture, double xD, double yD, double zD) {
        block.setBlockBounds((float) cube.getMinX() /16, (float) cube.getMinY()/16, (float) cube.getMinZ()/16, (float) cube.getMaxX()/16, (float) cube.getMaxY()/16 , (float) cube.getMaxZ()/16);
        renderblocks.setRenderBoundsFromBlock(block);
        tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
        renderblocks.renderFaceYNeg(block, xD, yD, zD, texture);
        tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        renderblocks.renderFaceYPos(block, xD, yD, zD, texture);
        tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
        renderblocks.renderFaceZNeg(block, xD, yD, zD, texture);
        renderblocks.renderFaceZPos(block, xD, yD, zD, texture);
        tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
        renderblocks.renderFaceXNeg(block, xD, yD, zD, texture);
        renderblocks.renderFaceXPos(block, xD, yD, zD, texture);
    }
}
