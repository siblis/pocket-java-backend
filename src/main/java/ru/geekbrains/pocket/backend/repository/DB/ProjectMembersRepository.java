package ru.geekbrains.pocket.backend.repository.DB;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.geekbrains.pocket.backend.domain.entitiesDB.ProjectMembers;

public interface ProjectMembersRepository extends MongoRepository<ProjectMembers,String> {
}
