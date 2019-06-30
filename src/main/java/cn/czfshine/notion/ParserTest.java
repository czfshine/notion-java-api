package cn.czfshine.notion;

import cn.czfshine.notion.model.Paser;
import cn.czfshine.notion.web.NotionClient;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.io.*;
import java.net.URL;

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
