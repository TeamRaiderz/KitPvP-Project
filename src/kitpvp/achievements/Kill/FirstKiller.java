package kitpvp.achievements.Kill;

import kitpvp.Main;
import kitpvp.achievements.Achievement;

public class FirstKiller extends Achievement{
	
	private Achievement[] children;
	
	@Override
	public String getName() {
		return "FirstKiller";
	}

	@Override
	public int getXPReward() {
		return 0;
	}

	@Override
	public Achievement[] getChildren() {
		try {
			return children;
		} catch (Exception e) {
			Main.getInstance().sendErrorMessage(e);
		}
		return null;
	}

	@Override
	public boolean hasChildren() {
		return false;
	}

	@Override
	public String toString() {
		return "Achievement: [Name: " + getName() + ", XP: " + getXPReward() + "]";
	}

	@Override
	public String getDisplayName() {
		// TODO Auto-generated method stub
		return "Killer 1";
	}

}
