package com.app.chat.repository;

import com.app.chat.entity.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends MongoRepository<Group,Long> {
    Optional<Group> findByGroupId(String groupId);
    Optional<Group> findByGroupName(String groupName);
    void deleteByGroupId(String groupId);
    List<Group> findAllByGroupIdIn(List<String> groupIds);
}
