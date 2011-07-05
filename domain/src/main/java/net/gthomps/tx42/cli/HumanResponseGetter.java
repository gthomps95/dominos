package net.gthomps.tx42.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import net.gthomps.tx42.Domino;
import net.gthomps.tx42.Player;

public abstract class HumanResponseGetter<T> {
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

	public T getResponseFromHuman(Player human) throws IOException {
		showDominos(human);
		showPrompt();
		
		Integer index = readResponse(human);
	
		if (index == null)
			return null;
		
		if (index.intValue() == -1)
			return getResponseFromHuman(human);
		
		return getResponseResult(index, human);
	}
}
