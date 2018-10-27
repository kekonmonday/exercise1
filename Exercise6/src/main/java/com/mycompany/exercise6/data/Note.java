package com.mycompany.exercise6.data;

public class Note {

	private static long iD = 0;

	private long id = iD++;
	private double note, durationNote = 0, startTime;

	public static int ALL_DIAPASON_NOTES = 176;

	public Note(double note, double startTime) {
		this.note = note;// (int) note / (ALL_DIAPASON_NOTES / 11);
		this.startTime = startTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getNote() {
		return note;
	}

	public void setNote(double note) {
		this.note = note;
	}

	public double getStartTime() {
		return startTime;
	}

	public void setStartTime(double startTime) {
		this.startTime = startTime;
	}

	public double getDurationNote() {
		return durationNote;
	}

	public void setDurationNote(double durationNote) {
		this.durationNote = durationNote;
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
		final Note other = (Note) obj;
		if (this.id != other.getId()) {
			return false;
		}
		return true;
	}

}
