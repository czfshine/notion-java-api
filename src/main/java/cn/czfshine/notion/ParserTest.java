package cn.czfshine.notion;

import cn.czfshine.notion.model.Paser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Paser Tester.
 *
 * @author <Authors name>
 * @version 1.0
 */
public class ParserTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: parserPageChunk(String json)
     */
    @Test
    public void testParserPageChunk() throws Exception {


        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(new File(".\\src\\main\\resources\\page.json"))));
        StringBuilder stringBuilder = new StringBuilder();
        while (bufferedReader.ready()){
            stringBuilder.append(bufferedReader.readLine());
        }
        Paser.paserPageChunk(stringBuilder.toString());
    }


} 
