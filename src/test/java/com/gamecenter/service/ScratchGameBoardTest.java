package com.gamecenter.service;

import com.gamecenter.model.GameConfigData;
import com.gamecenter.util.TestDataBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ScratchGameBoardTest {
    private ScratchGameBoard scratchGameBoard;
    private GameConfigData gameConfigData;
    @BeforeAll
    public void setup() throws IOException {
        gameConfigData = TestDataBuilder.buildConfigData();
        scratchGameBoard = new ScratchGameBoard(gameConfigData);
    }

    @Test
    public void loadStandardSymbolInBoardTest() {
        String[][] matrix = scratchGameBoard.loadStandardSymbolsInBoard();
        assertEquals(3, matrix.length);
        assertEquals(3, matrix[0].length);
        assertNotNull( matrix[0][0]);
    }

    @Test
    public void loadBonusSymbolInBoardTest() {
        String[][] matrix = scratchGameBoard.loadStandardSymbolsInBoard();
        String bonusSymbol = scratchGameBoard.loadBonusSymbolsInBoard(matrix);
        assertNotNull( bonusSymbol);
    }

    @Test
    public void decideWinningCombinationsTest() {
        String[][] matrix = {{"D","E","E"},{"F","+500","D"},{"B","E","C"}};
        Map<String, List<String>> winnComb =  scratchGameBoard.decideWinningCombination(matrix);
        System.out.println(winnComb);
        assertTrue(winnComb.containsKey("E"));
        assertTrue("same_symbol_3_times".equalsIgnoreCase(winnComb.get("E").get(0)));
        assertNotNull( winnComb);
    }

    @Test
    public void calculateRewardTest() {
        String[][] matrix = {{"F","F","E"},{"F","D","+500"},{"F","E","C"}};
        Map<String, List<String>> winnComb =  scratchGameBoard.decideWinningCombination(matrix);
        double reward =  scratchGameBoard.calculateReward(winnComb,"+500",1099.19999);
        assertEquals(10392.799910000002,reward);
    }
}
