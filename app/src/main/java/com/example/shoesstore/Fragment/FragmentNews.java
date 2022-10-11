package com.example.shoesstore.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.shoesstore.Adapter.NewsAdapter;
import com.example.shoesstore.Moder.NewsItem;
import com.example.shoesstore.R;
import com.example.shoesstore.WebViewActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FragmentNews extends Fragment {

    private List<NewsItem> mList = new ArrayList<>();
    private ListView listView;
    private NewsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        new FeedService().execute("https://ngoisao.vnexpress.net/rss/thoi-trang.rss");
//        new FeedService().execute("https://eva.vn/rss/rss.php/36");

        listView = view.findViewById(R.id.lv_news);
        adapter = new NewsAdapter(getContext(), mList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("link", mList.get(i).getLink());
                startActivity(intent);
            }
        });
        return view;
    }

    public List<NewsItem> getNewsItems(String url) throws XmlPullParserException, IOException {
        List<NewsItem> items = new ArrayList<>();
        XmlPullParser xmlPullParser = Xml.newPullParser();
        InputStream stream = null;
        try {
            stream = new URL(url).openConnection().getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        xmlPullParser.setInput(stream, "UTF-8");
        String name = "";
        String text = "";
        NewsItem item = new NewsItem();
        int eventype = xmlPullParser.getEventType();
        while (eventype != XmlPullParser.END_DOCUMENT) {
            name = xmlPullParser.getName();
            switch (eventype) {
                case XmlPullParser.START_TAG:
                    if (name.equalsIgnoreCase("item")) {
                        item = new NewsItem();
                    }
                    break;
                case XmlPullParser.TEXT:
                    text = xmlPullParser.getText();
                    break;
                case XmlPullParser.END_TAG:
                    if (name.equalsIgnoreCase("item")) {
                        items.add(item);
                    }
                    if (name.equalsIgnoreCase("title")) {
                        if (text != null) {
                            item.setTitle(text);
                        }
                    } else if (name.equalsIgnoreCase("link")) {
                        if (text != null) {
                            item.setLink(text);
                        }
                    } else if (name.equalsIgnoreCase("description")) {
                        if (text != null) {
                            String a[] = text.split("\" ></a></br>");
                            String b[] = a[0].split("src=\"");
                            for (int i = 0; i < a.length; i++) {
                                Log.i("b", "onPostExecute: " + b[i]);
                            }
                            item.setDescription(a[a.length - 1]);
                            item.setLinkImage(b[b.length - 1]);
                        }
                    }
                    break;
            }
            eventype = xmlPullParser.next();
        }

        return items;
    }


    class FeedService extends AsyncTask<String, Integer, Integer> {

        @Override
        protected Integer doInBackground(String... strings) {
            try {
                mList = getNewsItems(strings[0]);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);


            String a[] = (mList.get(0).getDescription()).split("</br>");

            adapter = new NewsAdapter(getContext(), mList);
            listView.setAdapter(adapter);
            for (int i = 0; i < a.length; i++) {
                Log.i("a", "onPostExecute: " + a[i]);
            }

        }
    }
}








