package com.SchoolBack.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum State {
	PRESENT("present"),
	ABSENT("absent"),
	LATE("late");
	
	@Getter
	private final String state;
}
