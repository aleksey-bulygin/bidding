package com.project.bidding.controller;

import com.project.bidding.entity.Nomenclature;
import com.project.bidding.service.NomenclatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bidding/check")
public class BiddingController {

    private final NomenclatureService nomenclatureService;

    @Autowired
    public BiddingController(NomenclatureService nomenclatureService) {
        this.nomenclatureService = nomenclatureService;
    }

    @RequestMapping(method = RequestMethod.GET, consumes = {"application/json"})
    public ResponseEntity<List<Nomenclature>> getNomenclatures() {
        List<Nomenclature> nomenclatures = nomenclatureService.findAll();

        return new ResponseEntity<>(nomenclatures, HttpStatus.OK);
    }
}
