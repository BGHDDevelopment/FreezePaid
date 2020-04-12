package me.noodles.ultimatefreeze.commands;

import me.noodles.ultimatefreeze.UltimateFreeze;
import me.noodles.ultimatefreeze.utilities.Common;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FreezeCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("freeze")) {
            if (sender.hasPermission("ultimatefreeze.freeze")) {
                if (args.length == 0)
                    Common.tell(sender, UltimateFreeze.plugin.getmessagesConfig().getString("InvalidUsage"));

                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null) {
                        Common.tell(sender, UltimateFreeze.plugin.getmessagesConfig().getString("NoPlayer"));
                        return true;
                    }
                    if (target.hasPermission("ultimatefreeze.override")) {
                        Common.tell(sender, UltimateFreeze.plugin.getmessagesConfig().getString("NoFreeze"));
                        return true;

                    } else if (!UltimateFreeze.getPlugin().isUserFrozen(target)) {
                        UltimateFreeze.getPlugin().addFrozenUser(target);
                        Common.tell(sender, UltimateFreeze.plugin.getmessagesConfig().getString("PlayerFrozen").replace("%target%", target.getName()));
                        Common.tell(target, UltimateFreeze.plugin.getmessagesConfig().getString("Frozen"));
                        if (UltimateFreeze.plugin.getConfig().getBoolean("GiveBlindness.Enabled") == true) {
                            target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10000000, 0));
                        }

                        } else {
                            Common.tell(sender, UltimateFreeze.plugin.getmessagesConfig().getString("PlayerUnFrozen").replace("%target%", target.getName()));
                            Common.tell(target, UltimateFreeze.plugin.getmessagesConfig().getString("UnFrozen"));
                            target.getActivePotionEffects().clear();
                            for (PotionEffect pe : target.getActivePotionEffects()) {
                                target.removePotionEffect(pe.getType());
                                target.closeInventory();
                                UltimateFreeze.getPlugin().removeFrozenUser(target);
                            }
                        }
                    }
                } else {
                    Common.tell(sender, UltimateFreeze.plugin.getmessagesConfig().getString("NoPermission"));
                }

        }

        return false;
    }

}