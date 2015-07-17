package forge;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import rollercoasterteam.rollercoaster2.core.BlockPosition;
import rollercoasterteam.rollercoaster2.core.api.BaseAPIProxy;
import rollercoasterteam.rollercoaster2.core.api.block.RCBlock;

public class BlockConverter extends Block {

   public RCBlock rcBlock;

    public int renderID = 0;

    public BlockConverter(RCBlock rcBlock) {
        super(Material.iron);
        setBlockName(rcBlock.getName());
        setCreativeTab(CreativeTabs.tabAllSearch);
        this.rcBlock = rcBlock;
    }


    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitx, float hity, float hitz) {
        return rcBlock.onActivated(BaseAPIProxy.API.getWorld(world.provider.dimensionId), new BlockPosition(x, y, z));
    }


    @SideOnly(Side.CLIENT)
    private IIcon iconUp;

    @SideOnly(Side.CLIENT)
    private IIcon iconDown;

    @SideOnly(Side.CLIENT)
    private IIcon iconNorth;

    @SideOnly(Side.CLIENT)
    private IIcon iconSouth;

    @SideOnly(Side.CLIENT)
    private IIcon iconEast;

    @SideOnly(Side.CLIENT)
    private IIcon iconWest;


    @Override
    public void registerBlockIcons(IIconRegister register) {
        super.registerBlockIcons(register);
        this.iconUp = register.registerIcon(rcBlock.getTexturesWithFaces().getUp().textureName());
        this.iconDown = register.registerIcon(rcBlock.getTexturesWithFaces().getDown().textureName());
        this.iconNorth = register.registerIcon(rcBlock.getTexturesWithFaces().getNorth().textureName());
        this.iconSouth = register.registerIcon(rcBlock.getTexturesWithFaces().getSouth().textureName());
        this.iconEast = register.registerIcon(rcBlock.getTexturesWithFaces().getEast().textureName());
        this.iconWest = register.registerIcon(rcBlock.getTexturesWithFaces().getWest().textureName());
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if(side == 0){
            return this.iconDown;
        }
        if(side == 1){
            return this.iconUp;
        }
        if(side == 2){
            return this.iconNorth;
        }
        if(side == 3){
            return this.iconSouth;
        }
        if(side == 4){
            return this.iconWest;
        }
        if(side == 5){
            return this.iconEast;
        }
        return super.getIcon(side, meta);
    }

    @Override
    public int getRenderType() {
        return renderID;
    }
}
