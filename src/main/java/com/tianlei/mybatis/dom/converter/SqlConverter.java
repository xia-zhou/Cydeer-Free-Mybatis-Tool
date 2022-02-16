package com.tianlei.mybatis.dom.converter;

import com.intellij.util.xml.ConvertContext;
import com.tianlei.mybatis.dom.model.IdDomElement;
import com.tianlei.mybatis.dom.model.Mapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class SqlConverter extends IdBasedTagConverter {

    @NotNull
    @Override
    public Collection<? extends IdDomElement> getComparisons(@Nullable Mapper mapper, ConvertContext context) {
        return mapper.getSqls();
    }

}
