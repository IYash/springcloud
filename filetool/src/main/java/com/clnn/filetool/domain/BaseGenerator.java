package com.clnn.filetool.domain;

import com.clnn.filetool.entity.beanDesc.CustomBaseEntity;
import com.clnn.filetool.entity.beanDesc.CustomClz;
import com.clnn.filetool.entity.beanDesc.CustomImport;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

@Data
public abstract class BaseGenerator extends CustomBaseEntity {

    /**
     * 引入
     */
    protected List<CustomImport> customImports;
    /**
     * 类名
     */
    protected CustomClz customClz;

    private String targetPath;

    public  void generateBean() throws Exception{
        OutputStream outSTr = new FileOutputStream(new File(targetPath));
        BufferedOutputStream buff= new BufferedOutputStream(outSTr);
        StringBuilder sb = new StringBuilder();
        String imports = customImports.stream().map(CustomImport::buildImportInfo).collect(Collectors.joining(SEMICOLO));
        if(StringUtils.isNotEmpty(imports)){
            sb.append(imports);
            sb.append(SEMICOLO);
        }
        String clz = customClz.buildConstruct();
        sb.append(clz);
        String specifyBody = specifyBody();
        sb.append(specifyBody).append(RIGHT_PARENTHESIS);
        buff.write(sb.toString().getBytes());
        buff.flush();
        buff.close();
    }
    protected abstract String specifyBody();
}
