package com.example.evropaplus.Evropa;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EvropaInfo {
    private String URL = "http://www.europaplus.ru/online/air/1.air.js";
    private OkHttpClient client = new OkHttpClient();

    public EvropaInfo() {

    }

    public String getResponce() throws Exception{
        Request request = new Request.Builder()
                .url(URL)
                .build();
        Response response = client.newCall(request).execute();
        String bodyResponse = response.body().string();
        return  bodyResponse;
    }

    public Song getSongLast(String bodyResponse) throws Exception {

        SongSostoyan.getInstance().setJson_answer(bodyResponse);

        JSONObject responseJson = new JSONObject(bodyResponse);



        Song songLast = new Song(responseJson.getString("song"), responseJson.getString("artist"), null);
        songLast.setUrl_img(responseJson.getString("photo"));
        return songLast;
    }

    public List<Stroka> getTextSong(FindSong findsong) throws Exception{
        String URLtext= "https://translatedlyrics.ru"+findsong.getUrl();
        Request request = new Request.Builder()
                .url(URLtext)
                .build();
        Response response = client.newCall(request).execute();
        String bodyResponse = response.body().string();
        Document docSongtextHtml = Jsoup.parse(bodyResponse);

        //text
        Elements col_1s = docSongtextHtml.getElementsByClass("col_1");
        Element col_1 = col_1s.get(1);

        List<Stroka> listSongtext = new ArrayList<>();
        Element nameSong = col_1.getElementsByTag("p").get(0);

        Element txtSong = col_1.getElementsByTag("p").get(1);
        String txtSongStr = txtSong.toString();
        txtSongStr = txtSongStr.replace("<p>","");
        txtSongStr = txtSongStr.replace("</p>","");

        String[] txtSongStrMass = txtSongStr.split("<br>");
        System.out.println(txtSongStrMass.length);

        //perevod
        Elements col_2s = docSongtextHtml.getElementsByClass("col_2");
        Element col_2 = col_2s.get(1);

        Element nameSongPerev = col_2.getElementsByTag("p").get(0);

        Element txtSongPerev = col_2.getElementsByTag("p").get(1);
        String txtSongStrPerev = txtSongPerev.toString();
        txtSongStrPerev = txtSongStrPerev.replace("<p>","");
        txtSongStrPerev = txtSongStrPerev.replace("</p>","");
        String[] txtSongStrMassPerev = txtSongStrPerev.split("<br>");
        System.out.println(txtSongStrMassPerev.length);
        //add list text and perevod
        listSongtext.add(new Stroka(nameSong.text(),nameSongPerev.text()));
        for (int i = 0; i < txtSongStrMass.length; i++) {
            listSongtext.add(new Stroka(txtSongStrMass[i], (txtSongStrMassPerev.length-1>=i)?txtSongStrMassPerev[i]:txtSongStrMassPerev[txtSongStrMassPerev.length-1]));
        }
        return listSongtext;
    }
    public FindSong getSsilkSong(Song song) throws  Exception{
        FindSong luchSong = null;
        String text = "NOT FOUND";
        String songName = song.getSong().split("\\(")[0];
        String artist = song.getArtist();
        String URLfind = "https://translatedlyrics.ru/search/?text=";
        URLfind += songName.replace(' ','+');
        Request request = new Request.Builder()
                .url(URLfind)
                .build();
        Response response = client.newCall(request).execute();
        String bodyResponse = response.body().string();
        Document docHtmlFind = Jsoup.parse(bodyResponse);
        Element search = docHtmlFind.getElementById("search");

        Elements el_col = search.getElementsByTag("div");
        Element el_col_1 = el_col.get(0);
        for (int i = 0; i < el_col.size(); i++) {
            Element el_col_1test = el_col.get(i);
            String strStrongv = el_col_1test.getElementsByTag("strong").get(0).text().toLowerCase(new Locale("ru", "RU"));
            //System.out.println(strStrongv);
            //System.out.println(strStrongv.contains("\u043F\u0435\u0441\u043D\u0438"));  //песни
            if(el_col_1test.getElementsByTag("strong").get(0).text().contains("\u043F\u0435\u0441\u043D\u0438")){
                el_col_1 = el_col_1test;
            }
        }


        Element strongFindSongs = el_col_1.getElementsByTag("ul").get(0);
        Elements li_strongFindSongs = strongFindSongs.getElementsByTag("li");

        List<FindSong> listFindSongs = new ArrayList<>();
        for(Element strongFindSong:li_strongFindSongs){
            String urlSong = strongFindSong.getElementsByTag("a").get(0).attr("href");
            String art = strongFindSong.getElementsByTag("a").get(0).text();
            FindSong findSongs = new FindSong(urlSong,strongFindSong.getElementsByTag("b").get(0).text(), art);
            listFindSongs.add(findSongs);
        }
        if (listFindSongs.size()>0) {
            luchSong = getLuchFind(listFindSongs, artist);

        }else{
            System.out.println("search for similar songs");
            el_col = search.getElementsByTag("strong");
            el_col_1 = el_col.get(0);
            int colPohoz = 0;
            for (int i = 0; i < el_col.size(); i++) {
                Element el_col_1test = el_col.get(i);
                String strStrongv = el_col_1test.text().toLowerCase(new Locale("ru", "RU"));
                //System.out.println(strStrongv);
                //System.out.println(strStrongv.contains("\u043F\u043E\u0445\u043E\u0436\u0438\u0435"));  //похожие
                if(strStrongv.contains("\u043F\u043E\u0445\u043E\u0436\u0438\u0435")){
                    el_col_1 = el_col_1test;
                    colPohoz = i;
                }
            }


            strongFindSongs = search.getElementsByTag("ul").get(colPohoz);
            li_strongFindSongs = strongFindSongs.getElementsByTag("li");

            listFindSongs = new ArrayList<>();
            for(Element strongFindSong:li_strongFindSongs){
                String urlSong = strongFindSong.getElementsByTag("a").get(0).attr("href");
                String art = strongFindSong.getElementsByTag("a").get(0).text();
                FindSong findSongs = new FindSong(urlSong,strongFindSong.getElementsByTag("b").get(0).text(), art);
                listFindSongs.add(findSongs);
            }
            if (listFindSongs.size()>0) {
                luchSong = getLuchFind(listFindSongs, artist);

            }
        }
        if(luchSong!= null){
            System.out.println(luchSong);
        }
        System.out.println(listFindSongs);

        return luchSong;
    }

    public FindSong getLuchFind(List<FindSong> listFindSongs, String artist){
        FindSong findSong = listFindSongs.get(0);
        int colsoot = 0;
        for (int i=0; i<listFindSongs.size(); i++) {
            FindSong findSongtest = listFindSongs.get(i);
            int testSootvet = 0;
            String artisto = findSongtest.getArtist().toLowerCase();
            String[] wordsArtist = artist.split(" ");
            for (int j = 0; j < wordsArtist.length; j++) {
                String wordArtist = wordsArtist[j].toLowerCase();
                if(artisto.contains(wordArtist)) testSootvet ++;
            }
            if (testSootvet>colsoot) {
                findSong = findSongtest;
                colsoot = testSootvet;
            }
        }
        return findSong;
    }

    public List<Song> getLastSongs(String bodyResponse) throws Exception {

        List<Song> listSongs = new ArrayList<>();
        JSONObject responseJson = new JSONObject(bodyResponse);
        JSONArray lastSongs = responseJson.getJSONArray("playlist");
        for(int i = 0; i<lastSongs.length(); i++){
            JSONObject lastSong = lastSongs.getJSONObject(i);
            Song songLast = new Song(lastSong.getString("song"), lastSong.getString("artist"), lastSong.getLong("start_ts"));
            songLast.setUrl_img(lastSong.getString("img1"));
            listSongs.add(songLast);
        }
        return listSongs;
    }
}
