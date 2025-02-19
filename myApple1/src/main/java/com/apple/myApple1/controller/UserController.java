package com.apple.myApple1.controller;

import com.apple.myApple1.service.UserService;
import com.apple.myApple1.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Hiển thị danh sách người dùng
    @GetMapping("/")
    public String showUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "home";
    }

    // Hiển thị form thêm người dùng
    @GetMapping("/add-user-form")
    public String showAddUserForm() {
        return "add-user";
    }

    // Xử lý thêm người dùng
    @PostMapping("/add-user")
    public String addUser(@RequestParam String name, @RequestParam int age) {
        User newUser = new User(name, age);
        userService.saveUser(newUser);
        return "redirect:/";
    }

    // Hiển thị form chỉnh sửa người dùng
    @GetMapping("/edit-user/{id}")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        if (user == null) {
            return "redirect:/";
        }
        model.addAttribute("user", user);
        return "edit-user";
    }

    // Xử lý cập nhật thông tin người dùng
    @PostMapping("/edit-user/{id}")
    public String updateUser(@PathVariable Long id, @RequestParam String name, @RequestParam int age) {
        User user = userService.getUserById(id);
        if (user != null) {
            user.setName(name);
            user.setAge(age);
            userService.updateUser(user);
        }
        return "redirect:/";
    }

    // Xử lý xóa người dùng
    @GetMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/";
    }
}
