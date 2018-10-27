package com.mycompany.exercise6.data;

public class MusicalInstrument {

	private String name;
	private int id;

	public static MusicalInstrument ACCORDION = new MusicalInstrument("Аккордион", 22),
			TRUMPET = new MusicalInstrument("Труба", 57),
			SYNTH_DRUM = new MusicalInstrument("Синтетический барабан", 119),
			REVERSE_CYMBAL = new MusicalInstrument("Реверсивная тарелка", 120),
			CLEAN_GUITAR = new MusicalInstrument("Электрогитара чистая", 28),
			GUNSHOT = new MusicalInstrument("Выстрел", 128);

	public static MusicalInstrument[] ALL_INSTRUMENTS = { ACCORDION, TRUMPET, SYNTH_DRUM, REVERSE_CYMBAL, CLEAN_GUITAR,
			GUNSHOT };

	public MusicalInstrument(String name, int id) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		final MusicalInstrument other = (MusicalInstrument) obj;
		if (this.id != other.getId()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return name;
	}

}