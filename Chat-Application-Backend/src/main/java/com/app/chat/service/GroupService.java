package com.app.chat.service;

import com.app.chat.constants.ExceptionConstants;
import com.app.chat.dto.group.GroupRequestDTO;
import com.app.chat.entity.Group;
import com.app.chat.entity.Message;
import com.app.chat.entity.User;
import com.app.chat.exception.GroupNotFoundException;
import com.app.chat.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    @Autowired
    GroupService(GroupRepository groupRepository,UserService userService)
    {
        this.groupRepository = groupRepository;
    }


    public Page<Group> getAllGroups(int page, int pageSize)
    {
        return groupRepository.findAll(PageRequest.of(page,pageSize));
    }

    public Group createNewGroup(GroupRequestDTO groupRequestDTO,String userId)
    {
        Group group = getGroupByGroupName(groupRequestDTO.getGroupName());
        if(group == null)
        {
               group = Group.builder()
                       .groupName(groupRequestDTO.getGroupName())
                       .groupDescription("Hello World")
                       .createdTime(LocalDateTime.now())
                       .updatedTime(LocalDateTime.now())
                       .superAdminUserId(userId)
                       .build();
               groupRepository.save(group);
        }

        return group;
    }

    public Group updateGroupById(String groupId, GroupRequestDTO groupRequestDTO)
    {
        return groupRepository
                .findByGroupId(groupId)
                .map(Group -> {
                    Group.setGroupName(groupRequestDTO.getGroupName());
                    Group.setGroupDescription(groupRequestDTO.getGroupDescription());
                    Group.setUpdatedTime(LocalDateTime.now());
                    return groupRepository.save(Group);
                })
                .orElseThrow(() -> new GroupNotFoundException(ExceptionConstants.GROUP_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    public Group getGroupById(String groupId)
    {
        return groupRepository
                .findByGroupId(groupId)
                .orElseThrow(() -> new GroupNotFoundException(ExceptionConstants.GROUP_NOT_FOUND,HttpStatus.NOT_FOUND));
    }

    public Group getGroupByGroupName(String groupName)
    {
        return groupRepository
                .findByGroupName(groupName)
                .orElse(null);
    }

    public void deleteGroupById(String userId)
    {
        groupRepository.deleteByGroupId(userId);
    }

    public void addMessageToGroup(Message message)
    {
        Group group = getGroupById(message.getGroupId());
        group.getMessages().add(message);
        groupRepository.save(group);
    }

    public List<Message> getMessages(String groupId,int page,int pageSize)
    {
        Group group = getGroupById(groupId);
        List<Message> messages = group.getMessages();
        int totalMessages = messages.size();
        int start = Math.max(0,totalMessages - (page+1) * pageSize);
        int end = Math.min(messages.size(),start + pageSize);
        return messages.subList(start,end);
    }

    public void joinGroup(String groupId,String user)
    {
        Group group = getGroupById(groupId);
        if(!group.getMembers().contains(user))
        {
            group.getMembers().add(user);
            groupRepository.save(group);
        }
    }

    public void leaveGroup(String groupId,String userToDeleteId)
    {
        Group group = getGroupById(groupId);
        group.getMembers().remove(userToDeleteId);
        groupRepository.save(group);
    }

    public List<String> getAllUsersInGroup(String groupId)
    {
        Group group = getGroupById(groupId);
        return group.getMembers();
    }

    public List<Group> getGroupsByGroupIds(List<String> groupIds)
    {
        return groupRepository.findAllByGroupIdIn(groupIds);
    }
}
