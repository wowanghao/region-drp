package io.terminus.common.data.transfer.demo.importer;

import io.terminus.common.data.transfer.spi.models.DataImportContext;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MyTestImportContext extends DataImportContext {

    private String myField;

}
