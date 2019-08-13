package test.cn.czfshine.notion.model;

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
 * @since <pre>Jun 30, 2019</pre>
 */
public class PaserTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: paserPageChunk(String json)
     */
    @Test
    public void testPaserPageChunk() throws Exception {


        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(new File("./src/main/resources/page.json"))));
        StringBuilder stringBuilder = new StringBuilder();
        while (bufferedReader.ready()) {
            stringBuilder.append(bufferedReader.readLine());
        }
        Paser.paserPageChunk(stringBuilder.toString());

    }

    /**
     * Method: parserUserContent(String json)
     */
    @Test
    public void testParserUserContent() throws Exception {


        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(new File("./src/main/resources/userContent.json"))));
        StringBuilder stringBuilder = new StringBuilder();
        while (bufferedReader.ready()) {
            stringBuilder.append(bufferedReader.readLine());
        }
        Paser.parserUserContent(stringBuilder.toString());

//TODO: Test goes here... 
    }


    /**
     * Method: parserTitle(ArrayList title)
     */
    @Test
    public void testParserTitle() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = Paser.getClass().getMethod("parserTitle", ArrayList.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: parserTextBlock(String uuid, Info block)
     */
    @Test
    public void testParserTextBlock() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = Paser.getClass().getMethod("parserTextBlock", String.class, Info.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

} 
