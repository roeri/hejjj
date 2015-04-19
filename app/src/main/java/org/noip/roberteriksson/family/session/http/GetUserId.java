package org.noip.roberteriksson.family.session.http;

import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.entity.StringEntity;
import org.noip.roberteriksson.family.session.Session;
import org.noip.roberteriksson.family.util.HttpPoster;
import org.noip.roberteriksson.family.util.Url;

import java.io.UnsupportedEncodingException;

public class GetUserId extends AsyncTask<String, Void, String> {

    private final Session session;
    private final String email;

    public GetUserId(Session session, String email) {
        this.session = session;
        this.email = email;
    }

    @Override
    protected String doInBackground(String... urls) {
        try {
            StringEntity entityToSend = new StringEntity(email);
            return HttpPoster.doHttpPost(Url.SESSION_GET_USER_ID, entityToSend);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "FAILURE";
    }

    @Override
    protected void onPostExecute(String result) {
        if(!result.equals("FAILURE")) {
            try {
                int id = Integer.parseInt(result);
                session.setUserId(id);
            } catch (NumberFormatException e) {
                Toast.makeText(session.getMainActivity(), "ERROR in GetUserId", Toast.LENGTH_SHORT).show();
                //TODO: Seriously have to handle when a session is not initialized correctly.
            }
        } else {
            //TODO: Also handle here when a session is not initialized correctly.
        }
    }
}
