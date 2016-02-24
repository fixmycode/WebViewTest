### Android System Webview Test

Small test app that shows a bug present in Android System Webview 48 and 49 beta.
The Webview fails to calculate the scroll limits correctly if there's no information
about the viewport scale, so operations like `window#scrollBy` won't scroll until
the edge of the document.

An example of this behaviour can be seen in this code, that resembles a book reader
application that manages the horizontal scrolling of the pages by requesting a window
scroll when the user swipes the screen.

Instructions for a workaround are present along the code and requires you to edit
the html page in `assets/book.html`.