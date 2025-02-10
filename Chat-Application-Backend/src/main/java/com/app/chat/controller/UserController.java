package com.app.chat.controller;

import com.app.chat.dto.APIResponseDTO;
import com.app.chat.dto.ListAPIResponseDTO;
import com.app.chat.dto.PagedAPIResponseDTO;
import com.app.chat.dto.user.UserRequestDTO;
import com.app.chat.entity.Group;
import com.app.chat.entity.User;
import com.app.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@CrossOrigin("http://localhost:5173")
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<PagedAPIResponseDTO> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int pageSize
    )
    {
        Page<User> users = userService.getAllUsers(page,pageSize);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        PagedAPIResponseDTO
                                .builder()
                                .pageData(users.getContent())
                                .totalElements(users.getTotalElements())
                                .totalPages(users.getTotalPages())
                                .currentLimit(users.getNumberOfElements())
                                .build()
                );
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponseDTO> createNewUser(@RequestBody UserRequestDTO userRequestDTO)
    {
        User user = userService.getUserByUserNameIfPresent(userRequestDTO.getUserName());
        if(user == null)
        {
            user = userService.createNewUser(userRequestDTO);
        }


        if(user.getPassword().equals(userRequestDTO.getPassword())){
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(
                            APIResponseDTO.builder()
                                    .message("New user created with the id: " + user.getUserId() + " and username: "+user.getUserName()+".")
                                    .data(user)
                                    .build()
                    );
        }
        else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(
                            APIResponseDTO.builder()
                                    .message("Invalid Password")
                                    .build()
                    );
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<APIResponseDTO> getUserById(@PathVariable String userId)
    {
        User user =  userService.getUserById(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        APIResponseDTO.builder()
                                .data(user)
                                .build()
                );
    }

    @PutMapping("/{userId}")
    public ResponseEntity<APIResponseDTO> updateUser(@PathVariable String userId,@RequestBody UserRequestDTO userRequestDTO)
    {
        User updatedUser = userService.updateUserById(userId,userRequestDTO);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        APIResponseDTO.builder()
                                .message("Updated the user with the id: " + updatedUser.getUserId() + " and username: " + updatedUser.getUserName()+".")
                                .data(updatedUser)
                                .build()
                );
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<APIResponseDTO> deleteUserById(@PathVariable String userId)
    {
        User deletedUser = userService.getUserById(userId);
        userService.deleteUserById(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        APIResponseDTO
                                .builder()
                                .message("Deleted the user with the id: "+deletedUser.getUserId()+" and username: " + deletedUser.getUserName()+".")
                                .build()
                );
    }

    @GetMapping("/{userId}/groups/all")
    public ResponseEntity<ListAPIResponseDTO> getAllGroupsByUser(@PathVariable String userId)
    {
        List<Group> groups = userService.getAllGroupsForUser(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ListAPIResponseDTO
                                .builder()
                                .pageData(groups)
                                .size(groups.size())
                                .build()
                );
    }
}
