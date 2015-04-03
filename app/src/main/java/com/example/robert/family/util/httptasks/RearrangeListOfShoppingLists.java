package com.example.robert.family.util.httptasks;

import android.os.AsyncTask;
import android.widget.Toast;

import com.example.robert.family.main.shoppinglist.ListOfShoppingListsFragment;
import com.example.robert.family.main.shoppinglist.ListOfShoppingListsJson;
import com.example.robert.family.util.HttpPoster;
import com.example.robert.family.util.Url;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

/**
 * Created by robert on 2015-03-06.
 */
public class RearrangeListOfShoppingLists extends AsyncTask<String, Void, String> {

    private final ListOfShoppingListsFragment listOfShoppingListsFragment;
    private final ListOfShoppingListsJson shoppingLists;

    public RearrangeListOfShoppingLists(ListOfShoppingListsFragment listOfShoppingListsFragment, ListOfShoppingListsJson shoppingLists) {
        this.listOfShoppingListsFragment = listOfShoppingListsFragment;
        this.shoppingLists = shoppingLists;
    }

    @Override
    protected String doInBackground(String... urls) {
        try {
            String json = new ObjectMapper().writeValueAsString(shoppingLists);
            StringEntity entityToSend = new StringEntity(json);
            return HttpPoster.doHttpPost(Url.LIST_OF_SHOPPING_LISTS_REARRANGE, entityToSend);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "FAILURE";
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.equals("SUCCESS")) {
            new GetListOfShoppingLists(listOfShoppingListsFragment).execute();
        } else {
            Toast.makeText(listOfShoppingListsFragment.getActivity(), "ERROR in RearrangeListOfShoppingLists", Toast.LENGTH_SHORT).show();
        }
    }
}
