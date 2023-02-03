package com.testing.investment.controller;

import com.testing.investment.entity.Reward;
import com.testing.investment.service.RewardService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

// TODO Single Controller with one GET and one POST method

@AllArgsConstructor
@SessionAttributes({"params"})
@Controller
@RequestMapping({"/calculator", "", "/", "/index"})
public class RewardController {

    //TODO Connects investmentService class
    RewardService rewardService;


    @ModelAttribute("params")
    public Reward params() {
        return new Reward();
    }


    // TODO Loads calculator page, when go to localhost:8080/calculator

    @GetMapping
    public String getParams() {
        return "calculator";
    }

    // TODO Loads CSV download page, when go to localhost:8080/calculator/download

    @GetMapping("/download")
    public void downloadCsv(@ModelAttribute("params") Reward reward, HttpServletResponse response, Model model) throws Exception {

        String filename = "rewardSchedule " + LocalDate.now() + ".csv";
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);

        rewardService.exportCsv(reward, response);

    }

//     TODO Saves user input data and show calculation on localhost:8080/calculator

    @PostMapping
    public String returnResult(@Valid @ModelAttribute("params") Reward reward, BindingResult result, Model model) {

        // errors handling
        if (result.hasErrors()) {
            return "calculator";
        }

        model.addAttribute("rewardList", rewardService.rewardScheduleCalculation(reward));
        model.addAttribute("params", reward);

        return "calculator";
    }

}
