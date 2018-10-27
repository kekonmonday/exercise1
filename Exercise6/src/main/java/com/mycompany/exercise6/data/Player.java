package com.mycompany.exercise6.data;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import pane.NoteStan;

public class Player implements Runnable {

	public static int iD = 0;

	private int id;
	private String name;
	private int during = 5;

	private ArrayList<Note> notes = new ArrayList<Note>();

	private MusicalInstrument instrument;

	public Player() {
		this.id = iD;
		this.name = "Игрок " + id;
		iD++;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MusicalInstrument getInstrument() {
		return instrument;
	}

	public void setInstrument(MusicalInstrument instrument) {
		this.instrument = instrument;
	}

	public int getDuring() {
		return during;
	}

	public void setDuring(int during) {
		this.during = during;
	}

	public void addNote(Note note) {
		notes.add(note);
	}

	public void removeNote(Note note) {
		notes.remove(note);
	}

	public ArrayList<Note> getNotes() {
		return notes;
	}

	public void setNotes(ArrayList<Note> notes) {
		this.notes = notes;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Player other = (Player) obj;
		if (this.id != other.getId()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public void run() {
		MusicalGroup.channels[id].programChange(instrument.getId());
		for (Note note : notes) {
			if (note.getNote() != -1) {
				MusicalGroup.channels[id].noteOn(((int) note.getNote() / (NoteStan.NOTE_STAN_HEIGHT / 11)) + 60,
						MusicalGroup.VOLUME);
				try {
					Thread.sleep((long) (note.getDurationNote() * 1000));
				} catch (InterruptedException ex) {
					Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
				}
				MusicalGroup.channels[id].noteOff(((int) note.getNote() / (NoteStan.NOTE_STAN_HEIGHT / 11)) + 60);

			} else {
				try {
					Thread.sleep((long) (note.getDurationNote() * 1000));
				} catch (InterruptedException ex) {
					Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}

}