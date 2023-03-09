package me.alvsch.alvschitems.core.scoreboard;

import me.alvsch.alvschitems.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.*;

public class CustomScoreboard {

	private final Scoreboard scoreboard;

	public CustomScoreboard() {
		ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
		this.scoreboard = scoreboardManager.getNewScoreboard();

		Objective objective = scoreboard.registerNewObjective("test", "dummy", Utils.color("&eAlvschItems"));
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);

		Score score1 = objective.getScore(Utils.color("&702/28/23"));
		score1.setScore(10);
		Score score2 = objective.getScore(" ");
		score2.setScore(9);
		Score score3 = objective.getScore(Utils.color("&fEarly Winter 23rd"));
		score3.setScore(8);
		Score score4 = objective.getScore(Utils.color("&710:30pm"));
		score4.setScore(7);
		Score score5 = objective.getScore(Utils.color("&aYour Island"));
		score5.setScore(6);
		Score score6 = objective.getScore(" ");
		score6.setScore(5);
		Score score7 = objective.getScore(Utils.color("&fPurse: &6100,000,001"));
		score7.setScore(4);
		Score score8 = objective.getScore(Utils.color("&fBits: &b1,500,000"));
		score8.setScore(3);
		Score score9 = objective.getScore(" ");
		score9.setScore(2);
		Score score10 = objective.getScore(Utils.color("&ewww.alvsch.net"));
		score10.setScore(1);

	}

	public Scoreboard getScoreboard() {
		return scoreboard;
	}

}
