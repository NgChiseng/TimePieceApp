package com.idbc.ngchiseng.timepieceapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;



public class PublicationDonationsFragment extends Fragment {


    /*  This will declare the variables that are going to use on the fragment. */
    private ListView listTabItems;
    private String text;
    private ProgressBar progressBar;
    ArrayList<Announce> data;
    SharedPreferences pref_others;
    PublicationDonationAdapter adapter;

    /* Declaration of the interface that will use to call and pass the detail data to the
    PublicationDonationsEditionFragment through the PublicationDonationsFragment.
     */
    OnPublicationDonationsFragmentInteractionListener publicationDonationsInterface;

    /*  Method that will onCreate the fragment, inflate its View, link its component, and will return
    the render to the main Activity.
        @date[13/07/2017]
        @author[ChiSeng Ng]
        @param [LayoutInflater] inflater The layout inflater corresponding of the caller Activity.
        @param [ViewGroup] container The viewGroup that will content the fragment on the layout.
        @param [Bundle] savedInstanceState InstanceState of the MainActivity.
        @return [View] The inflated an rendered view that will show its on the screen.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View donationsPublicationView = inflater.inflate(R.layout.fragment_publication_donations, container, false);
        progressBar = (ProgressBar) donationsPublicationView.findViewById(R.id.fragment_publications_donations_progressBar);

        /* This will obtain the resource for setting the formatting String in each Item view */
        Resources res = getResources();
        final String donationCollectedAndRequired = res.getString(R.string.collected_required);
        final String creationDate = res.getString(R.string.creation_date);
        final String modificationDate = res.getString(R.string.modification_date);


        // Initialized ArrayList of Announces
        data = new ArrayList<Announce>();

        /*  This block will obtain and add the date corresponding to each Announce object on the
        ArrayList of Announces.

        String description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras eget " +
                "nunc finibus, vehicula felis ac, pulvinar eros. Sed lobortis eu metus eu tristique. " +
                "Nam nec justo ut velit accumsan laoreet. Fusce ut leo vitae metus porta tempor sed " +
                "venenatis odio. Quisque suscipit ligula a risus pharetra dapibus. Nunc sit amet " +
                "neque odio. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere " +
                "cubilia Curae; Nunc ut vulputate ante, vitae rutrum nisi. Duis sed metus sapien. " +
                "Aenean gravida ac metus accumsan consequat. Ut laoreet, sapien sed molestie tempus, " +
                "lacus justo gravida felis, eget sagittis arcu est non dolor. Aenean vel dignissim " +
                "erat, vitae malesuada nisl.";
        data.add(new Announce(R.drawable.ninos_cocina_aprender, "Clases de cocina para niños de la comunidad la Sierra", "24-03-2017", "24-03-2017", "$", "44", "230", description));

        /*  This will handler the ArrayList of Announces and the data inside its with the screen list
        and components o widgets, using the AnnouncesAdapter.
         */
        listTabItems = (ListView) donationsPublicationView.findViewById(R.id.publication_donations_list);
        adapter = new PublicationDonationAdapter(getContext(), R.layout.tab_item, data);
        /*listTabItems.setAdapter(new AnnouncesAdapter(getContext(), R.layout.tab_item, data) {

            /* This will implement the abstract method onEntry(Implemented in AnnounceAdapter), with
            the respective elements and handlers.
             */
            /*@Override
            public void onEntry(Object entry, View view) {

                if (entry != null){

                    ImageView tabItemImage = (ImageView) view.findViewById(R.id.tab_item_image);
                    tabItemImage.setImageResource(((Announce) entry).getImage());

                    TextView tabItemTitle = (TextView) view.findViewById(R.id.tab_item_title);
                    tabItemTitle.setText(((Announce) entry).getTitle());

                    TextView tabItemPrice = (TextView) view.findViewById(R.id.tab_item_others);
                    text = String.format(donationCollectedAndRequired, ((Announce) entry).getCurrencyPrice(), ((Announce) entry).getCurrencyUnit() );
                    tabItemPrice.setText(text);

                    TextView tabItemFirstDate = (TextView) view.findViewById(R.id.tab_item_first_date);
                    text = String.format(creationDate, ((Announce) entry).getName());
                    tabItemFirstDate.setText(text);

                    TextView tabItemSecondDate = (TextView) view.findViewById(R.id.tab_item_second_date);
                    text = String.format(modificationDate, ((Announce) entry).getAddress());
                    tabItemSecondDate.setText(text);
                }
            }
        });

        /* Declaration and implementation of the item listener that will get the item that was
        clicked and call the Donations interface method that will be implemented in the PublicationFragment.
         */
        listTabItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Announce selected = (Announce) parent.getItemAtPosition(position);
                publicationDonationsInterface.onPublicationDonationsFragmentInteraction(selected);
            }
        });

        AttemptPublicationDonations attemptPublicationDonations = new AttemptPublicationDonations();
        attemptPublicationDonations.execute("","");
        return donationsPublicationView;
    }

    /*  Method that will be called by the system when is associated with the PublicationFragment, will
    validate the interface called, and will return an error if fail.
        @date[02/08/2017]
        @author[ChiSeng Ng]
        @param [Context] context Context that call it, in this case corresponding to the parent
        fragment.
        @return [Void]
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            publicationDonationsInterface = (OnPublicationDonationsFragmentInteractionListener) getParentFragment();
        } catch (ClassCastException exception) {
            throw new ClassCastException("Error in retrieving publication Donations data. Please try again.");
        }
    }

    /*
     * This interface must be implemented by parent fragments that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the parent fragment and potentially other fragments contained in that
     * parent.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnPublicationDonationsFragmentInteractionListener {
        void onPublicationDonationsFragmentInteraction(Announce announce);
    }

    /*--------------------------------------------------------------------------------------------*/
    /* Class that will be used for send Log In's data to the server's API and process the response.
    It is made in a AsyncTask because this type of process need run in secondary threads for not to
    saturate the main thread, its params mean <Params, Progress Units, Results>
     */
    public class AttemptPublicationDonations extends AsyncTask<String, Integer, Integer> {

        /* Method that is initialized in the main thread before entering the execution of the
        secondary thread
            @date[31/08/2017]
            @author[ChiSeng Ng]
            @param [Void]
            @return [void]
        */
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        /* Method that is initialized in the secondary thread, and will communicate with the
        TimePiece API and manage this connection. In this function is to send validated Log In's
        data to the server's API and process the response. Returns an integer value ([-1..1):
        * -1, if an error occurred during the communication.
        * 0, if everything went OK (redirecting to MainActivity and updating SharedPreferences afterwards).
        * 1, if the credentials provided aren't valid.
            @date[31/08/2017]
            @author[ChiSeng Ng]
            @param [String...] strings All the string that entered like params.
            @return [Integer] Represent the result of the connection operation.
        */
        @Override
        protected Integer doInBackground(String... strings) {

            Integer result = 0;
            try {
                // Defining and initializing server's communication's variables
                String credentials = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(strings[0], "UTF-8");
                credentials += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(strings[1], "UTF-8");

                URL url = new URL("http://192.168.1.110:8000/api-login/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(2000);

                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(credentials);
                writer.flush();
                /* This get the JSON object from the response urlConnection and save it the
                "response" variable. In this case the true value is because the value received is a
                JSON object, if received a JSON array object then is false.
                */
                APIResponse response = JSONResponseController.getJsonResponse(connection,true);

                if (response != null){

                    if (response.getStatus()==HttpURLConnection.HTTP_OK) {
                        JSONObject response_body = response.getBody();
                        Log.d("OK", "OK");
                        return 0;

                    } else if (response.getStatus() == HttpURLConnection.HTTP_BAD_REQUEST) {
                        Log.d("BAD", "BAD");
                        result = 1;
                    } else {
                        Log.d("NOT", "FOUND");
                        result = -1;
                    }
                }

            } catch (MalformedURLException e) {
                return result;
            } catch (IOException e) {
                return result;
            } catch (JSONException e) {
                return result;
            }
            return result;
        }

        /* Method that is initialized after the secondary thread finish it execution, and will
        process the doInBackground() method results.
            @date[01/09/2017]
            @author[ChiSeng Ng]
            @param [Integer] anInt Results passed from doInBackground() method.
            @return [Void]
        */
        @Override
        protected void onPostExecute(Integer anInt) {
            switch (anInt) {
                case (-1):
                    Toast.makeText(getContext(), R.string.connection_error, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    break;
                case (0):
                    /*  This block will obtain and add the date corresponding to each Announce object on the
                    ArrayList of Announces.
                    String description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras eget " +
                            "nunc finibus, vehicula felis ac, pulvinar eros. Sed lobortis eu metus eu tristique." +
                            " Nam nec justo ut velit accumsan laoreet. Fusce ut leo vitae metus porta tempor sed" +
                            " venenatis odio. Quisque suscipit ligula a risus pharetra dapibus. Nunc sit amet " +
                            "neque odio. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices " +
                            "posuere cubilia Curae; Nunc ut vulputate ante, vitae rutrum nisi. Duis sed metus " +
                            "sapien. Aenean gravida ac metus accumsan consequat. Ut laoreet, sapien sed molestie" +
                            " tempus, lacus justo gravida felis, eget sagittis arcu est non dolor. Aenean vel " +
                            "dignissim erat, vitae malesuada nisl.";

                    /*pref_others = getActivity().getSharedPreferences("others", 0);
                    boolean finish = pref_others.getBoolean("product_finish", false);
                    if (finish) {
                        data.add(new Announce(R.drawable.reloj_viejo2, "Relojes Viejos", "Chiseng Ng Yu", "Coordinar envío con el vendedor", "$", "100", "Unidad", description, "2", "Producto", "28-11-2017", "28-11-2017"));
                    }*/
                    /*data.add(new Announce(R.drawable.reloj_viejo2, "Relojes Viejos", "Coordinar envío con el vendedor" , "$", "100", "Unidad", description, "28-11-2017", "28-11-2017"));
                    data.add(new Announce(R.drawable.jarron, "Jarrones Artesanales", "Coordinar envío con el vendedor" , "$", "70", "Unidad", description, "28-11-2017", "28-11-2017"));
                    data.add(new Announce(R.drawable.ninos_cocina_aprender, "Clases de cocina para niños de la comunidad la Sierra", "24-03-2017", "24-03-2017", "$", "44", "230", description));
                    */
                    pref_others = getActivity().getSharedPreferences("others", 0);
                    boolean created = pref_others.getBoolean("donation_created", false);
                    if (created) {
                        data.add(new Announce(R.drawable.idiomas, pref_others.getString("donation_title", null), "28-11-2017", "28-11-2017", "$", "0", pref_others.getString("donation_required", null), pref_others.getString("donation_description", null)));
                    }
                    listTabItems.setAdapter(adapter);
                    listTabItems.deferNotifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                    break;
                case (1):
                    Toast.makeText(getContext(), R.string.email_password_invalid, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    }
}
