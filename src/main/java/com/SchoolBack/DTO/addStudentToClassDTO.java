package com.SchoolBack.DTO;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class addStudentToClassDTO {
	List<Long> studentList;
}
