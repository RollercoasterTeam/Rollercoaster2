package rcteam.rc2.rollercoaster;

import net.minecraft.util.ResourceLocation;
import rcteam.rc2.RC2;

import java.util.ArrayList;

public class ThemeParkLogo {
	public static ArrayList<ResourceLocation> bgs = new ArrayList<ResourceLocation>();
	public static ArrayList<ResourceLocation> fgs = new ArrayList<ResourceLocation>();
	public static ArrayList<ResourceLocation> texts = new ArrayList<ResourceLocation>();
	
	public int bg;
	public int fg;
	public int text;
	
	public int bgColour;
	public int fgColour;
	public int textColour;
	
	public static void init() {
		for(int i = 0; i < 4; i++) {
			bgs.add(new ResourceLocation(RC2.MODID + ":" + "textures/gui/logo/bg/" + i + ".png"));
		}
		
		for(int i = 0; i < 1; i++) {
			fgs.add(new ResourceLocation(RC2.MODID + ":" + "textures/gui/logo/fg/" + i + ".png"));
		}
		
		for(int i = 0; i < 1; i++) {
			texts.add(new ResourceLocation(RC2.MODID + ":" + "textures/gui/logo/text/" + i + ".png"));
		}
	}
	
}
