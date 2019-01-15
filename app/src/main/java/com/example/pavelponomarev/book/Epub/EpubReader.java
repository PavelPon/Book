package com.example.pavelponomarev.book.Epub;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pavelponomarev.book.R;
import com.github.mertakdut.BookSection;
import com.github.mertakdut.CssStatus;
import com.github.mertakdut.Reader;
import com.github.mertakdut.exception.OutOfPagesException;
import com.github.mertakdut.exception.ReadingException;

public class EpubReader extends AppCompatActivity implements PageFragment.OnFragmentReadyListener {
    private Reader reader;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private int pageCount = Integer.MAX_VALUE;
    private int pxScreenWidth;
    private boolean isPickedWebView = false;
    private MenuItem searchMenuItem;
    private SearchView searchView;
    private boolean isSkippedToPage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epub_reader);
        //Toast.makeText(this, "Открыт нужный  файл", Toast.LENGTH_SHORT).show();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        pxScreenWidth = getResources().getDisplayMetrics().widthPixels;

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        if (getIntent() != null && getIntent().getExtras() != null) {
            String filePath = getIntent().getExtras().getString("filePath");
            isPickedWebView = getIntent().getExtras().getBoolean("isWebView");
            try {
                reader = new Reader();
                reader.setMaxContentPerSection(1250);
                reader.setCssStatus(isPickedWebView ? CssStatus.INCLUDE : CssStatus.OMIT);
                reader.setIsIncludingTextContent(true);
                reader.setIsOmittingTitleTag(true);
                reader.setFullContent(filePath);
                if (reader.isSavedProgressFound()) {
                    int lastSavePage = reader.loadProgress();
                    mViewPager.setCurrentItem(lastSavePage);
                }

            } catch (ReadingException e) {
                Toast.makeText(EpubReader.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public View onFragmentReady(int position) {
        BookSection bookSection = null;
        try {
            bookSection = reader.readSection(position);

        } catch (ReadingException e) {
            e.printStackTrace();
            Toast.makeText(EpubReader.this, e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (OutOfPagesException e) {
            e.printStackTrace();
            this.pageCount = e.getPageCount();
            if (isSkippedToPage) {
                Toast.makeText(EpubReader.this, "Максимальное колличесво страниц: " + this.pageCount, Toast.LENGTH_LONG).show();

            }
            mSectionsPagerAdapter.notifyDataSetChanged();

        }
        isSkippedToPage = false;
        if (bookSection != null) {
            return setFragmentView(isPickedWebView, bookSection.getSectionContent(), "text/html", "UTF-8");

        }

        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null && !query.equals("")) {
                    if (TextUtils.isDigitsOnly(query)) {
                        loseFocusOnSearchView();
                        int skippingPage = Integer.valueOf(query);
                        if (skippingPage >= 0) {
                            isSkippedToPage = true;
                            mViewPager.setCurrentItem(skippingPage);
                        } else {
                            Toast.makeText(EpubReader.this, "Номер страницы не может быть меньше нуля", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        loseFocusOnSearchView();
                        Toast.makeText(EpubReader.this, "Введите номер страницы цифрами", Toast.LENGTH_LONG).show();
                    }
                    return true;
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            loseFocusOnSearchView();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            reader.saveProgress(mViewPager.getCurrentItem());
            Toast.makeText(EpubReader.this, "Прогресс сохранен: " + mViewPager.getCurrentItem() + "...", Toast.LENGTH_LONG).show();
        } catch (ReadingException e) {
            e.printStackTrace();
            Toast.makeText(EpubReader.this, "Прогресс не сохранен: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (OutOfPagesException e) {
            e.printStackTrace();
            Toast.makeText(EpubReader.this, "Прогресс не сохранен.Вне лимита колличесва страниц: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private View setFragmentView(boolean isContentStyled, String data, String mimeType, String encoding) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (isContentStyled) {
            WebView webView = new WebView(EpubReader.this);
            webView.loadDataWithBaseURL(null, data, mimeType, encoding, null);
            webView.setLayoutParams(layoutParams);
            return webView;
        } else {
            ScrollView scrollView = new ScrollView(EpubReader.this);
            scrollView.setLayoutParams(layoutParams);

            TextView textView = new TextView(EpubReader.this);
            textView.setLayoutParams(layoutParams);
            textView.setText(Html.fromHtml(data, new Html.ImageGetter() {
                @Override
                public Drawable getDrawable(String source) {
                    String imageAsStr = source.substring(source.indexOf(";base64") + 8);
                    byte[] imageAsBytes = Base64.decode(imageAsStr, Base64.DEFAULT);
                    Bitmap imageAsBitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                    int imageWidthStartPx = (pxScreenWidth - imageAsBitmap.getWidth()) / 2;
                    int imageWidthEndPx = pxScreenWidth - imageWidthStartPx;
                    Drawable imageAsDrawable = new BitmapDrawable(getResources(), imageAsBitmap);
                    imageAsDrawable.setBounds(imageWidthStartPx, 0, imageWidthEndPx, imageAsBitmap.getHeight());
                    return imageAsDrawable;
                }
            }, null));
            int pxPadding = dpToPx(12);
            textView.setPadding(pxPadding, pxPadding, pxPadding, pxPadding);
            scrollView.addView(textView);
            return scrollView;
        }
    }

    private void loseFocusOnSearchView() {
        searchView.setQuery("", false);
        searchView.clearFocus();
        searchView.setIconified(true);
        MenuItemCompat.collapseActionView(searchMenuItem);

    }

    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstence(position);
        }

        @Override
        public int getCount() {
            return pageCount;
        }
    }
}


