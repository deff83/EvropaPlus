package com.example.evropaplus;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.evropaplus.Evropa.EvropaInfo;
import com.example.evropaplus.Evropa.Song;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.example.evropaplus", appContext.getPackageName());
    }

    @Test
    public void testListPlay() {
        try {
            EvropaInfo evropa = new EvropaInfo();
            String responce = evropa.getResponce();
            System.out.println(responce);
            List<Song> list_test = evropa.getLastSongs(responce);
            assertEquals(list_test.size(), 5);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
