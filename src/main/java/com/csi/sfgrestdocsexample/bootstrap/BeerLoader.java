package com.csi.sfgrestdocsexample.bootstrap;

import com.csi.sfgrestdocsexample.domain.Beer;
import com.csi.sfgrestdocsexample.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class BeerLoader implements CommandLineRunner {
    private final BeerRepository beerRepository;
    @Override
    public void run(String... args) throws Exception {
        loadBeerObjects();
    }

    private void loadBeerObjects() {
        if(beerRepository.count()==0){
            beerRepository.save(Beer
                    .builder()
                    .beerName("Mango Bobs")
                    .beerStyle("IPA")
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .upc(334423444L)
                    .price(new BigDecimal("12.95"))
                    .build());
            beerRepository.save(Beer
                    .builder()
                    .beerName("Galaxy Cat")
                    .beerStyle("PALE_ALE")
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .upc(334423444L)
                    .price(new BigDecimal("11.95"))
                    .build());
        }
    }
}
