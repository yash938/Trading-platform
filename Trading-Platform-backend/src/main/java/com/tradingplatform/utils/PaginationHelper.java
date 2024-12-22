package com.tradingplatform.utils;

import com.tradingplatform.dto.PageResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class PaginationHelper {
    @Autowired
    private  static final ModelMapper modelMapper = new ModelMapper();
    public static <U,V> PageResponse<V> getPages(Page<U> page,Class<V> type){
        List<V> collect = page.getContent()
                .stream()
                .map(object -> modelMapper.map(object,type))
                .collect(Collectors.toList());

        PageResponse<V> pageResponse  = new PageResponse<>();
        pageResponse.setContent(collect);
        pageResponse.setPageNumber(page.getNumber());
        pageResponse.setPageSize(page.getSize());
        pageResponse.setTotalPages(page.getTotalPages());
        pageResponse.setTotalElements(page.getTotalElements());
        pageResponse.setLastPage(page.isLast());

        return pageResponse;
    }
}
