package com.SchoolBack.Model;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class addStudentToCourseDTO {
	List<Long> studentList;
}
