package ru.geekbrains.pocket.backend.domain.pub;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupMemberCollectionPub {

    private String group_id;

    private int offset;

    private ArrayList<GroupMemberPub> data;

    public GroupMemberCollectionPub(String group_id, int offset) {
        this.group_id = group_id;
        this.offset = offset;
    }
}
