package ru.geekbrains.pocket.backend.domain.entitiesDB;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("profile")
public class Profile {
    String name;

    @Field("last_name")
    String lastName;

    @Field("last_seen")
    Date lastSeen;
}
