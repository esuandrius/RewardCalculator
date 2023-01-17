package com.testing.investment.service;

import com.testing.investment.entity.Reward;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// TODO Single service class for business logic

@Service
public class RewardService {


    public List<Reward> rewardScheduleCalculation(Reward reward) {

        // TODO Initializing required fields

        List<Reward> rewardList = new ArrayList<>();
        int numberOfMonths = reward.getNumberOfMonths();
        LocalDate startDate = reward.getStartDate();
        double investedAmount = reward.getInvestedAmount();
        double rewardAmount = 0;
        double interestRate = reward.getInterestRate();
        double monthlyRewardRate = reward.getInterestRate() / 100 / 12;
        double totalRewardAmount = 0;
        String reinvestStakingReward = reward.getReinvestStakingReward();

        // TODO main logic of reward schedule calculation

        for(int number = 1; number <= numberOfMonths; number++) {

            if (reinvestStakingReward.equals("1")) {
                rewardAmount = 0;
            }

            startDate = startDate.plusMonths(1);
            investedAmount += Math.round(rewardAmount * 1000000.0) / 1000000.0;
            rewardAmount = Math.round(investedAmount * monthlyRewardRate * 1000000.0) / 1000000.0;
            totalRewardAmount += Math.round(rewardAmount * 100000.0) / 100000.0;

            Reward newReward = new Reward(number, startDate, investedAmount, rewardAmount, interestRate, totalRewardAmount);
            rewardList.add(newReward);
        }

        // TODO Returning list to controller, where it will be printed in to table

        return rewardList;
    }

    // TODO export data to CSV file logic

    public void exportCsv(Reward reward, HttpServletResponse response) throws IOException {
        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {"Line #", "Reward Date", "Investment Amount (ETH)", "Reward Amount (ETH)", "Total Reward Amount to Date (ETH)", "Yearly Staking Reward (annual, %)"};
        String[] fieldMapping = {"numberOfMonths", "startDate", "investedAmount", "rewardAmount", "totalRewardAmount", "interestRate"};

        List<Reward> rewardList = rewardScheduleCalculation(reward);

        csvWriter.writeHeader(csvHeader);

        for(Reward rew : rewardList) {
            csvWriter.write(rew, fieldMapping);
        }

        csvWriter.close();
    }

}
