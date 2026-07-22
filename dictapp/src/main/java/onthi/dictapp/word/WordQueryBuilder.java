/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onthi.dictapp.word;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ASUS
 */
public class WordQueryBuilder {
    private String keyword;
    private Integer typeId;
    private int limit = 100;
    private final List<Object> params = new ArrayList<>();

    public WordQueryBuilder keyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public WordQueryBuilder typeId(Integer typeId) {
        this.typeId = typeId;
        return this;
    }

    public WordQueryBuilder limit(int limit) {
        this.limit = limit;
        return this;
    }

    public String build() {
        params.clear();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT w.id, w.english, w.pronunciation, w.vietnamese, wt.name as wordType ");
        sql.append(" FROM word w ");
        sql.append(" INNER JOIN word_type wt ON w.type_id = wt.id ");
        sql.append(" WHERE 1 = 1 ");

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (w.english LIKE ? OR w.vietnamese LIKE ?)");
            String searchPattern = "%" + keyword.trim() + "%";
            params.add(searchPattern);
            params.add(searchPattern);
        }

        if (typeId != null && typeId > 0) {
            sql.append(" AND w.type_id = ?");
            params.add(typeId);
        }

        sql.append(" ORDER BY w.id DESC LIMIT ?");
        params.add(limit);

        return sql.toString();
    }

    public List<Object> getParams() {
        return params;
    }
}
