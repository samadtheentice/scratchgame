package com.gamecenter.objectmapper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ObjectMapperBuilderTest {

    @Test
    void objectMapperTest() {
        assertNotNull(ObjectMapperBuilder.getObjectMapper());
    }

}
