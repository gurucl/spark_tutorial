package com.mycompany.myproject.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.column.ColumnDescriptor;
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
import org.apache.parquet.schema.PrimitiveType;
import org.apache.parquet.schema.Type;


public class ParquetReader {

    private static Path path = new Path("file:///Users/gcl/Documents/Github/spark_tutorial/src/main/resources/userdata1.parquet");

    static Configuration conf = new Configuration();

    public static void main(String[] args) throws IllegalArgumentException {

        Map<String, String> colsMap = getColumnsList(path);

        for (String str:colsMap.keySet()){
            System.out.println(str + " : " + colsMap.get(str).toString());
        }
    }


    private static Map<String, String> getColumnsList(Path filePath) {

        Path path = filePath;
        Map<String, String> columnsList = new HashMap<>();

        try {
            ParquetMetadata readFooter = ParquetFileReader.readFooter(conf, path, ParquetMetadataConverter.NO_FILTER);
            MessageType schema = readFooter.getFileMetaData().getSchema();
            ParquetFileReader r = new ParquetFileReader(conf, path, readFooter);

            int fieldsCount = schema.getFieldCount();

            List<Type> listOfTypes = schema.getFields();
           // List<Type> fields = schema.getFields();
//            List<ColumnDescriptor> parquetCols = schema.getColumns();
//            for (ColumnDescriptor cd: parquetCols){
//                cd.getType();
//           }
//schema.getFields().get(3).toString()
//((PrimitiveType) schema.fields.get(0)).primitive.javaType
           // for(int i=0; i<fieldsCount; i++){

//                String FieldName = schema.getFieldName(i); // schema.asGroupType().getFieldName(i);//
//                String FieldValue = schema.getType(i).getName(); //.getType(i).getName();

                for ( Type tp:schema.getFields()){

                    String FieldName = tp.getName();
                    String FieldType = tp.asPrimitiveType().getPrimitiveTypeName().name();//((PrimitiveType) tp).getPrimitiveTypeName().name();

//                     switch (FieldValue) {
//
//                        case FieldValue.contains("binary"):      FieldType = "string";       break;
//                        case FieldValue.contains("int32"):       FieldType = "int";          break;
//                        case FieldValue.contains("int96"):       FieldType = "long";         break;
//                        case FieldValue.contains("double"):      FieldType = "double";       break;
//                        default: FieldType = "string";
//                    }
//
//                    if (FieldValue.contains("binary")){
//                        FieldType = "string";
//                    }else if(FieldValue.contains("int")){
//                        FieldType = "int";
//                    }else if(FieldValue.contains("int96")){
//                        FieldType = "long";
//                    }else if(FieldValue.contains("double")){
//                        FieldType = "double";
//                    }
//                    System.out.print(tp.toString());
//                    System.out.print(tp.getName());
//                    System.out.print(tp.getId());
//                    System.out.print(tp.isPrimitive());
//                    System.out.println();
                    columnsList.put(FieldName, FieldType);
                }

                //columnsList.put(FieldName, FieldValue);
           // }

//            PageReadStore pages = null;
//            try {
//                while (null != (pages = r.readNextRowGroup())) {
//                    final long rows = pages.getRowCount();
//                    System.out.println("Number of rows: " + rows);
//
//                    final MessageColumnIO columnIO = new ColumnIOFactory().getColumnIO(schema);
//                    final RecordReader recordReader = columnIO.getRecordReader(pages, new GroupRecordConverter(schema));
//                    for (int i = 0; i <= rows; i++) {
//
//                        final Group g = (Group)recordReader.read();
//                        columnsList.add(g.getType().getFieldName(i));
//                        printGroup(g);
//
//                        // TODO Compare to System.out.println(g);
//                    }
//                }
//            } finally {
//                r.close();
//            }
        } catch (IOException e) {
            System.out.println("Error reading parquet file.");
            e.printStackTrace();
        }

        return columnsList;

    }


    private static void printGroup(Group g) {

        int fieldCount = g.getType().getFieldCount();
        for (int field = 0; field<= fieldCount; field++) {
            int valueCount = g.getFieldRepetitionCount(field);

            Type fieldType = g.getType().getType(field);
            String fieldName = fieldType.getName();

            //columnsList.add(fieldName);
            for (int index = 0; index <=  valueCount; index++) {
                if (fieldType.isPrimitive()) {
                    System.out.println(fieldName + " " + g.getValueToString(field, index));
                }
            }
        }
        System.out.println("");
    }
}
