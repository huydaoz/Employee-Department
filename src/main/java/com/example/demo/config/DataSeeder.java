package com.example.demo.config;

import com.example.demo.entity.Department;
import com.example.demo.entity.Employee;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public void run(String... args) {
        if (departmentRepository.count() == 0) {
            // Tạo Phòng ban
            Department it = new Department(null, "Phòng IT", "Tầng 4", null);
            Department hr = new Department(null, "Phòng Nhân sự", "Tầng 2", null);
            departmentRepository.saveAll(List.of(it, hr));

            // Tạo Nhân viên
            Employee e1 = new Employee(null, "Phạm Tiến Đạt", 20, "dat.jpg", true, it);
            Employee e2 = new Employee(null, "Nguyễn Văn A", 22, "ava.png", true, hr);
            employeeRepository.saveAll(List.of(e1, e2));
        }
    }
}