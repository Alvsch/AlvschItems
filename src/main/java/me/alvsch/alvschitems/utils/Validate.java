package me.alvsch.alvschitems.utils;

import org.bukkit.command.CommandSender;

public class Validate {

    public static boolean argsLength(String[] args, int length) {
        return argsLength(args, length, null, null);
    }
    public static boolean argsLength(String[] args, int length, CommandSender sender, String message) {
        if(args.length >= length) {
            return true;
        }
        sendMessage(sender, message);
        return false;
    }

    public static boolean isInstanceOf(Class<?> type, Object obj) {
        return isInstanceOf(type, obj, null, null);
    }
    public static boolean isInstanceOf(Class<?> type, Object obj, CommandSender sender, String message) {
        if(type.isInstance(obj)) {
            return true;
        }
        sendMessage(sender, message);
        return false;
    }

    public static boolean notNull(Object obj) {
        return notNull(obj, null, null);
    }
    public static boolean notNull(Object obj, CommandSender sender, String message) {
        if(obj != null) {
            return true;
        }
        sendMessage(sender, message);
        return false;
    }

    private static void sendMessage(CommandSender sender, String message) {
        if(sender != null && message != null) {
            sender.sendMessage(message);
        }
    }

}
