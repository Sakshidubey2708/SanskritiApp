package com.example.awizom.sanskritidecoreapp.helper;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import com.example.awizom.sanskritidecoreapp.config.AppConfig;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OrderHelper extends AppCompatActivity{

    public static final class  AddRoom extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String room_type = params[0];
            String accesstoken = params[1];
            String json = "";
            try {

                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "RoomCatagoryNewPost");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                FormBody.Builder parameters = new FormBody.Builder();
                parameters.add("room_catagory_id", "");
                parameters.add("room_type", room_type);
                builder.post(parameters.build());
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;

        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class  UpdateRoom extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String id = params[0];
            String room_type = params[1];
            String active = params[2];
            String accesstoken = params[3];
            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "RoomCatagoryNewPost");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                FormBody.Builder parameters = new FormBody.Builder();
                parameters.add("room_catagory_id", id);
                parameters.add("room_type", room_type);
                parameters.add("active", active);
                builder.post(parameters.build());
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;

        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class  PostOrderDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {


            String accesstoken = params[0];
            String CustomerID = params[1];
            String date = params[2];
            String order_type = params[3];
            String created_by = params[4];
            String t_id = params[5];
            String catalog_catagory_id = params[6];
            String measur_id = params[7];
            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "OrderDetailNewPost");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                FormBody.Builder parameters = new FormBody.Builder();

                parameters.add("orderid", "0");
                parameters.add("CustomerID", CustomerID);
                parameters.add("date", date.toString().trim());
                parameters.add("order_type", order_type);
                parameters.add("created_by", created_by);
                parameters.add("t_id",t_id);
                parameters.add("catalog_catagory_id", catalog_catagory_id);
                parameters.add("measur_id", measur_id);
                builder.post(parameters.build());
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;

        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class  PostMeasurment extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {


            String umo_id = params[0];
            String msubcatagory_id = params[1];
            String measurment_data = params[2];
            String CreatedBy = params[3];
            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "MeasurementCatagoryPost");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");

                FormBody.Builder parameters = new FormBody.Builder();
                parameters.add("measur_id", "");
                parameters.add("umo_id", umo_id);
                parameters.add("msubcatagory_id", msubcatagory_id);
                parameters.add("measurment_data",measurment_data);
                parameters.add("CreatedBy",CreatedBy);
                builder.post(parameters.build());
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;

        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class  PostTailor extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String accesstoken = params[0];
            String t_name = params[1];
            String t_contact = params[2];

            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "TailorCatagoryPost");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                FormBody.Builder parameters = new FormBody.Builder();
                parameters.add("t_id", "");
                parameters.add("t_name", t_name);
                parameters.add("t_contact", t_contact);
                builder.post(parameters.build());
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;

        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class AddMaterial extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String material_type = params[0];
            String accesstoken = params[1];
            String catalogCatagoryId = params[2];
            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "MaterialCatagoryPost");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                FormBody.Builder parameters = new FormBody.Builder();
                parameters.add("mcatagory_id", "0");
                parameters.add("mcatagory_type", material_type);
                parameters.add("catalog_catagory_id", catalogCatagoryId);
                builder.post(parameters.build());
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;

        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class AddSubMaterial extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String material_subcatagory_type = params[0];
            String accesstoken = params[1];
            String mcatagoryid = params[2];
            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "MaterialSubCatagoryPost");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                FormBody.Builder parameters = new FormBody.Builder();
                parameters.add("msubcatagory_id", "0");
                parameters.add("msubcatagory_type", material_subcatagory_type);
                parameters.add("mcatagory_id", mcatagoryid);
                builder.post(parameters.build());
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;

        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class GetAllCustomerDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String accesstoken = strings[0];
            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "CustomerGet/");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class GetAllCustomerMobileDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String json = "";
            String accesstoken = strings[0];
            String cusname = strings[1];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "CustomerGets/" + cusname);
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class GetAllCustomerMobileNo extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String accesstoken = strings[0];
            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "CustomerGet/");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class GetCustomerNames extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String json = "";
            String accesstoken = strings[0];
            String cusname = strings[1];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "CustomerGet/" + cusname);
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class GetRoomsDetail extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String json = "";
            String accesstoken = strings[0];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "GETRoomCatagoryNewGet");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class GetRoomsDetailNames extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String json = "";
            String accesstoken = strings[0];
            String room_type = strings[1];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "GETRoomCatagoryNewGet/" + room_type);
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class GetCatalogsDesignName extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String json = "";
            String accesstoken = strings[0];
            String catalogDesignName = strings[1];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "GetCatalogCatagory/" +catalogDesignName);
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class GetCatalogsDetail extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String json = "";
            String accesstoken = strings[0];

            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "GetCatalogCatagory");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class GetMaterialsDetail extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String json = "";
            String accesstoken = strings[0];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "GETMaterialCatagory");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class GetMaterialsDetailNames extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String json = "";
            String accesstoken = strings[0];
            String mcatagory_type = strings[1];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "GETMaterialCatagory/" + mcatagory_type);
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class GetSubMaterialsDetail extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String json = "";
            String accesstoken = strings[0];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "GETMaterialSubCatagory");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class GetSubMaterialsDetailNames extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String json = "";
            String accesstoken = strings[0];
            String msubcatagory_type = strings[1];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "GETMaterialSubCatagory/" + msubcatagory_type);
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class GetMeasurementDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String json = "";
            String accesstoken = strings[0];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "GetMeasurementCatagory");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class GetMeasurementDetailsName extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String json = "";
            String accesstoken = strings[0];
            String measur_type = strings[1];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "Getmeasurementcatagory/" + measur_type);
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class GetCompleteOrderModel extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String json = "";

            String ordereId = strings[0];
            String accesstoken = strings[1];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "" +
                        "/" + ordereId );
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class GetcustomerOrderDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String json = "";


            String accesstoken = strings[0];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "GetCustomerOrder" );
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class GetCustomerFollowUpList extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String json = "";
            String accesstoken = strings[0];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "GetCustomerForFollowup" );
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class GetTailorsDetailNames extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String json = "";
            String accesstoken = strings[0];
            String tailor_name = strings[1];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "GETailorCatagory/" +tailor_name);
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);

                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class GetTailorsDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String json = "";
            String accesstoken = strings[0];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "GETailorCatagory");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);

                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class GetTailorsListDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String json = "";
            String accesstoken = strings[0];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "GetTailorsList");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);

                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class DeleteTailor extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String t_id = params[0];
            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "PostDeleteTailorCatagory/"+t_id);
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                FormBody.Builder parameters = new FormBody.Builder();
                builder.post(parameters.build());
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;

        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class GetPendingTailorsListDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String json = "";
            String accesstoken = strings[0];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "GetPendingToTailor");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);

                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class GetPendingAmountListDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String json = "";
            String accesstoken = strings[0];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "GetPendingAmount");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);

                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class GetPendingOrderListDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String json = "";
            String accesstoken = strings[0];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "GetPendingOrderList");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);

                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class UpdatePostPendingTailor extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String json = "";
            String accesstoken = strings[0];
            String orderoid = strings[1];
            String is_tailorStatus = strings[2];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "UpdateTailorSatus/" + orderoid);
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                FormBody.Builder parameters = new FormBody.Builder();
                parameters.add("is_tailorStatus", is_tailorStatus);
                builder.post(parameters.build());
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class UpdatePostPendingAmount extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String json = "";
            String accesstoken = strings[0];
            String orderoid = strings[1];
            String is_amountstatus = strings[2];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "UpdateAmountSatus/"+orderoid);
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                FormBody.Builder parameters = new FormBody.Builder();
                parameters.add("is_amountstatus", is_amountstatus);
                builder.post(parameters.build());
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class UpdatePostPendingOrder extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String json = "";
            String accesstoken = strings[0];
            String orderid = strings[1];
            String is_orderStatus = strings[2];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "UpdateOrderSatus/"+orderid);
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                FormBody.Builder parameters = new FormBody.Builder();
                parameters.add("is_orderStatus", is_orderStatus);
                builder.post(parameters.build());
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class GetMasterMeasurementDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String json = "";
            String accesstoken = strings[0];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "GetUMeasurementCatagory");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class  PostMasterMeasurment extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {


            String umo_type = params[0];
            String accesstoken = params[1];
            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "UMeasurementCatagoryPost");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                FormBody.Builder parameters = new FormBody.Builder();
                parameters.add("umo_id", "");
                parameters.add("umo_type", umo_type);

                builder.post(parameters.build());
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;

        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class  PostUpdateMasterMeasurment extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {


            String umo_id = params[0];
            String umo_type = params[1];
            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "UpdateUMeasurementCatagoryPost/"+umo_id);
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                FormBody.Builder parameters = new FormBody.Builder();
                parameters.add("umo_type", umo_type);
                builder.post(parameters.build());
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;

        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class  PostUpdateRoom extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {


            String room_catagory_id = params[0];
            String room_type = params[1];
            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "UpdateRoomCatagoryNewPost/"+room_catagory_id);
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                FormBody.Builder parameters = new FormBody.Builder();
                parameters.add("room_type", room_type);
                builder.post(parameters.build());
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;

        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class  PostUpdateTailor extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {


            String t_id = params[0];
            String t_name = params[1];
            String t_contact = params[2];
            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "UpdateTailorCatagoryPost/"+t_id);
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                FormBody.Builder parameters = new FormBody.Builder();
                parameters.add("t_name", t_name);
                parameters.add("t_contact", t_contact);
                builder.post(parameters.build());
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;

        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class GetMaterialDetail extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String json = "";
            String accesstoken = strings[0];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "GetMasterMaterialCatagory");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class  PostMaterialMaster extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {


            String MaterialID = params[0];
            String MaterialType = params[1];
            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "MasterMaterialCategoryPost");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                FormBody.Builder parameters = new FormBody.Builder();
                parameters.add("MaterialID", MaterialID);
                parameters.add("MaterialType", MaterialType);
                builder.post(parameters.build());
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;

        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class GetSubMaterialDetail extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String json = "";
            String accesstoken = strings[0];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "GetMasterSubMaterialCatagory");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class  PostSubMaterialMaster extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {


            String SubMaterialID = params[0];
            String SubMaterialType = params[1];
            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "MasterSubMaterialCatagoryPost");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                FormBody.Builder parameters = new FormBody.Builder();
                parameters.add("SubMaterialID", SubMaterialID);
                parameters.add("SubMaterialType", SubMaterialType);
                builder.post(parameters.build());
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;

        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class  PostCustomerOrderDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {


            String OrderId = params[0];
            String OrderDate = params[1];
            String CustomerID = params[2];
            String CreatedBy = params[3];

            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "OrderWorkPost");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                FormBody.Builder parameters = new FormBody.Builder();

                parameters.add("OrderId", OrderId);
                parameters.add("OrderDate", OrderDate);
                parameters.add("CustomerID", CustomerID);
                parameters.add("CreatedBy", CreatedBy);
                parameters.add("CreatedOn", "");

                builder.post(parameters.build());
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;

        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class  PostOrdersitemDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {


            String OrderItemID = params[0];
            String OrderId = params[1];
            String room_catagory_id = params[2];
            String SubMaterialID = params[3];
            String catalog_catagory_id = params[4];

            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "OrderItemWorkPost");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                FormBody.Builder parameters = new FormBody.Builder();

                parameters.add("OrderItemID", OrderItemID);
                parameters.add("OrderId", OrderId);
                parameters.add("room_catagory_id", room_catagory_id);
                parameters.add("SubMaterialID", SubMaterialID);
                parameters.add("catalog_catagory_id", catalog_catagory_id);

                builder.post(parameters.build());
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;

        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }



    public static final class  GetAfterAmountOrder extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {



            String OrderId = params[0];
            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "GetAfterdiscountTotalAmount/"+OrderId);
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                FormBody.Builder parameters = new FormBody.Builder();
                builder.post(parameters.build());
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;

        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class GetTailorWorkingListDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String json = "";
            String accesstoken = strings[0];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "GetTailorWorkPost");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", "Bearer " + accesstoken);

                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class  PostTailoCalculatio extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {


            String TailorCalculateID = params[0];
            String TailorWorkID = params[1];
            String t_id = params[2];
            String Quantity = params[3];
            String TailorPrice = params[4];
            String TotalPrice = params[5];
            String OrderId = params[6];

            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "TailorCalculationPost");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                FormBody.Builder parameters = new FormBody.Builder();

                parameters.add("TailorCalculateID", TailorCalculateID);
                parameters.add("TailorWorkID", TailorWorkID);
                parameters.add("t_id", t_id);
                parameters.add("Quantity", Quantity);
                parameters.add("TailorPrice", TailorPrice);
                parameters.add("TotalPrice", TotalPrice);
                parameters.add("OrderId", OrderId);

                builder.post(parameters.build());
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;

        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class  PostOrderItemDelete extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {


            String orderItemId = params[0];



            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "PostDeleteOrderItem/"+orderItemId);
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                FormBody.Builder parameters = new FormBody.Builder();

                builder.post(parameters.build());
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;

        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class  PostAmount extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String amount_type = params[0];
            String advance_amount = params[1];
            String pending_amount = params[2];
            String total_amount = params[3];
            String OrderId = params[4];
            String CreatedBy = params[5];
            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "AmountPost");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                FormBody.Builder parameters = new FormBody.Builder();

                parameters.add("amount_id", "0");
                parameters.add("amount_type", amount_type);
                parameters.add("amount_pending", pending_amount);
                parameters.add("amount_advance", advance_amount);
                parameters.add("amount_total", total_amount);
                parameters.add("OrderId", OrderId);
                parameters.add("amount_discount", "");
                parameters.add("CreatedBy", CreatedBy);
                builder.post(parameters.build());
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;

        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class  PostItemAmount extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {


            String amount_id = params[0];
            String amount = params[1];
            String discountAmount = params[2];
            String AfterDiscountamount = params[3];
            String OrderItemID = params[4];

            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "AmountItemPost");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                FormBody.Builder parameters = new FormBody.Builder();

                parameters.add("amount_id", amount_id);
                parameters.add("amount", amount);
                parameters.add("discountAmount", discountAmount);
                parameters.add("AfterDiscountamount", AfterDiscountamount);
                parameters.add("OrderItemID", OrderItemID);

                builder.post(parameters.build());
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;

        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class GetCatlogMaterialtemList extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String json = "";
            String materialID = strings[0];
            String catalog_catagory_id = strings[1];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "GetCatalogItem/"+ materialID +"/"+ catalog_catagory_id);
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class  PostCatalogDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {


            String catalog_catagory_id = params[0];
            String CreatedBy = params[1];
            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "CatalogTableNewPost");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");

                FormBody.Builder parameters = new FormBody.Builder();

                parameters.add("catalog_catagory_id", catalog_catagory_id);
                parameters.add("design", "");
                parameters.add("serial_no", "");
                parameters.add("page_no", "");
                parameters.add("quantity", "");
                parameters.add("mrp", "");
                parameters.add("quality", "");
                parameters.add("discount", "");
                parameters.add("room_catagory_id", "");
                parameters.add("umo_id", "");
                parameters.add("msubcatagory_id", "");
                parameters.add("measurment_data","");
                parameters.add("CreatedBy",CreatedBy);
                parameters.add("AfterDiscountAmt","");
                parameters.add("SubMaterialID","");
                parameters.add("CreatedOn","");


                builder.post(parameters.build());
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;

        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class  PostCatalogItemDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {


            String catalog_item_id = params[0];
            String catalog_catagory_id = params[1];
            String room_catagory_id = params[2];
            String SubMaterialID = params[3];
            String TailorCalculateID = params[4];

            String design = params[5];
            String serial_no = params[6];
            String page_no = params[7];
            String quantity = params[8];
            String mrp = params[9];

            String quality = params[10];
            String discount = params[11];
            String AfterDiscountAmt = params[12];
           String KarigarCalculationID = params[13];

            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "CatalogItemNewPost");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");

                FormBody.Builder parameters = new FormBody.Builder();

                parameters.add("catalog_item_id", catalog_item_id);
                parameters.add("catalog_catagory_id", catalog_catagory_id);
                parameters.add("room_catagory_id", room_catagory_id);
                parameters.add("SubMaterialID",SubMaterialID);
                parameters.add("TailorCalculateID",TailorCalculateID);

                parameters.add("design", design);
                parameters.add("serial_no", serial_no);
                parameters.add("page_no", page_no);
                parameters.add("quantity", quantity);
                parameters.add("mrp", mrp);
                parameters.add("quality", quality);
                parameters.add("discount", discount);
                parameters.add("AfterDiscountAmt",AfterDiscountAmt);
                parameters.add("KarigarCalculationID",KarigarCalculationID);

                builder.post(parameters.build());
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;

        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class GetkarigarListDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String json = "";
            String accesstoken = strings[0];
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "GetKarigarList");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");

                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class  PostKarigarCalculate extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {


            String KarigarCalculationID = params[0];
            String karigarID = params[1];
            String karigarPrice = params[2];
            String quantity = params[3];
            String totalAmount = params[4];
            String OrderId = params[5];


            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "KarigarCalculationTablePost");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                FormBody.Builder parameters = new FormBody.Builder();

                parameters.add("KarigarCalculationID", KarigarCalculationID);
                parameters.add("karigarID", karigarID);
                parameters.add("karigarPrice", karigarPrice);
                parameters.add("quantity", quantity);
                parameters.add("totalAmount",totalAmount);
                parameters.add("OrderId", OrderId);


                builder.post(parameters.build());
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;

        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class  PostKarigarDetail extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {


            String karigarID = params[0];
            String karigarName = params[1];
            String karigarContact = params[2];

            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "KarigarTablePost");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                FormBody.Builder parameters = new FormBody.Builder();

                parameters.add("karigarID", karigarID);
                parameters.add("karigarName", karigarName);
                parameters.add("karigarContact", karigarContact);



                builder.post(parameters.build());
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;

        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class GetOrderFinalItems extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {



            String room_catagory_id = params[0];
            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "GetCustomerOederItem/"+room_catagory_id);
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                FormBody.Builder parameters = new FormBody.Builder();
                builder.post(parameters.build());
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;

        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class GetOrderFinal extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {



            String OrderId = params[0];
            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "GetCustomerFinalOrder/"+OrderId);
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;

        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static final class GetAmountOrder extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {



            String OrderId = params[0];
            String json = "";
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API + "GetOrderGetForAmount/"+OrderId);
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;

        }
        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {
                } else {
                    super.onPostExecute(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}