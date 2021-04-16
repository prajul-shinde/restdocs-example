package com.csi.sfgrestdocsexample.web.controller;

import com.csi.sfgrestdocsexample.domain.Beer;
import com.csi.sfgrestdocsexample.repositories.BeerRepository;
import com.csi.sfgrestdocsexample.web.model.BeerDto;
import com.csi.sfgrestdocsexample.web.model.BeerStyleEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest(BeerController.class)
@ComponentScan(basePackages = "com.csi.sfgrestdocsexample.web.mappers")
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    BeerRepository beerRepository;

    @Test
    void getBeerById() throws Exception {
        given(beerRepository.findById(any())).willReturn(Optional.of(Beer.builder().build()));
        mockMvc.perform(get("/api/v1/beer/{beerId}", UUID.randomUUID().toString())
                .param("iscold","yes")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("v1/beer",pathParameters(
                        parameterWithName("beerId").description("UUID of desired beer to get.")),
                        requestParameters(parameterWithName("iscold").description("is beer cold? query paramter")
                ),responseFields(
                        fieldWithPath("id").description("id of beer"),
                        fieldWithPath("version").description("version number"),
                        fieldWithPath("createdDate").description("date created"),
                        fieldWithPath("lastModifiedDate").description("date updated"),
                        fieldWithPath("beerName").description("Beer name"),
                        fieldWithPath("beerStyle").description("beer style"),
                        fieldWithPath("upc").description("upc of beer"),
                        fieldWithPath("price").description("price of beer"),
                        fieldWithPath("quantityOnHand").description("quantity on hand")
                        )));

    }

    @Test
    void saveNewBeer() throws Exception {
        BeerDto beerDto = getValidBeerDto();
        String beerToJson = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(post("/api/v1/beer/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerToJson))
                .andExpect(status().isCreated())
        .andDo(document("v1/beer",requestFields(
                fieldWithPath("id").ignored(),
                fieldWithPath("version").ignored(),
                fieldWithPath("createdDate").ignored(),
                fieldWithPath("lastModifiedDate").ignored(),
                fieldWithPath("beerName").description("Beer Name"),
                fieldWithPath("beerStyle").description("Beer style"),
                fieldWithPath("upc").description("Beer upc").attributes(),
                fieldWithPath("price").description("price of beer"),
                fieldWithPath("quantityOnHand").ignored()
        )));

    }


    @Test
    void updateBeerById() throws Exception {
        BeerDto beerDto = getValidBeerDto();
        String beerToJson = objectMapper.writeValueAsString(beerDto);
        mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerToJson))
                .andExpect(status().isNoContent());

    }

    private BeerDto getValidBeerDto() {
        return BeerDto
                .builder()
                .beerName("Nice Ale")
                .beerStyle(BeerStyleEnum.ALE)
                .price(new BigDecimal("9.99"))
                .upc(123123123123L)
                .build();
    }
}