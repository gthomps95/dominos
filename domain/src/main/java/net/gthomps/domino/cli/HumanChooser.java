package net.gthomps.domino.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import net.gthomps.domino.Domino;
import net.gthomps.domino.Game;
import net.gthomps.domino.Player;
import net.gthomps.domino.ai.PlayChooser;

public abstract class HumanChooser<T> implements PlayChooser<T> {
	public void showDominos(Player human) {
		System.out.println(String.format("%s dominos:", human.toString()));
		int i = 1;
		for (Domino d : human.getDominosInHand()) {
			System.out.println(String.format("%d - %s", i++, d.toString()));
		}
	}
	
	public abstract void showPrompt();
	public abstract String getNoun();
	public abstract boolean checkIndex(int index, Player human);
	public abstract T getResponseResult(Integer index, Player human);

	public Integer readResponse(Player human) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String response = br.readLine();
		
		if (response.equals("f"))
			return null;
	
		int index = -1;
		try {
			index = Integer.parseInt(response);
	
			if (checkIndex(index, human)) {
				System.out.println(String.format("%s is not a valid %s", index, getNoun()));
				return new Integer(-1);
			}
		}
		catch (NumberFormatException nfe) {
			System.out.println(nfe.getMessage());
			System.out.println(String.format("%s is not a valid %s", response, getNoun()));
			return new Integer(-1);
		}
		
		return new Integer(index);
	}

	public T choose(Game game, Player player) {
		showDominos(player);
		showPrompt();
		
		Integer index = null;
		try {
			index = readResponse(player);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	
		if (index == null)
			return null;
		
		if (index.intValue() == -1)
			return choose(game, player);
		
		return getResponseResult(index, player);
	}
}
