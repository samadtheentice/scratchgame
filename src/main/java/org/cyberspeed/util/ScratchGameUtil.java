package org.cyberspeed.util;

import org.cyberspeed.constants.ScratchGameConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ScratchGameUtil {

    /**
     * Generate random symbol based on the probability percentage
     * @param symbols
     * @return
     */
    public static String generateRandomSymbol(final Map<String,Integer> symbols){
        final int totalValue = symbols.values().stream().mapToInt(Integer::intValue).sum();
        final int randomNumber = new Random().nextInt(100);
        float cumulativeProbability=0.0f;
        String selectedSymbol=null;

        for (String key : symbols.keySet()) {
            cumulativeProbability += ((float)symbols.get(key)/totalValue)*100;
            if (randomNumber <= cumulativeProbability) {
                selectedSymbol = key;
                break;
            }
        }
        return selectedSymbol;
    }

    public static void addSymbolToList(final List<String> symbolMatchLst,final Map<String,List<String>> appliedWinningCombination, final String group){
        symbolMatchLst.forEach(k->{
            appliedWinningCombination.computeIfAbsent(k, v -> new ArrayList<>()).add(group);
        });
    }

}
