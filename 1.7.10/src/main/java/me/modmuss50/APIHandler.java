package me.modmuss50;

import me.modmuss50.abLayer.api.IApiHandler;
import me.modmuss50.abLayer.api.IXMod;
import me.modmuss50.abLayer.api.block.Block;

public class APIHandler implements IApiHandler {
    @Override
    public void registerBlock(Block block) {
        System.out.println("Registering block");
    }

    @Override
    public void modContrcution(IXMod mod) {

    }
}
