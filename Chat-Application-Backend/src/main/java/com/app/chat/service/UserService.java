package com.app.chat.service;

import com.app.chat.constants.ExceptionConstants;
import com.app.chat.dto.user.UserRequestDTO;
import com.app.chat.entity.Group;
import com.app.chat.entity.User;
import com.app.chat.exception.UserConflictException;
import com.app.chat.exception.UserNotFoundException;
import com.app.chat.repository.GroupRepository;
import com.app.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.app.chat.constants.UserConstants.USER_ACTIVE;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    @Autowired
    UserService(UserRepository userRepository,GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    public Page<User> getAllUsers(int page, int pageSize)
    {
        return userRepository.findAll(PageRequest.of(page,pageSize,Sort.by(Sort.Direction.DESC,"updatedTime")));
    }

    public User createNewUser(UserRequestDTO userRequestDTO)
    {
        if(userRepository.findByUserName(userRequestDTO.getUserName()).isPresent())
        {
            throw new UserConflictException("User with the same username already exists", HttpStatus.CONFLICT);
        }

        User user = User.builder()
                        .userName(userRequestDTO.getUserName())
                        .userStatus(USER_ACTIVE)
                        .password(userRequestDTO.getPassword())
                        .createdTime(LocalDateTime.now())
                        .updatedTime(LocalDateTime.now())
                        .lastActiveTime(LocalDateTime.now())
                        .build();

        return userRepository.save(user);
    }

    public User updateUserById(String userId, UserRequestDTO userRequestDTO)
    {
        return userRepository
                .findByUserId(userId)
                .map(User -> {
                    User.setUserName(userRequestDTO.getUserName());
                    User.setUserStatus(USER_ACTIVE);
                    User.setPassword(userRequestDTO.getPassword());
                    User.setUpdatedTime(LocalDateTime.now());
                    return userRepository.save(User);
                })
                .orElseThrow(() -> new UserNotFoundException(ExceptionConstants.USER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    public User getUserById(String userId)
    {
        return userRepository
                .findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(ExceptionConstants.USER_NOT_FOUND,HttpStatus.NOT_FOUND));
    }

    public void deleteUserById(String userId)
    {
        userRepository.deleteByUserId(userId);
    }

    public User getUserByUserName(String userName)
    {
        return userRepository
                .findByUserName(userName)
                .orElseThrow(() -> new UserNotFoundException(ExceptionConstants.USER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    public User getUserByUserNameIfPresent(String userName)
    {
        return userRepository.findByUserName(userName).orElse(null);
    }

    public void addGroupForUser(String userName,String groupId)
    {
        User user = getUserByUserName(userName);
        if(!user.getUserVsGroups().contains(groupId))
        {
            user.getUserVsGroups().add(groupId);
            userRepository.save(user);
        }
    }

    public void removeGroupForUser(String userId,String groupId)
    {
        User user = getUserById(userId);
        user.getUserVsGroups().remove(groupId);
        userRepository.save(user);
    }

    public List<Group> getAllGroupsForUser(String userId)
    {
        List<String> groupIds = getUserByUserName(userId).getUserVsGroups();
        return groupRepository.findAllByGroupIdIn(groupIds);
    }
}
