package ru.geekbrains.pocket.backend.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.pocket.backend.domain.GroupMembers;

@Repository
public interface GroupMembersRepository extends MongoRepository<GroupMembers, ObjectId> {
}
