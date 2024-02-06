package com.gamecenter.application;

import com.gamecenter.exception.ConfigFileParsingException;
import com.gamecenter.model.GameConfigData;
import com.gamecenter.exception.ScratchGameValidationException;
import org.cyberspeed.model.*;

import com.gamecenter.service.ScratchGameBoard;
import com.gamecenter.service.GameBoard;
import com.gamecenter.util.ScratchGameUtil;
import com.gamecenter.validator.ScratchGameValidator;

public class ScratchGame {

    public static void main(String[] args) {

        try {
            ScratchGameValidator.validateProgramArgument(args);
        }catch(ScratchGameValidationException valExcep){
            System.out.println(valExcep.getMessage());
            System.exit(0);
        }

        try {
            final String finaleName = ScratchGameUtil.getFileName(args);
            final String betAmount = ScratchGameUtil.getBettingAmount(args);
            final GameConfigData gameConfigData = ScratchGameUtil.getScratchGameConfigData(finaleName);
            final GameBoard gb = new ScratchGameBoard(gameConfigData);
            gb.playGame(Double.parseDouble(betAmount));
        } catch (ConfigFileParsingException e) {
            throw new RuntimeException(e);
        }
    }
}