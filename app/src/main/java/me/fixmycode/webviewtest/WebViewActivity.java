package me.fixmycode.webviewtest;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {
    private static final int MIN_DISTANCE = 100;
    private WebView webView;
    private GestureDetector gestureDetector;

    @SuppressLint({"SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_webview);

        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setHorizontalScrollBarEnabled(true);
        webView.setVerticalScrollBarEnabled(false);

        Log.d("WEBVIEWTEST", "User Agent: " + webView.getSettings().getUserAgentString());

        webView.loadUrl("file:///android_asset/book.html");

        gestureDetector = new GestureDetector(this, new ReaderGestureListener());

        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    class ReaderGestureListener extends GestureDetector.SimpleOnGestureListener {
        /* This makes the paginated effect and logs some values so we can easily observe the
         * problem with the webview. I've used JQuery but it can be easily achieved with pure JS */
        @Override
        public boolean onFling(MotionEvent downMotion, MotionEvent upMotion,
                               float velX, float velY) {
            float delta = upMotion.getX() - downMotion.getX();
            if (Math.abs(delta) > MIN_DISTANCE) {
                webView.loadUrl("javascript:" +
                        "$('html, body').animate({" +
                            "scrollLeft: $(window).scrollLeft() " +
                            (delta > 0 ? "-" : "+") + " $(window).width()" +
                        "}).promise().then(function() {" +
                            "console.log('scroll position: ' + $(window).scrollLeft()" +
                            " + ', document width: ' + $(document).width())" +
                        "})");
            }
            return true;
        }
    }
}
