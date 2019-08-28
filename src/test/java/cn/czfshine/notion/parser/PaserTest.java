package cn.czfshine.notion.parser;

import cn.czfshine.notion.model.WorkSpace;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

/**
 * Parser Tester.
 *
 * @author czfshine
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

    private static String getResourcesFileContent(String filename) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(new File("./src/main/resources/" + filename))));
        StringBuilder stringBuilder = new StringBuilder();
        while (bufferedReader.ready()) {
            stringBuilder.append(bufferedReader.readLine());
        }
        return stringBuilder.toString();
    }

    /**
     * Method: parserPageChunk(String json)
     */
    @Test
    public void testPaserPageChunk() throws Exception {
        WorkSpace workSpace = Parser.parserPageChunk(getResourcesFileContent("page.json"));
        System.out.println(workSpace.toString());
        Parser.parserPageChunk(getResourcesFileContent("layout.json"));
    }

    /**
     * Method: parserUserContent(String json)
     */
    @Test
    public void testParserUserContent() throws Exception {
        Parser.parserUserContent(getResourcesFileContent("userContent.json"));
    }


    /**
     * Method: parserTitle(ArrayList text)
     */
    @Test
    public void testParserTitle() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = Parser.getClass().getMethod("parserTitle", ArrayList.class);
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
   Method method = Parser.getClass().getMethod("parserTextBlock", String.class, Info.class);
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

} 
