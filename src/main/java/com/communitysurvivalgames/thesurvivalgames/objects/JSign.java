package com.communitysurvivalgames.thesurvivalgames.objects;

import src.main.java.com.communitysurvivalgames.thesurvivalgames.TheSurvivalGames;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.exception.ArenaNotFoundException;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.signs.SignLayout;

@Entity
@Table(name = "lobby_teleportsigns")
public class JSign {
    @Id
    private int id;
    @NotNull
    private int arena;
    @NotEmpty
    private String layout;
    @NotEmpty
    private String worldName;
    @NotNull
    private double x;
    @NotNull
    private double y;
    @NotNull
    private double z;

    public JSign() {
    }

    public JSign(int arena, Location loc, String layout) {
        this.arena = arena;
        this.layout = layout;
        setLocation(loc);
    }

    private void setLocation(Location location) {
        this.worldName = location.getWorld().getName();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
    }

    Location getLocation() {
        World welt = Bukkit.getServer().getWorld(this.worldName);
        return new Location(welt, this.x, this.y, this.z);
    }

    public void updateSign() {
        Location location = getLocation();
        if (location.getWorld().getChunkAt(location).isLoaded()) {
            Block b = location.getBlock();
            if ((b.getState() instanceof Sign)) {
                SignLayout layout = TheSurvivalGames.getPlugin(TheSurvivalGames.class).getPluginConfig().getLayout(this.layout);
                if (layout != null) {
                    Sign s = (Sign) b.getState();
                    List<String> jau;
                    try {
                        jau = layout.parseLayout(SGApi.getArenaManager().getArena(arena));
                    } catch (ArenaNotFoundException e) {
                        Bukkit.getLogger().severe("[ArenaSigns] The arena could not be found on sign " + id);
                        return;
                    }
                    for (int i = 0; i < layout.getLines().size(); i++) {
                        s.setLine(i, jau.get(i));
                    }
                    s.update();
                } else {
                    Bukkit.getLogger().severe("[ArenaSigns] can't find layout '" + this.layout + "'");
                }
            }
        }
    }

    public int getId() {
        return this.id;
    }

    public int getArena() {
        return this.arena;
    }

    public String getLayout() {
        return this.layout;
    }

    public String getWorldName() {
        return this.worldName;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setArena(int arena) {
        this.arena = arena;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof JSign)) {
            return false;
        }
        JSign other = (JSign) o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (getId() != other.getId()) {
            return false;
        }
        Object this$server = getArena();
        Object other$server = other.getArena();
        if (this$server == null ? other$server != null : !this$server.equals(other$server)) {
            return false;
        }
        Object this$layout = getLayout();
        Object other$layout = other.getLayout();
        if (this$layout == null ? other$layout != null : !this$layout.equals(other$layout)) {
            return false;
        }
        Object this$worldName = getWorldName();
        Object other$worldName = other.getWorldName();
        if (this$worldName == null ? other$worldName != null : !this$worldName.equals(other$worldName)) {
            return false;
        }
        if (Double.compare(getX(), other.getX()) != 0) {
            return false;
        }
        if (Double.compare(getY(), other.getY()) != 0) {
            return false;
        }
        return Double.compare(getZ(), other.getZ()) == 0;
    }

    boolean canEqual(Object other) {
        return other instanceof JSign;
    }

    public int hashCode() {
        int result = 1;
        result = result * 31 + getId();
        Object $server = getArena();
        result = result * 31 + ($server == null ? 0 : $server.hashCode());
        Object $layout = getLayout();
        result = result * 31 + ($layout == null ? 0 : $layout.hashCode());
        Object $worldName = getWorldName();
        result = result * 31 + ($worldName == null ? 0 : $worldName.hashCode());
        long $x = Double.doubleToLongBits(getX());
        result = result * 31 + (int) ($x >>> 32 ^ $x);
        long $y = Double.doubleToLongBits(getY());
        result = result * 31 + (int) ($y >>> 32 ^ $y);
        long $z = Double.doubleToLongBits(getZ());
        result = result * 31 + (int) ($z >>> 32 ^ $z);
        return result;
    }

    public String toString() {
        return "TeleportSign(id=" + getId() + ", server=" + getArena() + ", layout=" + getLayout() + ", worldName=" + getWorldName() + ", x=" + getX() + ", y=" + getY() + ", z="
                + getZ() + ")";
    }

}
