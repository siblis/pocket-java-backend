package ru.geekbrains.pocket.backend.domain.pub;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.geekbrains.pocket.backend.domain.db.Group;

import javax.validation.constraints.NotNull;

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

    private String invitation_code; //Видна только создателю группы и только если активна

    private boolean isPublic;

    public GroupPub(String creator, String name, boolean isPublic) {
        this.creator = creator;
        this.name = name;
        this.isPublic = isPublic;
    }

    public GroupPub(@NotNull Group group) {
        this.id = group.getId().toString();
        this.creator = group.getCreator().getEmail();
        //this.space = "";
        this.name = group.getName();
        this.description = group.getDescription();
        this.invitation_code = group.getInvitation_code();
        this.isPublic = group.isPublic();
    }

    @Override
    public String toString() {
        return "GroupPub{" +
                "'id':'" + id + "'" +
                ", 'creator':'" + creator + "'" +
                ", 'space':'" + space + "'" +
                ", 'name +':'" + name + "'" +
                ", 'description':'" + description + "'" +
                ", 'invitation_code':'" + invitation_code + "'" +
                ", 'isPublic':'" + isPublic + "'" +
                '}';
    }
}