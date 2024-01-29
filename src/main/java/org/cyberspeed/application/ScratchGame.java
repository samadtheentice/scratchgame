package org.cyberspeed.application;

import org.cyberspeed.exception.ConfigFileParsingException;
import org.cyberspeed.exception.ScratchGameValidationException;
import org.cyberspeed.model.*;

import org.cyberspeed.service.ScratchGameBoard;
import org.cyberspeed.service.GameBoard;
import org.cyberspeed.util.ScratchGameUtil;
import org.cyberspeed.validator.ScratchGameValidator;

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