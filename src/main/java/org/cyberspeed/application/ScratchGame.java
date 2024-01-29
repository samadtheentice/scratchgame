package org.cyberspeed.application;

import org.cyberspeed.constants.ScratchGameConstants;
import org.cyberspeed.exception.ConfigFileParsingException;
import org.cyberspeed.ScratchGameConfigParser;
import org.cyberspeed.model.*;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.cyberspeed.service.ScratchGameBoard;
import org.cyberspeed.service.GameBoard;

public class ScratchGame {

    public static void main(String[] args) {

        try {
            GameConfigData gameConfigData = ScratchGameConfigParser.getScratchGameConfigData("C:\\scratchgame\\src\\main\\resources\\config.json");

            GameBoard gb = new ScratchGameBoard(gameConfigData);
            gb.playGame();

        } catch (ConfigFileParsingException e) {
            throw new RuntimeException(e);
        }
    }
}