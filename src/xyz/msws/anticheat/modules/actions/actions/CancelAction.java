package xyz.msws.anticheat.modules.actions.actions;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.BlockFace;

import xyz.msws.anticheat.NOPE;
import xyz.msws.anticheat.modules.actions.AbstractAction;
import xyz.msws.anticheat.modules.checks.Check;
import xyz.msws.anticheat.modules.data.CPlayer;

/**
 * Teleports the player to their {@link CPlayer#getLastSafeLocation()} This
 * naturally means it doesn't work against certain check types such as KillAura
 * 
 * @author imodm
 *
 */
public class CancelAction extends AbstractAction {

	public CancelAction(NOPE plugin) {
		super(plugin);
	}

	@Override
	public void execute(OfflinePlayer player, Check check) {
		if (!player.isOnline())
			return;
		CPlayer cp = plugin.getCPlayer(player);

		Location safe = cp.getLastSafeLocation();
		if (safe == null)
			return;
		if (!safe.getWorld().equals(player.getPlayer().getWorld()))
			return;
		if (!safe.getBlock().getRelative(BlockFace.DOWN).getType().isSolid())
			return;
		if (safe.getBlock().getRelative(BlockFace.UP).getType() != Material.AIR)
			return;
		if (safe.distanceSquared(player.getPlayer().getLocation()) > 100)
			return;

		player.getPlayer().teleport(cp.getLastSafeLocation());
	}

}
