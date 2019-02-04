package ru.geekbrains.pocket.backend.domain.pub;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupMemberPub {

    private String role;

    private UserProfilePub userProfile;

    @Override
    public String toString() {
        return "GroupMemberPub{" +
                "role='" + role + '\'' +
                ", userProfile=" + userProfile +
                '}';
    }
}
