package com.example.philipp.photogallery;

import android.net.Uri;
import android.util.Log;

import com.example.philipp.photogallery.model.GalleryItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * FlickrFetchr - class provide success to REST api  "api.flickr.com"
 * and any network communication with server API.
 */
public class FlickrFetchr {
    public static final String LOG_TAG = FlickrFetchr.class.getCanonicalName();
    private static final String ENDPOINT = "https://api.flickr.com/services/rest/";
    private static final String API_KEY = "18c5140f4578fd845c717f2dc9628501";
    private static final String METHOD_GET_RECENT = "flickr.photos.getRecent";
    private static final String XML_PHOTO = "photo";
    private static final String PARAM_EXTRAS = "extras";
    private static final String EXTRA_SMALL_URL = "url_s";
    private static final String FORMAT_JSON = "json";

    byte[] getUrlBytes(String urlSpec) throws IOException {
        Log.d(LOG_TAG, "getUrlBytes()");

        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];

            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public ArrayList<GalleryItem> fetchItems() {
        ArrayList<GalleryItem> items = new ArrayList<>();

        try {
            String url = Uri.parse(ENDPOINT).buildUpon()
                    .appendQueryParameter("method", METHOD_GET_RECENT)
                    .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter(PARAM_EXTRAS, EXTRA_SMALL_URL)
                    .build().toString();
            String xmlString = getUrl(url);

            Log.d(LOG_TAG, "Received xml: " + xmlString);

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xmlString));

            parseItems(items, parser);
            Log.d(LOG_TAG, "items---->" + items.size());

        } catch (IOException ioe) {
            Log.e(LOG_TAG, "Failed to fetch items", ioe);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return items;
    }

    public String getUrl(String urlSpec) throws IOException {
        Log.d(LOG_TAG, "getUrl()");
        return new String(getUrlBytes(urlSpec));
    }

    private void parseItems(ArrayList<GalleryItem> items, XmlPullParser parser)
            throws XmlPullParserException, IOException {
        Log.d(LOG_TAG, "parseItems()");
        int eventType = parser.next();
        int counter = 1;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG && XML_PHOTO.equals(parser.getName())) {
                String id = parser.getAttributeValue(null, "id");
                String caption = parser.getAttributeValue(null, "title");
                String smallUrl = parser.getAttributeValue(null, EXTRA_SMALL_URL);

                GalleryItem item = new GalleryItem();
                item.setId(id);
                item.setCaption(caption);
                item.setUrl(smallUrl);
                items.add(item);
                Log.d(LOG_TAG, "count -> " + counter++ +"<--"+ item.toString());
            }

            eventType = parser.next();
        }
    }
}
