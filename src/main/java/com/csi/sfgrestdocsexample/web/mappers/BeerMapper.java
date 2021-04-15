package com.csi.sfgrestdocsexample.web.mappers;

import com.csi.sfgrestdocsexample.domain.Beer;
import com.csi.sfgrestdocsexample.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {
    BeerDto beerToBeerDto(Beer beer);
    Beer beerDtoToBeer(BeerDto beerDto);

}
