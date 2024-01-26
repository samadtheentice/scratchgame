package org.cyberspeed.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cyberspeed.domain.GameConfigData;

import java.io.IOException;
import java.io.InputStream;

import org.cyberspeed.objectmapper.ObjectMapperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScratchGame {
    static final Logger logger = LoggerFactory.getLogger(ScratchGame.class);

    public static void main(String[] args) {

        /*logger.info("welcome to scratch game arg 0.."+args[0]);
        logger.info("welcome to scratch game arg 1.."+args[1]);
        logger.info("welcome to scratch game arg 2.."+args[2]);*/



        try(InputStream in=Thread.currentThread().getContextClassLoader().getResourceAsStream("configupdated.json")){
            jsonToObject(in);
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }

    }

    public static GameConfigData jsonToObject(InputStream in) {
        GameConfigData gameConfig = new GameConfigData();


        System.out.println("test..."+in.toString());

        ObjectMapper mapper = ObjectMapperBuilder.build();

        try {
            gameConfig = mapper.readValue(in, GameConfigData.class);
            System.out.println("wc..."+gameConfig.getWinCombinations().stream().filter(wc->"vertically_linear_symbols".equalsIgnoreCase(wc.getGroup())).findFirst().get().getCoveredAreas());

            //System.out.println("object mapper..."+gameConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gameConfig;
    }

}