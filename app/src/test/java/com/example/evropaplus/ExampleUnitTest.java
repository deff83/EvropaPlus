package com.example.evropaplus;

import com.example.evropaplus.Evropa.EvropaInfo;
import com.example.evropaplus.Evropa.Song;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testListPlay() {
        try {
            EvropaInfo evropa = new EvropaInfo();
            String responce = evropa.getResponce();
            System.out.println(responce);
            List<Song> list_test = evropa.getLastSongs(responce);
            System.out.println(list_test);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}