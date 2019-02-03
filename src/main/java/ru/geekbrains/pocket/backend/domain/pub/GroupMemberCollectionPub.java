package ru.geekbrains.pocket.backend.domain.pub;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupMemberCollectionPub {

    private String group_id;

    private int offset;

    private List<GroupMemberPub> data;

    @Override
    public String toString() {
        return "GroupPub{" +
                "'group_id':'" + group_id + "'" +
                ", 'offset':'" + offset + "'" +
                ", 'data':'" + data + "'" +
                '}';
    }

}
