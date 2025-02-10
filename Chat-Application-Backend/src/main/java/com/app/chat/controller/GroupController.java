package com.app.chat.controller;

import com.app.chat.dto.APIResponseDTO;
import com.app.chat.dto.ListAPIResponseDTO;
import com.app.chat.dto.PagedAPIResponseDTO;
import com.app.chat.dto.group.GroupRequestDTO;
import com.app.chat.entity.Group;
import com.app.chat.entity.Message;
import com.app.chat.service.GroupService;
import com.app.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/groups")
@CrossOrigin("http://localhost:5173")
public class GroupController {
    private final GroupService groupService;
    private final UserService userService;

    @Autowired
    GroupController(GroupService groupService,UserService userService)
    {
        this.groupService = groupService;
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<PagedAPIResponseDTO> getAllGroups(
            @RequestParam int page,
            @RequestParam int pageSize
    )
    {
        Page<Group> groups = groupService.getAllGroups(page,pageSize);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        PagedAPIResponseDTO
                                .builder()
                                .pageData(groups.getContent())
                                .totalElements(groups.getTotalElements())
                                .totalPages(groups.getTotalPages())
                                .currentLimit(groups.getNumberOfElements())
                                .build()
                );
    }

    @PostMapping("/create")
    public ResponseEntity<APIResponseDTO> createNewGroup(@RequestBody GroupRequestDTO groupRequestDTO)
    {
        Group newGroup = groupService.createNewGroup(groupRequestDTO,groupRequestDTO.getUserId());
        groupService.joinGroup(newGroup.getGroupId(),groupRequestDTO.getUserId());
        userService.addGroupForUser(groupRequestDTO.getUserId(),newGroup.getGroupId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        APIResponseDTO.builder()
                                .message("New user created with the id: " + newGroup.getGroupId() + " and group name: "+newGroup.getGroupName()+".")
                                .data(newGroup)
                                .build()
                );
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<APIResponseDTO> getGroupById(@PathVariable String groupId)
    {
        Group group = groupService.getGroupById(groupId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        APIResponseDTO.builder()
                                .data(group)
                                .build()
                );
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<APIResponseDTO> updateGroup(@PathVariable String groupId,@RequestBody GroupRequestDTO groupRequestDTO)
    {
        Group updateGroup = groupService.updateGroupById(groupId,groupRequestDTO);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        APIResponseDTO.builder()
                                .message("Updated the group with the id: " + updateGroup.getGroupId() + " and group name: " + updateGroup.getGroupName()+".")
                                .data(updateGroup)
                                .build()
                );
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<APIResponseDTO> deleteGroupById(@PathVariable String groupId)
    {
        Group deletedGroup = groupService.getGroupById(groupId);
        groupService.deleteGroupById(groupId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        APIResponseDTO
                                .builder()
                                .message("Deleted the user with the id: "+deletedGroup.getGroupId()+" and group name: " + deletedGroup.getGroupName()+".")
                                .build()
                );
    }

    @PostMapping("/{groupId}/user/{userId}/joinGroup")
    public ResponseEntity<APIResponseDTO> joinGroup(@PathVariable String groupId,@PathVariable String userId)
    {
        groupService.joinGroup(groupId,userId);
        userService.addGroupForUser(userId,groupId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        APIResponseDTO
                                .builder()
                                .message("The user joined")
                                .build()
                );
    }

    @PostMapping("/{groupId}/user/{userId}/leaveGroup")
    public ResponseEntity<APIResponseDTO> leaveGroup(@PathVariable String groupId,@PathVariable String userId)
    {
        groupService.leaveGroup(groupId,userId);
        userService.removeGroupForUser(groupId,userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        APIResponseDTO
                                .builder()
                                .message("The user left")
                                .build()
                );
    }

    @GetMapping("/{groupId}/getMessages")
    public ResponseEntity<ListAPIResponseDTO> getMessages(@PathVariable String groupId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int pageSize)
    {
        List<Message> messages = groupService.getMessages(groupId,page,pageSize);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ListAPIResponseDTO
                                .builder()
                                .pageData(messages)
                                .size(messages.size())
                                .build()
                );
    }
}
