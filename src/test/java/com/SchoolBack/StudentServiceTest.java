package com.SchoolBack;

import com.SchoolBack.DTO.Request.Student.studentUpdateDTO;
import com.SchoolBack.Entity.Student;
import com.SchoolBack.Exception.StudentServiceBusinessException;
import com.SchoolBack.Repository.StudentRepository;
import com.SchoolBack.Service.StudentService;
import com.SchoolBack.Util.ValueMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ValueMapper mapper;

    @InjectMocks
    private StudentService studentService;


    //===============[FindStudentByID Test]===============

    @Test
    void findStudentById_ShouldReturnStudent_WhenStudentIsActive() {
        // Arrange
        Long id = 1L;
        Student student = new Student();
        student.setId(id);
        student.setActive(true);
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));

        // Act
        Student result = studentService.findStudentById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(studentRepository, times(1)).findById(id);
    }

    @Test
    void findStudentById_ShouldThroweException_WhenStudentNotFound() {
        // Arrange
        Long id = 2L;
        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        // Act % Assert
        StudentServiceBusinessException thrown = assertThrows(StudentServiceBusinessException.class, () -> {
            studentService.findStudentById(2L);
        });

        assertEquals("Exception occurred while fetch a student from database:Student not found with id: 2", thrown.getMessage());
        verify(studentRepository, times(1)).findById(2L);
    }

    @Test
    void findStudentById_ShouldThrowException_WhenStudentIsInacive() {
        // Arrange
        Long id = 1L;
        Student student = new Student();
        student.setId(id);
        student.setActive(false);
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));

        // Act % Assert
        StudentServiceBusinessException thrown = assertThrows(StudentServiceBusinessException.class, () -> {
            studentService.findStudentById(id);
        });

        assertEquals("Exception occurred while fetch a student from database:Student not active with id: " + id, thrown.getMessage());
        verify(studentRepository, times(1)).findById(id);
    }

    @Test
    void findStudentById_ShouldThrowBusinessException_WhenUnexpectedErrorOccurs() {
        // Arrange
        Long id = 1L;
        when(studentRepository.findById(id)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        StudentServiceBusinessException exception = assertThrows(StudentServiceBusinessException.class,
                () -> studentService.findStudentById(id));
        assertTrue(exception.getMessage().contains("Exception occurred while fetch a student from database"));
        verify(studentRepository, times(1)).findById(id);
    }

    //===============[SaveStudent Test]===============

    @Test
    void saveStudent_ShouldReturnStudent_WhenIsSuccessfullySaved() {

        //Arrange
        Student student = new Student();
        student.setId(1L);
        student.setName("StudentName");
        student.setActive(true);
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        //Act
        Student result = studentService.save(student);

        //Assert
        assertNotNull(result);
        assertEquals(1L, student.getId());
        assertEquals("StudentName", student.getName());
        assertTrue(student.isActive());

        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void saveStudent_ShouldThrowException_WhenStudentCannotBeSaved() {

        //Arrange
        Student student = new Student();
        when(studentRepository.save(any(Student.class))).thenThrow(new RuntimeException("v"));

        //Act & Assert
        StudentServiceBusinessException thrown = assertThrows(StudentServiceBusinessException.class, () -> {
            studentService.save(student);
        });

        assertEquals("Exception occurred while create a new student", thrown.getMessage());

        verify(studentRepository, times(1)).save(student);
    }

    //===============[updateStudent Test]===============

    @Test
    void updateStudent_ShouldReturnStudent_WhenIsSuccessfullyUpdated() {
        //Arrange
        Long id = 1L;
        studentUpdateDTO studentUpdate = new studentUpdateDTO();
        studentUpdate.setName("Updated Name");
        studentUpdate.setMajor("Updated Major");

        Student studentToUpdate = new Student();
        studentToUpdate.setId(id);
        studentToUpdate.setName("Old Name");
        studentToUpdate.setMajor("Old Major");
        studentToUpdate.setActive(true);

        Student updatedStudent = new Student();
        updatedStudent.setId(id);
        updatedStudent.setName(studentUpdate.getName());
        updatedStudent.setMajor(studentUpdate.getMajor());
        updatedStudent.setActive(true);

        when(studentRepository.findById(id)).thenReturn(Optional.of(studentToUpdate));
        when(mapper.updateStudentFromDTO(any(Student.class), any(studentUpdateDTO.class))).thenReturn(updatedStudent);
        when(studentRepository.save(any(Student.class))).thenReturn(updatedStudent);

        //Act
        Student result = studentService.update(id, studentUpdate);

        //Assert
        assertNotNull(result);
        assertEquals(studentUpdate.getName(), result.getName());
        assertEquals(studentUpdate.getMajor(), result.getMajor());
        assertEquals(id, result.getId());

        verify(studentRepository, times(1)).findById(id);
        verify(mapper, times(1)).updateStudentFromDTO(any(Student.class), any(studentUpdateDTO.class));
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void updateStudent_ShouldThrowException_WhenStudentCannotBeFound() {
        //Arrange
        Long id = 1L;
        studentUpdateDTO studentUpdate = new studentUpdateDTO();


        //Act
        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        //Assert
        assertThrows(StudentServiceBusinessException.class, () -> studentService.update(id,studentUpdate));
        verify(studentRepository, times(1)).findById(id);
        verifyNoMoreInteractions(mapper, studentRepository);
    }

}
