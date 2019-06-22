package io.terminus.common.data.transfer.demo.exporter.db;

import io.terminus.common.data.transfer.spi.annotations.DataExporter;
import io.terminus.common.data.transfer.spi.exceptions.DataTransferException;
import io.terminus.common.data.transfer.spi.ifaces.IDataExporter;
import io.terminus.common.data.transfer.spi.models.DataExportContext;
import io.terminus.common.model.Paging;
import io.terminus.common.model.PagingCriteria;
import lombok.var;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * date 2018/9/27
 *
 * @author yushuo
 */

@DataExporter(name = "demo_db")
public class DbDataExporter implements IDataExporter<Map, DataExportContext> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DbDataExporter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Paging<Map> exportData(PagingCriteria page, DataExportContext context) throws DataTransferException {
        if (!(page instanceof DbPagingCriteria)) {
            throw new IllegalArgumentException("criteria is not DbPagingCriteria");
        }

        DbPagingCriteria criteria = (DbPagingCriteria) page;

        var countSql = sqlForCount(criteria);
        Long total = jdbcTemplate.queryForObject(countSql.getLeft(), Long.class, countSql.getRight());
        var query = sqlForQuery(criteria);
        List<Map<String, Object>> result = jdbcTemplate.queryForList(query.getLeft(), query.getRight());

        //noinspection unchecked
        return new Paging(total, result);
    }

    protected Pair<String, Object[]> sqlForQuery(DbPagingCriteria criteria) {
        String table = criteria.getTable();
        List<String> fields = criteria.getFields();
        String sqlPrepareStatement;
        if(CollectionUtils.isEmpty(fields)) {
            sqlPrepareStatement = MessageFormat.format(
                    "SELECT * FROM {0} LIMIT ?, ?;", table
            );

        } else {
            String placeHolder = String.join(",",
                    fields.stream().map(x -> "`" + x + "`").collect(Collectors.toList())
            );
            sqlPrepareStatement = MessageFormat.format(
                    "SELECT {0} FROM {1} LIMIT ?, ?;", placeHolder, table
            );
        }

        long offset = (long) (criteria.getOffset() == null ? 0 : criteria.getOffset());
        Integer limit = criteria.getPageSize();

        return Pair.of(sqlPrepareStatement, new Object[]{
                offset, limit
        });
    }

    protected Pair<String, Object[]> sqlForCount(DbPagingCriteria criteria) {
        String table = criteria.getTable();
        String sqlPrepareStatement = MessageFormat.format(
                "SELECT COUNT(*) FROM {0};", table
        );
        return Pair.of(sqlPrepareStatement, new Object[]{});
    }

    @Override
    public Class<DbPagingCriteria> criteriaClazz() {
        return DbPagingCriteria.class;
    }

}
