package com.project.bidding.controller;

import com.project.bidding.entity.Nomenclature;
import com.project.bidding.service.NomenclatureService;
import com.project.bidding.utils.XLSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin/setting/bidding")
public class CustomBiddingController {

    @Value("${upload.path}")
    private String uploadPath;

    private final NomenclatureService nomenclatureService;

    @Autowired
    public CustomBiddingController(NomenclatureService nomenclatureService) {
        this.nomenclatureService = nomenclatureService;
    }


    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<Void> parseBidingFile(@RequestParam("file") MultipartFile file) throws IOException {

       if (file != null && !file.getOriginalFilename().isEmpty()) {
           File uploadDir = new File(uploadPath);

           if (!uploadDir.exists())
               uploadDir.mkdir();

           File xlsfile = new File(uploadPath + "/" + file.getOriginalFilename());
           file.transferTo(xlsfile);

           List<Nomenclature> nomenclatures = XLSUtil.getNomeclaturesFromFile(xlsfile);

           nomenclatureService.removeAll();
           nomenclatureService.saveAll(nomenclatures);

           return new ResponseEntity<Void>(HttpStatus.OK);
       }

        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.GET, consumes = {"application/json"})
    ResponseEntity<List<Nomenclature>> getNomeclatures() {
        List<Nomenclature> nomenclatures = nomenclatureService.findAll();

        return new ResponseEntity<>(nomenclatures, HttpStatus.OK);
    }
}
