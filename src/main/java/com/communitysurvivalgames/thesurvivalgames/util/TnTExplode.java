//Name: TnTExplode.java
//Created: 20 December 2013
//Edited: Never
//Version: 1.0.0
public class TnTExplode {
  public void onBlockPlace(BlockPlaceEvent tntevent)
  {
		if(event.getBlock().getType().equals(Material.TNT)){
			   tntevent.getBlock().setType(Material.AIR); 
			   tntevent.getPlayer().getWorld().spawnEntity(tntevent.getBlock().getLocation(), EntityType.PRIMED_TNT);
			 return;
	}
} 
