package com.project.bidding.controller;

import com.project.bidding.entity.Costs;
import com.project.bidding.entity.Nomenclature;
import com.project.bidding.entity.PlayCosts;
import com.project.bidding.entity.User;
import com.project.bidding.repository.UserRepositories;
import com.project.bidding.service.NomenclatureService;
import com.project.bidding.service.PlayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping("/bidding/choose/play")
public class PlayController {

    @Resource(name = "userService")
    private UserDetailsService userDetailsService;

    private final PlayService playService;
    private final NomenclatureService nomenclatureService;
    private final UserRepositories userRepositories;

    @Autowired
    public PlayController(PlayService playService, NomenclatureService nomenclatureService, UserRepositories userRepositories) {
        this.playService = playService;
        this.nomenclatureService = nomenclatureService;
        this.userRepositories = userRepositories;
    }

    @RequestMapping(method = RequestMethod.GET, consumes = {"application/json"})
    public ResponseEntity<List<Nomenclature>> getPosition() {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
            String username = userDetails.getUsername();

            User user = userRepositories.findByUsername(username);

            List<Nomenclature> allNomenclatures = nomenclatureService.findAll();
            List<Nomenclature> choosePosition = new ArrayList<>();

            for (Nomenclature nomenclature : allNomenclatures) {
                Map<User, PlayCosts> tender = nomenclature.getTenders();

                if (tender.containsKey(user))
                    choosePosition.add(nomenclature);

            }
            return new ResponseEntity<>(choosePosition, HttpStatus.OK);
        } catch (Exception ex) {

        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT, consumes = {"application/json"})
    public ResponseEntity<Void> tender(@PathVariable Long id, @RequestBody Costs cost) {
        Optional<Nomenclature> nomenclatureById = nomenclatureService.findById(id);
        if (nomenclatureById.isPresent()) {
            Nomenclature nomenclature = nomenclatureById.get();

            try {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
                String username = userDetails.getUsername();

                User user = userRepositories.findByUsername(username);

                playService.saveCost(cost);

                PlayCosts playCosts = nomenclature.getTenders().get(user);
                Set<Costs> costsSet = playCosts.getCosts();

                costsSet.add(cost);
                playCosts.setCosts(costsSet);
                playService.savePlayCost(playCosts);

                Map<User, PlayCosts> tenders = nomenclature.getTenders();
                tenders.put(user, playCosts);

                nomenclatureService.save(nomenclature);

            } catch (Exception ex) {

            }
        }

        List<Nomenclature> nomenclatures = nomenclatureService.findAll();
        for (Nomenclature nomenclature : nomenclatures) {
            Double minCost = nomenclature.getCost();

            for (Map.Entry<User, PlayCosts> entry : nomenclature.getTenders().entrySet()) {

                for (Costs c : entry.getValue().getCosts()) {
                    if (c.getCost() != null && c.getCost() < minCost)
                        minCost = c.getCost();
                }
            }
            nomenclature.setMin_cost(minCost);
            nomenclatureService.save(nomenclature);
        }

        return new ResponseEntity<Void>(HttpStatus.OK);
    }


}
