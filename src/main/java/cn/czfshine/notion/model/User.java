package cn.czfshine.notion.model;

import cn.czfshine.notion.model.block.Block;
import lombok.Data;

import java.util.List;

@Data
public class User extends Block {

    private String role;
    private String email;
    private String givenName;
    private String familyName;
    private String profilePhoto;//头像

    private List<WorkSpace> workSpaces;

    public List<WorkSpace> getWorkSpaces() {
        return workSpaces;
    }
}
