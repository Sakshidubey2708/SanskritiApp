package com.example.awizom.sanskritidecoreapp.activity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.awizom.sanskritidecoreapp.R;
import com.example.awizom.sanskritidecoreapp.config.AppConfig;
import com.example.awizom.sanskritidecoreapp.helper.Helper;
import com.example.awizom.sanskritidecoreapp.helper.OrderHelper;
import com.example.awizom.sanskritidecoreapp.helper.SharedPrefManager;
import com.example.awizom.sanskritidecoreapp.model.HandOverModel;
import com.example.awizom.sanskritidecoreapp.modelnew.CompleteOrderModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class AmountOrderActivity extends AppCompatActivity {

    private TextView amountTotal, textAdavanceAmounts;
    private AutoCompleteTextView amountAdvance;
    TextView amountPending, grossAmount, amountDiscount;
    private Spinner amountType;
    private Button addAmount, okButton;
    private String amounttype, amountadvance, amountpending, amounttotal, amountdiscount;
    private ProgressDialog progressDialog;
    private String result = "", orderid = "", custmerName = "", MRPTotlalAmount, DiscountAmount, GrossAmount;
    ArrayAdapter<String> adapter;
    List<HandOverModel> handOverlist1;
    private Intent pdfOpenintent;
    private String hTelor;
    private CompleteOrderModel completeOrderModel;
    private long order_id = 0;
    private boolean isAmount = false;
    private LinearLayout secondBottomLayouts;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amount_order_layout);
        progressDialog = new ProgressDialog(this);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Payment Details ");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.slide_in, R.anim.slide_out);
                onBackPressed();
            }
        });

        toolbar.setSubtitleTextAppearance(getApplicationContext(), R.style.styleA);
        toolbar.setTitleTextAppearance(getApplicationContext(), R.style.styleA);
        toolbar.setTitleTextColor(Color.WHITE);

        init();

    }

    public void init() {
        orderid = getIntent().getExtras().getString("ORDERID", "");
        custmerName = getIntent().getExtras().getString("CustomerName", "");
        MRPTotlalAmount = String.valueOf(getIntent().getExtras().getDouble("MRP", 0));
        DiscountAmount = getIntent().getExtras().getString("Discount", "");
        GrossAmount = String.valueOf(getIntent().getExtras().getDouble("GrossAmount", 0));

        amountPending = findViewById(R.id.pendingAmount);
        grossAmount = findViewById(R.id.grossAmount);
        amountTotal = findViewById(R.id.totalAmount);
        textAdavanceAmounts = findViewById(R.id.textAdavanceAmount);
        amountType = findViewById(R.id.amountType);
        addAmount = findViewById(R.id.addAmount);
        okButton = findViewById(R.id.okBtn);
        amountAdvance = findViewById(R.id.advanceAmount);
        amountDiscount = findViewById(R.id.discountAmount);
        secondBottomLayouts = findViewById(R.id.secondBottomLayout);

        Double total = Double.parseDouble(GrossAmount.toString());
        if (total > 0) {
            amountAdvance.setEnabled(true);
        } else {
            amountAdvance.setEnabled(false);
        }

        amountDiscount.setText(DiscountAmount.toString() + "%");
        amountTotal.setText(MRPTotlalAmount.toString());
        int i = Math.round(Float.parseFloat(GrossAmount.toString()));
        grossAmount.setText(String.valueOf(i) + ".00");

        //GetCustomerCompleteOrder(orderid);

        String[] arraySpinner = new String[]{
                "Cash"
        };
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amountType.setAdapter(adapter);

        amountType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(
                    AdapterView<?> adapterView, View view,
                    int i, long l) {

                amounttype = amountType.getItemAtPosition(i).toString();
            }

            public void onNothingSelected(
                    AdapterView<?> adapterView) {

            }
        });


        amountAdvance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (amountAdvance.getText().toString().length() == 0) {

                } else {
                    Double total, discount, advance, discountamount, resutl;

                    total = Double.parseDouble(GrossAmount.toString());
                    advance = Double.parseDouble(amountAdvance.getText().toString());
                    resutl = total - advance;
                    if (resutl != null) {
                        //  amountPending.setText(resutl.toString());
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (amountAdvance.getText().toString().length() == 0) {

                } else {
                    Double total, discount, advance, discountamount, resutl;

                    total = Double.parseDouble(GrossAmount.toString());

                    advance = Double.parseDouble(amountAdvance.getText().toString());
                    resutl = total - advance;
                    if (resutl != null) {
                        amountPending.setText(resutl.toString());
                        discountamount = Double.parseDouble(amountAdvance.getText().toString());
                        int i = Math.round(Float.parseFloat(discountamount.toString()));
                        textAdavanceAmounts.setText(String.valueOf(i) + ".00");
                        // isAmount=false;
                        Double pending;
                        pending = Double.parseDouble(amountPending.getText().toString());
                        if (pending.equals(0.0)) {
                            isAmount = true;
                            addStatus(isAmount);
                        } else {
                            isAmount = false;
                            addStatus(isAmount);
                        }
                    }

                }

            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);

                if (!amountAdvance.getText().toString().trim().isEmpty()) {
                    Double total, discount, advance, discountamount, resutl;

                    total = Double.parseDouble(GrossAmount.toString());
                    advance = Double.parseDouble(amountAdvance.getText().toString());
                    resutl = total - advance;
                    if (advance <= total) {

                        if (resutl != null) {
                            secondBottomLayouts.setVisibility(View.VISIBLE);
                            int i = Math.round(Float.parseFloat(resutl.toString()));
                            amountPending.setText(String.valueOf(i) + ".00");
                            Double pending;
                            pending = Double.parseDouble(amountPending.getText().toString());
                            //isAmount = false;
                            if (pending.equals(0.0)) {
                                isAmount = true;
                                addStatus(isAmount);
                            } else {
                                isAmount = false;
                                addStatus(isAmount);
                            }
                        }
                    } else {
                        amountAdvance.setError("Amount not large");
                    }
                }
            }
        });

        addAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                switch (v.getId()) {
                    case R.id.addAmount:
                        if (amountAdvance.getText().toString().isEmpty() || amountPending.getText().toString().isEmpty() || amountTotal.getText().toString().isEmpty()) {
                            amountAdvance.setError("Required !");
                            amountPending.setError("Required !");
                            amountTotal.setError("Required !");

                        } else {
                            AddAmountOrder();


                        }

                        break;
                }
            }
        });
    }


    private void GetCustomerCompleteOrder(String oId) {
        try {

            result = new OrderHelper.GetCompleteOrderModel().execute(oId, SharedPrefManager.getInstance(this).getUser().access_token).get();
            if (result.isEmpty()) {
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<CompleteOrderModel>() {
                }.getType();
                completeOrderModel = new Gson().fromJson(result, listType);
                if (completeOrderModel != null) {
                    order_id = completeOrderModel.getOrderid();
//                    createPDF();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //  Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
            ///
        }
    }

    private void AddAmountOrder() {

        amountadvance = amountAdvance.getText().toString();
        amountpending = amountPending.getText().toString();
        amounttotal = amountTotal.getText().toString();
        amountdiscount = amountDiscount.getText().toString();

        try {
            progressDialog.setMessage("loading...");
            progressDialog.show();
            result = new Helper.AddAmoutnOrder().execute("Cash", amountadvance, amountpending, amounttotal, orderid, amountdiscount.toString().split("%")[0], SharedPrefManager.getInstance(AmountOrderActivity.this).getUser().access_token).get();

            try {
                if (result.isEmpty()) {
                    progressDialog.dismiss();
                    //  Toast.makeText(AmountOrderActivity.this, "Result empty", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                    GetCustomerCompleteOrder(orderid);
                    Gson gson = new Gson();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(AppConfig.BASE_URL + "Report/OrderDetail.aspx?OrderID=" + orderid));
                    startActivity(browserIntent);

                }

            } catch (Exception e) {
                progressDialog.dismiss();

            }
        } catch (Exception e) {
            e.printStackTrace();
            progressDialog.dismiss();

        }

    }

    private void addStatus(boolean isAmount) {


        try {
            progressDialog.setMessage("loading...");
            progressDialog.show();
            result = new Helper.AddStatus().execute(orderid, String.valueOf(false), String.valueOf(false), String.valueOf(isAmount), SharedPrefManager.getInstance(AmountOrderActivity.this).getUser().access_token).get();

            try {
                if (result.isEmpty()) {
                    progressDialog.dismiss();
                    //Toast.makeText(AmountOrderActivity.this, "Result empty", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                    Gson gson = new Gson();
                    // Toast.makeText(AmountOrderActivity.this, "Successful", Toast.LENGTH_SHORT).show();

                }

            } catch (Exception e) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
            progressDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {

        finish();
        Intent intent = new Intent(AmountOrderActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    //For PDF Creation
//    private void createPDF() {
//
//        Document doc = new Document();
//
//        PdfPTable table = new PdfPTable(new float[]{2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2});
//        table.getDefaultCell().
//
//                setHorizontalAlignment(Element.ALIGN_CENTER);
//
//        table.addCell("Name");
//        table.addCell("Catalog");
//        table.addCell("Room");
//        table.addCell("Design");
//        table.addCell("S.No");
//        table.addCell("PageNo");
//        table.addCell("Quantity");
//        table.addCell("Amount type");
//        table.addCell("MRP");
//        table.addCell("Advance");
//        table.addCell("Pending Amount");
//        table.addCell("Discount");
//        table.addCell("Total");
//        table.setHeaderRows(1);
//        PdfPCell[] cells = table.getRow(0).getCells();
//        for (
//                int j = 0;
//                j < cells.length; j++)
//
//        {
//            cells[j].setBackgroundColor(BaseColor.GRAY);
//        }
//
//            table.addCell(custmerName);
//            table.addCell(completeOrderModel.getOrder_type());
//            table.addCell(completeOrderModel.getRoom_type());
//            table.addCell(completeOrderModel.getDesign());
//            table.addCell(completeOrderModel.getSerial_no());
//            table.addCell(String.valueOf(completeOrderModel.getPage_no()));
//            table.addCell(String.valueOf(completeOrderModel.getQuantity()));
//            table.addCell(String.valueOf(completeOrderModel.getAmount_type()));
//            table.addCell(String.valueOf(completeOrderModel.getMrp()));
//            table.addCell(String.valueOf(completeOrderModel.getAmount_advance()));
//            table.addCell(String.valueOf(completeOrderModel.getAmount_pending()));
//            table.addCell(String.valueOf(completeOrderModel.getDiscount()));
//            table.addCell(String.valueOf(completeOrderModel.getAmount_total()));
//
//
//
//
//        try
//
//        {
//            // String path =Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDF";
//
//            String path = AmountOrderActivity.this.getExternalFilesDir(Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath();
//
//            File dir = new File(String.valueOf(path));
//            if (!dir.exists())
//                dir.mkdirs();
//
//            Log.d("PDFCreator", "PDF Path: " + path);
//
//            File file = new File(dir, "CustomerOrderDetail.pdf");
//
//            FileOutputStream fOut = new FileOutputStream(file);
//
//
//            PdfWriter.getInstance(doc, fOut);
//
//            //open the document
//            doc.open();
//
//            Paragraph p1 = new Paragraph(hTelor);
//
//
//            /* You can also SET FONT and SIZE like this */
//            Font paraFont1 = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.UNDERLINE, BaseColor.BLACK);
//            p1.setAlignment(Paragraph.ALIGN_CENTER);
//
//            p1.setSpacingAfter(20);
//            p1.setFont(paraFont1);
//            doc.add(p1);
//
//            /* You can also SET FONT and SIZE like this */
//
//
//            doc.setMargins(0, 0, 5, 5);
//            doc.add(table);
//
//            Phrase footerText = new Phrase("This is an amount_order_layout of a footer");
//            HeaderFooter pdfFooter = new HeaderFooter();
//            doc.newPage();
//
//            Toast.makeText(AmountOrderActivity.this, "Created...", Toast.LENGTH_LONG).show();
//
//
//        } catch (
//                DocumentException de)
//
//        {
//            Log.e("PDFCreator", "DocumentException:" + de);
//        } catch (
//                IOException e)
//
//        {
//            Log.e("PDFCreator", "ioException:" + e);
//        } finally
//
//        {
//            doc.close();
//        }
//
//
//        pdfOpenintent = new Intent(AmountOrderActivity.this, PdfViewActivity.class);
//        pdfOpenintent = pdfOpenintent.putExtra("PDFName","/CustomerOrderDetail.pdf");
//        startActivity( pdfOpenintent);
//        //  openPdf();
//
//        /////
//    }
//    private class HeaderFooter {
//
//        Phrase[] header = new Phrase[2];
//        /**
//         * Current page number (will be reset for every chapter).
//         */
//        int pagenumber;
//
//        /**
//         * Initialize one of the headers.
//         *
//         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onOpenDocument(
//         *com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
//         */
//        public void onOpenDocument(PdfWriter writer, Document document) {
//            header[0] = new Phrase("Movie history");
//        }
//
//        /**
//         * Initialize one of the headers, based on the chapter title;
//         * reset the page number.
//         *
//         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onChapter(
//         *com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document, float,
//         * com.itextpdf.text.Paragraph)
//         */
//        public void onChapter(PdfWriter writer, Document document,
//                              float paragraphPosition, Paragraph title) {
//            header[1] = new Phrase(title.getContent());
//            pagenumber = 1;
//        }
//
//        /**
//         * Increase the page number.
//         *
//         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onStartPage(
//         *com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
//         */
//        public void onStartPage(PdfWriter writer, Document document) {
//            pagenumber++;
//        }
//
//        /**
//         * Adds the header and the footer.
//         *
//         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
//         *com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
//         */
//        public void onEndPage(PdfWriter writer, Document document) {
//            Rectangle rect = writer.getBoxSize("art");
//            switch (writer.getPageNumber() % 2) {
//                case 0:
//                    ColumnText.showTextAligned(writer.getDirectContent(),
//                            Element.ALIGN_RIGHT, header[0],
//                            rect.getRight(), rect.getTop(), 0);
//                    break;
//                case 1:
//                    ColumnText.showTextAligned(writer.getDirectContent(),
//                            Element.ALIGN_LEFT, header[1],
//                            rect.getLeft(), rect.getTop(), 0);
//                    break;
//            }
//            ColumnText.showTextAligned(writer.getDirectContent(),
//                    Element.ALIGN_CENTER, new Phrase(String.format("page %d", pagenumber)),
//                    (rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 18, 0);
//        }
//    }
}