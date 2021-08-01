package com.zzs.bigdata;

import com.zzs.bigdata.service.HBaseUtils;
import com.zzs.bigdata.service.Student;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;

import java.io.IOException;
import java.util.Arrays;

public class HBaseMain {
    private static final String STUDENT_NAME = "zouxiaosheng";
    private static final String STUDENT_ID = "G20210735010471";
    private static final String TABLE_NAME = STUDENT_NAME + ":student";

    public static void main(String[] args) {
        try {
            //配置和连接,正式环境需要做配置文件
            HBaseUtils.setHBaseConfigAndConnection("jikehadoop01,jikehadoop02", "2181");

            //创建表和列族
            HBaseUtils.createTable(TABLE_NAME, Arrays.asList("name", "info", "score"));

            //插入数据
            HBaseUtils.insertData(TABLE_NAME, Student.createStudent(STUDENT_NAME, STUDENT_ID, "5", "99", "100"));

            //查询数据
            Result result = HBaseUtils.getByRowKey(TABLE_NAME, STUDENT_NAME);

            result.listCells().forEach(cell -> {
                System.out.println("rowkey:" + STUDENT_NAME);
                System.out.println("family:" + new String(CellUtil.cloneFamily(cell)));
                System.out.println("value:" + new String(CellUtil.cloneValue(cell)));
            });

            //删除数据
           HBaseUtils.deleteByRowKey(TABLE_NAME, STUDENT_NAME);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
