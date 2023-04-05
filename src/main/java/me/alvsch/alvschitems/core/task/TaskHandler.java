package me.alvsch.alvschitems.core.task;

import me.alvsch.alvschitems.AlvschItems;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TaskHandler {

    private final Map<UUID, Map<String, BukkitTask>> playerTaskMap = new HashMap<>();

    public TaskHandler() {

    }

    public void addTask(UUID uuid, String id, long delay, boolean stack, ITask iTask) {
        Map<String, BukkitTask> taskMap = getTaskMap().computeIfAbsent(uuid, k -> new HashMap<>());
        BukkitTask task = taskMap.get(id);

        // Check if task doesn't exist || isn't running || should stack
        if(task == null || !Bukkit.getScheduler().isQueued(task.getTaskId())) {
            iTask.onStart();
        } else {
            if(stack) {
                iTask.onStart();
            } else {
                task.cancel();
            }
        }
        // Create new task
        taskMap.put(id, newTask(iTask, delay));
    }

    protected BukkitTask newTask(ITask task, long delay) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                task.onEnd();
            }
        }.runTaskLater(AlvschItems.getInstance(), delay);
    }

    public Map<UUID, Map<String, BukkitTask>> getTaskMap() {
        return playerTaskMap;
    }

}
