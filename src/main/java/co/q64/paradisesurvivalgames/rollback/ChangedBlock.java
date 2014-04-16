package co.q64.paradisesurvivalgames.rollback;

import org.bukkit.Material;

public class ChangedBlock {
	private Material newmat;
	private Material premat;
	private byte prevdata, newdata;
	private String world;
	private int x, y, z;

	/**
	 * @param premat
	 * @param newmat
	 * @param x
	 * @param y
	 * @param z      Provides a object for holding the data for block changes
	 */
	public ChangedBlock(String world, Material premat, byte prevdata, Material newmat, byte newdata, int x, int y, int z) {
		this.world = world;
		this.premat = premat;
		this.prevdata = prevdata;
		this.newmat = newmat;
		this.newdata = newdata;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public byte getNewdata() {
		return newdata;
	}

	public Material getNewid() {
		return newmat;
	}

	public byte getPrevdata() {
		return prevdata;
	}

	public Material getPrevid() {
		return premat;
	}

	public String getWorld() {
		return world;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

}
