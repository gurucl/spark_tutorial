package com.mycompany.myproject.core;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.column.page.PageReadStore;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.example.data.simple.convert.GroupRecordConverter;
import org.apache.parquet.format.converter.ParquetMetadataConverter;
import org.apache.parquet.hadoop.ParquetFileReader;
import org.apache.parquet.hadoop.metadata.ParquetMetadata;
import org.apache.parquet.io.ColumnIOFactory;
import org.apache.parquet.io.MessageColumnIO;
import org.apache.parquet.io.RecordReader;
import org.apache.parquet.schema.MessageType;
import org.apache.parquet.schema.Type;

// https://www.jofre.de/?p=1459    => Origional Source Code at
public class ParquetDataReader {

    private static Path path = new Path("file:///Users/gcl/Documents/Github/spark_tutorial/src/main/resources/userdata1.parquet");

    static Configuration conf = new Configuration();
    static List<String> columnsList = new ArrayList<>();

    public static void main(String[] args) throws IllegalArgumentException {

        getColumnsList(path);

        for (String str:columnsList){
            System.out.println(str);
        }
    }


    private static void getColumnsList(Path filePath) {

        Path path = filePath;


        try {
            ParquetMetadata readFooter = ParquetFileReader.readFooter(conf, path, ParquetMetadataConverter.NO_FILTER);
            MessageType schema = readFooter.getFileMetaData().getSchema();
            ParquetFileReader r = new ParquetFileReader(conf, path, readFooter);

            PageReadStore pages = null;
            try {
                while (null != (pages = r.readNextRowGroup())) {
                    final long rows = pages.getRowCount();
                    System.out.println("Number of rows: " + rows);

                    final MessageColumnIO columnIO = new ColumnIOFactory().getColumnIO(schema);
                    final RecordReader recordReader = columnIO.getRecordReader(pages, new GroupRecordConverter(schema));
                    for (int i = 0; i <= rows; i++) {

                        final Group g = (Group)recordReader.read();
                        columnsList.add(g.getType().getFieldName(i));
                        printGroup(g);

                        // TODO Compare to System.out.println(g);
                    }
                }
            } finally {
                r.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading parquet file.");
            e.printStackTrace();
        }


    }


    private static void printGroup(Group g) {

        int fieldCount = g.getType().getFieldCount();
        for (int field = 0; field<= fieldCount; field++) {
            int valueCount = g.getFieldRepetitionCount(field);

            Type fieldType = g.getType().getType(field);
            String fieldName = fieldType.getName();

            columnsList.add(fieldName);
            for (int index = 0; index <=  valueCount; index++) {
                if (fieldType.isPrimitive()) {
                    System.out.println(fieldName + " " + g.getValueToString(field, index));
                }
            }
        }
        System.out.println("");
    }
}

