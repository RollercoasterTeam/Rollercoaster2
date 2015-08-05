package rcteam.rc2.rollercoaster;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import net.minecraftforge.client.model.IModelCustom;
import rcteam.rc2.RC2;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StyleRegistry {
	public static StyleRegistry INSTANCE = new StyleRegistry();
	private Map<CategoryEnum, Map<String, CoasterStyle>> registry = Maps.newEnumMap(CategoryEnum.class);
	private Map<CategoryEnum, Map<String, IModelCustom>> modelRegistry = Maps.newEnumMap(CategoryEnum.class);

	private StyleRegistry() {
		Lists.newArrayList(CategoryEnum.values()).forEach(categoryEnum -> this.registry.put(categoryEnum, Maps.newHashMap()));
		Lists.newArrayList(CategoryEnum.values()).forEach(categoryEnum -> this.modelRegistry.put(categoryEnum, Maps.newHashMap()));
	}

	public Map<CategoryEnum, Map<String, CoasterStyle>> getRegistry() {
		return this.registry;
	}

	public Map<CategoryEnum, Map<String, IModelCustom>> getModelRegistry() {
		return this.modelRegistry;
	}

	public void register(CoasterStyle style) {
		this.register(style.getName(), style, null);
	}

	public void register(CoasterStyle style, IModelCustom obj) {
		this.register(style.getName(), style, obj);
	}

	public void register(String name, CoasterStyle style) {
		this.register(name, style, null);
	}

	@SuppressWarnings("unchecked")
	public void register(String name, CoasterStyle style, IModelCustom obj) {
//		if (style != null) {
//			this.registry.put(style.getCategory(), (Map<String, CoasterStyle>) Maps.newHashMap().put(name, style));
//			this.modelRegistry.put(style.getCategory(), (Map<String, IModelCustom>) Maps.newHashMap().put(name, obj));
//			RC2.logger.info(String.format("Style registered: %s", name));
//		}
		if (style != null) {
			this.registry.get(style.getCategory()).put(name, style);
			this.modelRegistry.get(style.getCategory()).put(name, obj);
			RC2.logger.info(String.format("Style registered: %s", name));
		}
	}

	public boolean replaceStyleInRegistry(String name, CoasterStyle replacement) {
		if (!name.equals(replacement.getName()) || !hasNameBeenRegistered(name)) return false;
		registry.values().stream().filter(stringCoasterStyleMap -> stringCoasterStyleMap.containsKey(name)).findAny().get().replace(name, replacement);
		return true;
	}

	public boolean replaceModelInRegistry(String name, IModelCustom replacement) {
		if (!hasNameBeenRegistered(name)) return false;
		modelRegistry.values().stream().filter(stringFileMap -> stringFileMap.containsKey(name)).findAny().get().replace(name, replacement);
		return true;
	}

	public Map<String, CoasterStyle> getStylesFromCategory(CategoryEnum category) {
		return registry.get(category);
	}

	public Map<String, IModelCustom> getModelsFromCategory(CategoryEnum category) {
		return modelRegistry.get(category);
	}

	public boolean hasNameBeenRegistered(String name) {
		return registry.values().stream().anyMatch(stringCoasterStyleMap -> stringCoasterStyleMap.containsKey(name)) || modelRegistry.values().stream().anyMatch(stringFileMap -> stringFileMap.containsKey(name));
	}

	public CoasterStyle getStyleByName(String name) {
		return hasNameBeenRegistered(name) ? registry.values().stream().filter(stringCoasterStyleMap -> stringCoasterStyleMap.containsKey(name)).findFirst().get().get(name) : null;
	}

	public IModelCustom getModelByName(String name) {
		return hasNameBeenRegistered(name) ? modelRegistry.values().stream().filter(stringFileMap -> stringFileMap.containsKey(name)).findFirst().get().get(name) : null;
	}

	public void registerBlocks() {
//		this.registry.forEach((categoryEnum, stringCoasterStyleMap) -> stringCoasterStyleMap.forEach((s, style) -> style.registerBlocks()));
		for (CategoryEnum categoryEnum : CategoryEnum.values()) {
			if (this.registry.containsKey(categoryEnum)) {
				for (CoasterStyle style : this.registry.get(categoryEnum).values()) {
					style.registerBlocks();
				}
			}
		}
	}
}
