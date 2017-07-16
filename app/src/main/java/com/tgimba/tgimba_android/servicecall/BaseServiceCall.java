package com.tgimba.tgimba_android.servicecall;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.tgimba.tgimba_android.MainActivity;
import com.tgimba.tgimba_android.misc.Constants;
import com.tgimba.tgimba_android.misc.Types;
import com.tgimba.tgimba_android.misc.Utilities;

public class BaseServiceCall extends AsyncTask<String, String, String>
{
    protected MainActivity ma = null;
    protected String callUrl = null;

    protected BaseServiceCall(MainActivity pMa,
                              String pUrl){
        ma = pMa;
        callUrl = pUrl;
    }

    private String getUrl(String subUrl) {
        String completeUrl = "";

        if (ma.getHttps() == Types.httpsEnum.NotSet) {
            completeUrl = Constants.HTTPS_TGIMBA_BASE_API_URL + subUrl;
        } else {
            completeUrl = Constants.TGIMBA_BASE_API_URL + subUrl;
        }

        return completeUrl;
    }

    @Override
    protected String doInBackground(String... params) {
        String result = "";
        int ctr = 0;

        // HTTP Get
        while(ctr < 2) {

            try {
                String urlStr = this.getUrl(callUrl);

                URL url = new URL(urlStr);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("connection", "close");
                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream != null) {
                    BufferedReader br = new BufferedReader(new InputStreamReader((inputStream)));
                    StringBuilder sb = new StringBuilder(inputStream.available());
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }

                    result = sb.toString();
                }
            } catch (Exception e) {
                if (ma.getHttps() == Types.httpsEnum.NotSet) {
                    ma.setHttps(Types.httpsEnum.False);
                }
            } finally {
                if (ma.getHttps() == Types.httpsEnum.NotSet) {
                    ma.setHttps(Types.httpsEnum.True);
                    break;
                }
            }

            ctr++;
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        int NotImplemented = 1;
    }
}
