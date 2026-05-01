package com.example.demo.controller;

import com.example.demo.entity.Employee;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @GetMapping
    public String listEmployees(Model model) {

        model.addAttribute("list", employeeRepository.findAll());
        return "employee-list";
    }
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", departmentRepository.findAll());
        return "employee-add";
    }

    @PostMapping("/add")
    public String saveEmployee(@ModelAttribute("employee") Employee employee,
                               @RequestParam("file") MultipartFile file) throws IOException {

        if (file != null && !file.isEmpty()) {
            // 1. Xử lý đổi tên file tránh trùng lặp
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            // 2. Lưu file vào thư mục vật lý
            Path uploadPath = Paths.get("uploads/");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Files.copy(file.getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

            // 3. Lưu tên file vào database
            employee.setAvatar(fileName);
        } else {
            employee.setAvatar("default-avatar.png"); // Ảnh mặc định nếu không upload
        }

        employeeRepository.save(employee);
        return "redirect:/employees";
    }
}