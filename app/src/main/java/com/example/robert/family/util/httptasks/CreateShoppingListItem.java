package com.example.robert.family.util.httptasks;

import android.os.AsyncTask;
import android.widget.Toast;

import com.example.robert.family.Session;
import com.example.robert.family.main.shoppinglist.ShoppingListFragment;
import com.example.robert.family.main.shoppinglist.ShoppingListItemJson;
import com.example.robert.family.util.Url;
import com.example.robert.family.util.HttpPoster;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

/**
 * Created by robert on 2015-03-06.
 */
public class CreateShoppingListItem extends AsyncTask<String, Void, String> {

    private final ShoppingListFragment shoppingListFragment;
    private final String itemName;

    public CreateShoppingListItem(ShoppingListFragment shoppingListFragment, String itemName) {
        this.shoppingListFragment = shoppingListFragment;
        this.itemName = itemName;
    }

    @Override
    protected String doInBackground(String... urls) {
        try {
            ShoppingListItemJson shoppingListItemJson = new ShoppingListItemJson();
            shoppingListItemJson.setShoppingListsId(shoppingListFragment.id);
            shoppingListItemJson.setUsersId(Session.getInstance().getUserId());
            shoppingListItemJson.setText(itemName);
            String json = new ObjectMapper().writeValueAsString(shoppingListItemJson);
            StringEntity entityToSend = new StringEntity(json);
            return HttpPoster.doHttpPost(Url.SHOPPING_LIST_CREATE_ITEM, entityToSend);
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
            new GetShoppingList(shoppingListFragment).execute();
        } else {
            Toast.makeText(shoppingListFragment.getActivity(), "ERROR in CreateShoppingListItem", Toast.LENGTH_SHORT).show();
        }
    }
}
