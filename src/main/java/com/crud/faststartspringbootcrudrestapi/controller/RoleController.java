package com.crud.faststartspringbootcrudrestapi.controller;

import com.crud.faststartspringbootcrudrestapi.ResponseMessage;
import com.crud.faststartspringbootcrudrestapi.domain.Role;
import com.crud.faststartspringbootcrudrestapi.domain.dto.RoleAddToUserDto;
import com.crud.faststartspringbootcrudrestapi.service.RoleService;
import com.crud.faststartspringbootcrudrestapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> saveRole(@RequestBody() Role role) {
        roleService.save(role);
        return ResponseEntity.ok().body(new ResponseMessage("role has been saved"));
    }

    @GetMapping()
    public ResponseEntity<List<Role>> getAllRole() {
        return ResponseEntity.ok().body(roleService.findAll());
    }

    @GetMapping("/?name")
    public ResponseEntity<Role> findRoleByName(@RequestParam(value = "name") String name) {
        return ResponseEntity.ok().body(roleService.findByName(name));
    }

    @PostMapping("addRoleToUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleAddToUserDto roleAddToUserDto) {
        roleService.addRoleToUser(roleAddToUserDto.getUserEmail(), roleAddToUserDto.getRoleName());
        return ResponseEntity.ok().body(new ResponseMessage("Role has successfully fetch"));
    }
}
