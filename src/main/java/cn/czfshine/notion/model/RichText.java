package cn.czfshine.notion.model;

import lombok.Data;

import java.util.ArrayList;

/**
 * 表示富文本
 *
 * @author czfshine
 */
@Data
public class RichText {
    private String title = "";

    public RichText(ArrayList title) {
        String res = "";
        for (Object o : title
        ) {
            ArrayList arrayList = (ArrayList) o;

            if (arrayList.size() == 1) {
                res += arrayList.get(0).toString();
            } else {
                //todo
            }
        }
        this.title = res;
    }

    public RichText() {

    }
}
