package com.example.testxml;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/*class ProductAdapter extends ArrayAdapter<Product> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Product> productList;

    ProductAdapter(Context context, int resource, ArrayList<Product> products) {
        super(context, resource, products);
        this.productList = products;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public void addItem(View view) {
        // Получаем флажок
        CheckBox language = (CheckBox) view;
        // Получаем, отмечен ли данный флажок
        boolean checked = language.isChecked();

        //TextView selection = (TextView) findViewById(R.id.selection);
        // Смотрим, какой именно из флажков отмечен
        switch(view.getId()) {

        }
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Product product = productList.get(position);

        viewHolder.nameView.setText(product.getName());
        viewHolder.priceView.setText("Price: "+ product.getUsdPrice() + " AZN");
        ImageView photoView = (ImageView) convertView.findViewById(R.id.pictureView);
        photoView.setImageResource(R.drawable.ic_android_black_24dp);


        viewHolder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }


    private class ViewHolder {
        final Button addButton;
        final TextView nameView, priceView;
        ViewHolder(View view){
            addButton = (Button) view.findViewById(R.id.addButton);
            // removeButton = (Button) view.findViewById(R.id.removeButton);
            nameView = (TextView) view.findViewById(R.id.nameView);
            priceView = (TextView) view.findViewById(R.id.priceView);
        }
    }
}
*/

class ProductAdapter extends ArrayAdapter<Product> {

    private LayoutInflater inflater;

    public ProductAdapter( Context context, List<Product> productList ) {
        super( context, R.layout.list_item, R.id.priceView, productList );
        // Cache the LayoutInflate to avoid asking for a new one each time.
        inflater = LayoutInflater.from(context) ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Planet to display
        Product product = this.getItem( position );

        // The child views in each row.
        CheckBox checkBox ;
        TextView textView ;
        TextView priceView ;
        ImageView pictureView;

        // Create a new row view
        if ( convertView == null ) {
            convertView = inflater.inflate(R.layout.list_item, null);

            // Find the child views.
            textView = (TextView) convertView.findViewById( R.id.nameView );
            priceView = (TextView) convertView.findViewById( R.id.priceView );
            pictureView = (ImageView) convertView.findViewById( R.id.pictureView );
            checkBox = (CheckBox) convertView.findViewById( R.id.addChekBox );

            // Optimization: Tag the row with it's child views, so we don't have to
            // call findViewById() later when we reuse the row.
            convertView.setTag( new ProductViewHolder(textView, priceView, pictureView, checkBox) );

            // If CheckBox is toggled, update the planet it is tagged with.
            checkBox.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    Product product = (Product) cb.getTag();
                    product.setChecked( cb.isChecked() );
                }
            });
        }
        // Reuse existing row view
        else {
            // Because we use a ViewHolder, we avoid having to call findViewById().
            ProductViewHolder viewHolder = (ProductViewHolder) convertView.getTag();
            checkBox = viewHolder.getCheckBox() ;
            textView = viewHolder.getTextView() ;
            pictureView = viewHolder.getPicView();
            priceView = viewHolder.getPriceView();
        }

        // Tag the CheckBox with the Planet it is displaying, so that we can
        // access the planet in onClick() when the CheckBox is toggled.
        checkBox.setTag( product );

        // Display planet data
        checkBox.setChecked( product.isChecked() );
        textView.setText( product.getName() );
        pictureView.setImageResource(product.getPicture());
        priceView.setText( "Price: "+ product.getAznPrice() + " AZN" );

        return convertView;
    }

}

