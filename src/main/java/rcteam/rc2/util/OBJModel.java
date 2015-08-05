package rcteam.rc2.util;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.ModelFormatException;
import org.lwjgl.opengl.GL11;
import rcteam.rc2.RC2;

import javax.print.DocFlavor;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;
import java.io.*;
import java.util.*;

public class OBJModel implements IModelCustom {
	private String fileName;
	private InputStreamReader objStream;
	private BufferedReader objReader;
//	private ResourceLocation objFrom;
//	private File objFrom;
	public MaterialLibrary materialLibrary = new MaterialLibrary();

	private List<String> groupList = new ArrayList<>();
	private List<Vertex> vertices = new ArrayList<>();
	private List<Normal> normals = new ArrayList<>();
	private List<TextureCoordinate> texCoords = new ArrayList<>();

	private float minU = 0f;
	private float maxU = 1f;
	private float minV = 0f;
	private float maxV = 1f;

//	public OBJModel(ResourceLocation from) throws ModelFormatException {
//		this.fileName = from.toString();
//		this.objFrom = from;
//		try {
//			IResource resource = Minecraft.getMinecraft().getResourceManager().getResource(from);
//			this.objStream = new InputStreamReader(resource.getInputStream(), Charsets.UTF_8);
//			this.objReader = new BufferedReader(objStream);
//			parseModel();
//		} catch (IOException e) {
//			throw new ModelFormatException("IO Exception reading model format", e);
//		}
//	}

	public OBJModel(FileInputStream fromStream, String fileName) throws ModelFormatException {
//	public OBJModel(File from, String fileName) throws ModelFormatException {
		this.fileName = fileName;
//		this.objFrom = from;
		try {
//			this.objStream = new InputStreamReader(new FileInputStream(from), Charsets.UTF_8);
			this.objStream = new InputStreamReader(fromStream);
			this.objReader = new BufferedReader(objStream);
			parseModel();
		} catch (IOException e) {
			throw new ModelFormatException("IO Exception reading model format", e);
		}
	}

	private void parseModel() throws IOException {
		String currentLine = "";
		Material material = new Material();
		int usemtlCounter = 0;

		for (;;) {
			currentLine = objReader.readLine();
			if (currentLine == null) break;
			if (currentLine.isEmpty() || currentLine.startsWith("#")) continue;

			String[] fields = currentLine.split(" ", 2);
			String key = fields[0];
			String data = fields[1];

			if (key.equalsIgnoreCase("mtllib")) {
//				File mtlFrom = Lists.newArrayList(this.objFrom.getParentFile().listFiles()).stream().filter(file -> file.getName().equals("model.mtl")).findFirst().get();
//				this.materialLibrary.parseMaterials(data, mtlFrom);
			} else if (key.equalsIgnoreCase("usemtl")) {
				material = this.materialLibrary.materials.get(data);
				usemtlCounter++;
			} else if (key.equalsIgnoreCase("v")) {
				String[] splitData = data.split(" ");
				float[] floatSplitData = new float[splitData.length];
				for (int i = 0; i < splitData.length; i++) {
					floatSplitData[i] = Float.parseFloat(splitData[i]);
				}
				Vector4f pos = new Vector4f(floatSplitData[0], floatSplitData[1], floatSplitData[2], floatSplitData.length == 4 ? floatSplitData[3] : 1);
				Vertex vertex = new Vertex(pos, material);
				this.vertices.add(vertex);
			} else if (key.equalsIgnoreCase("vn")) {
				String[] splitData = data.split(" ");
				float[] floatSplitData = new float[splitData.length];
				for (int i = 0; i < splitData.length; i++) {
					floatSplitData[i] = Float.parseFloat(splitData[i]);
				}
				Normal normal = new Normal(new Vector3f(floatSplitData[0], floatSplitData[1], floatSplitData[2]));
				this.normals.add(normal);
			} else if (key.equalsIgnoreCase("vt")) {
				String[] splitData = data.split(" ");
				float[] floatSplitData = new float[splitData.length];
				for (int i = 0; i < splitData.length; i++) {
					floatSplitData[i] = Float.parseFloat(splitData[i]);
				}
				TextureCoordinate texCoord = new TextureCoordinate(new Vector3f(floatSplitData[0], floatSplitData[1], floatSplitData.length == 3 ? floatSplitData[2] : 1));
				this.minU = floatSplitData[0] < this.minU ? floatSplitData[0] : this.minU;
				this.maxU = floatSplitData[0] > this.maxU ? floatSplitData[0] : this.maxU;
				this.minV = floatSplitData[1] < this.minV ? floatSplitData[1] : this.minV;
				this.maxV = floatSplitData[1] > this.maxV ? floatSplitData[1] : this.maxV;
				this.texCoords.add(texCoord);
			} else if (key.equalsIgnoreCase("f")) {
				String[] splitSpace = data.split(" ");
				String[][] splitSlash = new String[splitSpace.length][];

				int vert = 0;
				int norm = 0;
				int texCoord = 0;

				List<Vertex> v = new ArrayList<>(splitSpace.length);
				List<Normal> n = new ArrayList<>(splitSpace.length);
				List<TextureCoordinate> t = new ArrayList<>(splitSpace.length);

				for (int i = 0; i < splitSpace.length; i++) {
					if (splitSpace[i].contains("//")) {
						splitSlash[i] = splitSpace[i].split("//");

						vert = Integer.parseInt(splitSlash[i][0]);
						vert = vert < 0 ? this.vertices.size() - 1 : vert - 1;
						norm = Integer.parseInt(splitSlash[i][1]);
						norm = norm < 0 ? this.normals.size() - 1 : norm - 1;

						v.add(this.vertices.get(vert));
						n.add(this.normals.get(norm));
					} else if (splitSpace[i].contains("/")) {
						splitSlash[i] = splitSpace[i].split("/");

						vert = Integer.parseInt(splitSlash[i][0]);
						vert = vert < 0 ? this.vertices.size() - 1 : vert - 1;
						texCoord = Integer.parseInt(splitSlash[i][1]);
						texCoord = texCoord < 0 ? this.texCoords.size() - 1 : texCoord - 1;
						if (splitSlash[i].length > 2) {
							norm = Integer.parseInt(splitSlash[i][2]);
							norm = norm < 0 ? this.normals.size() - 1 : norm - 1;
						}

						v.add(this.vertices.get(vert));
						t.add(this.texCoords.get(texCoord));
						if (splitSlash[i].length > 2) n.add(this.normals.get(norm));
					} else {
						splitSlash[i] = splitSpace[i].split("");

						vert = Integer.parseInt(splitSlash[i][0]);
						vert = vert < 0 ? this.vertices.size() - 1 : vert - 1;

						v.add(this.vertices.get(vert));
					}
				}

				Vertex[] va = new Vertex[v.size()];
				v.toArray(va);
				Normal[] na = new Normal[n.size()];
				n.toArray(na);
				TextureCoordinate[] ta = new TextureCoordinate[t.size()];
				t.toArray(ta);
				Face face = new Face(va, na, ta, material.name);
				if (usemtlCounter < this.vertices.size()) {
					for (Vertex vertex : face.getVertices()) {
						vertex.setMaterial(material);
					}
				}

				if (groupList.isEmpty()) {
					if (this.materialLibrary.getGroups().containsKey(Group.DEFAULT_NAME)) {
						this.materialLibrary.getGroups().get(Group.DEFAULT_NAME).addFace(face);
					} else {
						Group def = new Group(Group.DEFAULT_NAME, null);
						def.addFace(face);
						this.materialLibrary.getGroups().put(Group.DEFAULT_NAME, def);
					}
				} else {
					for (String s : groupList) {
						if (this.materialLibrary.getGroups().containsKey(s)) {
							this.materialLibrary.getGroups().get(s).addFace(face);
						} else {
							Group g = new Group(s, null);
							g.addFace(face);
							this.materialLibrary.getGroups().put(s, g);
						}
					}
				}
			} else if (key.equalsIgnoreCase("g") || key.equalsIgnoreCase("o")) {
				groupList.clear();
				if (key.equalsIgnoreCase("g")) {
					String[] splitSpace = data.split(" ");
					for (String s : splitSpace) {
						groupList.add(s);
					}
				} else {
					groupList.add(data);
				}
			}
		}
		this.materialLibrary.setUVBounds(minU, maxU, minV, maxV);
	}

	@Override
	public String getType() {
		return "obj";
	}

	@Override
	public void renderAll() {
		for (Group group : this.materialLibrary.getGroups().values()) {
			group.render();
		}
	}

	@Override
	public void renderOnly(String... groupNames) {
		for (Map.Entry<String, Group> entry : this.materialLibrary.getGroups().entrySet()) {
			if (Lists.newArrayList(groupNames).contains(entry.getKey())) {
				entry.getValue().render();
			}
		}
	}

	@Override
	public void renderPart(String partName) {
		this.materialLibrary.getGroups().get(partName).render();
	}

	@Override
	public void renderAllExcept(String... excludedGroupNames) {
		for (Map.Entry<String, Group> entry : this.materialLibrary.getGroups().entrySet()) {
			if (!Lists.newArrayList(excludedGroupNames).contains(entry.getKey())) {
				entry.getValue().render();
			}
		}
	}

	public static class MaterialLibrary {
		private Map<String, Material> materials = new HashMap<>();
		private Map<String, Group> groups = new HashMap<>();
		private InputStreamReader mtlStream;
		private BufferedReader mtlReader;

		private float minU = 0f;
		private float maxU = 1f;
		private float minV = 0f;
		private float maxV = 1f;

		public MaterialLibrary() {
			this.groups.put(Group.DEFAULT_NAME, new Group(Group.DEFAULT_NAME, null));
		}

		public float getMinU() {
			return this.minU;
		}

		public float getMaxU() {
			return this.maxU;
		}

		public float getMinV() {
			return this.minV;
		}

		public float getMaxV() {
			return this.maxV;
		}

		public void setUVBounds(float minU, float maxU, float minV, float maxV) {
			this.minU = minU;
			this.maxU = maxU;
			this.minV = minV;
			this.maxV = maxV;
		}

		public Map<String, Group> getGroups() {
			return this.groups;
		}

		public List<Group> getGroupsContainingFace(Face face) {
			List<Group> groups = new ArrayList<>();
			for (Group group : this.groups.values()) {
				if (group.faces.contains(face)) groups.add(group);
			}
			return groups;
		}

		public void changeMaterialColor(String name, int color) {
			Vector4f colorVec = new Vector4f((color >> 16 & 255) / 255, (color >> 8 & 255) / 255, (color & 255) / 255, (color >> 24 & 255) / 255);
			this.materials.get(name).setColor(colorVec);
		}

		public Material getMaterial(String name) {
			return this.materials.get(name);
		}

		public ImmutableList<String> getMaterialNames() {
			return ImmutableList.copyOf(this.materials.keySet());
		}

		public void parseMaterials(String path, FileInputStream fromStream) throws IOException {
//		public void parseMaterials(String path, File from) throws IOException {
			this.materials.clear();
			boolean hasSetTexture = false;
			boolean hasSetColor = false;
//			String domain = from.getResourceDomain();
//			mtlStream = new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(domain, path)).getInputStream(), Charsets.UTF_8);
//			mtlReader = new BufferedReader(mtlStream);
//			this.mtlStream = new InputStreamReader(newFileInputStream(from), Charsets.UTF_8);
			this.mtlStream = new InputStreamReader(fromStream);
			this.mtlReader = new BufferedReader(this.mtlStream);

			String currentLine = "";
			Material material = new Material();
			material.setName(Material.WHITE_NAME);
			material.setTexture(Texture.White);
			this.materials.put(Material.WHITE_NAME, material);
			this.materials.put(Material.DEFAULT_NAME, new Material(Texture.White));

			for (;;) {
				currentLine = mtlReader.readLine();
				if (currentLine == null) break;
				if (currentLine.isEmpty() || currentLine.startsWith("#")) continue;

				String[] fields = currentLine.split(" ", 2);
				String key = fields[0];
				String data = fields[1];

				if (key.equalsIgnoreCase("newmtl")) {
					hasSetColor = false;
					hasSetTexture = false;
					material = new Material();
					material.setName(data);
					this.materials.put(data, material);
				} else if (key.equalsIgnoreCase("Ka") || key.equalsIgnoreCase("Kd") || key.equalsIgnoreCase("Ks")) {
					if (key.equalsIgnoreCase("Kd") || !hasSetColor) {
						String[] rgbStrings = data.split(" ", 3);
						Vector4f color = new Vector4f(Float.parseFloat(rgbStrings[0]), Float.parseFloat(rgbStrings[1]), Float.parseFloat(rgbStrings[2]), 1.0f);
						hasSetColor = true;
						material.setColor(color);
					}
				} else if (key.equalsIgnoreCase("map_Ka") || key.equalsIgnoreCase("map_Kd") || key.equalsIgnoreCase("map_Ks")) {
					if (key.equalsIgnoreCase("map_Kd") || !hasSetTexture) {
						if (data.contains(" ")) {
							String[] mapStrings = data.split(" ");
							String texturePath = mapStrings[mapStrings.length - 1];
							Texture texture = new Texture(texturePath);
							hasSetTexture = true;
							material.setTexture(texture);
						} else {
							Texture texture = new Texture(data);
							hasSetTexture = true;
							material.setTexture(texture);
						}
					}
				}
			}
		}
	}

	public static class Material {
		public static final String WHITE_NAME = "OBJModel.White.Texture.Name";
		public static final String DEFAULT_NAME = "OBJModel.Default.Texture.Name";
		private Vector4f color;
		private Texture texture = Texture.White;
		private String name = DEFAULT_NAME;

		public Material() {
			this(new Vector4f(1f, 1f, 1f, 1f));
		}

		public Material(Vector4f color) {
			this(color, Texture.White, WHITE_NAME);
		}

		public Material(Texture texture) {
			this(new Vector4f(1f, 1f, 1f, 1f), texture, DEFAULT_NAME);
		}

		public Material(Vector4f color, Texture texture, String name) {
			this.color = color;
			this.texture = texture;
			this.name = name != null ? name : DEFAULT_NAME;
		}

		public void setName(String name) {
			this.name = name != null ? name : DEFAULT_NAME;
		}

		public String getName() {
			return this.name;
		}

		public void setColor(Vector4f color) {
			this.color = color;
		}

		public Vector4f getColor() {
			return this.color;
		}

		public void setTexture(Texture texture) {
			this.texture = texture;
		}

		public Texture getTexture() {
			return this.texture;
		}

		public boolean isWhite() {
			return this.texture.equals(Texture.White);
		}

		@Override
		public String toString()
		{
			StringBuilder builder = new StringBuilder(String.format("%nMaterial:%n"));
			builder.append(String.format("    Name: %s%n", this.name));
			builder.append(String.format("    Color: %s%n", this.color.toString()));
			builder.append(String.format("    Is White: %b%n", this.isWhite()));
			return builder.toString();
		}
	}

	public static class Texture {
		public static Texture White = new Texture("builtin/white", new Vector2f(0, 0), new Vector2f(1, 1), 0);
		private String path;
		private Vector2f position;
		private Vector2f scale;
		private float rotation;

		public Texture(String path) {
			this(path, new Vector2f(0, 0), new Vector2f(1, 1), 0);
		}

		public Texture(String path, Vector2f position, Vector2f scale, float rotation) {
			this.path = path;
			this.position = position;
			this.scale = scale;
			this.rotation = rotation;
		}

		public ResourceLocation getTextureLocation() {
			return new ResourceLocation(this.path);
		}

		public void setPath(String path) {
			this.path = path;
		}

		public String getPath() {
			return this.path;
		}

		public void setPosition(Vector2f position) {
			this.position = position;
		}

		public Vector2f getPosition() {
			return this.position;
		}

		public void setRotation(float rotation) {
			this.rotation = rotation;
		}

		public float getRotation() {
			return this.rotation;
		}
	}

	public static class Face {
		private Vertex[] verts = new Vertex[4];
		private Normal[] norms = new Normal[4];
		private TextureCoordinate[] texCoords = new TextureCoordinate[4];
		private String materialName = Material.DEFAULT_NAME;

		public Face(Vertex[] verts) {
			this(verts, null, null);
		}

		public Face(Vertex[] verts, Normal[] norms) {
			this(verts, norms, null);
		}

		public Face(Vertex[] verts, TextureCoordinate[] texCoords) {
			this(verts, null, texCoords);
		}

		public Face(Vertex[] verts, Normal[] norms, TextureCoordinate[] texCoords) {
			this(verts, norms, texCoords, Material.DEFAULT_NAME);
		}

		public Face(Vertex[] verts, Normal[] norms, TextureCoordinate[] texCoords, String materialName) {
			this.verts = verts;
			this.verts = this.verts != null && this.verts.length > 0 ? this.verts : null;
			this.norms = norms;
			this.norms = this.norms != null && this.norms.length > 0 ? this.norms : null;
			this.texCoords = texCoords;
			this.texCoords = this.texCoords != null && this.texCoords.length > 0 ? this.texCoords : null;
			setMaterialName(materialName);
			ensureQuads();
		}

		private void ensureQuads() {
			if (this.verts != null && this.verts.length == 3) {
				this.verts = new Vertex[] {this.verts[0], this.verts[1], this.verts[2], this.verts[2]};
			}

			if (this.norms != null && this.norms.length == 3) {
				this.norms = new Normal[] {this.norms[0], this.norms[1], this.norms[2], this.norms[2]};
			}

			if (this.texCoords != null && this.texCoords.length == 3) {
				this.texCoords = new TextureCoordinate[] {this.texCoords[0], this.texCoords[1], this.texCoords[2], this.texCoords[2]};
			}
		}

		public void setMaterialName(String materialName) {
			this.materialName = materialName != null && !materialName.isEmpty() ? materialName : this.materialName;
		}

		public String getMaterialName() {
			return this.materialName;
		}

		public boolean setVertices(Vertex[] verts) {
			if (verts == null) return false;
			else this.verts = verts;
			return true;
		}

		public Vertex[] getVertices() {
			return this.verts;
		}

		public boolean setNormals(Normal[] norms) {
			if (norms == null) return false;
			else this.norms = norms;
			return true;
		}

		public Normal[] getNormals() {
			return this.norms;
		}

		public boolean setTextureCoordinates(TextureCoordinate[] texCoords) {
			if (texCoords == null) return false;
			else this.texCoords = texCoords;
			return true;
		}

		public TextureCoordinate[] getTextureCoordinates() {
			return this.texCoords;
		}

		public boolean areUVsNormalized() {
			for (TextureCoordinate t : this.texCoords) {
				float[] position = new float[2];
				t.getPosition().get(position);
				if (!(position[0] >= 0.0f && position[0] <= 1.0f && position[1] >= 0.0f && position[1] <= 1.0f)) {
					return false;
				}
			}
			return true;
		}

//		public Face bake(TRSRTransformation transform)
//		{
//			Matrix4f m = transform.getMatrix();
//			Vertex[] vertices = new Vertex[verts.length];
//			Normal[] normals = norms != null ? new Normal[norms.length] : null;
//			TextureCoordinate[] textureCoords = texCoords != null ? new TextureCoordinate[texCoords.length] : null;
//
//			for (int i = 0; i < verts.length; i++)
//			{
//				m = transform.getMatrix();
//				Vertex v = verts[i];
//				Normal n = norms != null ? norms[i] : null;
//				TextureCoordinate t = texCoords != null ? texCoords[i] : null;
//
//				Vector4f pos = new Vector4f(v.getPosition()), newPos = new Vector4f();
//				pos.w = 1;
//				m.transform(pos, newPos);
//				Vector4f rPos = new Vector4f(newPos.x, newPos.y, newPos.z, newPos.w);
//				vertices[i] = new Vertex(rPos, v.getMaterial());
//
//				if (n != null)
//				{
//					m.invert();
//					m.transpose();
//					Vector4f normal = new Vector4f(n.getNormal()), newNormal = new Vector4f();
//					normal.w = 1;
//					m.transform(normal, newNormal);
//					Vector3f rNormal = new Vector3f(newNormal.x / newNormal.w, newNormal.y / newNormal.w, newNormal.z / newNormal.w);
//					rNormal.normalize();
//					normals[i] = new Normal(rNormal);
//				}
//
//				//texCoords TODO
//				if (t != null) textureCoords[i] = t;
//			}
//			return new Face(vertices, normals, textureCoords, this.materialName);
//		}

		public Normal getNormal() {
			if (norms == null) { // use vertices to calculate normal
				Vector3f v1 = new Vector3f(this.verts[0].getPosition().x, this.verts[0].getPosition().y, this.verts[0].getPosition().z);
				Vector3f v2 = new Vector3f(this.verts[1].getPosition().x, this.verts[1].getPosition().y, this.verts[1].getPosition().z);
				Vector3f v3 = new Vector3f(this.verts[2].getPosition().x, this.verts[2].getPosition().y, this.verts[2].getPosition().z);
				Vector3f v4 = this.verts.length > 3 ? new Vector3f(this.verts[3].getPosition().x, this.verts[3].getPosition().y, this.verts[3].getPosition().z) : null;

				if (v4 == null) {
					Vector3f v2c = new Vector3f(v2.x, v2.y, v2.z);
					Vector3f v1c = new Vector3f(v1.x, v1.y, v1.z);
					v1c.sub(v2c);
					Vector3f v3c = new Vector3f(v3.x, v3.y, v3.z);
					v3c.sub(v2c);
					Vector3f c = new Vector3f();
					c.cross(v1c, v3c);
					c.normalize();
					Normal normal = new Normal(c);
					return normal;
				} else {
					Vector3f v2c = new Vector3f(v2.x, v2.y, v2.z);
					Vector3f v1c = new Vector3f(v1.x, v1.y, v1.z);
					v1c.sub(v2c);
					Vector3f v3c = new Vector3f(v3.x, v3.y, v3.z);
					v3c.sub(v2c);
					Vector3f c = new Vector3f();
					c.cross(v1c, v3c);
					c.normalize();

					v1c = new Vector3f(v1.x, v1.y, v1.z);
					v3c = new Vector3f(v3.x, v3.y, v3.z);

					Vector3f v4c = new Vector3f(v4.x, v4.y, v4.z);
					v1c.sub(v4c);
					v3c.sub(v4c);
					Vector3f d = new Vector3f();
					d.cross(v1c, v3c);
					d.normalize();

					Vector3f avg = new Vector3f();
					avg.x = (c.x + d.x) * 0.5f;
					avg.y = (c.y + d.y) * 0.5f;
					avg.z = (c.z + d.z) * 0.5f;
					avg.normalize();
					Normal normal = new Normal(avg);
					return normal;
				}
			} else { // use normals to calculate normal
				Vector3f n1 = this.norms[0].getNormal();
				Vector3f n2 = this.norms[1].getNormal();
				Vector3f n3 = this.norms[2].getNormal();
				Vector3f n4 = this.norms.length > 3 ? this.norms[3].getNormal() : null;

				if (n4 == null) {
					Vector3f n2c = new Vector3f(n2.x, n2.y, n2.z);
					Vector3f n1c = new Vector3f(n1.x, n1.y, n1.z);
					n1c.sub(n2c);
					Vector3f n3c = new Vector3f(n3.x, n3.y, n3.z);
					n3c.sub(n2c);
					Vector3f c = new Vector3f();
					c.cross(n1c, n3c);
					c.normalize();
					Normal normal = new Normal(c);
					return normal;
				} else {
					Vector3f n2c = new Vector3f(n2.x, n2.y, n2.z);
					Vector3f n1c = new Vector3f(n1.x, n1.y, n1.z);
					n1c.sub(n2c);
					Vector3f n3c = new Vector3f(n3.x, n3.y, n3.z);
					n3c.sub(n2c);
					Vector3f c = new Vector3f();
					c.cross(n1c, n3c);
					c.normalize();

					n1c = new Vector3f(n1.x, n1.y, n1.z);
					n3c = new Vector3f(n3.x, n3.y, n3.z);

					Vector3f n4c = new Vector3f(n4.x, n4.y, n4.z);
					n1c.sub(n4c);
					n3c.sub(n4c);
					Vector3f d = new Vector3f();
					d.cross(n1c, n3c);
					d.normalize();

					Vector3f avg = new Vector3f();
					avg.x = (c.x + d.x) * 0.5f;
					avg.y = (c.y + d.y) * 0.5f;
					avg.z = (c.z + d.z) * 0.5f;
					avg.normalize();
					Normal normal = new Normal(avg);
					return normal;
				}
			}
		}
	}

	public static class Vertex {
		private Vector4f position;
		private Material material = new Material();

		public Vertex(Vector4f position, Material material) {
			this.position = position;
			this.material = material;
		}

		public void setPos(Vector4f position) {
			this.position = position;
		}

		public Vector4f getPosition() {
			return this.position;
		}

		public void setMaterial(Material material) {
			this.material = material;
		}

		public Material getMaterial() {
			return this.material;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append(String.format("v:%n"));
			builder.append(String.format("    position: %s %s %s%n", position.x, position.y, position.z));
			builder.append(String.format("    material: %s %s %s %s %s%n", material.getColor().x, material.getColor().y, material.getColor().z, material.getColor().w));
			return builder.toString();
		}
	}

	public static class Normal {
		private Vector3f normal;

		public Normal(Vector3f normal) {
			this.normal = normal;
		}

		public void setNormal(Vector3f normal) {
			this.normal = normal;
		}

		public Vector3f getNormal() {
			return this.normal;
		}
	}

	public static class TextureCoordinate {
		private Vector3f position;

		public TextureCoordinate(Vector3f position) {
			this.position = position;
		}

		public void setPosition(Vector3f position) {
			this.position = position;
		}

		public Vector3f getPosition() {
			return this.position;
		}
	}

	public static class Group {
		public static final String DEFAULT_NAME = "OBJModel.Default.Element.Name";
		public static final String ALL = "OBJModel.Group.All.Key";
		public static final String ALL_EXCEPT = "OBJModel.Group.All.Except.Key";
		private String name = DEFAULT_NAME;
		private LinkedHashSet<Face> faces = new LinkedHashSet<Face>();

		public Group(String name, LinkedHashSet<Face> faces) {
			this.name = name != null ? name : DEFAULT_NAME;
			this.faces = faces == null ? new LinkedHashSet<>() : faces;
		}

		public String getName() {
			return this.name;
		}

		public LinkedHashSet<Face> getFaces() {
			return this.faces;
		}

		public void setFaces(LinkedHashSet<Face> faces) {
			this.faces = faces;
		}

		public void addFace(Face face) {
			this.faces.add(face);
		}

		public void addFaces(List<Face> faces) {
			this.faces.addAll(faces);
		}

		public void render() {
			Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawingQuads();
			for (Face face : this.faces) {
				Normal normal = face.getNormal();
				for (int i = 0; i < face.getVertices().length; i++) {
					if (face.getNormals() != null && face.getNormals().length > 0) {
						tessellator.setNormal(face.getNormals()[i].getNormal().x, face.getNormals()[i].getNormal().y, face.getNormals()[i].getNormal().z);
					} else {
						tessellator.setNormal(normal.getNormal().x, normal.getNormal().y, normal.getNormal().z);
					}
					if (face.getTextureCoordinates() != null && face.getTextureCoordinates().length > 0) {
						tessellator.addVertexWithUV(face.getVertices()[i].getPosition().x, face.getVertices()[i].getPosition().y, face.getVertices()[i].getPosition().z, face.getTextureCoordinates()[i].getPosition().x, face.getTextureCoordinates()[i].getPosition().y);
					} else {
						tessellator.addVertex(face.getVertices()[i].getPosition().x, face.getVertices()[i].getPosition().y, face.getVertices()[i].getPosition().z);
					}
				}
			}
		}
	}
}
