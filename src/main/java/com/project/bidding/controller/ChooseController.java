package com.project.bidding.controller;

import com.project.bidding.entity.Costs;
import com.project.bidding.entity.Nomenclature;
import com.project.bidding.entity.PlayCosts;
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

import com.project.bidding.entity.User;
import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping("/bidding/choose")
public class ChooseController {

    @Resource(name = "userService")
    private UserDetailsService userDetailsService;

    private final PlayService playService;
    private final NomenclatureService nomenclatureService;
    private final UserRepositories userRepositories;

    @Autowired
    public ChooseController(PlayService playService, NomenclatureService nomenclatureService, UserRepositories userRepositories) {
        this.playService = playService;
        this.nomenclatureService = nomenclatureService;
        this.userRepositories = userRepositories;
    }


    @RequestMapping(value = "{id}", method = RequestMethod.PUT, consumes = {"application/json"})
    public ResponseEntity<Void> putPostion(@PathVariable Long id, @RequestParam String target) {
        Optional<Nomenclature> nomenclatureById = nomenclatureService.findById(id);

        if (nomenclatureById.isPresent()) {
            Nomenclature nomenclature = nomenclatureById.get();

            try {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
                String username = userDetails.getUsername();

                User user = userRepositories.findByUsername(username);

                if ("add".equals(target)) {

                    if (nomenclature.getTenders().isEmpty())
                        nomenclature.setTenders(new HashMap<>());

                    if (!nomenclature.getTenders().keySet().contains(user)) {
                        Map<User, PlayCosts> tenders = nomenclature.getTenders();

                        Costs costs = new Costs();
                        playService.saveCost(costs);

                        PlayCosts playCosts = new PlayCosts();

                        Set<Costs> costsSet = new HashSet<>();
                        costsSet.add(costs);

                        playCosts.setCosts(costsSet);
                        playService.savePlayCost(playCosts);

                        nomenclature.getTenders().put(user, playCosts);
                    }
                } else {

                    if (nomenclature.getTenders().isEmpty()) {
                        nomenclature.setTenders(new HashMap<>());
                        return new ResponseEntity<Void>(HttpStatus.OK);
                    }

                    if (nomenclature.getTenders().keySet().contains(user)) {
                        PlayCosts playCosts = nomenclature.getTenders().get(user);
                        Set<Costs> costsSet = playCosts.getCosts();

                        playCosts.setCosts(null);
                        nomenclature.getTenders().put(user, null);
                        nomenclatureService.save(nomenclature);
                        playService.savePlayCost(playCosts);

                        for (Costs costs : costsSet)
                            playService.removeCosts(costs);

                        playService.removePlayCosts(playCosts);

                        nomenclature.getTenders().remove(user);
                     }

                }
            } catch (Exception ex) {
                System.out.println(ex.getStackTrace());
            }

            nomenclatureService.save(nomenclature);
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
