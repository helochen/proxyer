package com.mhxh.proxyer.fake.command.v1.base;

import com.google.common.collect.Lists;
import com.mhxh.proxyer.fake.command.v1.remote.refuse.IRefuseFilter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

public abstract class LocalBaseCommand extends BaseCommand implements IType {

    private List<IRefuseFilter> refuseFilters = null;

    public void beforeCommandAddFilters() {
        if (!CollectionUtils.isEmpty(refuseFilters)) {
            refuseFilters.forEach(IRefuseFilter::addOneTime);
        }
    }

    public void addRefuseFilter(IRefuseFilter filter) {
        if (ObjectUtils.isEmpty(refuseFilters)) {
            refuseFilters = Lists.newArrayList();
        }
        refuseFilters.add(filter);
    }

    public LocalBaseCommand(String pattern) {
        super(pattern);
    }

    @Override
    public String format() {
        return this.format(getTime() ,getVerify());
    }

    @Override
    public int type() {
        return 1;
    }
}
