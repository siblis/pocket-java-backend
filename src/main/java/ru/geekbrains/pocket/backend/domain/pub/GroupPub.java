package ru.geekbrains.pocket.backend.domain.pub;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class GroupPub {

    private String id;

    private String creator;

    private String space;

    private String name;

    private String description;

    private String invitation_code;

    private boolean isPublic;

    public GroupPub(String creator, String name, boolean isPublic) {
        this.creator = creator;
        this.name = name;
        this.isPublic = isPublic;
    }

    @Override
    public String toString() {
        return "GroupPub{" +
                "id='" + id + '\'' +
                ", creator='" + creator + '\'' +
                ", space='" + space + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", invitation_code='" + invitation_code + '\'' +
                ", isPublic=" + isPublic +
                '}';
    }
}