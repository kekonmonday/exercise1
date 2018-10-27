package com.mycompany.exercise6.data;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class MusicalGroup {

	public static final int VOLUME = 80;
	public static MidiChannel[] channels = null;
	public static Synthesizer synth = null;
	public ArrayList<Player> players = new ArrayList<Player>();

	public MusicalGroup() {
		try {
			synth = MidiSystem.getSynthesizer();
			synth.open();
			channels = synth.getChannels();
		} catch (MidiUnavailableException ex) {
			Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void close() {
		synth.close();
	}

	public void playMusic() {
		for (Player p : players) {
			Thread t = new Thread(p);
			t.start();
		}
	}

	public void addPlayer(Player player) {
		players.add(player);
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

}
