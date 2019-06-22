package io.terminus.common.data.transfer.spi.defaults.formatter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.terminus.common.data.transfer.spi.ifaces.IDataFormatter;
import lombok.var;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * date 2018/9/26
 *
 * @author yushuo
 */
public class XlsDataFormatter<T> implements IDataFormatter<T> {

    private final ObjectMapper mapper = new ObjectMapper();

    private static final Integer PAGE_SIZE = 2000;
    private final SXSSFWorkbook xlsBook;
    private final Sheet sheet;
    private final Class dataModel;
    private Map<String, String> headerAlias;

    private List<String> headers = new ArrayList<>();
    private int cursorLine = 0;

    public XlsDataFormatter(Class<T> dataModel) {
        xlsBook = new SXSSFWorkbook(PAGE_SIZE);
        sheet = xlsBook.createSheet();
        this.dataModel = dataModel;
    }

    @Override
    public void setHeaderAlias(Map<String, String> headerAlias) {
        this.headerAlias = headerAlias;
    }

    private List<String> getHeadersFromModel(Class dataModel) {
        List<String> ret = new ArrayList<>();
        for(Field field : dataModel.getDeclaredFields()) {
            var shouldIgnore = field.getAnnotation(JsonIgnore.class);
            if(null != shouldIgnore) {
                continue;
            }
            var property = field.getAnnotation(JsonProperty.class);
            String name = field.getName();
            if(null != property && StringUtils.isNotEmpty(property.value())) {
                name = property.value();
            }
            ret.add(name);
        }
        return ret;
    }

    private void writeHead(T data) {
        if(CollectionUtils.isNotEmpty(this.headers)) {
            return; // header has already written
        }

        Class headersModel = null == dataModel ? data.getClass() : dataModel;
        if(headersModel == null) {
            return;
        }

        if(Map.class.isAssignableFrom(headersModel)) {
            headers = Arrays.stream(((Map) data).keySet().toArray())
                    .map(String::valueOf).collect(Collectors.toList());
        } else {
            headers = getHeadersFromModel(headersModel);
        }

        Row row = sheet.createRow(0);
        for(int j=0; j<headers.size(); ++j) {
            String fieldName = headers.get(j);
            String aliasName;
            if(null != headerAlias && StringUtils.isNotEmpty(headerAlias.get(fieldName))) {
                aliasName = headerAlias.get(fieldName);
            } else {
                aliasName = fieldName;
            }
            Cell cell = row.createCell(j);
            cell.setCellValue(aliasName);
        }
    }

    @Override
    public void writeLine(T data, OutputStream outputStream) {
        if(null == data) {
            cursorLine+=1;
            return;
        }
        writeHead(data);
        Map dataMap;
        if(Map.class.isAssignableFrom(data.getClass())) {
            dataMap = (Map) data;
        } else {
            dataMap = mapper.convertValue(data, Map.class);
        }
        writeMap(dataMap, outputStream);
    }

    private void writeMap(Map data, OutputStream outputStream) {
        Row row = sheet.createRow(++cursorLine);
        for(int j=0; j<headers.size(); ++j) {
            String name = headers.get(j);
            String value = Objects.toString(data.get(name), "");
            Cell cell = row.createCell(j);
            cell.setCellValue(value);
        }
    }

    @Override
    public void flush(OutputStream outputStream) throws IOException {
        xlsBook.write(outputStream);
    }

}
