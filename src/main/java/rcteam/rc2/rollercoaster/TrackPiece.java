package rcteam.rc2.rollercoaster;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraftforge.fml.common.registry.LanguageRegistry;

import javax.vecmath.Vector3f;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public enum TrackPiece {
	STRAIGHT("straight", new Vector3f(1f, 1f, 1f), null),
	SMALL_CORNER("small_corner", new Vector3f(2f, 1f, 2f), null),
	MEDIUM_CORNER("medium_corner", new Vector3f(3f, 1f, 3f), null),
	LARGE_CORNER_LEFT("large_corner_left", new Vector3f(2f, 1f, 2f), new Vector3f(0f, 0f, -1f)),
	LARGE_CORNER_RIGHT("large_corner_right", new Vector3f(2f, 1f, 2f), null);

	public final String name;
	public final Vector3f size;
	public final Vector3f offset;
	private final Map<String, CoasterStyle> styleMap = Maps.newHashMap();

	TrackPiece(String name, Vector3f size, Vector3f offset) {
		this.name = name;
		if (size.x >= 0.5f && size.y >= 0.5f && size.z >= 0.5f) this.size = size;
		else this.size = new Vector3f(1f, 1f, 1f);
		if (offset != null && offset.x >= 0f && offset.y >= 0f && offset.z >= 0f) this.offset = offset;
		else this.offset = new Vector3f(0f, 0f, 0f);
	}

	public String getDisplayName() {
		return LanguageRegistry.instance().getStringLocalization("piece." + this.name);
	}

	public static TrackPiece getByName(String name) {
		return Arrays.asList(values()).stream().filter(trackPiece -> trackPiece.name.equals(name)).findFirst().get();
	}

	public void addStyle(CoasterStyle style) {
		if (style != null) {
			this.styleMap.put(style.getName(), style);
		}
	}

	public CoasterStyle getStyle(String name) {
		return this.styleMap.get(name);
	}

	public List<String> getStyleNames() {
		return Lists.newArrayList(this.styleMap.keySet());
	}

	public List<CoasterStyle> getStyles() {
		return Lists.newArrayList(this.styleMap.values());
	}
}
