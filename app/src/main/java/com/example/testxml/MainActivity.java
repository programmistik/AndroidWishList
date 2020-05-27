package com.example.testxml;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends Activity {
    ArrayList<Product> products = new ArrayList();
    String[] currList = {"AZN", "USD", "EUR"};
    ArrayList<Currency> currencies = new ArrayList();
    TextView selection;
    ListView productList;
    Double Total = 0d;


    private ArrayAdapter<Product> adapter ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Currency curr = new Currency("AZN", "Manat", "1", "1");
        currencies.add(curr);

        try {

            // parse our XML
            new parseXmlAsync().execute();

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(products.size()==0){
            products.add(new Product("Mi Band ", R.drawable.ic_watch_black_24dp,60.0d));
            products.add(new Product("Phone", R.drawable.ic_phone_android_black_24dp,170.0d));
            products.add(new Product("Cake", R.drawable.ic_cake_black_24dp,10.0d));
            products.add(new Product("Android soft toy", R.drawable.ic_android_black_24dp,15.0d));
            products.add(new Product("Car", R.drawable.ic_directions_car_black_24dp,105.0d));
        }
        productList = (ListView) findViewById(R.id.productList);
       // ProductAdapter adapter = new ProductAdapter(this, R.layout.list_item, products);

        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView<?> parent, View item,
                                     int position, long id) {
                Product product = adapter.getItem( position );
                product.toggleChecked();
                ProductViewHolder viewHolder = (ProductViewHolder) item.getTag();
                viewHolder.getCheckBox().setChecked( product.isChecked() );

                if (product.isChecked())
                    Total = Total + product.getAznPrice();
                else
                    Total = Total - product.getAznPrice();

                selection = (TextView) findViewById(R.id.textView1);
                Spinner sp  = (Spinner) findViewById(R.id.currSpinner);
                String cur = sp.getSelectedItem().toString();

                Double sum = 0d;

                for(Currency c : currencies) {
                    if(c.getCode().equals(cur)) {
                        Double kurs = c.getValue();
                        sum = Total/kurs;
                    }
                }

                selection.setText(" Total sum: " + String.format("%.2f", sum) + " " + cur );
            }
        });
        adapter = new ProductAdapter(this,products);
        productList.setAdapter( adapter );



        Spinner sp = (Spinner) findViewById(R.id.currSpinner);
        selection = (TextView) findViewById(R.id.textView1);

        ArrayAdapter<String> Sadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, currList);
        Sadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp.setAdapter(Sadapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                String item = (String)parent.getItemAtPosition(position);

                Double sum = 0d;

                for(Currency c : currencies) {
                    if(c.getCode().equals(item)) {
                        Double kurs = c.getValue();
                        sum = Total/kurs;
                    }
                }

                selection.setText(" Total sum: " + String.format("%.2f", sum) + " " + item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        sp.setOnItemSelectedListener(itemSelectedListener);
    }




    private class parseXmlAsync extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            try {

                // initialize our input source variable
                InputSource inputSource = null;


                    String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
                    SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
                    Date dt = new Date();
                    String day          = (String) DateFormat.format("dd",   dt);
                    String monthNumber  = (String) DateFormat.format("MM",   dt);
                    String year         = (String) DateFormat.format("yyyy", dt);

                    String today = day+"."+monthNumber+"."+year;
                    URL url = new URL(
                            "https://www.cbar.az/currencies/"+today+".xml");
                    inputSource = new InputSource(url.openStream());


                // instantiate SAX parser
                SAXParserFactory saxParserFactory = SAXParserFactory
                        .newInstance();
                SAXParser saxParser = saxParserFactory.newSAXParser();

                // get the XML reader
                XMLReader xmlReader = saxParser.getXMLReader();


                XmlContentHandler xmlContentHandler = new XmlContentHandler();
                xmlReader.setContentHandler(xmlContentHandler);

                // parse the XML input source
                xmlReader.parse(inputSource);


                List<ParsedDataSet> parsedDataSet = xmlContentHandler
                        .getParsedData();


                Iterator<ParsedDataSet> i = parsedDataSet.iterator();
                ParsedDataSet dataItem;

                while (i.hasNext()) {

                    dataItem = (ParsedDataSet) i.next();

                    String parentTag = dataItem.getParentTag();


                    if (parentTag.equals("Valute")) {

                        if (dataItem.getName().equals("1 ABŞ dolları"  )){
                            Currency curr = new Currency("USD", dataItem.getName(), dataItem.getNominal(),dataItem.getValue());
                            currencies.add(curr);
                        }
                        else if(dataItem.getName().equals("1 Avro" )) {
                            Currency curr = new Currency("EUR", dataItem.getName(), dataItem.getNominal(), dataItem.getValue());
                            currencies.add(curr);
                        }
                    }



                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String lenghtOfFile) {
            // your do stuff after parsing the XML
        }
    }

}

