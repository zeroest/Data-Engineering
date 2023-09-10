package me.zeroest.hbase.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRepository<T extends HBaseData> {
    @Getter
    protected String tableName;
    protected Table table;

    @Getter
    protected byte[] cf = Bytes.toBytes("cf");
    @Getter
    protected byte[] qualifier = Bytes.toBytes("q");

    protected ObjectMapper objectMapper = new ObjectMapper();

    public abstract T readValue(byte[] raw) throws IOException;

    protected AbstractRepository(String tableName, Connection connection) throws IOException {
        this.tableName = tableName;
        this.table = connection.getTable(TableName.valueOf(tableName));
    }

    public T get(byte[] rowKey) throws IOException {
        Get get = new Get(rowKey);
        Result result = table.get(get);
        byte[] raw = result.getValue(cf, qualifier);
        return readValue(raw);
    }

    public void put(T value) throws IOException {
        Put put = new Put(value.getRowKey());
        put.addColumn(cf, qualifier, objectMapper.writeValueAsBytes(value));
        table.put(put);
    }

    public List<T> scanPrefix(byte[] prefix, int limit) throws IOException {
        Filter filter = new PrefixFilter(prefix);
        return scan(filter, limit);
    }

    public List<T> scan(T value, int limit, boolean isReverse) throws IOException {
        CompareOperator compareOperator;
        if (isReverse) {
            compareOperator = CompareOperator.LESS_OR_EQUAL;
        } else {
            compareOperator = CompareOperator.GREATER_OR_EQUAL;
        }

        // choose filter for your purpose from here - https://hbase.apache.org/apidocs/org/apache/hadoop/hbase/filter/package-summary.html
        Filter filter = new RowFilter(compareOperator, new BinaryComparator(value.getRowKey()));
        return scan(filter, limit);
    }

    private List<T> scan(Filter filter, int limit) throws IOException {
        Scan scan = new Scan();
        scan.addColumn(cf, qualifier);
        scan.setFilter(filter);
        scan.setLimit(limit);

        ResultScanner resultScanner = table.getScanner(scan);
        List<T> results = new ArrayList<>();
        for (Result result : resultScanner) {
            byte[] raw = result.getValue(cf, qualifier);
            T resultValue = readValue(raw);
            results.add(resultValue);
        }
        return results;
    }
}