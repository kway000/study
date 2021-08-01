package com.zzs.bigdata.service;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.List;

public class HBaseUtils {

    private static Configuration config = HBaseConfiguration.create();

    private static Connection connection;

    private static Admin admin;

    private HBaseUtils() {

    }

    public static void setHBaseConfigAndConnection(String ip, String port) throws IOException {
        config.set("hbase.zookeeper.quorum", ip);
        config.set("hbase.zookeeper.property.clientPort", port);
//        config.set("hbase.master", master);
        connection = ConnectionFactory.createConnection(config);
        admin = connection.getAdmin();
    }

    public static void createTable(String tableName, List<String> columnFamily) throws IOException {
        TableName tn = TableName.valueOf(tableName);
        if (!admin.tableExists(tn)) {
            HTableDescriptor htd = new HTableDescriptor(tn);
            for (int i = 0; i < columnFamily.size(); ++i) {
                String column = columnFamily.get(i);
                htd.addFamily(new HColumnDescriptor(column));
            }

            if (tableName.contains(":")) {
                String nameSpace = tableName.substring(0, tableName.indexOf(":"));
                try {
                    NamespaceDescriptor namespaceDescriptor = admin.getNamespaceDescriptor(nameSpace);
                    System.out.println("namespaceDescriptor : " + namespaceDescriptor.getName());
                } catch (NamespaceNotFoundException e) {
                    admin.createNamespace(NamespaceDescriptor.create(nameSpace).build());
                }
            }
            admin.createTable(htd);
            admin.close();
        }
    }

    public static void insertData(String tableName, Student student) throws IOException {
        TableName tn = TableName.valueOf(tableName);

        Table table = connection.getTable(tn);

        Put put = new Put(student.getName().getBytes());

        put.addColumn("info".getBytes(), "student_id".getBytes(), student.getInfo().getStudentId().getBytes());
        put.addColumn("info".getBytes(), "class".getBytes(), student.getInfo().getStudentClass().getBytes());
        put.addColumn("score".getBytes(), "understanding".getBytes(), student.getScore().getUnderstanding().getBytes());
        put.addColumn("score".getBytes(), "programming".getBytes(), student.getScore().getProgramming().getBytes());

        table.put(put);
        table.close();
    }

    public static Result getByRowKey(String tableName, String rowkey) throws IOException {
        TableName tn = TableName.valueOf(tableName);
        if (admin.tableExists(tn)) {
            Table table = connection.getTable(tn);
            return table.get(new Get(Bytes.toBytes(rowkey)));
        } else {
            return null;
        }
    }

    public static void deleteByRowKey(String tableName, String rowKey) throws IOException {
        TableName tn = TableName.valueOf(tableName);
        Table table = connection.getTable(tn);
        Delete delete = new Delete(Bytes.toBytes(rowKey));
        table.delete(delete);
    }

    public static void deleteTable(String tablename) throws IOException {
        TableName tn = TableName.valueOf(tablename);
        try {
            if (admin.tableExists(tn)) {
                admin.disableTable(tn);
                admin.deleteTable(tn);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
