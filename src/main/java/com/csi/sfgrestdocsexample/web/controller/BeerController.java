package com.csi.sfgrestdocsexample.web.controller;

import com.csi.sfgrestdocsexample.repositories.BeerRepository;
import com.csi.sfgrestdocsexample.web.mappers.BeerMapper;
import com.csi.sfgrestdocsexample.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/beer")
@RequiredArgsConstructor
public class BeerController {

    private final BeerMapper beerMapper;
    private final BeerRepository beerRepository;

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto>getBeerById(@PathVariable UUID beerId){
        return new ResponseEntity<>(beerMapper.beerToBeerDto(beerRepository.findById(beerId).get()), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity saveNewBeer(@RequestBody @Valid BeerDto beerDto){
        beerRepository.save(beerMapper.beerDtoToBeer(beerDto));
        return new ResponseEntity(HttpStatus.CREATED);
    }
    @PutMapping("/{beerId}")
    public ResponseEntity updateBeerById(@PathVariable UUID beerId,@Valid @RequestBody BeerDto beerDto){
        beerRepository.findById(beerId).ifPresent(beer ->{
            beer.setBeerName(beerDto.getBeerName());
            beer.setBeerStyle(beerDto.getBeerStyle().name());
            beer.setPrice(beerDto.getPrice());
            beer.setUpc(beerDto.getUpc());

            beerRepository.save(beer);
        });
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
